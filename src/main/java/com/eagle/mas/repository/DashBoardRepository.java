package com.eagle.mas.repository;

import com.eagle.mas.model.RegisterManualVerification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
@Repository
public interface DashBoardRepository extends CrudRepository<RegisterManualVerification, BigInteger> {
    //dash board operator
    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.op1verifyStatus='hit' and t1.op2verifyStatus='hit') and (t1.op1userId=:id  or t1.op2userId=:id)   ")
    public int numberofHitsOp(@Param("id") String id);

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.op1verifyStatus='nohit' and t1.op2verifyStatus='nohit') and (t1.op1userId=:id  or t1.op2userId=:id)   ")
    public int numberofNoHitsOp(@Param("id") String id);

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.op1verifyStatus is null or t1.op2verifyStatus is null) and (t1.regId <> t1.matchedRefId) and ((t1.op1userId<>:id  or t1.op1userId is null ) and (t1.op2userId<>:id  or t1.op2userId is null ))  ")
    public int numberofUnverifiedRecordsOp(@Param("id") String id);

    @Query(value = "SELECT count(t1) FROM  RegisterManualVerification t1  WHERE (t1.op1verifyStatus='hit' and t1.op2verifyStatus='hit') and (t1.op1userId=:id  or t1.op2userId=:id) and (MONTH(t1.op1updDate)=:month AND YEAR(t1.op1updDate)=:year)")
    public int hitsThisMonthOp(@Param("year") int year, @Param("month") int month,@Param("id") String id);

    //    @Query(value = "SELECT count(t1) FROM  RegisterManualVerification t1  WHERE  (MONTH(t1.op1updDate)=:month AND YEAR(t1.op1updDate)=:year)")
//    public int toalRecordsPerMonth(@Param("year") int year,@Param("month") int month);
    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where (t1.regId <> t1.matchedRefId)")
    public int toalRecordsOp();


    //dash board supervisor
    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where  t1.supervisorVerifyStatus='hit' and t1.userId=:id  ")
    public int numberofHitsSuper(@Param("id") String id);

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where  t1.supervisorVerifyStatus='nohit' and t1.userId=:id   ")
    public int numberofNoHitsSuper(@Param("id") String id);

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.supervisorVerifyStatus is null) and ((t1.op1verifyStatus='hit' and t1.op2verifyStatus='nohit') or (t1.op1verifyStatus='nohit' and t1.op2verifyStatus='hit'))   ")
    public int numberofUnverifiedRecordsSuper();

    @Query(value = "SELECT count(t1) FROM  RegisterManualVerification t1  WHERE  (t1.userId=:id) and ( t1.supervisorVerifyStatus='hit') and (MONTH(t1.supervisorUpdatedDate)=:month AND YEAR(t1.supervisorUpdatedDate)=:year)")
    public int hitsThisMonthSuper(@Param("year") int year, @Param("month") int month,@Param("id") String id);

    //    @Query(value = "SELECT count(t1) FROM  RegisterManualVerification t1  WHERE  (MONTH(t1.op1updDate)=:month AND YEAR(t1.op1updDate)=:year)")
//    public int toalRecordsPerMonth(@Param("year") int year,@Param("month") int month);
    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.regId <> t1.matchedRefId) ")
    public int toalRecordsSuper();
}
