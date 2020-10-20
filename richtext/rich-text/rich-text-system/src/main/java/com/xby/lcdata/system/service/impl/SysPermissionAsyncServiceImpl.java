package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.entity.*;
import com.xby.lcdata.system.repository.*;
import com.xby.lcdata.system.service.SysPermissionAsyncService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;

import com.sticker.online.core.utils.*;
import com.tb.base.common.vo.PageVo;
import com.xby.lcdata.system.core.annotation.LogAnnotation;
import com.xby.lcdata.system.model.SysPermissionTree;
import com.xby.lcdata.system.model.TreeModel;
import com.xby.lcdata.system.utils.PermissionDataUtil;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.service.impl
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/21
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Component
@AsyncServiceHandler
public class SysPermissionAsyncServiceImpl implements SysPermissionAsyncService, BaseAsyncService {
    @Autowired
    private SysPermissionRepository sysPermissionRepository;
    @Autowired
    private SysRolePermissionRepository sysRolePermissionRepository;
    @Autowired
    private SysPermissionDataRuleRepository sysPermissionDataRuleRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SRoleBtnRepository sRoleBtnRepository;
    @Autowired
    private SysUsersRolesRepository sysUsersRolesRepository;
    @Autowired
    private SysRolesRepository sysRolesRepository;
    @Autowired
    private SPermissionBtnRepository sPermissionBtnRepository;
    @Autowired
    private SysRoleDataRuleRepository sysRoleDataRuleRepository;
    @Autowired
    private SSvgRepository sSvgRepository;

    /**
     * @param params
     * @param handler
     */
    @Override
    public void list(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        String userId = JwtUtil.getUserInfo(params.getString("token")).getString("userid");
        List<SysPermission> list = getPermissionByUser(userId);
        List<SysPermissionTree> treeList = new ArrayList<>();
        getTreeList(treeList, list, null);
        future.complete(new JsonArray(Json.encode(treeList)));
        handler.handle(future);
    }

