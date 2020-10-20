package com.xby.lcdata.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sticker.online.core.anno.Dict;
import com.sticker.online.core.anno.Excel;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.entity
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/5
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Entity
@DataObject(generateConverter = true)
@Table(name = "s_announcement")
public class SysAnnouncement implements Serializable {
    private static final long serialVersionUID = 1L;

    public SysAnnouncement() {
    }
    public SysAnnouncement(JsonObject jsonObject) {
        SysAnnouncementConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SysAnnouncementConverter.toJson(this, json);
        return json;
    }

    public SysAnnouncement(SysAnnouncement sysAnnouncement, String sendId) {
       this.id = sysAnnouncement.getId();
       this.titile = sysAnnouncement.getTitile();
       this.msgContent = sysAnnouncement.getMsgContent();
       this.startTime = sysAnnouncement.getStartTime();
       this.endTime = sysAnnouncement.getEndTime();
       this.sender = sysAnnouncement.getSender();
       this.priority = sysAnnouncement.getPriority();
       this.msgCategory = sysAnnouncement.getMsgCategory();
       this.msgType = sysAnnouncement.getMsgType();
       this.sendStatus = sysAnnouncement.getSendStatus();
       this.sendTime = sysAnnouncement.getSendTime();
       this.cancelTime = sysAnnouncement.getCancelTime();
       this.delFlag = sysAnnouncement.getDelFlag();
       this.createBy = sysAnnouncement.getCreateBy();
       this.createTime = sysAnnouncement.getCreateTime();
       this.updateTime = sysAnnouncement.getUpdateTime();
       this.updateBy = sysAnnouncement.getUpdateBy();
       this.userIds = sysAnnouncement.getUserIds();
       this.sendId =sendId;
    }

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;
    /**
     * 标题
     */
    @Excel(name = "标题", width = 15)
    @Column(name="titile")
    private String titile;
    /**
     * 内容
     */
    @Excel(name = "内容", width = 30)
    @Column(name="msg_content")
    private String msgContent;
    /**
     * 开始时间
     */
    @Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="start_time")
    private Date startTime;
    /**
     * 结束时间
     */
    @Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="end_time")
    private Date endTime;
    /**
     * 发布人
     */
    @Excel(name = "发布人", width = 15)
    @Column(name="sender")
    private String sender;
    /**
     * 优先级（L低，M中，H高）
     */
    @Excel(name = "优先级", width = 15, dicCode = "priority")
    @Dict(dicCode = "priority")
    @Column(name="priority")
    private String priority;

    /**
     * 消息类型1:通知公告2:系统消息
     */
    @Excel(name = "消息类型", width = 15, dicCode = "msg_category")
    @Dict(dicCode = "msg_category")
    @Column(name="msg_category")
    private String msgCategory;
    /**
     * 通告对象类型（USER:指定用户，ALL:全体用户）
     */
    @Excel(name = "通告对象类型", width = 15, dicCode = "msg_type")
    @Dict(dicCode = "msg_type")
    @Column(name="msg_type")
    private String msgType;
    /**
     * 发布状态（0未发布，1已发布，2已撤销）
     */
    @Excel(name = "发布状态", width = 15, dicCode = "send_status")
    @Dict(dicCode = "send_status")
    @Column(name="send_status")
    private String sendStatus;
    /**
     * 发布时间
     */
    @Excel(name = "发布时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="send_time")
    private Date sendTime;
    /**
     * 撤销时间
     */
    @Excel(name = "撤销时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="cancel_time")
    private Date cancelTime;
    /**
     * 删除状态（0，正常，1已删除）
     */
    @Column(name="del_flag")
    private String delFlag;
    /**
     * 创建人
     */
    @Column(name="create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="create_time")
    private Date createTime;
    /**
     * 更新人
     */
    @Column(name="update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="update_time")
    private Date updateTime;
    /**
     * 指定用户
     **/
    @Column(name="user_ids")
    private String userIds;

    @Transient
    private String sendId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getMsgCategory() {
        return msgCategory;
    }

    public void setMsgCategory(String msgCategory) {
        this.msgCategory = msgCategory;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }
}
