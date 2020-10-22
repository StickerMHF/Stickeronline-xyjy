package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BMemberEntity;
import com.tb.service.cdxyh.repository.BMemberRepository;
import com.tb.service.cdxyh.service.BMemberAsyncService;
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
public class BMemberAsyncServiceImpl implements BMemberAsyncService, BaseAsyncService {
    @Autowired
    private BMemberRepository bMemberRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BMemberEntity bMemberEntity = new BMemberEntity(params);
        bMemberEntity.setCreateTime(new Date());
        bMemberEntity.setUpdateTime(new Date());
        bMemberRepository.save(bMemberEntity);
        future.complete("添加成功!");
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BMemberEntity bMemberEntity = new BMemberEntity(params);
        String sorts=params.getString("sort","createTime");
        Sort sort = new Sort(Sort.Direction.DESC, sorts);
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BMemberEntity> ex = Example.of(bMemberEntity, matcher);
        Page<BMemberEntity> plist = bMemberRepository.findAll(ex,pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BMemberEntity bMemberEntity = new BMemberEntity(params);
        bMemberEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BMemberEntity> sr = bMemberRepository.findById(bMemberEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bMemberEntity.setUpdateTime(new Date());
            bMemberRepository.save(bMemberEntity);
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
            bMemberRepository.deleteByIdEquals(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BMemberEntity bMemberEntity = new BMemberEntity(params);
        Optional<BMemberEntity> res = bMemberRepository.findById(bMemberEntity.getId());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }
}
