package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BMemberAttentionEntity;
import com.tb.service.cdxyh.repository.BMemberAttentionRepository;
import com.tb.service.cdxyh.service.BMemberAttentionAsyncService;
import com.tb.service.cdxyh.utils.HttpUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@AsyncServiceHandler
public class BMemberAttentionAsyncServiceImpl implements BMemberAttentionAsyncService, BaseAsyncService {
    @Autowired
    private BMemberAttentionRepository bMemberAttentionRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BMemberAttentionEntity bMemberAttentionEntity = new BMemberAttentionEntity(params);
        bMemberAttentionEntity.setCreateTime(new Date());
        bMemberAttentionEntity.setUpdateTime(new Date());
        bMemberAttentionRepository.save(bMemberAttentionEntity);
        future.complete("添加成功!");
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BMemberAttentionEntity bMemberAttentionEntity = new BMemberAttentionEntity(params);
        String sorts=params.getString("sort","createTime");
        Sort sort = new Sort(Sort.Direction.DESC, sorts);
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BMemberAttentionEntity> ex = Example.of(bMemberAttentionEntity, matcher);
        Page<BMemberAttentionEntity> plist = bMemberAttentionRepository.findAll(ex,pageable);
        JsonObject res=new JsonObject(Json.encode(plist));
        future.complete(res);
        handler.handle(future);
    }




    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BMemberAttentionEntity bMemberAttentionEntity = new BMemberAttentionEntity(params);
        bMemberAttentionEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BMemberAttentionEntity> sr = bMemberAttentionRepository.findById(bMemberAttentionEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bMemberAttentionEntity.setUpdateTime(new Date());
            bMemberAttentionRepository.save(bMemberAttentionEntity);
            //TODO 返回false说明什么？
            future.complete("修改成功!");
        }
        handler.handle(future);
    }

    @Override
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BMemberAttentionEntity bMemberAttentionEntity = new BMemberAttentionEntity(params);
        List<BMemberAttentionEntity> list= bMemberAttentionRepository.findAllByUserIdAndMemberId(bMemberAttentionEntity.getUserId(),bMemberAttentionEntity.getMemberId());
        bMemberAttentionRepository.deleteInBatch(list);
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BMemberAttentionEntity bMemberAttentionEntity = new BMemberAttentionEntity(params);
        Optional<BMemberAttentionEntity> res = bMemberAttentionRepository.findById(bMemberAttentionEntity.getId());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }
}
