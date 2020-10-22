package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_alumnus_member")
public class BAlummunsMemberEntity extends BaseEntity {
    public BAlummunsMemberEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BAlummunsMemberEntityConverter.toJson(this, json);
        return json;
    }

    public BAlummunsMemberEntity(JsonObject jsonObject) {
        super(jsonObject);
        BAlummunsMemberEntityConverter.fromJson(jsonObject, this);
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
     * 组织活动
     */
    @Column(name = "activity")
    private String activity;

    /**
     * 电话
     */
    @Column(name = "telephone")
    private String telephone;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 工作单位
     */
    @Column(name = "organization")
    private String organization;

    /**
     *所属行业
     */
    @Column(name = "trade")
    private String trade;

    /**
     * 所属分会
     */
    @Column(name = "fid")
    private String fid;

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

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
