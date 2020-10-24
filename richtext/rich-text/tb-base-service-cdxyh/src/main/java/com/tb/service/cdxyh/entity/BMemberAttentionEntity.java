package com.tb.service.cdxyh.entity;

import com.sticker.online.core.entity.BaseEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DataObject(generateConverter = true)
@Table(name = "b_member_attention")
public class BMemberAttentionEntity  extends BaseEntity {
public BMemberAttentionEntity() {
        }
        public JsonObject toJson() {
            JsonObject json = new JsonObject();
            json = super.toJson();
            BMemberAttentionEntityConverter.toJson(this, json);
            return json;
        }

        public BMemberAttentionEntity(JsonObject jsonObject) {
            super(jsonObject);
            BMemberAttentionEntityConverter.fromJson(jsonObject, this);
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
     * 校友ID
     */
    @Column(name = "member_id")
    private String memberId;
    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;


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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
