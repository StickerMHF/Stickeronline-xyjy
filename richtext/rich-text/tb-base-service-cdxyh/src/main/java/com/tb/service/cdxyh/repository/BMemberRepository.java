package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BMemberRepository extends JpaRepository<BMemberEntity, String> {
void deleteByIdEquals(String id);
}