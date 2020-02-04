package com.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.Authorities;
import com.model.Cart;
import com.model.Customer;
import com.model.User;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addCustomer(Customer customer) {
		System.out.println("Adding customer in dao");
		Session session = sessionFactory.openSession();
		
		customer.getUsers().setEnabled(true);
		
		Authorities authorities = new Authorities();
		authorities.setAuthorities("ROLE_USER");
		authorities.setEmailId(customer.getUsers().getEmailId());
		
		Cart cart = new Cart();
		
		customer.setCart(cart);
		
		cart.setCustomer(customer);
		session.save(customer);
		session.save(authorities);
		session.flush();
		session.close();
	}

	public List<Customer> getAllCustomers() {
		Session session = sessionFactory.openSession();
		List<Customer> customerList = session.createQuery("from Customer").list();
		
		return customerList;
	}

	public Customer getCustomerByemailId(String emailId) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where emailId=?");
		query.setString(0, emailId);
		User users = (User)query.uniqueResult();
		Customer customer = users.getCustomer();
		return customer;
	}
	
	
}
