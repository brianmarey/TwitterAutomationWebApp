package com.careydevelopment.twitterautomation.jpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.PageSpeedInsights;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;

public interface PageSpeedInsightsRepository extends BaseRepository<PageSpeedInsights,Long>{
    
    @Query("SELECT p FROM PageSpeedInsights p where p.projectUrl = :projectUrl")
    PageSpeedInsights findByProjectUrl(@Param("projectUrl") ProjectUrl projectUrl);
}
