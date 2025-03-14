package com.eagle.mas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdenetityDto {
    private Map<String, String> presentAddressLine1;
    private Map<String, String> presentZipcode;
    private Map<String, String> presentProvince;
    private Map<String, String> lastName;
    private Map<String, String> presentAddressLine2;
    private Map<String, String> presentAddressLine3;
    private Map<String, String> pobCountry;
    private Map<String, String> presentAddressLine4;
    private Map<String, String> proofOfAddress;
    private Map<String, String> pobProvince;
    private Map<String, String> gender;
    private Map<String, String> proofOfConsent;
    private Map<String, String> permanentBarangay;
    private Map<String, String> mobileno;
    private Map<String, String> suffix;
    private Map<String, String> bloodType;
    private Map<String, String> individualBiometrics;
    private Map<String, String> presentBarangay;
    private Map<String, String> proofOfDateOfBirth;
    private Map<String, String> residenceStatus;
    private Map<String, String> email;
    private Map<String, String> permanentZipcode;
    private Map<String, String> pobCity;
    private Map<String, String> dateOfBirth;
    private Map<String, String> presentCity;
    private Map<String, String> firstName;
    private Map<String, String> proofOfIdentity;
    private Map<String, String> permanentAddressLine1;
    private Map<String, String> proofOfException;
    private Map<String, String> permanentCountry;
    private Map<String, String> presentCountry;
    private Map<String, String> permanentProvince;
    private Map<String, String> middleName;
    private Map<String, String> proofOfRelationship;
    private Map<String, String> permanentCity;
    private Map<String, String> permanentAddressLine2;
    private Map<String, String> permanentAddressLine3;
    private Map<String, String> permanentAddressLine4;
    private Map<String, String> maritalStatus;
}
