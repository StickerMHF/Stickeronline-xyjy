package com.xby.lcdata.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sticker.online.core.utils.TimeUtil;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName: SysPermissionBtn
 * @Description:
 * @Author: wangf
 * @Date: 2019/11/6 0006 9:55
 * @Version: 1.0
 **/
@Entity
@DataObject(generateConverter = true)
@Table(name = "s_permission_btn",  catalog = "")
public class SysPermissionBtn {
    private String id;
    private String permissionId;
    private String btnName;
    private String btnClass;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    public SysPermissionBtn() {

    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysPermissionBtnConverter.toJson(this, json);
        return json;
    }

    public SysPermissionBtn(JsonObject jsonObject) {
        SysPermissionBtnConverter.fromJson(jsonObject, this);
        this.createTime = TimeUtil.getDateNow();
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
    @Column(name = "permission_id")
    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    @Basic
    @Column(name = "btn_name")
    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    @Basic
    @Column(name = "btn_class")
    public String getBtnClass() {
        return btnClass;
    }

    public void setBtnClass(String btnClass) {
        this.btnClass = btnClass;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()){ return false;}
        SysPermissionBtn that = (SysPermissionBtn) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(permissionId, that.permissionId) &&
                Objects.equals(btnName, that.btnName) &&
                Objects.equals(btnClass, that.btnClass) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permissionId, btnName, btnClass, createTime);
    }
}
