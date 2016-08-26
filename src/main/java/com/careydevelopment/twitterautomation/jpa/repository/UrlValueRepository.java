package com.careydevelopment.twitterautomation.jpa.repository;

import com.careydevelopment.twitterautomation.jpa.entity.UrlValue;

public interface UrlValueRepository extends BaseRepository<UrlValue,Long>{
    
    //@Query("SELECT p FROM PageSpeedInsights p where u.user = :user")
    //UserConfig findByUser(@Param("user") TwitterUser user);
}
