package com.xby.lcdata.system.handler;

import com.xby.lcdata.system.service.SysUsersAsyncService;
import com.sticker.online.core.anno.RouteHandler;
import com.sticker.online.core.anno.RouteMapping;
import com.sticker.online.core.anno.RouteMethod;
import com.sticker.online.core.model.ReplyObj;
import com.sticker.online.core.utils.AsyncServiceUtil;
import com.sticker.online.core.utils.HttpUtil;
import com.sticker.online.core.utils.UUIDUtil;
import com.sticker.online.tools.common.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import static java.net.HttpURLConnection.*;

/**
 * @FileName: com.sticker.online.main.system.handler
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/21
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@RouteHandler("stickeronline/sys/user")
@Api(tags = "用户管理")
public class SysUserHandler {

    private SysUsersAsyncService sysUsersAsyncService =
            AsyncServiceUtil.getAsyncServiceInstance(SysUsersAsyncService.class);

    @RouteMapping(value = "/editUDepartIds", method = RouteMethod.PUT, order = 1)
    @ApiOperation(value = "根据用户id编辑对应的部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "departIdList", value = "部门id（Json数组）", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> editUDepartIds() {
        return ctx -> {
            sysUsersAsyncService.editUDepartIds(CommonUtil.createCondition(ctx.request(), ctx.getBody()),
                    res -> {
                        if (res.succeeded()) {
                            HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                                    ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                        } else {
                            HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                                    ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                        }
                    });
        };
    }

    @RouteMapping(value = "/addUDepartIds", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "添加用户部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "departIdList", value = "部门id（Json数组）", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> addUDepartIds() {
        return ctx -> {
            sysUsersAsyncService.addUDepartIds(CommonUtil.createCondition(ctx.request(), ctx.getBody()),
                    res -> {
                        if (res.succeeded()) {
                            HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                                    ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                        } else {
                            HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                                    ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                        }
                    });
        };
    }

    @RouteMapping(value = "/edit", method = RouteMethod.PUT, order = 1)
    @ApiOperation(value = "编辑用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "realname", value = "真实姓名", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "salt", value = "盐", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "avatar", value = "头像地址", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "birthday", value = "生日", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "sex", value = "性别", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "email", value = "邮箱", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "orgCode", value = "机构编码", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "status", value = "状态(1：正常  2：冻结 ）", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "delFlag", value = "删除状态（0，正常，1已删除）", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "createBy", value = "创建人", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "updateBy", value = "更新人", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "updateTime", value = "更新时间", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "activitiSync", value = "同步工作流引擎1同步0不同步", dataType = "String", paramType = "body", required = true)
    })
    public Handler<RoutingContext> edit() {
        return ctx -> {
            sysUsersAsyncService.edit(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/checkOnlyUser", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "校验用户账号")
    public Handler<RoutingContext> checkOnlyUser() {
        return ctx -> {
            sysUsersAsyncService.checkOnlyUser(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/add", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = " 新增用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "body",required = true),
            @ApiImplicitParam(name = "realname", value = "真实姓名", dataType = "String", paramType = "body",required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "body",required = true),
            @ApiImplicitParam(name = "avatar", value = "头像地址", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "birthday", value = "生日", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "sex", value = "性别", dataType = "Integer", paramType = "body"),
            @ApiImplicitParam(name = "email", value = "邮箱", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "orgCode", value = "机构编码", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "activitiSync", value = "同步工作流引擎1同步0不同步", dataType = "String", paramType = "body")
    })
    public Handler<RoutingContext> add() {
        return ctx -> {
            sysUsersAsyncService.add(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/list", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页长", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Integer", paramType = "query")
    })
    public Handler<RoutingContext> list() {
        return ctx -> {
            sysUsersAsyncService.list(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/queryUserRole", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "根据用户ID查询拥有的角色")
    public Handler<RoutingContext> queryUserRole() {
        return ctx -> {
            sysUsersAsyncService.queryUserRole(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/userDepartList", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "获取用户所属部门")
    public Handler<RoutingContext> userDepartList() {
        return ctx -> {
            sysUsersAsyncService.userDepartList(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/delete", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> delUser() {
        return ctx -> {
            sysUsersAsyncService.del(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/deleteBatch", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> deleteBatch() {
        return ctx -> {
            sysUsersAsyncService.deleteBatch(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/freezeThawUser", method = RouteMethod.PUT, order = 1)
    @ApiOperation(value = "冻结解冻用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> freezeThawUser() {
        return ctx -> {
            sysUsersAsyncService.freezeThawUser(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/queryUserById", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "按id查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> queryUserById() {
        return ctx -> {
            sysUsersAsyncService.queryUserById(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/changPassword", method = RouteMethod.PUT, order = 1)
    @ApiOperation(value = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "新密码", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "oldPassword", value = "原密码", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "body", required = true)
    })
    public Handler<RoutingContext> modifyPwd() {
        return ctx -> {
            sysUsersAsyncService.modifyPwd(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/addSysUserRole", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = " 给指定添加角色用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id（Json数组）", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> addSysUserRole() {
        return ctx -> {
            sysUsersAsyncService.addSysUserRole(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/deleteUserRoleBatch", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = " 删除指定角色的用户关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id（多个使用逗号拼接）", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> deleteUserRoleBatch() {
        return ctx -> {
            sysUsersAsyncService.delUserRole(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/departUserList", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = " 获取部门用户信息")
    public Handler<RoutingContext> departUserList() {
        return ctx -> {
            sysUsersAsyncService.departUserList(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/editSysDepartWithUser", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = " 指定部门下添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIdList", value = "用户id（Json数组）", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "depId", value = "部门id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> editSysDepartWithUser() {
        return ctx -> {
            sysUsersAsyncService.editSysDepartWithUser(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/deleteUserInDepartBatch", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = "删除指定部门的用户关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id（多个使用逗号拼接）", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "depId", value = "部门id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> deleteUserInDepartBatch() {
        return ctx -> {
            sysUsersAsyncService.deleteUserInDepartBatch(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    @RouteMapping(value = "/generateUserId", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "获取生成用户主键id")
    public Handler<RoutingContext> generateUserId() {
        return ctx -> {
            HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                    ReplyObj.build().setSuccess(true).setResult(new JsonObject().put("data", UUIDUtil.createUUID())).setMsg("生成用户主键"));

        };
    }

    @RouteMapping(value = "/getUserByRole", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = " 获取部门用户信息")
    public Handler<RoutingContext> getUserByRole() {
        return ctx -> {
            sysUsersAsyncService.getUserByRole(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

}
