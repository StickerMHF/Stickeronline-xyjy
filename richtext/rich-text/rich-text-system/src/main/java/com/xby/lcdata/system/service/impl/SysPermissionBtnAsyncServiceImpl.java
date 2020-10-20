package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.entity.SysPermissionBtn;
import com.xby.lcdata.system.entity.SysRoleBtn;
import com.xby.lcdata.system.repository.SPermissionBtnRepository;
import com.xby.lcdata.system.repository.SRoleBtnRepository;
import com.xby.lcdata.system.service.SysPermissionBtnAsyncService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.xby.lcdata.system.core.annotation.LogAnnotation;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SPermissionBtnAsyncServiceImpl
 * @Description:
 * @Author: wangf
 * @Date: 2019/11/6 0006 9:49
 * @Version: 1.0
 **/
@Component
@AsyncServiceHandler
public class SysPermissionBtnAsyncServiceImpl implements SysPermissionBtnAsyncService, BaseAsyncService {

    @Autowired
    private SPermissionBtnRepository sPermissionBtnRepository;
    @Autowired
    private SRoleBtnRepository sRoleBtnRepository;

    /**
     * 条件查询按钮列表
     *
     * @param params
     * @param handler
     */
    @Override
    public void listByCondition(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        ExampleMatcher exampleMatcher =
                ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        SysPermissionBtn sysPermissionBtn = new SysPermissionBtn();
        String permissionId = params.getString("permissionId");
        PageVo pageVo = new PageVo(params);
        if (oConvertUtils.isNotEmpty(permissionId)) {
            sysPermissionBtn.setPermissionId(permissionId);
            exampleMatcher.withMatcher("permissionId", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example<SysPermissionBtn> example = Example.of(sysPermissionBtn, exampleMatcher);
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        Page<SysPermissionBtn> sPermissionBtnPage = sPermissionBtnRepository.findAll(example, pageable);
        future.complete(new JsonObject(Json.encode(sPermissionBtnPage)));
        handler.handle(future);
    }

    /**
     * 新增按钮
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "新增菜单按钮权限")
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        JsonArray btnInfo = params.getJsonArray("btnInfo");
        List<SysPermissionBtn> saveSysPermissionBtnList = new ArrayList<>();
        for (int i = 0; i < btnInfo.size(); i++) {
            saveSysPermissionBtnList.add(new SysPermissionBtn(btnInfo.getJsonObject(i)));
        }
        sPermissionBtnRepository.saveAll(saveSysPermissionBtnList);
        future.complete("添加成功!");
        handler.handle(future);
    }

    /**
     * 编辑按钮
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "编辑菜单按钮权限")
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        SysPermissionBtn sysPermissionBtn = new SysPermissionBtn(params);
        sPermissionBtnRepository.save(sysPermissionBtn);
        future.complete("修改成功!");
        handler.handle(future);
    }

    /**
     * 删除按钮
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "删除菜单按钮权限")
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String[] id = params.getString("id").split(",");
        for (int i = 0; i < id.length; i++) {
            sPermissionBtnRepository.deleteByIdEquals(id[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    /**
     * 根据菜单权限Id删除按钮
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "删除指定菜单按钮权限")
    public void deleteByPermissionId(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String permissionId = params.getString("permissionId");
        try {
            sPermissionBtnRepository.deleteByPermissionIdEquals(permissionId);
            future.complete("删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            future.fail("删除失败!" + e.getMessage());
        }
        handler.handle(future);
    }

    /**
     * 根据菜单Id查询按钮权限
     *
     * @param params
     * @param handler
     */
    @Override
    public void getBtnByPermission(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        JsonObject resultJson = new JsonObject();
        List<SysPermissionBtn> sysPermissionBtnList =
                sPermissionBtnRepository.getAllByPermissionIdEquals(params.getString("permissionId"));
        List<SysRoleBtn> sysRoleBtns = sRoleBtnRepository.getAllByRoleIdEquals(params.getString("roleId"));
        List<SysPermissionBtn> selectedBtn = new ArrayList<>();
        for (int i = 0; i < sysPermissionBtnList.size(); i++) {
            for (int k = 0; k < sysRoleBtns.size(); k++) {
                if (sysRoleBtns.get(k).getBtnId().equals(sysPermissionBtnList.get(i).getId())) {
                    selectedBtn.add(sysPermissionBtnList.get(i));
                    break;
                }
            }
        }
        resultJson.put("data", new JsonArray(Json.encode(sysPermissionBtnList)));
        resultJson.put("selected", new JsonArray(Json.encode(selectedBtn)));
        future.complete(resultJson);
        handler.handle(future);
    }

    /**
     * 根据角色Id查询按钮权限
     *
     * @param params
     * @param handler
     */
    @Override
    public void getBtnByRole(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        future.complete(new JsonArray(Json.encode(sRoleBtnRepository.getAllByRoleIdEquals(params.getString("roleId")))));
        handler.handle(future);
    }

}
