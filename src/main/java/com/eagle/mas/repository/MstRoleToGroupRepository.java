package com.eagle.mas.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.eagle.mas.model.MstRoleToGroup;

@Repository
public interface MstRoleToGroupRepository extends CrudRepository<MstRoleToGroup, Integer>{
	
	@Query(value = "SELECT MAX(sno)+1 from MstRoleToGroup")
	public int sno();
	
	
//	@Query(value = "SELECT r.roleid FROM MstRoleToGroup t join fetch t.mstRoleGroup ms join fetch t.mstRoles r where ms.groupId =:groupId")			
//	public ArrayList<String> findbyGroupId(@Param("groupId") int groupId);
	
	@Query(value = "SELECT  t  FROM MstRoleToGroup t join fetch t.mstRoleGroup ms join fetch t.mstRoles r where ms.groupId =:groupId")			
	public ArrayList<MstRoleToGroup> findbyGroupId(@Param("groupId") int groupId);
	
	@Query(value = "SELECT  t  FROM MstRoleToGroup t join fetch t.mstRoleGroup ms where ms.groupId =:groupId")			
	public List<MstRoleToGroup> findbyAllRoles(@Param("groupId") int groupId);
	
}
