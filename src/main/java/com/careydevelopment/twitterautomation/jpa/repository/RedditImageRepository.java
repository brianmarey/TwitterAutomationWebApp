package com.careydevelopment.twitterautomation.jpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.RedditImage;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;

public interface RedditImageRepository extends BaseRepository<RedditImage,Long>{

    
    @Query("SELECT r FROM RedditImage r where r.imageUrl = :imageUrl")
    RedditImage findByUrl(@Param("imageUrl") String imageUrl);
    
    
}
