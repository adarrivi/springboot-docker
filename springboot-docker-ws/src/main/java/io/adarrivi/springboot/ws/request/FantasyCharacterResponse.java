package io.adarrivi.springboot.ws.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class FantasyCharacterResponse {

    private String fullName;
    private int age;
    private String currentStatus;
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private LocalDateTime createdAt;

    FantasyCharacterResponse() {
        //Needed by json parser
    }

    public FantasyCharacterResponse(String fullName, int age, String currentStatus) {
        this.fullName = fullName;
        this.age = age;
        this.currentStatus = currentStatus;
        this.createdAt = LocalDateTime.now();
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
