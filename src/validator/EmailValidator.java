package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class EmailValidator implements Validator{

	private Pattern series;
	private Matcher match;
 
	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
	public EmailValidator() {
		series = Pattern.compile(EMAIL_PATTERN);
	}
 
	/**
	 * Validate hex with regular expression
	 * 
	 * @param hex
	 *            hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public boolean validate(String hex) {
 
		match = series.matcher(hex);
		return match.matches();
 
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
		message.setSummary("Enter a Valid Email Id");
		String email = (String) arg2;
		
		if(! validate(email)){
			throw new ValidatorException(message);
		}
		
	}
}
