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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
