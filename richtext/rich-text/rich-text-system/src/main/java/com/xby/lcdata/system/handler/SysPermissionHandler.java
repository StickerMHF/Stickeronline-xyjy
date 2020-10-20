package com.xby.lcdata.system.handler;

import com.xby.lcdata.system.service.SysPermissionAsyncService;
import com.xby.lcdata.system.service.SysPermissionBtnAsyncService;
import com.sticker.online.core.anno.RouteHandler;
import com.sticker.online.core.anno.RouteMapping;
import com.sticker.online.core.anno.RouteMethod;
import com.sticker.online.core.model.ReplyObj;
import com.sticker.online.core.utils.AsyncServiceUtil;
import com.sticker.online.core.utils.HttpUtil;
import com.sticker.online.tools.common.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @FileName: com.sticker.online.main.system.handler
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/21
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@RouteHandler("stickeronline/sys/permission")
@Api(tags = "菜单权限管理")
public class SysPermissionHandler {

    private SysPermissionAsyncService sysPermissionService =
            AsyncServiceUtil.getAsyncServiceInstance(SysPermissionAsyncService.class);

    private SysPermissionBtnAsyncService sysPermissionBtnAsyncService =
            AsyncServiceUtil.getAsyncServiceInstance(SysPermissionBtnAsyncService.class);

