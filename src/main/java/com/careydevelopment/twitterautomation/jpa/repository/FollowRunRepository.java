package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.careydevelopment.twitterautomation.jpa.entity.FollowRun;

public interface FollowRunRepository extends BaseRepository<FollowRun,Long>{

    @Query("SELECT f FROM FollowRun f")
    List<FollowRun> findAll();
}
