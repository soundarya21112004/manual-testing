package com.eagle.mas.idrepo.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The Class Uin -Entity class for uin table.
 *
 * @author Manoj SP
 */
@Getter
@Setter
@ToString(exclude = { "biometrics", "documents" })
@Entity
@NoArgsConstructor
@Table(name = "uin_h", schema = "idrepo")
public class Uin {

	/** The uin ref id. */
	@Id
	@Column(name="uin_ref_id",insertable = false, updatable = false, nullable = false)
	private String uinRefId;
	/** The uin. */
	@Column(name="uin")
	private String uin;

	@Column(name = "uin_hash")
	private String uinHash;

	/** The uin data. */
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	@Basic(fetch = FetchType.LAZY)
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Column(name = "uin_data")
	private byte[] uinData;

	/** The uin data hash. */
	@Column(name = "uin_data_hash")
	private String uinDataHash;

	/** The reg id. */
	@Column(name = "reg_id")
	private String regId;

	@Column(name = "bio_ref_id")
	private String bioRefId;

	/** The status code. */
	@Column(name = "status_code")
	private String statusCode;

	/** The lang code. */
	@Column(name = "lang_code")
	private String langCode;

	/** The created by. */
	@Column(name = "cr_by")
	private String createdBy;

	/** The created date time. */
	@Column(name = "cr_dtimes")
	private LocalDateTime createdDateTime;

	/** The updated by. */
	@Column(name = "upd_by")
	private String updatedBy;

	/** The updated date time. */
	@Column(name = "upd_dtimes")
	private LocalDateTime updatedDateTime;

	/** The is deleted. */
	@Column(name = "is_deleted")
	private Boolean isDeleted;

	/** The deleted date time. */
	@Column(name = "del_dtimes")
	private LocalDateTime deletedDateTime;

	/**
	 * Gets the uin data.
	 *
	 * @return the uin data
	 */
	public byte[] getUinData() {
		return uinData.clone();
	}

	/**
	 * Sets the uin data.
	 *
	 * @param uinData the new uin data
	 */
	public void setUinData(byte[] uinData) {
		this.uinData = uinData.clone();
	}


}
