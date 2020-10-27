package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BWechatUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BWechatUsersRepository extends JpaRepository<BWechatUsersEntity, String> {
    void deleteByOpenidEquals (String id);
}
