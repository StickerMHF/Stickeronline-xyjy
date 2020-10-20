package com.xby.lcdata.system.entity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * @ClassName: SysAccessory
 * @Description:
 * @Author: wangf
 * @Date: 2019/11/8 0008 11:32
 * @Version: 1.0
 **/
@Entity
@DataObject(generateConverter = true)
@Table(name = "s_accessory", catalog = "")
public class SysAccessory {
    private String id;
    private String catalogid;
    private String pid;
    private String address;
    private String type;
    private Boolean isEditor;
    private String filename;
    private String createBy;
    private String createTime;
    private String oldFileName;

    public SysAccessory() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysAccessoryConverter.toJson(this, json);
        return json;
    }

    public SysAccessory(JsonObject jsonObject) {
        SysAccessoryConverter.fromJson(jsonObject, this);
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
    @Column(name = "catalogid")
    public String getCatalogid() {
        return catalogid;
    }

    public void setCatalogid(String catalogid) {
        this.catalogid = catalogid;
    }

    @Basic
    @Column(name = "pid")
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "iseditor")
    public Boolean getIsEditor() {
        return isEditor;
    }

    public void setIsEditor(Boolean ispdf) {
        this.isEditor = ispdf;
    }

    @Basic
    @Column(name = "filename")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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
    @Column(name = "old_filename")
    public String getOldFileName() {
        return oldFileName;
    }

    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysAccessory that = (SysAccessory) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(catalogid, that.catalogid) &&
                Objects.equals(pid, that.pid) &&
                Objects.equals(address, that.address) &&
                Objects.equals(type, that.type) &&
                Objects.equals(isEditor, that.isEditor) &&
                Objects.equals(filename, that.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, catalogid, pid, address, type, isEditor, filename);
    }
}
