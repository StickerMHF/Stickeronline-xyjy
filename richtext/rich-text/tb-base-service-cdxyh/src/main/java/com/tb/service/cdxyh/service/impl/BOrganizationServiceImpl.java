package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.tb.service.cdxyh.entity.BOrganizationEntity;
import com.tb.service.cdxyh.repository.BOrganizationRepository;
import com.tb.service.cdxyh.service.BOrganizationService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AsyncServiceHandler
public class BOrganizationServiceImpl implements BOrganizationService, BaseAsyncService {
    @Autowired
    private BOrganizationRepository bOrganizationRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {

    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {

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
        BOrganizationEntity bNewsEntity = new BOrganizationEntity();
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BOrganizationEntity> ex = Example.of(bNewsEntity, matcher);
        List<BOrganizationEntity> newsList = bOrganizationRepository.findAll(ex);
        if (newsList == null || newsList.size() <= 0) {
            future.complete(new JsonArray());
        } else {
            future.complete(new JsonArray(Json.encode(newsList)));
        }
        handler.handle(future);
    }
}
