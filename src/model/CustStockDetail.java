package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="cust_stock_details")
public class CustStockDetail {

	@Id
	private int id;
	
	private int cust_id;
	
	private String stock_tickr;
	
	private int number_of_stocks;
	
	private float price_bought_at;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCust_id() {
		return cust_id;
	}

	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}

	public String getStock_tickr() {
		return stock_tickr;
	}

	public void setStock_tickr(String stock_tickr) {
		this.stock_tickr = stock_tickr;
	}

	public int getNumber_of_stocks() {
		return number_of_stocks;
	}

	public void setNumber_of_stocks(int number_of_stocks) {
		this.number_of_stocks = number_of_stocks;
	}

	public float getPrice_bought_at() {
		return price_bought_at;
	}

	public void setPrice_bought_at(float price_bought_at) {
		this.price_bought_at = price_bought_at;
	}
	
	
}
