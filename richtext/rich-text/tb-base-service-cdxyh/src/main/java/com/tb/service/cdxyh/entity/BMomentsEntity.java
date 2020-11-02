package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_moments")
public class BMomentsEntity extends BaseEntity {
    public BMomentsEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BMomentsEntityConverter.toJson(this, json);
        return json;
    }

    public BMomentsEntity(JsonObject jsonObject) {
        super(jsonObject);
        BMomentsEntityConverter.fromJson(jsonObject, this);
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
     * 姓名
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 照片列表
     */
    @Column(name = "photos")
    private String photos;
    /**
     * 发布内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 发布者头像
     */
    @Column(name = "user_photo")
    private String userPhoto;

    /**
     * 浏览量
     */
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     * 点赞
     */
    @Column(name = "like_count")
    private Integer likeCount;

    /**
     * 评论次数
     */
    @Column(name = "comment_count")
    private Integer commentCount;

    /**
     * 评论次数
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 评论次数
     */
    @Column(name = "status")
    private Integer status = 0;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
