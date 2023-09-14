package com.ask.example.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Entity
@Table(name = "apps")
public class App {

    @Id
    @Column(name = "app_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appId;

    @Column(name = "app_name", nullable = false, length = 300)
    private String appName;

    @Column(name = "created_at")
    @Temporal(value = TemporalType.TIMESTAMP)
    private ZonedDateTime createdAt;

    public App() {}
    public App(String appName) {
        this.appName = appName;
    }
    public static App of(String appName, ZonedDateTime createdAt) {
        App app = new App();
        app.appName = appName;
        app.createdAt = createdAt;
        return app;
    }
}
