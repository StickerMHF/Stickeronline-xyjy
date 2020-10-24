package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BMemberAttentionEntity;
import com.tb.service.cdxyh.entity.BMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BMemberAttentionRepository extends JpaRepository<BMemberAttentionEntity, String> {
List<BMemberAttentionEntity> findAllByUserIdAndMemberId(@Param("userId")String userId, @Param("memberId")String memberId);
}