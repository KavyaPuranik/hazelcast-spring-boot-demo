package com.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Marshaller {
    private static final Logger LOG = LoggerFactory.getLogger(Marshaller.class);
    private ObjectMapper mapper;

    public Marshaller() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public <T> String marshall(T obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOG.error("Unable to write value", e);
            return null;
        }
    }

    public <T> T unmarshall(String str, Class<T> clz) {
        try {
            return mapper.readValue(str, clz);
        } catch (IOException e) {
            LOG.error("Unable to read value", e);
            return null;
        }
    }

    public <T> T unmarshall(String str, TypeReference<T> reference) {
        try {
            return mapper.readValue(str, reference);
        } catch (IOException e) {
            LOG.error("Unable to read value", e);
            return null;
        }
    }
}
