package com.eagle.mas.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.eagle.mas.model.MstRoleGroup;
import com.eagle.mas.model.MstRoleToGroup;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional("transactionManager")
public class AccessControlDAO {
	@Autowired
	@Qualifier("entityManagerFactory")
	private EntityManager em;

	public boolean saveAll(MstRoleGroup mstRoleGroup) {
		boolean out = true;
		try {

			em.persist(mstRoleGroup);
			// address.setApplicant(app);
			// em.persist(mstRoleToGroup);
			// exam.setApplicant(app);

			out = true;
		} catch (Exception e) {
			e.printStackTrace();
			out = false;
		}
		return out;

	}

	public boolean saveRoleToGroup(MstRoleToGroup mstRoleToGroup) {
		boolean out = true;
		try {
//			em.persist(em);

			em.persist(mstRoleToGroup);

			out = true;
		} catch (Exception e) {
			e.printStackTrace();
			out = false;
		}
		return out;

	}
	
	public boolean updateRoleToGroup(MstRoleToGroup existingRoles) {
		boolean out = true;
		try {

			em.remove(existingRoles);

			out = true;
		} catch (Exception e) {
			e.printStackTrace();
			out = false;
		}
		return out;

	}

}
