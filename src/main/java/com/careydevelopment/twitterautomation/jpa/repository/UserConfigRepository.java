package com.careydevelopment.twitterautomation.jpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.entity.UserConfig;

public interface UserConfigRepository extends BaseRepository<UserConfig,String>{
    
    @Query("SELECT u FROM UserConfig u where u.user = :user")
    UserConfig findByUser(@Param("user") TwitterUser user);
}
