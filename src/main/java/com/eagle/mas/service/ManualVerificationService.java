//package com.eagle.mas.service;
//
//import com.eagle.mas.model.RegManualVerification;
//import com.eagle.mas.model.TblAssigned;
//import com.eagle.mas.repository.AssignmentRepository;
//import com.eagle.mas.repository.RegManualVerificationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigInteger;
//import java.util.List;
//import java.util.Optional;
//@Transactional
//@Service
//public class ManualVerificationService implements RegManualVerificationRepository {
//
//
//	@Autowired
//	RegManualVerificationRepository repo;
//
//
//	@Override
//	public List listOfRids() {
//		return repo.listOfRids();
//	}
//
//	@Override
//	public List listOfRidsForL2() {
//		return repo.listOfRidsForL2();
//	}
//
//	@Query("select t1 from RegManualVerification t1 where (t1.statusCode='2')" +
//			" and (t1.verifyStatus='hit' or t1.verifyStatusTwo ='nohit') " +
//			"and (t1.verifyStatus='nohit' or t1.verifyStatusTwo='hit')")
//	@Override
//	public List listOfRidsForL3(){
//		return repo.listOfRidsForL3();
//	}
//
//
//
//	@Override
//	public int updateRID(int id,String status, String comment,String userid,String level) {
//		System.out.println("id"+id);
//		System.out.println("status"+status);
//		System.out.println("comment"+comment);
//		System.out.println("userid"+userid);
//		System.out.println("level"+level);
//		return repo.updateRID(id,status,comment,userid,level);
//	}
//	@Override
//	public int updateRIDTwo(int id,String status, String comment,String userid,String level) {
//		System.out.println("id"+id);
//		System.out.println("status"+status);
//		System.out.println("comment"+comment);
//		System.out.println("userid"+userid);
//		System.out.println("level"+level);
//		return repo.updateRIDTwo(id,status,comment,userid,level);
//	}
//	@Override
//	public int updateRIDThree(int id,String status, String comment,String userid,String level) {
//		System.out.println("id"+id);
//		System.out.println("status"+status);
//		System.out.println("comment"+comment);
//		System.out.println("userid"+userid);
//		System.out.println("level"+level);
//		return repo.updateRIDThree(id,status,comment,userid,level);
//	}
//	@Override
//	public <S extends RegManualVerification> S save(S entity) {
//		return null;
//	}
//
//	@Override
//	public <S extends RegManualVerification> Iterable<S> saveAll(Iterable<S> entities) {
//		return null;
//	}
//
//	@Override
//	public Optional<RegManualVerification> findById(BigInteger bigInteger) {
//		return Optional.empty();
//	}
//
//	@Override
//	public boolean existsById(BigInteger bigInteger) {
//		return false;
//	}
//
//	@Override
//	public Iterable<RegManualVerification> findAll() {
//		return null;
//	}
//
//	@Override
//	public Iterable<RegManualVerification> findAllById(Iterable<BigInteger> bigIntegers) {
//		return null;
//	}
//
//	@Override
//	public long count() {
//		return 0;
//	}
//
//	@Override
//	public void deleteById(BigInteger bigInteger) {
//
//	}
//
//	@Override
//	public void delete(RegManualVerification entity) {
//
//	}
//
//	@Override
//	public void deleteAll(Iterable<? extends RegManualVerification> entities) {
//
//	}
//
//	@Override
//	public void deleteAll() {
//
//	}
//	@Override
//	public String fileDataProb(String regid, String mid) {
//		return repo.fileDataProb(regid,mid);
//	}
//	@Override
//	public String fileDataCandidate(String regid, String mid) {
//		return repo.fileDataCandidate(regid,mid);
//	}
//}
package com.eagle.mas.service;


import com.eagle.mas.model.RegisterManualVerification;
import com.eagle.mas.repository.RegManualVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Transactional
@Service
public class ManualVerificationService {


	@Autowired
	RegManualVerificationRepository repo;


	public int countAllByRegId(String regid) {
		return repo.countAllByRegId(regid);
	}

	//dashboard admin

	public int numberofHits(){
		 int count=repo.numberofHits();
		return count;
	}

	public int numberofNoHits(){
		int count=repo.numberofNoHits();
		return count;
	}

	public int numberofUnverifiedRecords(){
		int count=repo.numberofUnverifiedRecords();
		return count;
	}


	public int hitsThisMonth(int year,int month){
		int count=repo.hitsThisMonth(year,month);
		return count;
	}
//	@Override
//	public int toalRecordsPerMonth(int year,int month){
//		int count=repo.toalRecordsPerMonth(year,month);
//		return count;
//	}


