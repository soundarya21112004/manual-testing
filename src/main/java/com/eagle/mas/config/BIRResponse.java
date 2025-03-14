package com.eagle.mas.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

// Root element BIR
@JsonRootName("BIR")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BIRResponse {

    @JacksonXmlProperty(isAttribute = true)
    @JsonProperty(value = "http://standards.iso.org/iso-iec/19785/-3/ed-2/")
    private String xmlns;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonProperty("BIR")
    private List<BIR> birList;

    // Getters and Setters
    public List<BIR> getBirList() {
        return birList;
    }

    public void setBirList(List<BIR> birList) {
        this.birList = birList;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class Version {
    private int major;
    private int minor;

}
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class BIRInfo {
    @JsonProperty("Integrity")
    private boolean integrity;
    private String creator;
    private String index;
    private byte[] payload;
    private LocalDateTime creationDate;
    private LocalDateTime notValidBefore;
    private LocalDateTime notValidAfter;

    @JacksonXmlProperty(isAttribute = false)
    public boolean isIntegrity() {
        return integrity;
    }

    public void setIntegrity(boolean integrity) {
        this.integrity = integrity;
    }


}
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class BDBInfo {
    @JsonProperty("format")
    private Format format;

    @JsonProperty("creationDate")
    private String creationDate;

    @JsonProperty("type")
    private String[] type;

    @JsonProperty("subtype")
    private String[] subtype;

    @JsonProperty("level")
    private String level;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("quality")
    private Quality quality;

    public String getType() {
        // Extract the first element from the array and return it
        return type != null && type.length > 0 ? type[0] : null;
    }

    public String getSubtype() {
        // Extract the first element from the array and return it
//        return subtype != null && subtype.length > 0 ? subtype[0] : null;
        String subTypeString= "";
        for(String s: subtype){
            subTypeString = subTypeString.concat(" ").concat(s);
        }
        return subTypeString.trim();
    }

}
@Data
class Format {
    @JsonProperty("organization")
    private String organization;

    @JsonProperty("type")
    private String type;
}
@Data
class Quality {
    @JsonProperty("algorithm")
    private Algorithm algorithm;
    @JsonProperty("qualityCalculationFailed")
    private String qualityCalculationFailed;
    @JsonProperty("score")
    private int score;

}
@Data
class Algorithm {
    @JsonProperty("organization")
    private String organization;

    @JsonProperty("type")
    private String type;
}
