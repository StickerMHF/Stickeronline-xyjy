package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BNewsRepository extends JpaRepository<BNewsEntity, String> {

}