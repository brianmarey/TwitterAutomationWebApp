package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.AnchorTextData;
import com.careydevelopment.twitterautomation.jpa.entity.BacklinkData;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;

public interface AnchorTextDataRepository extends BaseRepository<AnchorTextData,Long>{

	@Query("SELECT b FROM AnchorTextData b where b.projectUrl = :projectUrl")
    List<AnchorTextData> findByUrl(@Param("projectUrl") ProjectUrl projectUrl);

	@Query("SELECT b FROM AnchorTextData b where b.projectUrl = :projectUrl and b.anchorText = :anchorText")
    AnchorTextData findSpecific(@Param("projectUrl") ProjectUrl projectUrl, @Param("anchorText") String anchorText);
	
	@Query("SELECT b FROM AnchorTextData b order by id asc")
    Page<AnchorTextData> findLatest(Pageable page);
	
	List<AnchorTextData> findTop20ByProjectUrlOrderByIdAsc(ProjectUrl projectUrl);
}
