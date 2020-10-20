package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysPermissionBtn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SPermissionBtnRepository extends JpaRepository<SysPermissionBtn, String> {

    void deleteByIdEquals(String id);

    void deleteByPermissionIdEquals(String permissionId);

    List<SysPermissionBtn> getAllByPermissionIdEquals(String permissionId);

}
