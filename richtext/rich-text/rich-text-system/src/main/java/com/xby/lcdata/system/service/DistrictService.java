package com.xby.lcdata.system.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @ClassName: DistrictService
 * @Description:
 * @Author: wangf
 * @Date: 2020/1/10 0010 15:05
 * @Version: 1.0
 **/
@ProxyGen
public interface DistrictService {

    void getDistrictTree(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    void getDistrict(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    void getAll(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

}
