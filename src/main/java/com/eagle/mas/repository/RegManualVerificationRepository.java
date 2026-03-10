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


    @Query(value =
            "WITH picked AS ( " +
                    "   SELECT req_id " +
                    "   FROM register_manual_verification " +
                    "   WHERE (status_code IS NULL OR status_code = '0') " +
                    "     AND (process_code IS NULL OR process_code = '0') " +
                    "     AND (operator1_user_id IS NULL OR operator2_user_id IS NULL) " +
                    "     AND (operator1_user_id <> :userid OR operator1_user_id IS NULL) " +
                    "     AND (operator2_user_id <> :userid OR operator2_user_id IS NULL) " +
                    "     AND (reg_id <> matched_ref_id) " +
                    "   ORDER BY sno " +
                    "   LIMIT 1 " +
                    "   FOR UPDATE SKIP LOCKED " +
                    ") " +
                    "UPDATE register_manual_verification " +
                    "SET process_code = '1' " +
                    "WHERE req_id = (SELECT req_id FROM picked) AND (reg_id <> matched_ref_id) " +
                    "RETURNING *",
            nativeQuery = true)
    List<RegisterManualVerification> getRequestIdOperator(@Param("userid") String userid);



    @Modifying @Query("UPDATE RegisterManualVerification r SET r.op1verifyStatus = null, r.op1updDate = null, r.op1UpdBy = null, r.op1Comment = null, r.op1userId = null, r.proStatus = '0' WHERE r.reqid = :reqId AND r.sno = :sno")
    void resetOp1CaseDecisions(@Param("reqId") String reqId, @Param("sno") int sno);

    @Modifying @Query("UPDATE RegisterManualVerification r SET r.op2verifyStatus = null, r.op2UpdatedDate = null, r.op2UpdBy = null, r.op2Comment = null, r.op2userId = null, r.proStatus = '0', r.statusCode = '0', r.finindi = null, r.caseEvaluationComplete = 0 WHERE r.reqid = :reqId AND r.sno = :sno")
    void resetOp2CaseDecisions(@Param("reqId") String reqId, @Param("sno") int sno);

    @Modifying @Query("UPDATE RegisterManualVerification r SET r.supervisorVerifyStatus = null, r.supervisorUpdatedDate = null, r.supervisorUpdBy = null, r.supervisorComment = null, r.userId = null, r.proStatus = '0', r.statusCode = '1', r.finindi = null, r.caseEvaluationComplete = 0 WHERE r.reqid = :reqId AND r.sno = :sno")
    void resetSupervisorCaseDecisions(@Param("reqId") String reqId, @Param("sno") int sno);


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

    @Query(value = "SELECT t1.reqid FROM RegisterManualVerification t1 where t1.sno=(SELECT min(t1.sno) FROM RegisterManualVerification t1 " +
            "where ((t1.statusCode is null or t1.statusCode='0') and (t1.proStatus is null or t1.proStatus='0')) and (t1.op1userId<>:userid or t1.op1userId is null )" +
            " and (t1.regId <> t1.matchedRefId) and t1.priority='2')")
    String getRequestIdHigherPriority(@Param("userid") String userid);


    @Query(value =
            "WITH picked AS ( " +
                    "   SELECT req_id " +
                    "   FROM register_manual_verification " +
                    "   WHERE (status_code IS NULL OR status_code = '0') " +
                    "     AND (process_code IS NULL OR process_code = '0') " +
                    "     AND (operator1_user_id IS NULL OR operator2_user_id IS NULL) " +
                    "     AND (operator1_user_id <> :userid OR operator1_user_id IS NULL) " +
                    "     AND (operator2_user_id <> :userid OR operator2_user_id IS NULL) " +
                    "     AND (reg_id <> matched_ref_id) " +
                    "     AND priority = :priority " +
                    "   ORDER BY cr_date " +
                    "   LIMIT 1 " +
                    "   FOR UPDATE SKIP LOCKED " +
                    ") " +
                    "UPDATE register_manual_verification " +
                    "SET process_code = '1' " +
                    "WHERE req_id = (SELECT req_id FROM picked) AND (reg_id <> matched_ref_id)" +
                    "RETURNING *",
            nativeQuery = true)
    List<RegisterManualVerification> getRequestIdHigherPriority1(@Param("userid") String userid, @Param("priority") String priority);


    @Query(value =
            "WITH picked AS ( " +
                    "   SELECT req_id " +
                    "   FROM register_manual_verification " +
                    "   WHERE (status_code IS NULL OR status_code = '0') " +
                    "     AND (process_code IS NULL OR process_code = '0') " +
                    "     AND (operator1_user_id <> :userid OR operator1_user_id IS NULL) " +
                    "     AND (reg_id <> matched_ref_id) " +
                    "     AND reg_type = :priority " +
                    "   ORDER BY cr_date " +
                    "   LIMIT 1 " +
                    "   FOR UPDATE SKIP LOCKED " +
                    ") " +
                    "UPDATE register_manual_verification " +
                    "SET process_code = '1' " +
                    "WHERE req_id = (SELECT req_id FROM picked) AND (reg_id <> matched_ref_id)" +
                    "RETURNING *",
            nativeQuery = true)
    List<RegisterManualVerification> getRequestIdHigherPriorityUpdate(@Param("userid") String userid, @Param("priority") String priority);

    @Query(value =
            "WITH picked AS ( " +
                    "   SELECT req_id " +
                    "   FROM register_manual_verification " +
                    "   WHERE status_code = '1' " +
                    "     AND (user_id <> :userid OR user_id IS NULL) " +
                    "     AND (process_code IS NULL OR process_code = '0') " +
                    "     AND ( (operator1_verify_status = 'hit'  AND operator2_verify_status = 'nohit') " +
                    "        OR (operator1_verify_status = 'nohit' AND operator2_verify_status = 'hit') ) " +
                    "   ORDER BY cr_date " +
                    "   LIMIT 1 " +
                    "   FOR UPDATE SKIP LOCKED " +
                    ") " +
                    "UPDATE register_manual_verification " +
                    "SET process_code = '1' " +
                    "WHERE req_id = (SELECT req_id FROM picked) AND (reg_id <> matched_ref_id)" +
                    "RETURNING *",
            nativeQuery = true)
    List<RegisterManualVerification> getReqIdForL2(@Param("userid") String userid);

    @Query(value =
            "WITH picked AS ( " +
                    "   SELECT req_id " +
                    "   FROM register_manual_verification " +
                    "   WHERE status_code = '1' " +
                    "     AND (user_id <> :userid OR user_id IS NULL) " +
                    "     AND (process_code IS NULL OR process_code = '0') " +
                    "     AND ( (operator1_verify_status = 'hit'  AND operator2_verify_status = 'nohit') " +
                    "        OR (operator1_verify_status = 'nohit' AND operator2_verify_status = 'hit') ) " +
                    "     AND priority = :priority " +
                    "   ORDER BY cr_date " +
                    "   LIMIT 1 " +
                    "   FOR UPDATE SKIP LOCKED " +
                    ") " +
                    "UPDATE register_manual_verification " +
                    "SET process_code = '1' " +
                    "WHERE req_id = (SELECT req_id FROM picked) AND (reg_id <> matched_ref_id)" +
                    "RETURNING *",
            nativeQuery = true)
    List<RegisterManualVerification> getRequestIdHigherPriority2(@Param("userid") String userid, @Param("priority") String priority);


    @Query(value =
            "WITH picked AS ( " +
                    "   SELECT req_id " +
                    "   FROM register_manual_verification " +
                    "   WHERE status_code = '1' " +
                    "     AND (user_id <> :userid OR user_id IS NULL) " +
                    "     AND (process_code IS NULL OR process_code = '0') " +
                    "     AND ( (operator1_verify_status = 'hit'  AND operator2_verify_status = 'nohit') " +
                    "        OR (operator1_verify_status = 'nohit' AND operator2_verify_status = 'hit') ) " +
                    "     AND reg_type = :priority " +
                    "   ORDER BY cr_date " +
                    "   LIMIT 1 " +
                    "   FOR UPDATE SKIP LOCKED " +
                    ") " +
                    "UPDATE register_manual_verification " +
                    "SET process_code = '1' " +
                    "WHERE req_id = (SELECT req_id FROM picked) AND (reg_id <> matched_ref_id)" +
                    "RETURNING *",
            nativeQuery = true)
    List<RegisterManualVerification> getRequestIdHigherPriorityUpdate2(@Param("userid") String userid, @Param("priority") String priority);

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

    RegisterManualVerification findFirst1BySnoLessThan(int sno);

    int deleteAllByReqid(String requestId);


    RegisterManualVerification findBySno(int sno);

    @Modifying
    @Query(value = "update RegisterManualVerification t1  set t1.finindi=null where t1.sno=:sno")
    int updateFinalIndi(int sno);
}
