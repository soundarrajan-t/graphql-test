package com.bookmyshow.test.data;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {

    public static Map<String, String> getResponse(String title) {
        Map<String, String> response = new HashMap<>();
        response.put("Title", title);
        response.put("Response", "True");
        response.put("imdbRating", "0.0");
        return response;
    }
}
