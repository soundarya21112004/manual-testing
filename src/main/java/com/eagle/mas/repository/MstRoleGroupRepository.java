package com.eagle.mas.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.eagle.mas.model.MstRoleGroup;

@Repository
public interface MstRoleGroupRepository extends CrudRepository<MstRoleGroup, Integer>{
	
	@Query(value = "SELECT MAX(groupId)+1 from MstRoleGroup")
	public Integer groupId();
	
	@Query(value = "SELECT t1 FROM MstRoleGroup t1 where t1.activestatus='1' order by t1.groupName")
	public Iterable<MstRoleGroup> listRoleToGroup();

	@Query(value = "SELECT t FROM MstRoleGroup t  where t.groupId=:groupId")
	public MstRoleGroup findbygroupId(@Param("groupId") Integer groupId);
	
	@Query(value = "SELECT t1 FROM MstRoleGroup t1 where t1.groupName=:groupName")
	public List findByGroupName(@Param("groupName") String groupName);
}
