package com.eagle.mas.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eagle.mas.model.TblAssigned;
import com.eagle.mas.repository.AssignmentRepository;
@Service
public class AssignmentService implements AssignmentRepository {
	@Autowired
	AssignmentRepository repo;

	@Override
	public <S extends TblAssigned> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends TblAssigned> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<TblAssigned> findById(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(BigInteger id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<TblAssigned> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<TblAssigned> findAllById(Iterable<BigInteger> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(BigInteger id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(TblAssigned entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends TblAssigned> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getmaxsno() {
		// TODO Auto-generated method stub
		return repo.getmaxsno();
	}

	@Override
	public TblAssigned getDetailsByRID(String rid) {
		// TODO Auto-generated method stub
		return repo.getDetailsByRID(rid);
	}

}
