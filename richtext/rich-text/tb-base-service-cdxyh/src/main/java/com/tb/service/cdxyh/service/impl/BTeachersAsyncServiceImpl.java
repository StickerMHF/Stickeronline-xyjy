package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BTeachersEntity;
import com.tb.service.cdxyh.repository.BTeachersRepository;
import com.tb.service.cdxyh.service.BTeachersAsyncService;
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
public class BTeachersAsyncServiceImpl implements BTeachersAsyncService, BaseAsyncService {
    @Autowired
    private BTeachersRepository bTeachersRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BTeachersEntity bTeachersEntity = new BTeachersEntity(params);
        bTeachersEntity.setCreateTime(new Date());
        bTeachersEntity.setUpdateTime(new Date());
        bTeachersRepository.save(bTeachersEntity);
        future.complete("添加成功!");
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BTeachersEntity bTeachersEntity = new BTeachersEntity(params);
        String sorts=params.getString("sort","createTime");
        Sort sort = new Sort(Sort.Direction.DESC, sorts);
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BTeachersEntity> ex = Example.of(bTeachersEntity, matcher);
        Page<BTeachersEntity> plist = bTeachersRepository.findAll(ex,pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BTeachersEntity bTeachersEntity = new BTeachersEntity(params);
        bTeachersEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BTeachersEntity> sr = bTeachersRepository.findById(bTeachersEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bTeachersEntity.setUpdateTime(new Date());
            bTeachersRepository.save(bTeachersEntity);
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
            bTeachersRepository.deleteByIdEquals(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BTeachersEntity bTeachersEntity = new BTeachersEntity(params);
        Optional<BTeachersEntity> res = bTeachersRepository.findById(bTeachersEntity.getId());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }
}
