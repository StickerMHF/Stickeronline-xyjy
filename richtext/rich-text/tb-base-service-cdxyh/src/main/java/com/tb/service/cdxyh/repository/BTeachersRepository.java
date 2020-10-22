package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BTeachersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BTeachersRepository extends JpaRepository<BTeachersEntity, String> {
void deleteByIdEquals(String id);
}