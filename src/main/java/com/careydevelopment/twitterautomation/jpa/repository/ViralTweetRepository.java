package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.ViralTweet;

public interface ViralTweetRepository extends BaseRepository<ViralTweet,Long>{
    
	@Query("SELECT t FROM ViralTweet t where t.tweetId = :tweetId")
	ViralTweet findByTweetId(@Param("tweetId") long tweetId);
    
	@Query("SELECT t FROM ViralTweet t where t.dateSeen > :date and t.category = :category order by t.retweets desc")
    Page<ViralTweet> findLatestByCategory(@Param("date") Date date, @Param("category") String category, Pageable page);
}
