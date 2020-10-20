package com.xby.lcdata.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sticker.online.core.anno.Dict;
import com.sticker.online.core.anno.Excel;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;

/**
 * @FileName: com.ygj.cyryks.internet.main.stu.model
 * @Author: Sticker
 * @Date: 2019/7/4
 * @Version: 1.0
 */
@Entity
@DataObject(generateConverter = true)
@Table(name = "s_depart")
public class SysDepart  {
    private static final long serialVersionUID = 1L;
    public SysDepart() {
    }


    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysDepartConverter.toJson(this, json);
        return json;
    }
    public SysDepart(JsonObject jsonObject) {
        SysDepartConverter.fromJson(jsonObject, this);
    }

    /**ID*/
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;
    /**父机构ID*/
    @Column(name = "parent_id")
    private String parentId;
    /**机构/部门名称*/
    @Excel(name="机构/部门名称",width=15)
    @Column(name = "depart_name")
    private String departName;
    /**英文名*/
    @Excel(name="英文名",width=15)
    @Column(name = "depart_name_en")
    private String departNameEn;
    /**缩写*/
    @Column(name = "depart_name_abbr")
    private String departNameAbbr;
    /**排序*/
    @Excel(name="排序",width=15)
    @Column(name = "depart_order")
    private Integer departOrder;
    /**描述*/
    @Excel(name="描述",width=15)
    private String description;
    /**机构类型*/
    @Excel(name="机构类型",width=15)
    @Column(name = "org_type")
    private String orgType;
    /**机构编码*/
    @Excel(name="机构编码",width=15)
    @Column(name = "org_code")
    private String orgCode;
    /**手机号*/
    @Excel(name="手机号",width=15)
    private String mobile;
    /**传真*/
    @Excel(name="传真",width=15)
    private String fax;
    /**地址*/
    @Excel(name="地址",width=15)
    private String address;
    /**备注*/
    @Excel(name="备注",width=15)
    private String memo;
    /**状态（1启用，0不启用）*/
    @Excel(name="状态",width=15)
    @Dict(dicCode = "depart_status")
    private String status;
    /**删除状态（0，正常，1已删除）*/
    @Excel(name="删除状态",width=15)
    @Dict(dicCode = "del_flag")
    @Column(name = "del_flag")
    private String delFlag;
    /**创建人*/
    @Column(name = "create_by")
    private String createBy;
    /**创建日期*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "create_time")
    private Date createTime;
    /**更新人*/
    @Column(name = "update_by")
    private String updateBy;
    /**更新日期*/
//    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDepartNameEn() {
        return departNameEn;
    }

    public void setDepartNameEn(String departNameEn) {
        this.departNameEn = departNameEn;
    }

    public String getDepartNameAbbr() {
        return departNameAbbr;
    }

    public void setDepartNameAbbr(String departNameAbbr) {
        this.departNameAbbr = departNameAbbr;
    }

    public Integer getDepartOrder() {
        return departOrder;
    }

    public void setDepartOrder(Integer departOrder) {
        this.departOrder = departOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
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
