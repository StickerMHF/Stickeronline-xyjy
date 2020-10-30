package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_barrage")
public class BBarrageEntity extends BaseEntity {
    public BBarrageEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BBarrageEntityConverter.toJson(this, json);
        return json;
    }

    public BBarrageEntity(JsonObject jsonObject) {
        super(jsonObject);
        BBarrageEntityConverter.fromJson(jsonObject, this);
    }

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(name = "id",length = 32)
    private String id;

    /**
     * 弹幕内容
     */
    @Column(name = "context")
    private String context;

    /**
     * 发布者名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 发布者ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 发布者头像
     */
    @Column(name = "user_photo")
    private String userPhoto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
