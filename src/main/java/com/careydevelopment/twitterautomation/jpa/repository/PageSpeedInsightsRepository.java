package com.careydevelopment.twitterautomation.jpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.PageSpeedInsights;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.entity.UserConfig;

public interface PageSpeedInsightsRepository extends BaseRepository<PageSpeedInsights,Long>{
    
    //@Query("SELECT p FROM PageSpeedInsights p where u.user = :user")
    //UserConfig findByUser(@Param("user") TwitterUser user);
}
