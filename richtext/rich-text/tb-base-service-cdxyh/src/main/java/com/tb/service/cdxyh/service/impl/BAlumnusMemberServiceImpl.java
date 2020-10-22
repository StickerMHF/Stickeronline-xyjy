package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BAlummunsMemberEntity;
import com.tb.service.cdxyh.repository.BAlumnusMemberRepository;
import com.tb.service.cdxyh.service.BAlumnusMemberService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AsyncServiceHandler
public class BAlumnusMemberServiceImpl implements BAlumnusMemberService, BaseAsyncService {
    @Autowired
    private BAlumnusMemberRepository bAlumnusMemberRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {

    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String type = params.getString("type");
        System.out.println(type);
        BAlummunsMemberEntity bAlummunsMemberEntity = new BAlummunsMemberEntity();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
//        if (oConvertUtils.isNotEmpty(type)) {
//            bAlummunsMemberEntity.setType(type);
//            exampleMatcher.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
//        }
        //创建实例
        Example<BAlummunsMemberEntity> ex = Example.of(bAlummunsMemberEntity);

        Page<BAlummunsMemberEntity> plist = bAlumnusMemberRepository.findAll(ex,pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {

    }

    @Override
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {

    }

    @Override
    public void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        BAlummunsMemberEntity bAlummunsMemberEntity = new BAlummunsMemberEntity();
        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BAlummunsMemberEntity> ex = Example.of(bAlummunsMemberEntity, matcher);
        List<BAlummunsMemberEntity> newsList = bAlumnusMemberRepository.findAll(ex);
        if (newsList == null || newsList.size() <= 0) {
            future.complete(new JsonArray());
        } else {
            future.complete(new JsonArray(Json.encode(newsList)));
        }
        handler.handle(future);
    }
}
