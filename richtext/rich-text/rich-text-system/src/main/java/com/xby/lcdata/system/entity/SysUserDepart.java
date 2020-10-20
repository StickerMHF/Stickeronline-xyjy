package com.xby.lcdata.system.entity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @FileName: com.ygj.cyryks.internet.main.stu.model
 * @Author: Sticker
 * @Date: 2019/7/4
 * @Version: 1.0
 */
@Entity
@DataObject(generateConverter = true)
@Table(name = "s_user_depart")
public class SysUserDepart implements Serializable  {
    private static final long serialVersionUID = 1L;

    public SysUserDepart() {
    }
    public SysUserDepart(JsonObject jsonObject) {
        SysUserDepartConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysUserDepartConverter.toJson(this, json);
        return json;
    }
    /**主键id*/
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;

    /**部门id*/
    @Column(name = "dep_id")
    private String depId;
    /**用户id*/
    @Column(name = "user_id")
    private String userId;

//
    public SysUserDepart(String id, String userId, String depId) {
        super();
        this.id = id;
        this.userId = userId;
        this.depId = depId;
    }

    public SysUserDepart(String userId, String depId) {
        super();
        this.userId = userId;
        this.depId = depId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
