package com.eagle.mas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.mas.model.MstRoleToGroup;
import com.eagle.mas.model.MstRolegroupToUser;
import com.eagle.mas.repository.RolesRepository;

@Service
public class RolesService implements RolesRepository {

	@Autowired
	private RolesRepository repository;

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
	public Set<String> findAllByUserid(String userid) {
		// TODO Auto-generated method stub
		Set<String> userlist = (Set<String>) repository.findAllByUserid(userid);
		return userlist;

	}

	@Override
	public List<MstRoleToGroup> findAllGroups(String userid) {
		ArrayList<MstRoleToGroup> cities = (ArrayList<MstRoleToGroup>) repository.findAllGroups(userid);
		return cities;
	}

	@Override
	public Set<String> findAllSubrolesByUserid(String email) {
		return repository.findAllSubrolesByUserid(email);
	}

	@Override
	public Set<String> findAllSubrolesByUseridCount(String email) {
		return repository.findAllSubrolesByUseridCount(email);
	}

}
