package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.repository.SAccessoryRepository;
import com.xby.lcdata.system.service.SysAccessoryAsyncService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.JwtUtil;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.vo.PageVo;
import com.xby.lcdata.system.core.annotation.LogAnnotation;
import com.xby.lcdata.system.entity.SysAccessory;
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
 * @Date: 2019/7/31
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Component
@AsyncServiceHandler
public class SysAccessoryAsyncServiceImpl implements SysAccessoryAsyncService, BaseAsyncService {
    @Autowired
    private SAccessoryRepository sysAccessoryRepository;

    /**
     * 获取附件列表
     *
     * @param params
     * @param handler
     */
    public void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize());
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        SysAccessory sysAccessory = new SysAccessory(params);
        Example<SysAccessory> ex = Example.of(sysAccessory, matcher);
        Page<SysAccessory> plist = sysAccessoryRepository.findAll(ex, pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    @LogAnnotation(operationType = "新增附件")
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        SysAccessory sysAccessory = new SysAccessory(params);
        sysAccessory.setCreateTime(TimeUtil.getNow());
        sysAccessory.setCreateBy(JwtUtil.getUserNameByToken(params.getString("token")));
        SysAccessory sysAccessoryAdd = sysAccessoryRepository.save(sysAccessory);
        future.complete(sysAccessoryAdd.toJson());
        handler.handle(future);
    }

    @Override
    @LogAnnotation(operationType = "编辑附件")
    public void edit(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        SysAccessory sysAccessory = new SysAccessory(params);
        SysAccessory sysAccessoryEdit = sysAccessoryRepository.save(sysAccessory);
        future.complete(sysAccessoryEdit.toJson());
        handler.handle(future);
    }

    @Override
    @LogAnnotation(operationType = "删除附件")
    public void delete(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String id = params.getString("id");
        sysAccessoryRepository.deleteById(id);
        future.complete();
        handler.handle(future);
    }

    @Override
    @LogAnnotation(operationType = "批量删除附件")
    public void deleteBatch(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String id = params.getString("ids");
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            sysAccessoryRepository.deleteById(ids[i]);
        }
        future.complete(new JsonObject().put("data", id).put("msg", "删除成功!"));
        handler.handle(future);
    }

}
