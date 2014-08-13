package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Account;
import model.CustStockDetail;
import model.CustTransactionHistory;
import model.CustomerDetail;
import model.CustomerPfm;
import model.CustomerPfmPK;
import model.Login;
import model.SuggestedStock;
import bean.CustomerBean;

public class CustomerService {

	EntityManagerFactory emf= Persistence.createEntityManagerFactory("PMS");
	EntityManager em= emf.createEntityManager();
	EntityTransaction et = em.getTransaction();

	public String save(CustomerBean bean) {
		// TODO Auto-generated method stub

		CustomerDetail cd= new CustomerDetail();
		cd.setAccountNumber(bean.getAccountNumber());
		cd.setAddress(bean.getAddress());
		cd.setAnswer(bean.getAnswer());
		cd.setContactNumber(bean.getContactNumber());
		cd.setUsername(bean.getUsername());
		cd.setEmailId(bean.geteMail());
		System.out.println("Email Id in Service "+cd.getEmailId());
		cd.setCustomerName(bean.getCustomerName());
		cd.setStatus("d");
		Login login=new Login();
		login.setPassword(bean.getPassword());
		login.setRole("c");
		login.setUsername(bean.getUsername());
		Account a= new Account();
		a.setAccountNumber(bean.getAccountNumber());
		a.setBalance(0.0f);
		et.begin();
		em.merge(cd);
		em.merge(login);
		em.merge(a);
		et.commit();
		return "success";
	}

	public List<CustomerDetail> getInactiveCustomer() {
		// TODO Auto-generated method stub

		Query q = em.createQuery("select c from CustomerDetail c where c.status=?1");
		q.setParameter(1, "d");
		@SuppressWarnings("unchecked")
		List<CustomerDetail> cd= q.getResultList();

		return cd;
	}

	public List<CustomerDetail> getAllCustomer(){
		Query q = em.createQuery("select c from CustomerDetail c");
		@SuppressWarnings("unchecked")
		List<CustomerDetail> cd= q.getResultList();

		return cd;
	}

	public String activate(int id)
	{
		CustomerDetail cust = em.find(CustomerDetail.class, id);
		if(cust==null)
			return "fail";
		else{
			cust.setStatus("a");
			et.begin();
			em.merge(cust);
			et.commit();
			return "success";
		}		
	}

	public String resetPassword(CustomerBean bean)
	{
		Login log= em.find(Login.class, bean.getUsername());
		if(log!=null)
		{
			log.setPassword(bean.getPassword());
			et.begin();
			em.merge(log);
			et.commit();
			return "success";
		}		
		else 
			return "fail";
	}

	public String deactivate(int cid) {
		// TODO Auto-generated method stub
		CustomerDetail cust = em.find(CustomerDetail.class, cid);

		if(cust!=null)
		{
			CustomerPfmPK pk=new  CustomerPfmPK();
			
			pk.setCustUser(cid);
			
			Query query=em.createQuery("select cp from CustomerPfm cp where cp.id.custUser=?1");
			query.setParameter(1, cid);
			
			@SuppressWarnings("unchecked")
			List<CustomerPfm> custPFMList = query.getResultList();
			
			if(custPFMList.size() != 0){
				CustomerPfm customerPfm = custPFMList.get(0);
				Login log=em.find(Login.class, cust.getUsername());
				Account acc= em.find(Account.class,cust.getAccountNumber());
				System.out.println(log.getUsername());
				System.out.println(acc.getAccountNumber());
				et.begin();
				System.out.println(cust.getAccountNumber());
				System.out.println(1);
				String accno= cust.getAccountNumber();
				System.out.println(accno);
				em.remove(customerPfm);
				em.remove(cust);
				em.remove(log);
				em.remove(acc);
				et.commit();
				return "success";				
			}else{
				Login log=em.find(Login.class, cust.getUsername());
				Account acc= em.find(Account.class,cust.getAccountNumber());
				et.begin();
				em.remove(cust);
				em.remove(log);
				em.remove(acc);
				et.commit();
				return "success";				
			}
		}
		else 
		{
			return "fail";
		}
	}
	
