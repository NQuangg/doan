package com.dong.do_an.repository;

import com.dong.do_an.entity.Role;
import com.dong.do_an.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<SystemUser, String> {

    @Query("SELECT e FROM SystemUser e WHERE e.role = :role")
    List<SystemUser> getAllUser(@Param("role") Role role);
}
