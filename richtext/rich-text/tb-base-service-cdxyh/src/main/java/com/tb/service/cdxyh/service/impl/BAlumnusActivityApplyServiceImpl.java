package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BAlumnusActivityApplyEntity;
import com.tb.service.cdxyh.entity.BNewsEntity;
import com.tb.service.cdxyh.repository.BAlumnusActivityApplyRepository;
import com.tb.service.cdxyh.service.BAlumnusActivityApplyService;
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
public class BAlumnusActivityApplyServiceImpl implements BAlumnusActivityApplyService, BaseAsyncService {
    @Autowired
    private BAlumnusActivityApplyRepository bAlumnusActivityApplyRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BAlumnusActivityApplyEntity bAlumnusActivityApplyEntity = new BAlumnusActivityApplyEntity(params);
        bAlumnusActivityApplyEntity.setCreateTime(new Date());
        bAlumnusActivityApplyEntity.setUpdateTime(new Date());
        bAlumnusActivityApplyRepository.save(bAlumnusActivityApplyEntity);
        future.complete("添加成功!");
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);

        BAlumnusActivityApplyEntity bAlumnusActivityApplyEntity = new BAlumnusActivityApplyEntity(params);
        String sorts=params.getString("sort","createTime");
        Sort sort = new Sort(Sort.Direction.DESC, sorts);
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        if (params.getString("type")!=null) {
//            bAlumnusActivityApplyEntity.setType(Integer.parseInt(params.getString("type")));
//            matcher.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        //创建实例
        Example<BAlumnusActivityApplyEntity> ex = Example.of(bAlumnusActivityApplyEntity, matcher);
        Page<BAlumnusActivityApplyEntity> plist = bAlumnusActivityApplyRepository.findAll(ex,pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BAlumnusActivityApplyEntity bAlumnusActivityApplyEntity = new BAlumnusActivityApplyEntity(params);
        bAlumnusActivityApplyEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BAlumnusActivityApplyEntity> sr = bAlumnusActivityApplyRepository.findById(bAlumnusActivityApplyEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bAlumnusActivityApplyEntity.setUpdateTime(new Date());
            bAlumnusActivityApplyRepository.save(bAlumnusActivityApplyEntity);
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
            bAlumnusActivityApplyRepository.deleteById(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        BAlumnusActivityApplyEntity bAlumnusActivityApplyEntity = new BAlumnusActivityApplyEntity(params);
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BAlumnusActivityApplyEntity> ex = Example.of(bAlumnusActivityApplyEntity, matcher);
        List<BAlumnusActivityApplyEntity> newsList = bAlumnusActivityApplyRepository.findAll(ex);
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
        BAlumnusActivityApplyEntity bAlumnusActivityApplyEntity = new BAlumnusActivityApplyEntity(params);
        Optional<BAlumnusActivityApplyEntity> res = bAlumnusActivityApplyRepository.findById(bAlumnusActivityApplyEntity.getId());
        if (res.isPresent()) {
            BAlumnusActivityApplyEntity bNewsEntity1=res.get();
//            bAlumnusActivityApplyRepository.save(bNewsEntity1);
            future.complete(new JsonObject(Json.encode(bNewsEntity1)));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }
}
