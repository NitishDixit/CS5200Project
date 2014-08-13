package bean;

import java.io.IOException;
import java.text.ParseException;
import java.io.InputStream;
import java.net.URL;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import model.CustStockDetail;

import org.json.JSONException;

import service.CustomerService;
import service.StockQuoteService;
import org.json.JSONObject;
import org.json.JSONTokener;

public class StockQuote {

	private double change;
	private double daysLow;
	private double daysHigh;
	private double yearHigh;
	private double yearLow;
	private String name;
	private String symbol;
	private String stockExchange;
	private double lastTradePrice;
	private String failMessage;
	private String quantity;

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	@SuppressWarnings("unused")
	public String getStockData() throws JSONException, IOException
	{		
		    StockQuoteService stockService = new StockQuoteService();
		    
			StockQuote stockQuote = stockService.getAllStockData(this.symbol);
			
			this.setChange(stockQuote.getChange());
			this.setDaysHigh(stockQuote.getDaysHigh());
			this.setDaysLow(stockQuote.getDaysLow());
			this.setFailMessage("");
			this.setLastTradePrice(stockQuote.getLastTradePrice());
			this.setName(stockQuote.getName());
			this.setStockExchange(stockQuote.getStockExchange());
			this.setSymbol(stockQuote.getSymbol());
			this.setYearHigh(stockQuote.getYearHigh());
			this.setYearLow(stockQuote.getYearLow());
						
			if(stockQuote != null){
				return "success";
			}else{
				this.setFailMessage("Enter a Valid Stock Symbol");
				return "fail";
			}			
	}
		
	public String placeBuyOrder() throws ParseException{
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext etx = ctx.getExternalContext();
		HttpSession session = (HttpSession)etx.getSession(true);		

		String username = (String) session.getAttribute("userId");
		System.out.println("current user is:: " + username);

		CustomerService custService = new CustomerService();

		int customerId = custService.getCustomerId(username);
		int quant=Integer.parseInt(this.quantity);
		
		float price;
		Double x= new Double(this.lastTradePrice);
		price = x.floatValue();
		
		String checkBalance = custService.checkSufficientBalance(customerId, price, quant);
		
		if(checkBalance.equals("success")){
			
			CustStockDetail csd = custService.updateCustomerPortfolio(customerId, this.symbol, price, quant);
			
			if(csd != null){
				
				String updateTransaction = custService.updateTransactionHistoryForBuy(csd, customerId, this.symbol, price, quant);
				
				if(updateTransaction.equals("success")){
					return "place-buy-order-success";
				}else{
					this.setFailMessage("Failed while updating transaction history");
					return "place-buy-order-fail";
				}
				
			}else{
				this.setFailMessage("Problem in updating Cusomer Portfolio");
				return "place-buy-order-fail";
			}
			
		}else{
			this.setFailMessage("No Sufficient Balance in your account to buy stocks");
			return "place-buy-order-fail";
		}
		
	}
	
	public String placeSellOrder() throws IOException, JSONException, ParseException{

		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext etx = ctx.getExternalContext();
		HttpSession session = (HttpSession)etx.getSession(true);		

		String username = (String) session.getAttribute("userId");
		System.out.println("current user is:: " + username);

		CustomerService custService = new CustomerService();
		
		StockQuoteService stockQuoteService = new StockQuoteService();

		int customerId = custService.getCustomerId(username);

		int quant=Integer.parseInt(this.quantity);


		String checkStock = custService.checkSymbol(customerId, this.symbol);

		if(checkStock.equals("success")){

			String checkQuantity = custService.checkBalanceQuantity(customerId, this.symbol, quant);

			if(checkQuantity.equals("success")){
				
				float price = stockQuoteService.getLastTradedPrice(this.symbol);
				
				String checktransaction = custService.updateTransactionHistoryForSell(customerId, this.symbol, quant, price);
				
				if(checktransaction.equals("success")){
					return "place-sell-order-success";
				}else{
					this.setFailMessage("Error in updating Transaction History Table");
					return "place-sell-order-fail";
				}					

			}else{
				this.setFailMessage("Quantity of the stock for selling mentioned exceeds what present in portfolio");
				return "place-sell-order-fail";
			}

		}else{
			this.setFailMessage("Stock Not Found in your portfolio");
			return "place-sell-order-fail";
		}	

	}

	public Boolean validateTickr(String tickr){
		String fullUrlStr1="http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%3D'"
				+tickr+"'&"+"format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

		try{
			URL fullUrl = new URL(fullUrlStr1);
			InputStream is = fullUrl.openStream();

			JSONTokener tok = new JSONTokener(is);

			JSONObject result = new JSONObject(tok);


			JSONObject q = result.getJSONObject("query");

			JSONObject result1 = q.getJSONObject("results");

			JSONObject quote =result1.getJSONObject("quote");
			setChange(quote.getDouble("Change"));
			return true;
		}
		catch(Exception e){
			return false;
		}

	}
	
	public String getTickrSymbol(String tickr){
		String fullUrlStr1="http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%3D'"
				+tickr+"'&"+"format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

		try{
			URL fullUrl = new URL(fullUrlStr1);
			InputStream is = fullUrl.openStream();

			JSONTokener tok = new JSONTokener(is);

			JSONObject result = new JSONObject(tok);


			JSONObject q = result.getJSONObject("query");

			JSONObject result1 = q.getJSONObject("results");

			JSONObject quote =result1.getJSONObject("quote");
			return quote.getString("Symbol");
		}
		catch(Exception e){
			return e.getMessage();
		}
	}


	public String reset(){
		setFailMessage("");
		setSymbol("");
		return("reset");
	}

	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
	public double getDaysLow() {
		return daysLow;
	}
	public void setDaysLow(double daysLow) {
		this.daysLow = daysLow;
	}
	public double getDaysHigh() {
		return daysHigh;
	}
	public void setDaysHigh(double daysHigh) {
		this.daysHigh = daysHigh;
	}
	public double getYearHigh() {
		return yearHigh;
	}
	public void setYearHigh(double yearHigh) {
		this.yearHigh = yearHigh;
	}
	public double getYearLow() {
		return yearLow;
	}
	public void setYearLow(double yearLow) {
		this.yearLow = yearLow;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getStockExchange() {
		return stockExchange;
	}
	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}
	public String getFailMessage() {
		return failMessage;
	}

	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}
	public String returnSuccess()
	{	
		setSymbol("");
		setFailMessage("");
		return "success";}

	public double getLastTradePrice() {
		return lastTradePrice;
	}

	public void setLastTradePrice(double lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
	}

}