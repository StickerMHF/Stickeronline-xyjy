package com.xby.lcdata.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sticker.online.core.anno.Dict;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @FileName: com.ygj.cyryks.internet.main.stu.model
 * @Author: Sticker
 * @Date: 2019/7/5
 * @Version: 1.0
 */
@Entity
@DataObject(generateConverter = true)
@Table(name = "s_permission")
public class SysPermission implements Serializable {
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysPermissionConverter.toJson(this, json);
        return json;
    }

    public SysPermission(JsonObject jsonObject) {
        SysPermissionConverter.fromJson(jsonObject, this);
    }

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;

    /**
     * 父id
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单权限编码，例如：“sys:schedule:list,sys:schedule:info”,多个逗号隔开
     */
    private String perms;
    /**
     * 权限策略1显示2禁用
     */
    @Column(name = "perms_type")
    private String permsType;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 组件
     */
    private String component;

    /**
     * 组件名字
     */
    @Column(name = "component_name")
    private String componentName;

    /**
     * 路径
     */
    private String url;
    /**
     * 一级菜单跳转地址
     */
    private String redirect;

    /**
     * 菜单排序
     */
    @Column(name = "sort_no")
    private Integer sortNo;

    /**
     * 类型（0：一级菜单；1：子菜单 ；2：按钮权限）
     */
    @Dict(dicCode = "menu_type")
    @Column(name = "menu_type")
    private Integer menuType;

    /**
     * 是否叶子节点: 1:是  0:不是
     */
    @Column(name = "is_leaf", columnDefinition = "bit(1) default 0")
    private Integer leaf = 0;

    /**
     * 是否路由菜单: 0:不是  1:是（默认值1）
     */
    @Column(name = "is_route", columnDefinition = "bit(1) default 1")
    private Integer route = 1;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 删除状态 0正常 1已删除
     */
    @Column(name = "del_flag", columnDefinition = "int(1) default 0")
    private Integer delFlag;

    /**
     * 是否配置菜单的数据权限 1是0否 默认0
     */
    @Column(name = "rule_flag")
    private Integer ruleFlag;

    /**
     * 是否隐藏路由菜单: 0否,1是（默认值0）
     */
    @Column(name = "hidden", columnDefinition = "bit(1) default 0")
    private Integer hidden = 0;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 按钮权限状态(0无效1有效)
     */
    private String status;

    /**
     * alwaysShow
     */
    @Column(name = "always_show", columnDefinition = "bit(1) default 0")
    private Integer alwaysShow = 0;

    @Column(name = "web_url")
    private String webUrl;

    @Column(name = "icon_type")
    private Integer iconType;

    public SysPermission() {

    }

    public SysPermission(boolean index) {
        if (index) {
            this.id = "9502685863ab87f0ad1134142788a385";
            this.name = "首页";
            this.component = "dashboard/Analysis";
            this.url = "/dashboard/analysis";
            this.icon = "home";
            this.menuType = 0;
            this.sortNo = 0;
            this.ruleFlag = 0;
            this.delFlag = 0;
            this.alwaysShow = 0;
            this.route = 1;
            this.leaf = 1;
            this.hidden = 0;
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getPermsType() {
        return permsType;
    }

    public void setPermsType(String permsType) {
        this.permsType = permsType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    public Integer getRoute() {
        return route;
    }

    public void setRoute(Integer route) {
        this.route = route;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getRuleFlag() {
        return ruleFlag;
    }

    public void setRuleFlag(Integer ruleFlag) {
        this.ruleFlag = ruleFlag;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Integer alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Integer getIconType() {
        return iconType;
    }

    public void setIconType(Integer iconType) {
        this.iconType = iconType;
    }
}
