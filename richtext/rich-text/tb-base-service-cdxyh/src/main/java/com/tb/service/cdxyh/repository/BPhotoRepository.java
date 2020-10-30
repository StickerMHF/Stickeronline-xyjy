package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BPhotoRepository extends JpaRepository<BPhotoEntity, String> {


    @Query(value="select user_id,user_name,user_photo, count(*) from b_photo group by user_id,user_name,user_photo order by user_name desc limit ?1 offset ?2", nativeQuery=true)
    List<Map<String, Object>> groupByUserId(Integer limit, Integer offset);

    Integer countByUserId(String userId);
}