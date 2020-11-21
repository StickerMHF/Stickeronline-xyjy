package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BCollectEntity;
import com.tb.service.cdxyh.repository.BCollectRepository;
import com.tb.service.cdxyh.service.BCollectAsyncService;
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
public class BCollectAsyncServiceImpl implements BCollectAsyncService, BaseAsyncService {
    @Autowired
    private BCollectRepository bCollectRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BCollectEntity bCollectEntity = new BCollectEntity(params);
        bCollectEntity.setCreateTime(new Date());
        bCollectEntity.setUpdateTime(new Date());
        bCollectRepository.save(bCollectEntity);
        future.complete("添加成功!");
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BCollectEntity bCollectEntity = new BCollectEntity(params);
        String sorts=params.getString("sort","createTime");
        Sort sort = new Sort(Sort.Direction.DESC, sorts);
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BCollectEntity> ex = Example.of(bCollectEntity, matcher);
        Page<BCollectEntity> plist = bCollectRepository.findAll(ex,pageable);
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
        BCollectEntity bCollectEntity = new BCollectEntity(params);
        bCollectEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BCollectEntity> sr = bCollectRepository.findById(bCollectEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bCollectEntity.setUpdateTime(new Date());
            bCollectRepository.save(bCollectEntity);
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
            bCollectRepository.deleteById(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }
    @Override
    public void deleteByUserIdAndRecordId(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String userId = params.getString("userId");
        String recordId = params.getString("recordId");
        List<BCollectEntity> list=bCollectRepository.findAllByUserIdAndRecordId(userId,recordId);
        bCollectRepository.deleteAll(list);
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BCollectEntity bCollectEntity = new BCollectEntity(params);
        Optional<BCollectEntity> res = bCollectRepository.findById(bCollectEntity.getId());
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
        BCollectEntity bCollectEntity = new BCollectEntity(params);
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
//        matcher.withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BCollectEntity> ex = Example.of(bCollectEntity,matcher);
        Integer offset=(pageVo.getPageNo()-1)*pageVo.getPageSize();
        List<BCollectEntity> plist = bCollectRepository.queryUserId(userId,pageable.getPageSize(),offset);
        Integer total=bCollectRepository.countUserId(userId);
        JsonObject resObj = new JsonObject();
        JsonArray contents=new JsonArray(Json.encode(plist));
        resObj.put("content",contents);
        resObj.put("total",total);
        future.complete(resObj);
        handler.handle(future);
    }

}
