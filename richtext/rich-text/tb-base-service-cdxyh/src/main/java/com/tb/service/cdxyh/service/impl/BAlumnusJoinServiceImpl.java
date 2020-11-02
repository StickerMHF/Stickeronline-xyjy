package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.tb.service.cdxyh.entity.BAlumnusJoinEntity;
import com.tb.service.cdxyh.repository.BAlumnusJoinRepository;
import com.tb.service.cdxyh.service.BAlumnusJoinService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AsyncServiceHandler
public class BAlumnusJoinServiceImpl implements BAlumnusJoinService, BaseAsyncService {
    @Autowired
    private BAlumnusJoinRepository bAlumnusJoinRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);
        bAlumnusJoinEntity.setCreateTime(new Date());
        bAlumnusJoinEntity.setCreateBy(bAlumnusJoinEntity.getUserName());
        BAlumnusJoinEntity save = bAlumnusJoinRepository.save(bAlumnusJoinEntity);
        future.complete(new JsonObject(Json.encode(save)));
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {

    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {

    }

    @Override
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);
        String alumnusId = bAlumnusJoinEntity.getAlumnusId();
        String userId = bAlumnusJoinEntity.getUserId();
        bAlumnusJoinRepository.deleteByAlumnusIdAndUserId(alumnusId,userId);
//        bAlumnusJoinRepository.delete(bAlumnusJoinEntity);
//        String[] ids = params.getString("id").split(",");
//        for (int i = 0; i < ids.length; i++) {
//            bAlumnusJoinRepository.deleteByIdEquals(ids[i]);
//        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
//        Future<JsonArray> future = Future.future();
//        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
//        BAlumnusJoinEntity bAlumnusEntity = new BAlumnusJoinEntity();
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
//        //创建实例
//        Example<BAlumnusJoinEntity> ex = Example.of(bAlumnusEntity, matcher);
//        List<BAlumnusJoinEntity> newsList = bAlumnusJoinRepository.findAll(ex);
//        if (newsList == null || newsList.size() <= 0) {
//            future.complete(new JsonArray());
//        } else {
//            future.complete(new JsonArray(Json.encode(newsList)));
//        }
//        handler.handle(future);
    }
}
