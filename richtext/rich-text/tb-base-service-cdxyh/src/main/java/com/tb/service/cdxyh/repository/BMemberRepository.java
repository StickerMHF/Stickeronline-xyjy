package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BMemberRepository extends JpaRepository<BMemberEntity, String> {
void deleteByIdEquals(String id);
@Query(value = "SELECT a.*,b.attention FROM b_member a left JOIN (SELECT member_id,count(*) as attention from b_member_attention where user_id=?1 GROUP BY member_id) b ON a.id=b.member_id limit ?2 offset ?3", nativeQuery = true)
List<Map<String,Object>> findAllListByUserId(String userId,Integer pageSize,Integer offset);
}