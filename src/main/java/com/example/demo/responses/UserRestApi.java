package com.example.demo.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UserRestApi {
    @JsonProperty("dateTime")
    private  String dateTimeString;

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.parse(dateTimeString);
    }
}