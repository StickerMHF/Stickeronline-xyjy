package com.xby.lcdata.sso.repository;

import com.xby.lcdata.sso.entity.SysRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.repository
 * @Description:
 * @Author: Sticker
 * @Date: 2019/7/31
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Repository
@Transactional
public interface SysRolesRepository extends JpaRepository<SysRoles, String>, JpaSpecificationExecutor<SysRoles> {

    List<SysRoles> findAllByIdIn(List<String> roleIds);

}