package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;


/**
 * @FileName: com.ygj.cyryks.internet.main.sys.repository
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/2
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Repository
@Transactional
public interface SysDictRepository extends JpaRepository<SysDict, String>, JpaSpecificationExecutor<SysDict> {

    @Query(value = " select s.text from SysDictItem s  where s.dictId = (select id from SysDict where dictCode = ?1) and s.value = ?2 ")
    String queryDictTextByKey(String dictCode, String key);

    @Modifying
    @Query(value = "update SysDict dic set dic.dictName=:dictName,dic.dictCode=:dictCode,dic.description=:description," +
            "dic.updateBy=:updateBy,dic.updateTime=:updateTime where dic.id=:id")
    int modifyDict(@Param("dictName") String dictName,
                   @Param("dictCode") String dictCode,
                   @Param("description") String description,
                   @Param("updateBy") String updateBy,
                   @Param("updateTime") Date updateTime,
                   @Param("id") String id);
}