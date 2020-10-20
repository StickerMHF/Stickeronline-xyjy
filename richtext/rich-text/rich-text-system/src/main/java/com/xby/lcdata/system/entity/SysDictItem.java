package com.xby.lcdata.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sticker.online.core.anno.Dict;
import com.sticker.online.core.anno.Excel;
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
@Table(name = "s_dict_item")
public class SysDictItem implements Serializable {

    private static final long serialVersionUID = 1L;

    public SysDictItem() {
    }
    public SysDictItem(JsonObject jsonObject) {
        SysDictItemConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysDictItemConverter.toJson(this, json);
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
     * 字典id
     */
    @Column(name="dict_id")
    private String dictId;

    /**
     * 字典项文本
     */
    @Excel(name = "字典项文本", width = 20)
    @Column(name="item_text")
    private String text;

    /**
     * 字典项值
     */
    @Excel(name = "字典项值", width = 30)
    @Column(name="item_value")
    private String value;

    /**
     * 描述
     */
    @Excel(name = "描述", width = 40)
    private String description;

    /**
     * 排序
     */
    @Excel(name = "排序", width = 15,type=4)
    @Column(name="sort_order")
    private Integer sortOrder;


    /**
     * 状态（1启用 0不启用）
     */
    @Dict(dicCode = "dict_item_status")
    private Integer status;

    @Column(name="create_by")
    private String createBy;

    @Column(name="create_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @Column(name="update_by")
    private String updateBy;

    @Column(name="update_time")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
