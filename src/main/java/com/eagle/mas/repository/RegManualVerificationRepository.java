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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface RegManualVerificationRepository extends CrudRepository<RegisterManualVerification, BigInteger> {


    int countAllByRegId(String regid);

//dash board admin
    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.op1verifyStatus='hit' and t1.op2verifyStatus='hit') or t1.supervisorVerifyStatus='hit'  ")
    public int numberofHits();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.op1verifyStatus='nohit' and t1.op2verifyStatus='nohit') or t1.supervisorVerifyStatus='nohit'  ")
    public int numberofNoHits();

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where (t1.op1verifyStatus is null or t1.op2verifyStatus is null) and (t1.regId <> t1.matchedRefId)  ")
    public int numberofUnverifiedRecords();

    @Query(value = "SELECT count(t1) FROM  RegisterManualVerification t1  WHERE ((t1.op1verifyStatus='hit' and t1.op2verifyStatus='hit') or t1.supervisorVerifyStatus='hit') and (MONTH(t1.op1updDate)=:month AND YEAR(t1.op1updDate)=:year)")
    public int hitsThisMonth(@Param("year") int year,@Param("month") int month);

//    @Query(value = "SELECT count(t1) FROM  RegisterManualVerification t1  WHERE  (MONTH(t1.op1updDate)=:month AND YEAR(t1.op1updDate)=:year)")
//    public int toalRecordsPerMonth(@Param("year") int year,@Param("month") int month);
    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1  where  (t1.regId <> t1.matchedRefId)")
    public int toalRecords();

//response json query
    @Query("SELECT count(t1) FROM RegisterManualVerification t1 where t1.regId=:regid")
    public int totalResponseCases(@Param("regid") String regid);

    @Query("SELECT count(t1) FROM RegisterManualVerification t1 where t1.regId=:regid and ((t1.op1verifyStatus='hit' and t1.op2verifyStatus='hit') or t1.supervisorVerifyStatus='hit') ")
    public int responseCasesHit(@Param("regid") String regid);

    @Query("SELECT count(t1) FROM RegisterManualVerification t1 where t1.regId=:regid and ((t1.op1verifyStatus='nohit' and t1.op2verifyStatus='nohit') or t1.supervisorVerifyStatus='nohit') ")
    public int responseCasesNohit(@Param("regid") String regid);

    @Query(value = "SELECT t1.proStatus FROM RegisterManualVerification t1 where t1.regId=:regid and t1.matchedRefId=:mid and t1.reqid=:requestId")
    public String proStatus(@Param("regid") String regid,@Param("mid") String mid,@Param("requestId") String requestId);

    //@Transactional
    @Modifying
    @Query(value = "update RegisterManualVerification t1  set t1.proStatus='1' where t1.sno=:sno")
    public int modify_process_status(@Param("sno") int sno);

    @Modifying
    @Query(value = "update RegisterManualVerification t1  set t1.proStatus='0' where t1.sno=:sno")
    public int modifyProcessStatus(@Param("sno") int sno);

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where t1.sno=:sno and t1.op1verifyStatus='nohit' and t1.op2verifyStatus='nohit' ")
    public int operatorVerifiedNohit(@Param("sno") int sno);

    @Query(value = "SELECT count(t1) FROM RegisterManualVerification t1 where t1.sno=:sno and t1.op1verifyStatus='hit' and t1.op2verifyStatus='hit' ")
    public int operatorVerifiedHit(@Param("sno") int sno);

    @Modifying
    @Query(value = "update RegisterManualVerification t1  set t1.finindi='UIN' where t1.sno=:sno")
    public int operatorUpdateNohit(@Param("sno") int sno);


    @Modifying
    @Query(value = "update RegisterManualVerification t1  set t1.finindi='DUP' where t1.sno=:sno")
    public int operatorUpdateHit(@Param("sno") int sno);


    @Query(value = "SELECT count(t1)  FROM RegisterManualVerification t1 where t1.sno=:sno and t1.supervisorVerifyStatus='nohit' ")
    public int supervisorVerifiedNohit(@Param("sno") int sno);

    @Query(value = "SELECT count(t1)  FROM RegisterManualVerification t1 where t1.sno=:sno and t1.supervisorVerifyStatus='hit' ")
    public int supervisorVerifiedHit(@Param("sno") int sno);

    @Query(value = "SELECT t1.reqid  FROM RegisterManualVerification t1 where t1.sno=:sno ")
    public String getReqId(@Param("sno") int sno);


    @Query(value = "SELECT count(t1)  FROM RegisterManualVerification t1 where t1.reqid=:ReqId ")
    public int getReqIdCount(@Param("ReqId") String ReqId);


    @Query(value = "SELECT count(t1)  FROM RegisterManualVerification t1 where t1.reqid=:ReqId and (t1.finindi is not null) ")
    public int getFinIndicate(@Param("ReqId") String ReqId);

    @Query(value = "SELECT count(t1)  FROM RegisterManualVerification t1 where t1.reqid=:reqid and t1.finindi='DUP' ")
    public int getReturnVal(@Param("reqid") String reqid);


    @Query(value = "SELECT count(t1)  FROM RegisterManualVerification t1 where t1.reqid=:reqid and t1.finindi='UIN' ")
    public int getCountforResponse(@Param("reqid") String reqid);



