package com.careydevelopment.twitterautomation.jpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.DomainRank;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;

public interface DomainRankRepository extends BaseRepository<DomainRank,Long>{

	@Query("SELECT b FROM DomainRank b where b.projectUrl = :projectUrl")
    DomainRank findByUrl(@Param("projectUrl") ProjectUrl projectUrl);

}
