package com.tb.service.cdxyh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.AsyncServiceUtil;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BMomentsCommentEntity;
import com.tb.service.cdxyh.entity.BMomentsEntity;
import com.tb.service.cdxyh.repository.BMomentsCommentRepository;
import com.tb.service.cdxyh.repository.BMomentsRepository;
import com.tb.service.cdxyh.service.BMomentsCommentService;
import com.tb.service.cdxyh.service.BMomentsService;
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
public class BMomentsServiceImpl implements BMomentsService, BaseAsyncService {
    @Autowired
    private BMomentsRepository bMomentsRepository;

    @Autowired
    private BMomentsCommentRepository bMomentsCommentRepository;

    @Override
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BMomentsEntity bMomentsEntity = new BMomentsEntity(params);
        bMomentsEntity.setCreateTime(new Date());
        BMomentsEntity save = bMomentsRepository.save(bMomentsEntity);
        future.complete(new JsonObject(Json.encode(save)));
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BMomentsEntity bMomentsEntity = new BMomentsEntity();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        //创建实例
        Example<BMomentsEntity> ex = Example.of(bMomentsEntity);
        Page<BMomentsEntity> plist = bMomentsRepository.findAll(ex,pageable);
        JsonObject resObj = new JsonObject(Json.encode(plist));
        JsonArray content = resObj.getJsonArray("content");
        for (int i = 0; i < content.size(); i++) {
            String commentId = content.getJsonObject(i).getString("id");
            List<BMomentsCommentEntity> commentList = bMomentsCommentRepository.queryByCommentId(commentId);
            resObj.getJsonArray("content").getJsonObject(i).put("commentList", new JsonArray(Json.encode(commentList)));
        }
        future.complete(resObj);
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
//        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        BMomentsEntity bMomentsEntity = new BMomentsEntity(params);
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BMomentsEntity> ex = Example.of(bMomentsEntity);
        List<BMomentsEntity> newsList = bMomentsRepository.findAll(ex);
        JsonArray resList = new JsonArray(Json.encode(newsList));
       for (int i = 0; i < resList.size(); i++) {
            String commentId = resList.getJsonObject(i).getString("id");
            List<BMomentsCommentEntity> commentList = bMomentsCommentRepository.queryByCommentId(commentId);
            System.out.println(i);
           resList.getJsonObject(i).put("commentList", commentList);
        }
       future.complete(resList);
        handler.handle(future);
    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler){
        Future<JsonObject> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching();
        BMomentsEntity bAlummunsMemberEntity = new BMomentsEntity(params);
        Optional<BMomentsEntity> res = bMomentsRepository.findById(bAlummunsMemberEntity.getId());
        if (res.isPresent()){
            future.complete(new JsonObject(Json.encode(res.get())));
        } else {
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }
}
