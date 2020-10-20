package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysOperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SOperationLogRepository extends JpaRepository<SysOperationLog, Long>, JpaSpecificationExecutor<SysOperationLog> {

    void deleteByIdEquals(String id);

}
