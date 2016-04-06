package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.careydevelopment.twitterautomation.jpa.entity.Followee;

public interface FolloweeRepository extends BaseRepository<Followee,Long>{

    @Query("SELECT f FROM Followee f")
    List<Followee> findAll();
}
