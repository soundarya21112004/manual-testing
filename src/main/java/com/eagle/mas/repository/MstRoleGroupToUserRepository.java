package com.eagle.mas.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.eagle.mas.model.MstRolegroupToUser;

@Repository
public interface MstRoleGroupToUserRepository extends CrudRepository<MstRolegroupToUser, Integer>{
	
	@Query(value = "SELECT MAX(sno)+1 from MstRolegroupToUser")
	public int sno();

}
