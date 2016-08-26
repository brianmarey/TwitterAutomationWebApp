package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.Project;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;

public interface ProjectUrlRepository extends BaseRepository<ProjectUrl,Long>{

    
    @Query("SELECT p FROM ProjectUrl p where p.project = :project")
    List<ProjectUrl> findByProject(@Param("project") Project project);
}
