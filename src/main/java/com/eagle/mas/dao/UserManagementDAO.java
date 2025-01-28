package com.eagle.mas.dao;

import java.util.Date;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.eagle.mas.model.MstRolegroupToUser;
import com.eagle.mas.model.Userdetails;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional("transactionManager")
public class UserManagementDAO {


	@Autowired
	@Qualifier("entityManagerFactory")
	private EntityManager em;

	public boolean saveAll(Userdetails user) {
		boolean out = true;
		try {  
			em.persist(user);
		} catch (Exception e) {
			e.printStackTrace();
			out = false;
		}
		return out;

	}
	
	public boolean saveUserToRole(MstRolegroupToUser mstRolegroupToUser) {
		boolean out = true;
		try {  
			em.persist(mstRolegroupToUser);
		} catch (Exception e) {
			e.printStackTrace();
			out = false;
		}
		return out;

	}
	
	
	public boolean updateAll(Userdetails user) {
		boolean out = true;
		try {  
			
			Userdetails userdetails = em.find(Userdetails.class, user.getUserid());
			userdetails.setFirstnameEn(user.getFirstnameEn());
			userdetails.setMiddleName(user.getMiddleName());
			userdetails.setLastnameEn(user.getLastnameEn());
			userdetails.setDesignation(user.getDesignation());
//			userdetails.setDepartment(user.getDepartment());
			userdetails.setOrganisation(user.getOrganisation());
			userdetails.setEmail(user.getEmail());
			userdetails.setContactnumber(user.getContactnumber());
			//userdetails.setLicenseissueoffice(user.getLicenseissueoffice());
			userdetails.setEnteredDate(new Date());
			em.merge(userdetails);
		} catch (Exception e) {
			e.printStackTrace();
			out = false;
		}
		return out;

	}
	public Userdetails findbyUserId(String id) {
		try {
			Userdetails instance = em.find(Userdetails.class, id);
			if (instance != null) {
				//Hibernate.initialize(instance.getLicenseissueoffice());
			}
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public boolean UpdateStatus(Userdetails user, String userid) {
		boolean out = true;
		try {

			Userdetails instance = em.find(Userdetails.class, userid);

			if (user.getPassword() != null) {
				instance.setPassword(user.getPassword());
			}
			if (user.getVerifycodePwd() != null) {
				instance.setVerifycodePwd(user.getVerifycodePwd());
			}
			if (user.getLoginStatus() != null) {
				instance.setLoginStatus(user.getLoginStatus());
			}
			em.merge(instance);
			out = true;
		} catch (Exception e) {
			e.printStackTrace();
			out = false;
		}
		return out;

	}

}
