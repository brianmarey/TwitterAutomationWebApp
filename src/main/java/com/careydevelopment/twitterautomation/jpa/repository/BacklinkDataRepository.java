package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.BacklinkData;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;

public interface BacklinkDataRepository extends BaseRepository<BacklinkData,Long>{

	@Query("SELECT b FROM BacklinkData b where b.projectUrl = :projectUrl")
    List<BacklinkData> findByUrl(@Param("projectUrl") ProjectUrl projectUrl);
	
	@Query("SELECT b FROM BacklinkData b where b.projectUrl = :projectUrl and b.sourceUrl = :sourceUrl")
    BacklinkData findSpecific(@Param("projectUrl") ProjectUrl projectUrl, @Param("sourceUrl") String sourceUrl);
    
	@Query("SELECT b FROM BacklinkData b order by id asc")
    Page<BacklinkData> findLatest(Pageable page);
	
	List<BacklinkData> findTop20ByProjectUrlOrderByIdAsc(ProjectUrl projectUrl);
}
