package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.Project;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;

public interface ProjectRepository extends BaseRepository<Project,Long>{

    
    @Query("SELECT p FROM Project p where p.owner = :owner")
    List<Project> findByOwner(@Param("owner") TwitterUser owner);
}
