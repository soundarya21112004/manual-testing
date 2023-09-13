package com.eagle.mas.repository;


import com.eagle.mas.model.RegisterManualVerification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ReportsRepository extends CrudRepository<RegisterManualVerification, BigInteger> {


    @Query(value="SELECT  t1.regId,count(t1.matchedRefId) from RegisterManualVerification t1 where t1.regId in (select distinct t2.regId from RegisterManualVerification t2 ) group by t1.regId")
    public ArrayList<String> getRegid();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where ((t1.op1verifyStatus is null) and(t1.op2verifyStatus is null)) and (t1.regId <> t1.matchedRefId) ")
   public int allUnverifiedReports();

 @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) or (t1.op2verifyStatus='hit' ) or (t1.supervisorVerifyStatus = 'hit')) and (t1.regId <> t1.matchedRefId) ")
 public int allverifiedReports();

   @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) and(t1.op2verifyStatus='nohit')) " +
           "OR ((t1.op1verifyStatus='nohit' ) and(t1.op2verifyStatus='hit'))")
   public int deadlockedReports();

 @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') " )
 public int verifiedDeadlockedReports();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) or (t1.op2verifyStatus='hit' ) or (t1.supervisorVerifyStatus = 'hit'))")
   public int hitcasesReports();


    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit'))) " )
    public int hitCasesFraudReports();

 @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where t1.op1verifyStatus='nohit' or t1.op2verifyStatus='nohit' or t1.supervisorVerifyStatus='nohit' ")
 public int nohitDecisionsReports();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where t1.op1verifyStatus='hit' or t1.op2verifyStatus='hit' or t1.supervisorVerifyStatus='hit' ")
    public int hitDecisionsReports();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit' and t1.trntypcode ='Demographic Potential Match' ) or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit' and t1.trntypcode ='Demographic Potential Match'))) " )
    public int demographicReports();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit' and t1.trntypcode ='Biometric Potential Match' ) or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit' and t1.trntypcode ='Biometric Potential Match'))) " )
    public int biometricReports();

// opertaor reports

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where ((t1.op1verifyStatus is null) and(t1.op2verifyStatus is null)) and (t1.regId <> t1.matchedRefId) ")
    public int unverifiedOperator();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) or (t1.op2verifyStatus='hit' )) and (t1.regId <> t1.matchedRefId) ")
    public int verifiedOperator();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) and(t1.op2verifyStatus='nohit')) " +
            "OR ((t1.op1verifyStatus='nohit' ) and(t1.op2verifyStatus='hit'))")
    public int deadlockedOperator();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) or (t1.op2verifyStatus='hit' ))")
    public int hitcasesOperator();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where t1.op1verifyStatus='nohit' or t1.op2verifyStatus='nohit'  ")
    public int nohitDecisionsOperator();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where t1.op1verifyStatus='hit' or t1.op2verifyStatus='hit'  ")
    public int hitDecisionsOperator();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where ( (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit' and t1.trntypcode ='Demographic Potential Match'))) " )
    public int demographicOperator();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where ((t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit' and t1.trntypcode ='Biometric Potential Match'))) " )
    public int biometricOperator();



    //supervisor reports
    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where (t1.supervisorVerifyStatus is null ) and (((t1.op1verifyStatus='hit' ) and(t1.op2verifyStatus='nohit')) OR ((t1.op1verifyStatus='nohit' ) and(t1.op2verifyStatus='hit')))")
    public int unverifiedSupervisor();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where (t1.supervisorVerifyStatus='hit' ) ")
    public int verifiedSupervisor();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) and(t1.op2verifyStatus='nohit')) " +
            "OR ((t1.op1verifyStatus='nohit' ) and(t1.op2verifyStatus='hit'))")
    public int deadlockedSupervisor();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.supervisorVerifyStatus='hit') " )
    public int verifiedDeadlockedSupervisor();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where (t1.supervisorVerifyStatus='hit' )")
    public int hitcasesSupervisor();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.supervisorVerifyStatus='hit' ) " )
    public int hitCasesFraudSupervisor();
    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where t1.supervisorVerifyStatus='nohit'  ")
    public int nohitDecisionsSupervisor();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where t1.supervisorVerifyStatus='hit'  ")
    public int hitDecisionsSupervisor();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where   (t1.supervisorVerifyStatus='hit' and t1.trntypcode ='Demographic Potential Match') " )
    public int demographicSupervisor();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where  (t1.supervisorVerifyStatus='hit' and t1.trntypcode ='Biometric Potential Match') " )
    public int biometricSupervisor();





//    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus is null) and(t1.op2verifyStatus is null))")
//    public List<RegisterManualVerification> allReports();
//
//    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) and(t1.op2verifyStatus='hit'))")
//    public List<RegisterManualVerification> verifiedReports();
//
//    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) and(t1.op2verifyStatus='nohit')) " +
//            "OR ((t1.op1verifyStatus='nohit' ) and(t1.op2verifyStatus='hit'))")
//    public List<RegisterManualVerification> deadlockReports();
//
//    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where (t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') " )
//
//    public List<RegisterManualVerification> supervisorReports();
//
//    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where ((t1.op1verifyStatus='hit' ) and(t1.op2verifyStatus='hit')) " )
//
//    public List<RegisterManualVerification> hitCasesReports();
//
//    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit'))) " )
//
//    public List<RegisterManualVerification> fraudReports();
//
//
//
//    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='nohit' ) and(t1.op2verifyStatus='nohit'))")
//    public List<RegisterManualVerification> noHitDecisionsReports();
//
//    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) and(t1.op2verifyStatus='hit'))")
//    public List<RegisterManualVerification> hitDecisionsReports();
//
//    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit' and t1.trntypcode ='Demographic Potential Match' ) or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit' and t1.trntypcode ='Demographic Potential Match'))) " )
//    public List<RegisterManualVerification> demographicReports();
//
//    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit' and t1.trntypcode ='Biometric Potential Match' ) or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit' and t1.trntypcode ='Biometric Potential Match'))) " )
//    public List<RegisterManualVerification> biometricReports();

}