	public int toalRecords(){
		int count=repo.toalRecords();
		return count;
	}


public int totalResponseCases(String regid){
	return repo.totalResponseCases(regid);
}

	public int responseCasesHit(String regid){
		return repo.responseCasesHit(regid);
	}

	public int responseCasesNohit(String regid){
		return repo.responseCasesNohit(regid);
	}

	public String proStatus(String regid ,String mid,String requestId ) {

		return repo.proStatus(regid,mid,requestId);
	}

	public int modify_process_status(int regid) {

		return repo.modify_process_status(regid);
	}


    public int modifyProcessStatus(int sno){
        return repo.modifyProcessStatus(sno);
    }

	public int operatorVerifiedNohit(int sno){

		return repo.operatorVerifiedNohit(sno);
	}


	public int operatorVerifiedHit(int sno){

		return repo.operatorVerifiedHit(sno);
	}


	public int operatorUpdateNohit(int sno){

		return repo.operatorUpdateNohit(sno);
	}


	public int operatorUpdateHit(int sno){

		return repo.operatorUpdateHit(sno);
	}


	public int supervisorVerifiedNohit(int sno){

		return repo.supervisorVerifiedNohit(sno);
	}


	public int supervisorVerifiedHit(int sno){

		return repo.supervisorVerifiedHit(sno);
	}


	public String getReqId(int sno){

		return repo.getReqId(sno);
	}


	public int getReqIdCount(String ReqId){

		return repo.getReqIdCount(ReqId);
	}


	public int getFinIndicate(String ReqId){

		return repo.getFinIndicate(ReqId);
	}

	public int getReturnVal(String reqid){
		return repo.getReturnVal(reqid);
	}


	public int getCountforResponse(String reqid){
		return repo.getCountforResponse(reqid);
	}


	public synchronized List<RegisterManualVerification> listOfRids(String userid) {
		List<RegisterManualVerification> list = repo.clusterOfRids(repo.getRequestIdOperator(userid));
		list.replaceAll(ad-> {ad.setProStatus("1"); return ad;});
		repo.saveAll(list);
		return list;
//		return repo.listOfRids(userid);
	}

//	@Override
//	public List listOfRids(String userid, Pageable page) {
//		System.out.println(userid);
//
//		return repo.listOfRids(userid,page);
//	}


	public synchronized List listOfRidsPriority(String userid) {
		System.out.println(userid);

		return repo.listOfRidsPriority(userid);
	}





	public List listOfRidsForL2() {
		return repo.listOfRidsForL2();
	}


	public List listOfRidsForL3(){
		return repo.listOfRidsForL3();
	}

	public List listForCandiat(String reqid){
		return repo.listForCandiat(reqid);
	}


	public String getStatuscomment (int id){
		System.out.println("id"+id);
		return repo.getStatuscomment(id);
	}


	public int updateRID(int id,String status, String comment,String user,String userid,String requestId,String level) {
		System.out.println("id"+id);
		System.out.println("status"+status);
		System.out.println("comment"+comment);
		System.out.println("userid"+userid);
		System.out.println("level"+level);

		return repo.updateRID(id,status,comment,user,userid,requestId,level);
	}


	public int updateRIDstatus2(int id,String status, String comment,String user,String userid,String requestId,String level) {
		System.out.println("id"+id);
		System.out.println("status"+status);
		System.out.println("comment"+comment);
		System.out.println("userid"+userid);
		System.out.println("level"+level);
		return repo.updateRIDstatus2(id,status,comment,user,userid,requestId,level);
	}
//@Override
//public int updoper1comm(int id,String status, String comment,String userid,String level) {
//	System.out.println("id"+id);
//	System.out.println("status"+status);
//	System.out.println("comment"+comment);
//	System.out.println("userid"+userid);
//	System.out.println("level"+level);
//	return repo.updoper1comm(id,status,comment,userid,level);
//}

	public int updateRIDTwo(int id,String status, String comment,String userid,String userName,String requestId,String level) {
		return repo.updateRIDTwo(id,status,comment,userid,userName,requestId,level);
	}


	public String getRegId(int id, String requestId) {
		return repo.getRegId(id,requestId);
	}

//	@Override
//	public int updateRIDThree(int id,String status, String comment,String userid,String level) {
//		return repo.updateRIDThree(id,status,comment,userid,level);
//	}




	public RegisterManualVerification findFirst1BySnoLessThan(int sno) {
		return repo.findFirst1BySnoLessThan(sno);
	}

	public int deleteAllByReqid(String requestId) {
		return repo.deleteAllByReqid(requestId);
	}


}
