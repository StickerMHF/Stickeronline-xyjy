package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysAnnouncement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.repository
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/5
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Repository
@Transactional
public interface SysAnnouncementRepository extends JpaRepository<SysAnnouncement, String>,
        JpaSpecificationExecutor<SysAnnouncement> {

    @Query(value = "select new com.xby.lcdata.system.entity.SysAnnouncement(a,b.id) from SysAnnouncement a ,SysAnnouncementSend b where a.id = b.anntId and a.sendStatus " +
            "='1' and a.delFlag = '0' and b.userId = ?1 and a.msgCategory = ?2 and b.readFlag='0' ")
    Page<SysAnnouncement> querySysCementListByUserId(String userId, String msgCategory, Pageable pageable);

    @Query(value = "select a from SysAnnouncement a  where a.msgType = 'ALL' and a.delFlag = '0' and a.id not in ?1 " +
            "and a.sendStatus = '1'  order by a.updateTime desc ")
    List<SysAnnouncement> findByIdsIsNotIn(Collection<String> anntIds);

    @Modifying
    @Query(value = "update SysAnnouncement msg set msg.titile=:titile,msg.msgContent=:msgContent,msg.startTime=:startTime,msg.endTime=:endTime," +
            "msg.priority=:priority,msg.msgCategory=:msgCategory,msg.msgType=:msgType,msg.userIds=:userIds,msg.updateBy=:updateBy,msg.updateTime=:updateTime" +
            " where msg.id=:id")
    int modifyAnnouncement(@Param("titile") String titile,
                           @Param("msgContent") String msgContent,
                           @Param("startTime") Date startTime,
                           @Param("endTime") Date endTime,
                           @Param("priority") String priority,
                           @Param("msgCategory") String msgCategory,
                           @Param("msgType") String msgType,
                           @Param("userIds") String userIds,
                           @Param("updateBy") String updateBy,
                           @Param("updateTime") Date updateTime,
                           @Param("id") String id);

}