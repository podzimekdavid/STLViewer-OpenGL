package com.dp.main.utils;


import java.net.URI;

public class Utils {
    public static URI resourcePath(String path) {
        try {
            return Thread.currentThread().getContextClassLoader().getResource(path).toURI();
        } catch (Exception e) {
            return null;
        }

    }
}
