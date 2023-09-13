package com.eagle.mas.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.mas.model.MstRolegroupToUser;
import com.eagle.mas.repository.MstRoleGroupToUserRepository;

@Service
public class MstRoleGroupToUserService implements MstRoleGroupToUserRepository{

	@Autowired
	MstRoleGroupToUserRepository mstRoleGroupToUserRepository;
	
	@Override
	public <S extends MstRolegroupToUser> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends MstRolegroupToUser> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MstRolegroupToUser> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<MstRolegroupToUser> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<MstRolegroupToUser> findAllById(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(MstRolegroupToUser entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends MstRolegroupToUser> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int sno() {
		// TODO Auto-generated method stub
		return mstRoleGroupToUserRepository.sno();
	}

}