//    @Modifying
//    @Query(value = "update RegManualVerification t1 set t1.proStatus='0' where (t1.verifyStatus='hit' AND t1.verifyStatusTwo ='nohit')")    ((t1.updatedBy<>:userid) OR ())


    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.sno=(SELECT min(t1.sno) FROM RegisterManualVerification t1 where ((t1.statusCode is null or t1.statusCode='0') and (t1.proStatus is null or t1.proStatus='0')) and (t1.op1userId<>:userid or t1.op1userId is null ) and (t1.regId <> t1.matchedRefId)  )")
    List listOfRids(@Param("userid") String userid);



//@Query(value = "SELECT t1 FROM RegisterManualVerification t1 where ((t1.statusCode is null or t1.statusCode='0') and (t1.proStatus is null or t1.proStatus='0')) and (t1.op1userId<>:userid or t1.op1userId is null ) and (t1.regId <> t1.matchedRefId) order by t1.createdDate asc ")
//List listOfRids(@Param("userid") String userid, Pageable page);

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.sno=(SELECT min(t1.sno) FROM RegisterManualVerification t1 " +
            "where ((t1.statusCode is null or t1.statusCode='0') and (t1.proStatus is null or t1.proStatus='0')) and (t1.op1userId<>:userid or t1.op1userId is null )" +
            " and (t1.regId <> t1.matchedRefId) and t1.priority= '1'  )")
    List listOfRidsPriority(@Param("userid") String userid);

//    @Query(value = "SELECT t1 FROM RegManualVerification t1 where t1.statusCode='1'")
@Query(value="select t1 from RegisterManualVerification t1 where (t1.statusCode='1')" +
        " and ((t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='nohit') " +
        "OR (t1.op1verifyStatus='nohit' AND t1.op2verifyStatus='hit')) order by t1.createdDate asc")
List listOfRidsForL2();

    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where (t1.statusCode='2' and t1.supervisorVerifyStatus = 'hit') or (t1.statusCode='1' and (t1.op1verifyStatus='hit' AND t1.op2verifyStatus ='hit')) order by t1.createdDate asc")
    List listOfRidsForL3();
    @Query(value = "SELECT t1 FROM RegisterManualVerification t1 where t1.reqid=:reqid and (t1.regId <> t1.matchedRefId)")
    List listForCandiat(String reqid);

    @Query(value = "SELECT t1.op1Comment FROM RegisterManualVerification t1 where t1.sno=:id")
    String getStatuscomment(@Param("id") int id);

    @Modifying
    @Query(value="update register_manual_verification  set status_code=:level, operator2_user_id=:user,operator2_comment=:comment, operator2_verify_status=:status,operator2_upd_by=:userid," +
            "operator2_upd_date= now() at time zone 'Asia/Manila' where sno=:id and req_id=:requestId",nativeQuery = true)
    public int updateRIDstatus2(@Param("id") int id,@Param("status") String status, @Param("comment") String comment,@Param("user") String user,@Param("userid") String userid,@RequestParam("requestId") String requestId, @Param("level") String level);

    @Modifying
    @Query(value = "update register_manual_verification set status_code=:level,operator1_user_id=:user,operator1_comment=:comment,operator1_verify_status=:status,operator1_upd_by=:userid," +
            "operator1_upd_date=  now() at time zone 'Asia/Manila' where sno=:id and req_id=:requestId", nativeQuery = true)
    public int  updateRID(@Param("id") int id,@Param("status") String status, @Param("comment") String comment,@Param("user") String user,@Param("userid") String userid,@Param("requestId")String requestId,@Param("level") String level);
