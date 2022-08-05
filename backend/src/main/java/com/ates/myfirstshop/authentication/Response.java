package com.ates.myfirstshop.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private int statusCode;
    private String body;
    private Map<String, String> header;

}
