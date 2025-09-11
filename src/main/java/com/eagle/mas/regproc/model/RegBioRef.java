package com.eagle.mas.regproc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "reg_bio_ref", schema = "regprc")
public class RegBioRef {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "reg_id")
    private String regId;

    @Column(name = "bio_ref_id")
    private String bioRefId;

    @Column(name = "cr_by")
    private String crBy ;

    @Column(name = "cr_dtimes")
    private LocalDateTime crDtimes;

    @Column(name = "del_dtimes")
    private LocalDateTime delDtimes;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "upd_by")
    private String updBy;

    @Column(name = "upd_dtimes")
    private LocalDateTime updDtimes;
}
