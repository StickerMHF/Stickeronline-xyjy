package com.xby.lcdata.sso.repository;

import com.xby.lcdata.sso.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SsoSysUserRepository extends JpaRepository<SysUser, String> {

    SysUser getSUserByUsernameEquals(String username);

}
