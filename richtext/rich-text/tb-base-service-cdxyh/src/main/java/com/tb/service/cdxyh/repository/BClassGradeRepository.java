package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BClassGradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BClassGradeRepository extends JpaRepository<BClassGradeEntity, String> {

}