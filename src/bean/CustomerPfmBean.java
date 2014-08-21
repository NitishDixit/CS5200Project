package bean;

import java.util.ArrayList;
import java.util.List;

import service.CustomerPFMService;
import service.CustomerService;
import service.PMService;
import model.CustomerDetail;
import model.CustomerPfm;
import model.PfmDetail;


public class CustomerPfmBean {
	private String custId;
	private String pmId;
	private List<PfmDetail> pfmDetails;
	private List<CustomerDetail> customerDetails;
	private List<CustomerPfm> customerPfms;
	private String message;
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getPmId() {
		return pmId;
	}
	public void setPmId(String pmId) {
		this.pmId = pmId;
	}
	public List<PfmDetail> getPfmDetails() {
		return pfmDetails;
	}
	public void setPfmDetails(List<PfmDetail> pfmDetails) {
		this.pfmDetails = pfmDetails;
	}
	public List<CustomerDetail> getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(List<CustomerDetail> customerDetails) {
		this.customerDetails = customerDetails;
	}
	public List<CustomerPfm> getCustomerPfms() {
		return customerPfms;
	}
	public void setCustomerPfms(List<CustomerPfm> customerPfms) {
		this.customerPfms = customerPfms;
	}
	
	public String getAllCustPM()
	{
		this.customerDetails=new ArrayList<CustomerDetail>();
		this.pfmDetails=new ArrayList<PfmDetail>();
		this.customerPfms=new ArrayList<CustomerPfm>();
		this.customerDetails=new CustomerService().getAllCustomer();
		this.pfmDetails = new PMService().getAllPM();
		if((customerDetails.size()==0) || pfmDetails.size()==0)
		{
			return "cust-pm-fail";
		}
		else 
		{
			this.customerPfms=new CustomerPFMService().getALLCustomerandPFM();
			return "cust-pm-success";
		}
	}
	
	public String MakeConnections()
	{
		int cid= Integer.parseInt(this.custId);
		int pid= Integer.parseInt(this.pmId);
		CustomerPFMService service= new CustomerPFMService();
		String saveMessage= service.save(cid, pid);
		if(saveMessage.equals("cust-fail"))
		{
			this.message="Customer does not exist";
			return "cust-fail";
		}
		else if (saveMessage.equals("pm-fail"))
		{
			this.message="PM does not exist";
			return "pm-fail";
		}
		else if (saveMessage.equals("pm-exist-fail"))
		{
			this.message="Already allocated customer to PM";
			return "pm-exist-fail";
		}
		else 
		{
			this.message="";
			this.custId="";
			this.pmId="";
			return "success";
		}
	}
	
	public String getAllCustPMS()
	{
		this.customerPfms=new ArrayList<CustomerPfm>();
		this.customerPfms=new CustomerPFMService().getALLCustomerandPFM();
		if(this.customerPfms.size()==0)
		{
			return "cust-pm-delete-fail";
		}
		else 
		{
			return "cust-pm-delete-success";
		}
	}
	
	public String deleteAlloc()
	{
		int cid=Integer.parseInt(this.custId);
		int pid=Integer.parseInt(this.pmId);
		CustomerPFMService service= new CustomerPFMService();
		String saveMessage= service.delete(cid, pid);
		if(saveMessage.equals("cust-fail"))
		{
			this.message="Customer does not exist";
			return "cust-fail";
		}
		else if (saveMessage.equals("pm-fail"))
		{
			this.message="PM does not exist";
			return "pm-fail";
		}
		else if (saveMessage.equals("pm-not-exist-fail"))
		{
			this.message="Not valid Allocations";
			return "pm-not-exist-fail";
		}
		else 
		{
			this.message="";
			this.custId="";
			this.pmId="";
			return "success";
		}
	}
	
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}

