package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.DomainAd;

public interface DomainAdRepository extends BaseRepository<DomainAd,Long>{

	@Query("SELECT da FROM DomainAd da where da.lastSeen > :date order by da.lastSeen desc")
    Page<DomainAd> findNativeAds(Pageable page, @Param("date") Date date);
	
	@Query("SELECT da FROM DomainAd da where da.lastSeen > :date order by da.nativeAd.daysSeen desc")
    Page<DomainAd> findHottestNativeAds(Pageable page, @Param("date") Date date);
}
