package com.eagle.mas.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.mas.model.MstRoleToGroup;
import com.eagle.mas.model.MstRolegroupToUser;

@Repository
public interface RolesRepository extends CrudRepository<MstRolegroupToUser, Integer> {

	@Query("select concat(t4.roleName,'~',t4.icon) from MstRoleToGroup t1 inner join MstRolegroupToUser t2 on t1.mstRoleGroup=t2.roleGroupId "
			+ "inner join Userdetails t3 on t3.userid=t2.userdetails "
			+ "inner join MstRoles t4 on t4.roleid = t1.mstRoles where  t3.userid=:id and t4.active='1' "
			+ "order by t4.displayOrder")
	Set<String> findAllByUserid(@Param("id") String email);

	@Query("select concat(t4.roleName,'~',t4.subRoleName) from MstRoleToGroup t1 inner join MstRolegroupToUser t2 on t1.mstRoleGroup=t2.roleGroupId "
			+ "inner join Userdetails t3 on t3.userid=t2.userdetails "
			+ "inner join MstRoles t4 on t4.roleid = t1.mstRoles where  t3.userid=:id and t4.active='1' "
			+ "order by t4.displayOrder")
	Set<String> findAllSubrolesByUserid(@Param("id") String email);

	@Query("select concat(t4.subRoleName,'~',count(*)) from MstRoleToGroup t1 inner join MstRolegroupToUser t2 on t1.mstRoleGroup=t2.roleGroupId "
			+ "inner join Userdetails t3 on t3.userid=t2.userdetails "
			+ "inner join MstRoles t4 on t4.roleid = t1.mstRoles where  t3.userid=:id and t4.active='1' "
			+ "group by t4.subRoleName")
	Set<String> findAllSubrolesByUseridCount(@Param("id") String email);

	/*
	 * @Query("select t1 from MstRoleToGroup t1 inner join MstRolegroupToUser t2 on t1.mstRoleGroup=t2.roleGroupId "
	 * + "inner join Userdetails t3 on t3.userid=t2.userdetails " +
	 * "JOIN FETCH t1.mstRoles t4  where  t3.userid=:id and t4.active='1' order by t4.displayOrder"
	 * ) List<MstRoleToGroup> findAllGroups(@Param("id") String email);
	 */
	
	@Query("select t1 from MstRoleToGroup t1 inner join MstRolegroupToUser t2 on t1.mstRoleGroup=t2.roleGroupId "
			  + "inner join Userdetails t3 on t3.userid=t2.userdetails " 
			  + "JOIN FETCH t1.mstRoles t4  where  t3.userid=:userid and t4.active='1' order by t4.displayOrder")
	List<MstRoleToGroup> findAllGroups(@Param("userid") String userid);
	

}
