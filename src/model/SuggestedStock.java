package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the suggested_stocks database table.
 * 
 */
@Entity
@Table(name="suggested_stocks")
@NamedQuery(name="SuggestedStock.findAll", query="SELECT s FROM SuggestedStock s")
public class SuggestedStock implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SuggestedStockPK id;

	private String comments;

	@Temporal(TemporalType.DATE)
	@Column(name="suggested_date")
	private Date suggestedDate;

	public SuggestedStock() {
	}

	public SuggestedStockPK getId() {
		return this.id;
	}

	public void setId(SuggestedStockPK id) {
		this.id = id;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getSuggestedDate() {
		return this.suggestedDate;
	}

	public void setSuggestedDate(Date suggestedDate) {
		this.suggestedDate = suggestedDate;
	}

}