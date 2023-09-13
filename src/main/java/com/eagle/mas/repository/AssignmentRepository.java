package com.eagle.mas.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.mas.model.TblAssigned;

import javax.transaction.Transactional;

@Repository
public interface AssignmentRepository extends CrudRepository<TblAssigned, BigInteger> {
    @Query(value = "Select max(sno) from TblAssigned")
    public Integer getmaxsno();

    @Query(value = "Select a from TblAssigned a where a.rid=:rid")
    public TblAssigned getDetailsByRID(@Param("rid") String rid);

}
