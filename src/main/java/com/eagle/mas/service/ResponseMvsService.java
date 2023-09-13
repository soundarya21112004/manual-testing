package com.eagle.mas.service;

import com.eagle.mas.model.ResponseMvs;
import com.eagle.mas.repository.ResponseMvsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResponseMvsService implements ResponseMvsRepository {
    @Autowired
    ResponseMvsRepository ResponseMvsRepository;

    public ArrayList getResponseOrID(){
        ArrayList OridList= (ArrayList) ResponseMvsRepository.getResponseOrID();
        return OridList;
    }

    public int getResponseSno(){
        int sno=ResponseMvsRepository.getResponseSno();
        return sno;
    }

    @Override
    public <S extends ResponseMvs> S save(S entity) {
        return null;
    }

    @Override
    public <S extends ResponseMvs> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ResponseMvs> findById(BigInteger bigInteger) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(BigInteger bigInteger) {
        return false;
    }

    @Override
    public Iterable<ResponseMvs> findAll() {
        return null;
    }

    @Override
    public Iterable<ResponseMvs> findAllById(Iterable<BigInteger> bigIntegers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(BigInteger bigInteger) {

    }

    @Override
    public void delete(ResponseMvs entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends ResponseMvs> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
