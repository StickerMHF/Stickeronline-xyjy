package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.entity.SysDepart;
import com.xby.lcdata.system.entity.SysUserDepart;
import com.xby.lcdata.system.entity.SysUsers;
import com.xby.lcdata.system.entity.SysUsersRoles;
import com.xby.lcdata.system.repository.SysDepartRepository;
import com.xby.lcdata.system.repository.SysUserDepartRepository;
import com.xby.lcdata.system.repository.SysUserRepository;
import com.xby.lcdata.system.repository.SysUsersRolesRepository;
import com.xby.lcdata.system.service.SysUsersAsyncService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.utils.*;
import com.tb.base.common.vo.PageVo;
import com.xby.lcdata.system.core.annotation.LogAnnotation;
import com.xby.lcdata.system.model.DepartIdModel;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.*;
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
public class SysUsersAsyncServiceImpl implements SysUsersAsyncService {
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysUsersRolesRepository sysUsersRolesRepository;
    @Autowired
    private SysUserDepartRepository sysUserDepartRepository;
    @Autowired
    private SysDepartRepository sysDepartRepository;

    /**
     * 用户列表（分页、条件查询）
     *
     * @param params
     * @param handler
     */
    @Override
    public void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String userName = params.getString("username", "");
        String email = params.getString("email", "");
        String phone = params.getString("phone", "");
        String status = params.getString("status");
        String sex = params.getString("sex");
        PageVo pageVo = new PageVo(params);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        SysUsers sysUsers = new SysUsers();
        sysUsers.setDelFlag(0);
        ExampleMatcher.GenericPropertyMatchers.contains();
        if (oConvertUtils.isNotEmpty(userName)) {
            sysUsers.setUsername(userName);
            exampleMatcher.withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith());
        }
        if (oConvertUtils.isNotEmpty(sex)) {
            sysUsers.setSex(Integer.valueOf(sex));
            exampleMatcher.withMatcher("sex", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if (oConvertUtils.isNotEmpty(email)) {
            sysUsers.setEmail(email);
            exampleMatcher.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.startsWith());
        }
        if (oConvertUtils.isNotEmpty(phone)) {
            sysUsers.setPhone(phone);
            exampleMatcher.withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.startsWith());
        }
        if (oConvertUtils.isNotEmpty(status)) {
            sysUsers.setStatus(Integer.valueOf(status));
            exampleMatcher.withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example<SysUsers> example = Example.of(sysUsers, exampleMatcher);
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        Page<SysUsers> page = sysUserRepository.findAll(example, pageable);
        future.complete(new JsonObject(Json.encode(page)));
        handler.handle(future);
    }

    /**
     * 新增用户信息
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "新增用户信息")
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String selectedRoles = params.getString("selectedroles");
        if (null == sysUserRepository.queryByUsernameEquals(params.getString("username"))) {
            SysUsers user = new SysUsers(params);
            if (oConvertUtils.isEmpty(params.getString("id"))) {
                user.setId(UUIDUtil.createUUID());
            }
            user.setCreateTime(TimeUtil.getDateNow());//设置创建时间
            String salt = oConvertUtils.randomGen(8);
            user.setSalt(salt);
            user.setBirthday(params.getString("birthday"));
            String passwordEncode = PasswordUtil.encrypt(user.getUsername(), user.getPassword(), salt);
            user.setPassword(passwordEncode);
            user.setStatus(1);
            user.setDelFlag(0);
            user.setUpdateTime(TimeUtil.getNow());
            SysUsers sysUsers = sysUserRepository.save(user);
            addUserWithRole(sysUsers, selectedRoles);
            future.complete("添加成功!");
        } else {
            future.fail("用户账户已存在!");
        }
        handler.handle(future);
    }

    /**
     * 根据用户ID查询拥有的角色id
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryUserRole(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        SysUsersRoles sysUsersRoles = new SysUsersRoles();
        sysUsersRoles.setUserId(params.getString("userid"));
        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<SysUsersRoles> ex = Example.of(sysUsersRoles, matcher);
        List<SysUsersRoles> userRole = sysUsersRolesRepository.findAll(ex);
        if (userRole == null || userRole.size() <= 0) {
            future.complete(new JsonArray());
        } else {
            List<String> list = userRole.stream().map(sysUsersRoles1 -> sysUsersRoles1.getRoleId()).collect(Collectors.toList());
            future.complete(new JsonArray(Json.encode(list)));
        }
        handler.handle(future);
    }

    /**
     * 指定用户绑定角色
     *
     * @param user
     * @param roles
     * @return
     */
    @LogAnnotation(operationType = "指定用户绑定角色")
    public boolean addUserWithRole(SysUsers user, String roles) {
        if (oConvertUtils.isNotEmpty(roles)) {
            String[] arr = roles.split(",");
            sysUsersRolesRepository.deleteAllByUserIdEquals(user.getId());
            for (String roleId : arr) {
                SysUsersRoles userRole = new SysUsersRoles(user.getId(), roleId);
                sysUsersRolesRepository.save(userRole);
            }
            return true;
        }
        return false;
    }

    /**
     * 获取用户所属部门
     *
     * @param params
     * @param handler
     */
    @Override
    public void userDepartList(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        SysUserDepart sysUserDepart = new SysUserDepart();
        sysUserDepart.setUserId(params.getString("userId"));
        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<SysUserDepart> ex = Example.of(sysUserDepart, matcher);
        List<SysUserDepart> userDepList = sysUserDepartRepository.findAll(ex);
        if (userDepList == null && userDepList.size() <= 0) {
            future.complete(new JsonArray());
        } else {
            List<SysDepart> list = sysDepartRepository.getAllByIdIn(userDepList.stream().map(sysUserDepart1 -> sysUserDepart1.getDepId()).collect(Collectors.toList()));
            List<DepartIdModel> departIdModels = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                departIdModels.add(new DepartIdModel().convertByUserDepart(list.get(i)));
            }
            future.complete(new JsonArray(Json.encode(departIdModels)));
        }
        handler.handle(future);
    }

    /**
     * 校验用户账号是否唯一<br>
     * 可以校验其他 需要检验什么就传什么。。。
     *
     * @param params
     * @param handler
     */
    @Override
    public void checkOnlyUser(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        SysUsers sysUsers = new SysUsers(params);
        String id = params.getString("id", "");
        SysUsers oldUser = null;
        if (oConvertUtils.isNotEmpty(id)) {
            oldUser = sysUserRepository.findById(id).get();
        } else {
            sysUsers.setId(null);
        }
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        matcher.withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<SysUsers> ex = Example.of(sysUsers, matcher);

        //通过传入信息查询新的用户信息
        Optional<SysUsers> newUser = sysUserRepository.findOne(ex);
        if (!newUser.equals(Optional.empty())) {
            //如果根据传入信息查询到用户了，那么就需要做校验了。
            if (oldUser == null) {
                future.fail("用户账号已存在");
            } else if (!id.equals(newUser.get().getId())) {
                future.fail("用户账号已存在");
            } else {
                future.complete(new JsonObject(Json.encode(sysUsers)));
            }
        } else {
            future.complete(new JsonObject(Json.encode(sysUsers)));
        }
        handler.handle(future);
    }

    /**
     * 编辑用户
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "编辑用户信息")
    public void edit(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        SysUsers sysUser = sysUserRepository.findById(params.getString("id")).get();
        if (sysUser == null) {
            future.fail("未找到对应实体");
        } else {
            SysUsers user = new SysUsers(params);
            user.setUpdateBy(JwtUtil.getUsername(params.getString("token")));
            user.setUpdateTime(TimeUtil.getNow());
            user.setBirthday(params.getString("birthday"));
//                user.setCreateTime(TimeUtil.convertTempstampToString(params.getLong("createTime")));
            user.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
            user.setPassword(sysUser.getPassword());
            String roles = params.getString("selectedroles");
            String depIds = params.getString("selecteddeparts");
            this.editUserWithRole(user, roles);
            this.editSysUserWithDepart(user, depIds);
            future.complete(new JsonObject(Json.encode(user)));
        }
        handler.handle(future);
    }

    /**
     * 修改用户部门信息
     *
     * @param params
     * @param handler
     */
    @Override
    public void editUDepartIds(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        if (oConvertUtils.isNotEmpty(params.getString("userId"))) {
            String userId = params.getString("userId");
            JsonArray departIdList = params.getJsonArray("departIdList");
            addSysUseWithDepart(sysUserRepository.findById(userId).get(), departIdList);
            future.complete("修改成功!");
        } else {
            future.complete("未选择部门!");
        }
        handler.handle(future);
    }

    @Override
    public void addUDepartIds(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        if (oConvertUtils.isNotEmpty(params.getString("userId"))) {
            String userId = params.getString("userId");
            JsonArray departIdList = params.getJsonArray("departIdList");
            addSysUseWithDepart(sysUserRepository.findById(userId).get(), departIdList);
            future.complete("添加成功!");
        } else {
            future.complete("未选择部门!");
        }
        handler.handle(future);
    }

    /**
     * 指定用户添加部门信息
     *
     * @param user
     * @param depId
     * @return
     */
    private boolean addSysUseWithDepart(SysUsers user, JsonArray depId) {
        sysUserDepartRepository.deleteAllByUserIdEquals(user.getId());
        for (int i = 0; i < depId.size(); i++) {
            SysUserDepart sysUserDepart = new SysUserDepart();
            sysUserDepart.setUserId(user.getId());
            sysUserDepart.setDepId(depId.getString(i));
            SysUserDepart sysUserDepart1 = sysUserDepartRepository.save(sysUserDepart);
            System.out.println(sysUserDepart1.getId());
        }
        return true;
    }

    /**
     * 根据用户id修改部门信息
     *
     * @param user
     * @param depId
     */
    public boolean editSysUserWithDepart(SysUsers user, String depId) {
        sysUserRepository.save(user);
        //先删后加
        sysUserDepartRepository.deleteAllByUserIdEquals(user.getId());
        if (oConvertUtils.isNotEmpty(depId)) {
            String[] depIds = depId.split(",");
            for (int i = 0; i < depIds.length; i++) {
                SysUserDepart sysUserDepart = new SysUserDepart(user.getId(), depIds[i]);
                sysUserDepartRepository.save(sysUserDepart);
            }
            return true;
        }
        return false;
    }

    /**
     * 根据用户id修改角色关系
     *
     * @param user
     * @param roles
     */
    @LogAnnotation(operationType = "修改指定用户的绑定角色")
    public void editUserWithRole(SysUsers user, String roles) {
        sysUserRepository.save(user);
        //先删后加
        sysUsersRolesRepository.deleteAllByUserIdEquals(user.getId());
        if (oConvertUtils.isNotEmpty(roles)) {
            String[] arr = roles.split(",");
            for (String roleId : arr) {
                SysUsersRoles userRole = new SysUsersRoles(user.getId(), roleId);
                sysUsersRolesRepository.save(userRole);
            }
        }
    }

    /**
     * 删除用户
     *
     * @param params
     */
    @Override
    @LogAnnotation(operationType = "删除用户信息")
    public void del(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String id = params.getString("id");
        sysUserRepository.updateDelFlag(1, id);
        sysUsersRolesRepository.deleteAllByUserIdEquals(id);
        future.complete("删除成功!");
        handler.handle(future);
    }

    /**
     * 批量删除用户信息
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "批量删除用户信息")
    public void deleteBatch(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String[] id = params.getString("ids").split(",");
        for (int i = 0; i < id.length; i++) {
            sysUserRepository.updateDelFlag(1, id[i]);
            sysUsersRolesRepository.deleteAllByUserIdEquals(id[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    /**
     * 解冻冻结用户
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "解冻/冻结用户信息")
    public void freezeThawUser(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String id = params.getString("id");
        SysUsers sysUsers = sysUserRepository.findById(id).get();
        if (sysUsers.getStatus() == 1) {
            sysUserRepository.freezeThawUser(2, id);
            future.complete("冻结成功!");
        } else {
            sysUserRepository.freezeThawUser(1, id);
            future.complete("解冻成功!");
        }
        handler.handle(future);
    }

    /**
     * 根据用户id查询
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryUserById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        future.complete(new JsonObject().put("data", sysUserRepository.findById(params.getString("id")).get()));
        handler.handle(future);
    }

    /**
     * 修改密码
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "修改密码")
    public void modifyPwd(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String token = params.getString("token");
        String newPassword = params.getString("password");
        String oldPassword = params.getString("oldPassword");
        String username = params.getString("username");
        SysUsers sysUsers = sysUserRepository.getUserByName(username);
        if (sysUsers != null) {
            if (oConvertUtils.isNotEmpty(oldPassword)) {
                if (oConvertUtils.isEmpty(username)) {
                    username = JwtUtil.getUserNameByToken(token);
                }
                if (sysUsers.getPassword().equals(PasswordUtil.encrypt(username, oldPassword, sysUsers.getSalt()))) {
                    String password = PasswordUtil.encrypt(username, newPassword, sysUsers.getSalt());
                    sysUserRepository.mofifyPwd(password, username);
                    future.complete("修改密码成功!");
                } else {
                    future.fail("原密码不正确!");
                }
            } else {
                String password = PasswordUtil.encrypt(username, newPassword, sysUsers.getSalt());
                sysUserRepository.mofifyPwd(password, username);
                future.complete("修改密码成功!");
            }
        } else {
            future.fail("用户不存在!");
        }
        handler.handle(future);
    }

    /**
     * 指定角色添加用户
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "指定角色绑定用户")
    public void addSysUserRole(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String roleId = params.getString("roleId");
        JsonArray userId = params.getJsonArray("userId");
        for (int i = 0; i < userId.size(); i++) {
            SysUsersRoles sysUsersRoles = new SysUsersRoles();
            sysUsersRoles.setId(UUIDUtil.createUUID());
            sysUsersRoles.setRoleId(roleId);
            sysUsersRoles.setUserId(userId.getString(i));
            sysUsersRolesRepository.save(sysUsersRoles);
        }
        future.complete("添加成功!");
        handler.handle(future);
    }

    /**
     * 删除指定角色的用户关系
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "删除指定角色绑定的用户信息")
    public void delUserRole(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String roleId = params.getString("roleId");
        String userId = params.getString("userId");
        if (userId != "") {
            String[] ids = userId.split(",");
            for (int i = 0; i < ids.length; i++) {
                sysUsersRolesRepository.deleteByUserIdEqualsAndRoleIdEquals(ids[i], roleId);
            }
        } else {
            sysUsersRolesRepository.deleteByRoleIdEquals(roleId);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    /**
     * 部门用户列表
     *
     * @param params
     * @param handler
     */
    @Override
    public void departUserList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String depId = params.getString("depId");
        PageVo pageVo = new PageVo(params);
        if (oConvertUtils.isNotEmpty(depId)) {
            Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
            Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
            List<String> userIds = sysUserDepartRepository.queryAllByDepIdEquals(depId).
                    stream().map(sysUserDepart -> sysUserDepart.getUserId()).collect(Collectors.toList());
            Page<SysUsers> sysUsersList = sysUserRepository.getAllByIdIn(userIds, pageable);
            future.complete(new JsonObject(Json.encode(sysUsersList)));
        }
        handler.handle(future);
    }

    /**
     * 给指定部门添加对应的用户
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "指定部门添加用户")
    public void editSysDepartWithUser(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String depId = params.getString("depId");
        JsonArray userId = params.getJsonArray("userIdList");
        for (int i = 0; i < userId.size(); i++) {
            SysUserDepart sysUserDepart = new SysUserDepart();
            sysUserDepart.setDepId(depId);
            sysUserDepart.setUserId(userId.getString(i));
            sysUserDepartRepository.save(sysUserDepart);
        }
        future.complete("添加成功!");
        handler.handle(future);
    }

    /**
     * 批量删除指定机构的用户关系
     *
     * @param params
     * @param handler
     */
    @Override
    @LogAnnotation(operationType = "批量删除指定部门的用户关系")
    public void deleteUserInDepartBatch(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String depId = params.getString("depId");
        String userId = params.getString("userId");
        if (oConvertUtils.isNotEmpty(userId)) {
            String[] ids = userId.split(",");
            for (int i = 0; i < ids.length; i++) {
                sysUserDepartRepository.deleteByDepIdEqualsAndUserIdEquals(depId, ids[i]);
            }
        } else {
            sysUserDepartRepository.deleteByDepIdEquals(depId);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    /**
     * 获取指定角色的用户列表
     *
     * @param params
     * @param handler
     */
    @Override
    public void getUserByRole(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String roleId = params.getString("roleId");
        if (oConvertUtils.isNotEmpty(roleId)) {
            Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
            Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
            List<String> userIdList = sysUsersRolesRepository.getAllByRoleIdEquals(roleId).stream().map(sysUsersRoles -> sysUsersRoles.getUserId()).collect(Collectors.toList());
            future.complete(new JsonObject(Json.encode(sysUserRepository.getAllByIdIn(userIdList, pageable))));
        }
        handler.handle(future);
    }
}
