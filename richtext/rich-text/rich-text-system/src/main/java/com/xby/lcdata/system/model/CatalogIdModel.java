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
public class CatalogIdModel implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键ID
    private String key;

    // 主键ID
    private String value;

    // 部门名称
    private String title;

    List<CatalogIdModel> children = new ArrayList<>();

    /**
     * 将SysDepartTreeModel的部分数据放在该对象当中
     * @param treeModel
     * @return
     */
    public CatalogIdModel convert(SysCatalogTreeModel treeModel) {
        this.key = treeModel.getId();
        this.value = treeModel.getId();
        this.title = treeModel.getTitle();
        return this;
    }

    /**
     * 该方法为用户部门的实现类所使用
     * @param sysCatalog
     * @return
     */
    public CatalogIdModel convertByUserDepart(SysCatalog sysCatalog) {
        this.key = sysCatalog.getId();
        this.value = sysCatalog.getId();
        this.title = sysCatalog.getName();
        return this;
    }

    public List<CatalogIdModel> getChildren() {
        return children;
    }

    public void setChildren(List<CatalogIdModel> children) {
        this.children = children;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
}
