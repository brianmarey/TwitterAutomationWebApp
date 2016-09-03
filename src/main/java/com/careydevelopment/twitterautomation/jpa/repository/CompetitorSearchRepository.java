package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.BacklinkData;
import com.careydevelopment.twitterautomation.jpa.entity.CompetitorSearch;
import com.careydevelopment.twitterautomation.jpa.entity.DomainSearchKeyword;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;

public interface CompetitorSearchRepository extends BaseRepository<CompetitorSearch,Long>{

	@Query("SELECT c FROM CompetitorSearch c where c.projectUrl = :projectUrl")
    List<CompetitorSearch> findByUrl(@Param("projectUrl") ProjectUrl projectUrl);
    
	@Query("SELECT b FROM CompetitorSearch b order by id asc")
    Page<CompetitorSearch> findLatest(Pageable page);
	
	@Query("SELECT b FROM CompetitorSearch b where b.projectUrl = :projectUrl and b.type = :type order by id asc")
    List<CompetitorSearch> findLatestByType(@Param("projectUrl") ProjectUrl projectUrl, @Param("type") String type, Pageable page);
	
	List<CompetitorSearch> findTop10ByProjectUrlOrderByIdAsc(ProjectUrl projectUrl);
}
