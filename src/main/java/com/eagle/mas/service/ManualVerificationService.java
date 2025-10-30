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


import com.eagle.mas.config.ConstantValue;
import com.eagle.mas.idrepo.repo.UinRepo;
import com.eagle.mas.model.RegisterManualVerification;
import com.eagle.mas.model.UserCaseAssignment;
import com.eagle.mas.repository.RegManualVerificationRepository;
import com.eagle.mas.repository.UserCaseAssignmentRepo;
import com.eagle.mas.service.impl.CredentialAPI;
//import com.eagle.mas.service.impl.TokenGenerator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class ManualVerificationService {


	@Autowired
	RegManualVerificationRepository repo;

	@Autowired
	UserCaseAssignmentRepo caseRepo;

	@Autowired
	CredentialAPI api;

//	@Autowired
//	TokenGenerator tokenGenerator;

	@Autowired
	UinRepo uinRepo;

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
		List<RegisterManualVerification> list = new ArrayList<>();
		List<String> reqId = repo.getRequestIdOperator(userid,PageRequest.of(0,1));
		if(!reqId.isEmpty()) {
			list = repo.clusterOfRids(reqId.get(0));
		}
		if(!list.isEmpty()){
			list.replaceAll(ad-> {ad.setProStatus("1"); return ad;});
			repo.saveAll(list);
			repo.flush();
			setCaseForUser(list.get(0).getReqid(),userid);
		}
		return list;
