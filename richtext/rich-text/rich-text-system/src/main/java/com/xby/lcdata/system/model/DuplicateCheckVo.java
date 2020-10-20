package com.xby.lcdata.system.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.io.Serializable;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.model
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/2
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@DataObject(generateConverter = true)
public class DuplicateCheckVo  implements Serializable {
    private static final long serialVersionUID = 1L;

    public DuplicateCheckVo() {
    }
    public DuplicateCheckVo(JsonObject jsonObject) {
        DuplicateCheckVoConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        DuplicateCheckVoConverter.toJson(this, json);
        return json;
    }
    /**
     * 表名
     */
//    @ApiModelProperty(value="表名",name="tableName",example="sys_log")
    private String tableName;

    /**
     * 字段名
     */
//    @ApiModelProperty(value="字段名",name="fieldName",example="id")
    private String fieldName;

    /**
     * 字段值
     */
//    @ApiModelProperty(value="字段值",name="fieldVal",example="1000")
    private String fieldVal;

    /**
     * 数据ID
     */
//    @ApiModelProperty(value="数据ID",name="dataId",example="2000")
    private String dataId;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldVal() {
        return fieldVal;
    }

    public void setFieldVal(String fieldVal) {
        this.fieldVal = fieldVal;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
}