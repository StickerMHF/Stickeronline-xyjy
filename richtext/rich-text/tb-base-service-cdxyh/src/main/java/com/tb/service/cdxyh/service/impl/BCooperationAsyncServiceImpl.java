package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BCooperationEntity;
import com.tb.service.cdxyh.repository.BCooperationRepository;
import com.tb.service.cdxyh.service.BCooperationAsyncService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@AsyncServiceHandler
public class BCooperationAsyncServiceImpl implements BCooperationAsyncService, BaseAsyncService {
    @Autowired
    private BCooperationRepository bCooperationRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BCooperationEntity bCooperationEntity = new BCooperationEntity(params);
        bCooperationEntity.setCreateTime(new Date());
        bCooperationEntity.setUpdateTime(new Date());
        bCooperationRepository.save(bCooperationEntity);
        future.complete("添加成功!");
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BCooperationEntity bCooperationEntity = new BCooperationEntity(params);
        String sorts=params.getString("sort","createTime");
        Sort sort = new Sort(Sort.Direction.DESC, sorts);
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BCooperationEntity> ex = Example.of(bCooperationEntity, matcher);
        Page<BCooperationEntity> plist = bCooperationRepository.findAll(ex,pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BCooperationEntity bCooperationEntity = new BCooperationEntity(params);
        bCooperationEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BCooperationEntity> sr = bCooperationRepository.findById(bCooperationEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bCooperationEntity.setUpdateTime(new Date());
            bCooperationRepository.save(bCooperationEntity);
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
            bCooperationRepository.deleteByIdEquals(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BCooperationEntity bCooperationEntity = new BCooperationEntity(params);
        Optional<BCooperationEntity> res = bCooperationRepository.findById(bCooperationEntity.getId());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }
}
