package validator;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


public class NumberValidator implements Validator{
	
	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if(length!=10)
			return false;
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c <= '/' || c >= ':') {
				return false;
			}
		}
		return true;
	}

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		// TODO Auto-generated method stub
		if(arg0==null || arg1==null)
			throw new NullPointerException();
		if(! (arg1 instanceof UIInput))
			return;
		FacesMessage message = new FacesMessage();
		message.setSummary("Invalid Contact Number");
		String contact = (String) arg2;
		
		if(!(isInteger(contact)))
		{ 
			throw new ValidatorException(message);
		}
	}
}
