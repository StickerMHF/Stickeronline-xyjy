package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BAlumnusPhotoEntity;
import com.tb.service.cdxyh.repository.BAlumnusPhotoRepository;
import com.tb.service.cdxyh.service.BAlumnusPhotoService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@AsyncServiceHandler
public class BAlumnusPhotoServiceImpl implements BAlumnusPhotoService, BaseAsyncService {
    @Autowired
    private BAlumnusPhotoRepository bAlumnusPhotoRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {

    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String type = params.getString("type");
        BAlumnusPhotoEntity bAlumnusPhotoEntity = new BAlumnusPhotoEntity();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
//        if (oConvertUtils.isNotEmpty(type)) {
//            bAlumnusPhotoEntity.setType(type);
//            exampleMatcher.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
//        }
        //创建实例
        Example<BAlumnusPhotoEntity> ex = Example.of(bAlumnusPhotoEntity, exampleMatcher);

        Page<BAlumnusPhotoEntity> plist = bAlumnusPhotoRepository.findAll(ex,pageable);
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
        BAlumnusPhotoEntity bAlumnusPhotoEntity = new BAlumnusPhotoEntity();
        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BAlumnusPhotoEntity> ex = Example.of(bAlumnusPhotoEntity, matcher);
        List<BAlumnusPhotoEntity> newsList = bAlumnusPhotoRepository.findAll(ex);
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
        BAlumnusPhotoEntity bAlumnusPhotoEntity = new BAlumnusPhotoEntity(params);
        Optional<BAlumnusPhotoEntity> res = bAlumnusPhotoRepository.findById(bAlumnusPhotoEntity.getId());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }



}
