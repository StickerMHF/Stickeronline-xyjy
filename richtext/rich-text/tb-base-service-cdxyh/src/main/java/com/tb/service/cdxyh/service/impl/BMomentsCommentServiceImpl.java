package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BMomentsCommentEntity;
import com.tb.service.cdxyh.repository.BMomentsCommentRepository;
import com.tb.service.cdxyh.service.BMomentsCommentService;
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
public class BMomentsCommentServiceImpl implements BMomentsCommentService, BaseAsyncService {
    @Autowired
    private BMomentsCommentRepository bMomentsCommentRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future future = Future.future();
        BMomentsCommentEntity bMomentsCommentEntity = new BMomentsCommentEntity(params);
        bMomentsCommentEntity.setCreateTime(new Date());
        BMomentsCommentEntity save = bMomentsCommentRepository.save(bMomentsCommentEntity);
        future.complete(new JsonObject(Json.encode(save)));
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String type = params.getString("type");
        System.out.println(type);
        BMomentsCommentEntity bMomentsCommentEntity = new BMomentsCommentEntity();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
//        if (oConvertUtils.isNotEmpty(type)) {
//            bAlummunsMemberEntity.setType(type);
//            exampleMatcher.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
//        }
        //创建实例
        Example<BMomentsCommentEntity> ex = Example.of(bMomentsCommentEntity);

        Page<BMomentsCommentEntity> plist = bMomentsCommentRepository.findAll(ex,pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {

    }

    @Override
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {

    }

    @Override
    public void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        BMomentsCommentEntity bMomentsCommentEntity = new BMomentsCommentEntity();
        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BMomentsCommentEntity> ex = Example.of(bMomentsCommentEntity, matcher);
        List<BMomentsCommentEntity> newsList = bMomentsCommentRepository.findAll(ex);
        if (newsList == null || newsList.size() <= 0) {
            future.complete(new JsonArray());
        } else {
            future.complete(new JsonArray(Json.encode(newsList)));
        }
        handler.handle(future);
    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler){
        Future<JsonObject> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching();
        BMomentsCommentEntity bMomentsCommentEntity = new BMomentsCommentEntity(params);
        Optional<BMomentsCommentEntity> res = bMomentsCommentRepository.findById(bMomentsCommentEntity.getId());
        if (res.isPresent()){
            future.complete(new JsonObject(Json.encode(res.get())));
        } else {
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }

    public void queryByMomentId(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        BMomentsCommentEntity bMomentsCommentEntity = new BMomentsCommentEntity();
        matcher.withMatcher("moments_id", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BMomentsCommentEntity> ex = Example.of(bMomentsCommentEntity, matcher);
        List<BMomentsCommentEntity> list = bMomentsCommentRepository.findAll(ex);
        if (list == null || list.size() <= 0) {
            future.complete(new JsonArray(Json.encode(list)));
        } else {
            future.complete(new JsonArray());
        }
        handler.handle(future);
    }
}
