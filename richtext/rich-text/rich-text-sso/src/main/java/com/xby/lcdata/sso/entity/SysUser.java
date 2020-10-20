package com.xby.lcdata.sso.entity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * @ClassName: SysUser
 * @Description:
 * @Author: wangf
 * @Date: 2019/11/19 0019 9:51
 * @Version: 1.0
 **/
@Entity
@DataObject(generateConverter = true)
@Table(name = "s_user", catalog = "")
public class SysUser {
    private String id;
    private String username;
    private String realname;
    private String password;
    private String salt;
    private String avatar;
    private String birthday;
    private Integer sex;
    private String email;
    private String phone;
    private String orgCode;
    private Integer status;
    private Integer delFlag;
    private String activitiSync;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;

    public SysUser() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysUserConverter.toJson(this, json);
        return json;
    }

    public SysUser(JsonObject jsonObject) {
        SysUserConverter.fromJson(jsonObject, this);
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "realname")
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Basic
    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Basic
    @Column(name = "birthday")
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "sex")
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "org_code")
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "del_flag")
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Basic
    @Column(name = "activiti_sync")
    public String getActivitiSync() {
        return activitiSync;
    }

    public void setActivitiSync(String activitiSync) {
        this.activitiSync = activitiSync;
    }

    @Basic
    @Column(name = "create_by")
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Basic
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_by")
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Basic
    @Column(name = "update_time")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUser sysUser = (SysUser) o;
        return Objects.equals(id, sysUser.id) &&
                Objects.equals(username, sysUser.username) &&
                Objects.equals(realname, sysUser.realname) &&
                Objects.equals(password, sysUser.password) &&
                Objects.equals(salt, sysUser.salt) &&
                Objects.equals(avatar, sysUser.avatar) &&
                Objects.equals(birthday, sysUser.birthday) &&
                Objects.equals(sex, sysUser.sex) &&
                Objects.equals(email, sysUser.email) &&
                Objects.equals(phone, sysUser.phone) &&
                Objects.equals(orgCode, sysUser.orgCode) &&
                Objects.equals(status, sysUser.status) &&
                Objects.equals(delFlag, sysUser.delFlag) &&
                Objects.equals(activitiSync, sysUser.activitiSync) &&
                Objects.equals(createBy, sysUser.createBy) &&
                Objects.equals(createTime, sysUser.createTime) &&
                Objects.equals(updateBy, sysUser.updateBy) &&
                Objects.equals(updateTime, sysUser.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, realname, password, salt, avatar, birthday, sex, email, phone, orgCode,
                status, delFlag, activitiSync, createBy, createTime, updateBy, updateTime);
    }
}
