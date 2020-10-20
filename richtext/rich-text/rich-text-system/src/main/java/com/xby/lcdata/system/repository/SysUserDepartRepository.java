package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysUserDepart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @FileName: com.ygj.cyryks.internet.main.stu.repository
 * @Author: Sticker
 * @Date: 2019/7/4
 * @Version: 1.0
 */
@Repository
@Transactional
public interface SysUserDepartRepository extends JpaRepository<SysUserDepart, String>, JpaSpecificationExecutor<SysUserDepart> {

    /**
     * 查询SysDepart集合
     *
     * @param userId
     * @return
     */
    @Query(value = "select s1 from  SysUserDepart s1 where s1.userId =?1")
    List<SysUserDepart> queryUserDeparts(String userId);

    List<SysUserDepart> queryAllByDepIdEquals(String depId);

    void deleteAllByUserIdEquals(String userId);

    void deleteByDepIdEqualsAndUserIdEquals(String depId, String userId);

    void deleteByDepIdEquals(String depId);

}