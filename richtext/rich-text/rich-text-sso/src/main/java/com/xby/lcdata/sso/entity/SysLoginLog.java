package com.xby.lcdata.sso.entity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * @ClassName: SysLoginLog
 * @Description:
 * @Author: wangf
 * @Date: 2019/11/19 0019 10:06
 * @Version: 1.0
 **/
@Entity
@DataObject(generateConverter = true)
@Table(name = "s_login_log", catalog = "")
public class SysLoginLog {
    private String id;
    private String username;
    private String loginTime;
    private String ip;
    private String loginResult;
    private SysLoginLog SysLoginLogConverter;

    public SysLoginLog() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysLoginLogConverter.toJson(this, json);
        return json;
    }

    private void toJson(SysLoginLog sysLoginLog, JsonObject json) {
    }

    public SysLoginLog(JsonObject jsonObject) {
        SysLoginLogConverter.fromJson(jsonObject, this);
    }

    private void fromJson(JsonObject jsonObject, SysLoginLog sysLoginLog) {
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
    @Column(name = "login_time")
    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "login_result")
    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysLoginLog sysLoginLog = (SysLoginLog) o;
        return Objects.equals(id, sysLoginLog.id) &&
                Objects.equals(username, sysLoginLog.username) &&
                Objects.equals(loginTime, sysLoginLog.loginTime) &&
                Objects.equals(ip, sysLoginLog.ip) &&
                Objects.equals(loginResult, sysLoginLog.loginResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, loginTime, ip, loginResult);
    }
}
