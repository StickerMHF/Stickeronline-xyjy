package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BMessageEntity;
import com.tb.service.cdxyh.entity.BMomentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BMessageRepository extends JpaRepository<BMessageEntity, String> {
    @Query(value = "SELECT * FROM b_message where user_id =?1 order by create_time desc limit ?2 offset ?3", nativeQuery = true)
    List<BMessageEntity> queryUserId(String userId, Integer pageSize, Integer offset);


}