package bean;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import service.LoginService;

public class LoginBean {
	private String username;
	private String password;
	private String role;
	private String message;
	private String re_pass;
	private String answer;
	public LoginBean()
	{
		this.username="";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String authenticate()
	{
		LoginBean lb = new LoginBean();
		lb.setUsername(getUsername());
		lb.setPassword(getPassword());
		if (new LoginService().authenticate(lb).equals("error"))
		{
			this.setMessage("Username or Password don't match");
			return "error";
		}
		else if (new LoginService().authenticate(lb).equals("cust-fail"))
		{
			this.setMessage("Customer has not yet been activated");
			return "error";			
		}else
		{
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext etx = ctx.getExternalContext();
			HttpSession session = (HttpSession) etx.getSession(true);
		
		session.setAttribute("userId",lb.getUsername());
			this.setMessage("");
			return (new LoginService().authenticate(lb));
		}
		}
	
	public String validateUsername()
	{
		LoginBean bean = new LoginBean();
		bean.setUsername(this.username);
		if(new LoginService().validateUser(bean).equals("error"))
		{
			this.setMessage("Username Does not exist");
			return "error";
		}
		else 
			return "success";
	}
	
	public String validateAnswer()
	{
		LoginBean bean = new LoginBean();
		bean.setAnswer(this.answer);
		bean.setUsername(this.username);
		if(new LoginService().validateAnswer(bean).equals("fail"))
		{
			this.setMessage("Answer is Wrong");
			return "fail";
		}
		else 
			return "success";
		
	}
	
	public String changePassword()
	{
		LoginBean b=new LoginBean();
		b.setPassword(this.password);
		b.setUsername(this.username);
		if(!(this.re_pass.equals(this.password)))
		{
			this.setMessage("Password and retype Password does not match");
			return "fail";
		}
		else
			return(new LoginService().changePassword(b));
		
	}
	public String getRe_pass() {
		return re_pass;
	}
	public void setRe_pass(String re_pass) {
		this.re_pass = re_pass;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}