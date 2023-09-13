package com.eagle.mas.service;

import com.eagle.mas.model.RegisterManualVerification;
import com.eagle.mas.repository.ReportsDetailRepository;
import com.eagle.mas.repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportsDetailService implements ReportsDetailRepository {
    @Autowired
    ReportsDetailRepository reportsDetailRepository;

    @Override
    public ArrayList<RegisterManualVerification> allUnverifiedReports(){
        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsDetailRepository.allUnverifiedReports();
        return reports;
    }
    @Override
    public   ArrayList<RegisterManualVerification>   allverifiedReports(){
        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsDetailRepository.allverifiedReports();
        return reports;
    }
    @Override
    public   ArrayList<RegisterManualVerification>   deadlockedReports(){
        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsDetailRepository.deadlockedReports();
        return reports;
    }
    @Override
    public   ArrayList<RegisterManualVerification>   verifiedDeadlockedReports(){
        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsDetailRepository.verifiedDeadlockedReports();
        return reports;
    }
    @Override
    public   ArrayList<RegisterManualVerification>   hitcasesReports(){
        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsDetailRepository.hitcasesReports();
        return reports;
    }
    @Override
    public   ArrayList<RegisterManualVerification>   hitCasesFraudReports(){
        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsDetailRepository.hitCasesFraudReports();
        return reports;
    }
    @Override
    public   ArrayList<RegisterManualVerification>   nohitDecisionsReports(){
        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsDetailRepository.nohitDecisionsReports();
        return reports;
    }

    @Override
    public   ArrayList<RegisterManualVerification>   hitDecisionsReports(){
        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsDetailRepository.hitDecisionsReports();
        return reports;
    }
    @Override
    public  ArrayList<RegisterManualVerification>   demographicReports(){
        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsDetailRepository.demographicReports();
        return reports;
    }

    @Override
    public   ArrayList<RegisterManualVerification>   biometricReports(){
        ArrayList<RegisterManualVerification> reports= (ArrayList<RegisterManualVerification>) reportsDetailRepository.biometricReports();
        return reports;
    }



    //operator reports

    @Override
    public  ArrayList<RegisterManualVerification> unverifiedOperator(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.unverifiedOperator();
        return reports;
    }
    @Override
    public  ArrayList<RegisterManualVerification> verifiedOperator(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.verifiedOperator();
        return reports;
    }
    @Override
    public  ArrayList<RegisterManualVerification> deadlockedOperator(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.deadlockedOperator();
        return reports;
    }

    @Override
    public  ArrayList<RegisterManualVerification> hitcasesOperator(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.hitcasesOperator();
        return reports;
    }

    @Override
    public  ArrayList<RegisterManualVerification> nohitDecisionsOperator(){
        ArrayList<RegisterManualVerification>   reports= reportsDetailRepository.nohitDecisionsOperator();
        return reports;
    }
    @Override
    public  ArrayList<RegisterManualVerification>  hitDecisionsOperator(){
        ArrayList<RegisterManualVerification>   reports= reportsDetailRepository.hitDecisionsOperator();
        return reports;
    }

    @Override
    public  ArrayList<RegisterManualVerification>  demographicOperator(){
        ArrayList<RegisterManualVerification>   reports= reportsDetailRepository.demographicOperator();
        return reports;
    }
    @Override
    public  ArrayList<RegisterManualVerification>  biometricOperator(){
        ArrayList<RegisterManualVerification>   reports= reportsDetailRepository.biometricOperator();
        return reports;
    }

    // supervisor reports
    @Override
    public   ArrayList<RegisterManualVerification> unverifiedSupervisor(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.unverifiedSupervisor();
        return reports;
    }

    public   ArrayList<RegisterManualVerification> verifiedSupervisor(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.verifiedSupervisor();
        return reports;
    }

    public   ArrayList<RegisterManualVerification> deadlockedSupervisor(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.deadlockedSupervisor();
        return reports;
    }

    public   ArrayList<RegisterManualVerification> verifiedDeadlockedSupervisor(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.verifiedDeadlockedSupervisor();
        return reports;
    }

    public   ArrayList<RegisterManualVerification> hitcasesSupervisor(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.hitcasesSupervisor();
        return reports;
    }

    public   ArrayList<RegisterManualVerification> hitCasesFraudSupervisor(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.hitCasesFraudSupervisor();
        return reports;
    }

    public   ArrayList<RegisterManualVerification> nohitDecisionsSupervisor(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.nohitDecisionsSupervisor();
        return reports;
    }

    public   ArrayList<RegisterManualVerification> hitDecisionsSupervisor(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.hitDecisionsSupervisor();
        return reports;
    }

    public   ArrayList<RegisterManualVerification> demographicSupervisor(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.demographicSupervisor();
        return reports;
    }
    public   ArrayList<RegisterManualVerification> biometricSupervisor(){
        ArrayList<RegisterManualVerification>  reports= reportsDetailRepository.biometricSupervisor();
        return reports;
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
