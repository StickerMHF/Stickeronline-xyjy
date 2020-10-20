package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.repository.SysAnnouncementRepository;
import com.xby.lcdata.system.repository.SysAnnouncementSendRepository;
import com.xby.lcdata.system.repository.SysUserRepository;
import com.xby.lcdata.system.service.SysAnnouncementAsyncService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.CommonConstant;
import com.sticker.online.core.utils.JwtUtil;
import com.sticker.online.core.utils.TimeUtil;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.xby.lcdata.system.core.annotation.LogAnnotation;
import com.xby.lcdata.system.entity.SysAnnouncement;
import com.xby.lcdata.system.entity.SysAnnouncementSend;
import com.xby.lcdata.system.entity.SysUsers;
import com.xby.lcdata.system.utils.CommonSendStatus;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.service.impl
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/5
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Component
@AsyncServiceHandler
public class SysAnnouncementAsyncServiceImpl implements SysAnnouncementAsyncService, BaseAsyncService {
    @Autowired
    private SysAnnouncementRepository sysAnnouncementRepository;
    @Autowired
    private SysAnnouncementSendRepository sysAnnouncementSendRepository;
    @Autowired
    private SysUserRepository sysUserRepository;

    /**
     * 获取系统消息列表
     *
     * @param params
     * @param handler
     */
    @Override
    public void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        SysAnnouncement sysAnnouncement = new SysAnnouncement(params);
        sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // 构建对象
        matcher.withMatcher("delFlag", ExampleMatcher.GenericPropertyMatchers.contains());
        String title = params.getString("title");
        if (oConvertUtils.isNotEmpty(title)) {
            sysAnnouncement.setTitile(title);
            matcher.withMatcher("title", ExampleMatcher.GenericPropertyMatchers.startsWith());
        }
        // 创建实例
        Example<SysAnnouncement> ex = Example.of(sysAnnouncement, matcher);
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        Page<SysAnnouncement> sysDicts = sysAnnouncementRepository.findAll(ex, pageable);
        future.complete(new JsonObject(Json.encode(sysDicts)));
        handler.handle(future);
    }

    /**
     * 新增
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "新增消息、公告")
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        try {
            String userName = JwtUtil.getUserNameByToken(params.getString("token"));
            SysAnnouncement sysAnnouncement = new SysAnnouncement(params);
            sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
            sysAnnouncement.setSendStatus(CommonSendStatus.UNPUBLISHED_STATUS_0);// 未发布
            sysAnnouncement.setCreateBy(userName);
            sysAnnouncement.setCreateTime(TimeUtil.getDateNow());
            sysAnnouncement.setStartTime(TimeUtil.convertStringToDate(params.getString("startTime")));
            sysAnnouncement.setEndTime(TimeUtil.convertStringToDate(params.getString("endTime")));
            sysAnnouncement.setUserIds(params.getString("userIds"));
            // JsonArray userIds = params.getJsonArray("userIds");
            // String userStr = "";
            // for (int i = 0; i < userIds.size(); i++) {
            // userStr += userIds.getString(i) + ",";
            // }
            // sysAnnouncement.setUserIds(userStr);
            sysAnnouncementRepository.save(sysAnnouncement);
            future.complete("新增成功!");
        } catch (Exception e) {
            future.fail("新增失败!" + e.getMessage());
        }
        // sysAnnouncement.setUserIds(userStr);
        // sysAnnouncementRepository.save(sysAnnouncement);
        // future.complete("新增成功!");
        handler.handle(future);
    }

    /**
     * 编辑
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "编辑消息、公告")
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String userName = JwtUtil.getUserNameByToken(params.getString("token"));
        SysAnnouncement sysAnnouncement = new SysAnnouncement(params);
        Optional<SysAnnouncement> sysAnnouncementEntity = sysAnnouncementRepository.findById(sysAnnouncement.getId());
        if (sysAnnouncementEntity.equals(null)) {
            future.fail("未找到对应实体");
        } else {
            sysAnnouncementRepository.modifyAnnouncement(params.getString("titile"), params.getString("msgContent"),
                    TimeUtil.convertStringToDate(params.getString("startTime")),
                    TimeUtil.convertStringToDate(params.getString("endTime")), params.getString("priority"),
                    params.getString("msgCategory"), params.getString("msgType"), params.getString("userIds"), userName,
                    TimeUtil.getDateNow(), params.getString("id"));
            future.complete("修改成功!");
        }
        handler.handle(future);
    }

    /**
     * 删除
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "删除消息、公告")
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        try {
            String id = params.getString("id", "");
            Optional<SysAnnouncement> sysAnnouncement = sysAnnouncementRepository.findById(id);
            sysAnnouncement.get().setDelFlag(CommonConstant.DEL_FLAG_1.toString());
            sysAnnouncementRepository.save(sysAnnouncement.get());
            sysAnnouncementSendRepository.deleteByAnntIdEquals(sysAnnouncement.get().getId());
            future.complete("删除成功!");
        } catch (Exception e) {
            future.fail("删除失败!" + e.getMessage());
        }
        handler.handle(future);
    }

    /**
     * 批量删除
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "批量删除消息、公告")
    public void deleteBatch(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String ids = params.getString("ids", "");
        String[] id = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            SysAnnouncement sysAnnouncement = sysAnnouncementRepository.findById(id[i]).get();
            sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_1.toString());
            sysAnnouncementRepository.save(sysAnnouncement);
            sysAnnouncementSendRepository.deleteByAnntIdEquals(sysAnnouncement.getId());
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    /**
     * 通过id查询
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String id = params.getString("id", "");
        Optional<SysAnnouncement> sysAnnouncement = sysAnnouncementRepository.findById(id);
        if (sysAnnouncement.equals(null)) {
            future.fail("未找到对应实体");
        } else {
            future.complete(new JsonObject(Json.encode(sysAnnouncement.get())));
        }
        handler.handle(future);
    }

    /**
     * 更新发布操作
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "发布消息、公告")
    public void doReleaseData(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String id = params.getString("id", "");
        Optional<SysAnnouncement> sysAnnouncement = sysAnnouncementRepository.findById(id);
        if (sysAnnouncement.equals(null)) {
            future.fail("未找到对应实体");
        } else {
            sysAnnouncement.get().setSendStatus(CommonSendStatus.PUBLISHED_STATUS_1);// 发布中
            sysAnnouncement.get().setSendTime(new Date());
            String currentUserName = JwtUtil.getUserNameByToken(params.getString("token", ""));
            sysAnnouncement.get().setSender(currentUserName);
            sysAnnouncementRepository.save(sysAnnouncement.get());
            if (sysAnnouncement.get().getMsgType().equals("ALL")) {
                List<SysUsers> usersList = sysUserRepository.findAll();
                for (int i = 0; i < usersList.size(); i++) {
                    SysAnnouncementSend sysAnnouncementSend = new SysAnnouncementSend();
                    sysAnnouncementSend.setAnntId(sysAnnouncement.get().getId());
                    sysAnnouncementSend.setReadFlag("0");
                    sysAnnouncementSend.setUserId(usersList.get(i).getId());
                    sysAnnouncementSend.setCreateBy(currentUserName);
                    sysAnnouncementSend.setCreateTime(TimeUtil.getDateNow());
                    sysAnnouncementSend.setUpdateTime(TimeUtil.getDateNow());
                    sysAnnouncementSendRepository.save(sysAnnouncementSend);
                }
            } else {
                String[] userId = sysAnnouncement.get().getUserIds().split(",");
                for (int i = 0; i < userId.length; i++) {
                    SysAnnouncementSend sysAnnouncementSend = new SysAnnouncementSend();
                    sysAnnouncementSend.setAnntId(sysAnnouncement.get().getId());
                    sysAnnouncementSend.setReadFlag("0");
                    sysAnnouncementSend.setUserId(userId[i]);
                    sysAnnouncementSend.setCreateBy(currentUserName);
                    sysAnnouncementSend.setCreateTime(TimeUtil.getDateNow());
                    sysAnnouncementSend.setUpdateTime(TimeUtil.getDateNow());
                    sysAnnouncementSendRepository.save(sysAnnouncementSend);
                }
            }
            future.complete("已发布!");
        }
        handler.handle(future);
    }

    /**
     * 更新撤销操作
     *
     * @param handler
     * @@param params
     */
    @Override
    @LogAnnotation(operationType = "撤销消息、公告")
    public void doReovkeData(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String id = params.getString("id", "");
        Optional<SysAnnouncement> sysAnnouncemento = sysAnnouncementRepository.findById(id);
        if (sysAnnouncemento.equals(null)) {
            future.fail("未找到对应实体");
        } else {
            SysAnnouncement sysAnnouncement = sysAnnouncemento.get();
            sysAnnouncement.setSendStatus(CommonSendStatus.REVOKE_STATUS_2);// 撤销发布
            sysAnnouncement.setCancelTime(new Date());
            sysAnnouncementRepository.save(sysAnnouncement);
            sysAnnouncementSendRepository.deleteByAnntIdEquals(id);
            future.complete("已撤销!");
        }
        handler.handle(future);
    }

    /**
     * 补充用户数据，并返回系统消息
     *
     * @param params
     * @param handler
     */
    @Override
    public void listByUser(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String token = params.getString("token", "");
        JsonObject userinfo = JwtUtil.getUserInfo(token);
        String userId = userinfo.getString("userid", "");
        // 1.将系统消息补充到用户通告阅读标记表中
        Collection<String> anntIds = sysAnnouncementSendRepository.queryByUserId(userinfo.getString("userid"));
        SysAnnouncement sysAnnouncement = new SysAnnouncement(params);
        sysAnnouncement.setMsgType(CommonConstant.MSG_TYPE_ALL); // 全部人员
        sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_0.toString());// 未删除
        sysAnnouncement.setSendStatus(CommonConstant.HAS_SEND);// 已发布
        sysAnnouncement.setId(anntIds.toString());
        ExampleMatcher matcher = ExampleMatcher.matching(); // 构建对象
        matcher.withMatcher("msgType", ExampleMatcher.GenericPropertyMatchers.contains());
        matcher.withMatcher("delFlag", ExampleMatcher.GenericPropertyMatchers.contains());
        matcher.withMatcher("sendStatus", ExampleMatcher.GenericPropertyMatchers.contains());
        matcher.withMatcher("id", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        // 创建实例
        Example<SysAnnouncement> ex = Example.of(sysAnnouncement, matcher);
        if (anntIds.isEmpty()) {
            anntIds.add("");
        }
        List<SysAnnouncement> announcements = sysAnnouncementRepository.findByIdsIsNotIn(anntIds);// .findAll(ex);
        if (announcements.size() > 0) {
            for (int i = 0; i < announcements.size(); i++) {
                SysAnnouncementSend announcementSend = new SysAnnouncementSend();
                announcementSend.setAnntId(announcements.get(i).getId());
                announcementSend.setUserId(userinfo.getString("userid"));
                announcementSend.setReadFlag(CommonConstant.NO_READ_FLAG);
                sysAnnouncementSendRepository.save(announcementSend);
            }
        }
        // 2.查询用户未读的系统消息
        Pageable pageable = new PageRequest(0, 5); // 页码：前端从1开始，jpa从0开始，做个转换
        Page<SysAnnouncement> anntMsgList = sysAnnouncementRepository.querySysCementListByUserId(userId, "1", pageable);// 通知公告消息
        Page<SysAnnouncement> sysMsgList = sysAnnouncementRepository.querySysCementListByUserId(userId, "2", pageable);// 系统消息
        JsonObject sysMsgMap = new JsonObject();
        sysMsgMap.put("sysMsgList", new JsonArray(Json.encode(sysMsgList.getContent())));
        sysMsgMap.put("sysMsgTotal", sysMsgList.getTotalElements());
        sysMsgMap.put("anntMsgList", new JsonArray(Json.encode(anntMsgList.getContent())));
        sysMsgMap.put("anntMsgTotal", anntMsgList.getTotalElements());
        future.complete(sysMsgMap);
        handler.handle(future);
    }

}
