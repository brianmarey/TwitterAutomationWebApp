package com.careydevelopment.twitterautomation.jpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.IndexItemInfoRow;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;

public interface IndexItemInfoRepository extends BaseRepository<IndexItemInfoRow,Long>{

    
    @Query("SELECT p FROM IndexItemInfoRow p where p.projectUrl = :projectUrl")
    IndexItemInfoRow findByUrl(@Param("projectUrl") ProjectUrl projectUrl);
}
