package com.xby.lcdata.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ClassName: SysAnnouncementUser
 * @Description:
 * @Author: wangf
 * @Date: 2019/8/19 0019 17:44
 * @Version: 1.0
 **/
@Entity
@Table(name = "s_announcement")
public class SysAnnouncementUser {

    /**
     * id
     */
    @Id
    @Column(name = "send_id")
    private String id;

    /**
     * id
     */
    @Column(name = "id")
    private String msgId;

    /**
     * 标题
     */
    @Column(name = "titile")
    private String titile;
    /**
     * 内容
     */
    @Column(name = "msg_content")
    private String msgContent;
    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;
    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;
    /**
     * 发布人
     */
    @Column(name = "sender")
    private String sender;
    /**
     * 优先级（L低，M中，H高）
     */
    @Column(name = "priority")
    private String priority;

    /**
     * 消息类型1:通知公告2:系统消息
     */
    @Column(name = "msg_category")
    private String msgCategory;
    /**
     * 通告对象类型（USER:指定用户，ALL:全体用户）
     */
    @Column(name = "msg_type")
    private String msgType;
    /**
     * 发布状态（0未发布，1已发布，2已撤销）
     */
    @Column(name = "send_status")
    private String sendStatus;
    /**
     * 发布时间
     */
    @Column(name = "send_time")
    private Date sendTime;
    /**
     * 撤销时间
     */
    @Column(name = "cancel_time")
    private Date cancelTime;
    /**
     * 删除状态（0，正常，1已删除）
     */
    @Column(name = "del_flag")
    private String delFlag;
    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
     * 更新人
     */
    @Column(name = "update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 指定用户
     **/
    @Column(name = "user_ids")
    private String userIds;

    @Column(name = "read_flag")
    private Integer readFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
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

    public Integer getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(Integer readFlag) {
        this.readFlag = readFlag;
    }
}
