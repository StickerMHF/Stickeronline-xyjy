package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SysUserRepository extends JpaRepository<SysUsers, String>, JpaSpecificationExecutor<SysUsers> {

    @Query(value = "select s1 from  SysUsers s1 where s1.username =?1")
    SysUsers getUserByName(String username);

    @Modifying
    @Query("update SysUsers user set user.delFlag=:delFlag where user.id =:id ")
    void updateDelFlag(@Param("delFlag") Integer delFlag, @Param("id") String id);

    @Modifying
    @Query("update SysUsers user set user.status=:status where user.id =:id ")
    void freezeThawUser(@Param("status") Integer status, @Param("id") String id);

    @Modifying
    @Query("update SysUsers user set user.password=:pwd where user.username=:username")
    void mofifyPwd(@Param("pwd") String pwd, @Param("username") String username);

    Page<SysUsers> getAllByIdIn(List<String> userId, Pageable pageable);

    SysUsers queryByUsernameEquals(String userName);
}