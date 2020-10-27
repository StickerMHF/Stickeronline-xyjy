package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BMomentsEntity;
import com.tb.service.cdxyh.entity.BMomentsLikeEntity;
import com.tb.service.cdxyh.repository.BMomentsLikeRepository;
import com.tb.service.cdxyh.repository.BMomentsRepository;
import com.tb.service.cdxyh.service.BMomentsLikeService;
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
public class BMomentsLikeServiceImpl implements BMomentsLikeService, BaseAsyncService {
    @Autowired
    private BMomentsLikeRepository bMomentsLikeRepository;
    @Autowired
    private BMomentsRepository bMomentsRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {

        Future<JsonObject> future = Future.future();
        BMomentsLikeEntity bMomentsLikeEntity = new BMomentsLikeEntity(params);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("momentId", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("userId",ExampleMatcher.GenericPropertyMatchers.contains());
        Example<BMomentsLikeEntity> ex = Example.of(bMomentsLikeEntity,matcher);

        List<BMomentsLikeEntity> like = bMomentsLikeRepository.findAllByUserIdAndMomentId(bMomentsLikeEntity.getUserId(),bMomentsLikeEntity.getMomentId());
        BMomentsLikeEntity save;
        BMomentsEntity bMomentsEntity = bMomentsRepository.findById(params.getString("momentId")).get();
        Integer likeCount = bMomentsEntity.getLikeCount();
        String status = params.getString("status");
        if(status.equals("like")){
            likeCount += 1;
        } else {
            likeCount -= 1;
        }
        bMomentsEntity.setLikeCount(likeCount);
        bMomentsRepository.save(bMomentsEntity);
        if (like.size()>0){
            BMomentsLikeEntity updatedDta = like.get(0);
            updatedDta.setStatus(status);
            //更新数据
            save = bMomentsLikeRepository.save(updatedDta);
        } else {
            bMomentsLikeEntity.setCreateTime(new Date());
            bMomentsLikeEntity.setCreateBy(params.getString("userName"));
            save = bMomentsLikeRepository.save(bMomentsLikeEntity);
        }
        future.complete(new JsonObject(Json.encode(save)));
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String type = params.getString("type");
        BMomentsLikeEntity bMomentsLikeEntity = new BMomentsLikeEntity();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
//        if (oConvertUtils.isNotEmpty(type)) {
//            bAlummunsMemberEntity.setType(type);
//            exampleMatcher.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
//        }
        //创建实例
        Example<BMomentsLikeEntity> ex = Example.of(bMomentsLikeEntity);

        Page<BMomentsLikeEntity> plist = bMomentsLikeRepository.findAll(ex,pageable);
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
        BMomentsLikeEntity bMomentsLikeEntity = new BMomentsLikeEntity();
        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BMomentsLikeEntity> ex = Example.of(bMomentsLikeEntity, matcher);
        List<BMomentsLikeEntity> newsList = bMomentsLikeRepository.findAll(ex);
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
        BMomentsLikeEntity bMomentsLikeEntity = new BMomentsLikeEntity(params);
        Optional<BMomentsLikeEntity> res = bMomentsLikeRepository.findById(bMomentsLikeEntity.getId());
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
        BMomentsLikeEntity bMomentsLikeEntity = new BMomentsLikeEntity();
        matcher.withMatcher("moments_id", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BMomentsLikeEntity> ex = Example.of(bMomentsLikeEntity, matcher);
        List<BMomentsLikeEntity> list = bMomentsLikeRepository.findAll(ex);
        if (list == null || list.size() <= 0) {
            future.complete(new JsonArray(Json.encode(list)));
        } else {
            future.complete(new JsonArray());
        }
        handler.handle(future);
    }
}
