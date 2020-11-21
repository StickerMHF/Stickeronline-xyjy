package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BMessageEntity;
import com.tb.service.cdxyh.entity.BMomentsEntity;
import com.tb.service.cdxyh.entity.BMomentsLikeEntity;
import com.tb.service.cdxyh.repository.BMessageRepository;
import com.tb.service.cdxyh.service.BMessageAsyncService;
import com.tb.service.cdxyh.utils.HttpUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@AsyncServiceHandler
public class BMessageAsyncServiceImpl implements BMessageAsyncService, BaseAsyncService {
    @Autowired
    private BMessageRepository bMessageRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BMessageEntity bMessageEntity = new BMessageEntity(params);
        bMessageEntity.setCreateTime(new Date());
        bMessageEntity.setUpdateTime(new Date());
        bMessageRepository.save(bMessageEntity);
        future.complete("添加成功!");
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BMessageEntity bMessageEntity = new BMessageEntity(params);
        String sorts=params.getString("sort","createTime");
        Sort sort = new Sort(Sort.Direction.DESC, sorts);
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BMessageEntity> ex = Example.of(bMessageEntity, matcher);
        Page<BMessageEntity> plist = bMessageRepository.findAll(ex,pageable);
        JsonObject res=new JsonObject(Json.encode(plist));
//        this.queryPageListOfChdEdu(r->{
//            if(r.succeeded()){
//                future.complete(res);
//            }else {
//                future.fail(r.cause());
//            }
//        });
        future.complete(res);
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BMessageEntity bMessageEntity = new BMessageEntity(params);
        bMessageEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BMessageEntity> sr = bMessageRepository.findById(bMessageEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bMessageEntity.setUpdateTime(new Date());
            bMessageRepository.save(bMessageEntity);
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
            bMessageRepository.deleteById(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BMessageEntity bMessageEntity = new BMessageEntity(params);
        Optional<BMessageEntity> res = bMessageRepository.findById(bMessageEntity.getId());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }
    @Override
    public void queryByUserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BMessageEntity bMessageEntity = new BMessageEntity(params);
        String likeCount = params.getString("order");
        String userId = params.getString("userId");
        Sort sort;
        if (likeCount != null&&!likeCount.isEmpty()){
            sort =new Sort(Sort.Direction.DESC, likeCount);
        } else {
            sort = new Sort(Sort.Direction.DESC, "createTime");
        }
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        matcher.withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        bMessageEntity.setStatus(1);
        Example<BMessageEntity> ex = Example.of(bMessageEntity,matcher);
        Integer offset=(pageVo.getPageNo()-1)*pageVo.getPageSize();
        List<BMessageEntity> plist = bMessageRepository.queryUserId(userId,pageable.getPageSize(),offset);
        JsonObject resObj = new JsonObject();
        JsonArray contents=new JsonArray(Json.encode(plist));
        resObj.put("content",contents);
        future.complete(resObj);
        handler.handle(future);
    }

}
