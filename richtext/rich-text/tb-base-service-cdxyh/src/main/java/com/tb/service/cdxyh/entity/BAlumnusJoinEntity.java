package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_alumnus_join")
public class BAlumnusJoinEntity extends BaseEntity {
    public BAlumnusJoinEntity() {
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json = super.toJson();
        BAlumnusJoinEntityConverter.toJson(this, json);
        return json;
    }

    public BAlumnusJoinEntity(JsonObject jsonObject) {
        super(jsonObject);
        BAlumnusJoinEntityConverter.fromJson(jsonObject, this);
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
     * 组织ID
     */
    @Column(name = "alumnus_id")
    private String alumnusId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户头像
     */
    @Column(name = "user_photo")
    private String userPhoto;

    /**
     * 关注状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 审核状态：2:审核通过;0:未申请;-1:审核不通过;1:待审核
     */
    @Column(name = "check_state")
    private Integer checkState;
    /**
     * 身份: 1:会长;2:副会长;0:成员
     */
    @Column(name = "president")
    private Integer president;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlumnusId() {
        return alumnusId;
    }

    public void setAlumnusId(String alumnusId) {
        this.alumnusId = alumnusId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    public Integer getPresident() {
        return president;
    }

    public void setPresident(Integer president) {
        this.president = president;
    }
}
