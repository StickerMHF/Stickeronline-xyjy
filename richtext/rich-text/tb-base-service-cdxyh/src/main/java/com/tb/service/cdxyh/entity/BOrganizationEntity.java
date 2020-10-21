package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_organization")
public class BOrganizationEntity extends BaseEntity {
    public BOrganizationEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BOrganizationEntityConverter.toJson(this, json);
        return json;
    }

    public BOrganizationEntity(JsonObject jsonObject) {
        super(jsonObject);
        BOrganizationEntityConverter.fromJson(jsonObject, this);
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
     * 组织标题
     */
    @Column(name = "name")
    private String name;

    /**
     * 组织活动
     */
    @Column(name = "activity")
    private String activity;

    /**
     * 组织成员数量
     */
    @Column(name = "member")
    private Integer member;

    /**
     * 活跃度
     */
    @Column(name = "liveness")
    private Integer liveness;

    /**
     * 会标
     */
    @Column(name = "thumb")
    private String thumb;

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

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public Integer getLiveness() {
        return liveness;
    }

    public void setLiveness(Integer liveness) {
        this.liveness = liveness;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
