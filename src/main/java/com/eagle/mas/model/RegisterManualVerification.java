package com.eagle.mas.model;

import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@ToString
@Table(name = "register_manual_verification")
public class RegisterManualVerification implements Serializable  {

    private int sno;
    private String regId;
    private String matchedRefId;
    private String trntypcode;
    private String createdBy;
    private String matchingScore;
    private Date createdDate;
    private String proStatus;
    private String statusCode;

    private String op1UpdBy;
    private Date op1updDate;
    private String op1verifyStatus;
    private String op1Comment;


    private Date op2UpdatedDate;
    private String op2UpdBy;
    private String op2Comment;
    private String op2verifyStatus;


    private Date supervisorUpdatedDate;
    private String supervisorUpdBy;
    private String supervisorComment;
    private String supervisorVerifyStatus;

    private String op1userId;
    private String op2userId;
    private String userId;
    private String reqid;
    private String finindi;

    private String priority;

    private String regType;

    private String candidateRegType;

    @Column(name = "candidate_reg_type")
    public String getCandidateRegType() {
        return candidateRegType;
    }

    public void setCandidateRegType(String candidateRegType) {
        this.candidateRegType = candidateRegType;
    }

    @Column(name = "reg_type")
    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    private int caseEvaluationComplete;

    @Column(name = "case_evaluation_complete")
    public int getCaseEvaluationComplete() {
        return caseEvaluationComplete;
    }

    public void setCaseEvaluationComplete(int caseEvaluationComplete) {
        this.caseEvaluationComplete = caseEvaluationComplete;
    }

    @Id
    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }
    @Column(name = "operator1_user_id")
    public String getOp1userId() {
        return op1userId;
    }

    public void setOp1userId(String op1userId) {
        this.op1userId = op1userId;
    }
    @Column(name = "operator2_user_id")
    public String getOp2userId() {
        return op2userId;
    }

    public void setOp2userId(String op2userId) {
        this.op2userId = op2userId;
    }



    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
//    @Column(name = "user_id")
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }

    @Column(name = "reg_id")
    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
    @Column(name = "matched_ref_id")
    public String getMatchedRefId() {
        return matchedRefId;
    }

    public void setMatchedRefId(String matchedRefId) {
        this.matchedRefId = matchedRefId;
    }
    @Column(name = "trntype_code")
    public String getTrntypcode() {
        return trntypcode;
    }

    public void setTrntypcode(String trntypcode) {
        this.trntypcode = trntypcode;
    }
    @Column(name = "cr_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    @Column(name = "matched_score")
    public String getMatchingScore() {
        return matchingScore;
    }

    public void setMatchingScore(String matchingScore) {
        this.matchingScore = matchingScore;
    }
    @Column(name = "cr_date")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    @Column(name = "process_code")
    public String getProStatus() {
        return proStatus;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }
    @Column(name = "status_code")
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    @Column(name = "operator1_upd_by")
    public String getOp1UpdBy() {
        return op1UpdBy;
    }

    public void setOp1UpdBy(String op1UpdBy) {
        this.op1UpdBy = op1UpdBy;
    }
    @Column(name = "operator1_upd_date")
    public Date getOp1updDate() {
        return op1updDate;
    }

    public void setOp1updDate(Date op1updDate) {
        this.op1updDate = op1updDate;
    }
    @Column(name = "operator1_verify_status")
    public String getOp1verifyStatus() {
        return op1verifyStatus;
    }

    public void setOp1verifyStatus(String op1verifyStatus) {
        this.op1verifyStatus = op1verifyStatus;
    }
    @Column(name = "operator1_comment")
    public String getOp1Comment() {
        return op1Comment;
    }

    public void setOp1Comment(String op1Comment) {
        this.op1Comment = op1Comment;
    }
    @Column(name = "operator2_upd_date")
    public Date getOp2UpdatedDate() {
        return op2UpdatedDate;
    }

    public void setOp2UpdatedDate(Date op2UpdatedDate) {
        this.op2UpdatedDate = op2UpdatedDate;
    }
    @Column(name = "operator2_upd_by")
    public String getOp2UpdBy() {
        return op2UpdBy;
    }

    public void setOp2UpdBy(String op2UpdBy) {
        this.op2UpdBy = op2UpdBy;
    }
    @Column(name = "operator2_comment")
    public String getOp2Comment() {
        return op2Comment;
    }

    public void setOp2Comment(String op2Comment) {
        this.op2Comment = op2Comment;
    }
    @Column(name = "operator2_verify_status")
    public String getOp2verifyStatus() {
        return op2verifyStatus;
    }

    public void setOp2verifyStatus(String op2verifyStatus) {
        this.op2verifyStatus = op2verifyStatus;
    }
    @Column(name = "supervisor_upd_date")
    public Date getSupervisorUpdatedDate() {
        return supervisorUpdatedDate;
    }

    public void setSupervisorUpdatedDate(Date supervisorUpdatedDate) {
        this.supervisorUpdatedDate = supervisorUpdatedDate;
    }
    @Column(name = "supervisor_upd_by")
    public String getSupervisorUpdBy() {
        return supervisorUpdBy;
    }

    public void setSupervisorUpdBy(String supervisorUpdBy) {
        this.supervisorUpdBy = supervisorUpdBy;
    }
    @Column(name = "supervisor_comment")
    public String getSupervisorComment() {
        return supervisorComment;
    }

    public void setSupervisorComment(String supervisorComment) {
        this.supervisorComment = supervisorComment;
    }

    @Column(name = "supervisor_verify_status")
    public String getSupervisorVerifyStatus() {
        return supervisorVerifyStatus;
    }

    public void setSupervisorVerifyStatus(String supervisorVerifyStatus) {
        this.supervisorVerifyStatus = supervisorVerifyStatus;
    }
    @Column(name="req_id")
    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    @Column(name="Fin_indi")
    public String getFinindi() {
        return finindi;
    }

    public void setFinindi(String finindi) {
        this.finindi = finindi;
    }


    @Column(name = "priority")
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


}
