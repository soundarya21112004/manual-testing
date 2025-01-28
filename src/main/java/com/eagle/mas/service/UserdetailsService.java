package com.eagle.mas.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.mas.model.Userdetails;
import com.eagle.mas.repository.UserdetailsRepository;

@Transactional
@Service
public class UserdetailsService implements UserdetailsRepository {
	@Autowired
	UserdetailsRepository userrepository;
	@Override
public String getUserType(String id){
	String usertype=userrepository.getUserType(id);
	return usertype;
}

	@Override
	public Userdetails findUserdetailsByEmail(String email){
		Userdetails list=userrepository.findUserdetailsByEmail(email);
		return list;
	}
	@Override
	public ArrayList <Userdetails> getUserdetails(){
		 ArrayList<Userdetails> list= (ArrayList) userrepository.getUserdetails();
		return list;
	}
	@Override
	public Set<String> getOperator() {
		return userrepository.getOperator();
	}

	@Override
	public void setActivestatus(String userid){
		userrepository.setActivestatus(userid);
	}
	@Override
	public void setActivestatus2(String userid){
		userrepository.setActivestatus2(userid);
	}
	@Override
	public void setUserApproval(String status,String userid){
		userrepository.setUserApproval( status,userid);
	}
	@Override
	public Userdetails getUserdetails1(String userid){
		Userdetails   list=  userrepository.getUserdetails1(userid);
		return list;
	}
	@Override
	public Userdetails passwordChangeDetails(String username){
		Userdetails   list=  userrepository.passwordChangeDetails(username);
		return list;
	}


	@Override
	public <S extends Userdetails> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Userdetails> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Userdetails> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Userdetails> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Userdetails> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Userdetails entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends Userdetails> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public int maxUserid() {

		return userrepository.maxUserid();
	}

	@Override
	public List<Userdetails> findbyGroupName(String groupName) {
		// TODO Auto-generated method stub
		return userrepository.findbyGroupName(groupName);
	}

	@Override
	public Userdetails findbyUserId(String userId) {
		// TODO Auto-generated method stub
		return userrepository.findbyUserId(userId);
	}
	
	public String findByphone(String contactnumber) {
		return userrepository.findByphone(contactnumber);
	}

	public String findByphoneByuserid(String contactnumber, String userid) {
		return userrepository.findByphoneByuserid(contactnumber, userid);
	}

	@Override
	public Userdetails findbyOldPwd(String oldPwd) {
		return userrepository.findbyOldPwd(oldPwd);
	}

	@Override
	public int updatePasswordDetails(String enpassword, String password, String userId) {
		return userrepository.updatePasswordDetails(enpassword, password, userId);
	}
	@Override
	public String getUserOldPassword( String userId) {
		return userrepository.getUserOldPassword( userId);
	}

	public int checkmail(String email) {
		 return userrepository.checkmail(email);
	}
}