    @RouteMapping(value = "/queryListBySystemType", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "查询所有子系统")
    public Handler<RoutingContext> queryListBySystemType() {
        return ctx -> {
            sysPermissionService.queryListBySystemType(CommonUtil.createCondition(ctx.request(), ctx.getBody()),
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
    @ApiOperation(value = "编辑菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单id", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "parentId", value = "父级id", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "name", value = "菜单名称", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "perms", value = "菜单权限编码", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "permsType", value = "权限策略:1显示2禁用", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "icon", value = "菜单图标", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "component", value = "组件", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "componentName", value = "组件名称", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "url", value = "路径", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "redirect", value = "一级菜单跳转地址", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "sortNo", value = "菜单排序", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "menuType", value = "菜单类型(0:一级菜单; 1:子菜单:2:按钮权限;3:系统)", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "isLeaf", value = "是否叶子节点:    1:是   0:不是", dataType = "Boolean", paramType = "body", required = true),
            @ApiImplicitParam(name = "route", value = "是否路由菜单: 0:不是  1:是", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "description", value = "描述", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "delFlag", value = "删除状态 0正常 1已删除", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "createBy", value = "创建人", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "updateBy", value = "更新人", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "updateTime", value = "更新时间", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "alwaysShow", value = "聚合子路由: 1是0否", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "hidden", value = "是否隐藏路由: 0否,1是", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "status", value = "是否添加数据权限1是0否", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "webUrl", value = "web路径", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "iconType", value = "图标类型:0:自带1:上传", dataType = "Integer", paramType = "body", required = true)
    })
    public Handler<RoutingContext> edit() {
        return ctx -> {
            sysPermissionService.edit(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "新增菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父级id", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "name", value = "菜单名称", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "perms", value = "菜单权限编码", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "permsType", value = "权限策略:1显示2禁用", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "icon", value = "菜单图标", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "component", value = "组件", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "componentName", value = "组件名称", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "url", value = "路径", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "redirect", value = "一级菜单跳转地址", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "sortNo", value = "菜单排序", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "menuType", value = "菜单类型(0:一级菜单; 1:子菜单:2:按钮权限;3:系统)", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "isLeaf", value = "是否叶子节点:    1:是   0:不是", dataType = "Boolean", paramType = "body", required = true),
            @ApiImplicitParam(name = "route", value = "是否路由菜单: 0:不是  1:是", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "description", value = "描述", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "alwaysShow", value = "聚合子路由: 1是0否", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "hidden", value = "是否隐藏路由: 0否,1是", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "status", value = "是否添加数据权限1是0否", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "webUrl", value = "web路径", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "iconType", value = "图标类型:0:自带1:上传", dataType = "Integer", paramType = "body", required = true)
    })
    public Handler<RoutingContext> add() {
        return ctx -> {
            sysPermissionService.add(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/queryTreeList", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "获取全部的权限树")
    public Handler<RoutingContext> queryTreeList() {
        return ctx -> {
            sysPermissionService.queryTreeList(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/getPermRuleListByPermId", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "根据菜单id来获取其对应的权限数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "permissionId", value = "菜单权限Id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "ruleName", value = "数据权限名称", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "ruleValue", value = "数据权限值", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> getPermRuleListByPermId() {
        return ctx -> {
            sysPermissionService.getPermRuleListByPermId(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "加载菜单数据节点")
    public Handler<RoutingContext> list() {
        return ctx -> {
            sysPermissionService.list(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/getUserPermissionByToken", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "查询用户拥有的菜单权限和按钮权限（根据TOKEN）")
    public Handler<RoutingContext> getUserPermissionByToken() {
        return ctx -> {
            sysPermissionService.getUserPermissionByTokenOfSystem(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/getUserPermissionByTokenOfSystem", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "查询用户拥有的菜单权限按钮权限、子系统权限（根据TOKEN）")
    public Handler<RoutingContext> getUserPermissionByTokenOfSystem() {
        return ctx -> {
            sysPermissionService.getUserPermissionByTokenOfSystem(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/queryRolePermission", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "查询角色授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> queryRolePermission() {
        return ctx -> {
            sysPermissionService.queryRolePermission(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/saveRolePermission", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "保存角色授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "permissionIds", value = "权限id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> saveRolePermission() {
        return ctx -> {
            sysPermissionService.saveRolePermission(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> delete() {
        return ctx -> {
            JsonObject params = CommonUtil.createCondition(ctx.request(), ctx.getBody());
            sysPermissionService.delete(params, res -> {
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
    @ApiOperation(value = "批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> deleteBatch() {
        return ctx -> {
            sysPermissionService.deleteBatch(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/addPermissionRule", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "添加菜单权限数据")
    public Handler<RoutingContext> addPermissionRule() {
        return ctx -> {
            sysPermissionService.addPermissionRule(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/editPermissionRule", method = RouteMethod.PUT, order = 1)
    @ApiOperation(value = "编辑菜单权限数据")
    public Handler<RoutingContext> editPermissionRule() {
        return ctx -> {
            sysPermissionService.editPermissionRule(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/deletePermissionRule", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = "删除菜单权限数据")
    public Handler<RoutingContext> deletePermissionRule() {
        return ctx -> {
            sysPermissionService.deletePermissionRule(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/queryPermissionRule", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "查询菜单权限数据")
    public Handler<RoutingContext> queryPermissionRule() {
        return ctx -> {
            JsonObject params = CommonUtil.createCondition(ctx.request(), ctx.getBody());
            sysPermissionService.queryPermissionRule(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/queryById", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "查询菜单权限数据")
    public Handler<RoutingContext> queryById() {
        return ctx -> {
            sysPermissionService.queryById(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/getBtnByPermission/:permissionId", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "根据菜单Id获取对应按钮权限")
    public Handler<RoutingContext> getBtnByPermission() {
        return ctx -> {
            sysPermissionBtnAsyncService.getBtnByPermission(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/addBtn", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "新增按钮权限")
    public Handler<RoutingContext> addBtn() {
        return ctx -> {
            sysPermissionBtnAsyncService.add(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/editBtn", method = RouteMethod.PUT, order = 1)
    @ApiOperation(value = "编辑按钮权限")
    public Handler<RoutingContext> editBtn() {
        return ctx -> {
            sysPermissionBtnAsyncService.edit(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/deleteBtn", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = "删除按钮权限")
    public Handler<RoutingContext> deleteBtn() {
        return ctx -> {
            sysPermissionBtnAsyncService.delete(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/deleteByPermissionId/:permissionId", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = "根据菜单Id删除按钮权限")
    public Handler<RoutingContext> deleteByPermissionId() {
        return ctx -> {
            sysPermissionBtnAsyncService.deleteByPermissionId(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/getBtnByRole/:roleId", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "根据角色Id获取按钮权限")
    public Handler<RoutingContext> getBtnByRole() {
        return ctx -> {
            sysPermissionBtnAsyncService.getBtnByRole(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/addRoleBtn", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "新增角色按钮授权")
    public Handler<RoutingContext> addRoleBtn() {
        return ctx -> {
            sysPermissionService.addRoleBtn(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/addDataRule", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "添加数据权限")
    public Handler<RoutingContext> addDataRule() {
        return ctx -> {
            sysPermissionService.addDataRule(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/editDataRule", method = RouteMethod.PUT, order = 1)
    @ApiOperation(value = "修改数据权限")
    public Handler<RoutingContext> editDataRule() {
        return ctx -> {
            sysPermissionService.editDataRule(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/delDataRule", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = "删除数据权限")
    public Handler<RoutingContext> delDataRule() {
        return ctx -> {
            sysPermissionService.delDataRule(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/getDataRule", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "获取角色数据权限")
    public Handler<RoutingContext> getDataRule() {
        return ctx -> {
            sysPermissionService.getDataRule(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/saveSvg", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "保存SVG图标信息")
    public Handler<RoutingContext> saveSvg() {
        return ctx -> {
            sysPermissionService.saveSvg(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/getSvgList", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "获取SVG图标信息")
    public Handler<RoutingContext> getSvgList() {
        return ctx -> {
            sysPermissionService.getSvgList(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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

    @RouteMapping(value = "/delSvg/:id", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = "删除SVG图标信息")
    public Handler<RoutingContext> delSvg() {
        return ctx -> {
            sysPermissionService.delSvg(CommonUtil.createCondition(ctx.request(),
                    ctx.getBody()), res -> {
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
