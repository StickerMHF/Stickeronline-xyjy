package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_message")
public class BMessageEntity extends BaseEntity {
    public BMessageEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BMessageEntityConverter.toJson(this, json);
        return json;
    }

    public BMessageEntity(JsonObject jsonObject) {
        super(jsonObject);
        BMessageEntityConverter.fromJson(jsonObject, this);
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
     * 消息类型 1:校友认证;2:申请会长;3:关注消息;
     */
    @Column(name = "type")
    private Integer type;
    /**
     * 消息内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 查看状态 1:未读;2:已读;
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 操作记录ID
     */
    @Column(name = "recore_id")
    private String recoreId;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRecoreId() {
        return recoreId;
    }

    public void setRecoreId(String recoreId) {
        this.recoreId = recoreId;
    }
}
