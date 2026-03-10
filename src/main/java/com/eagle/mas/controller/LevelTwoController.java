package com.eagle.mas.controller;

import com.eagle.mas.bean.GalleryBean;
import com.eagle.mas.common.HitUsers;
import com.eagle.mas.common.ReadImage;
import com.eagle.mas.config.ConstantValue;
import com.eagle.mas.dto.SaveMvsResultRequestDto;
import com.eagle.mas.model.BioScore;
import com.eagle.mas.model.RegisterManualVerification;
import com.eagle.mas.model.UserCaseAssignment;
import com.eagle.mas.model.Userdetails;
import com.eagle.mas.regproc.model.AbisRequest;
import com.eagle.mas.regproc.model.AbisResponse;
import com.eagle.mas.regproc.model.RegBioRef;
import com.eagle.mas.regproc.repo.AbisRequestRepo;
import com.eagle.mas.regproc.repo.AbisResponseRepo;
import com.eagle.mas.regproc.repo.BioRefRepo;
import com.eagle.mas.repository.BioScoreRepository;
import com.eagle.mas.repository.RegManualVerificationRepository;
import com.eagle.mas.repository.UserCaseAssignmentRepo;
import com.eagle.mas.service.ManualVerificationService;
import com.eagle.mas.service.MvJsonService;
import com.eagle.mas.service.UserdetailsService;
import org.jose4j.base64url.Base64Url;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Scope("session")
public class LevelTwoController {

    @Autowired
    private ManualVerificationService mvs;

    @Autowired
    private RegManualVerificationRepository regManualVerificationRepository;

    @Autowired
    MvJsonService mvJsonService;

    @Autowired
    LevelThreeController req;

    @Autowired
    private UserdetailsService userdetailsService;

    @Autowired
    BioScoreRepository bioRepository;

    @Autowired
    BioRefRepo regBioRef;

    @Autowired
    AbisResponseRepo abisResponseRepo;

    @Autowired
    AbisRequestRepo abisRequestRepo;

    @Autowired
    UserCaseAssignmentRepo userCaseAssignmentRepo;

    @Autowired
    HitUsers hitUsers;

    File catalinaBase = new File(System.getProperty("catalina.base")).getAbsoluteFile();
    File propertyFile = new File(catalinaBase, "bin/mvs/");
    private String Right;
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    public String getUtcTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");

        String convertDate = dateFormat.format(new Date());
        return convertDate;

    }


    @RequestMapping(value = "/refreshClusterCaseL2",method = RequestMethod.GET)
    public String refreshNewCase(RedirectAttributes redirectAttributes, HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userID") == null) {
                logger.warn("User not logged in, redirecting to login.");
                return "redirect:redirectlogin";
            }
            Userdetails user = (Userdetails) session.getAttribute("userdetails");
            logger.info("UserID: {}", user.getUserid());
            UserCaseAssignment userCaseRequest = mvs.userCaseDetails(user.getUserid());
            if(userCaseRequest != null) {
                logger.debug("User case details found for requestId: {}", userCaseRequest.getRequestId());
                List<RegisterManualVerification> list = mvs.retreiveCaseForUser(userCaseRequest.getRequestId());
                List<RegisterManualVerification> result = list.stream().filter(e -> {
                    if (user.getUserid().equals(e.getUserId())) {
                        return e.getSupervisorVerifyStatus() != null && !e.getSupervisorVerifyStatus().isEmpty();
                    } else {
                        return false;
                    }
                }).collect(Collectors.toList());
                logger.info("Fetched list result size: {}", list.size());

                int reqCount = list.size();
                int finalIndicateCount = mvs.getFinIndicate(userCaseRequest.getRequestId());
                logger.info("Final indication result size: {}", finalIndicateCount);

                if(reqCount == finalIndicateCount){
                    list.forEach(li -> li.setCaseEvaluationComplete(1));
                 /*   long dupCount = list.stream().filter(li -> li.getFinindi().equalsIgnoreCase("DUP")).count();
                    if(dupCount > 0){
                        try {
                            hitUsers.createCase(list.get(0).getRegId(),user.getOrganisation());
                        } catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        } catch (KeyManagementException ex) {
                            throw new RuntimeException(ex);
                        }
                    }*/

                    regManualVerificationRepository.saveAll(list);
                    mvs.resetProcessStatus(userCaseRequest.getRequestId());
                    mvs.removeProcessedCaseForUser(user.getUserid());
                    logger.info("Case evaluation complete, cases submitted successfully.");
                    redirectAttributes.addFlashAttribute("successMessage", "case is submitted");
                }else {
                    logger.warn("Not all cases processed before submission. Processed count: {}, Expected count: {}", reqCount, finalIndicateCount);
                    redirectAttributes.addFlashAttribute("failureMessage", "please process all the cases before submission");
                }

            }else{
                logger.warn("No user case found, late submission is not allowed.");
                redirectAttributes.addFlashAttribute("failureMessage","late submission is not allowed");
            }
        }catch (Exception e){
            logger.error("Error in refreshNewCase method: {}", e.getMessage(), e);
        }
        return "redirect:levelTwoCluster";
    }



    @RequestMapping(value = "/levelTwoCluster", method = RequestMethod.GET)
    public String getSupervisorCluster(ModelMap model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            session.setAttribute("viewType","cluster");
            if(session.getAttribute("userID")==null){
                logger.warn("User not logged in, redirecting to login.");
                return "redirect:redirectlogin";
            }
            Userdetails user = (Userdetails) session.getAttribute("userdetails");
            logger.info("UserID: {}", user.getUserid());
            List<RegisterManualVerification> roles = new ArrayList<>();
            UserCaseAssignment supervisorCases = mvs.userCaseDetails(user.getUserid());
            if(supervisorCases ==null) {

                roles = mvs.listOfRidsHigherPriority2(user.getUserid(),"Update");

                if (roles == null || roles.isEmpty()) {
                    String[] priorities = {"2", "1"};
                    for (String priority : priorities) {
                        logger.info("Checking for cases with priority: {}", priority);
                        roles = mvs.listOfRidsHigherPriority2(user.getUserid(), priority);
                        if (roles != null && !roles.isEmpty()) {
                            logger.info("Cases found for priority: {}", priority);
                            break;
                        }
                    }
                }

                if (roles == null || roles.isEmpty()) {
                    logger.info("No cases found in priority list. Loading fallback list...");
                    roles =  mvs.getClusterForL2(user.getUserid());
                }

            }else{
                logger.debug("Supervisor case found, retrieving cases for requestId: {}", supervisorCases.getRequestId());
                roles =  mvs.retreiveCaseForUser(supervisorCases.getRequestId());
            }

            List<RegisterManualVerification> filteredItems = roles.stream()
                    .filter(item -> (("hit".equals(item.getOp1verifyStatus()) && "nohit".equals(item.getOp2verifyStatus()))
                            || ("nohit".equals(item.getOp1verifyStatus()) && "hit".equals(item.getOp2verifyStatus()))))
                    .collect(Collectors.toList());

            logger.debug("Filtered items count: {}", filteredItems.size());

            model.addAttribute("galleryList", filteredItems);
            model.addAttribute("userid",user.getUserid());
            model.addAttribute("typeOfView","clusterView");

            logger.info("LevelTwoController: showHomePage | UserID: {} | UTC Time: {}", user.getUserid(), getUtcTime());
        }catch (Exception e){
            logger.error("Error in getSupervisorCluster method | Error: {} | UTC Time: {}", e.getMessage(), getUtcTime(), e);
            return "redirect:errorPage";

        }
        return "levelTwoClusterSearch";

    }

    @RequestMapping(value = "/levelTwoSearch", method = RequestMethod.GET)
    public String showHomePage(ModelMap model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("userID")==null){
                logger.warn("User not logged in, redirecting to login.");
                return "redirect:redirectlogin";
            }
            session.setAttribute("viewType","master");
            Userdetails user = (Userdetails) session.getAttribute("userdetails");
            logger.info("UserID: {}", user.getUserid());