	public CustomerDetail getcustDetail(String username)
	{
		Query q =em.createQuery("Select c from CustomerDetail c where c.username=?1");
		q.setParameter(1, username);
		CustomerDetail cd=(CustomerDetail)q.getSingleResult();
		return cd;
	}

	public int getCustomerId(String user){

		System.out.println("Current user is ::" +user);
		Query q = em.createQuery("select c.id from CustomerDetail c where c.username = ?1");
		q.setParameter(1, user);
		@SuppressWarnings("unchecked")
		List<Object> rs = q.getResultList();

		int cid = (int) rs.get(0);
		return cid;
	}
	
		public void update(CustomerBean bean) {
		// TODO Auto-generated method stub
		CustomerDetail detail= new CustomerDetail();
		detail.setAccountNumber(bean.getAccountNumber());
		detail.setAddress(bean.getAddress());
		detail.setAnswer(bean.getAnswer());
		detail.setContactNumber(bean.getContactNumber());
		detail.setCustomerName(bean.getCustomerName());
		detail.setId(Integer.parseInt(bean.getCustId()));
		detail.setStatus("a");
		detail.setUsername(bean.getUsername());
		Login login=new Login();
		login.setUsername(bean.getUsername());
		login.setRole("c");
		login.setPassword(bean.getPassword());
		et.begin();
		em.merge(detail);
		em.merge(login);
		et.commit();
	}
	
	public CustomerDetail retreiveVal(String username) {
		// TODO Auto-generated method stub
		System.out.println(username);
		Query q=em.createQuery("Select c from CustomerDetail c where c.username= ?1");
		q.setParameter(1, username);
		CustomerDetail detail= (CustomerDetail) q.getSingleResult();
		return detail;
	}

	public String checkSufficientBalance(int cid, float ltp, int quantity){

		Query q = em.createQuery("select a.balance from Account a, CustomerDetail c "
				+ "where a.accountNumber = c.accountNumber and c.id = ?1");

		q.setParameter(1, cid);		
		Object obj = q.getSingleResult();		

		float balance = (float) obj;		

		if(balance >= (ltp*quantity)){
			return "success";
		}else{
			return "fail";
		}		
	}

	public CustStockDetail updateCustomerPortfolio(int cid, String symbol, float ltp, int quantity){

		Query q1 = em.createQuery("select c from CustStockDetail c where c.cust_id = ?1 and c.stock_tickr = ?2");
		q1.setParameter(1, cid);
		q1.setParameter(2, symbol);

		//CustStockDetail custStockDetail = (CustStockDetail) q1.getSingleResult();

		@SuppressWarnings("unchecked")
		List<CustStockDetail> csdList = (List<CustStockDetail>) q1.getResultList();

		int size = csdList.size();

		CustStockDetail custStockDetail = new CustStockDetail();

		if(size == 0){			
			custStockDetail.setCust_id(cid);
			custStockDetail.setNumber_of_stocks(quantity);
			custStockDetail.setPrice_bought_at(ltp);
			custStockDetail.setStock_tickr(symbol);

			return  custStockDetail;			
		}else{			
			custStockDetail = csdList.get(0);			
			custStockDetail.setCust_id(custStockDetail.getCust_id());
			custStockDetail.setStock_tickr(custStockDetail.getStock_tickr());
			int number_of_stocks = custStockDetail.getNumber_of_stocks();
			float previousPrice = custStockDetail.getPrice_bought_at();
			float weightedPrice = (((number_of_stocks*previousPrice) + (quantity*ltp))/(quantity+number_of_stocks));
			custStockDetail.setNumber_of_stocks(number_of_stocks + quantity);
			custStockDetail.setPrice_bought_at(weightedPrice);
			return  custStockDetail;			
		}
	}

