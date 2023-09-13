package com.eagle.mas.repository;

import com.eagle.mas.model.RegisterManualVerification;
import com.eagle.mas.model.ResponseMvs;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ResponseMvsRepository extends CrudRepository<ResponseMvs, BigInteger> {

    @Query(value = "SELECT MAX(sno)+1 from ResponseMvs ")
    public int getResponseSno();

    @Query(value = "SELECT t1.orid from ResponseMvs t1")
    public List getResponseOrID();

}
