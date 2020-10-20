package com.xby.lcdata.system.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.io.Serializable;
import java.util.List;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.model
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/1
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@DataObject(generateConverter = true)
public class SysUserDepartsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public SysUserDepartsVO() {
    }
    public SysUserDepartsVO(JsonObject jsonObject) {
        SysUserDepartsVOConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysUserDepartsVOConverter.toJson(this, json);
        return json;
    }
    /**用户id*/
    private String userId;
    /**对应的部门id集合*/
    private List<String> departIdList;
    public SysUserDepartsVO(String userId, List<String> departIdList) {
        super();
        this.userId = userId;
        this.departIdList = departIdList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getDepartIdList() {
        return departIdList;
    }

    public void setDepartIdList(List<String> departIdList) {
        this.departIdList = departIdList;
    }
}