    /**
     * 根据菜单id来获取其对应的权限数据
     *
     * @param params
     * @param handler
     */
    @Override
    public void getPermRuleListByPermId(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        ExampleMatcher matcher =
                ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); //构建对象
        PageVo pageVo = new PageVo(params);
        String ruleName = params.getString("ruleName", "");
        String ruleValue = params.getString("ruleValue", "");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize());
        SysPermissionDataRule sysPermissionDataRule = new SysPermissionDataRule(params);
        //创建实例
        Example<SysPermissionDataRule> ex = Example.of(sysPermissionDataRule, matcher);
        String permissionId = params.getString("permissionId");
        if (oConvertUtils.isNotEmpty(permissionId)) {
            sysPermissionDataRule.setPermissionId(permissionId);
            matcher.withMatcher("permissionId", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if (oConvertUtils.isNotEmpty(ruleName)) {
            sysPermissionDataRule.setRuleName(ruleName);
            matcher.withMatcher("ruleName", ExampleMatcher.GenericPropertyMatchers.startsWith());
        }
        if (oConvertUtils.isNotEmpty(ruleValue)) {
            sysPermissionDataRule.setRuleValue(ruleValue);
            matcher.withMatcher("ruleValue", ExampleMatcher.GenericPropertyMatchers.startsWith());
        }
        Page<SysPermissionDataRule> sysPermissionDataRules = sysPermissionDataRuleRepository.findAll(ex, pageable);
        future.complete(new JsonObject(Json.encode(sysPermissionDataRules)));
        handler.handle(future);
    }

    /**
     * 获取全部的权限树
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryTreeList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        // 全部权限ids
        List<String> ids = new ArrayList<>();
        String userId = JwtUtil.getUserInfo(params.getString("token")).getString("userid");
        List<SysPermission> list = getPermissionByUser(userId);
        for (SysPermission sysPer : list) {
            ids.add(sysPer.getId());
        }
        List<TreeModel> treeList = new ArrayList<>();
        this.getTreeModelList(treeList, list, null);
        JsonObject resMap = new JsonObject();
        resMap.put("treeList", new JsonArray(Json.encode(treeList))); // 全部树节点数据
        resMap.put("ids", ids);// 全部树ids
        future.complete(resMap);
        handler.handle(future);
    }

    /**
     * 根据用户获取权限
     *
     * @param userId
     * @return
     */
    public List<SysPermission> getPermissionByUser(String userId) {
        List<String> sysUsersRoles = sysUsersRolesRepository.getAllByUserIdEquals(userId).stream().map(sysUsersRoles1 -> sysUsersRoles1.getRoleId()).collect(Collectors.toList());
        List<String> sysRolePermissions = sysRolePermissionRepository.getAllByRoleIdIn(sysUsersRoles).stream().map(sysRolePermission -> sysRolePermission.getPermissionId()).collect(Collectors.toList());
        return sysPermissionRepository.getAllByIdInOrderBySortNo(sysRolePermissions);
    }

    /**
     * 构建模型菜单树
     *
     * @param treeList
     * @param metaList
     * @param temp
     */
    private void getTreeModelList(List<TreeModel> treeList, List<SysPermission> metaList, TreeModel temp) {
        for (SysPermission permission : metaList) {
            String tempPid = permission.getParentId();
            TreeModel tree = new TreeModel(permission);
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
     * 添加菜单
     *
     * @param params
     * @param handler
     */
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String userId = JwtUtil.getUserInfo(params.getString("token")).getString("userid");
        SysPermission permission = new SysPermission(params);
        permission = PermissionDataUtil.intelligentProcessData(permission);
        permission.setWebUrl(params.getString("webUrl"));
        Integer iconType = params.getInteger("iconType");
        if (oConvertUtils.isNotEmpty(iconType)) {
            permission.setIconType(iconType);
        }
        permission.setLeaf(0);
        permission.setDelFlag(0);
        SysPermission result = sysPermissionRepository.save(permission);
        List<SysUsersRoles> sysUsersRoles = sysUsersRolesRepository.getAllByUserIdEquals(userId);
        List<SysRolePermission> sysRolePermission = new ArrayList<>();
        for (int i = 0; i < sysUsersRoles.size(); i++) {
            SysRolePermission sysRolePermissions = new SysRolePermission();
            sysRolePermissions.setPermissionId(result.getId());
            sysRolePermissions.setRoleId(sysUsersRoles.get(i).getRoleId());
            sysRolePermission.add(sysRolePermissions);
        }
        sysRolePermissionRepository.saveAll(sysRolePermission);
        future.complete("添加成功!");
        handler.handle(future);
    }

    /**
     * 编辑菜单
     *
     * @param params
     * @param handler
     */
    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        SysPermission permission = new SysPermission(params);
        permission = PermissionDataUtil.intelligentProcessData(permission);
        permission.setWebUrl(params.getString("webUrl"));
        sysPermissionRepository.save(permission);
        future.complete("修改成功!");
        handler.handle(future);
    }

    /**
     * 构建菜单树
     *
     * @param treeList
     * @param metaList
     * @param temp
     */
    public void getTreeList(List<SysPermissionTree> treeList, List<SysPermission> metaList, SysPermissionTree temp) {
        int i = 0;
        for (SysPermission permission : metaList) {
            String tempPid = permission.getParentId();
            i++;
            SysPermissionTree tree = new SysPermissionTree(permission);
            if (temp == null && oConvertUtils.isEmpty(tempPid)) {
                treeList.add(tree);
                if (tree.getIsLeaf() == 0) {
                    getTreeList(treeList, metaList, tree);
                }
            } else if (temp != null && tempPid != null && tempPid.equals(temp.getId())) {
                temp.getChildren().add(tree);
                if (tree.getIsLeaf() == 0) {
                    getTreeList(treeList, metaList, tree);
                }
            }
        }
    }

    /**
     * 查询所有子系统
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryListBySystemType(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        String userId = JwtUtil.getUserInfo(params.getString("token")).getString("userid");
        //当前用户拥有的角色ids
        List<SysUsersRoles> rolesList = sysUsersRolesRepository.getAllByUserIdEquals(userId);
        List<String> roleIds = rolesList.stream().map(SysUsersRoles::getRoleId).collect(Collectors.toList());
        //角色对应的所有权限
        List<SysRolePermission> sysRolePermissions = sysRolePermissionRepository.findAll();
        List<SysRolePermission> rolePermissionArrayList = sysRolePermissions.stream().filter(p -> roleIds.contains(p.getRoleId())).collect(Collectors.toList());
        List<SysPermission> sysPermissions = sysPermissionRepository.queryByMenuType(3);
        //获取角色对应的权限的id
        List<String> rolePermissionIds = rolePermissionArrayList.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
        //获取角色拥有的子系统权限
        List<SysPermission> resultPermissionsList = sysPermissions.stream().filter(p -> rolePermissionIds.contains(p.getId())).collect(Collectors.toList());
        future.complete(new JsonArray(Json.encode(resultPermissionsList)));
        handler.handle(future);
    }

    /**
     * 获取权限JSON数组
     *
     * @param jsonArray
     * @param allList
     */
    public void getAllAuthJsonArray(JsonArray jsonArray, List<SysPermission> allList) {
        for (SysPermission permission : allList) {
            JsonObject json = new JsonObject();
            json.put("action", permission.getPerms());
            json.put("status", permission.getStatus());
            json.put("type", permission.getPermsType());
            json.put("describe", permission.getName());
            jsonArray.add(json);
        }
    }

    /**
     * 获取权限JSON数组
     *
     * @param jsonArray
     * @param metaList
     */
    public void getAuthJsonArray(JsonArray jsonArray, List<SysPermission> metaList) {
        for (SysPermission permission : metaList) {
            if (permission.getMenuType() == null) {
                continue;
            }
            if (permission.getMenuType() == 2 && "1".equals(permission.getStatus())) {
                JsonObject json = new JsonObject();
                json.put("action", permission.getPerms());
                json.put("type", permission.getPermsType());
                json.put("describe", permission.getName());
                jsonArray.add(json);
            }
        }
    }

    /**
     * 查询用户拥有的菜单权限\按钮权限、子系统权限（根据TOKEN）
     *
     * @param params
     * @param handler
     */
    @Override
    public void getUserPermissionByTokenOfSystem(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String token = params.getString("token");
        String pid = params.getString("id");
        String username = JwtUtil.getUsername(token);
        String userId = JwtUtil.getUserInfo(token).getString("userid");
        List<SysUsersRoles> sysRoles = sysUsersRolesRepository.getAllByUserIdEquals(userId);
        boolean isAdmin = false;
        for (int i = 0; i < sysRoles.size(); i++) {
            SysUsersRoles sysUsersRoles = sysRoles.get(i);
            Optional<SysRoles> sysRolesOptional = sysRolesRepository.findById(sysUsersRoles.getRoleId());
            if (sysRolesOptional.isPresent()) {
                if (sysRolesOptional.get().getRoleCode().equals("admin")) {
                    isAdmin = true;
                    break;
                }
            }
        }
        List<SysPermission> metaList = new ArrayList<SysPermission>();
        //超级管理员不做权限控制
        if (isAdmin) {
            metaList = sysPermissionRepository.queryAllByDelFlagEqualsOrderBySortNo(0);
        } else {
            metaList = sysPermissionRepository.queryByUserOrderBySortNo(username);
        }
        PermissionDataUtil.addIndexPage(metaList);
        JsonObject json = new JsonObject();
        JsonArray menujsonArray = new JsonArray();
        this.getPermissionJsonArray(menujsonArray, metaList, null);
        JsonArray authjsonArray = new JsonArray();
        this.getAuthJsonArray(authjsonArray, metaList);
        //查询所有的权限
        List<SysPermission> allAuthList = sysPermissionRepository.queryByMenuType(2);
        JsonArray allauthjsonArray = new JsonArray();
        this.getAllAuthJsonArray(allauthjsonArray, allAuthList);
        menujsonArray.forEach(item -> {
            JsonObject j = (JsonObject) item;
            if (j.getString("id").equals(pid)) {
                json.put("menu", j.getJsonArray("children"));
            }
        });
        if (json.getJsonArray("menu") == null) {
            json.put("menu", new JsonArray());
        }
        // 获取按钮权限
        json.put("auth", authjsonArray);
        json.put("allAuth", allauthjsonArray);
        json.put("btnAuth", getBtn(userId));
        json.put("btnAllAuth", getBtn(userId));
        future.complete(new JsonObject(Json.encode(json)));
        handler.handle(future);
    }

    /**
     * 获取菜单JSON数组
     *
     * @param jsonArray
     * @param metaList
     * @param parentJson
     */
    public void getPermissionJsonArray(JsonArray jsonArray, List<SysPermission> metaList, JsonObject parentJson) {
        for (SysPermission permission : metaList) {
            if (permission.getMenuType() == null) {
                continue;
            }
            String tempPid = permission.getParentId();
            JsonObject json = getPermissionJsonObject(permission);
            if (json == null) {
                continue;
            }
            if (parentJson == null && oConvertUtils.isEmpty(tempPid)) {
                jsonArray.add(json);
                if (permission.getLeaf() == 0) {
                    getPermissionJsonArray(jsonArray, metaList, json);
                }
            } else if (parentJson != null && oConvertUtils.isNotEmpty(tempPid) && tempPid.equals(parentJson.getString("id"))) {
                // 类型(2：按钮 )
                if (permission.getMenuType() == 2) {
                    JsonObject metaJson = parentJson.getJsonObject("meta");
                    if (metaJson.containsKey("permissionList")) {
                        metaJson.getJsonArray("permissionList").add(json);
                    } else {
                        JsonArray permissionList = new JsonArray();
                        permissionList.add(json);
                        metaJson.put("permissionList", permissionList);
                    }
                    // 类型( 0：一级菜单 1：子菜单 3：子系统 )
                } else if (permission.getMenuType() == 1 || permission.getMenuType() == 0 || permission.getMenuType() == 3) {
                    if (parentJson.containsKey("children")) {
                        parentJson.getJsonArray("children").add(json);
                    } else {
                        JsonArray children = new JsonArray();
                        children.add(json);
                        parentJson.put("children", children);
                    }
                    if (permission.getLeaf() == 0) {
                        getPermissionJsonArray(jsonArray, metaList, json);
                    }
                }
            }
        }
    }

    /**
     * 获取菜单JSON对象
     *
     * @param permission
     * @return
     */
    private JsonObject getPermissionJsonObject(SysPermission permission) {
        JsonObject json = new JsonObject();
        // 类型(0：一级菜单 1：子菜单 2：按钮 3:子系统)
        if (permission.getMenuType() == 2) {
            //json.put("action", permission.getPerms());
            //json.put("type", permission.getPermsType());
            //json.put("describe", permission.getName());
            return null;
        } else if (permission.getMenuType() == 0 || permission.getMenuType() == 1 || permission.getMenuType() == 3) {
            json.put("id", permission.getId());
            json.put("route", permission.getRoute());// 表示生成路由
            if (isWWWHttpUrl(permission.getUrl())) {
                json.put("path", MD5Util.MD5Encode(permission.getUrl(), "utf-8"));
            } else {
                json.put("path", permission.getUrl());
            }
            // 重要规则：路由name (通过URL生成路由name,路由name供前端开发，页面跳转使用)
            if (oConvertUtils.isNotEmpty(permission.getComponentName())) {
                json.put("name", permission.getComponentName());
            } else {
                json.put("name", urlToRouteName(permission.getUrl()));
            }
            // 是否隐藏路由，默认都是显示的
            if (permission.getHidden() == 1) {
                json.put("hidden", true);
            }
            // 聚合路由
            if (permission.getAlwaysShow() == 1) {
                json.put("alwaysShow", true);
            }
            json.put("component", permission.getComponent());
            JsonObject meta = new JsonObject();
            // 默认所有的菜单都加路由缓存，提高系统性能
            meta.put("keepAlive", "true");
            meta.put("title", permission.getName());
            if (oConvertUtils.isEmpty(permission.getParentId())) {
                // 一级菜单跳转地址
                json.put("redirect", permission.getRedirect());
                json.put("webUrl", permission.getWebUrl());
                if (oConvertUtils.isNotEmpty(permission.getIcon())) {
                    meta.put("icon", permission.getIcon());
                    meta.put("iconType", permission.getIconType());
                }
            } else {
                json.put("redirect", permission.getRedirect());
                json.put("webUrl", permission.getWebUrl());
                if (oConvertUtils.isNotEmpty(permission.getIcon())) {
                    meta.put("icon", permission.getIcon());
                    meta.put("iconType", permission.getIconType());
                }
            }
            if (isWWWHttpUrl(permission.getUrl())) {
                meta.put("url", permission.getUrl());
            }
            json.put("meta", meta);
        }
        return json;
    }

    /**
     * 查询角色授权
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryRolePermission(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        SysRolePermission sysPermission = new SysRolePermission(params);
        matcher.withMatcher("roleId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<SysRolePermission> ex = Example.of(sysPermission, matcher);
        List<SysRolePermission> list = sysRolePermissionRepository.findAll(ex);
        List<String> ids =
                list.stream().map(SysRolePermission -> String.valueOf(SysRolePermission.getPermissionId())).collect(Collectors.toList());
        future.complete(new JsonArray(Json.encode(ids)));
        handler.handle(future);
    }

    /**
     * 保存角色授权
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "保存角色授权")
    public void saveRolePermission(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String roleId = params.getString("roleId");
        String permissionIds = params.getString("permissionIds");
        this.saveRolePermission(roleId, permissionIds);
        future.complete(new JsonObject().put("data", "保存成功!"));
        handler.handle(future);
    }

    /**
     * 保存授权 将上次的权限和这次作比较 差异处理提高效率
     *
     * @param roleId
     * @param permissionIds
     */
    public void saveRolePermission(String roleId, String permissionIds) {
        sysRolePermissionRepository.deleteByRoleIdEquals(roleId);
        String[] permissionId = permissionIds.split(",");
        List<SysRolePermission> list = new ArrayList<SysRolePermission>();
        for (int i = 0; i < permissionId.length; i++) {
            SysRolePermission rolepms = new SysRolePermission(roleId, permissionId[i]);
            list.add(rolepms);
        }
        sysRolePermissionRepository.saveAll(list);
    }

    /**
     * 保存按钮授权
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "保存按钮授权")
    public void addRoleBtn(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String roleId = params.getString("roleId");
        String permissionId = params.getString("permissionId");
        sRoleBtnRepository.deleteByRoleIdEqualsAndPermissionIdEquals(roleId, permissionId);
        JsonArray btnId = params.getJsonArray("btnId");
        List<SysRoleBtn> sysRoleBtns = new ArrayList<>();
        for (int i = 0; i < btnId.size(); i++) {
            SysRoleBtn sysRoleBtn = new SysRoleBtn(roleId, btnId.getString(i), permissionId, TimeUtil.getNow());
            sysRoleBtns.add(sysRoleBtn);
        }
        sRoleBtnRepository.saveAll(sysRoleBtns);
        future.complete(new JsonObject().put("data", "保存成功!"));
        handler.handle(future);
    }

    /**
     * 从diff中找出main中没有的元素
     *
     * @param main
     * @param diff
     */
    private List<String> getDiff(String main, String diff) {
        if (oConvertUtils.isEmpty(diff)) {
            return null;
        }
        if (oConvertUtils.isEmpty(main)) {
            return Arrays.asList(diff.split(","));
        }
        String[] mainArr = main.split(",");
        String[] diffArr = diff.split(",");
        Map<String, Integer> map = new HashMap<>();
        for (String string : mainArr) {
            map.put(string, 1);
        }
        List<String> res = new ArrayList<String>();
        for (String key : diffArr) {
            if (oConvertUtils.isNotEmpty(key) && !map.containsKey(key)) {
                res.add(key);
            }
        }
        return res;
    }

    /**
     * 判断是否外网URL 例如： http://localhost:8080/jeecg-boot/swagger-ui.html#/ 支持特殊格式： {{
     * window._CONFIG['domianURL'] }}/druid/ {{ JS代码片段 }}，前台解析会自动执行JS代码片段
     *
     * @return
     */
    private boolean isWWWHttpUrl(String url) {
        if (url != null && (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("{{"))) {
            return true;
        }
        return false;
    }

    /**
     * 通过URL生成路由name（去掉URL前缀斜杠，替换内容中的斜杠‘/’为-） 举例： URL = /isystem/role RouteName =
     * isystem-role
     *
     * @return
     */
    private String urlToRouteName(String url) {
        if (oConvertUtils.isNotEmpty(url)) {
            if (url.startsWith("/")) {
                url = url.substring(1);
            }
            url = url.replace("/", "-");
            // 特殊标记
            url = url.replace(":", "@");
            return url;
        } else {
            return null;
        }
    }

    /**
     * 批量删除
     */
    @Override
    @LogAnnotation(operationType = "批量删除菜单")
    public void deleteBatch(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String id = params.getString("ids");
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            sysPermissionRepository.deleteByIdEquals(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    @LogAnnotation(operationType = "删除菜单")
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String id = params.getString("id", "");
        sysPermissionRepository.deleteByIdEquals(id);
        sysPermissionRepository.deleteByParentIdEquals(id);
        sysRolePermissionRepository.deleteByPermissionIdEquals(id);
        sPermissionBtnRepository.deleteByPermissionIdEquals(id);
        future.complete("删除成功!");
        handler.handle(future);
    }

    /**
     * 添加菜单权限数据
     */
    @Override
    @LogAnnotation(operationType = "新增菜单数据权限")
    public void addPermissionRule(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String userName = JwtUtil.getUserNameByToken(params.getString("token"));
        SysPermissionDataRule sysPermissionDataRule = new SysPermissionDataRule(params);
        sysPermissionDataRule.setCreateBy(userName);
        sysPermissionDataRule.setCreateTime(TimeUtil.getDateNow());
        sysPermissionDataRule.setStatus("1");
        sysPermissionDataRuleRepository.save(sysPermissionDataRule);
        future.complete("添加成功!");
        handler.handle(future);
    }

    /**
     * 编辑菜单权限数据
     */
    @Override
    @LogAnnotation(operationType = "编辑菜单数据权限")
    public void editPermissionRule(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String userName = JwtUtil.getUserNameByToken(params.getString("token"));
        sysPermissionDataRuleRepository.modifyDataRule(params.getString("permissionId"), params.getString(
                "ruleName"), params.getString("ruleColumn"),
                params.getString("ruleConditions"), params.getString("ruleValue"), params.getString("status")
                , params.getString("id"),
                userName, TimeUtil.getDateNow());
        future.complete("修改成功!");
        handler.handle(future);
    }

    /**
     * 删除菜单权限数据
     */
    @Override
    @LogAnnotation(operationType = "删除菜单数据权限")
    public void deletePermissionRule(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String id = params.getString("id");
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            sysPermissionDataRuleRepository.deleteByIdEquals(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    /**
     * 查询菜单权限数据
     */
    @Override
    public void queryPermissionRule(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        Page<SysPermissionDataRule> sysPermissionDataRulePage = sysPermissionDataRuleRepository.findAll(pageable);
        future.complete(new JsonObject(Json.encode(sysPermissionDataRulePage)));
        handler.handle(future);
    }

    /**
     * 根据id获取菜单
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String id = params.getString("id");
        if (id != null) {
            future.complete(new JsonObject(Json.encode(sysPermissionRepository.queryByIdEquals(id))));
        } else {
            future.complete(new JsonObject().put("error", "id不能为空"));
        }
        handler.handle(future);
    }

    /**
     * 根据用户登录Token获取按钮权限
     *
     * @param userId
     */
    public JsonArray getBtn(String userId) {
        List<SysUsersRoles> sysRoles = sysUsersRolesRepository.getAllByUserIdEquals(userId);
        List<SysRoleBtn> sysRoleBtns = sRoleBtnRepository.findAll();
        List<SysPermissionBtn> sysPermissionBtnList = sPermissionBtnRepository.findAll();
        List<SysRoleBtn> sysRoleBtnsList = new ArrayList<>();
        for (int i = 0; i < sysRoles.size(); i++) {
            SysUsersRoles sysUsersRoles = sysRoles.get(i);
            Optional<SysRoles> sysRolesOptional = sysRolesRepository.findById(sysUsersRoles.getRoleId());
            if (sysRolesOptional.isPresent()) {
                for (int k = 0; k < sysRoleBtns.size(); k++) {
                    if (sysRolesOptional.get().getId().equals(sysRoleBtns.get(k).getRoleId())) {
                        sysRoleBtnsList.add(sysRoleBtns.get(k));
                    }
                }
            }
        }
        List<SysPermissionBtn> resultList = new ArrayList<>();
        for (int i = 0; i < sysPermissionBtnList.size(); i++) {
            for (int k = 0; k < sysRoleBtnsList.size(); k++) {
                if (sysPermissionBtnList.get(i).getId().equals(sysRoleBtnsList.get(k).getBtnId())) {
                    resultList.add(sysPermissionBtnList.get(i));
                }
            }
        }
        return new JsonArray(Json.encode(resultList));
    }

    /**
     * 添加数据权限
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "添加数据权限")
    public void addDataRule(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        List<SysRoleDataRule> list = new ArrayList<>();
        String roleId = params.getString("roleId");
        Integer isSql = params.getInteger("isSql");
        String tableId = params.getString("tableId");
        JsonArray field = params.getJsonArray("field");
        for (int i = 0; i < field.size(); i++) {
            JsonObject fieldItem = field.getJsonObject(i);
            list.add(new SysRoleDataRule(roleId, fieldItem.getString("fieldName"), fieldItem.getString("symbol"),
                    fieldItem.getString("value"), TimeUtil.getNow(), tableId, isSql));
        }
        sysRoleDataRuleRepository.saveAll(list);
        future.complete("添加成功!");
        handler.handle(future);
    }

    /**
     * 修改数据权限
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "修改数据权限")
    public void editDataRule(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        List<SysRoleDataRule> list = new ArrayList<>();
        String roleId = params.getString("roleId");
        String tableId = params.getString("tableId");
        JsonArray field = params.getJsonArray("field");
        Integer isSql = params.getInteger("isSql");
        for (int i = 0; i < field.size(); i++) {
            JsonObject fieldItem = field.getJsonObject(i);
            String id = fieldItem.getString("id");
            String createTime = fieldItem.getString("createTime");
            SysRoleDataRule sysRoleDataRule = new SysRoleDataRule(roleId, fieldItem.getString("fieldName"),
                    fieldItem.getString("symbol"),
                    fieldItem.getString("value"), TimeUtil.getNow(), tableId, isSql);
            sysRoleDataRule.setId(id);
            sysRoleDataRule.setCreateTime(createTime);
            list.add(sysRoleDataRule);
        }
        sysRoleDataRuleRepository.saveAll(list);
        future.complete("修改成功!");
        handler.handle(future);
    }

    /**
     * 删除数据权限
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "删除数据权限")
    public void delDataRule(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        sysRoleDataRuleRepository.deleteByIdEquals(params.getString("roleId"));
        future.complete("删除成功!");
        handler.handle(future);
    }

    /**
     * 获取数据权限
     *
     * @param params
     * @param handler
     */
    @Override
    public void getDataRule(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        SysRoleDataRule sysRoleDataRule = new SysRoleDataRule();
        sysRoleDataRule.setRoleId(params.getString("roleId"));
        exampleMatcher.withMatcher("roleId", ExampleMatcher.GenericPropertyMatchers.contains());
        sysRoleDataRule.setTableId(params.getString("tableId"));
        exampleMatcher.withMatcher("tableId", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<SysRoleDataRule> example = Example.of(sysRoleDataRule, exampleMatcher);
        List<SysRoleDataRule> list = sysRoleDataRuleRepository.findAll(example);
        future.complete(new JsonArray(Json.encode(list)));
        handler.handle(future);
    }

    /**
     * 保存图标
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "新增SVG图标信息")
    public void saveSvg(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        SSvg sSvg = new SSvg();
        Integer type = params.getInteger("type");
        if (oConvertUtils.isNotEmpty(type)) {
            sSvg.setType(type);
        }
        sSvg.setUrl(params.getString("url"));
        sSvgRepository.save(sSvg);
        future.complete("保存成功!");
        handler.handle(future);
    }

    /**
     * 获取图标列表
     *
     * @param params
     * @param handler
     */
    @Override
    public void getSvgList(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        future.complete(new JsonArray(Json.encode(sSvgRepository.findAll())));
        handler.handle(future);
    }

    /**
     * 删除SVG图标
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "删除SVG图标信息")
    public void delSvg(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String id = params.getString("id");
        sSvgRepository.deleteByIdEquals(id);
        future.complete("删除成功!");
        handler.handle(future);
    }

}
