package service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.CustomerDetail;
import model.CustomerPfm;
import model.CustomerPfmPK;
import model.PfmDetail;

public class CustomerPFMService {

	EntityManagerFactory emf= Persistence.createEntityManagerFactory("PMS");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et= em.getTransaction();
	
	@SuppressWarnings("unchecked")
	public List<CustomerPfm> getALLCustomerandPFM()
	{
		Query q= em.createQuery("select cp from CustomerPfm cp");
		List<CustomerPfm> list=(List<CustomerPfm>)q.getResultList();
		return list;
	}
	
	public String save(int cid,int pid)
	{
		CustomerDetail customerDetail=em.find(CustomerDetail.class, cid);
		PfmDetail pfmDetail = em.find(PfmDetail.class, pid);
		CustomerPfmPK pfmPK= new CustomerPfmPK();
		pfmPK.setCustUser(cid);
		pfmPK.setPfmUser(pid);
		CustomerPfm customerPfm= em.find(CustomerPfm.class, pfmPK);
				
		
		if (customerDetail==null)
		{
			return "cust-fail";
		}
		else if (pfmDetail==null)
		{
			return "pm-fail";
		}
		else if (customerPfm!=null)
		{
			return "pm-exist-fail";
		}
		else
		{
			et.begin();
			CustomerPfm custp=new CustomerPfm();
			custp.setId(pfmPK);
			em.persist(custp);
			et.commit();
			return "success";
		}
	}	
	public String delete(int cid,int pid)
	{
		CustomerDetail customerDetail=em.find(CustomerDetail.class, cid);
		PfmDetail pfmDetail = em.find(PfmDetail.class, pid);
		CustomerPfmPK pfmPK= new CustomerPfmPK();
		pfmPK.setCustUser(cid);
		pfmPK.setPfmUser(pid);
		CustomerPfm customerPfm= em.find(CustomerPfm.class, pfmPK);
				
		
		if (customerDetail==null)
		{
			return "cust-fail";
		}
		else if (pfmDetail==null)
		{
			return "pm-fail";
		}
		else if (customerPfm==null)
		{
			return "pm-not-exist-fail";
		}
		else
		{
			et.begin();
			em.remove(customerPfm);
			et.commit();
			return "success";
		}
	}
	public Boolean validate(int cid,int pid)
	{
		CustomerPfmPK pk=new CustomerPfmPK();
		pk.setCustUser(cid);
		pk.setPfmUser(pid);
		CustomerPfm pfm2=em.find(CustomerPfm.class, pk);
		if(pfm2==null)
			return false;
		else 
			return true;
	}
	
	public PfmDetail retrievePM(String username)
	{
		Query query = em.createQuery("Select c from CustomerDetail c where c.username=?1");
		query.setParameter(1, username);
		CustomerDetail cd =(CustomerDetail)query.getSingleResult();
		int cid=cd.getId();
		Query query1= em.createQuery("Select cp from CustomerPfm cp where cp.id.custUser=?1");
		query1.setParameter(1, cid);
		@SuppressWarnings("unchecked")
		List<CustomerPfm> customerPfm=(List<CustomerPfm>)query1.getResultList();
		if(customerPfm.size()==0)
			return null;
		else
		{
			CustomerPfm pfm= customerPfm.get(0);
			int pid=pfm.getId().getPfmUser();
			PfmDetail pfmDetail= em.find(PfmDetail.class, pid);
			return pfmDetail;
		}		
	}
}

