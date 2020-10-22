package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_teachers")
public class BTeachersEntity extends BaseEntity {
    public BTeachersEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BTeachersEntityConverter.toJson(this, json);
        return json;
    }

    public BTeachersEntity(JsonObject jsonObject) {
        super(jsonObject);
        BTeachersEntityConverter.fromJson(jsonObject, this);
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
     * 职称
     */
    @Column(name = "rank")
    private String rank;
    /**
     * 所属学院
     */
    @Column(name = "college")
    private String college;
    /**
     * 学历
     */
    @Column(name = "education")
    private String education;
    /**
     * 毕业院校
     */
    @Column(name = "byyx")
    private String byyx;
    /**
     * 邮件
     */
    @Column(name = "email")
    private String email;
    /**
     * 办公地点
     */
    @Column(name = "bgdd")
    private String bgdd;

    /**
     * 浏览量
     */
    @Column(name = "view_count")
    private Integer viewCount;

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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getByyx() {
        return byyx;
    }

    public void setByyx(String byyx) {
        this.byyx = byyx;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBgdd() {
        return bgdd;
    }

    public void setBgdd(String bgdd) {
        this.bgdd = bgdd;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
