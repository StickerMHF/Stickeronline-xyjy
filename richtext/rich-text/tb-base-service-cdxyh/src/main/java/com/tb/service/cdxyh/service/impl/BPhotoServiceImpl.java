package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BNewsEntity;
import com.tb.service.cdxyh.entity.BPhotoEntity;
import com.tb.service.cdxyh.repository.BPhotoRepository;
import com.tb.service.cdxyh.service.BPhotoService;
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
import java.util.Map;
import java.util.Optional;

@Component
@AsyncServiceHandler
public class BPhotoServiceImpl implements BPhotoService, BaseAsyncService {
    @Autowired
    private BPhotoRepository bPhotoRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BPhotoEntity bPhotoEntity = new BPhotoEntity(params);
        bPhotoEntity.setCreateTime(new Date());
        bPhotoEntity.setCreateBy(bPhotoEntity.getUserName());
        BPhotoEntity save = bPhotoRepository.save(bPhotoEntity);
        if (oConvertUtils.isNotEmpty(save)){
            future.complete(new JsonObject(Json.encode(save)));
        } else {
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String userId = params.getString("userId");
        BPhotoEntity bPhotoEntity = new BPhotoEntity();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        if (oConvertUtils.isNotEmpty(userId)) {
            bPhotoEntity.setUserId(userId);
            exampleMatcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example<BPhotoEntity> ex = Example.of(bPhotoEntity, exampleMatcher);
        Page<BPhotoEntity> plist = bPhotoRepository.findAll(ex,pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BPhotoEntity bPhotoEntity = new BPhotoEntity(params);
        bPhotoEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BPhotoEntity> sr = bPhotoRepository.findById(bPhotoEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bPhotoEntity.setUpdateTime(new Date());
            bPhotoRepository.save(bPhotoEntity);
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
            bPhotoRepository.deleteById(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void getUserList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String userId = params.getString("userId");
        Integer offset = (pageVo.getPageNo()-1)*pageVo.getPageSize();
        Integer zoom = bPhotoRepository.countByUserId(userId);  //统计总条数
        //总页数
        Integer totalPages = (zoom-1)/pageVo.getPageSize()+1;
        List<Map<String, Object>> list = bPhotoRepository.groupByUserId(pageVo.getPageSize(),offset);

        JsonObject pageable = new JsonObject();
        pageable.put("pageNumber", pageVo.getPageNo());
        pageable.put("offset", 0);
        pageable.put("pageSize", pageVo.getPageSize());
        future.complete(new JsonObject().put("content",list).put("pageable",pageable).put("totalPages", totalPages));
//        future.complete(new JsonArray(Json.encode(list)));

        handler.handle(future);
    }

    @Override
    public void getList(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {

    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BPhotoEntity bPhotoEntity = new BPhotoEntity(params);
        Optional<BPhotoEntity> res = bPhotoRepository.findById(bPhotoEntity.getId());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }



}
