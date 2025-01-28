package com.eagle.mas.dao;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.eagle.mas.model.Userdetails;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional("transactionManager")
public class LoginDAO {

	@Autowired
	@Qualifier("entityManagerFactory")
	private EntityManager em;

	public Userdetails getAllPersons(String email, String pwd) {
		Userdetails user = null;
		try {
			final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			System.out.println("email "+pwd);
			CriteriaQuery<Userdetails> crit = criteriaBuilder.createQuery(Userdetails.class);
			Root<Userdetails> root = crit.from(Userdetails.class);
			System.out.println("email "+email);
			System.out.println("email "+pwd);
			crit.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("email"), email),
					criteriaBuilder.equal(root.get("password"), pwd)));
			TypedQuery<Userdetails> query = em.createQuery(crit);
			user = (Userdetails) query.getSingleResult();
			/*
			 * if (user != null) { Hibernate.initialize(user.getLicenseissueoffice()); }
			 */
		} catch (Exception e) {
e.printStackTrace();
		}
		return user;

	}



}
