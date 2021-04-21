package com.demo.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

@Component
public class StringUtil {
    private static Marshaller marshaller = new Marshaller();

    public static <T> String marshall(T obj) {
        return marshaller.marshall(obj);
    }

    public static <T> T unmarshall(String str, Class<T> clz) {
        return marshaller.unmarshall(str, clz);
    }

    public static <T> T unmarshall(String str, TypeReference<T> reference) {
        return marshaller.unmarshall(str, reference);
    }
}
