package com.xby.lcdata.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: SysOperationLog
 * @Description:
 * @Author: wangf
 * @Date: 2019/8/26 0026 10:08
 * @Version: 1.0
 **/
@Entity
@Table(name = "s_operation_log",  catalog = "")
public class SysOperationLog {
    private String id;
    private String ip;
    private String operationType;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date oparationTime;
    private String operationResult;
    private String operator;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "operation_type")
    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Basic
    @Column(name = "oparation_time")
    public Date getOparationTime() {
        return oparationTime;
    }

    public void setOparationTime(Date oparationTime) {
        this.oparationTime = oparationTime;
    }

    @Basic
    @Column(name = "operation_result")
    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    @Basic
    @Column(name = "operator")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
