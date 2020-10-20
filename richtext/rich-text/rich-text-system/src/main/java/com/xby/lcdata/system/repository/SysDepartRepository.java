package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysDepart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @FileName: com.ygj.cyryks.internet.main.stu.repository
 * @Author: Sticker
 * @Date: 2019/7/4
 * @Version: 1.0
 */
@Repository
@Transactional
public interface SysDepartRepository extends JpaRepository<SysDepart, String>, JpaSpecificationExecutor<SysDepart> {

    /**
     * 查询SysDepart集合
     *
     * @param userId
     * @return
     */
    @Query(value = "select a from SysDepart a join SysUserDepart b on a.id = b.depId where b.userId = ?1")
    List<SysDepart> queryUserDeparts(String userId);

    @Modifying
    @Query(value = "update SysDepart  as s set s.delFlag=1 where s.id=:id")
    void deleteByIdEquals(@Param("id") String id);

    List<SysDepart> getAllByIdIn(List<String> departId);

    @Modifying
    @Query(value = "update SysDepart dep set dep.departName=:departName,dep.departOrder=:departOrder,dep" +
            ".description=:description," +
            "dep.mobile=:mobile,dep.memo=:memo,dep.address=:address," +
            "dep.updateBy=:updateBy,dep.updateTime=:updateTime where dep.id=:id")
    int modifyDepart(@Param("departName") String departName,
                     @Param("departOrder") Integer departOrder,
                     @Param("description") String description,
                     @Param("mobile") String mobile,
                     @Param("memo") String memo,
                     @Param("address") String address,
                     @Param("updateBy") String updateBy,
                     @Param("updateTime") Date updateTime,
                     @Param("id") String id);
}