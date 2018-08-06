package com.cg.wallet.dao;






import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.cg.wallet.Exception.AccountException;
import com.cg.wallet.bean.Account;


public class WalletDao implements IWallet {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
	 
	EntityManager em = emf.createEntityManager();
	@Override
	public String createAccount(Account acc) throws AccountException {
		
		em.getTransaction().begin();
		em.persist(acc);
		em.getTransaction().commit();
		return acc.getMobileNo();
	}

	@Override
	public double showBalance(String mobileNo) throws AccountException {
		String str="select a from Account a where a.mobileNo=?";
		TypedQuery<Account> query=em.createQuery(str,Account.class);
		query.setParameter(1,mobileNo);
		Account ac=query.getSingleResult();
		if(mobileNo.equals(ac.getMobileNo())) {
		return ac.getBalance();
		}else {
		throw new AccountException("number doesnot exists");
	}
	}
	@Override
	public Account deposit(String mobileNo, double depAmt)
			throws AccountException {
		em.getTransaction().begin();
		String str="select a from Account a where a.mobileNo=?";
		TypedQuery<Account> query=em.createQuery(str,Account.class);
		query.setParameter(1,mobileNo);
		Account ac=query.getSingleResult();
		if(ac==null) {
		throw new AccountException("does not exists");
		}
		double d=ac.getBalance()+depAmt;
		ac.setBalance(d);
		em.merge(ac);
		 
		 
		em.getTransaction().commit();
		return ac;
	}

	@Override
	public Account withdraw(String mobileNo, double withdrawAmt)
			throws AccountException {
		
			em.getTransaction().begin();
			String str="select a from Account a where a.mobileNo=?";
			TypedQuery<Account> query=em.createQuery(str,Account.class);
			query.setParameter(1,mobileNo);
			Account ac=query.getSingleResult();
			if(ac==null) {
			throw new AccountException("does not exists");
			}
			double d=ac.getBalance()-withdrawAmt;
			ac.setBalance(d);
			em.merge(ac);
			 
			 
			em.getTransaction().commit();
			return ac;
	}

	@Override
	public Account printTransactionDetails(String mobileNo)throws AccountException {
		String str="select a from Account a where a.mobileNo=?";
		TypedQuery<Account> query=em.createQuery(str,Account.class);
		query.setParameter(1,mobileNo);
		Account ac=query.getSingleResult();
		if(ac==null) {
		return ac;
		}else {
		throw new AccountException("number doesnot exists");
		}
		}
	

	

	}


