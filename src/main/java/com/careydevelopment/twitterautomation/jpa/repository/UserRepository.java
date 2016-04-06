package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careydevelopment.twitterautomation.jpa.entity.User;

public interface UserRepository extends BaseRepository<User,String>{

    void delete(User deleted);
    
    @Query("SELECT u FROM User u")
    List<User> findAll();
 
    User findById(String id);
 
    User save(User persisted);
    
    @Query("SELECT u FROM User u where u.id = :username")
    User findByUsername(@Param("username") String username);
}
