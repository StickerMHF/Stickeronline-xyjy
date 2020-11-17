package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BMomentsEntity;
import com.tb.service.cdxyh.entity.BWechatUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BMomentsRepository extends JpaRepository<BMomentsEntity, String> {
    @Query(value = "SELECT * FROM b_moments where user_id in (select member_id from b_wechat_users_attention where user_id=?1) and status=1 order by create_time desc limit ?2 offset ?3", nativeQuery = true)
    List<BMomentsEntity> queryUserId(String userId, Integer pageSize, Integer offset);

    @Query(value = "select * from b_moments where user_id in (select user_id from b_alumnus_join where alumnus_id=?1) and status=1 order by create_time desc limit ?2 offset ?3", nativeQuery = true)
    List<BMomentsEntity> queryByAlumnusId(String alumnusId, Integer pageSize, Integer offset);
}