package com.eagle.mas.dao;

import com.eagle.mas.model.MstRoleGroup;
import com.eagle.mas.model.ResponseMvs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional("transactionManager")
public class ResponseMvsDao {
    @Autowired
    @Qualifier("entityManagerFactory")
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
