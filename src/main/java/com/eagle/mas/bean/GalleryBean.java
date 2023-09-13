package com.eagle.mas.bean;

import java.io.Serializable;

public class GalleryBean implements Serializable {


	private static final long serialVersionUID = 1L;

	private String matchedRefType;
	private String matchedRegId;
	private String reasonCode;
	private String matchingScore;
	private String url;
	private String firstName;
	private String lastName;
	private String presentProvince;
	private String middleName;
	private String presentAddressLine1;
	private String presentCity;
	private String presentCountry;
	private String presentZipcode;
	private String pobCountry;
	private String bloodType;
	private String residenceStatus;
	private String permanentZipcode;
	private String permanentAddressLine1;
	private String subDivision;

	public String getSubDivision() {
		return subDivision;
	}

	public void setSubDivision(String subDivision) {
		this.subDivision = subDivision;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	private String registrationType;
	private String regId;
	private String count;

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	private String maritalStatus;
	private String permanentCountry;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String permanentCity;
	private String permanentProvince;
	private String probeIrisImage;
	private String originalIrisImage;
	private String type;
	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	private String creationdate;
	private String officer;
	private String email;
	private String mobileno;
	private String regCenterId;

	public String getRegCenterId() {
		return regCenterId;
	}

	public void setRegCenterId(String regCenterId) {
		this.regCenterId = regCenterId;
	}

	public String getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}

	public String getOfficer() {
		return officer;
	}

	public void setOfficer(String officer) {
		this.officer = officer;
	}

	private String permanentBarangay;
	private String presentBarangay;
	private String pobCity;
	private String pobProvince;
	private String rid;
	private String fileText;
	private String suffix;
	private String registrationId;

