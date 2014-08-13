package model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the cust_transaction_history database table.
 * 
 */
@Entity
@Table(name="cust_transaction_history")
@NamedQuery(name="CustTransactionHistory.findAll", query="SELECT c FROM CustTransactionHistory c")
public class CustTransactionHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="cust_id")
	private int custId;

	@Column(name="price_bought_at")
	private float priceBoughtAt;

	@Column(name="price_sold_at")
	private float priceSoldAt;

	@Column(name="profit_loss_percent")
	private float profitLossPercent;

	@Column(name="stock_tickr")
	private String stockTickr;

	@Temporal(TemporalType.DATE)
	@Column(name="transaction_date")
	private Date transactionDate;

	@Column(name="transaction_type")
	private String transactionType;
	
	@Column(name="quantity")
	private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public CustTransactionHistory() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustId() {
		return this.custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public float getPriceBoughtAt() {
		return this.priceBoughtAt;
	}

	public void setPriceBoughtAt(float priceBoughtAt) {
		this.priceBoughtAt = priceBoughtAt;
	}

	public float getPriceSoldAt() {
		return this.priceSoldAt;
	}

	public void setPriceSoldAt(float priceSoldAt) {
		this.priceSoldAt = priceSoldAt;
	}

	public float getProfitLossPercent() {
		return this.profitLossPercent;
	}

	public void setProfitLossPercent(float profitLossPercent) {
		this.profitLossPercent = profitLossPercent;
	}

	public String getStockTickr() {
		return this.stockTickr;
	}

	public void setStockTickr(String stockTickr) {
		this.stockTickr = stockTickr;
	}

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}