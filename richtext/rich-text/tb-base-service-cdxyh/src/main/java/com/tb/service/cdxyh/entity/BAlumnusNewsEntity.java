package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_alumnus_news")
public class BAlumnusNewsEntity extends BaseEntity {
    public BAlumnusNewsEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BAlumnusNewsEntityConverter.toJson(this, json);
        return json;
    }

    public BAlumnusNewsEntity(JsonObject jsonObject) {
        super(jsonObject);
        BAlumnusNewsEntityConverter.fromJson(jsonObject, this);
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
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 作者
     */
    @Column(name = "author")
    private String author;

    /**
     * 正文
     */
    @Column(name = "context")
    private String context;

    /**
     * 照片
     */
    @Column(name = "img")
    private String img;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
