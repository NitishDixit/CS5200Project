package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import service.CustomerPFMService;
import service.PMService;
import bean.LoginBean;

public class CustIdValidation implements Validator{

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		// TODO Auto-generated method stub
		if(arg0==null || arg1==null)
			throw new NullPointerException();
		if(! (arg1 instanceof UIInput))
			return;
		FacesMessage message = new FacesMessage();
		message.setSummary("Enter Proper Customer Id");
		LoginBean lb= (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
		 String username=lb.getUsername();
		 PMService pmService=new PMService();
		 int pid=pmService.getPfmId(username);
		 
		 int cid = Integer.parseInt((String) arg2);
		 
		 Boolean valid=new CustomerPFMService().validate(cid, pid);
		 if(!valid)
			 throw new ValidatorException(message);
	}

}
