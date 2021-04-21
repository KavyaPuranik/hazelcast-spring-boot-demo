package com.demo.controller;

import com.hazelcast.core.HazelcastInstance;
import com.demo.model.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import com.demo.util.StringUtil;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/hazelcast")
public class HazelcastController {
    private static final Logger LOG = LoggerFactory.getLogger(HazelcastController.class);
    private final HazelcastInstance hazelcastInstance;

    @Autowired
    HazelcastController(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @PostMapping("/write")
    public String write(@RequestParam String key,
                        @RequestParam String value) {
        Map<String, UserDetails> hazelcastMap = hazelcastInstance.getMap("UserDetails");
        hazelcastMap.put(key, StringUtil.unmarshall(value, UserDetails.class));
        return String.format("written %s", key);
    }

    @GetMapping(value = "/read", produces = APPLICATION_JSON_VALUE)
    public String read(@RequestParam String key) {
        Map<String, UserDetails> hazelcastMap = hazelcastInstance.getMap("UserDetails");
        return StringUtil.marshall(hazelcastMap.get(key));
    }

    @GetMapping(value = "/read-all", produces = APPLICATION_JSON_VALUE)
    public Map<String, UserDetails> readAll() {
        return hazelcastInstance.getMap("UserDetails");
    }
}
