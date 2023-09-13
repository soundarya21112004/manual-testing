package com.eagle.mas.dao;

import com.eagle.mas.model.MstRoleGroup;
import com.eagle.mas.model.ResponseMvs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class ResponseMvsDao {
    @Autowired
    private EntityManager em;
    public void saveAll(ResponseMvs responseObj) {
        try {

            em.persist(responseObj);
            // address.setApplicant(app);
            // em.persist(mstRoleToGroup);
            // exam.setApplicant(app);


        } catch (Exception e) {
            e.printStackTrace();

        }
    }



}
