package bean;

import java.util.ArrayList;
import java.util.List;

import service.StockQuoteService;

public class StockKeyStatsBean {
	
	private String failMessage;
	private String stock_tickr;
	private List<KeyStatsBean> stockKeyStats = new ArrayList<KeyStatsBean>();
	
	public String getStock_tickr() {
		return stock_tickr;
	}
	public void setStock_tickr(String stock_tickr) {
		this.stock_tickr = stock_tickr;
	}

	public String getFailMessage() {
		return failMessage;
	}
	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}
	public List<KeyStatsBean> getStockKeyStats() {
		return stockKeyStats;
	}
	public void setStockKeyStats(List<KeyStatsBean> stockKeyStats) {
		this.stockKeyStats = stockKeyStats;
	}
	
	public String getKeyStats(){
		
		StockQuoteService stockQuoteService = new StockQuoteService();
		List<KeyStatsBean> keyStatsList = stockQuoteService.getKeyStatsForStock(this.stock_tickr);
		this.stockKeyStats = keyStatsList;
		System.out.println("KeyStat List details "+keyStatsList);
		if(keyStatsList.size() != 0){
			return "key-stats-success";
		}else{
			this.setFailMessage("Enter a vaid Stock Symbol");
			return "key-stats-fail";
		}
		
	}
	
	public String getHistoricalData(){
		
		StockQuoteService stockQuoteService = new StockQuoteService();
		this.stockKeyStats = stockQuoteService.getHistoricalDataForStocks(this.stock_tickr);
		if(this.stockKeyStats.size() != 0){
			return "historical-data-success";
		}else{
			this.setFailMessage("Enter a valid Stock Symbol");
			return "historical-data-fail";
		}
	}
	
}
