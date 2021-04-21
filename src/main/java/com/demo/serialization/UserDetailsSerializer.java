package com.demo.serialization;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import com.demo.model.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.demo.util.StringUtil;

import java.io.IOException;

public class UserDetailsSerializer implements StreamSerializer<UserDetails> {
    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsSerializer.class);

    @Override
    public void write(ObjectDataOutput output, UserDetails externalUser) throws IOException {
        output.writeUTF(StringUtil.marshall(externalUser));
    }

    @Override
    public UserDetails read(ObjectDataInput input) throws IOException {
        return StringUtil.unmarshall(input.readUTF(), UserDetails.class);
    }

    @Override
    public int getTypeId() {
        return 10;
    }

    @Override
    public void destroy() {
    }
}
