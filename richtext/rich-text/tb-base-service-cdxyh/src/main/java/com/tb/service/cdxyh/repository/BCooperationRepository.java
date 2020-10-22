package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BCooperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BCooperationRepository extends JpaRepository<BCooperationEntity, String> {
void deleteByIdEquals(String id);
}