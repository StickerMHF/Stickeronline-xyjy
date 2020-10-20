package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysRolePermission;
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
public interface SysRolePermissionRepository extends JpaRepository<SysRolePermission, String>,
        JpaSpecificationExecutor<SysRolePermission> {

    void deleteByRoleIdEquals(String roleId);

    void deleteByPermissionIdEquals(String permissionId);

    List<SysRolePermission> getAllByRoleIdIn(List<String> roleIds);

}
