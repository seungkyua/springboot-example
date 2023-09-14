package com.ask.example.repository;

import com.ask.example.domain.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {

    List<App> findByAppName(String appName);

    List<App> findByAppNameLikeIgnoreCaseOrderByCreatedAt(String appName);

    List<App> findByAppNameLikeIgnoreCaseOrderByCreatedAtDesc(String appName);

    @Query("SELECT app FROM App as app WHERE app.appName like :appName order by :orderBy")
    List<App> findByAppNameQuery(
            @Param("appName") String appName,
            @Param("orderBy") String orderBy);
}
