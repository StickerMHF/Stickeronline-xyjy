package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_member")
public class BMemberEntity extends BaseEntity {
    public BMemberEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BMemberEntityConverter.toJson(this, json);
        return json;
    }

    public BMemberEntity(JsonObject jsonObject) {
        super(jsonObject);
        BMemberEntityConverter.fromJson(jsonObject, this);
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
    @Column(name = "name")
    private String name;
    /**
     * 性别
     */
    @Column(name = "sex")
    private String sex;
    /**
     * 联系方式
     */
    @Column(name = "contact")
    private String contact;
    /**
     * 校友简介
     */
    @Column(name = "describe")
    private String describe;
    /**
     * 照片
     */
    @Column(name = "photo")
    private String photo;

    /**
     * 是否关注
     */
    @Transient
    private Integer attention=0;


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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getAttention() {
        return attention;
    }

    public void setAttention(Integer attention) {
        this.attention = attention;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
