package com.xby.lcdata.sso.repository;

import com.xby.lcdata.sso.entity.SysUsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SysUsersRolesRepository extends JpaRepository<SysUsersRoles, String>,
        JpaSpecificationExecutor<SysUsersRoles> {

    List<SysUsersRoles> findAllByUserIdEquals(String userId);


}
