package com.careydevelopment.twitterautomation.jpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;

public interface TwitterUserRepository extends BaseRepository<TwitterUser,Long>{

    
    @Query("SELECT u FROM TwitterUser u where u.screenName = :screenName")
    TwitterUser findByScreenName(@Param("screenName") String screenName);
}
