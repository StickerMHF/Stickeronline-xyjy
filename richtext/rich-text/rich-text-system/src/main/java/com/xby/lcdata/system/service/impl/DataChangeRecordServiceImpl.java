package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.repository.SChangeRecordRepository;
import com.xby.lcdata.system.service.DataChangeRecordService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.tb.base.common.vo.PageVo;
import com.xby.lcdata.system.entity.SChangeRecord;
import com.xby.lcdata.system.entity.SysColumn;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DataChangeRecordServiceImpl
 * @Description:
 * @Author: wangf
 * @Date: 2020/2/25 0025 17:36
 * @Version: 1.0
 **/
@Component
@AsyncServiceHandler
public class DataChangeRecordServiceImpl implements DataChangeRecordService, BaseAsyncService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private SChangeRecordRepository sChangeRecordRepository;

    @Override
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {

    }

    public List<Map<String, Object>> getList(String sql, Class entity) {
        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.unwrap(org.hibernate.SQLQuery.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//        Query nativeQuery = entityManager.createNativeQuery(sql, entity);
        List<Map<String, Object>> list = nativeQuery.getResultList();
        return list;
    }

    public List<SysColumn> querycolomn(String tableName) {
        String sql = String.format("select @i\\:=@i+1 as id,t1.* from information_schema.`COLUMNS` t1,(select " +
                "@i\\:=0) t2  where t1.TABLE_NAME = '%s'", tableName);
        Query nativeQuery = entityManager.createNativeQuery(sql, SysColumn.class);
        List<SysColumn> list = nativeQuery.getResultList();
        if (list.size() > 0) {
            Query nativeQueryList = entityManager.createNativeQuery(sql, SysColumn.class);
            return nativeQueryList.getResultList();
        } else {
            return null;
        }
    }

    @Override
    public void getChangeRecord(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        SChangeRecord sChangeRecord = new SChangeRecord();
        PageVo pageVo = new PageVo(params);
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize());
        Example<SChangeRecord> example = Example.of(sChangeRecord, exampleMatcher);
        Page<SChangeRecord> districtList = sChangeRecordRepository.findAll(example, pageable);
        future.complete(new JsonObject(Json.encode(districtList)));
        handler.handle(future);
    }
}
