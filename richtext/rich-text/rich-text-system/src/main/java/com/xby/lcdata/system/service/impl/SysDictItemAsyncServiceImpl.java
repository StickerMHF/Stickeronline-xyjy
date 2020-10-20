package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.repository.SysDictItemRepository;
import com.xby.lcdata.system.service.SysDictItemAsyncService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.JwtUtil;
import com.sticker.online.core.utils.TimeUtil;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.xby.lcdata.system.core.annotation.LogAnnotation;
import com.xby.lcdata.system.entity.SysDictItem;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.Optional;

import static java.lang.Integer.parseInt;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.service.impl
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/2
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Component
@AsyncServiceHandler
public class SysDictItemAsyncServiceImpl implements SysDictItemAsyncService, BaseAsyncService {
    @Autowired
    private SysDictItemRepository sysDictItemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 获取字典列表
     *
     * @param params
     * @param handler
     */
    @Override
    public void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        try {
            ExampleMatcher matcher =
                    ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); //构建对象
            String id = params.getString("dictId");
            String itemText = params.getString("itemText");
            Integer status = null;
            if (params.getString("status") != null) {
                status = parseInt(params.getString("status"));
            }
            SysDictItem sysDictItem = new SysDictItem();
            if (oConvertUtils.isNotEmpty(id)) {
                sysDictItem.setDictId(id);
                matcher.withMatcher("dictId", ExampleMatcher.GenericPropertyMatchers.contains());
                if (oConvertUtils.isNotEmpty(itemText)) {
                    sysDictItem.setText(itemText);
                    matcher.withMatcher("text", ExampleMatcher.GenericPropertyMatchers.startsWith());
                }
                if (oConvertUtils.isNotEmpty(status)) {
                    sysDictItem.setStatus(status);
                    matcher.withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains());
                }
                //创建实例
                Example<SysDictItem> ex = Example.of(sysDictItem, matcher);
                //分页信息
                Sort sort = new Sort(Sort.Direction.DESC, "sortOrder");
                PageVo pageVo = new PageVo(params);
                Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort); //页码：前端从1开始，jpa从0开始，做个转换
                Page<SysDictItem> sysDictItems = sysDictItemRepository.findAll(ex, pageable);
                future.complete(new JsonObject(Json.encode(sysDictItems)));
            } else {
                future.fail("id不能为空!");
            }
        } catch (Exception e) {
            future.fail(e);
        }
        handler.handle(future);
    }

    /**
     * 根据dictId查询字典数据
     *
     * @param dictId
     * @return
     */
    @Override
    public void querrByDicId(String dictId, String itemText, Integer status, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        SysDictItem sysDictItem = new SysDictItem();
        if (oConvertUtils.isNotEmpty(itemText)) {
            sysDictItem.setText(itemText);
            exampleMatcher.withMatcher("text",ExampleMatcher.GenericPropertyMatchers.startsWith());
        }
        if (status != -1) {
            sysDictItem.setStatus(status);
            exampleMatcher.withMatcher("status",ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example<SysDictItem> example = Example.of(sysDictItem,exampleMatcher);
        future.complete(new JsonArray(Json.encode(sysDictItemRepository.findAll(example))));
        handler.handle(future);
    }

    /**
     * 新增
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "新增字典数据")
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        try {
            SysDictItem sysDictItem = new SysDictItem(params);
            sysDictItem.setStatus(params.getInteger("status"));
            sysDictItem.setSortOrder(params.getInteger("sortOrder"));
            sysDictItem.setCreateTime(new Date());
            sysDictItemRepository.save(sysDictItem);
            future.complete(new JsonObject(Json.encode(sysDictItem)));
        } catch (Exception e) {
            future.fail(e);
        }
        handler.handle(future);
    }

    /**
     * 编辑
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "编辑字典数据")
    public void edit(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        try {
            sysDictItemRepository.modifyDictItem(params.getString("dictId"), params.getString("text"),
                    params.getString("value"),
                    params.getString("description"), params.getInteger("sortOrder"),
                    params.getInteger("status"),
                    JwtUtil.getUsername(params.getString("token")),
                    TimeUtil.getDateNow(), params.getString("id"));
            future.complete(new JsonObject(Json.encode(sysDictItemRepository.findById(params.getString("id")))));
        } catch (Exception e) {
            future.fail(e);
        }
        handler.handle(future);
    }

    /**
     * @功能：删除
     */
    @Override
    @LogAnnotation(operationType = "删除字典数据")
    public void delete(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        try {
            String id = params.getString("id", "");
            SysDictItem sysDictItem = new SysDictItem(params);
            Optional<SysDictItem> sysDict = sysDictItemRepository.findById(id);
            if (sysDict.equals(null)) {
                future.fail("未找到对应实体");
            } else {
                //update-begin--Author:huangzhilin  Date:20140417 for：[bugfree号]数据字典增加级联删除功能--------------------
                sysDictItemRepository.delete(sysDict.get());
                //update-begin--Author:huangzhilin  Date:20140417 for：[bugfree号]数据字典增加级联删除功能--------------------
                future.complete(new JsonObject(Json.encode(sysDict)));
            }
        } catch (Exception e) {
            future.fail(e);
        }
        handler.handle(future);
    }

    /**
     * @功能：批量删除
     */
    @Override
    @LogAnnotation(operationType = "批量删除字典数据")
    public void deleteBatch(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        try {
            String ids = params.getString("ids", "");
            if (ids == null || "".equals(ids.trim())) {
                future.fail("参数不识别！");
            } else {
                String[] id = ids.split(",");
                for (int i = 0; i < id.length; i++) {
                    SysDictItem sysDict = sysDictItemRepository.findById(id[i]).get();
                    sysDictItemRepository.delete(sysDict);
                }
                future.complete(new JsonObject().put("ids", ids));
            }
        } catch (Exception e) {
            future.fail(e);
        }
        handler.handle(future);
    }
}
