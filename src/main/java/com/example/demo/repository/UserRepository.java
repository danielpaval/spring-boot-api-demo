package com.example.demo.repository;

import com.example.common.repository.CommonRepository;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CommonRepository<Long, User>, RevisionRepository<User, Long, Integer> {

    // Find users by name (case-insensitive)
    List<User> findByNameContainingIgnoreCase(String name);

    // Find user by exact name
    Optional<User> findByName(String name);

    // Check if a user exists with the given name
    boolean existsByName(String name);

    // Custom query to find users ordered by name
    @Query("SELECT u FROM User u ORDER BY u.name ASC")
    List<User> findAllOrderedByName();

}