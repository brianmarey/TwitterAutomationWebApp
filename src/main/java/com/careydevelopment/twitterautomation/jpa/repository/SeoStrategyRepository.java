package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.SeoStrategy;

public interface SeoStrategyRepository extends BaseRepository<SeoStrategy,Long>{

    @Query("SELECT p FROM SeoStrategy p where p.projectUrl = :projectUrl and p.strategyStatus='Open'")
    List<SeoStrategy> findOpenByProjectUrl(@Param("projectUrl") ProjectUrl projectUrl);
}