	public String updateTransactionHistoryForBuy(CustStockDetail custStockDetail, int cid, String symbol, float price, int quantity) throws ParseException{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");      
		Date dateWithoutTime = sdf.parse(sdf.format(new Date()));			

		// Query to fetch Account details for a particular customer
		Query q5 = em.createQuery("select c from CustomerDetail c where c.id = ?1");
		q5.setParameter(1, cid);

		CustomerDetail rs = (CustomerDetail) q5.getSingleResult();
		String accNo= rs.getAccountNumber();

		System.out.println("Customer's Account Number is ::"+accNo);

		Account account = em.find(Account.class, accNo);
		account.setBalance(account.getBalance() - (price*quantity));

		System.out.println("New balance is"+ account.getBalance());
		System.out.println("Account number is"+ account.getAccountNumber());

		System.out.println("Setting cust transaction History Bean: Start");

		CustTransactionHistory custTransactionHistory = new CustTransactionHistory();

		custTransactionHistory.setCustId(cid);
		custTransactionHistory.setTransactionType("Buy");
		custTransactionHistory.setStockTickr(symbol);
		custTransactionHistory.setPriceBoughtAt(price);
		custTransactionHistory.setPriceSoldAt(0);
		custTransactionHistory.setProfitLossPercent(0);
		custTransactionHistory.setTransactionDate(dateWithoutTime);
		custTransactionHistory.setQuantity(quantity);

		System.out.println("Setting cust transaction History Bean : End");

		et.begin();
		em.merge(custStockDetail);
		em.merge(custTransactionHistory);
		em.merge(account);
		System.out.println("I am Here");		
		et.commit();	
		System.out.println("Setting cust transaction History Bean : End");

		return "success";

	}

	public List<CustStockDetail> getCustStockDetails(int cid){

		System.out.println("Customer Id is:::" +cid);

		Query q1 = em.createQuery("select c from CustStockDetail c where c.cust_id= ?1");
		q1.setParameter(1, cid);

		@SuppressWarnings("unchecked")
		List<CustStockDetail> custStockList= q1.getResultList();

		System.out.println("list of items::" +custStockList);

		return custStockList;
	}

	@SuppressWarnings("unchecked")
	public List<CustTransactionHistory> getCustTransactionDetails(int cid){

		Query q1 = em.createQuery("select c from CustTransactionHistory c where c.custId= ?1");

		q1.setParameter(1, cid);

		List<CustTransactionHistory> custTransactionList = new ArrayList<CustTransactionHistory>();
		custTransactionList= q1.getResultList();

		return custTransactionList;		
	}

	public String checkSymbol(int cid, String companyId){

		Query q1 = em.createQuery("select c from CustStockDetail c where c.cust_id = ?1 and c.stock_tickr = ?2");
		q1.setParameter(1, cid);
		q1.setParameter(2, companyId);

		@SuppressWarnings("unchecked")
		List<CustStockDetail> csdList = q1.getResultList();

		if(csdList != null){
			return "success";
		}else{
			return "fail";
		}
	}

	public String checkBalanceQuantity(int cid, String companyId, int quantity){

		Query q1 = em.createQuery("select c from CustStockDetail c where c.cust_id = ?1 and c.stock_tickr = ?2");
		q1.setParameter(1, cid);
		q1.setParameter(2, companyId);

		@SuppressWarnings("unchecked")
		List<CustStockDetail> obj = q1.getResultList();

		CustStockDetail custStockDetail = (CustStockDetail) obj.get(0);

		System.out.println("Number of Stocks :: "+ custStockDetail.getNumber_of_stocks());

		if(quantity > custStockDetail.getNumber_of_stocks()){
			return "fail";
		}else{
			if(custStockDetail.getNumber_of_stocks() == quantity){

				et.begin();
				em.remove(custStockDetail);
				et.commit();
				return "success";
			}else{

				System.out.println("Quantity is less than the present number of stocks");

				et.begin();

				custStockDetail.setId(custStockDetail.getId());
				custStockDetail.setCust_id(custStockDetail.getCust_id());
				custStockDetail.setNumber_of_stocks(custStockDetail.getNumber_of_stocks() - quantity);
				custStockDetail.setStock_tickr(custStockDetail.getStock_tickr());
				custStockDetail.setPrice_bought_at(custStockDetail.getPrice_bought_at());

				em.merge(custStockDetail);
				et.commit();
				System.out.println("Data Merged in database");
				return "success";

			}
		}
	}

