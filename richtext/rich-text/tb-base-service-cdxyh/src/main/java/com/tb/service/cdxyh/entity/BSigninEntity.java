package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_signin")
public class BSigninEntity extends BaseEntity {
    public BSigninEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BSigninEntityConverter.toJson(this, json);
        return json;
    }

    public BSigninEntity(JsonObject jsonObject) {
        super(jsonObject);
        BSigninEntityConverter.fromJson(jsonObject, this);
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
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 用户头像
     */
    @Column(name = "user_photo")
    private String userPhoto;
    /**
     * 签到地点
     */
    @Column(name = "location")
    private String location;
    /**
     * 签到次序
     */
    @Column(name = "order_num")
    private long orderNum;
    /**
     * 所在国家
     */
    @Column(name = "country")
    private String country;
    /**
     * 所在城市
     */
    @Column(name = "city")
    private String city;


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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