	public String getPresentCity() {
		return presentCity;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getFileText() {
		return fileText;
	}

	public void setFileText(String fileText) {
		this.fileText = fileText;
	}

	public void setPresentCity(String presentCity) {
		this.presentCity = presentCity;
	}

	public String getPresentCountry() {
		return presentCountry;
	}

	public void setPresentCountry(String presentCountry) {
		this.presentCountry = presentCountry;
	}

	public String getPermanentCity() {
		return permanentCity;
	}

	public void setPermanentCity(String permanentCity) {
		this.permanentCity = permanentCity;
	}

	public String getProbeIrisImage() {
		return probeIrisImage;
	}

	public void setProbeIrisImage(String probeIrisImage) {
		this.probeIrisImage = probeIrisImage;
	}

	public String getOriginalIrisImage() {
		return originalIrisImage;
	}

	public void setOriginalIrisImage(String originalIrisImage) {
		this.originalIrisImage = originalIrisImage;
	}

	private String probeId;
	private String originalId;

	public String getProbeId() {
		return probeId;
	}


	public void setProbeId(String probeId) {
		this.probeId = probeId;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	private String score;

	private String fingerImage;

	public String getFingerImage() {
		return fingerImage;
	}

	public void setFingerImage(String fingerImage) {
		this.fingerImage = fingerImage;
	}

	public String getScore() {
		return score;
	}

	public String setScore(String score) {
		this.score = score;
		return score;
	}

	private String dayOfBirth;
	public String getDayOfBirth() {
		return dayOfBirth;
	}
	public void setDayOfBirth(String dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	private String yearOfBirth;
	public String getYearOfBirth() {
		return yearOfBirth;
	}
	public void setYearOfBirth(String yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	private String dateOfBirth;
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	private String monthOfBirth;
	public String getMonthOfBirth() {
		return monthOfBirth;
	}
	public void setMonthOfBirth(String monthOfBirth) {
		this.monthOfBirth = monthOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	private String gender;


	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getPermanentCountry() {
		return permanentCountry;
	}

	public void setPermanentCountry(String permanentCountry) {
		this.permanentCountry = permanentCountry;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPresentProvince() {
		return presentProvince;
	}

	public void setPresentProvince(String presentProvince) {
		this.presentProvince = presentProvince;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPresentAddressLine1() {
		return presentAddressLine1;
	}

	public void setPresentAddressLine1(String presentAddressLine1) {
		this.presentAddressLine1 = presentAddressLine1;
	}

	public String getPresentZipcode() {
		return presentZipcode;
	}

	public void setPresentZipcode(String presentZipcode) {
		this.presentZipcode = presentZipcode;
	}

	public String getPobCountry() {
		return pobCountry;
	}

	public void setPobCountry(String pobCountry) {
		this.pobCountry = pobCountry;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getResidenceStatus() {
		return residenceStatus;
	}

	public void setResidenceStatus(String residenceStatus) {
		this.residenceStatus = residenceStatus;
	}

	public String getPermanentZipcode() {
		return permanentZipcode;
	}

	public void setPermanentZipcode(String permanentZipcode) {
		this.permanentZipcode = permanentZipcode;
	}

	public String getPermanentAddressLine1() {
		return permanentAddressLine1;
	}

	public void setPermanentAddressLine1(String permanentAddressLine1) {
		this.permanentAddressLine1 = permanentAddressLine1;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public String getMatchedRefType() {
		return matchedRefType;
	}

	public void setMatchedRefType(String matchedRefType) {
		this.matchedRefType = matchedRefType;
	}

	public String getMatchedRegId() {
		return matchedRegId;
	}

	public void setMatchedRegId(String matchedRegId) {
		this.matchedRegId = matchedRegId;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMatchingScore() {
		return matchingScore;
	}

	public void setMatchingScore(String matchingScore) {
		this.matchingScore = matchingScore;
	}

	public String getPermanentProvince() {
		return permanentProvince;
	}

	public void setPermanentProvince(String permanentProvince) {
		this.permanentProvince = permanentProvince;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getPermanentBarangay() {
		return permanentBarangay;
	}

	public void setPermanentBarangay(String permanentBarangay) {
		this.permanentBarangay = permanentBarangay;
	}

	public String getPresentBarangay() {
		return presentBarangay;
	}

	public void setPresentBarangay(String presentBarangay) {
		this.presentBarangay = presentBarangay;
	}

	public String getPobCity() {
		return pobCity;
	}

	public void setPobCity(String pobCity) {
		this.pobCity = pobCity;
	}

	public String getPobProvince() {
		return pobProvince;
	}

	public void setPobProvince(String pobProvince) {
		this.pobProvince = pobProvince;
	}
	private String IrscoresProb;
	private String scoreProbe;

	public String getIrscoresProb() {
		return IrscoresProb;
	}

	public void setIrscoresProb(String irscoresProb) {
		IrscoresProb = irscoresProb;
	}

	public String getScoreProbe() {
		return scoreProbe;
	}

	public void setScoreProbe(String scoreProbe) {
		this.scoreProbe = scoreProbe;
	}
	private String leftiris;
	private String rightiris;
	private String leftmiddlefinger;
	private String leftindexfinger;
	private String leftlittlefinger;

	public String getLeftiris() {
		return leftiris;
	}

	public void setLeftiris(String leftiris) {
		this.leftiris = leftiris;
	}

	public String getRightiris() {
		return rightiris;
	}

	public void setRightiris(String rightiris) {
		this.rightiris = rightiris;
	}

	public String getLeftmiddlefinger() {
		return leftmiddlefinger;
	}

	public void setLeftmiddlefinger(String leftmiddlefinger) {
		this.leftmiddlefinger = leftmiddlefinger;
	}

	public String getLeftindexfinger() {
		return leftindexfinger;
	}

	public void setLeftindexfinger(String leftindexfinger) {
		this.leftindexfinger = leftindexfinger;
	}

	public String getLeftlittlefinger() {
		return leftlittlefinger;
	}

	public void setLeftlittlefinger(String leftlittlefinger) {
		this.leftlittlefinger = leftlittlefinger;
	}

	public String getLeftringfinger() {
		return leftringfinger;
	}

	public void setLeftringfinger(String leftringfinger) {
		this.leftringfinger = leftringfinger;
	}

	public String getLeftthumb() {
		return leftthumb;
	}

	public void setLeftthumb(String leftthumb) {
		this.leftthumb = leftthumb;
	}

	public String getRightmiddlefinger() {
		return rightmiddlefinger;
	}

	public void setRightmiddlefinger(String rightmiddlefinger) {
		this.rightmiddlefinger = rightmiddlefinger;
	}

	public String getRightindexfinger() {
		return rightindexfinger;
	}

	public void setRightindexfinger(String rightindexfinger) {
		this.rightindexfinger = rightindexfinger;
	}

	public String getRightlittlefinger() {
		return rightlittlefinger;
	}

	public void setRightlittlefinger(String rightlittlefinger) {
		this.rightlittlefinger = rightlittlefinger;
	}

	public String getRightringfinger() {
		return rightringfinger;
	}

	public void setRightringfinger(String rightringfinger) {
		this.rightringfinger = rightringfinger;
	}

	public String getLeftfingerProb() {
		return leftfingerProb;
	}

	public void setLeftfingerProb(String leftfingerProb) {
		this.leftfingerProb = leftfingerProb;
	}

	public String getRightfingerProb() {
		return rightfingerProb;
	}

	public void setRightfingerProb(String rightfingerProb) {
		this.rightfingerProb = rightfingerProb;
	}

	public String getSubtype() {
		return Subtype;
	}

	public void setSubtype(String subtype) {
		Subtype = subtype;
	}

	public String getRightthumb() {
		return rightthumb;
	}

	public void setRightthumb(String rightthumb) {
		this.rightthumb = rightthumb;
	}

	private String leftringfinger;
	private String leftthumb;
	private String rightmiddlefinger;
	private String rightindexfinger;
	private String rightlittlefinger;
	private String rightringfinger;
	private String rightthumb;
	private String leftfingerProb;
	private String rightfingerProb;
	private String Subtype;
	private String creationtime;

	public String getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(String creationtime) {
		this.creationtime = creationtime;
	}
}
