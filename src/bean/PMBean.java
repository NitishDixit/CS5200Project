package bean;

import java.util.ArrayList;
import java.util.List;




import javax.faces.context.FacesContext;

import model.CustomerDetail;
import model.PfmDetail;
import service.PMService;

public class PMBean {
	private String pfmID;
	private String pfm_name;
	private String username;
	private String address;
	private String contact_number;
	private String password;
	private String re_password;
	private String message;
	private List<PfmDetail> pfmDetails;
	private int id;
	private List<CustomerDetail> customerDetails;
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PMBean() {
		
	}
	public String getPfm_name() {
		return pfm_name;
	}
	public void setPfm_name(String pfm_name) {
		this.pfm_name = pfm_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact_number() {
		return contact_number;
	}
	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRe_password() {
		return re_password;
	}
	public void setRe_password(String re_password) {
		this.re_password = re_password;
	}
	
	public String save()
	{
		System.out.println(re_password.equals(password));
		if(!(re_password.equals(password)))
		{
			setMessage("Password and Retype Password Do not Match");
			return "error";
		}
		else
		{
			PMBean pmBean= new PMBean();
			pmBean.setAddress(this.getAddress());
			pmBean.setContact_number(this.getContact_number());
			pmBean.setPassword(this.getPassword());
			pmBean.setPfm_name(this.getPfm_name());
			pmBean.setUsername(this.username);
			return (new PMService().save(pmBean));
			
		}
		
		
	}
	public List<PfmDetail> getPfmDetails() {
		return pfmDetails;
	}
	public void setPfmDetails(List<PfmDetail> pfmDetails) {
		this.pfmDetails = pfmDetails;
	}
	
	public String getAllPM()
	{
		this.pfmDetails= new ArrayList<PfmDetail>();
		this.pfmDetails = new PMService().getAllPM();
		if(this.pfmDetails.size()!=0)
			return "PM-get-success";
		else 
			return "PM--get-fail";
	}
	
	public String deletePM()
	{
		int pid=Integer.parseInt(this.pfmID);
		if(new PMService().delete(pid).equals("success"))
		{
			this.pfmID="";
			return "success";
		}
		else
		{
			this.setMessage("PM Id does not exist");
			this.pfmID="";
			return "fail";
		}
	}
	public String getPfmID() {
		return pfmID;
	}
	public void setPfmID(String pfmID) {
		this.pfmID = pfmID;
	}
	
	public String retrieveVal()
	{
		 LoginBean lb= (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
		 this.username=lb.getUsername();
		 PMService pmService=new PMService();
		 PfmDetail detail=pmService.getValue(this.username);
		 this.pfm_name=detail.getPfmName();
		 this.contact_number=detail.getContactNumber();
		 this.address=detail.getAddress();
		 this.password="";
		 this.message="";
		 this.re_password="";
		 this.id=detail.getId();
		 return "success";
	}
	
	public String update()
	{
		if(!(this.password.equals(this.re_password)))
		{
			this.setMessage("Password and Retype Password did not match");
			return "fail";
			
		}
		else
		{
			PMService pmService=new PMService();
			PMBean bean=new PMBean();
			bean.setAddress(this.address);
			bean.setContact_number(this.contact_number);
			bean.setId(this.id);
			bean.setPassword(this.password);
			bean.setPfm_name(this.pfm_name);
			bean.setUsername(this.username);
			pmService.update(bean);
			this.message="";
			return "success";
		}
	}
	
	public String getAllCust() {
		LoginBean lb= (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
		this.username=lb.getUsername();
		this.customerDetails=new ArrayList<CustomerDetail>();
		this.customerDetails=new PMService().getAllCust(this.username);
		if(customerDetails.size()==0){
			return "get-all-fail";
		}
			
		else 
			return "get-all-success";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<CustomerDetail> getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(List<CustomerDetail> customerDetails) {
		this.customerDetails = customerDetails;
	}
}
