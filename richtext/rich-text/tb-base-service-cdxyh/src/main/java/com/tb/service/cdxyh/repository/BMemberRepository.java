package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BMemberRepository extends JpaRepository<BMemberEntity, String> {
void deleteByIdEquals(String id);
}