package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BWechatUsersEntity;
import com.tb.service.cdxyh.repository.BWechatUsersRepository;
import com.tb.service.cdxyh.service.BWechatUsersAsyncService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@AsyncServiceHandler
public class BWechatUsersAsyncServiceImpl implements BWechatUsersAsyncService, BaseAsyncService {
    @Autowired
    private BWechatUsersRepository bWechatUsersRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BWechatUsersEntity bWechatUsersEntity = new BWechatUsersEntity(params);
        bWechatUsersEntity.setCreateTime(new Date());
        bWechatUsersEntity.setUpdateTime(new Date());
        bWechatUsersRepository.save(bWechatUsersEntity);
        future.complete("添加成功!");
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);

        BWechatUsersEntity bWechatUsersEntity = new BWechatUsersEntity(params);
        String sorts=params.getString("sort","createTime");
        Sort sort = new Sort(Sort.Direction.DESC, sorts);
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        if (params.getString("type")!=null) {
            bWechatUsersEntity.setType(params.getString("type"));
            matcher.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        //创建实例
        Example<BWechatUsersEntity> ex = Example.of(bWechatUsersEntity, matcher);
        Page<BWechatUsersEntity> plist = bWechatUsersRepository.findAll(ex,pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BWechatUsersEntity bWechatUsersEntity = new BWechatUsersEntity(params);
        bWechatUsersEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BWechatUsersEntity> sr = bWechatUsersRepository.findById(bWechatUsersEntity.getOpenid());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bWechatUsersEntity.setUpdateTime(new Date());
            bWechatUsersRepository.save(bWechatUsersEntity);
            //TODO 返回false说明什么？
            future.complete("修改成功!");
        }
        handler.handle(future);
    }

    @Override
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String[] ids = params.getString("id").split(",");
        for (int i = 0; i < ids.length; i++) {
            bWechatUsersRepository.deleteByOpenidEquals(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }


    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BWechatUsersEntity bWechatUsersEntity = new BWechatUsersEntity(params);
        Optional<BWechatUsersEntity> res = bWechatUsersRepository.findById(bWechatUsersEntity.getOpenid());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.complete(null);
        }
        handler.handle(future);
    }
}
