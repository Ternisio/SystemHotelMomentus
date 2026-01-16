package com.example.demo.Util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonEscape {
    public static String jsonEscape(Map<String,Object> value) {
        try {
            return new ObjectMapper().writeValueAsString(value);
        } catch (Exception e) {
            return "\"\"";
        }
    }
}
