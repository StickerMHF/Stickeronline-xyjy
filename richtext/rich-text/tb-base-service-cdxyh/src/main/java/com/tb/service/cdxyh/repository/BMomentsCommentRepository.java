package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BMomentsCommentEntity;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BMomentsCommentRepository extends JpaRepository<BMomentsCommentEntity, String> {

    @Modifying
    @Query(value = "select a from BMomentsCommentEntity a where a.momentsId = :commentId")
    List<BMomentsCommentEntity> queryByCommentId(@Param("commentId") String commentId);
}