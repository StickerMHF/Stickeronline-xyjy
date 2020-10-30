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
     * 照片
     */
    @Column(name = "photos")
    private String photos;
    /**
     * 浏览量
     */
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     * 个人简介
     */
    @Column(name = "grjj")
    private String grjj;
    /**
     * 研究领域
     */
    @Column(name = "yjly")
    private String yjly;
    /**
     * 科研项目
     */
    @Column(name = "kyxm")
    private String kyxm;
    /**
     * 论文
     */
    @Column(name = "lw")
    private String lw;
    /**
     * 科技成果
     */
    @Column(name = "kjcg")
    private String kjcg;
    /**
     * 荣誉奖励
     */
    @Column(name = "ryjl")
    private String ryjl;
    /**
     * 工作经历
     */
    @Column(name = "gzjl")
    private String gzjl;

    /**
     * 社会职务
     */
    private String shzw;

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

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getGrjj() {
        return grjj;
    }

    public void setGrjj(String grjj) {
        this.grjj = grjj;
    }

    public String getYjly() {
        return yjly;
    }

    public void setYjly(String yjly) {
        this.yjly = yjly;
    }

    public String getKyxm() {
        return kyxm;
    }

    public void setKyxm(String kyxm) {
        this.kyxm = kyxm;
    }

    public String getLw() {
        return lw;
    }

    public void setLw(String lw) {
        this.lw = lw;
    }

    public String getKjcg() {
        return kjcg;
    }

    public void setKjcg(String kjcg) {
        this.kjcg = kjcg;
    }

    public String getRyjl() {
        return ryjl;
    }

    public void setRyjl(String ryjl) {
        this.ryjl = ryjl;
    }

    public String getShzw() {
        return shzw;
    }

    public void setShzw(String shzw) {
        this.shzw = shzw;
    }

    public String getGzjl() {
        return gzjl;
    }

    public void setGzjl(String gzjl) {
        this.gzjl = gzjl;
    }
}
