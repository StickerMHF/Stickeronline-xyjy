package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BMomentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BMomentsRepository extends JpaRepository<BMomentsEntity, String> {

}