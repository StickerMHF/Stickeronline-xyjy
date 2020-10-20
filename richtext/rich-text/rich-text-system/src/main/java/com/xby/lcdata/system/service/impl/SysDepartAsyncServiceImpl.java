package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.repository.SysDepartRepository;
import com.xby.lcdata.system.repository.SysUserDepartRepository;
import com.xby.lcdata.system.service.SysDepartAsyncService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.JwtUtil;
import com.sticker.online.core.utils.TimeUtil;
import com.sticker.online.core.utils.UUIDUtil;
import com.sticker.online.core.utils.oConvertUtils;
import com.xby.lcdata.system.core.annotation.LogAnnotation;
import com.xby.lcdata.system.entity.SysDepart;
import com.xby.lcdata.system.model.DepartIdModel;
import com.xby.lcdata.system.model.SysDepartTreeModel;
import com.xby.lcdata.system.utils.FindsDepartsChildrenUtil;
import com.xby.lcdata.system.utils.YouBianCodeUtil;
import io.netty.util.internal.StringUtil;
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
import java.util.Date;
import java.util.List;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.service.impl
 * @Description:
 * @Author: Sticker
 * @Date: 2019/7/29
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Component
@AsyncServiceHandler
public class SysDepartAsyncServiceImpl implements SysDepartAsyncService, BaseAsyncService {

    @Autowired
    private SysDepartRepository sysDepartRepository;
    @Autowired
    private SysUserDepartRepository sysUserDepartRepository;

    /**
     * 新增部门
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "新增部门")
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String username = JwtUtil.getUserInfo(params.getString("token")).getString("username");
        SysDepart sysDepart = new SysDepart(params);
        sysDepart.setCreateBy(username);
        this.saveDepartData(sysDepart, username);
        future.complete("添加成功!");
        handler.handle(future);
    }

    /**
     * 编辑部门
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "编辑部门")
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        SysDepart sysDepart = new SysDepart(params);
        String username = JwtUtil.getUserInfo(params.getString("token")).getString("username");
        sysDepart.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        sysDepart.setUpdateBy(username);
        SysDepart sysDepartEntity = sysDepartRepository.getOne(sysDepart.getId());
        if (sysDepartEntity == null) {
            future.fail("未找到对应实体");
        } else {
            sysDepartRepository.modifyDepart(params.getString("departName"), params.getInteger("departOrder"),
                    params.getString("description"),
                    params.getString("mobile"), params.getString("memo"), params.getString("address"), username,
                    TimeUtil.getDateNow(), params.getString("id"));
            future.complete("修改成功!");
        }
        handler.handle(future);
    }

    /**
     * 获取部门树
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryTreeList(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        SysDepart sysDepart = new SysDepart();
        sysDepart.setDelFlag("0");
        matcher.withMatcher("delFlag", ExampleMatcher.GenericPropertyMatchers.contains());
        Sort sort = new Sort(Sort.Direction.ASC, "departOrder");
        //创建实例
        Example<SysDepart> ex = Example.of(sysDepart, matcher);
        List<SysDepart> list = sysDepartRepository.findAll(ex, sort);
        // 调用wrapTreeDataToTreeList方法生成树状数据
        List<SysDepartTreeModel> listResult = FindsDepartsChildrenUtil.wrapTreeDataToTreeList(list);
        future.complete(new JsonArray(Json.encode(listResult)));
        handler.handle(future);
    }

    /**
     * 查询数据 添加或编辑页面对该方法发起请求,以树结构形式加载所有部门的名称,方便用户的操作
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryIdTree(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        List<DepartIdModel> idList = FindsDepartsChildrenUtil.wrapDepartIdModel();
        if (idList != null && idList.size() > 0) {
            future.complete(new JsonArray(Json.encode(idList)));
        } else {
            this.queryTreeList(null, res -> {
                if (res.succeeded()) {
                    List<DepartIdModel> idLists = FindsDepartsChildrenUtil.wrapDepartIdModel();
                    future.complete(new JsonArray(Json.encode(idLists)));
                } else {
                    future.fail(res.cause());
                }
            });

        }
        handler.handle(future);
    }

    /**
     * 保存部门数据
     *
     * @param sysDepart
     * @param username
     */
    public Boolean saveDepartData(SysDepart sysDepart, String username) {
        if (sysDepart != null && username != null) {
            if (sysDepart.getParentId() == null) {
                sysDepart.setParentId("");
            }
            sysDepart.setId(UUIDUtil.createUUID());
            String parentId = sysDepart.getParentId();
            String[] codeArray = generateOrgCode(parentId);
            sysDepart.setOrgCode(codeArray[0]);
            String orgType = codeArray[1];
            sysDepart.setOrgType(String.valueOf(orgType));
            sysDepart.setCreateTime(new Date());
            sysDepart.setDelFlag("0");
            sysDepartRepository.save(sysDepart);
            return true;
        }
        return false;
    }

