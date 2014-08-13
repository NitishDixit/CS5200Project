package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import bean.StockQuote;

public class StockTickrValidator implements Validator{
	
	

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		// TODO Auto-generated method stub
		if(arg0==null || arg1==null)
			throw new NullPointerException();
		if(! (arg1 instanceof UIInput))
			return;
		FacesMessage message = new FacesMessage();
		message.setSummary("Invalid Ticker Symbol");
		String contact = (String) arg2;
		
		if(!(new StockQuote().validateTickr(contact)))
		{
			throw new ValidatorException(message);
		}
	}
}
