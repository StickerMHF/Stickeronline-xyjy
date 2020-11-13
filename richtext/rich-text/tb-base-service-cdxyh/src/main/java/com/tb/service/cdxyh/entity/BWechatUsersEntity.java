package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "w_wechat_users")
public class BWechatUsersEntity  extends BaseEntity {
    public BWechatUsersEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BWechatUsersEntityConverter.toJson(this, json);
        return json;
    }

    public BWechatUsersEntity(JsonObject jsonObject) {
        super(jsonObject);
        BWechatUsersEntityConverter.fromJson(jsonObject, this);
    }


    /**
     * 微信ID
     */
    @Id
    @Column(name = "id")
    private String openid;


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
     * 身份证号
     */
    @Column(name = "identity_card")
    private String identityCard;
    /**
     * 所属学院
     */
    @Column(name = "college")
    private String college;
    /**
     * 所在专业
     */
    @Column(name = "profession")
    private String profession;
    /**
     * 学历
     */
    @Column(name = "education")
    private String education;
    /**
     * 入校时间
     */
    @Column(name = "start_date")
    private String startDate;
    /**
     * 离校时间
     */
    @Column(name = "end_date")
    private String endDate;
    /**
     * 类型 1:曾在校学习或工作;2:在校生;3:教职工
     */
    @Column(name = "type")
    private String type;
    /**
     * 微信昵称
     */
    @Column(name = "nick_name")
    private String nickName;
    /**
     * 市
     */
    @Column(name = "city")
    private String city;
    /**
     * 省
     */
    @Column(name = "province")
    private String province;
    /**
     * 国家
     */
    @Column(name = "country")
    private String country;
    /**
     * 微信头像
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * 名字首字母
     */
    @Column(name = "name_initial")
    private String 	nameInitial;

    /**
     * 班级
     */
    @Column(name = "class_grade")
    private String 	classGrade;
    /**
     * 学号
     */
    @Column(name = "student_number")
    private String 	studentNumber;
    /**
     * 邮箱
     */
    @Column(name = "email")
    private String 	email;
    /**
     * QQ
     */
    @Column(name = "qq")
    private String 	qq;
    /**
     * 住址
     */
    @Column(name = "address")
    private String 	address;
    /**
     * 单位名称
     */
    @Column(name = "company")
    private String 	company;
    /**
     * 职位
     */
    @Column(name = "job_title")
    private String 	jobTitle;
    /**
     * 电话
     */
    @Column(name = "phone")
    private String 	phone;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String 	remark;
    /**
     * 微信
     */
    @Column(name = "wechat")
    private String 	wechat;


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNameInitial() {
        return nameInitial;
    }

    public void setNameInitial(String nameInitial) {
        this.nameInitial = nameInitial;
    }

    public String getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(String classGrade) {
        this.classGrade = classGrade;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
}
