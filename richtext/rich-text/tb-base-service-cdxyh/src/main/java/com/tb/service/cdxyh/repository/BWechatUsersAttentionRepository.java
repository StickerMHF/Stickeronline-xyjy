package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BWechatUsersAttentionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BWechatUsersAttentionRepository extends JpaRepository<BWechatUsersAttentionEntity, String> {
List<BWechatUsersAttentionEntity> findAllByUserIdAndMemberId(@Param("userId")String userId, @Param("memberId")String memberId);
}