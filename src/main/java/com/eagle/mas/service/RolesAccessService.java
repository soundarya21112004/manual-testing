package com.eagle.mas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.mas.model.MstRoles;
import com.eagle.mas.repository.RolesAccessRepository;

@Service
@Transactional
public class RolesAccessService implements RolesAccessRepository {
	@Autowired
	RolesAccessRepository rolesAccessRepository;

	@Override
	public <S extends MstRoles> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends MstRoles> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MstRoles> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<MstRoles> findAll() {
		  return rolesAccessRepository.findAll();
	}

	@Override
	public Iterable<MstRoles> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}
	public Iterable<String> findbyRoleName(String id) {
		// TODO Auto-generated method stub
		return rolesAccessRepository.findbyRoleName(id);
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
	public void delete(MstRoles entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends MstRoles> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	 public Iterable<String> findRoleName() {
	        return rolesAccessRepository.findRoleName();
	    }
	 
	 public Iterable<MstRoles> findAllRoles() {
	        return rolesAccessRepository.findAllRoles();
	    }
	 
	 public ArrayList<String> findAllRolesId() {
	        return rolesAccessRepository.findAllRolesId();
	    }

	@Override
	public MstRoles findbyRoleId(String roleid) {
		// TODO Auto-generated method stub
		return rolesAccessRepository.findbyRoleId(roleid);
	}
	 

}
