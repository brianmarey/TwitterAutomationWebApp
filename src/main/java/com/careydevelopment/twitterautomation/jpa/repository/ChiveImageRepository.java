package com.careydevelopment.twitterautomation.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.ChiveImage;

public interface ChiveImageRepository extends BaseRepository<ChiveImage,Long>{

    
	@Query("SELECT c FROM ChiveImage c where c.url = :url")
    ChiveImage findByUrl(@Param("url") String url);
    
	@Query("SELECT c FROM ChiveImage c order by id desc")
    Page<ChiveImage> findLatest(Pageable page);
}
