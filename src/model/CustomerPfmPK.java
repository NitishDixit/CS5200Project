package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the customer_pfm database table.
 * 
 */
@Embeddable
public class CustomerPfmPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pfm_user")
	private int pfmUser;

	@Column(name="cust_user")
	private int custUser;

	public CustomerPfmPK() {
	}
	public int getPfmUser() {
		return this.pfmUser;
	}
	public void setPfmUser(int pfmUser) {
		this.pfmUser = pfmUser;
	}
	public int getCustUser() {
		return this.custUser;
	}
	public void setCustUser(int custUser) {
		this.custUser = custUser;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CustomerPfmPK)) {
			return false;
		}
		CustomerPfmPK castOther = (CustomerPfmPK)other;
		return 
			(this.pfmUser == castOther.pfmUser)
			&& (this.custUser == castOther.custUser);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pfmUser;
		hash = hash * prime + this.custUser;
		
		return hash;
	}
}