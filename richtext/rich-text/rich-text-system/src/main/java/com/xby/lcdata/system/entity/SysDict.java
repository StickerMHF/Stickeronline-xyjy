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
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/2
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Entity
@DataObject(generateConverter = true)
@Table(name = "s_dict")
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    public SysDict() {
    }
    public SysDict(JsonObject jsonObject) {
        SysDictConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysDictConverter.toJson(this, json);
        return json;
    }

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;

    /**
     * [预留字段，暂时无用]
     * 字典类型,0 string,1 number类型,2 boolean
     * 前端js对stirng类型和number类型 boolean 类型敏感，需要区分。在select 标签匹配的时候会用到
     * 默认为string类型
     */
    private Integer type;

    /**
     * 字典名称
     */
    @Column(name="dict_name")
    private String dictName;

    /**
     * 字典编码
     */
    @Column(name="dict_code")
    private String dictCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 删除状态
     */
    @Column(name="del_flag")
    private Integer delFlag = 0;

    /**
     * 创建人
     */
    @Column(name="create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name="create_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    @Column(name="update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name="update_time")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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
