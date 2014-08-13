package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the suggested_stocks database table.
 * 
 */
@Embeddable
public class SuggestedStockPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pfm_id")
	private int pfmId;

	@Column(name="stock_tickr")
	private String stockTickr;
	
	@Column(name="cust_id")
	private int custId;

	public SuggestedStockPK() {
	}
	public int getPfmId() {
		return this.pfmId;
	}
	public void setPfmId(int pfmId) {
		this.pfmId = pfmId;
	}
	public String getStockTickr() {
		return this.stockTickr;
	}
	public void setStockTickr(String stockTickr) {
		this.stockTickr = stockTickr;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SuggestedStockPK)) {
			return false;
		}
		SuggestedStockPK castOther = (SuggestedStockPK)other;
		return 
			(this.pfmId == castOther.pfmId)
			&& this.stockTickr.equals(castOther.stockTickr);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pfmId;
		hash = hash * prime + this.stockTickr.hashCode();
		
		return hash;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
}