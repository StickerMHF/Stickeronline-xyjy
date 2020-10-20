package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SLoginLogRepository extends JpaRepository<SysLoginLog, Long>, JpaSpecificationExecutor<SysLoginLog> {

    void deleteByIdEquals(String id);

}
