package com.xby.lcdata.system.entity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * @ClassName: SysCatalog
 * @Description:
 * @Author: wangf
 * @Date: 2019/11/8 0008 11:32
 * @Version: 1.0
 **/
@Entity
@DataObject(generateConverter = true)
@Table(name = "s_catalog",  catalog = "")
public class SysCatalog {
    private String id;
    private String pid;
    private String name;
    private String description;
    private String code;

    public SysCatalog() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysCatalogConverter.toJson(this, json);
        return json;
    }
    public SysCatalog(JsonObject jsonObject) {
        SysCatalogConverter.fromJson(jsonObject, this);
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
    @Column(name = "pid")
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysCatalog sysCatalog = (SysCatalog) o;
        return Objects.equals(id, sysCatalog.id) &&
                Objects.equals(pid, sysCatalog.pid) &&
                Objects.equals(name, sysCatalog.name) &&
                Objects.equals(description, sysCatalog.description) &&
                Objects.equals(code, sysCatalog.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pid, name, description, code);
    }
}
