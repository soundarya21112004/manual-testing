package com.eagle.mas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name ="response_mvs")
public class ResponseMvs {
    private int sno;
    private String Orid;
    private String Response_Json;
    private Date CrDate;
    private String status;
@Id
@Column(name = "sno")
    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }
    @Column(name = "orid")
    public String getOrid() {
        return Orid;
    }

    public void setOrid(String orid) {
        Orid = orid;
    }
    @Column(name = "resp_json")
    public String getResponse_Json() {
        return Response_Json;
    }

    public void setResponse_Json(String response_Json) {
        Response_Json = response_Json;
    }
    @Column(name = "cr_times")
    public Date getCrDate() {
        return CrDate;
    }

    public void setCrDate(Date crDate) {
        CrDate = crDate;
    }
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
