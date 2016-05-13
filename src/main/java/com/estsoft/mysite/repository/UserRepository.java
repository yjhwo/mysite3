package com.estsoft.mysite.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.GuestBook;
import com.estsoft.mysite.domain.User;

@Repository
public class UserRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public void insert(User user) {
		em.persist(user);
	}

	public User get(User user) {		// email, password 값이 있음

		TypedQuery<User> query = em.createQuery(
				"select u from User u where u.email = :email and u.password = :password", User.class);
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());
		
		List<User> list = query.getResultList();
		if (list.size() != 1) {
			return null;
		}
		return list.get(0);
	}

	public User get(String email) {
		
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email",
						User.class);
		query.setParameter("email", email);
		
		List<User> list = query.getResultList();
		if (list.size() != 1) {
			return null;
		}	
		return list.get(0);
	}
	
	

}
