package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysUsersRoles;
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
public interface SysUsersRolesRepository extends JpaRepository<SysUsersRoles, String>, JpaSpecificationExecutor<SysUsersRoles> {

    void deleteAllByUserIdEquals(String userId);

    void deleteByUserIdEqualsAndRoleIdEquals(String userId, String roleId);

    void deleteByRoleIdEquals(String roleId);

    List<SysUsersRoles> getAllByUserIdEquals(String userId);

    List<SysUsersRoles> getAllByRoleIdEquals(String userId);
}
