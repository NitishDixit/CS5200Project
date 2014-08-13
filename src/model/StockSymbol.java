package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the stock_symbol database table.
 * 
 */
@Entity
@Table(name="stock_symbol")
@NamedQuery(name="StockSymbol.findAll", query="SELECT s FROM StockSymbol s")
public class StockSymbol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="tickr_symbol")
	private String tickrSymbol;

	@Column(name="stock_name")
	private String stockName;

	public StockSymbol() {
	}

	public String getTickrSymbol() {
		return this.tickrSymbol;
	}

	public void setTickrSymbol(String tickrSymbol) {
		this.tickrSymbol = tickrSymbol;
	}

	public String getStockName() {
		return this.stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

}