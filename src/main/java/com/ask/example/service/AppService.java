package com.ask.example.service;

import com.ask.example.controller.AppRequest;
import com.ask.example.controller.AppResponse;
import com.ask.example.domain.App;
import com.ask.example.repository.AppRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

@Service
public class AppService {

    private final AppRepository appRepository;

    public AppService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }


    public AppResponse createApp(AppRequest appRequest) {
        App app = App.of(appRequest.getAppName(), ZonedDateTime.now());
        appRepository.save(app);
        return AppResponse.of(app);
    }


    public List<AppResponse> getAppAll(Boolean asc) {
        Sort.Direction orderBy;
        if (asc) {
            orderBy = Sort.Direction.ASC;
        } else {
            orderBy = Sort.Direction.DESC;
        }

        return appRepository.findAll(Sort.by(orderBy, "createdAt"))
                .stream()
                .map(AppResponse::of)
                .collect(Collectors.toList());
    }

    public AppResponse getAppById(Long appId) {
        return appRepository.findById(appId)
                .map(AppResponse::of)
                .orElse(AppResponse.EMPTY);
    }

    public List<AppResponse> getAppsByAppName(String appName) {
        Example<App> example = Example.of(new App(appName));
//        appRepository.findAll(example);
        return appRepository.findByAppName(appName)
                .stream()
                .map(AppResponse::of)
                .collect(Collectors.toList());
    }

    public List<AppResponse> getAppByStartsWithAppName(String appName) {

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("appName", startsWith().ignoreCase());

        Example<App> example = Example.of(new App(appName), matcher);

        return appRepository.findAll(example)
                .stream()
                .map(AppResponse::of)
                .collect(Collectors.toList());
    }

    public Long getCountByStartsWithAppName(String appName) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("appName", startsWith().ignoreCase());

        Example<App> example = Example.of(new App(appName), matcher);

        return appRepository.count(example);
    }

    public List<AppResponse> findByAppNameLikeIgnoreCaseOrderByCreatedAt(String appName, Boolean asc) {

//        if (asc) {
//            return appRepository.findByAppNameLikeIgnoreCaseOrderByCreatedAt(appName)
//                    .stream()
//                    .map(AppResponse::of)
//                    .collect(Collectors.toList());
//        } else {
//            return appRepository.findByAppNameLikeIgnoreCaseOrderByCreatedAtDesc(appName)
//                    .stream()
//                    .map(AppResponse::of)
//                    .collect(Collectors.toList());
//        }

        String orderBy;
        if (asc) {
            orderBy = "app.createdAt ASC";
        } else {
            orderBy = "app.createdAt DESC";
        }

        return appRepository.findByAppNameQuery(appName, orderBy)
                .stream()
                .map(AppResponse::of)
                .collect(Collectors.toList());
    }

    public List<AppResponse> findByAppNameUsingExample(String appName, Boolean asc, int pageSize, int currentPage) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withMatcher("appName", contains().ignoreCase());
        Example<App> filter = Example.of(new App(appName), matcher);
        Sort sort = Sort.by((asc ? Sort.Direction.ASC:Sort.Direction.DESC), "createdAt");
        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);

        return appRepository.findAll(filter, pageable)
                .stream()
                .map(AppResponse::of)
                .collect(Collectors.toList());
    }
}
