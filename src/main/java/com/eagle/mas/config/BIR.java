package com.eagle.mas.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BIR {

    private Version version;
    private Version cbeffversion;

    @JsonProperty("birInfo")
    private BIRInfo birInfo;

    @JsonProperty("bdbInfo")
    private BDBInfo bdbInfo;
    @JsonProperty("bdb")
    private String bdb;

    private String sb;
    private String sbInfo;
    private String others;


}