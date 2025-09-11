package com.eagle.mas.regproc.repo;

import com.eagle.mas.regproc.model.AbisRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AbisRequestRepo extends JpaRepository<AbisRequest, String> {
    @Query(value = "SELECT t1  FROM AbisRequest t1 where t1.bioRefId=:bioRefId and t1.requestType='IDENTIFY' and t1.statusCode in ('ALREADY_PROCESSED' , 'PROCESSED') order by t1.crDtimes desc")
    AbisRequest findIdByRefId(String bioRefId);



}
