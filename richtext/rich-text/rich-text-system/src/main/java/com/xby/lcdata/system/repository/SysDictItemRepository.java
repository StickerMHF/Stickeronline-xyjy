package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysDictItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


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
public interface SysDictItemRepository extends JpaRepository<SysDictItem, String>, JpaSpecificationExecutor<SysDictItem> {
    @Modifying
    @Query(value = "update SysDictItem dic set dic.dictId=:dictId,dic.text=:text,dic.value=:values,dic.description=:description," +
            "dic.sortOrder=:sortOrder,dic.status=:status,dic.updateBy=:updateBy,dic.updateTime=:updateTime where dic.id=:id")
    int modifyDictItem(@Param("dictId") String dictId,
                       @Param("text") String text,
                       @Param("values") String values,
                       @Param("description") String description,
                       @Param("sortOrder") Integer sortOrder,
                       @Param("status") Integer status,
                       @Param("updateBy") String updateBy,
                       @Param("updateTime") Date updateTime,
                       @Param("id") String id);

    @Query(value = " select s from SysDictItem s  where s.dictId = (select b.id from SysDict b where b.dictCode = ?1) order by s.sortOrder asc ")
    List<SysDictItem> queryDictItemsByCode(String dictCode);

}