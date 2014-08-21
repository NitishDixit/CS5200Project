package bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import model.CustomerDetail;
import model.PfmDetail;
import service.CustomerPFMService;
import service.CustomerService;



public class CustomerBean {

	private List<CustomerDetail> custDetail= new ArrayList<CustomerDetail>();

	private String custId;
	
	private PfmDetail pfmDetail;

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	private String accountNumber;

	private String address;


	private String contactNumber;


	private String customerName;

	private String username;

	private String answer;

	private String message;

	private String password;
	
	private String eMail;

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	
	public String geteMail(){
		return eMail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String re_pass;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getRe_pass() {
		return re_pass;
	}

	public void setRe_pass(String re_pass) {
		this.re_pass = re_pass;
	}

	public String save()
	{

		if(!(re_pass.equals(password)))
		{
			setMessage("Password and Retype Password Do not Match");
			return "error";
		}
		else
		{
			CustomerBean bean = new CustomerBean();
			bean.setAddress(this.getAddress());
			bean.setContactNumber(this.getContactNumber());
			bean.setPassword(this.getPassword());
			bean.setCustomerName(this.customerName);
			bean.setUsername(this.username);
			bean.setAccountNumber(this.accountNumber);
			bean.setAnswer(this.answer);
			bean.seteMail(this.eMail);
			System.out.println("Email in Bean is::"+this.eMail);
			return (new CustomerService().save(bean));

		}
	}

	public String getInactiveCustomer()
	{
		custDetail = new ArrayList<CustomerDetail>();
		custDetail= new CustomerService().getInactiveCustomer();
		if(custDetail.size()==0)
			return "detail-fail";
		else 
			return "detail-success";
	}

	public String getAllCustomer()
	{
		custDetail = new ArrayList<CustomerDetail>();
		custDetail= new CustomerService().getAllCustomer();
		if(custDetail.size()==0)
			return "all-detail-fail";
		else 
			return "all-detail-success";
	}

	public String activateCustomer()
	{
		int cid=Integer.parseInt(this.custId);
		if(new CustomerService().activate(cid).equals("success"))
		{
			this.custId="";
			return "success";
		}
		else
		{
			this.setMessage("The customer Id cannot be found, Enter valid customer Id");
			this.custId="";
			return "fail";
		}
	}

	public String deactivateCustomer()
	{
		int cid=Integer.parseInt(this.custId);
		if(new CustomerService().deactivate(cid).equals("success"))
		{
			this.custId="";
			return "success";
		}
		else
		{
			this.setMessage("The customer Id cannot be found, Enter valid customer Id");
			this.custId="";
			return "fail";
		}
	}

	public List<CustomerDetail> getCustDetail(){
		return custDetail;
	}

	public void setCustDetail(List<CustomerDetail> custDetail){
		this.custDetail = custDetail;
	}

	public String passReset(){
		CustomerBean bean= new CustomerBean();
		bean.setUsername(this.username);
		bean.setPassword(this.password);

		if(this.password.equals(this.re_pass))
		{
			this.setMessage("Password and Retype Password Should match");
			return "error";
		}
		else
		{
			return (new CustomerService().resetPassword(bean));
		}
	}	
	public String getPM()
	{
		LoginBean lb= (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
		this.username=lb.getUsername();
		CustomerPFMService pfmService=new CustomerPFMService();
		PfmDetail pfm=pfmService.retrievePM(this.username);
		if(pfm!=null)
		{
			this.pfmDetail=pfm;
			return "pm-get-success";
		}
		else 
			return "pm-get-fail";
	}

	public PfmDetail getPfmDetail() {
		return pfmDetail;
	}

	public void setPfmDetail(PfmDetail pfmDetail) {
		this.pfmDetail = pfmDetail;
	}
	
	public String retrieveVal()
	{
		LoginBean lb= (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
		this.username=lb.getUsername();
		System.out.println(this.username);
		CustomerDetail cd=new CustomerService().retreiveVal(this.username);
		this.setAccountNumber(cd.getAccountNumber());
		this.setAddress(cd.getAddress());
		this.setAnswer(cd.getAnswer());
		this.setContactNumber(cd.getContactNumber());
		String id= Integer.toString(cd.getId());
		this.setCustId(id);
		this.setCustomerName(cd.getCustomerName());
		return "update-success";
	}
	public String update()
	{
		if(!(this.password.equals(this.re_pass)))
		{
			this.setMessage("Password and Retype Password did not match");
			return "fail";
			
		}
		else
		{
			CustomerService service=new CustomerService();
			CustomerBean bean=new CustomerBean();
			bean.setAddress(this.address);
			bean.setContactNumber(this.contactNumber);
			bean.setCustId(this.custId);
			bean.setPassword(this.password);
			bean.setCustomerName(this.customerName);;
			bean.setUsername(this.username);
			bean.setAccountNumber(this.accountNumber);
			bean.setAnswer(this.answer);
			service.update(bean);
			this.message="";
			return "success";
		}
	}
}

