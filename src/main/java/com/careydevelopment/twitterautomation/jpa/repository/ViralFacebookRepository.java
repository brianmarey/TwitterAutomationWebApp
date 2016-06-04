package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.ViralFacebook;
import com.careydevelopment.twitterautomation.jpa.entity.ViralTweet;

public interface ViralFacebookRepository extends BaseRepository<ViralFacebook,Long>{
    
	@Query("SELECT f FROM ViralFacebook f where f.facebookId = :facebookId")
	ViralFacebook findByFacebookId(@Param("facebookId") String facebookId);
    
	@Query("SELECT f FROM ViralFacebook f where f.dateSeen > :date and f.category = :category order by f.shares desc")
    Page<ViralFacebook> findLatestByCategory(@Param("date") Date date, @Param("category") String category, Pageable page);
}
