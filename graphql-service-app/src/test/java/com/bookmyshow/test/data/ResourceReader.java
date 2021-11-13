package com.bookmyshow.test.data;

import java.io.IOException;
import java.io.InputStream;

public class ResourceReader {

    public static final String GRAPHQL_QUERY_REQUEST_PATH = "graphql/request/";
    public static final String GRAPHQL_QUERY_RESPONSE_PATH = "graphql/response/";
    private static final String JSON_EXTENSION = ".json";
    private static final String GRAPHQL_EXTENSION = ".graphql";

    public static String getRequestPath(String fileName) {
        return GRAPHQL_QUERY_REQUEST_PATH + fileName + GRAPHQL_EXTENSION;
    }

    public static String getResponsePath(String fileName) {
        return GRAPHQL_QUERY_RESPONSE_PATH + fileName + JSON_EXTENSION;
    }

    public static String readFromResource(String resourcePath) {
        try (InputStream in = ResourceReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            assert in != null;
            return new String(in.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}