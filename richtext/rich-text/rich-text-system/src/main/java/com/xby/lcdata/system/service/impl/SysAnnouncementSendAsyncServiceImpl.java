package com.xby.lcdata.system.service.impl;


import com.xby.lcdata.system.repository.SysAnnouncementRepository;
import com.xby.lcdata.system.repository.SysAnnouncementSendRepository;
import com.xby.lcdata.system.service.SysAnnouncementSendAsyncService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.JwtUtil;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.vo.PageVo;
import com.xby.lcdata.system.core.annotation.LogAnnotation;
import com.xby.lcdata.system.entity.SysAnnouncementResult;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

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
public class SysAnnouncementSendAsyncServiceImpl implements SysAnnouncementSendAsyncService, BaseAsyncService {

    @Autowired
    private SysAnnouncementRepository sysAnnouncementRepository;
    @Autowired
    private SysAnnouncementSendRepository sysAnnouncementSendRepository;

    /**
     * 我的消息列表（分页查询）
     *
     * @param params
     * @param handler
     */
    @Override
    public void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String userId = JwtUtil.getUserInfo(params.getString("token", "")).getString("userid");
        String sender = params.getString("sender", "");
        String title = params.getString("title", "");
        PageVo pageVo = new PageVo(params);
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        Page<SysAnnouncementResult> sysAnnouncementResults = sysAnnouncementSendRepository.getAllByUserId(userId, "%" + sender+ "%", "%" + title + "%", pageable);
        future.complete(new JsonObject(Json.encode(sysAnnouncementResults)));
        handler.handle(future);
    }

    /**
     * 根据id查询
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        String id = params.getString("id");
        Future<JsonObject> future = Future.future();
        JsonObject result = new JsonObject();
        if (id != null && id != "") {
            future.complete(result);
            result.put("data", sysAnnouncementRepository.findById(id).get());
        }
        future.complete(result);
        handler.handle(future);
    }

    /**
     * 已读
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "设置已读")
    public void read(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String userName = JwtUtil.getUserNameByToken(params.getString("token"));
        String id = params.getString("id");
        sysAnnouncementSendRepository.read(id, userName, TimeUtil.getDateNow());
        future.complete("已设置为已读!");
        handler.handle(future);
    }

    /**
     * 一键已读
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "一键设置已读")
    public void readAll(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        JsonObject userInfo = JwtUtil.getUserInfo(params.getString("token", ""));
        sysAnnouncementSendRepository.readAll(userInfo.getString("userid"), userInfo.getString("username"), TimeUtil.getDateNow());
        future.complete("已全部设置已读!");
        handler.handle(future);
    }
}
