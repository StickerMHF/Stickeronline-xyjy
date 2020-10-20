package com.xby.lcdata.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.entity
 * @Author: Sticker
 * @Date: 2019/7/12
 * @Version: 1.0
 */
@Entity
@DataObject(generateConverter = true)
@Table(name = "s_permission_data_rule")
public class SysPermissionDataRule implements Serializable {
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysPermissionDataRuleConverter.toJson(this, json);
        return json;
    }
    public SysPermissionDataRule(JsonObject jsonObject) {
        SysPermissionDataRuleConverter.fromJson(jsonObject, this);
    }

    public SysPermissionDataRule() {
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
     * 对应的菜单id
     */
    @Column(name="permission_id")
    private String permissionId;

    /**
     * 规则名称
     */
    @Column(name="rule_name")
    private String ruleName;

    /**
     * 字段
     */
    @Column(name="rule_column")
    private String ruleColumn;

    /**
     * 条件
     */
    @Column(name="rule_conditions")
    private String ruleConditions;

    /**
     * 规则值
     */
    @Column(name="rule_value")
    private String ruleValue;

    /**
     * 状态值 1有效 0无效
     */
    private String status;

    /**
     * 创建时间
     */
    @Column(name="create_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name="create_by")
    private String createBy;

    /**
     * 修改时间
     */
    @Column(name="update_time")
    private Date updateTime;

    /**
     * 修改人
     */
    @Column(name="update_by")
    private String updateBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleColumn() {
        return ruleColumn;
    }

    public void setRuleColumn(String ruleColumn) {
        this.ruleColumn = ruleColumn;
    }

    public String getRuleConditions() {
        return ruleConditions;
    }

    public void setRuleConditions(String ruleConditions) {
        this.ruleConditions = ruleConditions;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
