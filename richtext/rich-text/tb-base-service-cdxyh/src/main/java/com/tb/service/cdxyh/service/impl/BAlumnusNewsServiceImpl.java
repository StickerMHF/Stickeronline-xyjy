package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BAlumnusNewsEntity;
import com.tb.service.cdxyh.repository.BAlumnusNewsRepository;
import com.tb.service.cdxyh.service.BAlumnusNewsService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AsyncServiceHandler
public class BAlumnusNewsServiceImpl implements BAlumnusNewsService, BaseAsyncService {
    @Autowired
    private BAlumnusNewsRepository bAlumnusNewsRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {

    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String fid = params.getString("fid");
        BAlumnusNewsEntity bAlumnusNewsEntity = new BAlumnusNewsEntity();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        if (oConvertUtils.isNotEmpty(fid)) {
            bAlumnusNewsEntity.setFid(fid);
            exampleMatcher.withMatcher("fid", ExampleMatcher.GenericPropertyMatchers.contains());
            //创建实例
            Example<BAlumnusNewsEntity> ex = Example.of(bAlumnusNewsEntity, exampleMatcher);

            Page<BAlumnusNewsEntity> plist = bAlumnusNewsRepository.findAll(ex,pageable);
            future.complete(new JsonObject(Json.encode(plist)));
        } else {
            future.complete(new JsonObject("请输入有效fid"));
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
        BAlumnusNewsEntity bAlumnusEntity = new BAlumnusNewsEntity();
        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BAlumnusNewsEntity> ex = Example.of(bAlumnusEntity, matcher);
        List<BAlumnusNewsEntity> newsList = bAlumnusNewsRepository.findAll(ex);
        if (newsList == null || newsList.size() <= 0) {
            future.complete(new JsonArray());
        } else {
            future.complete(new JsonArray(Json.encode(newsList)));
        }
        handler.handle(future);
    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BAlumnusNewsEntity bAlumnusNewsEntity = new BAlumnusNewsEntity(params);
        Optional<BAlumnusNewsEntity> res = bAlumnusNewsRepository.findById(bAlumnusNewsEntity.getId());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }
}
