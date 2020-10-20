package com.xby.lcdata.system.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * @ClassName: DataChangeRecordService
 * @Description:
 * @Author: wangf
 * @Date: 2020/2/25 0025 17:35
 * @Version: 1.0
 **/
@ProxyGen
public interface DataChangeRecordService {

    void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    void getChangeRecord(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

}
