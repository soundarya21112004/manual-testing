package com.eagle.mas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.lang.model.element.Element;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

    private byte[] document;
    private String value;
    private String type;
    private String format;

}

