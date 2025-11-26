package com.eagle.mas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "mv_json")
public class MvJson {
    private int id;
    private String reqId;
    private String regId;
    private String matchedRefId;
    private String mvReqJson;
    private String url;
    private String reqTime;
    private Date currentTime;
    private String regType;
    private String updateStatus;

    public String getUpdateStatus() {
        return updateStatus;
    }

    @Column(name = "update_status")
    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    /* private boolean encryptionStatus;

    @Column(name = "encryption_status")
    public boolean isEncryptionStatus() {
        return encryptionStatus;
    }

    public void setEncryptionStatus(boolean encryptionStatus) {
        this.encryptionStatus = encryptionStatus;
    }*/

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "req_id")
    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }
    @Column(name = "o_rid")
    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
    @Column(name = "m_rid")
    public String getMatchedRefId() {
        return matchedRefId;
    }

    public void setMatchedRefId(String matchedRefId) {
        this.matchedRefId = matchedRefId;
    }
    @Column(name = "mv_req_json")
    public String getMvReqJson() {
        return mvReqJson;
    }

    public void setMvReqJson(String mvReqJson) {
        this.mvReqJson = mvReqJson;
    }
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @Column(name = "req_time")
    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }
    @Column(name = "curr_time")
    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    @Column(name = "reg_type")
    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }
}
