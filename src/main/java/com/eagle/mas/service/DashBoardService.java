package com.eagle.mas.service;

import com.eagle.mas.model.RegisterManualVerification;
import com.eagle.mas.repository.DashBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;
@Service
public class DashBoardService implements DashBoardRepository {
    @Autowired
    DashBoardRepository dash;
    @Override
    public int numberofHitsOp(String id){
        int count=dash.numberofHitsOp(id);
        return count;
    }
    @Override
    public int numberofNoHitsOp(String id){
        int count=dash.numberofNoHitsOp(id);
        return count;
    }
    @Override
    public int numberofUnverifiedRecordsOp(String id){
        int count=dash.numberofUnverifiedRecordsOp(id);
        return count;
    }

    @Override
    public int hitsThisMonthOp(int year,int month,String id){
        int count=dash.hitsThisMonthOp(year,month,id);
        return count;
    }
//	@Override
//	public int toalRecordsPerMonth(int year,int month){
//		int count=repo.toalRecordsPerMonth(year,month);
//		return count;
//	}

    @Override
    public int toalRecordsOp(){
        int count=dash.toalRecordsOp();
        return count;
    }





    @Override
    public int numberofHitsSuper(String userId){
        int count=dash.numberofHitsSuper(userId);
        return count;
    }
    @Override
    public int numberofNoHitsSuper(String userId){
        int count=dash.numberofNoHitsSuper(userId);
        return count;
    }
    @Override
    public int numberofUnverifiedRecordsSuper(){
        int count=dash.numberofUnverifiedRecordsSuper();
        return count;
    }

    @Override
    public int hitsThisMonthSuper(int year,int month,String userId){
        int count=dash.hitsThisMonthSuper(year,month,userId);
        return count;
    }
//	@Override
//	public int toalRecordsPerMonth(int year,int month){
//		int count=repo.toalRecordsPerMonth(year,month);
//		return count;
//	}

    @Override
    public int toalRecordsSuper(){
        int count=dash.toalRecordsSuper();
        return count;
    }


    @Override
    public <S extends RegisterManualVerification> S save(S entity) {
        return null;
    }

    @Override
    public <S extends RegisterManualVerification> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<RegisterManualVerification> findById(BigInteger bigInteger) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(BigInteger bigInteger) {
        return false;
    }

    @Override
    public Iterable<RegisterManualVerification> findAll() {
        return null;
    }

    @Override
    public Iterable<RegisterManualVerification> findAllById(Iterable<BigInteger> bigIntegers) {
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
    public void delete(RegisterManualVerification entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends RegisterManualVerification> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
