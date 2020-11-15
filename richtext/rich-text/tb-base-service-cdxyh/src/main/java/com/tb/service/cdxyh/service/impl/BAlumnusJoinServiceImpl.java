package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BAlumnusActivityEntity;
import com.tb.service.cdxyh.entity.BAlumnusJoinEntity;
import com.tb.service.cdxyh.entity.BNewsEntity;
import com.tb.service.cdxyh.repository.BAlumnusJoinRepository;
import com.tb.service.cdxyh.service.BAlumnusJoinService;
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
public class BAlumnusJoinServiceImpl implements BAlumnusJoinService, BaseAsyncService {
    @Autowired
    private BAlumnusJoinRepository bAlumnusJoinRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);
        bAlumnusJoinEntity.setCreateTime(new Date());
        bAlumnusJoinEntity.setCreateBy(bAlumnusJoinEntity.getUserName());
        BAlumnusJoinEntity save = bAlumnusJoinRepository.save(bAlumnusJoinEntity);
        future.complete(new JsonObject(Json.encode(save)));
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        if (oConvertUtils.isNotEmpty(params.getString("president"))) {
            bAlumnusJoinEntity.setCheckState(Integer.parseInt(params.getString("president")));
            exampleMatcher.withMatcher("president", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if (oConvertUtils.isNotEmpty(params.getString("checkState"))) {
            bAlumnusJoinEntity.setCheckState(Integer.parseInt(params.getString("checkState")));
            exampleMatcher.withMatcher("checkState", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example<BAlumnusJoinEntity> ex = Example.of(bAlumnusJoinEntity, exampleMatcher);
        Page<BAlumnusJoinEntity> plist = bAlumnusJoinRepository.findAll(ex,pageable);

        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);

        Optional<BAlumnusJoinEntity> sr =bAlumnusJoinRepository.findById(bAlumnusJoinEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bAlumnusJoinEntity.setUpdateTime(new Date());
            bAlumnusJoinRepository.save(bAlumnusJoinEntity);
            //TODO 返回false说明什么？
            future.complete(new JsonObject(Json.encode(bAlumnusJoinEntity)));
        }

        handler.handle(future);
    }

    @Override
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);
        String alumnusId = bAlumnusJoinEntity.getAlumnusId();
        String userId = bAlumnusJoinEntity.getUserId();
        //查询数据条目
        List<BAlumnusJoinEntity> list = bAlumnusJoinRepository.findByAlumnusIdAndUserId(alumnusId, userId);
        bAlumnusJoinRepository.deleteAll(list);
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
//        Future<JsonArray> future = Future.future();
//        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
//        BAlumnusJoinEntity bAlumnusEntity = new BAlumnusJoinEntity();
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
//        //创建实例
//        Example<BAlumnusJoinEntity> ex = Example.of(bAlumnusEntity, matcher);
//        List<BAlumnusJoinEntity> newsList = bAlumnusJoinRepository.findAll(ex);
//        if (newsList == null || newsList.size() <= 0) {
//            future.complete(new JsonArray());
//        } else {
//            future.complete(new JsonArray(Json.encode(newsList)));
//        }
//        handler.handle(future);
    }

    @Override
    public void checkById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);

        Optional<BAlumnusJoinEntity> sr =bAlumnusJoinRepository.findById(bAlumnusJoinEntity.getId());
        if (!sr.isPresent()) {
            future.fail("未找到对应实体");
        } else {
            BAlumnusJoinEntity result=sr.get();
            result.setCheckState(Integer.parseInt(params.getString("checkState","0")));
//            result.setPresident(Integer.parseInt(params.getString("president","0")));
            bAlumnusJoinRepository.save(result);
            //TODO 返回false说明什么？
            future.complete(new JsonObject(Json.encode(result)));
        }

        handler.handle(future);
    }
    /**
     * 根据用户查询申请会长状态
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryPresidentByUserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);
        String alumnusId = bAlumnusJoinEntity.getAlumnusId();
        String userId = bAlumnusJoinEntity.getUserId();
        List<BAlumnusJoinEntity> list = bAlumnusJoinRepository.findByAlumnusIdAndUserId(alumnusId, userId);
        if (list.size()==0) {
            future.fail("未找到对应实体");
        } else {
            BAlumnusJoinEntity result=list.get(0);
            future.complete(new JsonObject(Json.encode(result)));
        }
        handler.handle(future);
    }
    /**
     * 申请会长
     *
     * @param params
     * @param handler
     */
    @Override
    public void applyByUserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);
        String alumnusId = bAlumnusJoinEntity.getAlumnusId();
        String userId = bAlumnusJoinEntity.getUserId();
        List<BAlumnusJoinEntity> list = bAlumnusJoinRepository.findByAlumnusIdAndUserId(alumnusId, userId);
if(list.size()>0){
    BAlumnusJoinEntity result=list.get(0);
    result.setCheckState(Integer.parseInt(params.getString("checkState","0")));
    bAlumnusJoinRepository.save(result);
    //TODO 返回false说明什么？
    future.complete(new JsonObject(Json.encode(result)));
}else{
    future.fail("未加入本会");
}


        handler.handle(future);
    }
}