//@Modifying
//@Query(value = "update RegManualVerification t1 set t1.statusCode=:level,t1.oper1Comm=:comment,t1.verifyStatus=:status,t1.updatedBy=:userid," +
//        "t1.updatedDate=now() where t1.sno=:id")
//public int  updoper1comm(@Param("id") int id,@Param("status") String status, @Param("oper1Comm") String comment,@Param("userid") String userid,@Param("level") String level);

  //@Modifying
//@Query(value = "update RegManualVerification t1 set t1.statusCode=:level,t1.oper2Comm=:comment,t1.verifyStatus=:status,t1.updatedBy=:userid," +
//        "t1.updatedDate=now() where t1.sno=:id")
//public int  updoper1comm(@Param("id") int id,@Param("status") String status, @Param("oper2Comm") String comment,@Param("userid") String userid,@Param("level") String level);

  @Modifying
    @Query(value = " update register_manual_verification  set status_code=:level, supervisor_comment=:comment,supervisor_verify_status=:status,supervisor_upd_by=:userName,user_id=:userid," +
            "supervisor_upd_date=  now() at time zone 'Asia/Manila'  where sno=:id and req_id=:requestId ", nativeQuery = true)
  int  updateRIDTwo(@Param("id") int id, @Param("status") String status, @Param("comment") String comment, @Param("userid") String userid, @Param("userName") String userName, @Param("requestId") String requestId, @Param("level") String level);

    @Query(value = "SELECT t1.regId FROM RegisterManualVerification t1 where t1.sno=:id and t1.reqid=:requestId")
    String getRegId(@Param("id") int id, @Param("requestId") String requestId);

   //@Query(value = "SELECT t1.reqid FROM RegisterManualVerification t1 where t1.createdDate < '2022-07-23T03:13:00.665Z' order by t1.createdDate  desc")
   //RegisterManualVerification findFirst1ByCreatedByBefore(@Param("createdDate") String createdDate);

    //List<RegisterManualVerification> findAllByCreatedByLessThan(String createdDate);

    RegisterManualVerification findFirst1BySnoLessThan(int sno);

    int deleteAllByReqid(String requestId);


    //    @Query(value="update reg_manual_verification  set status_code=?1,status_comment_three=?2,verify_status_three=?3,upd_by_three=?4,upd_dt_three=now() where sno=?5",nativeQuery = true)

//    @Transactional
//    @Modifying
//    @Query(value = "update RegManualVerification t1 set t1.statusCode=:level,t1.statusCommentThree=:comment,t1.verifyStatusThree=:status,t1.updatedByThree=:userid," +"t1.updatedDateThree=now() where t1.sno=:id")
//    public int  updateRIDThree(@Param("id") int id,@Param("status") String status, @Param("comment") String comment,@Param("userid") String userid,@Param("level") String level);

//    @Query(value = "SELECT c.fileDatas FROM RegManualVerification c  where c.regId=:regid and c.matchedRefId=:mid")
//    public String fileDataCandidate(@Param("regid") String regid,@Param("mid") String mid);
//
//    @Query(value = "SELECT c.probeString FROM RegManualVerification c  where c.regId=:regid and c.matchedRefId=:mid")
//    public String fileDataProb(@Param("regid") String regid,@Param("mid") String mid);


}
