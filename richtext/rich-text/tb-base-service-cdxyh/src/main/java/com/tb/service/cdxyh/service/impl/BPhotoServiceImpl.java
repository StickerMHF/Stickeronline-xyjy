package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BPhotoEntity;
import com.tb.service.cdxyh.repository.BPhotoRepository;
import com.tb.service.cdxyh.service.BPhotoService;
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
public class BPhotoServiceImpl implements BPhotoService, BaseAsyncService {
    @Autowired
    private BPhotoRepository bPhotoRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BPhotoEntity bPhotoEntity = new BPhotoEntity(params);
        bPhotoEntity.setCreateTime(new Date());
        bPhotoEntity.setCreateBy(bPhotoEntity.getUserName());
        BPhotoEntity save = bPhotoRepository.save(bPhotoEntity);
        if (oConvertUtils.isNotEmpty(save)){
            future.complete(new JsonObject(Json.encode(save)));
        } else {
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String userId = params.getString("userId");
        BPhotoEntity bPhotoEntity = new BPhotoEntity();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        if (oConvertUtils.isNotEmpty(userId)) {
            bPhotoEntity.setUserId(userId);
            exampleMatcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
            //创建实例
            Example<BPhotoEntity> ex = Example.of(bPhotoEntity, exampleMatcher);

            Page<BPhotoEntity> plist = bPhotoRepository.findAll(ex,pageable);
            future.complete(new JsonObject(Json.encode(plist)));
        } else {
            future.complete(new JsonObject().put("msg","请传入有效参数！"));
        }
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
        BPhotoEntity bPhotoEntity = new BPhotoEntity();
        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BPhotoEntity> ex = Example.of(bPhotoEntity, matcher);
        List<BPhotoEntity> newsList = bPhotoRepository.findAll(ex);
        if (newsList == null || newsList.size() <= 0) {
            future.complete(new JsonArray());
        } else {
            future.complete(new JsonArray(Json.encode(newsList)));
        }
        handler.handle(future);
    }

    @Override
    public void getList(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {

    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BPhotoEntity bPhotoEntity = new BPhotoEntity(params);
        Optional<BPhotoEntity> res = bPhotoRepository.findById(bPhotoEntity.getId());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }



}
