package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysRoleDataRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface SysRoleDataRuleRepository extends JpaRepository<SysRoleDataRule, String>,
        JpaSpecificationExecutor<SysRoleDataRule> {

    void deleteByIdEquals(String roleId);

}
