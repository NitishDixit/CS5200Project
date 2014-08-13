package model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the pfm_details database table.
 * 
 */
@Entity
@Table(name="pfm_details")
@NamedQuery(name="PfmDetail.findAll", query="SELECT p FROM PfmDetail p")
public class PfmDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String address;

	@Column(name="contact_number")
	private String contactNumber;

	@Column(name="pfm_name")
	private String pfmName;

	private String username;

	public PfmDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return this.contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getPfmName() {
		return this.pfmName;
	}

	public void setPfmName(String pfmName) {
		this.pfmName = pfmName;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}