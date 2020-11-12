package com.tb.service.cdxyh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_alumnus_activity")
public class BAlumnusActivityEntity extends BaseEntity {
    public BAlumnusActivityEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BAlumnusActivityEntityConverter.toJson(this, json);
        return json;
    }

    public BAlumnusActivityEntity(JsonObject jsonObject) {
        super(jsonObject);
        BAlumnusActivityEntityConverter.fromJson(jsonObject, this);
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
     * 活动标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 活动标题
     */
    @Column(name = "img")
    private String img;


    /**
     * 正文
     */
    @Column(name = "context")
    private String context;

    /**
     * 所属分会
     */
    @Column(name = "fid")
    private String fid;
    /**
     * 开始时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_time",updatable = false,nullable = false)
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time",updatable = false,nullable = false)
    private Date endTime;

    /**
     * 活动地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 报名截止日期
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "deadline",updatable = false,nullable = false)
    private Date deadline;

    /**
     * 类型 1:校庆活动;2:校友会活动;
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 点赞列表
     */
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="activity_id",referencedColumnName = "id")
    private Set<BAlumnusActivityApplyEntity> applyList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Set<BAlumnusActivityApplyEntity> getApplyList() {
        return applyList;
    }

    public void setApplyList(Set<BAlumnusActivityApplyEntity> applyList) {
        this.applyList = applyList;
    }
}
