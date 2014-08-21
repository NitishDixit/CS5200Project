package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the customer_pfm database table.
 * 
 */
@Entity
@Table(name="customer_pfm")
@NamedQuery(name="CustomerPfm.findAll", query="SELECT c FROM CustomerPfm c")
public class CustomerPfm implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CustomerPfmPK id;

	public CustomerPfm() {
	}

	public CustomerPfmPK getId() {
		return this.id;
	}

	public void setId(CustomerPfmPK id) {
		this.id = id;
	}

}