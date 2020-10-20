package com.xby.lcdata.system.model;

import com.xby.lcdata.system.entity.SysCatalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.model
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/1
 * @Version: 1.0
 * @LastModified:毛海锋
 */
public class SysCatalogTreeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 对应SysCatalog中的id字段,前端数据树中的key*/
    private String key;

    /** 对应SysCatalog中的id字段,前端数据树中的value*/
    private String value;

    /** 对应name字段,前端数据树中的title*/
    private String title;


    private Integer isLeaf;
    // 以下所有字段均与SysDepart相同

    private String id;

    /**
     * 父节点ID
     */
    private String pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 编码
     */
    private String code;

    private List<SysCatalogTreeModel> children = new ArrayList<>();


    /**
     * 将SysDepart对象转换成SysDepartTreeModel对象
     * @param sysCatalog
     */
    public SysCatalogTreeModel(SysCatalog sysCatalog) {
        this.key = sysCatalog.getId();
        this.value = sysCatalog.getId();
        this.title = sysCatalog.getName();
        this.id = sysCatalog.getId();
        this.desc= sysCatalog.getDescription();
        this.pid= sysCatalog.getPid();
        this.code= sysCatalog.getCode();
//        this.isLeaf=false;
//        this.children = new ArrayList<SysCatalogTreeModel>();
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isleaf) {
        this.isLeaf = isleaf;
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


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SysCatalogTreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<SysCatalogTreeModel> children) {
        if (children==null){
            this.isLeaf=1;
        }
        this.children = children;
    }

    public Integer isLeaf() {
        return isLeaf;
    }

    public void setLeaf(Integer leaf) {
        isLeaf = leaf;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SysCatalogTreeModel() { }

}
