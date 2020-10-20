package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.repository.SysPermissionRepository;
import com.xby.lcdata.system.repository.SysRolePermissionRepository;
import com.xby.lcdata.system.repository.SysRolesRepository;
import com.xby.lcdata.system.repository.SysUsersRolesRepository;
import com.xby.lcdata.system.service.SysRolesAsyncService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.xby.lcdata.system.core.annotation.LogAnnotation;
import com.xby.lcdata.system.entity.SysPermission;
import com.xby.lcdata.system.entity.SysRoles;
import com.xby.lcdata.system.model.TreeModel;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.service.impl
 * @Description:
 * @Author: Sticker
 * @Date: 2019/7/31
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Component
@AsyncServiceHandler
public class SysRolesAsyncServiceImpl implements SysRolesAsyncService, BaseAsyncService {
    @Autowired
    private SysRolesRepository sysRolesRepository;
    @Autowired
    private SysPermissionRepository sysPermissionRepository;
    @Autowired
    private SysUsersRolesRepository sysUsersRolesRepository;
    @Autowired
    private SysRolePermissionRepository sysRolePermissionRepository;

    /**
     * 新增角色
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "新增角色信息")
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        SysRoles sysRoles = new SysRoles(params);
        sysRoles.setCreateTime(new Date());
        sysRolesRepository.save(sysRoles);
        future.complete("添加成功!");
        handler.handle(future);
    }

    /**
     * 分页查询角色列表
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String roleName = params.getString("roleName");
        SysRoles sysRoles = new SysRoles();
        if (oConvertUtils.isNotEmpty(roleName)) {
            sysRoles.setRoleName(roleName);
        }
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        Page<SysRoles> plist = sysRolesRepository.findAll(new Specification<SysRoles>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<SysRoles> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                Path<String> roleNameField = root.get("roleName");
                Path<Date> createTimeField = root.get("createTime");
                List<Predicate> list = new ArrayList<>();
                if (oConvertUtils.isNotEmpty(sysRoles.getRoleName())) {
                    list.add(criteriaBuilder.like(roleNameField, "%" + sysRoles.getRoleName() + "%"));
                }
                // 创建时间
                if (oConvertUtils.isNotEmpty(params.getString("startTime")) && oConvertUtils.isNotEmpty(params.getString(
                        "endTime"))) {
                    Date start = TimeUtil.convertStringToDate(params.getString("startTime"));
                    Date end = TimeUtil.convertStringToDate(params.getString("endTime"));
                    list.add(criteriaBuilder.between(createTimeField, start, end));
                }
                Predicate[] arr = new Predicate[list.size()];
                criteriaQuery.where(list.toArray(arr));
                return null;
            }
        }, pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    /**
     * 编辑角色信息
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "编辑角色信息")
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        SysRoles sysRoles = new SysRoles(params);
        sysRoles.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<SysRoles> sr = sysRolesRepository.findById(sysRoles.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            sysRoles.setUpdateTime(new Date());
            sysRolesRepository.save(sysRoles);
            //TODO 返回false说明什么？
            future.complete("修改成功!");
        }
        handler.handle(future);
    }

    /**
     * 通过id删除
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "删除角色信息")
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String[] ids = params.getString("id").split(",");
        for (int i = 0; i < ids.length; i++) {
            sysRolesRepository.deleteByIdEquals(ids[i]);
            sysUsersRolesRepository.deleteByRoleIdEquals(ids[i]);
            sysRolePermissionRepository.deleteByRoleIdEquals(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }


    /**
     * 用户角色授权功能，查询菜单权限树
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryTreeList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String roleId = params.getString("roleId", "");
        List<SysPermission> list = sysPermissionRepository.getAllByRole(roleId);
        List<String> ids = list.stream().map(sysPermission -> sysPermission.getId()).collect(Collectors.toList());
        List<TreeModel> treeList = new ArrayList<>();
        getTreeModelList(treeList, list, null);
        JsonObject resMap = new JsonObject();
        resMap.put("treeList", new JsonArray(Json.encode(treeList))); //全部树节点数据
        resMap.put("ids", ids);//全部树ids
        future.complete(resMap);
        handler.handle(future);
    }

    /**
     * 构建角色权限树
     *
     * @param treeList
     * @param metaList
     * @param temp
     */
    private void getTreeModelList(List<TreeModel> treeList, List<SysPermission> metaList, TreeModel temp) {
        for (SysPermission permission : metaList) {
            String tempPid = permission.getParentId();
            TreeModel tree = new TreeModel(permission.getId(), tempPid, permission.getName(),
                    permission.getRuleFlag(), permission.getLeaf(), true);
            if (temp == null && oConvertUtils.isEmpty(tempPid)) {
                treeList.add(tree);
                if (tree.getLeaf() != 1) {
                    getTreeModelList(treeList, metaList, tree);
                }
            } else if (temp != null && tempPid != null && tempPid.equals(temp.getKey())) {
                temp.getChildren().add(tree);
                if (tree.getLeaf() != 1) {
                    getTreeModelList(treeList, metaList, tree);
                }
            }

        }
    }

    /**
     * 查询所有角色
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        List<SysRoles> list = sysRolesRepository.findAll();
        if (list == null || list.size() <= 0) {
            future.complete(new JsonArray());
        } else {
            future.complete(new JsonArray(Json.encode(list)));
        }
        handler.handle(future);
    }

}
