package com.ask.example.controller;

import com.ask.example.domain.App;
import com.ask.example.utils.IdGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AppResponse implements CommonResponseInterface {

    public static final AppResponse EMPTY = new AppResponse();

    @JsonProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long appId;

    private String appName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    private List<AppResponse.Task> tasks;

    public AppResponse() {}
    public AppResponse(Long appId, String appName, LocalDateTime createdAt) {
        this.appId = appId;
        this.appName = appName;
        this.createdAt = createdAt;
        this.tasks = new ArrayList<>();
    }

    public static AppResponse of(App app) {
        AppResponse appResponse = new AppResponse();
        appResponse.appId = app.getAppId();
        appResponse.appName = app.getAppName();
        appResponse.createdAt = app.getCreatedAt().toLocalDateTime();


//        AppResponse appResponse = new AppResponse(appId, appName, LocalDateTime.now());
//        appResponse.addTask("v1", LocalDateTime.now());
//        appResponse.addTask("v2", LocalDateTime.now());
        return appResponse;
    }

    public void addTask(String version, LocalDateTime createdAt) {
        tasks.add(new AppResponse.Task(IdGenerator.create(), version, createdAt));
    }

    public static Task createTask(Long taskId, String version) {
        return new Task(taskId, version, LocalDateTime.now());
    }

    @Getter
    public static class Task {

        @JsonProperty("id")
        @JsonSerialize(using = ToStringSerializer.class)
        private final Long taskId;

        private final String version;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        private final LocalDateTime createdAt;

        public Task(Long taskId, String version, LocalDateTime createdAt) {
            this.taskId = taskId;
            this.version = version;
            this.createdAt = createdAt;
        }
    }
}
