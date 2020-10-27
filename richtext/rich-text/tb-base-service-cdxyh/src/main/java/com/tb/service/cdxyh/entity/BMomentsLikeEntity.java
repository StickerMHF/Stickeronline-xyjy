package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_moments_like")
public class BMomentsLikeEntity extends BaseEntity {
    public BMomentsLikeEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BMomentsLikeEntityConverter.toJson(this, json);
        return json;
    }

    public BMomentsLikeEntity(JsonObject jsonObject) {
        super(jsonObject);
        BMomentsLikeEntityConverter.fromJson(jsonObject, this);
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
     * 朋友圈ID
     */
    @Column(name = "moment_id")
    private String momentId;

    /**
     * 点赞人ID
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 点赞人头像
     */
    @Column(name = "user_photo")
    private String userPhoto;
    /**
     * 点赞人姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 点赞状态
     */
    @Column(name = "status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
