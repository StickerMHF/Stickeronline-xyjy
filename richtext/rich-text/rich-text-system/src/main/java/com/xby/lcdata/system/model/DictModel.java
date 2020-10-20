package com.xby.lcdata.system.model;

import com.xby.lcdata.system.entity.SysDictItem;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.io.Serializable;

/**
 * @FileName: com.sticker.online.main.system.model
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/21
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@DataObject(generateConverter = true)
public class DictModel implements Serializable {
    private static final long serialVersionUID = 1L;

    public DictModel() {
    }
    public DictModel(JsonObject jsonObject) {
        DictModelConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        DictModelConverter.toJson(this, json);
        return json;
    }
    public DictModel(SysDictItem sysDictItem) {
       this.text=sysDictItem.getText();
       this.description=sysDictItem.getDescription();
       this.value=sysDictItem.getId();
    }
    /**
     * 字典value
     */
    private String value;
    /**
     * 字典文本
     */
    private String text;

    /**
     * 字典描述
     */
    private String description;

    /**
     * 特殊用途： JgEditableTable
     * @return
     */
    public String getTitle() {
        return this.text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
