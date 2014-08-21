package bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import service.CustomerService;
import model.CustTransactionHistory;

public class CustTransactionHistoryBean {
	
	private String message;
	private List<CustTransactionHistory> custTransactionList = new ArrayList<CustTransactionHistory>();
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<CustTransactionHistory> getCustTransactionList() {
		return custTransactionList;
	}
	public void setCustTransactionList(
			List<CustTransactionHistory> custTransactionList) {
		this.custTransactionList = custTransactionList;
	}
	
public String getCustTransactionDetails(){
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext etx = ctx.getExternalContext();
		HttpSession session = (HttpSession)etx.getSession(true);		

		String username = (String) session.getAttribute("userId");
		System.out.println("current user is:: " + username);
		
		CustomerService custService = new CustomerService();
		
		int customerId = custService.getCustomerId(username);
		
		custTransactionList = new ArrayList<CustTransactionHistory>();
		custTransactionList = custService.getCustTransactionDetails(customerId);
		
		if(custTransactionList.size()==0)
			return "customer-transaction-fail";
		else 
			return "customer-transaction-success";
	}

}
