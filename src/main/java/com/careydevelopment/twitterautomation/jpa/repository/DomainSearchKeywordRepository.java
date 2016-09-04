package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.BacklinkData;
import com.careydevelopment.twitterautomation.jpa.entity.DomainSearchKeyword;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;

public interface DomainSearchKeywordRepository extends BaseRepository<DomainSearchKeyword,Long>{

	@Query("SELECT b FROM DomainSearchKeyword b where b.projectUrl = :projectUrl")
    List<DomainSearchKeyword> findByUrl(@Param("projectUrl") ProjectUrl projectUrl);
    
	@Query("SELECT b FROM DomainSearchKeyword b order by id asc")
    Page<DomainSearchKeyword> findLatest(Pageable page);
	
	@Query("SELECT b FROM DomainSearchKeyword b where b.projectUrl = :projectUrl and b.type = :type order by id asc")
    List<DomainSearchKeyword> findLatestByType(@Param("projectUrl") ProjectUrl projectUrl, @Param("type") String type, Pageable page);

	@Query("SELECT b FROM DomainSearchKeyword b where b.projectUrl = :projectUrl and b.type = :type order by id asc")
    List<DomainSearchKeyword> findLatestByType(@Param("projectUrl") ProjectUrl projectUrl, @Param("type") String type);

	//List<DomainSearchKeyword> findTop10ByProjectUrlOrderByIdAsc(ProjectUrl projectUrl);
}
