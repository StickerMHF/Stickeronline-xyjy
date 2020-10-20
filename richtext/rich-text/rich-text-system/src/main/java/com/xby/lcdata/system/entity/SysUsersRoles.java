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
@Table(name = "s_user_role")
public class SysUsersRoles implements Serializable {
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysUsersRolesConverter.toJson(this, json);
        return json;
    }
    public SysUsersRoles(JsonObject jsonObject) {
        SysUsersRolesConverter.fromJson(jsonObject, this);
    }
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;

    public SysUsersRoles() {
    }

    public SysUsersRoles(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
