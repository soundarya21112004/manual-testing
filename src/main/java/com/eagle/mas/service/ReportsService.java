package com.eagle.mas.service;

import com.eagle.mas.model.RegisterManualVerification;
import com.eagle.mas.repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportsService implements ReportsRepository {
@Autowired
ReportsRepository reportsRepository;

    @Override
    public ArrayList<String> getRegid(){
        return reportsRepository.getRegid();
    }

    @Override
    public  int allUnverifiedReports(){
        int  reports= reportsRepository.allUnverifiedReports();
        return reports;
    }
    @Override
    public  int allverifiedReports(){
        int  reports= reportsRepository.allverifiedReports();
        return reports;
    }
    @Override
    public  int deadlockedReports(){
        int  reports= reportsRepository.deadlockedReports();
        return reports;
    }
    @Override
    public  int verifiedDeadlockedReports(){
        int  reports= reportsRepository.verifiedDeadlockedReports();
        return reports;
    }
    @Override
    public  int hitcasesReports(){
        int  reports= reportsRepository.hitcasesReports();
        return reports;
    }
    @Override
    public  int hitCasesFraudReports(){
        int  reports= reportsRepository.hitCasesFraudReports();
        return reports;
    }
    @Override
    public  int nohitDecisionsReports(){
        int  reports= reportsRepository.nohitDecisionsReports();
        return reports;
    }

    @Override
    public  int hitDecisionsReports(){
        int  reports= reportsRepository.hitDecisionsReports();
        return reports;
    }
    @Override
    public  int demographicReports(){
        int  reports= reportsRepository.demographicReports();
        return reports;
    }

    @Override
    public  int biometricReports(){
        int  reports= reportsRepository.biometricReports();
        return reports;
    }

    //             operator reports


    @Override
    public  int unverifiedOperator(){
        int  reports= reportsRepository.unverifiedOperator();
        return reports;
    }
    @Override
    public  int verifiedOperator(){
        int  reports= reportsRepository.verifiedOperator();
        return reports;
    }
    @Override
    public  int deadlockedOperator(){
        int  reports= reportsRepository.deadlockedOperator();
        return reports;
    }

    @Override
    public  int hitcasesOperator(){
        int  reports= reportsRepository.hitcasesOperator();
        return reports;
    }

    @Override
    public  int nohitDecisionsOperator(){
        int  reports= reportsRepository.nohitDecisionsOperator();
        return reports;
    }
    @Override
    public  int hitDecisionsOperator(){
        int  reports= reportsRepository.hitDecisionsOperator();
        return reports;
    }

    @Override
    public  int demographicOperator(){
        int  reports= reportsRepository.demographicOperator();
        return reports;
    }
    @Override
    public  int biometricOperator(){
        int  reports= reportsRepository.biometricOperator();
        return reports;
    }
// supervisor reports
@Override
public  int unverifiedSupervisor(){
    int  reports= reportsRepository.unverifiedSupervisor();
    return reports;
}

    public  int verifiedSupervisor(){
        int  reports= reportsRepository.verifiedSupervisor();
        return reports;
    }

    public  int deadlockedSupervisor(){
        int  reports= reportsRepository.deadlockedSupervisor();
        return reports;
    }

    public  int verifiedDeadlockedSupervisor(){
        int  reports= reportsRepository.verifiedDeadlockedSupervisor();
        return reports;
    }

    public  int hitcasesSupervisor(){
        int  reports= reportsRepository.hitcasesSupervisor();
        return reports;
    }

    public  int hitCasesFraudSupervisor(){
        int  reports= reportsRepository.hitCasesFraudSupervisor();
        return reports;
    }

    public  int nohitDecisionsSupervisor(){
        int  reports= reportsRepository.nohitDecisionsSupervisor();
        return reports;
    }

    public  int hitDecisionsSupervisor(){
        int  reports= reportsRepository.hitDecisionsSupervisor();
        return reports;
    }

    public  int demographicSupervisor(){
        int  reports= reportsRepository.demographicSupervisor();
        return reports;
    }
    public  int biometricSupervisor(){
        int  reports= reportsRepository.biometricSupervisor();
        return reports;
    }

// common reprts


//    @Override
//    public  List<RegisterManualVerification> allReports(){
//        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsRepository.allReports();
//        return reports;
//    }
//    @Override
//    public List<RegisterManualVerification> verifiedReports(){
//        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsRepository.verifiedReports();
//        return reports;
//    }
//
//    @Override
//    public List<RegisterManualVerification> deadlockReports(){
//        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsRepository.deadlockReports();
//        return reports;
//    }
//    @Override
//    public List<RegisterManualVerification> supervisorReports(){
//        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsRepository.supervisorReports();
//        return reports;
//
//    }
//
//    @Override
//    public List<RegisterManualVerification> hitCasesReports(){
//        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsRepository.hitCasesReports();
//        return reports;
//
//    }
//    @Override
//    public List<RegisterManualVerification> fraudReports(){
//        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsRepository.fraudReports();
//        return reports;
//    }
//    @Override
//    public List<RegisterManualVerification> noHitDecisionsReports(){
//        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsRepository.noHitDecisionsReports();
//        return reports;
//    }
//    @Override
//    public List<RegisterManualVerification> hitDecisionsReports(){
//        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsRepository.hitDecisionsReports();
//        return reports;
//
//    }
//
//    @Override
//    public List<RegisterManualVerification> demographicReports(){
//        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsRepository.demographicReports();
//        return reports;
//
//    }
//
//
//    @Override
//    public List<RegisterManualVerification> biometricReports(){
//        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsRepository.biometricReports();
//        return reports;
//
//    }

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
