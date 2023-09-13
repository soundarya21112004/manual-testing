package com.eagle.mas.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.mas.model.MstRoleToGroup;
import com.eagle.mas.repository.MstRoleToGroupRepository;

@Service
public class MstRoleToGroupService implements MstRoleToGroupRepository{
	
	@Autowired
	MstRoleToGroupRepository mstRoleToGroupRepository; 

	@Override
	public <S extends MstRoleToGroup> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends MstRoleToGroup> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MstRoleToGroup> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<MstRoleToGroup> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<MstRoleToGroup> findAllById(Iterable<Integer> ids) {
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
	public void delete(MstRoleToGroup entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends MstRoleToGroup> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int sno() {
		// TODO Auto-generated method stub
		return mstRoleToGroupRepository.sno();
	}

	@Override
	public ArrayList<MstRoleToGroup> findbyGroupId(int groupId) {
		// TODO Auto-generated method stub
		return mstRoleToGroupRepository.findbyGroupId(groupId);
	}

	@Override
	public List<MstRoleToGroup> findbyAllRoles(int i) {
		// TODO Auto-generated method stub
		return mstRoleToGroupRepository.findbyAllRoles(i);
	}

	

//	@Override
//	public ArrayList<String> listRoleToGroup(BigDecimal groupId) {
//		// TODO Auto-generated method stub
//		return mstRoleToGroupRepository.listRoleToGroup(groupId);
//	}

}
