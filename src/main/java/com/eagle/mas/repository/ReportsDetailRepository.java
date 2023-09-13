package com.eagle.mas.repository;

import com.eagle.mas.model.RegisterManualVerification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ReportsDetailRepository extends CrudRepository<RegisterManualVerification, BigInteger> {

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus is null) and(t1.op2verifyStatus is null)) and (t1.regId <> t1.matchedRefId)")
    public List<RegisterManualVerification> allUnverifiedReports();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) or (t1.op2verifyStatus='hit' ) or (t1.supervisorVerifyStatus = 'hit')) and (t1.regId <> t1.matchedRefId) ")
    public List<RegisterManualVerification> allverifiedReports();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) and(t1.op2verifyStatus='nohit')) " +
            "OR ((t1.op1verifyStatus='nohit' ) and(t1.op2verifyStatus='hit'))")
    public List<RegisterManualVerification> deadlockedReports();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where (t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') " )
    public List<RegisterManualVerification> verifiedDeadlockedReports();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) or (t1.op2verifyStatus='hit' ) or (t1.supervisorVerifyStatus = 'hit'))")
    public List<RegisterManualVerification> hitcasesReports();


    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit'))) " )
    public List<RegisterManualVerification> hitCasesFraudReports();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.op1verifyStatus='nohit' or t1.op2verifyStatus='nohit' or t1.supervisorVerifyStatus='nohit' ")
    public List<RegisterManualVerification> nohitDecisionsReports();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.op1verifyStatus='hit' or t1.op2verifyStatus='hit' or t1.supervisorVerifyStatus='hit' ")
    public List<RegisterManualVerification> hitDecisionsReports();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit' and t1.trntypcode ='Demographic Potential Match' ) or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit' and t1.trntypcode ='Demographic Potential Match'))) " )
    public List<RegisterManualVerification> demographicReports();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit' and t1.trntypcode ='Biometric Potential Match' ) or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit' and t1.trntypcode ='Biometric Potential Match'))) " )
    public List<RegisterManualVerification> biometricReports();

    //operator reports



    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus is null) and(t1.op2verifyStatus is null)) and (t1.regId <> t1.matchedRefId) ")
    public ArrayList<RegisterManualVerification> unverifiedOperator();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) or (t1.op2verifyStatus='hit' )) and (t1.regId <> t1.matchedRefId) ")
    public ArrayList<RegisterManualVerification> verifiedOperator();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) and(t1.op2verifyStatus='nohit')) " +
            "OR ((t1.op1verifyStatus='nohit' ) and(t1.op2verifyStatus='hit'))")
    public ArrayList<RegisterManualVerification> deadlockedOperator();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) or (t1.op2verifyStatus='hit' ))")
    public ArrayList<RegisterManualVerification> hitcasesOperator();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.op1verifyStatus='nohit' or t1.op2verifyStatus='nohit'  ")
    public ArrayList<RegisterManualVerification> nohitDecisionsOperator();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.op1verifyStatus='hit' or t1.op2verifyStatus='hit'  ")
    public ArrayList<RegisterManualVerification> hitDecisionsOperator();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where ( (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit' and t1.trntypcode ='Demographic Potential Match'))) " )
    public ArrayList<RegisterManualVerification> demographicOperator();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where ((t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit' and t1.trntypcode ='Biometric Potential Match'))) " )
    public ArrayList<RegisterManualVerification> biometricOperator();


    //supervisor reports
    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where (t1.supervisorVerifyStatus is null ) and (((t1.op1verifyStatus='hit' ) and(t1.op2verifyStatus='nohit')) OR ((t1.op1verifyStatus='nohit' ) and(t1.op2verifyStatus='hit')))")
    public  ArrayList<RegisterManualVerification> unverifiedSupervisor();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where (t1.supervisorVerifyStatus='hit' )  ")
    public  ArrayList<RegisterManualVerification> verifiedSupervisor();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where ((t1.op1verifyStatus='hit' ) and(t1.op2verifyStatus='nohit')) " +
            "OR ((t1.op1verifyStatus='nohit' ) and(t1.op2verifyStatus='hit'))")
    public  ArrayList<RegisterManualVerification> deadlockedSupervisor();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where (t1.supervisorVerifyStatus='hit') " )
    public  ArrayList<RegisterManualVerification> verifiedDeadlockedSupervisor();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1  where (t1.supervisorVerifyStatus='hit' )")
    public  ArrayList<RegisterManualVerification> hitcasesSupervisor();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where (t1.supervisorVerifyStatus='hit' ) " )
    public  ArrayList<RegisterManualVerification> hitCasesFraudSupervisor();
    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.supervisorVerifyStatus='nohit'  ")
    public  ArrayList<RegisterManualVerification> nohitDecisionsSupervisor();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.supervisorVerifyStatus='hit'  ")
    public  ArrayList<RegisterManualVerification> hitDecisionsSupervisor();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where   (t1.supervisorVerifyStatus='hit' and t1.trntypcode ='Demographic Potential Match') " )
    public  ArrayList<RegisterManualVerification> demographicSupervisor();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where  (t1.supervisorVerifyStatus='hit' and t1.trntypcode ='Biometric Potential Match') " )
    public  ArrayList<RegisterManualVerification> biometricSupervisor();
}


