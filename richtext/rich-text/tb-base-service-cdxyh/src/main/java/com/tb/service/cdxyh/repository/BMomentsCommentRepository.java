package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BMomentsCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BMomentsCommentRepository extends JpaRepository<BMomentsCommentEntity, String> {

}