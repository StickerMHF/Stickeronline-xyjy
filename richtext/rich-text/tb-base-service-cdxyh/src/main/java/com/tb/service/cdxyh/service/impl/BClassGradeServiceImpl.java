package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BClassGradeEntity;
import com.tb.service.cdxyh.repository.BAlumnusJoinRepository;
import com.tb.service.cdxyh.repository.BClassGradeRepository;
import com.tb.service.cdxyh.service.BClassGradeService;
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
public class BClassGradeServiceImpl implements BClassGradeService, BaseAsyncService {
    @Autowired
    private BClassGradeRepository bClassGradeRepository;
    @Autowired
    private BAlumnusJoinRepository bAlumnusJoinRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BClassGradeEntity bClassGradeEntity = new BClassGradeEntity(params);
        bClassGradeEntity.setCreateTime(new Date());
        bClassGradeEntity.setUpdateTime(new Date());
        bClassGradeRepository.save(bClassGradeEntity);
        future.complete("添加成功!");
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);

        BClassGradeEntity bClassGradeEntity = new BClassGradeEntity();
        String sorts=params.getString("sort","createTime");
        Sort sort = new Sort(Sort.Direction.DESC, sorts);

        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
//        if (params.getString("type")!=null) {
//            bClassGradeEntity.setType(Integer.parseInt(params.getString("type")));
//            matcher.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
//        }
        //创建实例
        Example<BClassGradeEntity> ex = Example.of(bClassGradeEntity, matcher);
        Page<BClassGradeEntity> plist = bClassGradeRepository.findAll(ex,pageable);
        JsonObject resObj = new JsonObject(Json.encode(plist));
        JsonArray contents = resObj.getJsonArray("content");
        for (int i = 0; i < contents.size(); i++) {
            JsonObject obj = contents.getJsonObject(i);
            String id = obj.getString("id");
            //统计成员
            Integer member = bAlumnusJoinRepository.countAllByAlumnusIdAndStatus(id,"1");
            obj.put("member", member);
        }
        future.complete(resObj);
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BClassGradeEntity bClassGradeEntity = new BClassGradeEntity(params);
        bClassGradeEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BClassGradeEntity> sr = bClassGradeRepository.findById(bClassGradeEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bClassGradeEntity.setUpdateTime(new Date());
            bClassGradeRepository.save(bClassGradeEntity);
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
            bClassGradeRepository.deleteById(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        BClassGradeEntity bClassGradeEntity = new BClassGradeEntity();
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BClassGradeEntity> ex = Example.of(bClassGradeEntity, matcher);
        List<BClassGradeEntity> newsList = bClassGradeRepository.findAll(ex);
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
        BClassGradeEntity bClassGradeEntity = new BClassGradeEntity(params);
        Optional<BClassGradeEntity> res = bClassGradeRepository.findById(bClassGradeEntity.getId());
        if (res.isPresent()) {
            BClassGradeEntity bClassGradeEntity1=res.get();
//            if(bClassGradeEntity1.getViewCount()!=null){
//                bClassGradeEntity1.setViewCount(bClassGradeEntity1.getViewCount()+1);
//            }else{
//                bClassGradeEntity1.setViewCount(1);
//            }

            bClassGradeRepository.save(bClassGradeEntity1);
            future.complete(new JsonObject(Json.encode(bClassGradeEntity1)));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }
}
