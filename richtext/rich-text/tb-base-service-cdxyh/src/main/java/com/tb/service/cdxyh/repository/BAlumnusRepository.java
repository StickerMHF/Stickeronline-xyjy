package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BAlumnusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BAlumnusRepository extends JpaRepository<BAlumnusEntity, String> {

    @Query(value="select a.id, a.thumb, a.name from b_alumnus a, b_alumnus_join b where a.id = b.alumnus_id and b.user_id=?1 limit ?2 offset ?3", nativeQuery = true)
    List<Map<String, Object>> queryByuserId(String userId,Integer pageSize,Integer offset);

    @Query(value="select count(*) as num from (select count(*) from b_photo group by user_id) a", nativeQuery = true)
    Integer countByuserId();
}