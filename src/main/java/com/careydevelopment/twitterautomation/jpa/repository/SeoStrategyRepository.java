package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.SeoStrategy;

public interface SeoStrategyRepository extends BaseRepository<SeoStrategy,Long>{

    @Query("SELECT p FROM SeoStrategy p where p.projectUrl = :projectUrl and p.strategyStatus='Open'")
    List<SeoStrategy> findOpenByProjectUrl(@Param("projectUrl") ProjectUrl projectUrl);
    
    @Query("SELECT p FROM SeoStrategy p where p.projectUrl.project.owner.id = :id order by p.name")
    List<SeoStrategy> findByOwner(@Param("id") Long id);
    
    @Query("SELECT p FROM SeoStrategy p where p.projectUrl.project.owner.id = :id and p.strategySuccess = :success order by p.name")
    List<SeoStrategy> findByOwnerAndSuccess(@Param("id") Long id,@Param("success") String success);
    
    @Query("SELECT p FROM SeoStrategy p where p.projectUrl.project.owner.id = :id and p.strategySuccess = :success and p.strategyStatus = :status order by p.name")
    List<SeoStrategy> findByOwnerAndSuccessAndStatus(@Param("id") Long id,@Param("success") String success,@Param("status") String status);

    @Query("SELECT p FROM SeoStrategy p where p.projectUrl.project.owner.id = :id and p.strategyStatus = :status order by p.name")
    List<SeoStrategy> findByOwnerAndStatus(@Param("id") Long id,@Param("status") String status);
    
}
