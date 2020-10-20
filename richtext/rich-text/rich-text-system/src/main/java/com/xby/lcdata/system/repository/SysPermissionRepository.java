package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @FileName: com.ygj.cyryks.internet.main.stu.repository
 * @Author: Sticker
 * @Date: 2019/7/5
 * @Version: 1.0
 */
@Repository
@Transactional
public interface SysPermissionRepository extends JpaRepository<SysPermission, String>,
        JpaSpecificationExecutor<SysPermission> {

    /**
     * 查询SysDepart集合
     *
     * @param username
     * @return
     */
    @Query(value = "SELECT p FROM  SysPermission p WHERE exists( select a.id from SysRolePermission a join SysRoles b" +
            " on a.roleId = b.id join SysUsersRoles c on c.roleId = b.id join SysUsers d on d.id = c.userId  where p" +
            ".id = a.permissionId AND d.username = ?1 ) and p.delFlag = 0 order by p.sortNo ASC")
    List<SysPermission> queryByUserOrderBySortNo(String username);

    List<SysPermission> queryAllByDelFlagEqualsOrderBySortNo(Integer delflag);

    /**
     * 根据菜单类型查询权限列表
     *
     * @param menuType
     * @return
     */
    @Query(value = "SELECT p FROM  SysPermission p WHERE  p.delFlag = 0 and p.menuType=?1  order by p.sortNo ASC")
    List<SysPermission> queryByMenuType(int menuType);

    List<SysPermission> queryAllBy();

    @Modifying
    @Query(value = "update SysPermission  as s set s.delFlag=1 where s.id=:id")
    void deleteByIdEquals(@Param("id") String id);

    @Modifying
    @Query(value = "update SysPermission  as s set s.delFlag=1 where s.parentId=:parentId")
    void deleteByParentIdEquals(@Param("parentId") String parentId);

    SysPermission queryByIdEquals(String id);

    List<SysPermission> getAllByIdInOrderBySortNo(List<String> ids);

    @Query(value = "select sp from SysPermission  sp where sp.delFlag=0 and sp.id in (select s.permissionId from SysRolePermission s where s.roleId=:roleId)")
    List<SysPermission> getAllByRole(@Param("roleId") String roleId);

}