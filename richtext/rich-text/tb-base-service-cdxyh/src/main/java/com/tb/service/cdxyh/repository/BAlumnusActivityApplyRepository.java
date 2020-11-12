package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BAlumnusActivityApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BAlumnusActivityApplyRepository extends JpaRepository<BAlumnusActivityApplyEntity, String> {
    List<BAlumnusActivityApplyEntity> findAllByActivityIdAndUserId(@Param("activityId") String activityId,@Param("userId") String userId);
}