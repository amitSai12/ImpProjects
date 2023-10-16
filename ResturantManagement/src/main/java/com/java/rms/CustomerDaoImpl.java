package com.java.rms;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CustomerDaoImpl implements CustomerDAO{

	SessionFactory sf;
	Session session;
	
	private String localCode;
	private double pursevalue;
	
	
	public double getPursevalue() {
		return pursevalue;
	}
	public void setPursevalue(double pursevalue) {
		this.pursevalue = pursevalue;
	}
	public String getLocalCode() {
		return localCode;
	}
	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}
	public void employLocaleCodeChanged(ValueChangeEvent e){
		//assign new value to localeCode
		this.localCode = e.getNewValue().toString();
		System.out.println("Selected Wallet...." +localCode);
		Map<String, Object> sessionMap =
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("payType", localCode);
		//this.pursevalue = purseValue(localCode);
//		System.out.println(this.localCode);
//		this.venId = getVendorId(localCode);
//		System.out.println(this.venId);
	}
	@Override
	public List<Customer> showCustomerDao() {
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		Criteria criteria = session.createCriteria(Customer.class);
		List<Customer> customersList = criteria.list();
		return customersList;
	}
	@Override
	public String searchCustomerDetailsDao() {
	Map<String, Object> sessionMap =
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	 String customerLoggined = (String)sessionMap.get("loggedCustomer");
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		Criteria criteria = session.createCriteria(Customer.class);
		
		criteria.add(Restrictions.eq("cususername", customerLoggined));
		Customer customerData = (Customer) criteria.uniqueResult();
	//	sessionMap.put("customerFound", customerData);
		sessionMap.put("customerId", customerData.getCusid());
		return "CustomerInfo.jsp?faces-redirect=true";
	}
	
	@Override
	public String addCustomerDao(Customer customer) {
		String pwd = EncryptPassword.getCode(customer.getCuspassword());
		customer.setCuspassword(pwd);
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		Transaction trans = session.beginTransaction();
		session.save(customer);
		trans.commit();
		String body ="Welcome to Mr/Miss  " +customer.getCusname() + "\r\n"+
				"Your Account Created Successfully...";
		MailSend.mailOtp(customer.getCusemail(), "Otp Send Succesfully...", body);
		
		return "CustomerLogin.jsp?faces-redirect=true";
	}

	@Override
	public String customerLoginDao(Customer customer) {
		 Map<String, Object> sessionMap =
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		String encPass = EncryptPassword.getCode(customer.getCuspassword());
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		Criteria cr = session.createCriteria(Customer.class);
		cr.add(Restrictions.eqOrIsNull("cususername", customer.getCususername()));
		//to get the userName
		cr.add(Restrictions.eqOrIsNull("cuspassword", encPass.trim()));
		cr.setProjection(Projections.rowCount());
		//count of rows acc to the following condition
		long count = (long)cr.uniqueResult();
		session.close();
		sf.close();

		if(count==1) {
			sessionMap.put("loggedCustomer",  customer.getCususername());
			sf = SessionHelper.getConnection();
			session = sf.openSession();
			cr = session.createCriteria(Customer.class);
			cr.add(Restrictions.eqOrIsNull("cususername", customer.getCususername()));
			Customer customerFound = (Customer) cr.uniqueResult(); //customer detail is stored in customerFound
			sessionMap.put("customerFound", customerFound);
			sessionMap.put("customerId", customerFound.getCusid()); // we put customerId from customerFound here
			return "Dashboard.jsp?faces-redirect=true";
		}else {
			sessionMap.put("errorMessage", "Invalid Credentials....");
			return "CustomerLogin.jsp?faces-redirect=true";
		}
	}
	@Override
	public String showCustomerWalletDao() {
		 Map<String, Object> sessionMap =
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		 int custId = (Integer)sessionMap.get("customerId"); // and the customerId is fetched from above
		 // getting custId from the session
		 Criteria criteria = session.createCriteria(Wallet.class);
		 criteria.add(Restrictions.eq("custId", custId));
		 List<Wallet> walletList = criteria.list();
		 sessionMap.put("walletList", walletList);
		 return "CustomerWalletShow.jsp?faces-redirect=true";
	}
	
	public List<String> showCustomerWalleNames() {
		 Map<String, Object> sessionMap =
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		int custId = (Integer)sessionMap.get("customerId");
		Criteria criteria = session.createCriteria(Wallet.class);
		criteria.add(Restrictions.eq("custId", custId));
		Projection projection = Projections.property("walletSource");
		criteria.setProjection(projection);
		List<String> walletList = criteria.list();
		System.out.println(walletList);
		return walletList;
	}
	public double purseValue(String walletSource) {
		WalletSource wSource = WalletSource.valueOf(walletSource);
		 Map<String, Object> sessionMap =
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		 Integer custId = (Integer)sessionMap.get("customerId");
		// int custId = 4;
		 //String payType = (String) sessionMap.get("payType").toString();
		 System.out.println("CustomerId is"+custId);
		 //System.out.println("Paytype is"+payType);
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		Criteria criteria = session.createCriteria(Wallet.class);
		WalletSource wS = WalletSource.valueOf(walletSource);
		criteria.add(Restrictions.eq("custId", custId));
		criteria.add(Restrictions.eq("walletSource", wS));
		Projection projection = Projections.property("walletAmount");
		criteria.setProjection(projection);
		double pursevalue = (double) criteria.uniqueResult();
		System.out.println("Purse value is"+pursevalue);
		return pursevalue;
		
	}
	public Wallet searchwalletById(int cusid, String walletSource) {
	//	String wal = walletSource.valueOf(walletSource);
		sf = SessionHelper.getConnection();
		System.out.println(cusid);
		System.out.println(walletSource);
		WalletSource ws = WalletSource.valueOf(walletSource);
		session = sf.openSession();
		Criteria criteria = session.createCriteria(Wallet.class);
		criteria.add(Restrictions.eq("custId", cusid));
		criteria.add(Restrictions.eq("walletSource", ws));
		Wallet wallet = (Wallet) criteria.uniqueResult();
		System.out.println(wallet);
		return wallet;
	}
}
