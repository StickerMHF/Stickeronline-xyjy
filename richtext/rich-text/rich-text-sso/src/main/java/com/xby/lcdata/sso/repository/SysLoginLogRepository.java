package com.xby.lcdata.sso.repository;

import com.xby.lcdata.sso.entity.SysLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: SysLoginLogRepository
 * @Description:
 * @Author: wangf
 * @Date: 2019/11/19 0019 10:12
 * @Version: 1.0
 **/
@Repository
public interface SysLoginLogRepository extends JpaRepository<SysLoginLog, String> {
}
