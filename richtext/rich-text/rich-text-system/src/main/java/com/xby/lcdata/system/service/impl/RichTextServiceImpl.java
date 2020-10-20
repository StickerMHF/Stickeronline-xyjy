package com.xby.lcdata.system.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.xby.lcdata.system.entity.RichTextEntity;
import com.xby.lcdata.system.repository.RichTextRepository;
import com.xby.lcdata.system.service.RichTextService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName: RichTextServiceImpl
 * @Description:
 * @Author: wangf
 * @Date: 2020/10/19 001916:17
 * @Version: 1.0
 **/
@Component
@AsyncServiceHandler
public class RichTextServiceImpl implements RichTextService, BaseAsyncService {

    @Autowired
    private RichTextRepository richTextRepository;

    @Override
    public void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        RichTextEntity richTextEntity = new RichTextEntity();
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        String name = params.getString("name");
        if (oConvertUtils.isNotEmpty(name)) {
            richTextEntity.setName(name);
            exampleMatcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith());
        }
        PageVo pageVo = new PageVo(params);
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        Example<RichTextEntity> example = Example.of(richTextEntity, exampleMatcher);
        Page<RichTextEntity> page = richTextRepository.findAll(example, pageable);
        future.complete(new JsonObject(Json.encode(page)));
        handler.handle(future);
    }

    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        RichTextEntity richTextEntity = new RichTextEntity();
        richTextEntity.setContent(params.getString("content"));
        richTextEntity.setName(params.getString("name"));
        richTextEntity.setCreateTime(TimeUtil.getNow());
        richTextRepository.save(richTextEntity);
        future.complete("添加成功");
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String id = params.getString("id");
        RichTextEntity richTextEntity = richTextRepository.findById(id).get();
        richTextEntity.setContent(params.getString("content"));
        richTextEntity.setName(params.getString("name"));
        richTextRepository.save(richTextEntity);
        future.complete("修改成功");
        handler.handle(future);
    }

    @Override
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String[] ids = params.getString("ids").split(",");
        for (int i = 0; i < ids.length; i++) {
            richTextRepository.deleteById(ids[i]);
        }
        future.complete("删除成功");
        handler.handle(future);
    }
}
