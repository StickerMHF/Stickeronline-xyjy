package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BNewsEntity;
import com.tb.service.cdxyh.repository.BNewsRepository;
import com.tb.service.cdxyh.service.BNewsAsyncService;
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
public class BNewsAsyncServiceImpl implements BNewsAsyncService, BaseAsyncService {
    @Autowired
    private BNewsRepository bNewsRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BNewsEntity bNewsEntity = new BNewsEntity(params);
        bNewsEntity.setCreateTime(new Date());
        bNewsEntity.setUpdateTime(new Date());
        bNewsRepository.save(bNewsEntity);
        future.complete("添加成功!");
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BNewsEntity bNewsEntity = new BNewsEntity();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BNewsEntity> ex = Example.of(bNewsEntity, matcher);
        Page<BNewsEntity> plist = bNewsRepository.findAll(ex,pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BNewsEntity bNewsEntity = new BNewsEntity(params);
        bNewsEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BNewsEntity> sr = bNewsRepository.findById(bNewsEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bNewsEntity.setUpdateTime(new Date());
            bNewsRepository.save(bNewsEntity);
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
            bNewsRepository.deleteByIdEquals(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        BNewsEntity bNewsEntity = new BNewsEntity();
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BNewsEntity> ex = Example.of(bNewsEntity, matcher);
        List<BNewsEntity> newsList = bNewsRepository.findAll(ex);
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
        BNewsEntity bNewsEntity = new BNewsEntity(params);
        Optional<BNewsEntity> res = bNewsRepository.findById(bNewsEntity.getId());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }
}
