package service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.CustomerDetail;
import model.CustomerPfm;
import model.Login;
import model.PfmDetail;
import bean.PMBean;

public class PMService {

	EntityManagerFactory emf= Persistence.createEntityManagerFactory("PMS");
	EntityManager em= emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public String save(PMBean bean)
	{
		System.out.println("1");
		PfmDetail detail=new PfmDetail();
		detail.setPfmName(bean.getPfm_name());
		detail.setContactNumber(bean.getContact_number());
		detail.setUsername(bean.getUsername());
		System.out.println("2");
		detail.setAddress(bean.getAddress());
		Login login=new Login();
		login.setPassword(bean.getPassword());
		login.setUsername(bean.getUsername());
		login.setRole("p");
		System.out.println("3");
		et.begin();
		em.merge(detail);
		em.merge(login);
		System.out.println("4");
		et.commit();
		System.out.println("5");
		return "success";
	}
	
	public List<PfmDetail> getAllPM()
	{
		Query q = em.createQuery("Select p from PfmDetail p");
		@SuppressWarnings("unchecked")
		List<PfmDetail> list=(List<PfmDetail>)q.getResultList();
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	public String delete(int pid)
	{
		PfmDetail pfmDetail=em.find(PfmDetail.class,pid);
		Query query=em.createQuery("Select cp from CustomerPfm cp where cp.id.pfmUser=?1");
		query.setParameter(1, pid);
		List<CustomerPfm> list=(List<CustomerPfm>)query.getResultList();
		
		int size = list.size();
		
		
		if(pfmDetail!=null)
		{
			et.begin();
			for (int i=0;i<size;i++)
			{
				em.remove(list.get(i));
			}
			Login login=em.find(Login.class,pfmDetail.getUsername());
			em.remove(pfmDetail);
			em.remove(login);
			et.commit();
			return "success";
		}
		else 
			return "fail";
	}
	
	public PfmDetail getValue(String Username)
	{
		Query q=em.createQuery("Select p from PfmDetail p where p.username= ?1");
		q.setParameter(1, Username);
		PfmDetail detail= (PfmDetail) q.getSingleResult();
		return detail;
	}
	public int getPfmId(String Username)
	{
		Query q=em.createQuery("Select p from PfmDetail p where p.username= ?1");
		q.setParameter(1, Username);
		PfmDetail detail= (PfmDetail) q.getSingleResult();
		return detail.getId();
	}

	public void update(PMBean bean) {
		// TODO Auto-generated method stub
		PfmDetail detail=new PfmDetail();
		detail.setAddress(bean.getAddress());
		detail.setContactNumber(bean.getContact_number());
		detail.setId(bean.getId());
		detail.setPfmName(bean.getPfm_name());
		detail.setUsername(bean.getUsername());
		Login login=new Login();
		login.setUsername(bean.getUsername());
		login.setRole("p");
		login.setPassword(bean.getPassword());
		et.begin();
		em.merge(detail);
		em.merge(login);
		et.commit();
	}

	@SuppressWarnings("unchecked")
	public List<CustomerDetail> getAllCust(String username) {
		// TODO Auto-generated method stub
		List<CustomerDetail> customerDetails=new ArrayList<CustomerDetail>();
		Query q= em.createQuery("select p from PfmDetail p where p.username=?1");
		q.setParameter(1, username);
		PfmDetail pfmDetail=(PfmDetail) q.getSingleResult();
		int pmid=pfmDetail.getId();
		Query q1=em.createQuery("Select cp from CustomerPfm cp where cp.id.pfmUser=?1");
		q1.setParameter(1, pmid);
		List<CustomerPfm> customerPfms=(List<CustomerPfm>)q1.getResultList();
		int size=customerPfms.size();
		for(int i=0;i<size;i++)
		{
			CustomerDetail customerDetail=em.find(CustomerDetail.class, customerPfms.get(i).getId().getCustUser());
			customerDetails.add(customerDetail);
		}
		return customerDetails;
	}
}
