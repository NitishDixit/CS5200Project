package bean;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import model.Account;
import service.CustomerService;

public class AccountBean {

	private String accountNo;
	private String balance;
	private String amount;
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

	private String failMessage;

	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getFailMessage() {
		return failMessage;
	}
	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}

	@SuppressWarnings("unused")
	public String getAccountDetails(){

		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext etx = ctx.getExternalContext();
		HttpSession session = (HttpSession)etx.getSession(true);		

		String username = (String) session.getAttribute("userId");
		System.out.println("current user is:: " + username);

		CustomerService custService = new CustomerService();

		int customerId = custService.getCustomerId(username);

		Account account = custService.getAccountDetailsForCustomer(customerId);
		
		this.accountNo = account.getAccountNumber();
		this.balance = String.valueOf(account.getBalance());

		if(account != null){
			return "account-success";
		}else{
			return "account-fail";
		}
	}

	public String transferFunds(){

		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext etx = ctx.getExternalContext();
		HttpSession session = (HttpSession)etx.getSession(true);		

		String username = (String) session.getAttribute("userId");
		System.out.println("current user is:: " + username);

		CustomerService custService = new CustomerService();

		int customerId = custService.getCustomerId(username);
		
		String transferFunds = custService.updateAccount(customerId, Float.parseFloat(this.amount));
		
		if(transferFunds.equals("success")){
			this.setFailMessage("Transfer Funds successful");
			return "transfer-funds-success";
		}else{
			this.setFailMessage("Transfer Funds Aborted");
			return "transfer-funds-fail";
		}		
	}
}
