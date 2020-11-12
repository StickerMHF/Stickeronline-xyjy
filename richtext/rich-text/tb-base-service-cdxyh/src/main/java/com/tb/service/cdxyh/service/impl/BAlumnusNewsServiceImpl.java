package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BAlumnusEntity;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@AsyncServiceHandler
public class BAlumnusNewsServiceImpl implements BAlumnusNewsService, BaseAsyncService {
    @Autowired
    private BAlumnusNewsRepository bAlumnusNewsRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BAlumnusNewsEntity bAlumnusEntity = new BAlumnusNewsEntity(params);
        bAlumnusEntity.setCreateTime(new Date());
        bAlumnusEntity.setUpdateTime(new Date());
        bAlumnusNewsRepository.save(bAlumnusEntity);
        future.complete("添加成功!");
        handler.handle(future);
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
        }
        Example<BAlumnusNewsEntity> ex = Example.of(bAlumnusNewsEntity, exampleMatcher);

        Page<BAlumnusNewsEntity> plist = bAlumnusNewsRepository.findAll(ex,pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BAlumnusNewsEntity bAlumnusEntity = new BAlumnusNewsEntity(params);
        bAlumnusEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BAlumnusNewsEntity> sr = bAlumnusNewsRepository.findById(bAlumnusEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bAlumnusEntity.setUpdateTime(new Date());
            bAlumnusNewsRepository.save(bAlumnusEntity);
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
            bAlumnusNewsRepository.deleteById(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
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
