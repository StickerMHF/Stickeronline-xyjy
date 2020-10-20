package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysRoleBtn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SRoleBtnRepository extends JpaRepository<SysRoleBtn, String> {

//    void deleteByRoleIdEqualsAndPermissionIdEquals(@Param("roleId") String roleId,@Param("permissionId") String permissionId);
    void deleteByRoleIdEqualsAndPermissionIdEquals(String roleId,String permissionId);

    List<SysRoleBtn> getAllByRoleIdEquals(String roleId);

}
