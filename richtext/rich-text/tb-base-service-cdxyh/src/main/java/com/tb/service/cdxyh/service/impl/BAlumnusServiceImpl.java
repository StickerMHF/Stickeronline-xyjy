package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BAlumnusActivityEntity;
import com.tb.service.cdxyh.entity.BAlumnusEntity;
import com.tb.service.cdxyh.entity.BAlumnusJoinEntity;
import com.tb.service.cdxyh.entity.BNewsEntity;
import com.tb.service.cdxyh.repository.BAlumnusActivityRepository;
import com.tb.service.cdxyh.repository.BAlumnusJoinRepository;
import com.tb.service.cdxyh.repository.BAlumnusMemberRepository;
import com.tb.service.cdxyh.repository.BAlumnusRepository;
import com.tb.service.cdxyh.service.BAlumnusService;
import io.swagger.models.auth.In;
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
public class BAlumnusServiceImpl implements BAlumnusService, BaseAsyncService {
    @Autowired
    private BAlumnusRepository bAlumnusRepository;
    @Autowired
    private BAlumnusJoinRepository bAlumnusJoinRepository;
    @Autowired
     private BAlumnusActivityRepository bAlumnusActivityRepository;

    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BAlumnusEntity bAlumnusEntity = new BAlumnusEntity(params);
        bAlumnusEntity.setCreateTime(new Date());
        bAlumnusEntity.setUpdateTime(new Date());
        bAlumnusRepository.save(bAlumnusEntity);
        future.complete("添加成功!");
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String type = params.getString("type");
        System.out.println(type);
        BAlumnusEntity bAlumnusEntity = new BAlumnusEntity();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        if (oConvertUtils.isNotEmpty(type) && !type.equals("all")) {
            bAlumnusEntity.setType(type);
            exampleMatcher.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        //创建实例
        Example<BAlumnusEntity> ex = Example.of(bAlumnusEntity, exampleMatcher);
        Page<BAlumnusEntity> plist = bAlumnusRepository.findAll(ex,pageable);
        JsonObject resObj = new JsonObject(Json.encode(plist));
        JsonArray contents = resObj.getJsonArray("content");
        String userId = params.getString("userId");
        for (int i = 0; i < contents.size(); i++) {
            JsonObject obj = contents.getJsonObject(i);
            String id = obj.getString("id");
            //获取关注情况
            Integer join = bAlumnusJoinRepository.countAllByAlumnusIdAndUserId(id, userId);
            if (join > 0){
                obj.put("join", true);
            } else {
                obj.put("join", false);
            }
            //统计活动数量
            Integer activity = bAlumnusActivityRepository.countAllByFid(id);
            obj.put("activity", activity);
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
        BAlumnusEntity bAlumnusEntity = new BAlumnusEntity(params);
        bAlumnusEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BAlumnusEntity> sr = bAlumnusRepository.findById(bAlumnusEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bAlumnusEntity.setUpdateTime(new Date());
            bAlumnusRepository.save(bAlumnusEntity);
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
            bAlumnusRepository.deleteById(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        BAlumnusEntity bAlumnusEntity = new BAlumnusEntity();
        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BAlumnusEntity> ex = Example.of(bAlumnusEntity, matcher);
        List<BAlumnusEntity> newsList = bAlumnusRepository.findAll(ex);
        if (newsList == null || newsList.size() <= 0) {
            future.complete(new JsonArray());
        } else {
            future.complete(new JsonArray(Json.encode(newsList)));
        }
        handler.handle(future);
    }

    @Override
    public void queryListByUserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String userId = params.getString("userId");
        Integer offset=(pageVo.getPageNo()-1)*pageVo.getPageSize();
        Integer zoom = bAlumnusRepository.countByuserId();  //统计总条数
        //总页数
        Integer totalPages = (zoom-1)/pageVo.getPageSize()+1;
        List<Map<String, Object>> list = bAlumnusRepository.queryByuserId(userId,pageVo.getPageSize(),offset);
        JsonArray resArray = new JsonArray();
        for (int i = 0; i < list.size(); i++) {
            JsonObject item = new JsonObject(list.get(i));
            JsonObject resObj = new JsonObject(Json.encode(item));
            String id = item.getString("id");
            //统计活动数量
            Integer activity = bAlumnusActivityRepository.countAllByFid(id);
            resObj.put("activity", activity);
            //统计成员
            Integer member = bAlumnusJoinRepository.countAllByAlumnusIdAndStatus(id,"1");
            resObj.put("member", member);
            resArray.add(resObj);
        }
        JsonObject pageable = new JsonObject();
        pageable.put("pageNumber", pageVo.getPageNo());
        pageable.put("offset", zoom);
        pageable.put("pageSize", pageVo.getPageSize());
        future.complete(new JsonObject().put("content",resArray).put("pageable",pageable).put("totalPages", totalPages));
        handler.handle(future);
    }

}
