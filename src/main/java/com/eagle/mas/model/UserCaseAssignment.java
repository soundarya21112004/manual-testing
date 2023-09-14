package com.eagle.mas.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "user_id_case_mapping")
public class UserCaseAssignment {


    @Column(name = "request_id")
    private String requestId;

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "case_pickup_dtimes")
    private LocalDateTime pickupDtimes;
}
