package com.demo.config;

import com.hazelcast.config.*;
import com.demo.model.UserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.demo.serialization.UserDetailsSerializer;

@Configuration
public class HazelcastConfiguration {
    @Bean
    public Config hazelcastConfig() {
        SerializerConfig serializerConfig = new SerializerConfig();
        serializerConfig.setImplementation(new UserDetailsSerializer()).setTypeClass(UserDetails.class);

        Config config = new Config();

        NetworkConfig network = config.getNetworkConfig();
        network.setPortAutoIncrement(true);
        network.setPort(5701).setPortCount(20);
        JoinConfig join = network.getJoin();
        join.getMulticastConfig().setEnabled(false);
        join.getTcpIpConfig()
                .addMember("localhost").setEnabled(true);

        config.setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig()
                        .setName("UserDetails")
                        .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setTimeToLiveSeconds(-1))
                .getSerializationConfig()
                .addSerializerConfig(serializerConfig);

        return config;
    }
}
