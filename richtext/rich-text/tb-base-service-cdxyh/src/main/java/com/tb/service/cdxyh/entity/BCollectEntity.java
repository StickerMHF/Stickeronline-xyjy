package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_collect")
public class BCollectEntity extends BaseEntity {
    public BCollectEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BCollectEntityConverter.toJson(this, json);
        return json;
    }

    public BCollectEntity(JsonObject jsonObject) {
        super(jsonObject);
        BCollectEntityConverter.fromJson(jsonObject, this);
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
    @Column(name = "user_id")
    private String userId;
    /**
     * 收藏类型 1:新闻资讯;2:其他;
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 收藏记录ID
     */
    @Column(name = "record_id")
    private String recordId;
    /**
     * 收藏记录ID
     */
    @Column(name = "record_title")
    private String recordTitle;
//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name="moment_id",referencedColumnName = "id")
//    @Where(clause="status=1")
//    @OrderBy("create_time ASC")
//    private Set<BMomentsCommentEntity> commentList;


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordTitle() {
        return recordTitle;
    }

    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }
}
