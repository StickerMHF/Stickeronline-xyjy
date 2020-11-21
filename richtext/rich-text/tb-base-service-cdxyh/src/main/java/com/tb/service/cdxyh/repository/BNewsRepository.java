package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BNewsRepository extends JpaRepository<BNewsEntity, String> {
    @Query(value = "SELECT count(*) FROM b_collect where record_id=?1 and user_id =?2", nativeQuery = true)
    Integer countMyCollectById(String recordId,String userId);
}