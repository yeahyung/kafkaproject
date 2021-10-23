package com.example.kafkaproject.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

public class JsonUtil {

    private static ObjectMapper objectMapper;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
    }

    public static String convertMapToJsonLine(Object obj){
        try{
            return objectMapper.writer().writeValueAsString(obj);
        } catch(JsonProcessingException e){
            System.out.println(e);
        }

        return null;
    }
}
