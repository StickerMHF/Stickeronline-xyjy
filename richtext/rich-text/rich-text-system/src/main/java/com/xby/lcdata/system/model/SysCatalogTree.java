package com.xby.lcdata.system.model;


import com.xby.lcdata.system.entity.SysAccessory;
import com.xby.lcdata.system.entity.SysCatalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: com.sticker.online.main.system.model
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/21
 * @Version: 1.0
 * @LastModified:毛海锋
 */
public class SysCatalogTree implements Serializable {

    /**
     * id
     */
    private String id;
    private String key;
    private String value;
    private String code;

    private String title;
    private String description;
    /**
     * 父id
     */
    private String pid;

    /**
     * 目录名称
     */
    private String name;
    /**
     * 是不是目录
     */
    private Integer isLeaf;

    private List<SysCatalogTree> children;

    public SysCatalogTree() {
    }

    public SysCatalogTree(SysCatalog sysCatalog) {
        this.id= sysCatalog.getId();
        this.key= sysCatalog.getId();
        this.value= sysCatalog.getId();
        this.code= sysCatalog.getCode();
        this.name= sysCatalog.getName();
        this.pid= sysCatalog.getPid();
        this.title= sysCatalog.getName();
        this.isLeaf=0;
        this.description= sysCatalog.getDescription();
        this.children = new ArrayList<SysCatalogTree>();
    }

    public SysCatalogTree(SysAccessory sAccessory) {
        this.id=sAccessory.getId();
        this.key=sAccessory.getId();
        this.value=sAccessory.getId();
        this.code=sAccessory.getId();
        this.name=sAccessory.getFilename();
        this.pid=sAccessory.getPid();
        this.title=sAccessory.getFilename();
        this.isLeaf=1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Integer leaf) {
        isLeaf = leaf;
    }

    public List<SysCatalogTree> getChildren() {
        return children;
    }

    public void setChildren(List<SysCatalogTree> children) {
        this.children = children;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
