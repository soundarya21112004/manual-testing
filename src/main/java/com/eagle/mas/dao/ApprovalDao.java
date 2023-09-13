package com.eagle.mas.dao;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.*;
import javax.transaction.Transactional;

import com.eagle.mas.model.MstRoleToGroup;

import com.eagle.mas.model.Userdetails;
import com.eagle.mas.repository.RegManualVerificationRepository;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eagle.mas.model.TblAssigned;
import com.eagle.mas.service.AssignmentService;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ApprovalDao {
	@Autowired
	private AssignmentService service;
	//@Autowired
	private EntityManager entity;
	@Autowired
	private SessionFactory factory;
	@Autowired
	RegManualVerificationRepository regManualVerificationRepository;

	public boolean saveGallery(String gallery,String rid) {
		
		try {
			
			TblAssigned assign = new TblAssigned();
			assign.setGallery(gallery);
			assign.setAssignedDate(new java.util.Date());
			assign.setRid(rid);
			
			Integer maxsno = service.getmaxsno();
			if(maxsno!=null) {
				assign.setSno(maxsno +1 );
			}else {
				assign.setSno(1);
			}
			entity.persist(assign);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
