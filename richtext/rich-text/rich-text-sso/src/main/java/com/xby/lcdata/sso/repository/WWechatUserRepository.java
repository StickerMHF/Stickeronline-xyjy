package com.xby.lcdata.sso.repository;


import com.xby.lcdata.sso.entity.WWechatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface WWechatUserRepository extends JpaRepository<WWechatUser, String> {

    WWechatUser findByOpenId(String openId);
}
