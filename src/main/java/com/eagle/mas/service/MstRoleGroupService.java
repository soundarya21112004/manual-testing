package com.eagle.mas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.eagle.mas.model.MstRoleGroup;
import com.eagle.mas.repository.MstRoleGroupRepository;

@Service
public class MstRoleGroupService implements MstRoleGroupRepository {
	
	@Autowired
	MstRoleGroupRepository mstRoleGroupRepository;

	@Override
	public <S extends MstRoleGroup> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends MstRoleGroup> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MstRoleGroup> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<MstRoleGroup> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<MstRoleGroup> findAllById(Iterable<Integer> ids) {
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
	public void delete(MstRoleGroup entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends MstRoleGroup> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer groupId() {
		// TODO Auto-generated method stub
		return mstRoleGroupRepository.groupId();
	}

	@Override
	public Iterable<MstRoleGroup> listRoleToGroup() {
		// TODO Auto-generated method stub
		return mstRoleGroupRepository.listRoleToGroup();
	}

	@Override
	public MstRoleGroup findbygroupId(Integer i) {
		// TODO Auto-generated method stub
		return mstRoleGroupRepository.findbygroupId(i);
	}

	@Override
	public List findByGroupName(String groupName) {
		// TODO Auto-generated method stub
		return mstRoleGroupRepository.findByGroupName(groupName);
	}

	

}
