package com.tb.service.cdxyh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_alumnus_activity_apply")
public class BAlumnusActivityApplyEntity extends BaseEntity {
    public BAlumnusActivityApplyEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BAlumnusActivityApplyEntityConverter.toJson(this, json);
        return json;
    }

    public BAlumnusActivityApplyEntity(JsonObject jsonObject) {
        super(jsonObject);
        BAlumnusActivityApplyEntityConverter.fromJson(jsonObject, this);
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
     * 用户ID
     */
    @Column(name = "userid")
    private String userId;

    /**
     * 活动ID
     */
    @Column(name = "activity_id")
    private String activityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
