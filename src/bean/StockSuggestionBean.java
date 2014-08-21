package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import model.SuggestedStock;
import service.CustomerService;
import service.PMService;
import service.StockSuggestionService;

public class StockSuggestionBean {
	private String custId;
	private int pfmId;
	private String stockTickr;
	private String suggestion;
	private Date suggestedDate;
	private List<SuggestedStock> suggestedStocks;
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public int getPfmId() {
		return pfmId;
	}
	public void setPfmId(int pfmId) {
		this.pfmId = pfmId;
	}
	public String getStockTickr() {
		return stockTickr;
	}
	public void setStockTickr(String stockTickr) {
		this.stockTickr = stockTickr;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public Date getSuggestedDate() {
		return suggestedDate;
	}
	public void setSuggestedDate(Date suggestedDate) {
		this.suggestedDate = suggestedDate;
	}
	
	public String navigate()
	{
		LoginBean lb= (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
		String username=lb.getUsername();
		this.pfmId=new PMService().getPfmId(username);
		this.suggestedStocks= new ArrayList<SuggestedStock>();
		this.suggestedStocks=new StockSuggestionService().findAllSuggestionsByCustUser(Integer.parseInt(this.custId), this.pfmId);
		if(suggestedStocks.size()==0)
		{
			return "success";
		}
		else
		{
			return "fail";
		}
	}
	
	public String saveSuggestion()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		LoginBean lb= (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
		String username=lb.getUsername();
		this.pfmId=new PMService().getPfmId(username);
		StockSuggestionBean bean=new StockSuggestionBean();
		bean.setCustId(this.custId);
		bean.setPfmId(this.pfmId);
		bean.setStockTickr(new StockQuote().getTickrSymbol(this.stockTickr));
		bean.setSuggestedDate(date);
		bean.setSuggestion(this.suggestion);
		StockSuggestionService service=new StockSuggestionService();
		service.mergeSuggestion(bean);
		this.custId="";
		this.stockTickr="";
		this.suggestion="";
		return "success";
	}
	
	public String getCustomerStockSuggestion()
	{
		LoginBean lb= (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
		String username=lb.getUsername();
		CustomerService cs=new CustomerService();
		int cid=cs.getcustDetail(username).getId();
		suggestedStocks=new ArrayList<SuggestedStock>();
		suggestedStocks=new StockSuggestionService().findAllSuggestionsByCustomer(cid);
		if(suggestedStocks.size()==0)
			return "no-stock";
		else
			return "stocks-present";
	}
	
	public List<SuggestedStock> getSuggestedStocks() {
		return suggestedStocks;
	}
	public void setSuggestedStocks(List<SuggestedStock> suggestedStocks) {
		this.suggestedStocks = suggestedStocks;
	}
}
