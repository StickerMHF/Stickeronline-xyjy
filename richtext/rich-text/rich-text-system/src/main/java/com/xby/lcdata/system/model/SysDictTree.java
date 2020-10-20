package com.xby.lcdata.system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xby.lcdata.system.entity.SysDict;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.model
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/5
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@DataObject(generateConverter = true)
public class SysDictTree implements Serializable {

    private static final long serialVersionUID = 1L;

    public SysDictTree() {
    }
    public SysDictTree(JsonObject jsonObject) {
        SysDictTreeConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysDictTreeConverter.toJson(this, json);
        return json;
    }

    private String key;

    private String title;

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;
    /**
     * 字典类型,0 string,1 number类型,2 boolean
     * 前端js对stirng类型和number类型 boolean 类型敏感，需要区分。在select 标签匹配的时候会用到
     * 默认为string类型
     */
    private Integer type;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 删除状态
     */
    private Integer delFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    public SysDictTree(SysDict node) {
        this.id = node.getId();
        this.key = node.getId();
        this.title = node.getDictName();
        this.dictCode = node.getDictCode();
        this.description = node.getDescription();
        this.delFlag = node.getDelFlag();
        this.type = node.getType();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
