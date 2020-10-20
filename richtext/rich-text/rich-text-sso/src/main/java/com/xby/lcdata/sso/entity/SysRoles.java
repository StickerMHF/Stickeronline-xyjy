package com.xby.lcdata.sso.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sticker.online.core.anno.Excel;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "s_role")
public class SysRoles implements Serializable {

    public SysRoles() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysRolesConverter.toJson(this, json);
        return json;
    }
    public SysRoles(JsonObject jsonObject) {
        SysRolesConverter.fromJson(jsonObject, this);
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
     * 角色名称
     */
    @Excel(name="角色名",width=15)
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色编码
     */
    @Excel(name="角色编码",width=15)
    @Column(name = "role_code")
    private String roleCode;

    /**
     * 描述
     */
    @Excel(name="描述",width=60)
    private String description;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
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
}
