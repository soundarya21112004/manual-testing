//package com.eagle.mas.repository;
//
//import com.eagle.mas.model.RegManualVerification;
//import com.eagle.mas.model.TblAssigned;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.math.BigInteger;
//import java.util.List;
//
//@Repository
//public interface RegManualVerificationRepository extends CrudRepository<RegManualVerification, BigInteger> {
//
//    @Query(value = "SELECT t1 FROM RegManualVerification t1 where t1.statusCode='0'")
//    public List listOfRids();
//
//    @Query(value = "SELECT t1 FROM RegManualVerification t1 where t1.statusCode='1'")
//    public List listOfRidsForL2();
//
////    @Query(value = "SELECT t1 FROM RegManualVerification t1 where t1.statusCode='2'")
//    @Query(value="select t1 from RegManualVerification t1 where (t1.statusCode='2')" +
//            " and (t1.verifyStatus='hit' or t1.verifyStatusTwo ='nohit') " +
//            "and (t1.verifyStatus='nohit' or t1.verifyStatusTwo='hit')")
//    public List listOfRidsForL3();
//
//    @Modifying
//    @Query(value = "update RegManualVerification t1 set t1.statusCode=:level,t1.statusComment=:comment,t1.verifyStatus=:status,t1.updatedBy=:userid," +
//            "t1.updatedDate=now() where t1.sno=:id")
//    public int  updateRID(@Param("id") int id,@Param("status") String status, @Param("comment") String comment,@Param("userid") String userid,@Param("level") String level);
//
//
//    @Modifying
//    @Query(value = "update RegManualVerification t1 set t1.statusCode=:level,t1.statusCommTwo=:comment,t1.verifyStatus=:status,t1.updatedByTwo=:userid," +
//            "t1.updatedDateTwo=now() where t1.sno=:id")
//    public int  updateRIDTwo(@Param("id") int id,@Param("status") String status, @Param("comment") String comment,@Param("userid") String userid,@Param("level") String level);
//
//    @Modifying
//    @Query(value = "update RegManualVerification t1 set t1.statusCode=:level,t1.statusCommThree=:comment,t1.verifyStatus=:status,t1.updatedByThree=:userid," +
//            "t1.updatedDateThree=now() where t1.sno=:id")
//    public int  updateRIDThree(@Param("id") int id,@Param("status") String status, @Param("comment") String comment,@Param("userid") String userid,@Param("level") String level);
//
//
//
//    @Query(value = "SELECT c.fileDatas FROM RegManualVerification c  where c.regId=:regid and c.matchedRefId=:mid")
//    public String fileDataCandidate(@Param("regid") String regid,@Param("mid") String mid);
//
//    @Query(value = "SELECT c.probeString FROM RegManualVerification c  where c.regId=:regid and c.matchedRefId=:mid")
//    public String fileDataProb(@Param("regid") String regid,@Param("mid") String mid);
//}
package com.eagle.mas.repository;

import com.eagle.mas.model.RegisterManualVerification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
public interface RegManualVerificationRepository extends JpaRepository<RegisterManualVerification, BigInteger> {

