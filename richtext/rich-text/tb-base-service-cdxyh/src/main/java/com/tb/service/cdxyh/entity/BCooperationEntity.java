package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_cooperation")
public class BCooperationEntity extends BaseEntity {
    public BCooperationEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BCooperationEntityConverter.toJson(this, json);
        return json;
    }

    public BCooperationEntity(JsonObject jsonObject) {
        super(jsonObject);
        BCooperationEntityConverter.fromJson(jsonObject, this);
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
     * 合作事项
     */
    @Column(name = "title")
    private String title;
    /**
     * 合作内容
     */
    @Column(name = "contents")
    private String contents;
    /**
     * 联系方式
     */
    @Column(name = "contact")
    private String contact;
    /**
     * 审核状态
     */
    @Column(name = "status")
    private Integer status;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
