package com.xby.lcdata.system.entity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @ClassName: SysRoleDataRule
 * @Description:
 * @Author: wangf
 * @Date: 2019/12/6 0006 14:09
 * @Version: 1.0
 **/
@Entity
@DataObject(generateConverter = true)
@Table(name = "s_role_data_rule", catalog = "")
public class SysRoleDataRule {
    private String id;
    private String roleId;
    private String fieldName;
    private String symbol;
    private String value;
    private String createTime;
    private String tableId;
    private Integer isSql;

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysRoleDataRuleConverter.toJson(this, json);
        return json;
    }

    public SysRoleDataRule(JsonObject jsonObject) {
        SysRoleDataRuleConverter.fromJson(jsonObject, this);
    }

    public SysRoleDataRule() {
    }

    public SysRoleDataRule(String roleId, String fieldName, String symbol, String value, String createTime
            , String tableId, Integer isSql) {
        this.roleId = roleId;
        this.fieldName = fieldName;
        this.symbol = symbol;
        this.value = value;
        this.createTime = createTime;
        this.tableId = tableId;
        this.isSql = isSql;
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
    @Column(name = "field_name")
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Basic
    @Column(name = "symbol")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
    @Column(name = "table_id")
    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    @Basic
    @Column(name = "is_sql")
    public Integer getIsSql() {
        return isSql;
    }

    public void setIsSql(Integer isSql) {
        this.isSql = isSql;
    }

}