    int countAllByRegId(String regid);

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.op1verifyStatus='hit' and t1.op2verifyStatus='hit') or t1.supervisorVerifyStatus='hit'  ")
    int numberofHits();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.op1verifyStatus='nohit' and t1.op2verifyStatus='nohit') or t1.supervisorVerifyStatus='nohit'  ")
    int numberofNoHits();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.op1verifyStatus is null or t1.op2verifyStatus is null) and (t1.regId <> t1.matchedRefId)  ")
    int numberofUnverifiedRecords();

    @Query(value = "SELECT count(t1) FROM  RegisterManualVerification t1  WHERE ((t1.op1verifyStatus='hit' and t1.op2verifyStatus='hit') or t1.supervisorVerifyStatus='hit') and (MONTH(t1.op1updDate)=:month AND YEAR(t1.op1updDate)=:year)")
    int hitsThisMonth(@Param("year") int year,@Param("month") int month);

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where  (t1.regId <> t1.matchedRefId)")
    int toalRecords();

    @Query("SELECT count(t1) FROM RegisterManualVerification t1 where t1.regId=:regid")
    int totalResponseCases(@Param("regid") String regid);

    @Query("SELECT count(t1) FROM RegisterManualVerification t1 where t1.regId=:regid and ((t1.op1verifyStatus='hit' and t1.op2verifyStatus='hit') or t1.supervisorVerifyStatus='hit') ")
    int responseCasesHit(@Param("regid") String regid);

    @Query("SELECT count(t1) FROM RegisterManualVerification t1 where t1.regId=:regid and ((t1.op1verifyStatus='nohit' and t1.op2verifyStatus='nohit') or t1.supervisorVerifyStatus='nohit') ")
    int responseCasesNohit(@Param("regid") String regid);

    @Query(value = "SELECT t1.proStatus FROM RegisterManualVerification t1 where t1.regId=:regid and t1.matchedRefId=:mid and t1.reqid=:requestId")
    String proStatus(@Param("regid") String regid,@Param("mid") String mid,@Param("requestId") String requestId);

    //@Transactional
    @Modifying
    @Query(value = "update RegisterManualVerification t1  set t1.proStatus='1' where t1.sno=:sno")
    int modify_process_status(@Param("sno") int sno);

    @Modifying
    @Query(value = "update RegisterManualVerification t1  set t1.proStatus='0' where t1.sno=:sno")
    int modifyProcessStatus(@Param("sno") int sno);

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where t1.sno=:sno and t1.op1verifyStatus='nohit' and t1.op2verifyStatus='nohit' ")
    int operatorVerifiedNohit(@Param("sno") int sno);

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where t1.sno=:sno and t1.op1verifyStatus='hit' and t1.op2verifyStatus='hit' ")
    int operatorVerifiedHit(@Param("sno") int sno);

    @Modifying
    @Query(value = "update RegisterManualVerification t1  set t1.finindi='UIN' where t1.sno=:sno")
    int operatorUpdateNohit(@Param("sno") int sno);

    @Modifying
    @Query(value = "update RegisterManualVerification t1  set t1.finindi='DUP' where t1.sno=:sno")
    int operatorUpdateHit(@Param("sno") int sno);

    @Query(value = "SELECT count(t1)  FROM RegisterManualVerification t1 where t1.sno=:sno and t1.supervisorVerifyStatus='nohit' ")
    int supervisorVerifiedNohit(@Param("sno") int sno);

    @Query(value = "SELECT count(t1)  FROM RegisterManualVerification t1 where t1.sno=:sno and t1.supervisorVerifyStatus='hit' ")
    int supervisorVerifiedHit(@Param("sno") int sno);

    @Query(value = "SELECT t1.reqid  FROM RegisterManualVerification t1 where t1.sno=:sno ")
    String getReqId(@Param("sno") int sno);


    @Query(value = "SELECT count(t1)  FROM RegisterManualVerification t1 where t1.reqid=:ReqId ")
    int getReqIdCount(@Param("ReqId") String ReqId);


    @Query(value = "SELECT count(t1)  FROM RegisterManualVerification t1 where t1.reqid=:ReqId and (t1.finindi is not null) ")
    int getFinIndicate(@Param("ReqId") String ReqId);

    @Query(value = "SELECT count(t1)  FROM RegisterManualVerification t1 where t1.reqid=:reqid and t1.finindi='DUP' ")
    int getReturnVal(@Param("reqid") String reqid);


    @Query(value = "SELECT count(t1)  FROM RegisterManualVerification t1 where t1.reqid=:reqid and t1.finindi='UIN' ")
    int getCountforResponse(@Param("reqid") String reqid);


    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.sno=(SELECT min(t1.sno) FROM RegisterManualVerification t1 where ((t1.statusCode is null or t1.statusCode='0') and (t1.proStatus is null or t1.proStatus='0')) and (t1.op1userId<>:userid or t1.op1userId is null ) and (t1.regId <> t1.matchedRefId)  )")
    List listOfRids(@Param("userid") String userid);

    /*@Query(value = "SELECT t1.reqid FROM RegisterManualVerification t1 where t1.sno=(SELECT min(t1.sno) FROM RegisterManualVerification t1 where ((t1.statusCode is null or t1.statusCode='0') and (t1.proStatus is null or t1.proStatus='0')) and (t1.op1userId<>:userid or t1.op1userId is null ) and (t1.regId <> t1.matchedRefId) ) order by t1.createdDate asc ")
    List<String> getRequestIdOperator(@Param("userid") String userid, Pageable size);*/

    @Query(value = "SELECT t1.reqid FROM RegisterManualVerification t1 where t1.sno=(SELECT min(t1.sno) FROM RegisterManualVerification t1 where ((t1.statusCode is null or t1.statusCode='0') and (t1.proStatus is null or t1.proStatus='0')) and (t1.op1userId is null or t1.op2userId is null) and (t1.op1userId<>:userid or t1.op1userId is null) and (t1.op2userId<>:userid or t1.op2userId is null) and (t1.regId <> t1.matchedRefId) ) order by t1.createdDate asc ")
    List<String> getRequestIdOperator(@Param("userid") String userid, Pageable size);

    @Modifying
    @Query(value = "update public.register_manual_verification set operator1_verify_status=null,operator1_upd_date=null,\n" +
            "operator1_upd_by=null,operator1_comment=null,operator1_user_id=null, process_code ='0' where req_id =:reqId and sno =:sno",nativeQuery = true)
    void resetOp1CaseDecisions(@Param("reqId") String reqId,@Param("sno") int sno);

    @Modifying
    @Query(value = "update public.register_manual_verification set operator2_verify_status=null,operator2_upd_date=null,\n" +
            "operator2_upd_by=null,operator2_comment=null,operator2_user_id=null, process_code ='0', status_code ='0' where req_id =:reqId and sno =:sno",nativeQuery = true)
    void resetOp2CaseDecisions(@Param("reqId") String reqId,@Param("sno") int sno);

    @Modifying
    @Query(value = "update public.register_manual_verification set supervisor_verify_status=null,supervisor_upd_date=null,\n" +
            "supervisor_upd_by=null,supervisor_comment=null,user_id=null, process_code = '0', status_code='1' where req_id =:reqId and sno =:sno" ,nativeQuery = true)
    void resetSupervisorCaseDecisions(@Param("reqId") String reqId,@Param("sno") int sno);

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.reqid = :reqId and t1.regId <> t1.matchedRefId order by t1.sno")
    List<RegisterManualVerification> clusterOfRids(@Param("reqId") String reqId);

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.sno=(SELECT min(t1.sno) FROM RegisterManualVerification t1 " +
            "where ((t1.statusCode is null or t1.statusCode='0') and (t1.proStatus is null or t1.proStatus='0')) and (t1.op1userId<>:userid or t1.op1userId is null )" +
            " and (t1.regId <> t1.matchedRefId) and t1.priority= '1'  )")
    List<RegisterManualVerification> listOfRidsPriority(@Param("userid") String userid);

    @Query(value = "SELECT t1.reqid FROM RegisterManualVerification t1 where t1.sno=(SELECT min(t1.sno) FROM RegisterManualVerification t1 " +
            "where ((t1.statusCode is null or t1.statusCode='0') and (t1.proStatus is null or t1.proStatus='0')) and (t1.op1userId<>:userid or t1.op1userId is null )" +
            " and (t1.regId <> t1.matchedRefId) and t1.priority= '1')")
    String getRequestIdPriority(@Param("userid") String userid);

    @Query(value="select t1.reqid from RegisterManualVerification t1 where (t1.statusCode='1')" +
            " and (t1.userId<>:userid or t1.userId is null ) and (t1.proStatus is null or t1.proStatus='0') and ((t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='nohit') " +
            "OR (t1.op1verifyStatus='nohit' AND t1.op2verifyStatus='hit')) order by t1.createdDate asc")
    List<String> getReqIdForL2(@Param("userid") String userid,Pageable size);

    //      -----------------------------------------------------------------------------------------------------------------------

    @Query(value="select t1 from RegisterManualVerification t1 where (t1.statusCode='1')" +
            " and ((t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='nohit') " +
            "OR (t1.op1verifyStatus='nohit' AND t1.op2verifyStatus='hit')) order by t1.op2UpdatedDate desc")
    List<RegisterManualVerification> listOfRidsForL2(Pageable pageable);


    @Query(value="select t1 from RegisterManualVerification t1 where (t1.statusCode='1')" +
            " and ((t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='nohit') " +
            "OR (t1.op1verifyStatus='nohit' AND t1.op2verifyStatus='hit')) and (t1.op2UpdatedDate between :startDate and :endDate) and (t1.op1UpdBy =:operator1) and (t1.op2UpdBy =:operator2) order by t1.op2UpdatedDate desc")
    List<RegisterManualVerification> listOfRidsForVerifiedDateL2(Date startDate, Date endDate, String operator1, String operator2, Pageable pageable);

    @Query(value="select t1 from RegisterManualVerification t1 where (t1.statusCode='1')" +
            " and ((t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='nohit') " +
            "OR (t1.op1verifyStatus='nohit' AND t1.op2verifyStatus='hit')) and (t1.op2UpdatedDate between :startDate and :endDate) and (t1.op1UpdBy =:operator1) order by t1.op2UpdatedDate desc")
    List<RegisterManualVerification> listOfRidsForVerifiedDateL2Op1(Date startDate, Date endDate, String operator1, Pageable pageable);

    @Query(value="select t1 from RegisterManualVerification t1 where (t1.statusCode='1')" +
            " and ((t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='nohit') " +
            "OR (t1.op1verifyStatus='nohit' AND t1.op2verifyStatus='hit')) and (t1.op2UpdatedDate between :startDate and :endDate) and (t1.op2UpdBy =:operator2) order by t1.op2UpdatedDate desc")
    List<RegisterManualVerification> listOfRidsForVerifiedDateL2Op2(Date startDate, Date endDate, String operator2, Pageable pageable);


    @Query(value="select t1 from RegisterManualVerification t1 where (t1.statusCode='1')" +
            " and ((t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='nohit') " +
            "OR (t1.op1verifyStatus='nohit' AND t1.op2verifyStatus='hit')) and (t1.op2UpdatedDate between :startDate and :endDate) order by t1.op2UpdatedDate desc")
    List<RegisterManualVerification> listOfRidsForVerifiedDateL22(Date startDate, Date endDate, Pageable pageable);

    //      -----------------------------------------------------------------------------------------------------------------------

    @Query(value="select t1 from RegisterManualVerification t1 where (t1.statusCode='1')" +
            " and ((t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='nohit') " +
            "OR (t1.op1verifyStatus='nohit' AND t1.op2verifyStatus='hit')) and (t1.createdDate between :startDate and :endDate) and (t1.op1UpdBy =:operator1) and (t1.op2UpdBy =:operator2) order by t1.createdDate asc")
    List<RegisterManualVerification> listOfRidsForCreatedDateL2(Date startDate, Date endDate, String operator1, String operator2,  Pageable pageable);

    @Query(value="select t1 from RegisterManualVerification t1 where (t1.statusCode='1')" +
            " and ((t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='nohit') " +
            "OR (t1.op1verifyStatus='nohit' AND t1.op2verifyStatus='hit')) and (t1.createdDate between :startDate and :endDate) and (t1.op1UpdBy =:operator1) order by t1.createdDate asc")
    List<RegisterManualVerification> listOfRidsForCreatedDateL2Op1(Date startDate, Date endDate, String operator1, Pageable pageable);

    @Query(value="select t1 from RegisterManualVerification t1 where (t1.statusCode='1')" +
            " and ((t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='nohit') " +
            "OR (t1.op1verifyStatus='nohit' AND t1.op2verifyStatus='hit')) and (t1.createdDate between :startDate and :endDate) and (t1.op2UpdBy =:operator2) order by t1.createdDate asc")
    List<RegisterManualVerification> listOfRidsForCreatedDateL2Op2(Date startDate, Date endDate, String operator2, Pageable pageable);


    @Query(value="select t1 from RegisterManualVerification t1 where (t1.statusCode='1')" +
            " and ((t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='nohit') " +
            "OR (t1.op1verifyStatus='nohit' AND t1.op2verifyStatus='hit')) and (t1.createdDate between :startDate and :endDate) order by t1.createdDate asc")
    List<RegisterManualVerification> listOfRidsForCreatedDateL22(Date startDate, Date endDate, Pageable pageable);


    //      -----------------------------------------------------------------------------------------------------------------------


    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.caseEvaluationComplete = 1 and ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit')))  and (t1.createdDate between :startDate and :endDate) and (t1.op1UpdBy =:operator1) and (t1.op2UpdBy =:operator2) AND NOT EXISTS (SELECT 1 FROM UserCaseAssignment t2 WHERE t1.reqid = t2.requestId) order by COALESCE(t1.supervisorUpdatedDate, t1.op2UpdatedDate) asc ")
    List<RegisterManualVerification> listOfRidsForCreatedDateL3(Date startDate, Date endDate, String operator1, String operator2, Pageable pageable);

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.caseEvaluationComplete = 1 and ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit'))) and (t1.createdDate between :startDate and :endDate) AND NOT EXISTS (SELECT 1 FROM UserCaseAssignment t2 WHERE t1.reqid = t2.requestId) order by COALESCE(t1.supervisorUpdatedDate, t1.op2UpdatedDate) asc")
    List<RegisterManualVerification> listOfRidsForCreatedDateL33(Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.caseEvaluationComplete = 1 and ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit')))  and (t1.createdDate between :startDate and :endDate)  and (t1.op1UpdBy =:operator1) AND NOT EXISTS (SELECT 1 FROM UserCaseAssignment t2 WHERE t1.reqid = t2.requestId) order by COALESCE(t1.supervisorUpdatedDate, t1.op2UpdatedDate) asc")
    List<RegisterManualVerification> listOfRidsForCreatedDateL3Op1(Date startDate, Date endDate,String operator1, Pageable pageable);

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.caseEvaluationComplete = 1 and ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit')))  and (t1.createdDate between :startDate and :endDate)   and (t1.op2UpdBy =:operator2) AND NOT EXISTS (SELECT 1 FROM UserCaseAssignment t2 WHERE t1.reqid = t2.requestId) order by COALESCE(t1.supervisorUpdatedDate, t1.op2UpdatedDate) asc")
    List<RegisterManualVerification> listOfRidsForCreatedDateL3Op2(Date startDate, Date endDate,String operator2, Pageable pageable);

    //      -----------------------------------------------------------------------------------------------------------------------

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.caseEvaluationComplete = 1 and ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit')))  AND NOT EXISTS (SELECT 1 FROM UserCaseAssignment t2 WHERE t1.reqid = t2.requestId)  order by COALESCE(t1.supervisorUpdatedDate, t1.op2UpdatedDate) DESC")
    List<RegisterManualVerification> listOfRidsForL3(Pageable pageable);

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where  t1.caseEvaluationComplete = 1 and ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit')))   and ((CASE WHEN t1.supervisorUpdatedDate IS NOT NULL THEN t1.supervisorUpdatedDate ELSE t1.op2UpdatedDate END) between :startDate and :endDate) and (t1.op1UpdBy =:operator1) and (t1.op2UpdBy =:operator2) AND NOT EXISTS (SELECT 1 FROM UserCaseAssignment t2 WHERE t1.reqid = t2.requestId) order by COALESCE(t1.supervisorUpdatedDate, t1.op2UpdatedDate) DESC")
    List<RegisterManualVerification> listOfRidsForVerifiedDateL3(Date startDate, Date endDate, String operator1, String operator2, Pageable pageable);

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.caseEvaluationComplete = 1 and ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit')))  and ((CASE WHEN t1.supervisorUpdatedDate IS NOT NULL THEN t1.supervisorUpdatedDate ELSE t1.op2UpdatedDate END) between :startDate and :endDate) AND NOT EXISTS (SELECT 1 FROM UserCaseAssignment t2 WHERE t1.reqid = t2.requestId) order by COALESCE(t1.supervisorUpdatedDate, t1.op2UpdatedDate) DESC")
    List<RegisterManualVerification> listOfRidsForVerifiedDateL33(Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.caseEvaluationComplete = 1 and ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit')))  and ((CASE WHEN t1.supervisorUpdatedDate IS NOT NULL THEN t1.supervisorUpdatedDate ELSE t1.op2UpdatedDate END) between :startDate and :endDate)  and (t1.op1UpdBy =:operator1) AND NOT EXISTS (SELECT 1 FROM UserCaseAssignment t2 WHERE t1.reqid = t2.requestId) order by COALESCE(t1.supervisorUpdatedDate, t1.op2UpdatedDate) DESC")
    List<RegisterManualVerification> listOfRidsForVerifiedDateL3Op1(Date startDate, Date endDate,String operator1, Pageable pageable);

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.caseEvaluationComplete = 1 and ((t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit')))   and ((CASE WHEN t1.supervisorUpdatedDate IS NOT NULL THEN t1.supervisorUpdatedDate ELSE t1.op2UpdatedDate END) between :startDate and :endDate)   and (t1.op2UpdBy =:operator2) AND NOT EXISTS (SELECT 1 FROM UserCaseAssignment t2 WHERE t1.reqid = t2.requestId) order by COALESCE(t1.supervisorUpdatedDate, t1.op2UpdatedDate) DESC")
    List<RegisterManualVerification> listOfRidsForVerifiedDateL3Op2(Date startDate, Date endDate,String operator2, Pageable pageable);

    //      -----------------------------------------------------------------------------------------------------------------------


    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.reqid=:reqid and (t1.regId <> t1.matchedRefId)")
    List listForCandiat(String reqid);

    @Query(value = "SELECT t1.op1Comment FROM RegisterManualVerification t1 where t1.sno=:id")
    String getStatuscomment(@Param("id") int id);

    @Modifying
    @Query(value="update register_manual_verification  set status_code=:level, operator2_user_id=:user,operator2_comment=:comment, operator2_verify_status=:status,operator2_upd_by=:userid," +
            "operator2_upd_date= now() at time zone 'Asia/Manila' where sno=:id and req_id=:requestId",nativeQuery = true)
    int updateRIDstatus2(@Param("id") int id,@Param("status") String status, @Param("comment") String comment,@Param("user") String user,@Param("userid") String userid,@RequestParam("requestId") String requestId, @Param("level") String level);

    @Modifying
    @Query(value = "update register_manual_verification set status_code=:level,operator1_user_id=:user,operator1_comment=:comment,operator1_verify_status=:status,operator1_upd_by=:userid," +
            "operator1_upd_date=  now() at time zone 'Asia/Manila' where sno=:id and req_id=:requestId", nativeQuery = true)
    int updateRID(@Param("id") int id,@Param("status") String status, @Param("comment") String comment,@Param("user") String user,@Param("userid") String userid,@Param("requestId")String requestId,@Param("level") String level);

    @Modifying
    @Query(value = " update register_manual_verification  set status_code=:level, supervisor_comment=:comment,supervisor_verify_status=:status,supervisor_upd_by=:userName,user_id=:userid,case_evaluation_complete = 1," +
            "supervisor_upd_date=  now() at time zone 'Asia/Manila'  where sno=:id and req_id=:requestId ", nativeQuery = true)
    int  updateRIDTwo(@Param("id") int id, @Param("status") String status, @Param("comment") String comment, @Param("userid") String userid, @Param("userName") String userName, @Param("requestId") String requestId, @Param("level") String level);

    @Query(value = "SELECT t1.regId FROM RegisterManualVerification t1 where t1.sno=:id and t1.reqid=:requestId")
    String getRegId(@Param("id") int id, @Param("requestId") String requestId);

/*    @Query(value = "SELECT t1.reqid FROM RegisterManualVerification t1 where t1.createdDate < '2022-07-23T03:13:00.665Z' order by t1.createdDate  desc")
    RegisterManualVerification findFirst1ByCreatedByBefore(@Param("createdDate") String createdDate);

    List<RegisterManualVerification> findAllByCreatedByLessThan(String createdDate);*/

    RegisterManualVerification findFirst1BySnoLessThan(int sno);

    int deleteAllByReqid(String requestId);


     /*   @Query(value="update reg_manual_verification  set status_code=?1,status_comment_three=?2,verify_status_three=?3,upd_by_three=?4,upd_dt_three=now() where sno=?5",nativeQuery = true)

    @Transactional
    @Modifying
    @Query(value = "update RegManualVerification t1 set t1.statusCode=:level,t1.statusCommentThree=:comment,t1.verifyStatusThree=:status,t1.updatedByThree=:userid," +"t1.updatedDateThree=now() where t1.sno=:id")
    public int  updateRIDThree(@Param("id") int id,@Param("status") String status, @Param("comment") String comment,@Param("userid") String userid,@Param("level") String level);

    @Query(value = "SELECT c.fileDatas FROM RegManualVerification c  where c.regId=:regid and c.matchedRefId=:mid")
    public String fileDataCandidate(@Param("regid") String regid,@Param("mid") String mid);

    @Query(value = "SELECT c.probeString FROM RegManualVerification c  where c.regId=:regid and c.matchedRefId=:mid")
    public String fileDataProb(@Param("regid") String regid,@Param("mid") String mid);*/

    RegisterManualVerification findBySno(int sno);

    @Modifying
    @Query(value = "update RegisterManualVerification t1  set t1.finindi=null where t1.sno=:sno")
    int updateFinalIndi(int sno);
}
