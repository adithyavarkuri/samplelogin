package com.mkyong.customer.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mkyong.customer.model.Customer;
import com.mkyong.listener.HibernateListener;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
 
public class CustomerAction extends ActionSupport 
	implements ModelDriven{

	Customer customer = new Customer();
	List<Customer> customerList = new ArrayList<Customer>();
	
	public String execute() throws Exception {
		return SUCCESS;
	}

	public Object getModel() {
		return customer;
	}
	
	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	//save customer
	public String addCustomer(Customer customer) throws Exception{
		try{
		if(customer!=null){
			
		}else{
			customer=new Customer();
			customer.setName(this.customer.getName());
			customer.setAddress(this.customer.getAddress());
		}
		//get hibernate session from the servlet context
		/*SessionFactory sessionFactory = 
	         (SessionFactory) ServletActionContext.getServletContext()
                     .getAttribute(HibernateListener.KEY_NAME);
*/
		Session session = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory().openSession();

		//save it
		customer.setCreatedDate(new Date());
	 
		session.beginTransaction();
		session.save(customer);
		session.getTransaction().commit();
	 
		//reload the customer list
		customerList = null;
		customerList = session.createQuery("from Customer").list();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e); 
		}
		
		return SUCCESS;
	
	}
	
	//list all customers
	public String listCustomer() throws Exception{
		
		/*//get hibernate session from the servlet context
		SessionFactory sessionFactory = 
	         (SessionFactory) ServletActionContext.getServletContext()
                     .getAttribute(HibernateListener.KEY_NAME);

		Session session = sessionFactory.openSession();*/
		try{
		Session session = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory().openSession();

		customerList = session.createQuery("from Customer").list();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e); 
		}
		
		return SUCCESS;
	
	}
	
	public String deleteCustomer(Customer cust)throws Exception{
		try{
			Session session = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory().openSession();
			session.beginTransaction();
			session.delete(cust);
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e); 
		}
		
		return SUCCESS;
	}
	
}