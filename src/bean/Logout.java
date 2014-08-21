package bean;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class Logout {
	public String logout() throws IOException
	{
		System.out.println("testing logout");
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();

	    final HttpServletRequest r = (HttpServletRequest)ec.getRequest();
	    r.getSession( false ).invalidate();

	    return "logout";
	       

	}
}
