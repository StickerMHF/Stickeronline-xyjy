package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.repository.DistrictRepository;
import com.xby.lcdata.system.service.DistrictService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.oConvertUtils;
import com.xby.lcdata.system.entity.SDistrictEntity;
import com.xby.lcdata.system.model.DistrictTree;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: DistrictServiceImpl
 * @Description:
 * @Author: wangf
 * @Date: 2020/1/10 0010 15:07
 * @Version: 1.0
 **/
@Component
@AsyncServiceHandler
public class DistrictServiceImpl implements DistrictService, BaseAsyncService {

    @Autowired
    private DistrictRepository districtRepository;

    /**
     * 获取行政区域树
     *
     * @param params
     * @param handler
     */
    @Override
    public void getDistrictTree(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        Sort sort = new Sort(Sort.Direction.ASC, "adCode");
        List<SDistrictEntity> districtList = districtRepository.findAll(sort);
        List<DistrictTree> treeList = new ArrayList<>();
        buildDistrictTree(districtList, treeList, null);
        future.complete(new JsonArray(Json.encode(treeList)));
        handler.handle(future);
    }

    /**
     * 构建行政区域树
     *
     * @param districtList
     * @param treeList
     * @param districtTree
     */
    public void buildDistrictTree(List<SDistrictEntity> districtList, List<DistrictTree> treeList,
                                  DistrictTree districtTree) {
        for (int i = 0; i < districtList.size(); i++) {
            SDistrictEntity district = districtList.get(i);
            String superiorCode = district.getSuperiorCode();
            DistrictTree tree = new DistrictTree(district);
            if (districtTree == null && superiorCode.equals("0") || tree.getSuperiorCode() == null) {
                treeList.add(tree);
                treeList.remove(i);
                buildDistrictTree(districtList, treeList, tree);
            }
            if (districtTree != null && !superiorCode.equals("0") && superiorCode.equals(districtTree.getAdCode())) {
                districtTree.getDistricts().add(tree);
                treeList.remove(i);
                buildDistrictTree(districtList, treeList, tree);
            }
        }
    }

    /**
     * 获取行政区域列表
     *
     * @param params
     * @param handler
     */
    @Override
    public void getDistrict(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        String adCode = params.getString("adCode");
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        SDistrictEntity sDistrictEntity = new SDistrictEntity();
        if (oConvertUtils.isNotEmpty(adCode)) {
            sDistrictEntity.setSuperiorCode(adCode);
            exampleMatcher.withMatcher("superiorCode", ExampleMatcher.GenericPropertyMatchers.contains());
        } else {
            sDistrictEntity.setLevel("country");
            exampleMatcher.withMatcher("level", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example<SDistrictEntity> example = Example.of(sDistrictEntity, exampleMatcher);
        List<SDistrictEntity> districtList = districtRepository.findAll(example);
        if (districtList.size() > 0) {
            future.complete(new JsonArray(Json.encode(districtList)));
        } else {
            future.complete(new JsonArray());
        }
        handler.handle(future);
    }

    @Override
    public void getAll(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        List<SDistrictEntity> districtList = districtRepository.findAll();
        if (districtList.size() > 0) {
            future.complete(new JsonArray(Json.encode(districtList)));
        } else {
            future.complete(new JsonArray());
        }
        handler.handle(future);
    }

}
