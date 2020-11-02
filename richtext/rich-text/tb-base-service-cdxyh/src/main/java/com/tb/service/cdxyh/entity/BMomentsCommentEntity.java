package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_moments_comment")
public class BMomentsCommentEntity extends BaseEntity {
    public BMomentsCommentEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BMomentsCommentEntityConverter.toJson(this, json);
        return json;
    }

    public BMomentsCommentEntity(JsonObject jsonObject) {
        super(jsonObject);
        BMomentsCommentEntityConverter.fromJson(jsonObject, this);
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
     * 评论内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 朋友圈ID
     */
    @Column(name = "moment_id")
    private String momentId;
    /**
     * 评论人ID
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 评论人头像
     */
    @Column(name = "user_photo")
    private String userPhoto;
    /**
     * 评论人姓名
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 评论父ID
     */
    @Column(name = "fid")
    private String fid;
    /**
     * 审核状态
     */
    @Column(name = "status")
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
