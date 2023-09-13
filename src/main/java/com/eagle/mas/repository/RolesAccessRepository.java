package com.eagle.mas.repository;



import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.eagle.mas.model.MstRoles;

@Repository
public interface RolesAccessRepository extends CrudRepository<MstRoles, String> {
	
	
	
	@Query("select distinct(t1.roleName) from MstRoles t1 where t1.active='1' order by t1.roleName")
	Iterable<String>findRoleName();
	
	@Query("select t1 from MstRoles t1 where t1.active='1' order by t1.displayOrder")
	Iterable<MstRoles>findAllRoles();
	
	@Query("select t1.roleid from MstRoles t1 where t1.active='1' ")
	ArrayList<String>findAllRolesId();
	
	@Query(value = "SELECT t1 FROM MstRoles t1 where t1.roleid=:roleId")
	public MstRoles findbyRoleId(@Param("roleId") String roleid);
	
	@Query("select t1.roleDetails from MstRoles t1 where t1.roleid=:roleId and t1.active='1'")
	Iterable<String>findbyRoleName(@Param("roleId") String roleid);
	

	
	
	
			
	
	
}
