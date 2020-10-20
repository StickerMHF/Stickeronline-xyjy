package com.xby.lcdata.system.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * @ClassName: SysRoleBtn
 * @Description:
 * @Author: wangf
 * @Date: 2019/11/6 0006 9:55
 * @Version: 1.0
 **/
@Entity
@Table(name = "s_role_btn",  catalog = "")
public class SysRoleBtn {
    private String id;
    private String roleId;
    private String permissionId;
    private String btnId;
    private String createTime;

    public SysRoleBtn() {

    }

    public SysRoleBtn(String roleId, String btnId, String permissionId, String createTime) {
        this.roleId = roleId;
        this.btnId = btnId;
        this.permissionId = permissionId;
        this.createTime = createTime;
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
    @Column(name = "role_id")
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
    @Column(name = "btn_id")
    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }

    @Basic
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRoleBtn sysRoleBtn = (SysRoleBtn) o;
        return Objects.equals(id, sysRoleBtn.id) &&
                Objects.equals(roleId, sysRoleBtn.roleId) &&
                Objects.equals(btnId, sysRoleBtn.btnId) &&
                Objects.equals(createTime, sysRoleBtn.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleId, btnId, createTime);
    }
}
