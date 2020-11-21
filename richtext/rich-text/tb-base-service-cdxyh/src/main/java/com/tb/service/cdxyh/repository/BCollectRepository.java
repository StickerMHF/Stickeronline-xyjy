package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BCollectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BCollectRepository extends JpaRepository<BCollectEntity, String> {
    @Query(value = "SELECT * FROM b_collect where user_id =?1 order by create_time desc limit ?2 offset ?3", nativeQuery = true)
    List<BCollectEntity> queryUserId(String userId, Integer pageSize, Integer offset);
    @Query(value = "SELECT count(*) FROM b_collect where user_id =?1", nativeQuery = true)
    Integer countUserId(String userId);

    List<BCollectEntity> findAllByUserIdAndRecordId(@Param("userId") String userId,@Param("recordId") String recordId);
}