	public String updateTransactionHistoryForSell(int cid, String symbol, int quantity, float price) throws ParseException{

		System.out.println("Customer Id is :: "+ em.isOpen());

		// to generate sysdate
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");      
		Date dateWithoutTime = sdf.parse(sdf.format(new Date()));			

		// Query to fetch Account details for a particular customer
		Query q5 = em.createQuery("select c from CustomerDetail c where c.id = ?1");
		q5.setParameter(1, cid);

		CustomerDetail rs = (CustomerDetail) q5.getSingleResult();
		String accNo= rs.getAccountNumber();

		System.out.println("Customer's Account Number is ::"+accNo);

		Account account = em.find(Account.class, accNo);
		account.setBalance(account.getBalance() + (price*quantity));

		System.out.println("Setting cust transaction History Bean: Start");

		CustTransactionHistory custTransactionHistory = new CustTransactionHistory();

		custTransactionHistory.setCustId(cid);
		custTransactionHistory.setTransactionType("Sell");
		custTransactionHistory.setStockTickr(symbol);
		custTransactionHistory.setPriceBoughtAt(price);
		custTransactionHistory.setPriceSoldAt(0);
		custTransactionHistory.setProfitLossPercent(0);
		custTransactionHistory.setTransactionDate(dateWithoutTime);
		custTransactionHistory.setQuantity(quantity);

		System.out.println("Setting cust transaction History Bean : End");

		et.begin();
		em.merge(custTransactionHistory);
		em.merge(account);
		et.commit();	

		System.out.println("Setting cust transaction History Bean : End");

		return "success";
	}

	@SuppressWarnings("unchecked")
	public List<SuggestedStock> getSuggestionsFromPFM(int cid){

		List<SuggestedStock> suggestedStocksList = new ArrayList<SuggestedStock>();

		Query q6 = em.createQuery("select sp from SuggestedStock s, SuggestedStockPK spk where s.id =?1");
		q6.setParameter(1, cid);

		suggestedStocksList = q6.getResultList();

		return suggestedStocksList;
	}
	
	public Account getAccountDetailsForCustomer(int cid){
		
		Query q10 = em.createQuery("select c from CustomerDetail c where c.id = ?1");
		q10.setParameter(1, cid);
		
		CustomerDetail custDetail = (CustomerDetail) q10.getSingleResult();
		
		Account account = em.find(Account.class, custDetail.getAccountNumber());
		
		return account;
	}
	
	public String updateAccount(int cid, float amount){
		
		Query q11 = em.createQuery("select c from CustomerDetail c where c.id = ?1");
		q11.setParameter(1, cid);
		CustomerDetail custDetail = (CustomerDetail) q11.getSingleResult();
		
		String accountNumber = custDetail.getAccountNumber();
		
		Account account = em.find(Account.class, accountNumber);
		
		account.setBalance(account.getBalance() + amount);
		
		et.begin();
		em.merge(account);
		et.commit();
		
		return "success";
	}
	
/*	public String getEmailIdOfCustomer(int custId){
		
		Query q12 = em.createQuery("select c from CustomerDetail c where c.id = ?1");
		q12.setParameter(1, custId);
		CustomerDetail custDetail = (CustomerDetail) q12.getSingleResult();
		
		String emailId = "";
		emailId = custDetail.getEmailId();		
		return emailId;		
	}*/
}




