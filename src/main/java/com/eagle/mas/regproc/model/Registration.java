package com.eagle.mas.regproc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "registration", schema ="regprc")
@Entity
public class Registration {
    @Id
    @Column(name = "id")
    private String regId;

    @Column(name = "reg_type")
    private String registrationType;
}
