package service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.SuggestedStock;
import model.SuggestedStockPK;
import bean.StockSuggestionBean;

public class StockSuggestionService {
	EntityManagerFactory emf= Persistence.createEntityManagerFactory("PMS");
	EntityManager em= emf.createEntityManager();
	EntityTransaction et = em.getTransaction();


	public void mergeSuggestion(StockSuggestionBean bean) {
		// TODO Auto-generated method stub
		et.begin();
		SuggestedStock suggestedStock=new SuggestedStock();
		SuggestedStockPK stockPK=new SuggestedStockPK();
		int cid=Integer.parseInt(bean.getCustId());
		stockPK.setCustId(cid);
		stockPK.setPfmId(bean.getPfmId());
		stockPK.setStockTickr(bean.getStockTickr());
		suggestedStock.setId(stockPK);
		suggestedStock.setComments(bean.getSuggestion());
		suggestedStock.setSuggestedDate(bean.getSuggestedDate());
		em.merge(suggestedStock);
		et.commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<SuggestedStock> findAllSuggestionsByCustUser(int cid,int pid)
	{
		Query query=em.createQuery("Select s from SuggestedStock s where s.id.pfmId=?1 and s.id.custId=?2");
		query.setParameter(1, pid);
		query.setParameter(2, cid);
		List<SuggestedStock> list=(List<SuggestedStock>)query.getResultList();
		return list;
	}
	
	public List<SuggestedStock> findAllSuggestionsByCustomer(int cid)
	{
		Query query=em.createQuery("Select s from SuggestedStock s where s.id.custId=?1");
		query.setParameter(1, cid);
		@SuppressWarnings("unchecked")
		List<SuggestedStock> list=(List<SuggestedStock>)query.getResultList();
		return list;
	}
}
