package com.eagle.mas.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.eagle.mas.model.Userdetails;

@Repository
public interface UserdetailsRepository extends CrudRepository<Userdetails, String> {

	@Query(value = "SELECT t1.designation from Userdetails t1 where t1.userid=:userid")
	public String getUserType(@Param( "userid")String  userid);

	@Query(value = "SELECT t1 from Userdetails t1 where t1.activestatus='0'")
	public List <Userdetails> getUserdetails();
	@Modifying
	@Query(value = "UPDATE  Userdetails t1 set t1.activestatus='1' where t1.userid=:userid")
	public void setActivestatus(@Param( "userid")String  userid);

	@Modifying
	@Query(value = "UPDATE  Userdetails t1 set t1.activestatus='2' where t1.userid=:userid")
	public void setActivestatus2(@Param( "userid")String  userid);

	@Modifying
	@Query(value = "UPDATE  Userdetails t1 set t1.userApproval=:status where t1.userid=:userid ")
	public void setUserApproval(@Param( "status")String status, @Param( "userid")String  userid);

	@Query(value = "SELECT t1 from Userdetails t1 where t1.userid=:userid ")
	public Userdetails getUserdetails1(@Param( "userid")String  userid);

	@Query(value = "SELECT t1 from Userdetails t1 where t1.email=:username ")
	public Userdetails passwordChangeDetails(@Param( "username")String  username);
	
	@Query(value = "SELECT MAX(sno)+1 from Userdetails")
	public int maxUserid();
	
	@Query(value = "SELECT u FROM Userdetails u  where u.belongsTo=:groupName")
	public List <Userdetails> findbyGroupName(@Param("groupName") String groupName);
	
	@Query(value = "SELECT u FROM Userdetails u  where u.userid=:userId")
	public Userdetails findbyUserId(@Param("userId") String userId);
	
	@Query(value = "SELECT u.contactnumber FROM Userdetails u where u.contactnumber=:phone")
	public String findByphone(@Param("phone") String contactnumber);
	
	@Query(value = "SELECT u.contactnumber FROM Userdetails u where u.contactnumber=:phone and u.userid!=:userid")
	public String findByphoneByuserid(@Param("phone") String contactnumber, @Param("userid") String userid);
	
	@Query(value = "SELECT u FROM Userdetails u  where u.verifycodePwd=:oldPwd")
	public Userdetails findbyOldPwd(@Param("oldPwd") String oldPwd);

	@Query(value = "SELECT u FROM Userdetails u  where u.email=:email")
	public Userdetails findUserdetailsByEmail(@Param("email") String email);
	
	@Modifying(flushAutomatically = true)
    @Query(value = "update Userdetails u set u.password=:enpassword, u.verifycodePwd=:password where u.userid=:userId ")       
	public int updatePasswordDetails(@Param("enpassword") String enpassword, @Param("password") String password, @Param("userId") String userId);

	@Query(value = "SELECT u.verifycodePwd FROM Userdetails u  where u.userid=:userId")
	public String getUserOldPassword(@Param("userId") String userId);
	
}
