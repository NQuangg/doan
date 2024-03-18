package com.dong.do_an.repository;

import com.dong.do_an.entity.SemesterPoint;
import com.dong.do_an.entity.SemesterPointId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<SemesterPoint, SemesterPointId> {
}
