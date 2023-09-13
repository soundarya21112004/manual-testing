package com.eagle.mas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "bioscore")
public class BioScore {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "reg_id")
    private String regID;

    @Column(name = "matched_ref_id")
    private String matchedRefId;

    @Column(name = "req_id")
    private String reqId;

    @Column(name = "bio_ref_id")
    private String bioRefId;

    @Column(name = "resp_text")
    private String responseText;

    @Column(name = "cr_dtimes_reg_id")
    private LocalDateTime crTimesRegId;

    @Column(name = "cr_dtimes_matched_id")
    private LocalDateTime crTimesMatchedId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegID() {
        return regID;
    }

    public void setRegID(String regID) {
        this.regID = regID;
    }

    public String getMatchedRefId() {
        return matchedRefId;
    }

    public void setMatchedRefId(String matchedRefId) {
        this.matchedRefId = matchedRefId;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getBioRefId() {
        return bioRefId;
    }

    public void setBioRefId(String bioRefId) {
        this.bioRefId = bioRefId;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public LocalDateTime getCrTimesRegId() {
        return crTimesRegId;
    }

    public void setCrTimesRegId(LocalDateTime crTimesRegId) {
        this.crTimesRegId = crTimesRegId;
    }

    public LocalDateTime getCrTimesMatchedId() {
        return crTimesMatchedId;
    }

    public void setCrTimesMatchedId(LocalDateTime crTimesMatchedId) {
        this.crTimesMatchedId = crTimesMatchedId;
    }
}
