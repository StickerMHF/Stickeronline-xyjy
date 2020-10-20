package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysPermissionDataRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.repository
 * @Author: Sticker
 * @Date: 2019/7/12
 * @Version: 1.0
 */
@Repository
@Transactional
public interface SysPermissionDataRuleRepository extends JpaRepository<SysPermissionDataRule, String>, JpaSpecificationExecutor<SysPermissionDataRule> {

    @Modifying
    @Query(value = "update SysPermissionDataRule set permissionId=:permissionId,ruleName=:ruleName,ruleColumn=:ruleColumn," +
            "ruleConditions=:ruleConditions,ruleValue=:ruleValue,status=:status,updateBy=:updateBy,updateTime=:updateTime where id=:id")
    void modifyDataRule(@Param("permissionId") String permissionId,
                        @Param("ruleName") String ruleName,
                        @Param("ruleColumn") String ruleColumn,
                        @Param("ruleConditions") String ruleConditions,
                        @Param("ruleValue") String ruleValue,
                        @Param("status") String status,
                        @Param("id") String id,
                        @Param("updateBy") String updateBy,
                        @Param("updateTime") Date updateTime);

    void deleteByIdEquals(String id);

}