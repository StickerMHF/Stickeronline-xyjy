package com.xby.lcdata.system.entity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @FileName: com.ygj.cyryks.internet.main.stu.model
 * @Author: Sticker
 * @Date: 2019/7/5
 * @Version: 1.0
 */
@Entity
@DataObject(generateConverter = true)
@Table(name = "s_role_permission")
public class SysRolePermission implements Serializable {
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysRolePermissionConverter.toJson(this, json);
        return json;
    }
    public SysRolePermission(JsonObject jsonObject) {
        SysRolePermissionConverter.fromJson(jsonObject, this);
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
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 权限id
     */
    @Column(name = "permission_id")
    private String permissionId;

    /**
     * 数据权限
     */
    @Column(name = "data_rule_ids")
    private String dataRuleIds;

    

    public SysRolePermission() {
    }

    public SysRolePermission(String roleId, String permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getDataRuleIds() {
        return dataRuleIds;
    }

    public void setDataRuleIds(String dataRuleIds) {
        this.dataRuleIds = dataRuleIds;
    }
}
