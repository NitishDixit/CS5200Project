package bean;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.json.JSONException;

import service.CustomerService;
import service.StockQuoteService;
import model.CustStockDetail;

public class CustomerStockDetailsBean {

	private String message;
	private float currentPrice;
	private float profit_loss;
	private String stock_tickr;
	private float bought_at;
	private int quantity;

	public String getStock_tickr() {
		return stock_tickr;
	}
	public void setStock_tickr(String stock_tickr) {
		this.stock_tickr = stock_tickr;
	}
	public float getBought_at() {
		return bought_at;
	}
	public void setBought_at(float bought_at) {
		this.bought_at = bought_at;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	private List<CustomerStockDetailsBean> csdbList;
	public float getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}
	public float getProfit_loss() {
		return profit_loss;
	}
	public void setProfit_loss(float profit_loss) {
		this.profit_loss = profit_loss;
	}
	public List<CustomerStockDetailsBean> getCsdbList() {
		return csdbList;
	}
	public void setCsdbList(List<CustomerStockDetailsBean> csdbList) {
		this.csdbList = csdbList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	private List<CustStockDetail> custStockList = new ArrayList<CustStockDetail>();


	public List<CustStockDetail> getCustStockList() {
		return custStockList;
	}
	public void setCustStockList(List<CustStockDetail> custStockList) {
		this.custStockList = custStockList;
	}

	public CustomerStockDetailsBean(){

	}

	public String getCustStockDetails() throws IOException, JSONException, ParseException{

		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext etx = ctx.getExternalContext();
		HttpSession session = (HttpSession)etx.getSession(true);		

		String username = (String) session.getAttribute("userId");
		System.out.println("current user is:: " + username);
		CustomerService custService = new CustomerService();
		int customerId = custService.getCustomerId(username);

		StockQuoteService stockQuoteService = new StockQuoteService();

		custStockList = new ArrayList<CustStockDetail>();

		custStockList= custService.getCustStockDetails(customerId);

		csdbList = new ArrayList<CustomerStockDetailsBean>();

		for(int i=0; i<custStockList.size(); i++){

			CustomerStockDetailsBean custStockDetailBean = new CustomerStockDetailsBean();
			float currentPrice = stockQuoteService.getLastTradedPrice(custStockList.get(i).getStock_tickr());
			custStockDetailBean.setCurrentPrice(currentPrice);
			float currentValue = (custStockList.get(i).getPrice_bought_at() * custStockList.get(i).getNumber_of_stocks());
			float profit_loss = (((custStockList.get(i).getNumber_of_stocks() * currentPrice) - currentValue) *100)/currentValue;
			custStockDetailBean.setProfit_loss(profit_loss);
			custStockDetailBean.setBought_at(custStockList.get(i).getPrice_bought_at());
			custStockDetailBean.setQuantity(custStockList.get(i).getNumber_of_stocks());
			custStockDetailBean.setStock_tickr(custStockList.get(i).getStock_tickr());

			csdbList.add(custStockDetailBean);			
		}

		if(csdbList.size() !=0){
			return "all-detail-success";
		}else{
			return "all-detail-fail";
		}

	}

}
