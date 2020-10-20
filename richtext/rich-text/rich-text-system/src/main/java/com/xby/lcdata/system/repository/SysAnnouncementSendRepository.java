package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SysAnnouncementResult;
import com.xby.lcdata.system.entity.SysAnnouncementSend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * @Date: 2019/8/5
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Repository
@Transactional
public interface SysAnnouncementSendRepository extends JpaRepository<SysAnnouncementSend, String>,
        JpaSpecificationExecutor<SysAnnouncementSend> {

    @Query(value = "select a.anntId from SysAnnouncementSend a where a.userId = ?1")
    List<String> queryByUserId(String userid);


    @Modifying
    @Query(value = "update SysAnnouncementSend msgSend set msgSend.readFlag=1,msgSend.updateBy=:updateBy,msgSend" +
            ".updateTime=:updateTime where " +
            "msgSend.id=:id")
    int read(@Param("id") String id, @Param("updateBy") String updateBy, @Param("updateTime") Date updateTime);

    @Modifying
    @Query(value = "update SysAnnouncementSend msgSend set msgSend.readFlag=1,msgSend.updateBy=:updateBy,msgSend" +
            ".updateTime=:updateTime where " +
            "msgSend.userId=:userId")
    int readAll(@Param("userId") String userId, @Param("updateBy") String updateBy,
                @Param("updateTime") Date updateTime);

    @Query(value = "select msgSend from SysAnnouncementSend  msgSend where msgSend.userId=:userId")
    List<SysAnnouncementSend> getSendByUser(@Param("userId") String userId);

    void deleteByAnntIdEquals(String anntId);

    @Query(value = "select new com.xby.lcdata.system.entity.SysAnnouncementResult(s,send.readFlag,send.id) from SysAnnouncementSend send left join SysAnnouncement s on send.anntId=s.id " +
            "where send.userId=:userId and s.sender like :sender and s.titile like :title")
    Page<SysAnnouncementResult> getAllByUserId(@Param("userId") String userId,
                                               @Param("sender") String sender,
                                               @Param("title") String title, Pageable pageable);
}