    /**
     * saveDepartData 的调用方法,生成部门编码和部门类型
     *
     * @param parentId
     * @return
     */
    private String[] generateOrgCode(String parentId) {
        //update-begin--Author:Steve  Date:20190201 for：组织机构添加数据代码调整
        ExampleMatcher matcher1 = ExampleMatcher.matching(); //构建对象
        ExampleMatcher matcher2 = ExampleMatcher.matching(); //构建对象
        String[] strArray = new String[2];
        // 创建一个List集合,存储查询返回的所有SysDepart对象
        List<SysDepart> departList = new ArrayList<>();
        // 定义新编码字符串
        String newOrgCode = "";
        // 定义旧编码字符串
        String oldOrgCode = "";
        // 定义部门类型
        String orgType = "";
        // 如果是最高级,则查询出同级的org_code, 调用工具类生成编码并返回
        if (StringUtil.isNullOrEmpty(parentId)) {
            // 线判断数据库中的表是否为空,空则直接返回初始编码
            matcher1.withMatcher("parentId", ExampleMatcher.GenericPropertyMatchers.contains());
            Sort sort = new Sort(Sort.Direction.DESC, "orgCode");
            SysDepart sysDepart = new SysDepart();
            sysDepart.setParentId("");
            //创建实例
            Example<SysDepart> ex = Example.of(sysDepart, matcher1);
            departList = sysDepartRepository.findAll(ex, sort);
            if (departList == null || departList.size() == 0) {
                strArray[0] = YouBianCodeUtil.getNextYouBianCode(null);
                strArray[1] = "1";
                return strArray;
            } else {
                SysDepart depart = departList.get(0);
                oldOrgCode = depart.getOrgCode();
                orgType = depart.getOrgType();
                newOrgCode = YouBianCodeUtil.getNextYouBianCode(oldOrgCode);
            }
        } else { // 反之则查询出所有同级的部门,获取结果后有两种情况,有同级和没有同级
            // 封装查询同级的条件
            matcher2.withMatcher("parentId", ExampleMatcher.GenericPropertyMatchers.contains());
            Sort sort = new Sort(Sort.Direction.DESC, "orgCode");
            SysDepart sysDepart = new SysDepart();
            sysDepart.setParentId(parentId);
            //创建实例
            Example<SysDepart> ex = Example.of(sysDepart, matcher2);
            // 查询出同级部门的集合
            List<SysDepart> parentList = sysDepartRepository.findAll(ex, sort);
            // 查询出父级部门
            SysDepart depart = sysDepartRepository.findById(parentId).get();
            // 获取父级部门的Code
            String parentCode = depart.getOrgCode();
            // 根据父级部门类型算出当前部门的类型
            orgType = String.valueOf(Integer.valueOf(depart.getOrgType()) + 1);
            // 处理同级部门为null的情况
            if (parentList == null || parentList.size() == 0) {
                // 直接生成当前的部门编码并返回
                newOrgCode = YouBianCodeUtil.getSubYouBianCode(parentCode, null);
            } else { //处理有同级部门的情况
                // 获取同级部门的编码,利用工具类
                String subCode = parentList.get(0).getOrgCode();
                // 返回生成的当前部门编码
                newOrgCode = YouBianCodeUtil.getSubYouBianCode(parentCode, subCode);
            }
        }
        // 返回最终封装了部门编码和部门类型的数组
        strArray[0] = newOrgCode;
        strArray[1] = orgType;
        return strArray;
    }

    /**
     * 删除部门（批量删除）
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "删除部门")
    public void deleteBatch(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String id = params.getString("ids");
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            sysDepartRepository.deleteByIdEquals(ids[i]);
            sysUserDepartRepository.deleteByDepIdEquals(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    /**
     * 搜索部门
     *
     * @param params
     * @param handler
     */
    @Override
    public void searchBy(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        String name = params.getString("departName");
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); //构建对象
        SysDepart sysDepart = new SysDepart();
        sysDepart.setDelFlag("0");
        matcher.withMatcher("delFlag", ExampleMatcher.GenericPropertyMatchers.contains());
        if (oConvertUtils.isNotEmpty(name)) {
            sysDepart.setDepartName(name);
            matcher.withMatcher("departName", ExampleMatcher.GenericPropertyMatchers.startsWith());
        }
        Sort sort = new Sort(Sort.Direction.ASC, "departOrder");
        //创建实例
        Example<SysDepart> ex = Example.of(sysDepart, matcher);
        List<SysDepart> list = sysDepartRepository.findAll(ex, sort);
        List<SysDepartTreeModel> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            resultList.add(new SysDepartTreeModel(list.get(i)));
        }
        future.complete(new JsonArray(Json.encode(resultList)));
        handler.handle(future);
    }

}