//		return repo.listOfRids(userid);
	}

	public void resetProcessStatus(String reqId){
		List<RegisterManualVerification> list = repo.clusterOfRids(reqId);
		if(!list.isEmpty()){
			System.out.println("LIST SIZE : "+ list.size());
			System.out.println("Set Process code equal to 0");
			list.replaceAll(ad-> {ad.setProStatus("0"); return ad;});
			repo.saveAll(list);
			repo.flush();
		}
	}

	public void setCaseForUser(String reqId,String userId){
		UserCaseAssignment caseAssignment = new UserCaseAssignment();
		caseAssignment.setRequestId(reqId);
		caseAssignment.setUserId(userId);
		caseAssignment.setPickupDtimes(LocalDateTime.now(ZoneId.of("UTC")));
		caseRepo.saveAndFlush(caseAssignment);
	}

	@Transactional
	public void removeProcessedCaseForUser(String userId){
		System.out.println("Remove Case for user");
		caseRepo.deleteById(userId);
		caseRepo.flush();

	}

	public  List<RegisterManualVerification> retreiveCaseForUser(String reqId){
		System.out.println("retrieve case for user : ");
		return repo.clusterOfRids(reqId);
	}


	public synchronized List<RegisterManualVerification>  listOfRidsPriority(String userid) {
		List<RegisterManualVerification> list = repo.clusterOfRids(repo.getRequestIdPriority(userid));

		if(!list.isEmpty()){
			list.replaceAll(ad-> {ad.setProStatus("1"); return ad;});
			repo.saveAll(list);
			repo.flush();
			setCaseForUser(list.get(0).getReqid(),userid);
		}
		return list;
	}

	public synchronized List<RegisterManualVerification>  listOfRidsHigherPriority(String userid) {
		List<RegisterManualVerification> list = repo.clusterOfRids(repo.getRequestIdHigherPriority(userid));

		if(!list.isEmpty()){
			list.replaceAll(ad-> {ad.setProStatus("1"); return ad;});
			repo.saveAll(list);
			repo.flush();
			setCaseForUser(list.get(0).getReqid(),userid);
		}
		return list;
	}

	public synchronized List<RegisterManualVerification> listOfRidsHigherPriority1(String userid, String priority) {
		List<RegisterManualVerification> list = repo.clusterOfRids(repo.getRequestIdHigherPriority1(userid, priority));

		if(!list.isEmpty()){
			list.replaceAll(ad-> {ad.setProStatus("1"); return ad;});
			repo.saveAll(list);
			repo.flush();
			setCaseForUser(list.get(0).getReqid(),userid);
		}
		return list;
	}

	@Transactional()
	public synchronized UserCaseAssignment userCaseDetails(String userId){
		if(caseRepo.existsById(userId)){
			System.out.println("usercaseassign exist true");
			return	caseRepo.findByUserId(userId);
		}else {
			System.out.println("usercaseassign exist false");
			return null;
		}
	}

	@Scheduled(initialDelay = 10000, fixedDelay = 60000)
	public void checkTimelpseAndUnassign(){
		List<UserCaseAssignment> userCaseAssignment = caseRepo.findAll();
		List<UserCaseAssignment> result = userCaseAssignment.stream().filter(e->
				LocalDateTime.now(ZoneId.of("UTC")).isAfter(e.getPickupDtimes().plusHours(ConstantValue.elapsedHours))
		).collect(Collectors.toList());
		if(result.size()>0) {
			result.forEach(e -> {
				List<RegisterManualVerification> cases = repo.clusterOfRids(e.getRequestId());
				List<RegisterManualVerification> finalList = cases.stream().filter(t -> {
					if (t.getOp1userId() != null && t.getOp1userId().equals(e.getUserId())) {
						resetOp1Decisions(t);
					} else if (t.getOp2userId() != null && t.getOp2userId().equals(e.getUserId())) {
						resetOp2Decisions(t);
					} else if (t.getUserId() != null && t.getUserId().equals(e.getUserId())) {
						resetSupervisorDecisions(t);
					}
					return false;
				}).collect(Collectors.toList());
				System.out.println("print final list : " + finalList.size());
			});


			result.forEach(e -> {
//				List<RegisterManualVerification> cases = repo.clusterOfRids(e.getRequestId());
//				cases.replaceAll(ad -> {
//					ad.setProStatus("0");
//					return ad;
//				});
//				repo.saveAll(cases);
				removeProcessedCaseForUser(e.getUserId());
//				caseRepo.deleteById(e.getUserId());
			});
		}

	}

	@Transactional
	public void resetOp1Decisions(RegisterManualVerification cases){

		if (cases != null){
			repo.resetOp1CaseDecisions(cases.getReqid(),cases.getSno());
			repo.flush();
		}else{
			System.out.println("case is null op1");
		}
	}
	@Transactional
	public void resetOp2Decisions(RegisterManualVerification cases){
		if (cases != null){
			repo.resetOp2CaseDecisions(cases.getReqid(),cases.getSno());
			repo.flush();
		}else{
			System.out.println("case is null op2");
		}
	}
	@Transactional
	public void resetSupervisorDecisions(RegisterManualVerification cases){
		if (cases != null){
			repo.resetSupervisorCaseDecisions(cases.getReqid(),cases.getSno());
			repo.flush();
		}else{
			System.out.println("case is null sup");
		}
	}

	public boolean getIdentityDetails(String id){
		/*try {
			ResponseEntity<String> tempResponse = api.getApi(ConstantValue.IDENTITY+id, String.class, tokenGenerator.getToken());
			JSONObject response = validate(tempResponse.getBody());

			if(response != null) {
				String uin = response.getJSONObject("identity").get("UIN").toString();
				System.out.println("psn generated :"+uin);
				return uin != null && !uin.isEmpty();
			}else{
				return false;
			}

		}catch (Exception e){
			e.printStackTrace();
			return false;
		}*/
		try{
			System.out.println(uinRepo.existsByRegId(id));
			return uinRepo.existsByRegId(id);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	private JSONObject validate(String response) {
		JSONObject object = new JSONObject(response);
		if (object.get("response") != JSONObject.NULL){
			return (JSONObject) object.get("response");
		}else if (object.get("errors") != JSONObject.NULL){
			return null;
//				throw new ApiResourceException("invalid input parameter -ID");
		}else {
			return null;
//				throw new ApiResourceException("Response is null");
		}

	}


	public List<RegisterManualVerification> listOfRidsForL2(Pageable pageable) {
		return repo.listOfRidsForL2(pageable);
	}


	public List<RegisterManualVerification> listOfRidsForL2(Date startDate, Date endDate, String operator1, String operator2, String dateType, int pageNo) {
		if(dateType.equals("verifiedDate")){
			return repo.listOfRidsForVerifiedDateL2(startDate, endDate, operator1, operator2, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}
		else {
			return repo.listOfRidsForCreatedDateL2(startDate, endDate, operator1, operator2, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}

	}


	public List<RegisterManualVerification> listOfRidsForL22(Date startDate, Date endDate,String dateType, int pageNo) {
		if(dateType.equals("verifiedDate")){
			return repo.listOfRidsForVerifiedDateL22(startDate, endDate, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}
		else {
			return repo.listOfRidsForCreatedDateL22(startDate, endDate, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}

	}


	public List<RegisterManualVerification> listOfRidsForL2Op1(Date startDate, Date endDate, String operator1,String dateType, int pageNo) {
		if(dateType.equals("verifiedDate")) {
			return repo.listOfRidsForVerifiedDateL2Op1(startDate, endDate, operator1, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}
		else {
			return repo.listOfRidsForCreatedDateL2Op1(startDate, endDate, operator1, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}
	}


	public List<RegisterManualVerification> listOfRidsForL2Op2(Date startDate, Date endDate, String operator2,String dateType, int pageNo) {
		if(dateType.equals("verifiedDate")) {
			return repo.listOfRidsForVerifiedDateL2Op2(startDate, endDate, operator2, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}
		else {
			return repo.listOfRidsForCreatedDateL2Op2(startDate, endDate, operator2, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}
	}




	public List<RegisterManualVerification> listOfRidsForL3(Pageable pageable) {
		return repo.listOfRidsForL3(pageable);
	}


	public List<RegisterManualVerification> listOfRidsForL3(Date startDate, Date endDate, String operator1, String operator2, String dateType, int pageNo) {
		if(dateType.equals("verifiedDate")){
			return repo.listOfRidsForVerifiedDateL3(startDate, endDate, operator1, operator2, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}
		else {
			return repo.listOfRidsForCreatedDateL3(startDate, endDate, operator1, operator2, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}
	}


	public List<RegisterManualVerification> listOfRidsForL33(Date startDate, Date endDate, String dateType, int pageNo) {
		if(dateType.equals("verifiedDate")){
			return repo.listOfRidsForVerifiedDateL33(startDate, endDate, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}
		else{
			return repo.listOfRidsForCreatedDateL33(startDate, endDate, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}
	}

	public List<RegisterManualVerification> listOfRidsForL3Op1(Date startDate, Date endDate, String operator1, String dateType, int pageNo) {
		if(dateType.equals("verifiedDate")){
			return repo.listOfRidsForVerifiedDateL3Op1(startDate, endDate, operator1, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}
		else {
			return repo.listOfRidsForCreatedDateL3Op1(startDate, endDate, operator1, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}

	}

	public List<RegisterManualVerification> listOfRidsForL3Op2(Date startDate, Date endDate, String operator1, String dateType, int pageNo) {
		if(dateType.equals("verifiedDate")){
			return repo.listOfRidsForVerifiedDateL3Op2(startDate, endDate, operator1, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}
		else {
			return repo.listOfRidsForCreatedDateL3Op2(startDate, endDate, operator1, PageRequest.of(pageNo,ConstantValue.MAXRESULT));
		}

	}



	public synchronized List getClusterForL2(String userid) {
		List<String> reqId = repo.getReqIdForL2(userid, PageRequest.of(0,1));
		System.out.println("Request id : "+reqId );
		List<RegisterManualVerification> list = new ArrayList<>();
		List<RegisterManualVerification> pendinglist = new ArrayList<>();

		if(!reqId.isEmpty()) {
			list = repo.clusterOfRids(reqId.get(0));
		}
		if(!list.isEmpty()){
			pendinglist = list.stream().filter(e -> {
				return e.getOp1userId() == null || e.getOp2userId() == null;
			}).collect(Collectors.toList());

			if(!pendinglist.isEmpty()){
				System.out.println("Pending list size : "+ pendinglist.size());
				list.replaceAll(e ->{
					e.setStatusCode("0");
					return e;
				});
				repo.saveAll(list);
				repo.flush();
//				list.clear();
				return getClusterForL2(userid);
			}
			else{
				list.replaceAll(ad-> {ad.setProStatus("1"); return ad;});
				repo.saveAll(list);
				repo.flush();
				setCaseForUser(list.get(0).getReqid(),userid);
			}
		}
		return list;
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


	public RegisterManualVerification findBySerialNumber(int sno) {
		return repo.findBySno(sno);
	}

	public int updateFinIndi(int sno){
		return repo.updateFinalIndi(sno);
	}

}
