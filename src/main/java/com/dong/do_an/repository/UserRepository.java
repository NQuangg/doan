package com.dong.do_an.repository;

import com.dong.do_an.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SystemUser, String> {
}