//            ArrayList<RegisterManualVerification> roles = (ArrayList<RegisterManualVerification>) mvs.listOfRidsForL2();
            Set<String> operators = userdetailsService.getOperator();
            logger.debug("Retrieved operators for user {}: {}", user.getUserid(), operators);

            model.addAttribute("operators", operators);
            model.addAttribute("typeOfView","listView");
//            model.addAttribute("galleryList", roles);
            logger.info("LevelTwoController: showHomePage | UserID: {} | UTC Time: {}", user.getUserid(), getUtcTime());
        }catch (Exception e){
            logger.error("Error in showHomePage method | Error: {} | UTC Time: {}", e.getMessage(), getUtcTime(), e);
            return "redirect:errorPage";
        }

        return "levelTwoSearch";

    }

    @RequestMapping(value="/loadLevelTwoData", method= RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> loadLevelTwoData(ModelMap model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userID") == null) {
                logger.warn("User session not found, returning Unauthorized.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        logger.info("User session found, proceeding with data retrieval.");

        ArrayList<RegisterManualVerification> data = (ArrayList<RegisterManualVerification>) mvs.listOfRidsForL2(PageRequest.of(0, ConstantValue.MAXRESULT));
        List<String> cases = userCaseAssignmentRepo.getRequestIds();

        logger.debug("Fetched {} records from listOfRidsForL2.", data.size());
        logger.debug("Fetched {} request IDs from userCaseAssignmentRepo.", cases.size());

        Map<String, Object> response = new HashMap<>();
        response.put("data",FilterL2Data(data, cases)); // Indicate if more data is available   FilterL2Data(data, cases)

        return ResponseEntity.ok(response);
    }

    public List<RegisterManualVerification> FilterL2Data(List<RegisterManualVerification> list, List<String> cases){
        return list.stream().filter(item->  !cases.contains(item.getReqid())).collect(Collectors.toList());
    }


    @RequestMapping(value = "/loadDataTwo", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> loadMoreFilterDataTwo(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String operator1,
            @RequestParam String operator2,
            @RequestParam String dateType,
            @RequestParam int offSet,
            HttpServletRequest request) throws java.text.ParseException {
        logger.info("Entering loadMoreFilterDataTwo method with offset: {}", offSet);
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userID") == null) {
                logger.warn("User session not found, returning Unauthorized.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
            logger.info("User session found, proceeding with data retrieval.");

        } catch (Exception e) {
            logger.error("Error checking session : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> response = new HashMap<>();
        List<RegisterManualVerification> data;
        try {
            if(startDate.isEmpty()){
                logger.info("Fetching data without date filtering.");
                data = mvs.listOfRidsForL2(PageRequest.of(offSet,ConstantValue.MAXRESULT));
                response.put("data", data);
            }
            else{
                Date start = sdf.parse(startDate);
                Date end = sdf.parse(endDate);
                // Process the data here and return a response
                logger.info("Fetching data with filters - Operator1: {}, Operator2: {}, Start Date: {}, End Date: {}, Date Type: {}",
                        operator1, operator2, startDate, endDate, dateType);

                if(operator1.isEmpty() && operator2.isEmpty()){
                    logger.info("No operators provided, filtering data by date range.");
                    data = mvs.listOfRidsForL22(start, end, dateType, offSet);
                }
                else if(operator2.isEmpty()){
                    logger.info("Operator2 is empty, filtering by Operator1 and date range.");
                    data = mvs.listOfRidsForL2Op1(start, end, operator1,dateType, offSet);
                }
                else if(operator1.isEmpty()){
                    logger.info("Operator1 is empty, filtering by Operator2 and date range.");
                    data = mvs.listOfRidsForL2Op2(start, end, operator2,dateType, offSet);
                }
                else{
                    logger.info("Both operators provided, filtering by both operators and date range.");
                    data = mvs.listOfRidsForL2(start, end, operator1, operator2,dateType, offSet);
                }

                logger.info("Successfully fetched {} records.", data.size());
                response.put("data", data);
            }
        } catch (Exception e) {
            logger.error("Error occurred while loading data: {}", e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Indicate if more data is available

        return ResponseEntity.ok(response); // Properly return data with HTTP 200 status
    }

    @RequestMapping(value = "/leveltwodetails", method = RequestMethod.GET)
    public String showLevelTwoDetailsPage(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (redirectAttributes.equals(true)) {
            redirectAttributes.addFlashAttribute("successMessage", "APPROVED SUCCESSFULLY");
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", "REJECTED SUCCESSFULLY");
        }

        return "leveltwodetails";
    }


    @RequestMapping(value = "/leveltwoSearchByName", method = RequestMethod.GET)
    public String leveltwoSearchByName(ModelMap model, HttpServletRequest request, @RequestParam("id") String id, @RequestParam("probe") String probe,
                                       @RequestParam("candidate") String candidate,@RequestParam("requestId") String requestId,@RequestParam("op1Comment")String op1Comment,
                                       @RequestParam("op1verifyStatus") String op1verifyStatus,@RequestParam("op2verifyStatus") String op2verifyStatus,@RequestParam("op2Comment")String op2Comment,
                                       @RequestParam("caseListNo") String caseListNo, @RequestParam("typeOfView") String typeOfView, RedirectAttributes redirectAttributes
    ) {

        HttpSession session = request.getSession();
        session.setAttribute("regId",probe );
        session.setAttribute("matchId",candidate);
        boolean psnGenerated =false;

        try {
            if (session.getAttribute("userID") == null) {
                return "redirect:redirectlogin";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("Can", candidate);
        model.addAttribute("Prob", probe);
        model.addAttribute("id", id);
        model.addAttribute("requestId", requestId);
        model.addAttribute("typeOfView", typeOfView);

        String pathname = new FileSystemResource("").getFile().getAbsolutePath();
//        if(matchingScore.equals(null))
//            matchingScore="0";
//        matchingScore=matchingScore+"%";
        //model.addAttribute("matchingScore", matchingScore);
        model.addAttribute("op1Comment", op1Comment);
        model.addAttribute("op2Comment", op2Comment);
        if (op2verifyStatus.equalsIgnoreCase("hit"))
            model.addAttribute("op2verifyStatus","Hit");
        else if (op2verifyStatus.equalsIgnoreCase("nohit"))
            model.addAttribute("op2verifyStatus","No Hit");
        if (op1verifyStatus.equals("hit"))
            model.addAttribute("op1verifyStatus","Hit");
        else if (op1verifyStatus.equals("nohit"))
            model.addAttribute("op1verifyStatus","No Hit");


        if (probe != null) {
            try {

                String probeRegType = mvJsonService.getRegTypeFromRegistration(probe);

                List<GalleryBean> leftfingerProb = new ArrayList<GalleryBean>();
                List<GalleryBean> rightfingerProb = new ArrayList<GalleryBean>();
                List<GalleryBean> irisProbScore = new ArrayList<GalleryBean>();
                GalleryBean beanProbe = new GalleryBean();
                GalleryBean beanBioProbe = new GalleryBean();
                JSONParser jsonParser = new JSONParser();
                String  imag = null;
                byte[] imageData = null;
                String probFaceImage=null;
                String base64StringPOI;
                String pdfFileI = null;
                String base64StringPOA;
                String base64StringPOE;
                String pdfFileA = null;
                String pdfFileE = null;
                // String pathname = new FileSystemResource("").getFile().getAbsolutePath();


                JSONParser jsonParser1 = new JSONParser();
                int count = mvs.countAllByRegId(probe);
                model.addAttribute("count",caseListNo);
                String responseText;
                String bioRefID;
                try{

                    BioScore score = bioRepository.findFirstByRegIDAndMatchedRefIdAndResponseTextNotNullOrderByCrTimesRegIdDesc(probe,probe);
                    BioScore getBioRefId = bioRepository.findFirstByMatchedRefIdAndBioRefIdIsNotNull(candidate);
//                    score.setResponseText("{\"id\":\"mosip.abis.identify\",\"requestId\":\"bc9b3ddb-8ee0-4c1a-ab4c-8fec48c66ef2\",\"returnValue\":\"1\",\"responsetime\":\"2022-07-14T10:29:39.284Z\",\"candidateList\":{\"count\":\"1\",\"candidates\":[{\"referenceId\":\"84240b4d-f61b-42fc-979f-94d99b7f2949\",\"analytics\":{\"internalScore\":\"22130.0\",\"rank\":\"2\"},\"modalities\":[{\"biometricType\":\"IIR\",\"analytics\":{\"internalScore\":\"16635.0\"}},{\"biometricType\":\"FIR\",\"analytics\":{\"internalScore\":\"22280.0\"}}]}]}}");

//                    getBioRefId.setBioRefId("84240b4d-f61b-42fc-979f-94d99b7f2949");
                    if(score != null && (!score.getResponseText().isEmpty() || score.getResponseText() != null)){
//                        logger.info("Score Response Text: {}", score.getResponseText());
                        responseText = score.getResponseText();
                    }
                    else{
                        logger.info("Score is null! Fetching from RegProc and Abis");
                        RegBioRef score1 = regBioRef.findFirstByRegIdAndBioRefIdIsNotNull(probe);
                        AbisRequest abisRequest = abisRequestRepo.findIdByRefId(score1.getBioRefId());
                        AbisResponse abisResponse = abisResponseRepo.findReqIdById(abisRequest.getId());
                        responseText = new String(abisResponse.getRespText(), StandardCharsets.UTF_8);
                    }
                    if(getBioRefId != null ){
                        logger.info("getBioRefID: {}", getBioRefId);
                        bioRefID=getBioRefId.getBioRefId();
                    }
                    else{
                        logger.info("Bio Ref Id is null! Fetching from RegProc");
                        RegBioRef refId = regBioRef.findFirstByRegIdAndBioRefIdIsNotNull(candidate);
                        bioRefID = refId.getBioRefId();
                    }

                    org.json.JSONObject matchedScore = new org.json.JSONObject(responseText);

                    org.json.JSONObject matchedCandidatesList = matchedScore.getJSONObject("candidateList");
                    org.json.JSONArray matchedCandidates = matchedCandidatesList.getJSONArray("candidates");

                    for (int i=0; i < matchedCandidates.length(); i++){
                        org.json.JSONObject getCandidate = matchedCandidates.getJSONObject(i);
                        if (bioRefID.equals(getCandidate.get("referenceId"))){
                            org.json.JSONArray modalities = getCandidate.getJSONArray("modalities");
                            for (int j=0; j < modalities.length(); j++){
                                org.json.JSONObject matchedDetails = modalities.getJSONObject(j);
                                org.json.JSONObject analytics = matchedDetails.getJSONObject("analytics");
                                if (matchedDetails.get("biometricType") != null && matchedDetails.get("biometricType").equals("FIR")){
                                    model.addAttribute("fir",analytics.get("internalScore"));
                                }
                                if (matchedDetails.get("biometricType") != null && matchedDetails.get("biometricType").equals("IIR")){
                                    model.addAttribute("iir",analytics.get("internalScore"));

                                }
                                if (matchedDetails.get("biometricType") != null && matchedDetails.get("biometricType").equals("FID")){
                                    model.addAttribute("fid",analytics.get("internalScore"));

                                }
                            }
                        }
                    }

                    if(matchedScore.getJSONObject("analytics")!=null){
                        org.json.JSONObject matchedCommentAnalytics = matchedScore.getJSONObject("analytics");
                        org.json.JSONArray matchedCommentCandidates = matchedCommentAnalytics.getJSONArray("candidates");
                        for (int i=0; i < matchedCommentCandidates.length(); i++){
                            org.json.JSONObject getCCandidate = matchedCommentCandidates.getJSONObject(i);
                            if (bioRefID.equals(getCCandidate.get("referenceId"))){

                                org.json.JSONArray adjudicationDetailsComment = getCCandidate.getJSONArray("adjudicationDetails");
                                // for (int j=0; j < adjudicationDetailsComment.length(); j++){
                                //   org.json.JSONObject matchedDetails = adjudicationDetailsComment.getJSONObject(j);
                                org.json.JSONObject matchedDetails = adjudicationDetailsComment.getJSONObject(0);
                                String comment =(String) matchedDetails.get("comment");
                                model.addAttribute("commentABIS",comment);
                                org.json.JSONObject matchedDetails1 = adjudicationDetailsComment.getJSONObject(1);
                                String comment1 = (String) matchedDetails1.get("comment");
                                model.addAttribute("comment1ABIS",comment1);
                                // }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error("level two MatchedScored Json Exception :"+e.toString());
                }

                /*
                 * Getting dcouments from mvs Response JSON
                 * Start
                 * */
                try  {
                    JSONObject jsonObject1 =mvJsonService.getJson(probe);
                    try {
                        if ("update".equalsIgnoreCase(probeRegType)) {
                            JSONObject updatedJsonObject = mvJsonService.getDemoFromIdRepo(probe, jsonObject1, probeRegType);
                            if (updatedJsonObject != null){
                                jsonObject1 = updatedJsonObject;
                            }
                            else {
                                logger.info("Identity not found in id repo");
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                        if(jsonObject1.get("documents")!=null) {
                            JSONObject jsonObjectResponse = (JSONObject) ((JSONObject) jsonObject1).get("documents");
                            if(jsonObjectResponse.get("proofOfIdentity")!=null) {
                                base64StringPOI = (String) jsonObjectResponse.get("proofOfIdentity");
                                pdfFileI = base64StringPOI;
//                                pdfFileI = "data:application/pdf;base64," + base64StringPOI;
                            }
                            if(jsonObjectResponse.get("proofOfAddress")!=null) {
                                base64StringPOA = (String) jsonObjectResponse.get("proofOfAddress");
                                pdfFileA = base64StringPOA;
//                                pdfFileA = "data:application/pdf;base64," + base64StringPOA;
                            }
                            if(jsonObjectResponse.get("proofOfException")!=null) {
                                base64StringPOE = (String) jsonObjectResponse.get("proofOfException");
//                                pdfFileE = "data:application/pdf;base64," + base64StringPOE;
                                model.addAttribute("reportPDFPOE", base64StringPOE);
                            }
                            model.addAttribute("reportPDFPOI", pdfFileI);
                            model.addAttribute("reportPDFPOA", pdfFileA);
                        }
                    }catch (Exception e){
                        logger.info("Level two search by name : " + e);
                    }
                    /*
                     * Getting dcouments from mvs JSON
                     * End
                     * */
                    /*Reading Document from JSON PROBE  End*/

                    try{
                        JSONObject jsonObj1 =(JSONObject) jsonObject1.get("identity");


                        /************Variables for Valuaes taken from JSON*************/
                        String data1 =  null;
                        JSONArray jsonArray1 = new JSONArray();
                        JSONObject jsonObject3 = new JSONObject();
                        String valueFrm= null;


                        if(jsonObj1.get("firstName")!=null) {
                            data1 = (String) jsonObj1.get("firstName");
                            JSONArray jsonArray = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject2 = (JSONObject) jsonArray.get(0);
                            valueFrm = (String) jsonObject2.get("value");
                            beanProbe.setFirstName(valueFrm);
                        }

                        if(jsonObj1.get("presentAddressLine1")!=null){
                            data1 =  (String) jsonObj1.get("presentAddressLine1");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            jsonObject3 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject3.get("value");
                            beanProbe.setPresentAddressLine1(valueFrm);
                        }

                        if(jsonObj1.get("presentBarangay")!=null){
                            data1 =  (String) jsonObj1.get("presentBarangay");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            jsonObject3 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject3.get("value");
                            beanProbe.setPresentBarangay(valueFrm);
                        }

                        if(jsonObj1.get("presentProvince")!=null){
                            data1 =  (String) jsonObj1.get("presentProvince");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanProbe.setPresentProvince(valueFrm);}

                        String can_poa = "";
                        if(jsonObj1.get("pobCity")!=null){
                            data1 =  (String) jsonObj1.get("pobCity");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            can_poa = valueFrm +", ";
                        }

                        if(jsonObj1.get("pobCountry")!=null){
                            data1 =  (String) jsonObj1.get("pobCountry");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            can_poa = can_poa + valueFrm;
                            beanProbe.setPobCountry(can_poa);}

                        if(jsonObj1.get("presentCountry")!=null){
                            data1 =  (String) jsonObj1.get("presentCountry");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanProbe.setPresentCountry(valueFrm);
                        }

                        if(jsonObj1.get("gender")!=null){
                            data1 =  (String) jsonObj1.get("gender");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanProbe.setGender(valueFrm);
                        }

                        if(jsonObj1.get("presentCity")!=null){
                            data1 =  (String) jsonObj1.get("presentCity");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanProbe.setPresentCity(valueFrm);}

                        if(jsonObj1.get("middleName")!=null){
                            data1 =  (String) jsonObj1.get("middleName");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanProbe.setMiddleName(valueFrm);}

                        if(jsonObj1.get("lastName")!=null){
                            data1 =  (String) jsonObj1.get("lastName");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanProbe.setLastName(valueFrm);}

                        if(jsonObj1.get("suffix")!=null){
                            data1 =  (String) jsonObj1.get("suffix");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanProbe.setSuffix(valueFrm);}

                        if(jsonObj1.get("dateOfBirth")!=null){
                            String data7 = (String) jsonObj1.get("dateOfBirth");
                            String[] split = data7.split("/");
                            String monthOfBirth = split[1].toString();
                            String dayOfBirth = split[2].substring(0, 2).toString();
                            String yearOfBirth = split[0].toString();
                            beanProbe.setMonthOfBirth(monthOfBirth);
                            beanProbe.setDayOfBirth(dayOfBirth);
                            beanProbe.setYearOfBirth(yearOfBirth);}

                        if(jsonObject1.get("metaInfo")!=null){
                            data1 =  (String) jsonObject1.get("metaInfo");
                            JSONObject jsonObjmet = (JSONObject) jsonParser1.parse(data1);
                            valueFrm= (String) jsonObjmet.get("registrationId");
                            beanProbe.setRegistrationId(valueFrm);

                            String crDate= (String) jsonObjmet.get("creationDate");
                            beanProbe.setCreationdate(crDate);

                            //   if(jsonObject1.get("operationsData")!=null){
                            data1 =  (String) jsonObjmet.get("operationsData");

                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            JSONObject jsonObject5 = (JSONObject) jsonArray1.get(1);
                            JSONObject jsonObject6 = (JSONObject) jsonArray1.get(2);
                            String  officer= (String) jsonObject4.get("value");
                            String  valueFrm4= (String) jsonObject4.get("value");
                            String valueFrm5= (String) jsonObject5.get("value");
                            String valueFrm6= (String) jsonObject6.get("value");

                            beanProbe.setOfficer(officer);
                            String[] dateprobes=crDate.split("T");
                            beanProbe.setCreationdate(dateprobes[0]);
                            beanProbe.setCreationtime("T"+dateprobes[1]);

                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                   try{
                       byte[] decodedBytes = Base64.getUrlDecoder().decode((String) jsonObject1.get("biometrics"));
                       String decodedBioXml = new String(decodedBytes);

                       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

                       DocumentBuilder db = dbf.newDocumentBuilder();
                       Document document = (Document) db.parse(new InputSource(new StringReader(decodedBioXml)));
                       NodeList nodeList = document.getElementsByTagName("BIR");
                       for (int i = 0; i < nodeList.getLength(); ++i) {
                           Node node = nodeList.item(i);

                           Element tElement = (Element)node;
                           String type=tElement.getElementsByTagName("Type").item(1).getTextContent();

                           if (type.equalsIgnoreCase("IRIS")) {
                               String Subtypeiris=tElement.getElementsByTagName("Subtype").item(0).getTextContent();
                               imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                               imag = new ReadImage().covertasImage(imageData,146);
                               GalleryBean irscoresProb = new GalleryBean();
                               if(Subtypeiris.equalsIgnoreCase("Left")){
                                   String leftiris = irscoresProb.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                   beanBioProbe.setLeftiris(leftiris); }
                               if(Subtypeiris.equalsIgnoreCase("Right")){
                                   String rightiris = irscoresProb.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                   beanBioProbe.setRightiris(rightiris); }
                               irscoresProb.setProbeIrisImage(imag);
                               if(irisProbScore.size()<2)
                                   irisProbScore.add(irscoresProb);
                           }else if (type.equalsIgnoreCase("Finger")) {
                               String Subtype=tElement.getElementsByTagName("Subtype").item(0).getTextContent();
                               imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                               imag = new ReadImage().covertasImage(imageData,138);

                               GalleryBean score = new GalleryBean();
                               if(Subtype.equalsIgnoreCase("Left MiddleFinger")){
                                   String leftmiddlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                   beanBioProbe.setLeftmiddlefinger(leftmiddlefinger); }
                               if(Subtype.equalsIgnoreCase("Left IndexFinger")){
                                   String leftindexfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                   beanBioProbe.setLeftindexfinger(leftindexfinger);}
                               if(Subtype.equalsIgnoreCase("Left LittleFinger")){
                                   String leftlittlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                   beanBioProbe.setLeftlittlefinger(leftlittlefinger);}
                               if(Subtype.equalsIgnoreCase("Left RingFinger")){
                                   String leftringfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                   beanBioProbe.setLeftringfinger(leftringfinger);}
                               if(Subtype.equalsIgnoreCase("Left Thumb")){
                                   String leftthumb = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                   beanBioProbe.setLeftthumb(leftthumb);}
                               if(Subtype.equalsIgnoreCase("Right MiddleFinger")){
                                   String rightmiddlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                   beanBioProbe.setRightmiddlefinger(rightmiddlefinger);}
                               if(Subtype.equalsIgnoreCase("Right IndexFinger")){
                                   String rightindexfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                   beanBioProbe.setRightindexfinger(rightindexfinger);}
                               if(Subtype.equalsIgnoreCase("Right LittleFinger")){
                                   String rightlittlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                   beanBioProbe.setRightlittlefinger(rightlittlefinger);}
                               if(Subtype.equalsIgnoreCase("Right RingFinger")){
                                   String rightringfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                   beanBioProbe.setRightringfinger(rightringfinger);}
                               if(Subtype.equalsIgnoreCase("Right Thumb")){
                                   String rightthumb = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                   beanBioProbe.setRightthumb(rightthumb);}
                               score.setFingerImage(imag);
                               leftfingerProb.add(score);
                               rightfingerProb.add(score);

                           } else if (type.equalsIgnoreCase("FACE")) {
                               imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                               probFaceImage = new ReadImage().covertasImage(imageData, 136);
                           }
                       }
                   }
                   catch(Exception e){
                       e.printStackTrace();
                    }


                    model.addAttribute("probeBioFields", beanBioProbe);
                    model.addAttribute("probeDemoFields", beanProbe);
                    model.addAttribute("leftfingerProb", leftfingerProb);
                    model.addAttribute("rightfingerProb", rightfingerProb);
                    model.addAttribute("irisProbScore", irisProbScore);
                    model.addAttribute("probFaceImage", probFaceImage);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (candidate != null) {
            try {

                String candidateRegType = mvJsonService.getRegTypeFromRegistration(candidate);

                psnGenerated = mvs.getIdentityDetails(candidate);
                List<GalleryBean> rightfingerCan = new ArrayList<GalleryBean>();
                List<GalleryBean> leftfingerCan = new ArrayList<GalleryBean>();
                List<GalleryBean> irisCanScore = new ArrayList<GalleryBean>();
                GalleryBean beanCan = new GalleryBean();
                GalleryBean beanBioCan = new GalleryBean();
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonIdentityvalue = new JSONObject();
                JSONObject jsonBiovalue = new JSONObject();
                JSONArray jsonBIRArray = new JSONArray();
                JSONObject jsonObj = new JSONObject();
                JSONObject jsonObjValue = new JSONObject();
                String jsonvalue = null;
                String CanFaceImage = null;
                String imag = null;
                byte[] imageData = null;
                String faceImage=null;
                String base64StringCPOI;
                String base64StringCPOA;
                String base64StringCPOE;
                String pdfFileCPOI = null;
                String pdfFileCPOA = null;
                String pdfFileCPOE = null;
                JSONParser jsonParser1 = new JSONParser();
                String pathname1 = new FileSystemResource("").getFile().getAbsolutePath();


                try  {

                    JSONObject jsonObject1 =mvJsonService.getJson(candidate);
                    try {
                        if ("update".equalsIgnoreCase(candidateRegType)) {
                            JSONObject updatedJsonObject = mvJsonService.getDemoFromIdRepo(candidate, jsonObject1, candidateRegType);
                            if (updatedJsonObject != null) {
                                jsonObject1 = updatedJsonObject;
                            } else {
                                logger.info("Identity not found in id repo");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (jsonObject1.get("identity") == null) {
                            if (psnGenerated) {
                                JSONObject updatedJsonObject = mvJsonService.getDemoFromIdRepo(candidate, jsonObject1, candidateRegType);
                                if (updatedJsonObject != null) {
                                    jsonObject1 = updatedJsonObject;
                                } else {
                                    logger.info("Identity not found in id repo");
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                    try {
                        if (jsonObject1.get("documents") != null) {
                            JSONObject jsonObjectResponse = (JSONObject) (jsonObject1).get("documents");
                            if (jsonObjectResponse.get("proofOfIdentity") != null) {
                                base64StringCPOI = (String) jsonObjectResponse.get("proofOfIdentity");
                                pdfFileCPOI =  base64StringCPOI;
                            }
                            if (jsonObjectResponse.get("proofOfAddress") != null) {
                                base64StringCPOA = (String) jsonObjectResponse.get("proofOfAddress");
                                pdfFileCPOA = base64StringCPOA;
                            }
                            if (jsonObjectResponse.get("proofOfException") != null) {
                                base64StringCPOE = (String) jsonObjectResponse.get("proofOfException");
                                model.addAttribute("reportPDFPOECan", base64StringCPOE);
                            }
                            model.addAttribute("reportPDFPOICan", pdfFileCPOI);
                            model.addAttribute("reportPDFPOACan", pdfFileCPOA);
                        }
                    }
                    catch (Exception e){
                        logger.info("Level two search by name : " + e);
                    }

                    try{
                        JSONObject jsonObj1 =(JSONObject) jsonObject1.get("identity");

                        String data1 =  null;
                        JSONArray jsonArray1 = new JSONArray();
                        JSONObject jsonObject3 = new JSONObject();
                        String valueFrm= null;

                        if(jsonObj1.get("firstName")!=null) {
                            data1 = (String) jsonObj1.get("firstName");
                            JSONArray jsonArray = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject2 = (JSONObject) jsonArray.get(0);
                            valueFrm = (String) jsonObject2.get("value");
                            beanCan.setFirstName(valueFrm);
                        }
                        if(jsonObj1.get("presentAddressLine1")!=null){
                            data1 =  (String) jsonObj1.get("presentAddressLine1");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            jsonObject3 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject3.get("value");
                            beanCan.setPresentAddressLine1(valueFrm);
                        }

                        if(jsonObj1.get("presentBarangay")!=null){
                            data1 =  (String) jsonObj1.get("presentBarangay");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            jsonObject3 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject3.get("value");
                            beanCan.setPresentBarangay(valueFrm);}

                        if(jsonObj1.get("presentProvince")!=null){
                            data1 =  (String) jsonObj1.get("presentProvince");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanCan.setPresentProvince(valueFrm);}
                        String can_poa = "";
                        if(jsonObj1.get("pobCity")!=null){
                            data1 =  (String) jsonObj1.get("pobCity");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            can_poa = valueFrm+", ";
                        }

                        if(jsonObj1.get("pobCountry")!=null){
                            data1 =  (String) jsonObj1.get("pobCountry");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            can_poa = can_poa + valueFrm;
                            beanCan.setPobCountry(can_poa);}

                        if(jsonObj1.get("presentCountry")!=null){
                            data1 =  (String) jsonObj1.get("presentCountry");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanCan.setPresentCountry(valueFrm);}

                        if(jsonObj1.get("gender")!=null){
                            data1 =  (String) jsonObj1.get("gender");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanCan.setGender(valueFrm);}

                        if(jsonObj1.get("presentCity")!=null){
                            data1 =  (String) jsonObj1.get("presentCity");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanCan.setPresentCity(valueFrm);}

                        if(jsonObj1.get("middleName")!=null){
                            data1 =  (String) jsonObj1.get("middleName");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanCan.setMiddleName(valueFrm);}

                        if(jsonObj1.get("lastName")!=null){
                            data1 =  (String) jsonObj1.get("lastName");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanCan.setLastName(valueFrm);}

                        if(jsonObj1.get("suffix")!=null){
                            data1 =  (String) jsonObj1.get("suffix");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanCan.setSuffix(valueFrm);}

                        if(jsonObj1.get("dateOfBirth")!=null){
                            String data7 = (String) jsonObj1.get("dateOfBirth");
                            String[] split = data7.split("/");
                            String monthOfBirth = split[1].toString();
                            String dayOfBirth = split[2].substring(0, 2).toString();
                            String yearOfBirth = split[0].toString();
                            beanCan.setMonthOfBirth(monthOfBirth);
                            beanCan.setDayOfBirth(dayOfBirth);
                            beanCan.setYearOfBirth(yearOfBirth);}
                        if(jsonObject1.get("metaInfo")!=null){
                            data1 =  (String) jsonObject1.get("metaInfo");
                            JSONObject jsonObjmet = (JSONObject) jsonParser1.parse(data1);
                            valueFrm= (String) jsonObjmet.get("registrationId");
                            beanCan.setRegistrationId(valueFrm);

                            String crDate= (String) jsonObjmet.get("creationDate");
                            beanCan.setCreationdate(crDate);

                            data1 =  (String) jsonObjmet.get("operationsData");

                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            JSONObject jsonObject5 = (JSONObject) jsonArray1.get(1);
                            JSONObject jsonObject6 = (JSONObject) jsonArray1.get(2);
                            String  officer= (String) jsonObject4.get("value");
                            String  valueFrm4= (String) jsonObject4.get("value");
                            String valueFrm5= (String) jsonObject5.get("value");
                            String valueFrm6= (String) jsonObject6.get("value");

                            beanCan.setOfficer(officer);
                            String[] datecandi=crDate.split("T");
                            beanCan.setCreationdate(datecandi[0]);
                            beanCan.setCreationtime("T"+datecandi[1]);

                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        byte[] decodedBytes = Base64.getUrlDecoder().decode((String) jsonObject1.get("biometrics"));
                        String decodedBioXml = new String(decodedBytes);
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

                        DocumentBuilder db = dbf.newDocumentBuilder();
                        Document document = (Document) db.parse(new InputSource(new StringReader(decodedBioXml)));
                        NodeList nodeList = document.getElementsByTagName("BIR");
                        for (int i = 0; i < nodeList.getLength(); ++i) {
                            Node node = nodeList.item(i);

                            Element tElement = (Element)node;
                            String type=tElement.getElementsByTagName("Type").item(1).getTextContent();
                            if (type.equalsIgnoreCase("IRIS")) {
                                String Subtypeiris=tElement.getElementsByTagName("Subtype").item(0).getTextContent();
                                imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                                CanFaceImage = new ReadImage().covertasImage(imageData,146);

                                GalleryBean irscores = new GalleryBean();
                                if(Subtypeiris.equalsIgnoreCase("Left")){
                                    String leftiris = irscores.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                    beanBioCan.setLeftiris(leftiris); }
                                if(Subtypeiris.equalsIgnoreCase("Right")){
                                    String rightiris = irscores.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                    beanBioCan.setRightiris(rightiris); }
                                irscores.setProbeIrisImage(CanFaceImage);
                                if(irisCanScore.size()<2)
                                    irisCanScore.add(irscores);
                            }else if (type.equalsIgnoreCase("Finger")) {
                                String Subtype=tElement.getElementsByTagName("Subtype").item(0).getTextContent();
                                imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                                CanFaceImage = new ReadImage().covertasImage(imageData,138);
                                GalleryBean score = new GalleryBean();
                                if(Subtype.equalsIgnoreCase("Left MiddleFinger")){
                                    String leftmiddlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                    beanBioCan.setLeftmiddlefinger(leftmiddlefinger); }
                                if(Subtype.equalsIgnoreCase("Left IndexFinger")){
                                    String leftindexfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                    beanBioCan.setLeftindexfinger(leftindexfinger);}
                                if(Subtype.equalsIgnoreCase("Left LittleFinger")){
                                    String leftlittlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                    beanBioCan.setLeftlittlefinger(leftlittlefinger);}
                                if(Subtype.equalsIgnoreCase("Left RingFinger")){
                                    String leftringfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                    beanBioCan.setLeftringfinger(leftringfinger);}
                                if(Subtype.equalsIgnoreCase("Left Thumb")){
                                    String leftthumb = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                    beanBioCan.setLeftthumb(leftthumb);}
                                if(Subtype.equalsIgnoreCase("Right MiddleFinger")){
                                    String rightmiddlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                    beanBioCan.setRightmiddlefinger(rightmiddlefinger);}
                                if(Subtype.equalsIgnoreCase("Right IndexFinger")){
                                    String rightindexfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                    beanBioCan.setRightindexfinger(rightindexfinger);}
                                if(Subtype.equalsIgnoreCase("Right LittleFinger")){
                                    String rightlittlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                    beanBioCan.setRightlittlefinger(rightlittlefinger);}
                                if(Subtype.equalsIgnoreCase("Right RingFinger")){
                                    String rightringfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                    beanBioCan.setRightringfinger(rightringfinger);}
                                if(Subtype.equalsIgnoreCase("Right Thumb")){
                                    String rightthumb = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                    beanBioCan.setRightthumb(rightthumb);}
                                //  JSONObject qualityJson = (JSONObject) jsonObjValue.get("Quality");
                                //score.setScore(qualityJson.get("Score").toString());
                                //  score.setUrl(jsonObjValue.get("Subtype").toString());
                                score.setFingerImage(CanFaceImage);
//                                if (jsonObjValue.get("Subtype").toString().contains("Left")) {
                                leftfingerCan.add(score);
//                                } else {
                                rightfingerCan.add(score);
//                                }

                            } else if (type.equalsIgnoreCase("FACE")) {
                                //imageData = FaceDecoder.convertFaceISO19794_5_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
                                imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                                CanFaceImage = new ReadImage().covertasImage(imageData,136);

                            }
                            // }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    model.addAttribute("CanBioFields", beanBioCan);
                    model.addAttribute("CanDemoFields", beanCan);
                    model.addAttribute("leftfingerCan", leftfingerCan);
                    model.addAttribute("rightfingerCan", rightfingerCan);
                    model.addAttribute("irisCanScore", irisCanScore);
                    model.addAttribute("CanFaceImage", CanFaceImage);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        model.addAttribute("psnGenerated",psnGenerated);
        model.addAttribute("probeid",probe);
        model.addAttribute("canid",candidate);

//        redirectAttributes.addFlashAttribute("LevelTwoSearchByNameModel", new HashMap<>(model));
        request.getSession().setAttribute("LevelTwoSearchByNameModel", new HashMap<>(model));
        return "redirect:levelTwoDetailPage";

    }

    @RequestMapping(value = "/levelTwoDetailPage")
    public String redirectingLevelTwoDetail(Model model, HttpServletRequest request) {
//        Map<String, Object> details = (Map<String, Object>) model.getAttribute("LevelTwoSearchByNameModel");
        Map<String, Object> details = (Map<String, Object>) request.getSession().getAttribute("LevelTwoSearchByNameModel");

        model.addAllAttributes(details);
        return "mvsLevelTwoDetail";  // Loads the JSP page
    }

    private String jsondatavalue(String jsondata){
        JSONObject fn=new JSONObject();
        try {
            JSONParser jsonParser = new JSONParser();
            jsondata.replace("", "");
            JSONArray jsonArray = null;
            jsonArray = (JSONArray) jsonParser.parse(jsondata);
            fn =(JSONObject) jsonArray.get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        };
        return(fn.get("value").toString());
    }
    public String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }

        return hexStringBuffer.toString();
    }

    public String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    public String getValueJson(String input) {
        String output = "";
        try {


            if (input.substring(0, 1).contains("[")) {
                input = input.replace("[", "").replace("]", "");

                org.json.JSONObject jsonObject = new org.json.JSONObject(input);
                output = (String) jsonObject.get("value");

            } else {
                output = input;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return output;
    }


    public static HashMap<String, Long> sortByValue(HashMap<String, Long> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Long>> list =
                new LinkedList<Map.Entry<String, Long>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
            public int compare(Map.Entry<String, Long> o1,
                               Map.Entry<String, Long> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Long> temp = new LinkedHashMap<String, Long>();
        for (Map.Entry<String, Long> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    @RequestMapping(value = "/saveMVSL2Result")
    public String saveMVSL2ResultDetail(ModelMap model, HttpServletRequest request,
                                        RedirectAttributes redirectAttributes, SaveMvsResultRequestDto mvsResultRequestDto) {
        String viewType = null;
        try {
            HttpSession session = request.getSession();
            viewType = (String) session.getAttribute("viewType");
            logger.info("view type: {}", viewType);
            Userdetails user = (Userdetails) session.getAttribute("userdetails");
            String probe= (String) session.getAttribute("regId" );
            String candidate= (String) session.getAttribute("matchId");
            int out = mvs.updateRIDTwo(Integer.parseInt(mvsResultRequestDto.getSno()), mvsResultRequestDto.getVerifyStatus(), mvsResultRequestDto.getStatusComment(), user.getUserid(), user.getFirstnameEn(), mvsResultRequestDto.getRequestId(),"2");

//            if(status.equals("decidelater"))
//                out = mvs.updateRIDTwo(Integer.valueOf(id), status, comment, user.getFirstnameEn(), "1");
//            else
//                out = mvs.updateRIDTwo(Integer.valueOf(id), status, comment, user.getFirstnameEn(), "2");
            int check = mvs.supervisorVerifiedNohit(Integer.parseInt(mvsResultRequestDto.getSno()));
            int hitCheck = mvs.supervisorVerifiedHit(Integer.parseInt(mvsResultRequestDto.getSno()));
            int hitUpdate = 0;
            int noHitUpdate = 0;
            if(check == 1){
                noHitUpdate = mvs.operatorUpdateNohit(Integer.parseInt(mvsResultRequestDto.getSno()));
            }
            if(hitCheck == 1){
                hitUpdate = mvs.operatorUpdateHit(Integer.parseInt(mvsResultRequestDto.getSno()));
            }
            if(hitUpdate == 1 || noHitUpdate == 1){
                String ReqId = mvs.getReqId(Integer.parseInt(mvsResultRequestDto.getSno()));
                int reqCount = mvs.getReqIdCount(ReqId);
                int finalIndicateCount = mvs.getFinIndicate(ReqId);
                reqCount = reqCount-1;
                if(reqCount == finalIndicateCount ){
                    int NHCount=mvs.getCountforResponse(ReqId);
                    String regId=mvs.getRegId(Integer.parseInt(mvsResultRequestDto.getSno()),mvsResultRequestDto.getRequestId());
                    if(reqCount==NHCount)
                        req.responseRequest(ReqId,regId,1);
                    else
                        req.responseRequest(ReqId,regId,0);

                }
            }

            if (out == 1) {
                if(check == 1) {
                    redirectAttributes.addFlashAttribute("successMessage", "CASE SENT FOR PSN ISSUANCE");
                }
                else{
                    redirectAttributes.addFlashAttribute("successMessage", "DETAILS UPDATED SUCCESSFULLY");
                }
            } else {
                redirectAttributes.addFlashAttribute("failureMessage", "ERROR WHILE UPDATING.");


            }
            logger.info("Operator: {}, Command: {}, Status: {}, RegId: {}, Matched RefId: {}",
                    user.getFirstnameEn(),
                    mvsResultRequestDto.getStatusComment(),
                    mvsResultRequestDto.getVerifyStatus(),
                    probe,
                    candidate);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failureMessage", "ERROR WHILE UPDATING.");

        }
        if(viewType !=null && viewType.equalsIgnoreCase("cluster")){
            return "redirect:/levelTwoCluster";
        }else {
            return "redirect:/levelTwoSearch";
        }
    }




    public static String logger(String contoller , String method, String time, String response) {
        String loggerJson = "{"+"\"controller\":\""+contoller+"\"," + "\"method\": \""+method+"\","+
                "\"time stamp\":"+time+","+"\"response\":\""+response+"\""+ "}";
        return loggerJson;
    }

}