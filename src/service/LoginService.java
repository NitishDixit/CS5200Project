package service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.CustomerDetail;
import model.Login;
import bean.LoginBean;

public class LoginService {

	EntityManagerFactory emf= Persistence.createEntityManagerFactory("PMS");
	EntityManager em= emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	public String authenticate(LoginBean lb)
	{
		et.begin();
		String uname=lb.getUsername();
		Login login = em.find(Login.class, uname);
		et.commit();
		if(login==null)
		{
			return "error";
		}
		else if(!(login.getPassword().equals(lb.getPassword())))
		{
			return "error";
		}
		else
		{
			lb.setRole(login.getRole());
			lb.setMessage("");
			if(lb.getRole().equals("a"))
			{
				return "admin";
			}
			else if(lb.getRole().equals("p"))
			{
				return "pm";
			}
			else
			{
				Query q=em.createQuery("select c from CustomerDetail c where c.username=?1");
				q.setParameter(1, login.getUsername());
				CustomerDetail cd= (CustomerDetail) q.getSingleResult();
				if(cd.getStatus().equals("a"))
					return "cust";
				else 
					return "cust-fail";
			}
		}
	}
	public Login find(String username)
	{
		et.begin();
		Login login= em.find(Login.class, username);
		et.commit();
		return login;		
	}
	public String validateUser(LoginBean bean) {
		// TODO Auto-generated method stub
		et.begin();
		Login log=em.find(Login.class, bean.getUsername());
		et.commit();
		if(log!=null)
		{
			return "success";
			
			
		}
		else
		return "error";
	}
	public String validateAnswer(LoginBean bean) {
		
		
		// TODO Auto-generated method stub
		Query q = em.createQuery("select c from CustomerDetail c where c.username=?1");
		q.setParameter(1, bean.getUsername());
		CustomerDetail cd= (CustomerDetail) q.getSingleResult();
		System.out.println(cd.getAnswer());
		System.out.println(bean.getAnswer());
		if(cd.getAnswer().equalsIgnoreCase(bean.getAnswer()))
		{
			System.out.println(cd.getAnswer().equalsIgnoreCase(bean.getAnswer()));
			return "success";
		}
		else
			return "fail";
	}
	public String changePassword(LoginBean b) {
		// TODO Auto-generated method stub
		Login log=em.find(Login.class, b.getUsername());
		log.setPassword(b.getPassword());
		et.begin();
			em.merge(log);
		et.commit();
		return "success";
	}
}
		
