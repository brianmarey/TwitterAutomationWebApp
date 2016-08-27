package com.careydevelopment.twitterautomation.jpa.repository;

import com.careydevelopment.twitterautomation.jpa.entity.IndexItemInfoRow;

public interface IndexItemInfoRepository extends BaseRepository<IndexItemInfoRow,Long>{

    
    //@Query("SELECT p FROM ProjectUrl p where p.project = :project")
    //List<ProjectUrl> findByProject(@Param("project") Project project);
}
