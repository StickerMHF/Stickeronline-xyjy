package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BAlumnusActivityApplyEntity;
import com.tb.service.cdxyh.entity.BAlumnusActivityEntity;
import com.tb.service.cdxyh.entity.BAlumnusEntity;
import com.tb.service.cdxyh.repository.BAlumnusActivityApplyRepository;
import com.tb.service.cdxyh.repository.BAlumnusActivityRepository;
import com.tb.service.cdxyh.service.BAlumnusActivityService;
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
public class BAlumnusActivityServiceImpl implements BAlumnusActivityService, BaseAsyncService {
    @Autowired
    private BAlumnusActivityRepository bAlumnusActivityRepository;
    @Autowired
    private BAlumnusActivityApplyRepository bAlumnusActivityApplyRepository;

    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        try{
            BAlumnusActivityEntity bAlumnusActivityEntity = new BAlumnusActivityEntity(params);
            bAlumnusActivityEntity.setCreateTime(new Date());
            bAlumnusActivityEntity.setUpdateTime(new Date());
            bAlumnusActivityEntity.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params.getString("startTime")));
            bAlumnusActivityEntity.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params.getString("endTime")));
            bAlumnusActivityEntity.setDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params.getString("deadline")));
            bAlumnusActivityRepository.save(bAlumnusActivityEntity);
            future.complete("添加成功!");
        }catch (Exception e){
            future.fail(e);
        }

        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BAlumnusActivityEntity bAlumnusActivityEntity = new BAlumnusActivityEntity(params);
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        String fid = params.getString("fid");
        if (oConvertUtils.isNotEmpty(fid) && !fid.equals("")) {
            exampleMatcher.withMatcher("fid", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if (oConvertUtils.isNotEmpty(params.getString("type"))) {
            bAlumnusActivityEntity.setType(Integer.parseInt(params.getString("type")));
            exampleMatcher.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example<BAlumnusActivityEntity> ex = Example.of(bAlumnusActivityEntity, exampleMatcher);
        Page<BAlumnusActivityEntity> plist = bAlumnusActivityRepository.findAll(ex,pageable);

        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        try {
            BAlumnusActivityEntity bAlumnusActivityEntity = new BAlumnusActivityEntity(params);
            bAlumnusActivityEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
            bAlumnusActivityEntity.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params.getString("startTime")));
            bAlumnusActivityEntity.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params.getString("endTime")));
            bAlumnusActivityEntity.setDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params.getString("deadline")));
            Optional<BAlumnusActivityEntity> sr = bAlumnusActivityRepository.findById(bAlumnusActivityEntity.getId());
            if (sr == null) {
                future.fail("未找到对应实体");
            } else {
                bAlumnusActivityEntity.setUpdateTime(new Date());
                bAlumnusActivityRepository.save(bAlumnusActivityEntity);
                //TODO 返回false说明什么？
                future.complete("修改成功!");
            }
        }catch (Exception e){
            future.fail(e);
        }

        handler.handle(future);
    }

    @Override
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String[] ids = params.getString("id").split(",");
        for (int i = 0; i < ids.length; i++) {
            bAlumnusActivityRepository.deleteById(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        BAlumnusActivityEntity bAlumnusActivityEntity = new BAlumnusActivityEntity();
        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BAlumnusActivityEntity> ex = Example.of(bAlumnusActivityEntity, matcher);
        List<BAlumnusActivityEntity> newsList = bAlumnusActivityRepository.findAll(ex);
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
        BAlumnusActivityEntity bAlumnusActivityEntity = new BAlumnusActivityEntity(params);
        Optional<BAlumnusActivityEntity> res = bAlumnusActivityRepository.findById(bAlumnusActivityEntity.getId());
        String userId=params.getString("userId");
        if (res.isPresent()) {
            JsonObject result=new JsonObject(Json.encode(res.get()));
            if(userId!=null){
                List<BAlumnusActivityApplyEntity> applyList=bAlumnusActivityApplyRepository.findAllByActivityIdAndUserId(res.get().getId(),userId);
                if(applyList.size()>0){
                    result.put("isApply",true);
                }
                future.complete(result);
            }else{
                future.complete(result);
            }


        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }
}
