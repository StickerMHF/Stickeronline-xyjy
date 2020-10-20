package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @FileName: com.ygj.cyryks.internet.main.stu.repository
 * @Author: Sticker
 * @Date: 2019/7/4
 * @Version: 1.0
 */
@Repository
@Transactional
public interface SysCatalogRepository extends JpaRepository<SysCatalog, String>, JpaSpecificationExecutor<SysCatalog> {

}