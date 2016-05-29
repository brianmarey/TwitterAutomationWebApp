package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.ChiveImage;
import com.careydevelopment.twitterautomation.jpa.entity.ViralContent;

public interface ViralContentRepository extends BaseRepository<ViralContent,Long>{

    
	@Query("SELECT c FROM ViralContent c where c.url = :url")
	ViralContent findByUrl(@Param("url") String url);
    
	@Query("SELECT c FROM ViralContent c where c.dateSeen > :date order by c.shareCount desc")
    Page<ViralContent> findLatest(@Param("date") Date date, Pageable page);
}
