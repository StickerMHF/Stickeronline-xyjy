package com.xby.lcdata.system.model;


import com.xby.lcdata.system.entity.SysPermission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.model
 * @Description:
 * @Author: Sticker
 * @Date: 2019/7/31
 * @Version: 1.0
 * @LastModified:毛海锋
 */
public class TreeModel implements Serializable {

    private static final long serialVersionUID = 4013193970046502756L;

    private String key;

    private String title;

    private String slotTitle;

    private Integer isLeaf;

    private Integer selectable;

    private String icon;

    private Integer ruleFlag;

    private Map<String, String> scopedSlots;

    private List<TreeModel> children;

    private String parentId;

    private String label;

    private String value;

    public TreeModel() {

    }

    public TreeModel(SysPermission permission) {
        this.key = permission.getId();
        this.icon = permission.getIcon();
        this.parentId = permission.getParentId();
        this.title = permission.getName();
        this.slotTitle = permission.getName();
        this.value = permission.getId();
        this.isLeaf = permission.getLeaf();
        this.label = permission.getName();
        if (permission.getLeaf() == 0) {
            this.children = new ArrayList<TreeModel>();
        }
    }

    public TreeModel(String key, String parentId, String slotTitle, Integer ruleFlag, Integer isLeaf, Boolean selectable) {
        this.key = key;
        this.parentId = parentId;
        this.ruleFlag = ruleFlag;
        this.title = slotTitle;
        this.slotTitle = slotTitle;
        Map<String, String> map = new HashMap<String, String>();
        map.put("title", "hasDatarule");
        this.selectable = selectable ? 1 : 0;
        this.scopedSlots = map;
        this.isLeaf = isLeaf;
        this.value = key;
        this.label = slotTitle;
        if (isLeaf == 0) {
            this.children = new ArrayList<TreeModel>();
        }
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlotTitle() {
        return slotTitle;
    }

    public void setSlotTitle(String slotTitle) {
        this.slotTitle = slotTitle;
    }

    public Integer getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Integer leaf) {
        isLeaf = leaf;
    }

    public Integer getSelectable() {
        return selectable;
    }

    public void setSelectable(Integer selectable) {
        this.selectable = selectable;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getRuleFlag() {
        return ruleFlag;
    }

    public void setRuleFlag(Integer ruleFlag) {
        this.ruleFlag = ruleFlag;
    }

    public Map<String, String> getScopedSlots() {
        return scopedSlots;
    }

    public void setScopedSlots(Map<String, String> scopedSlots) {
        this.scopedSlots = scopedSlots;
    }

    public List<TreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<TreeModel> children) {
        this.children = children;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

