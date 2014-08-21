package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import service.LoginService;



public class UsernameValidator implements Validator {
	public UsernameValidator()
	{}
	public void validate(FacesContext fc, UIComponent comp, Object obj) throws ValidatorException
	{
		if(fc==null || comp==null)
			throw new NullPointerException();
		if(! (comp instanceof UIInput))
			return;
		FacesMessage message = new FacesMessage();
		message.setSummary("Invalid Username, not available");
		String username = (String) obj;
		if((new LoginService().find(username))!=null)
			throw new ValidatorException(message);
	}
	

}
