package com.eagle.mas.controller;

import com.eagle.mas.bean.GalleryBean;
import com.eagle.mas.common.HitUsers;
import com.eagle.mas.common.ReadImage;
import com.eagle.mas.config.ConstantValue;
import com.eagle.mas.dto.FieldResponseDto;
import com.eagle.mas.dto.ResponseDto;
import com.eagle.mas.dto.SaveMvsResultRequestDto;
import com.eagle.mas.model.*;
import com.eagle.mas.regproc.model.AbisRequest;
import com.eagle.mas.regproc.model.AbisResponse;
import com.eagle.mas.regproc.model.RegBioRef;
import com.eagle.mas.regproc.repo.AbisRequestRepo;
import com.eagle.mas.regproc.repo.AbisResponseRepo;
import com.eagle.mas.regproc.repo.BioRefRepo;
import com.eagle.mas.repository.BioScoreRepository;
import com.eagle.mas.repository.RegManualVerificationRepository;
import com.eagle.mas.service.ManualVerificationService;
import com.eagle.mas.service.MvJsonService;
import com.eagle.mas.util.JsonUtility;
import com.eagle.mas.util.TokenGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.aspectj.weaver.bcel.ExceptionRange;
import org.jose4j.base64url.Base64Url;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Controller
@Scope("session")
public class LevelOneController {

    @Autowired
    private ManualVerificationService mvs;

    @Autowired
    MvJsonService mvJsonService;

    @Autowired
    LevelThreeController req;

    @Autowired
    JsonUtility jsonUtility;

    @Autowired
    RegManualVerificationRepository regManualVerificationRepository;

    @Autowired
    BioScoreRepository bioRepository;

    @Autowired
    ObjectMapper obj;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    BioRefRepo regBioRef;

    @Autowired
    AbisResponseRepo abisResponseRepo;

    @Autowired
    AbisRequestRepo abisRequestRepo;

    @Autowired
    HitUsers hitUsers;


    private static final Logger logger = LoggerFactory.getLogger(LevelOneController.class);
    File catalinaBase = new File(System.getProperty("catalina.base")).getAbsoluteFile();
    public String getUtcTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        String convertDate = dateFormat.format(new Date());
        return convertDate;
    }

    @RequestMapping(value = "/refreshNewCase",method = RequestMethod.GET)
    public String refreshNewCase(RedirectAttributes redirectAttributes, HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userID") == null) {
                logger.warn("Session expired or user not logged in. Redirecting to login.");
                return "redirect:redirectlogin";
            }
            Userdetails user = (Userdetails) session.getAttribute("userdetails");
            logger.info("refreshNewCase - UserID: {}", user.getUserid());
            UserCaseAssignment userCaseRequest = mvs.userCaseDetails(user.getUserid());
            if(userCaseRequest != null) {
                List<RegisterManualVerification> list = mvs.retreiveCaseForUser(userCaseRequest.getRequestId());
                List<RegisterManualVerification> result = list.stream().filter(e -> {
                    if (user.getUserid().equals(e.getOp1userId())) {
                        return e.getOp1verifyStatus() != null && !e.getOp1verifyStatus().isEmpty();
                    } else if (user.getUserid().equals(e.getOp2userId())) {
                        return e.getOp2verifyStatus() != null && !e.getOp2verifyStatus().isEmpty();
                    }
                    else {
                            if (e.getOp1userId() == null || e.getOp1userId().isEmpty()) {
                                // No OP1 yet ? current user becomes OP1
                                return false; // Needs to fill OP1 fields
                            } else if (e.getOp2userId() == null || e.getOp2userId().isEmpty()) {
                                // OP1 already filled by someone else ? current user becomes OP2
//                                boolean op1Filled = e.getOp1verifyStatus() != null && !e.getOp1verifyStatus().isEmpty();
                                return false;
                                // OP2 (current user) not yet filled ? allow submission
//                                return op1Filled;
                            } else {
                                // Both users already assigned ? no action needed
                                return true;
                            }

                    }
                }).collect(Collectors.toList());

                logger.info("Verification complete entries count: {}/{}", result.size(), list.size());

//                int reqCount = mvs.getReqIdCount(userCaseRequest.getRequestId());
               /* int reqCount = list.size();
                int finalIndicateCount = mvs.getFinIndicate(userCaseRequest.getRequestId());
                if(reqCount == finalIndicateCount){
                    list.forEach(li -> li.setCaseEvaluationComplete(1));
                    regManualVerificationRepository.saveAll(list);
                }*/

                if (result.size() == list.size()) {
                    logger.info("All cases processed. Submitting...");

                         /*long dupCount = list.stream().filter(e -> "DUP".equals(e.getFinindi())).count();

                            if(dupCount > 0 && dupCount == list.size()) {
                                try {
                                    hitUsers.createCase(list.get(0).getRegId(),user.getOrganisation());
                                } catch (NoSuchAlgorithmException ex) {
                                    throw new RuntimeException(ex);
                                } catch (KeyManagementException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }*/

                                list.stream().filter(e -> "DUP".equals(e.getFinindi())).forEach(
                                        e -> e.setCaseEvaluationComplete(1)
                                );





                    list.stream().filter(e -> (e.getOp1userId() != null && !e.getOp1userId().isEmpty()) && (e.getOp2userId() != null) && !e.getOp2userId().isEmpty()).forEach(e -> e.setStatusCode("1"));
                    regManualVerificationRepository.saveAll(list);
                    mvs.resetProcessStatus(userCaseRequest.getRequestId());
                    mvs.removeProcessedCaseForUser(user.getUserid());
                    redirectAttributes.addFlashAttribute("successMessage", "case is submitted");
                } else {
                    logger.warn("Some cases are not yet processed.");
                    redirectAttributes.addFlashAttribute("failureMessage", "please process all the cases before submission");
                }
            }else{
                logger.warn("No active case found or late submission attempted.");
                redirectAttributes.addFlashAttribute("failureMessage","late submission is not allowed");
            }
        }catch (Exception e){
            logger.error("Exception in refreshNewCase: ", e);
        }
        return "redirect:levelOneSearch";
    }

    @RequestMapping(value = "/levelOneSearch", method = RequestMethod.GET)
    public String showHomePage(ModelMap model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userID") == null) {
                logger.warn("Session expired or user not logged in. Redirecting to login.");
                return "redirect:redirectlogin";
            }
            Userdetails user = (Userdetails) session.getAttribute("userdetails");
            logger.info("showHomePage - UserID: {}", user.getUserid());

            Pageable page = PageRequest.of(0, 1);
            UserCaseAssignment userCaseRequest = mvs.userCaseDetails(user.getUserid());
            List<RegisterManualVerification> roles = new ArrayList<>();
           /*     String[] priorites = {"2","1","0"};
        if(userCaseRequest == null){
                for (String priority : priorites ){
                    System.out.println("priority : "+ priority);
                    roles = (ArrayList<RegisterManualVerification>) mvs.listOfRidsHigherPriority1(user.getUserid(), priority);
                    if (!roles.isEmpty() && roles !=null){
                        break;
                    }
                }
            }
            else {
                roles = (ArrayList<RegisterManualVerification>) mvs.retreiveCaseForUser(userCaseRequest.getRequestId());
            }*/

           /* if(userCaseRequest == null) {
                roles = (ArrayList<RegisterManualVerification>) mvs.listOfRidsHigherPriority(user.getUserid());
                if (roles.isEmpty() || roles == null){
                    roles= (ArrayList<RegisterManualVerification>) mvs.listOfRidsPriority(user.getUserid());
                }
                if (roles == null || roles.isEmpty()) {
                    // in this step first load operator 2 list, for this we need new query . if it is null then run this below query
                    roles = (ArrayList<RegisterManualVerification>) mvs.listOfRids(user.getUserid());
                }

            }else{
                roles = (ArrayList<RegisterManualVerification>) mvs.retreiveCaseForUser(userCaseRequest.getRequestId());
            }*/

            if (userCaseRequest == null) {
                logger.info("No case assigned to user. Searching for new cases by priority");
                logger.info("Checking for cases with priority: {}", "Update");
                roles = mvs.listOfRidsHigherPriority1(user.getUserid(),"Update");

                /*String[] priorities = {"2", "1"};
                for (String priority : priorities) {
                    logger.info("Checking for cases with priority: {}", priority);
                    roles = mvs.listOfRidsHigherPriority1(user.getUserid(), priority);
                    if (roles != null && !roles.isEmpty()) {
                        logger.info("Cases found for priority: {}", priority);
                        break;
                    }
                }*/

                if (roles == null || roles.isEmpty()) {
                    String[] priorities = {"3","2", "1"};
                    for (String priority : priorities) {
                        logger.info("Checking for cases with priority: {}", priority);
                        roles = mvs.listOfRidsHigherPriority1(user.getUserid(), priority);
                        if (roles != null && !roles.isEmpty()) {
                            logger.info("Cases found for priority: {}", priority);
                            break;
                        }
                    }
                }

                // Fallback if all priority calls return empty
                if (roles == null || roles.isEmpty()) {
                    logger.info("No cases found in priority list. Loading fallback list...");
                    roles = mvs.listOfRids(user.getUserid());
                }
            } else {
                logger.info("Case already assigned. RequestID: {}", userCaseRequest.getRequestId());
                roles = mvs.retreiveCaseForUser(userCaseRequest.getRequestId());
            }


            model.addAttribute("galleryList", roles);
            model.addAttribute("userid",user.getUserid());
            logger.info(logger("LevelOneController", "showHomePage", getUtcTime(), "UserId :" + user.getUserid()));
        }
        catch (Exception e){
            logger.error("Exception in showHomePage: ", e);
            return "redirect:errorPage";
        }
        return "levelOneSearch";
    }


    @RequestMapping(value = "/leveloneSearchByName", method = RequestMethod.GET)
    public String leveloneSearchByName(ModelMap model, RedirectAttributes redirectAttributes,HttpServletRequest request,
                                       @RequestParam("id") String id,
                                       @RequestParam("probe") String probe,
                                       @RequestParam("candidate") String candidate,
                                       @RequestParam("requestId") String requestId,
                                       @RequestParam("caseListNo") String caseListNo

    ) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        logger.info("Entering leveloneSearchByName method with params: id={}, probe={}, candidate={}, requestId={}, caseListNo={}",
                id, probe, candidate, requestId, caseListNo);

        boolean psnGenerated =false;
        logger.debug("Session attributes set: regId={}, matchRegId={}", probe, candidate);


     //   hitUsers.AuthenticateLogin();


        HttpSession session = request.getSession();
        session.setAttribute("regId",probe);
        session.setAttribute("matchRegId",candidate);
//        Pageable page = (Pageable) PageRequest.of(0,10);

//
//        List<RegisterManualVerification> processList = mvs.getListforDeactivation(false, "ACTIVATED",
//                LocalDateTime.now(ZoneId.of("UTC")).minusHours(ConstantValue.elapseTime),page);
//        session.getAttribute(candidate);
        try{
            if(session.getAttribute("userID")==null){
                logger.warn("User session expired or invalid. Redirecting to error page.");
                return "redirect:errorPage";
            }
            Userdetails user = (Userdetails) session.getAttribute("userdetails");
            logger.info("User ID: {}", user.getUserid());
            UserCaseAssignment userCase= mvs.userCaseDetails(user.getUserid());
            if(userCase == null){
                logger.warn("Case processing time expired for request ID {}. Redirecting to levelOneSearch.", requestId);
                redirectAttributes.addFlashAttribute("failureMessage","case processing time has expired for this request id");
                return "redirect:levelOneSearch";
            }
        }catch(Exception e){
            logger.error("Error in processing user case details: {}", e.getMessage(), e);
        }
//        try {
//             processStatusExist = mvs.proStatus(probe, candidate,requestId);
//        }catch(Exception e){
//            logger.error(logger("LevelOneController","leveloneSearchByName",getUtcTime(), e.toString()));
//            return "redirect:errorPage";
//        }
//        if(processStatusExist==null || processStatusExist.equalsIgnoreCase("0"))

//        {
        try{
            int string_id = Integer.parseInt(id);
//            int modify_process_status =mvs.modify_process_status(string_id); // can omit return
//            HttpSession session = request.getSession();
            logger.debug("Parsed string_id: {}", string_id);

            model.addAttribute("probefilename", probe);
            model.addAttribute("originalfilename", probe);
            model.addAttribute("id", id);
            model.addAttribute("requestId",requestId);

            logger.debug("Processing documents for probe: {}", probe);

            String pathname = new FileSystemResource("").getFile().getAbsolutePath();


            if (probe != null) {
                String probeRegType = mvJsonService.getRegTypeFromRegistration(probe);

//                int count = mvs.countAllByRegId(probe);
//                int count = Integer.parseInt(caseListNo);
                model.addAttribute("count",caseListNo);
                List<GalleryBean> leftfingerProb = new ArrayList<GalleryBean>();
                List<GalleryBean> rightfingerProb = new ArrayList<GalleryBean>();
                List<GalleryBean> irisProbScore = new ArrayList<GalleryBean>();
                GalleryBean beanProbe = new GalleryBean();
                GalleryBean beanBioProbe = new GalleryBean();
                JSONParser jsonParser = new JSONParser();
                String probFaceImage = null;
                String imag;
                byte[] imageData;
                String base64StringPOI;
                String pdfFileI = null;
                String base64StringPOA;
                String base64StringPOE;
                String pdfFileA = null;
                String pdfFileE = null;
                String pdfFile =null;
                /*
                 * Getting bioscores from JSON
                 * getting ABIS Response JSON based on RID (PROBE)
                 * retrives score based on BIOREF ID of Candidate
                 * */
                String responseText = null;
                String bioRefID;
                try{
                    logger.info("Candidate: {}", candidate);

                    BioScore score = bioRepository.findFirstByRegIDAndMatchedRefIdAndResponseTextNotNullOrderByCrTimesRegIdDesc(probe,probe);
                    BioScore getBioRefId = bioRepository.findFirstByMatchedRefIdAndBioRefIdIsNotNull(candidate);

                    // score.setResponseText("{\"id\":\"mosip.abis.identify\",\"requestId\":\"bc9b3ddb-8ee0-4c1a-ab4c-8fec48c66ef2\",\"returnValue\":\"1\",\"responsetime\":\"2022-07-14T10:29:39.284Z\",\"candidateList\":{\"count\":\"1\",\"candidates\":[{\"referenceId\":\"84240b4d-f61b-42fc-979f-94d99b7f2949\",\"analytics\":{\"internalScore\":\"22130.0\",\"rank\":\"2\"},\"modalities\":[{\"biometricType\":\"IIR\",\"analytics\":{\"internalScore\":\"16635.0\"}},{\"biometricType\":\"FIR\",\"analytics\":{\"internalScore\":\"22280.0\"}}]}]}}");
                    //score.setResponseText("{\"id\":\"mosip.abis.identify\",\"requestId\":\"bc39a755-ab30-4d54-b0fb-1a050d0d4112\",\"returnValue\":\"1\",\"responsetime\":\"2021-01-22T00:37:50.679Z\",\"candidateList\":{\"count\":\"1\",\"candidates\":[{\"referenceId\":\"825e5ec4-b990-408f-93b5-faf6f9a0de28\",\"analytics\":{\"internalScore\":\"3145.0\",\"rank\":\"2\"},\"modalities\":[{\"biometricType\":\"IIR\",\"analytics\":{\"internalScore\":\"3295.0\"}}]}]},\"analytics\":{\"wasAdjudicated\":true,\"candidates\":[{\"referenceId\":\"825e5ec4-b990-408f-93b5-faf6f9a0de28\",\"internalScore\":\"3145.0\",\"consistency\":\"Consistent\",\"adjudicationDetails\":[{\"decision\":\"NO_HIT\",\"operator\":\"soquindo\",\"comment\":\"Both Iris and fingerprints of the probe and candidate were found to be different\"},{\"decision\":\"NO_HIT\",\"operator\":\"ncabauatan\",\"comment\":\"Both Iris and fingerprints of the probe and candidate were found to be different\"}]}]}}");
                    if (score != null && score.getResponseText() != null && !score.getResponseText().isEmpty()) {
//                        logger.info("Score Response Text: {}", score.getResponseText());
                        responseText = score.getResponseText();
                    } else {
                        logger.info("Score is null or response text empty! Fetching from RegProc and Abis");

                        RegBioRef score1 = regBioRef.findFirstByRegIdAndBioRefIdIsNotNull(probe);
                        if (score1 == null) {
                            throw new Exception("Bio ref id not available");
                        }

                        AbisRequest abisRequest = abisRequestRepo.findIdByRefId(score1.getBioRefId());
                        if (abisRequest != null) {
                            AbisResponse abisResponse = abisResponseRepo.findReqIdById(abisRequest.getId());
                            if (abisResponse != null && abisResponse.getRespText() != null) {
                                responseText = new String(abisResponse.getRespText(), StandardCharsets.UTF_8);
                            }
                        }
                    }

                    if (getBioRefId != null && getBioRefId.getBioRefId() != null) {
                        logger.info("getBioRefID: {}", getBioRefId.getBioRefId());
                        bioRefID = getBioRefId.getBioRefId();
                    } else {
                        logger.info("Bio Ref Id is null! Fetching from RegProc");
                        RegBioRef refId = regBioRef.findFirstByRegIdAndBioRefIdIsNotNull(candidate);
                        if (refId != null && refId.getBioRefId() != null) {
                            bioRefID = refId.getBioRefId();
                        } else {
                            throw new Exception("Bio ref id not available for candidate");
                        }
                    }
                    // getBioRefID.setBioRefId("84240b4d-f61b-42fc-979f-94d99b7f2949");
                    //getBioRefID.setBioRefId("825e5ec4-b990-408f-93b5-faf6f9a0de28");
//                    System.out.println("BIOref_id :"+getBioRefID.getBioRefId());
                    org.json.JSONObject matchedScore = new org.json.JSONObject(responseText);
                    org.json.JSONObject matchedCandidatesList = matchedScore.getJSONObject("candidateList");
                    org.json.JSONArray matchedCandidates = matchedCandidatesList.getJSONArray("candidates");
                    for (int i=0; i < matchedCandidates.length(); i++){
                        org.json.JSONObject getCandidate = matchedCandidates.getJSONObject(i);
                        if (bioRefID.equals(getCandidate.get("referenceId"))){
                            logger.info("Test refid: {}", getCandidate.get("referenceId"));
                            org.json.JSONArray modalities = getCandidate.getJSONArray("modalities");
                            for (int j=0; j < modalities.length(); j++){
                                org.json.JSONObject matchedDetails = modalities.getJSONObject(j);
                                org.json.JSONObject analytics = matchedDetails.getJSONObject("analytics");
                                if (matchedDetails.get("biometricType") != null && matchedDetails.get("biometricType").equals("FIR")){
                                    model.addAttribute("fir",analytics.get("internalScore"));
                                    logger.info("FIR: {}", analytics.get("internalScore"));
                                }
                                if (matchedDetails.get("biometricType") != null && matchedDetails.get("biometricType").equals("IIR")){
                                    model.addAttribute("iir",analytics.get("internalScore"));
                                    logger.info("IIR: {}", analytics.get("internalScore"));
                                }
                                if (matchedDetails.get("biometricType") != null && matchedDetails.get("biometricType").equals("FID")){
                                    model.addAttribute("fid",analytics.get("internalScore"));
                                    logger.info("FID: {}", analytics.get("internalScore"));
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
                                logger.info("Test refid comment section: {}", getCCandidate.get("referenceId"));
                                org.json.JSONArray adjudicationDetailsComment = getCCandidate.getJSONArray("adjudicationDetails");
                                // for (int j=0; j < adjudicationDetailsComment.length(); j++){
                                //   org.json.JSONObject matchedDetails = adjudicationDetailsComment.getJSONObject(j);
                                org.json.JSONObject matchedDetails = adjudicationDetailsComment.getJSONObject(0);
                                String comment =(String) matchedDetails.get("comment");
                                logger.info("Adjudication L1 Details Comment : " + comment);
                                model.addAttribute("commentABIS",comment);
                                org.json.JSONObject matchedDetails1 = adjudicationDetailsComment.getJSONObject(1);
                                String comment1 = (String) matchedDetails1.get("comment");
                                logger.info("Adjudication L1 Details Comment 1 : " + comment1);
                                model.addAttribute("comment1ABIS",comment1);
                                // }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error("level one MatchedScored Json Exception :"+e.toString());
                }
                /*
                 * End of getting bioscores
                 * */


                try {
                    try{
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
                                JSONObject jsonObjectResponse = (JSONObject) jsonObject1.get("documents");
                                if(jsonObjectResponse.get("proofOfIdentity")!=null) {
                                    base64StringPOI = (String) jsonObjectResponse.get("proofOfIdentity");
//                                     pdfFileI = "data:application/pdf;base64," + base64StringPOI;
                                    pdfFileI = base64StringPOI;
                                }
                                if(jsonObjectResponse.get("proofOfAddress")!=null) {
                                    base64StringPOA = (String) jsonObjectResponse.get("proofOfAddress");
//                                    pdfFileA = "data:application/pdf;base64," + base64StringPOA;
                                    pdfFileA = base64StringPOA;
                                }
                                if(jsonObjectResponse.get("proofOfException")!=null) {
                                    base64StringPOE = (String) jsonObjectResponse.get("proofOfException");
                                    model.addAttribute("reportPDFPOE", base64StringPOE);
                                }
                                if(pdfFileI!=null)
                                    model.addAttribute("reportPDFPOI", pdfFileI);

                                if(pdfFileA!=null)
                                    model.addAttribute("reportPDFPOA", pdfFileA);

//                                if(pdfFileE!=null)
//                                    model.addAttribute("reportPDFPOE", pdfFileE);

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            logger.error(logger("LevelOneController","leveloneSearchByName",getUtcTime(), e.toString()));
                        }
                        /*
                         * Getting dcouments from mvs JSON
                         * End
                         * */
                        /*Reading Document from JSON PROBE  End*/

                        try{
                            JSONObject jsonObj1 =(JSONObject) jsonObject1.get("identity");
                            String data1 =  null;
                            JSONArray jsonArray1 = new JSONArray();
                            JSONObject jsonObject3 = new JSONObject();
                            String valueFrm= null;


                            if(jsonObj1.get("firstName") != null){
                                data1 =  (String) jsonObj1.get("firstName");
                                JSONArray jsonArray = (JSONArray) jsonParser.parse(data1);
                                JSONObject jsonObject2 = (JSONObject) jsonArray.get(0);
                                String firstName= (String) jsonObject2.get("value");
                                beanProbe.setFirstName(firstName);
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
                                JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                                valueFrm= (String) jsonObject4.get("value");
                                beanProbe.setPresentBarangay(valueFrm);}

                            if(jsonObj1.get("presentProvince")!=null){
                                data1 =  (String) jsonObj1.get("presentProvince");
                                jsonArray1 = (JSONArray) jsonParser.parse(data1);
                                JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                                valueFrm= (String) jsonObject4.get("value");

                                beanProbe.setPresentProvince(valueFrm);}
                            String pobCity_Country = "";

                            if(jsonObj1.get("pobCity")!=null){
                                data1 =  (String) jsonObj1.get("pobCity");
                                jsonArray1 = (JSONArray) jsonParser.parse(data1);
                                JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                                valueFrm= (String) jsonObject4.get("value");
                                pobCity_Country = valueFrm+", ";

//                            beanProbe.setPobCountry(valueFrm);
                            }

                            if(jsonObj1.get("pobCountry")!=null){
                                data1 =  (String) jsonObj1.get("pobCountry");
                                jsonArray1 = (JSONArray) jsonParser.parse(data1);
                                JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                                valueFrm= (String) jsonObject4.get("value");

                                pobCity_Country = pobCity_Country + valueFrm;
                                beanProbe.setPobCountry(pobCity_Country);}

                            if(jsonObj1.get("gender")!=null){
                                data1 =  (String) jsonObj1.get("gender");
                                jsonArray1 = (JSONArray) jsonParser.parse(data1);
                                JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                                valueFrm= (String) jsonObject4.get("value");

                                beanProbe.setGender(valueFrm);}

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

                            if(jsonObj1.get("presentZipcode")!=null){
                                data1 =  (String) jsonObj1.get("presentZipcode");
                                jsonArray1 = (JSONArray) jsonParser.parse(data1);
                                JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                                valueFrm= (String) jsonObject4.get("value");

                                beanProbe.setPresentZipcode(valueFrm);}

                            if(jsonObj1.get("presentCountry")!=null){
                                data1 =  (String) jsonObj1.get("presentCountry");
                                jsonArray1 = (JSONArray) jsonParser.parse(data1);
                                JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                                valueFrm= (String) jsonObject4.get("value");

                                beanProbe.setPresentCountry(valueFrm);}

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
                            model.addAttribute("probeDemoFields", beanProbe);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                        try{
                            byte[] decodedBytes = Base64.getUrlDecoder().decode((String) jsonObject1.get("biometrics"));
                            String decodedBioXml = new String(decodedBytes);
//                        try{
//                            //jsonObject = (JSONObject) jsonParser.parse(reader2);
//
//                            // String pathname = new FileSystemResource("").getFile().getAbsolutePath();
//                            OutputStream out = new FileOutputStream(pathname+"/mvs/datajsonProgram.xml");
//                            out.write(decodedBioXml.getBytes());
//                            out.close();
//                        }
//                        catch(Exception e) {
//                            logger.error(logger("LevelOneController","leveloneSearchByName",getUtcTime(), e.toString()));
//                        }
//                        File file = new File(pathname+"/mvs/datajsonProgram.xml");
                            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();


                            DocumentBuilder db = dbf.newDocumentBuilder();
                            Document document = (Document) db.parse(new InputSource(new StringReader(decodedBioXml)));
                            NodeList nodeList = document.getElementsByTagName("BIR");

                            for (int i = 0; i < nodeList.getLength(); ++i) {
                                Node node = nodeList.item(i);
//                        System.out.println("\nNode Name :"
//                                + node.getNodeName());
                                //  if (node.getNodeType()== Node.ELEMENT_NODE) {
                                Element tElement = (Element)node;
                                String type=tElement.getElementsByTagName("Type").item(1).getTextContent();

                                if (type.equalsIgnoreCase("IRIS")) {
                                    String Subtypeiris=tElement.getElementsByTagName("Subtype").item(0).getTextContent();

                                    imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                                    imag = new ReadImage().covertasImage(imageData,146);
//                                  imageData = IrisDecoder.convertFaceISO19794_6_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                                  BufferedImage image = JDeli.read(imageData);
//                                String pathname = new FileSystemResource("").getFile().getAbsolutePath();
//                                 File opFile = new File("D://probeIris.jpg");
//                                  JDeli.write(image, "jpg", opFile);
//                                jarPath = JDeli.class
//                                        .getProtectionDomain()
//                                        .getCodeSource()
//                                        .getLocation()
//                                        .toURI()
//                                        .getPath();
//                                System.out.println("jarPath"+jarPath);
//                                 String enCodeFile = null;
//                                  FileInputStream tmpInputStream = new FileInputStream(opFile);
//                                 byte[] viewImage = new byte[(int) opFile.length()];
//                                  tmpInputStream.read(viewImage);
//                                  enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                                  imag = "data:image/jpg;base64," + enCodeFile;
//                                   tmpInputStream.close();
                                    GalleryBean irscoresProb = new GalleryBean();
                                    if(Subtypeiris.equalsIgnoreCase("Left")){
                                        String leftiris = irscoresProb.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                        beanBioProbe.setLeftiris(leftiris); }
                                    if(Subtypeiris.equalsIgnoreCase("Right")){
                                        String rightiris = irscoresProb.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());

                                        beanBioProbe.setRightiris(rightiris); }
//                             irscoresProb.setScore(tElement.getElementsByTagName("Subtype").item(0).getTextContent());

//                            beanProbe.setIrscoresProb(irscoresProb.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent()));

                                    // JSONObject qualityJson = (JSONObject) jsonObjValue.get("Quality");
                                    // irscores.setScore(qualityJson.get("Score").toString());
                                    // irscores.setUrl(jsonObjValue.get("Subtype").toString());
                                    irscoresProb.setProbeIrisImage(imag);
                                    if(irisProbScore.size()<2)
                                        irisProbScore.add(irscoresProb);
                                }else if (type.equalsIgnoreCase("Finger")) {
                                    String Subtype=tElement.getElementsByTagName("Subtype").item(0).getTextContent();


                                    imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                                    imag = new ReadImage().covertasImage(imageData,138);
//                                imageData = FingerDecoder.convertFingerISO19794_4_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                                BufferedImage image = JDeli.read(imageData);
//                                // String pathname = new FileSystemResource("").getFile().getAbsolutePath();
//                                File opFile = new File(pathname+"/mvs/probeFace1.jpg");
//                                ImageIO.write(image, "jpg", opFile);
//                                // JDeli.write(image, "jpg", opFile);
//                                String enCodeFile = null;
//                                FileInputStream tmpInputStream = new FileInputStream(opFile);
//                                byte[] viewImage = new byte[(int) opFile.length()];
//                                tmpInputStream.read(viewImage);
//                                enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                                imag = "data:image/jpg;base64," + enCodeFile;
//                                tmpInputStream.close();
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

                                    //  JSONObject qualityJson = (JSONObject) jsonObjValue.get("Quality");
                                    //score.setScore(qualityJson.get("Score").toString());
                                    //  score.setUrl(jsonObjValue.get("Subtype").toString());
                                    score.setFingerImage(imag);
//                                if (jsonObjValue.get("Subtype").toString().contains("Left")) {
                                    leftfingerProb.add(score);
//                                } else {
                                    rightfingerProb.add(score);
//                                }
                                } else
                                if (type.equalsIgnoreCase("FACE")) {
                                    //imageData = FaceDecoder.convertFaceISO19794_5_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
                                    imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                                    probFaceImage = new ReadImage().covertasImage(imageData,136);
//                                imageData = FaceDecoder.convertFaceISO19794_5_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                                System.out.println(imageData);
//                                BufferedImage image = JDeli.read(imageData);
//                                File opFile = new File(pathname+"/mvs/probeFace.jpg");
//                                JDeli.write(image, "jpg", opFile);
//                                String enCodeFile = null;
//                                FileInputStream tmpInputStream = new FileInputStream(opFile);
//                                byte[] viewImage = new byte[(int) opFile.length()];
//                                tmpInputStream.read(viewImage);
//                                enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                                probFaceImage = "data:image/jpg;base64," + enCodeFile;
//                                  model.addAttribute("mosipimage", imag);
//                                tmpInputStream.close();
                                }
                                // }
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }



                        model.addAttribute("probeBioFields", beanBioProbe);
                        model.addAttribute("probeDemoFields", beanProbe);
                        model.addAttribute("leftfingerProb", leftfingerProb);
                        model.addAttribute("rightfingerProb", rightfingerProb);
                        model.addAttribute("irisProbScore", irisProbScore);
                        model.addAttribute("probFaceImage", probFaceImage);
                        model.addAttribute("iconspath", pathname);

                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error(logger("LevelOneController","leveloneSearchByName",getUtcTime(), e.toString()));

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    logger.error(logger("LevelOneController","leveloneSearchByName",getUtcTime(), e.toString()));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        if (candidate != null) {
            String candidateRegType = mvJsonService.getRegTypeFromRegistration(candidate);


            List<GalleryBean> leftfingerCan = new ArrayList<GalleryBean>();
            List<GalleryBean> rightfingerCan = new ArrayList<GalleryBean>();
            List<GalleryBean> irisCanScore = new ArrayList<GalleryBean>();
            GalleryBean beanCan = new GalleryBean();
            GalleryBean beanBioCan = new GalleryBean();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonIdentityvalue = new JSONObject();
            JSONObject jsonBiovalue = new JSONObject();
            JSONArray jsonBIRArray = new JSONArray();
            JSONObject jsonObj = new JSONObject();
            JSONObject jsonObjValue = new JSONObject();
            String jsonvalue = null;
            String CanFaceImage = null;
            String imag = null;
            byte[] imageData = null;
            String faceImage = null;
            String base64StringCPOI;
            String base64StringCPOA;
            String base64StringCPOE;
            String pdfFileCPOI = null;
            String pdfFileCPOA = null;
            String pdfFileCPOE = null;



            try {

                psnGenerated = mvs.getIdentityDetails(candidate);
                JSONParser jsonParser1 = new JSONParser();
                String pathname1 = new FileSystemResource("").getFile().getAbsolutePath();

                try  {
                    JSONObject jsonObject1 = mvJsonService.getJson(candidate);

                        try {
                            if ("update".equalsIgnoreCase(candidateRegType)) {
                                JSONObject updatedJsonObject = mvJsonService.getDemoFromIdRepo(candidate, jsonObject1, candidateRegType);
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
                            if ( jsonObject1.get("identity") == null) {
                                if(psnGenerated){
                                    JSONObject updatedJsonObject = mvJsonService.getDemoFromIdRepo(candidate, jsonObject1, candidateRegType);
                                    if (updatedJsonObject != null){
                                        jsonObject1 = updatedJsonObject;
                                    }
                                    else {
                                        logger.info("Identity not found in id repo");
                                    }
                                }

                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }


                    /*Reading Document from JSON CANDIDATE Start*/
                    try{
                        if(jsonObject1.get("documents")!=null) {
                            JSONObject jsonObjectResponse = (JSONObject) (jsonObject1.get("documents")) ;
                            if (jsonObjectResponse.get("proofOfIdentity") != null) {
                                base64StringCPOI = (String) jsonObjectResponse.get("proofOfIdentity");
//                                pdfFileCPOI = "data:application/pdf;base64," + base64StringCPOI;
                                pdfFileCPOI =  base64StringCPOI;
                            }
                            if (jsonObjectResponse.get("proofOfAddress") != null) {
                                base64StringCPOA = (String) jsonObjectResponse.get("proofOfAddress");
                                pdfFileCPOA =  base64StringCPOA;
                            }
                            if (jsonObjectResponse.get("proofOfException") != null) {
                                base64StringCPOE = (String) jsonObjectResponse.get("proofOfException");
                                // pdfFileCPOE = "data:application/pdf;base64," + base64StringCPOE;
                                model.addAttribute("reportPDFPOECan", base64StringCPOE);
                            }
                            model.addAttribute("reportPDFPOICan", pdfFileCPOI);
                            model.addAttribute("reportPDFPOACan", pdfFileCPOA);

                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        logger.error(logger("LevelOneController","leveloneSearchByName",getUtcTime(), e.toString()));
                    }
                    /*Reading Document from JSON CANDIDATE  End*/

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
                        String can_poa = "";
                        if(jsonObj1.get("pobCity")!=null){
                            data1 =  (String) jsonObj1.get("pobCity");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            can_poa = valueFrm +", ";

//                            beanCan.setPobCountry(valueFrm);
                        }

                        if(jsonObj1.get("pobCountry")!=null){
                            data1 =  (String) jsonObj1.get("pobCountry");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");

                            can_poa = can_poa + valueFrm;
                            beanCan.setPobCountry(can_poa);}

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

                        if(jsonObj1.get("presentZipcode")!=null){
                            data1 =  (String) jsonObj1.get("presentZipcode");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");

                            beanCan.setPresentZipcode(valueFrm);}

                        if(jsonObj1.get("presentCountry")!=null){
                            data1 =  (String) jsonObj1.get("presentCountry");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            valueFrm= (String) jsonObject4.get("value");
                            beanCan.setPresentCountry(valueFrm);

                        }

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
                        model.addAttribute("CanDemoFields", beanCan);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    /************Variables for Valuaes taken from JSON*************/
                    try {
                        byte[] decodedBytes = Base64.getUrlDecoder().decode((String) jsonObject1.get("biometrics"));
                        String decodedBioXml = new String(decodedBytes);

//                        try{
//                            //jsonObject = (JSONObject) jsonParser.parse(reader2);
//
//                            OutputStream out = new FileOutputStream(pathname+"/mvs/canjsonProgram.xml");
//                            out.write(decodedBioXml.getBytes());
//                            out.close();
//                        }
//                        catch(Exception e) {
//                            logger.error(logger("LevelOneController","leveloneSearchByName",getUtcTime(), e.toString()));
//                        }
//                        File file = new File(pathname+"/mvs/canjsonProgram.xml");
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();


                        DocumentBuilder db = dbf.newDocumentBuilder();
                        Document document = (Document) db.parse(new InputSource(new StringReader(decodedBioXml)));

                        NodeList nodeList = document.getElementsByTagName("BIR");

                        for (int i = 0; i < nodeList.getLength(); ++i) {
                            Node node = nodeList.item(i);
//                        System.out.println("\nNode Name :"
//                       + node.getNodeName());
                            //  if (node.getNodeType()== Node.ELEMENT_NODE) {
                            Element tElement = (Element)node;
                            String type=tElement.getElementsByTagName("Type").item(1).getTextContent();

                            if (type.equalsIgnoreCase("IRIS")) {
                                String Subtypeiris=tElement.getElementsByTagName("Subtype").item(0).getTextContent();

                                imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                                CanFaceImage = new ReadImage().covertasImage(imageData,146);
//
//                                imageData = IrisDecoder.convertFaceISO19794_6_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                                //imageData = IrisDecoder.convertFaceISO19794_6_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
//                                // imageData = cDecoder.eISO19794_6_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
//                                BufferedImage image = JDeli.read(imageData);
//                                File opFile = new File(pathname+"/mvs/"   + probe+i + ".jpg");
//                                JDeli.write(image, "jpg", opFile);
//                                String enCodeFile = null;
//                                FileInputStream tmpInputStream = new FileInputStream(opFile);
//                                byte[] viewImage = new byte[(int) opFile.length()];
//                                tmpInputStream.read(viewImage);
//                                enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                                CanFaceImage = "data:image/jpg;base64," + enCodeFile;
//                                tmpInputStream.close();
                                GalleryBean irscores = new GalleryBean();
                                if(Subtypeiris.equalsIgnoreCase("Left")){
                                    String leftiris = irscores.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());

                                    beanBioCan.setLeftiris(leftiris); }
                                if(Subtypeiris.equalsIgnoreCase("Right")){
                                    String rightiris = irscores.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());

                                    beanBioCan.setRightiris(rightiris); }
//                            beanBioCan.setScore (tElement.getElementsByTagName("Subtype").item(0).getTextContent());
//                            beanBioCan.setIrscoresProb(irscores.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent()));
                                // JSONObject qualityJson = (JSONObject) jsonObjValue.get("Quality");
                                // irscores.setScore(qualityJson.get("Score").toString());
                                // irscores.setUrl(jsonObjValue.get("Subtype").toString());
                                irscores.setProbeIrisImage(CanFaceImage);
                                if(irisCanScore.size()<2)
                                    irisCanScore.add(irscores);
                            }else if (type.equalsIgnoreCase("Finger")) {
                                String Subtype=tElement.getElementsByTagName("Subtype").item(0).getTextContent();

                                imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                                CanFaceImage = new ReadImage().covertasImage(imageData,138);
//                                imageData = FingerDecoder.convertFingerISO19794_4_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                                BufferedImage image = JDeli.read(imageData);
//                                //  String pathname = new FileSystemResource("").getFile().getAbsolutePath();
//                                File opFile = new File(pathname+"/mvs/"   + probe + ".jpg");
//                                JDeli.write(image, "jpg", opFile);
//                                String enCodeFile = null;
//                                FileInputStream tmpInputStream = new FileInputStream(opFile);
//                                byte[] viewImage = new byte[(int) opFile.length()];
//                                tmpInputStream.read(viewImage);
//                                enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                                CanFaceImage = "data:image/jpg;base64," + enCodeFile;
//                                tmpInputStream.close();
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
                            } else

                            if (type.equalsIgnoreCase("FACE")) {
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

                }
                catch (Exception e){
                    e.printStackTrace();
                    logger.error(logger("LevelOneController","leveloneSearchByName",getUtcTime(), e.toString()));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                logger.error(logger("LevelOneController","leveloneSearchByName",getUtcTime(), e.toString()));
            }
        }
        model.addAttribute("psnGenerated",psnGenerated);
        model.addAttribute("probeid",probe);
        model.addAttribute("canid",candidate);
        System.out.println("checking candi and probe id in jsp : "+ probe + " : " +candidate);

//        redirectAttributes.addFlashAttribute("LevelOneSearchByNameModel", new HashMap<>(model));
        request.getSession().setAttribute("LevelOneSearchByNameModel", new HashMap<>(model));
        return "redirect:levelOneDetail";
    }

    @RequestMapping(value = "/levelOneDetail")
    public String redirectingLevelOneDetail(Model model, HttpServletRequest request) {
//        Map<String, Object> details = (Map<String, Object>) model.getAttribute("LevelOneSearchByNameModel");
        Map<String, Object> details = (Map<String, Object>) request.getSession().getAttribute("LevelOneSearchByNameModel");
        model.addAllAttributes(details);
        return "mvsLevelOneDetail";
    }

    private String jsondatavalue(String jsondata) {
        JSONObject fn = new JSONObject();
        try {
            JSONParser jsonParser = new JSONParser();
            jsondata.replace("", "");
            JSONArray jsonArray = null;
            jsonArray = (JSONArray) jsonParser.parse(jsondata);
            fn = (JSONObject) jsonArray.get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (fn.get("value").toString());
    }    /*KJS*/



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

//    @RequestMapping(value = "/saveMVSL1Result")
//    public String saveMVSL1ResultDetail(ModelMap model, HttpServletRequest request,
//                                        RedirectAttributes redirectAttributes, @RequestParam("sno") String id,
//                                        @RequestParam("verifyStatus") String status,
//                                        @RequestParam("statusComment") String comment,
//                                        @RequestParam("requestId")String requestId) {

    @RequestMapping(value = "/saveMVSL1Result")
    public String saveMVSL1ResultDetail(ModelMap model, HttpServletRequest request,
                                        RedirectAttributes redirectAttributes, SaveMvsResultRequestDto mvsResultRequestDto) {
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("userID")==null){
                logger.warn("Session expired or user not logged in. Redirecting to login.");
                return "redirect:redirectlogin";
            }
            Userdetails user = (Userdetails) session.getAttribute("userdetails");
            logger.info("saveMVSL1ResultDetail - UserID: {}, OperatorName: {}", user.getUserid(), user.getFirstnameEn());
            UserCaseAssignment userCase= mvs.userCaseDetails(user.getUserid());
            if(userCase == null){
                logger.warn("Case processing time expired for requestId: {}", mvsResultRequestDto.getRequestId());
                redirectAttributes.addFlashAttribute("failureMessage","case processing time has expired for this request id");
                return "redirect:levelOneSearch";
            }
            String probe= (String) session.getAttribute("regId");

            String candidate= (String) session.getAttribute("matchRegId");

            session.getAttribute("regId");
            session.getAttribute("candidate");

            String Statuscoment= mvs.getStatuscomment(Integer.parseInt(mvsResultRequestDto.getSno()));
            logger.info("PreviousStatusComment for SerialNumber {}: {}", mvsResultRequestDto.getSno(), Statuscoment);


            RegisterManualVerification reg = mvs.findBySerialNumber(Integer.parseInt(mvsResultRequestDto.getSno()));

            int out=0;
            int UINGen=0;
            // int op2Out=0;
           /* if(Statuscoment==null || Statuscoment.equalsIgnoreCase("")){
                out = mvs.updateRID(Integer.parseInt(mvsResultRequestDto.getSno()), mvsResultRequestDto.getVerifyStatus(), mvsResultRequestDto.getStatusComment(),user.getUserid(), user.getFirstnameEn(),mvsResultRequestDto.getRequestId(), "0");
            }
             if(Statuscoment !=null) {
                out = mvs.updateRIDstatus2(Integer.parseInt(mvsResultRequestDto.getSno()), mvsResultRequestDto.getVerifyStatus(), mvsResultRequestDto.getStatusComment(), user.getUserid(),user.getFirstnameEn(), mvsResultRequestDto.getRequestId(),"1");
            }*/
            // this code for restrict operator from taking 2 decisions
            if(Statuscoment==null || Statuscoment.equalsIgnoreCase("")){
                out = mvs.updateRID(Integer.parseInt(mvsResultRequestDto.getSno()), mvsResultRequestDto.getVerifyStatus(), mvsResultRequestDto.getStatusComment(),user.getUserid(), user.getFirstnameEn(),mvsResultRequestDto.getRequestId(), "0");
                logger.info("Operator 1 decision by operator. Update result: {}", out);

            }
            else if(user.getUserid().equals(reg.getOp1userId())){
                out = mvs.updateRID(Integer.parseInt(mvsResultRequestDto.getSno()), mvsResultRequestDto.getVerifyStatus(), mvsResultRequestDto.getStatusComment(),user.getUserid(), user.getFirstnameEn(),mvsResultRequestDto.getRequestId(), "0");
                logger.info("Operator 1 updated existing decision. Update result: {}", out);

            }
            else if(user.getUserid().equals(reg.getOp2userId()) ){


                out = mvs.updateRIDstatus2(Integer.parseInt(mvsResultRequestDto.getSno()), mvsResultRequestDto.getVerifyStatus(), mvsResultRequestDto.getStatusComment(), user.getUserid(),user.getFirstnameEn(), mvsResultRequestDto.getRequestId(),"1");
                logger.info("Operator 2 updated existing decision. Update result: {}", out);

            }
            else {

                out = mvs.updateRIDstatus2(Integer.parseInt(mvsResultRequestDto.getSno()), mvsResultRequestDto.getVerifyStatus(), mvsResultRequestDto.getStatusComment(), user.getUserid(),user.getFirstnameEn(), mvsResultRequestDto.getRequestId(),"1");
                logger.info("Operator 2 time decision by operator. Update result: {}", out);
            }


            int nhCase = mvs.operatorVerifiedNohit(Integer.parseInt(mvsResultRequestDto.getSno()));
            int hCase = mvs.operatorVerifiedHit(Integer.parseInt(mvsResultRequestDto.getSno()));

            int hitUpdate = 0;
            int noHitUpdate = 0;
            if(nhCase == 1){
                noHitUpdate = mvs.operatorUpdateNohit(Integer.parseInt(mvsResultRequestDto.getSno()));
            }
            if(hCase == 1){
                hitUpdate = mvs.operatorUpdateHit(Integer.parseInt(mvsResultRequestDto.getSno()));
            }

            // this code for restrict operator from taking 2 decision
        /*    if(nhCase == 1){
                noHitUpdate = mvs.operatorUpdateNohit(Integer.parseInt(mvsResultRequestDto.getSno()));
            }
            else if(hCase == 1){
                hitUpdate = mvs.operatorUpdateHit(Integer.parseInt(mvsResultRequestDto.getSno()));
            }
            else {
                mvs.updateFinIndi(Integer.parseInt(mvsResultRequestDto.getSno()));
                UINGen = 0;
            }*/
            logger.info("Hit/NoHit flags - nhCase: {}, hCase: {}, nhUpdate: {}, hitUpdate: {}", nhCase, hCase, noHitUpdate, hitUpdate);


            if(hitUpdate == 1 || noHitUpdate == 1){
                // String ReqId = mvs.getReqId(Integer.parseInt(id));
                int reqCount = mvs.getReqIdCount(mvsResultRequestDto.getRequestId());

                int finalIndicateCount = mvs.getFinIndicate(mvsResultRequestDto.getRequestId());
                reqCount = reqCount-1;
                if(reqCount == finalIndicateCount ){
                    int NHCount=mvs.getCountforResponse(mvsResultRequestDto.getRequestId());
                    String regId=mvs.getRegId(Integer.parseInt(mvsResultRequestDto.getSno()),mvsResultRequestDto.getRequestId());
                    logger.info("Final verification counts - Total: {}, NoHit: {}", reqCount, NHCount);
                    if(reqCount==NHCount){
                        UINGen=1;
                        req.responseRequest(mvsResultRequestDto.getRequestId(),regId,1);
                    }
                    else{
                        req.responseRequest(mvsResultRequestDto.getRequestId(),regId,0);
                    }
                }
            }

            if (out == 1) {
                if(UINGen == 1) {
                    logger.info("Case submitted for PSN issuance.");
                    redirectAttributes.addFlashAttribute("successMessage", "CASE SENT FOR PSN ISSUANCE");
                }else {
                    logger.info("Verification result saved successfully.");
                    redirectAttributes.addFlashAttribute("successMessage", "DETAILS UPDATED SUCCESSFULLY");
                }

            } else {
                logger.error("Failed to update verification result.");
                redirectAttributes.addFlashAttribute("failureMessage", "ERROR WHILE UPDATING.");

            }
            logger.info("AuditLog - Operator: {}, RequestID: {}, Serial: {}, VerifyStatus: {}, Comment: {}, regId: {}, matchedRefId: {}",
                    user.getFirstnameEn(), mvsResultRequestDto.getRequestId(), mvsResultRequestDto.getSno(),
                    mvsResultRequestDto.getVerifyStatus(), mvsResultRequestDto.getStatusComment(), probe, candidate);
        } catch (Exception e) {
            logger.error("Exception in saveMVSL1ResultDetail: ", e);
            logger.error(logger("LevelOneController","saveMVSL1ResultDetail",getUtcTime(), e.toString()));
            redirectAttributes.addFlashAttribute("failureMessage", "ERROR WHILE UPDATING.");
        }

        return "redirect:levelOneSearch";
    }
    //@RequestMapping(value = "/saveMVSL1Result")
//public String saveMVSL1Result(ModelMap model, HttpServletRequest request,
//                              RedirectAttributes redirectAttributes, @RequestParam("sno") String id,
//                              @RequestParam("verifyStatus") String status,
//                              @RequestParam("oper1Comm") String comment) {
//    System.out.println("Successssslevel1");
//    try {
//        HttpSession session = request.getSession();
//        Userdetails user = (Userdetails) session.getAttribute("userdetails");
//        System.out.println("id"+id);
//        System.out.println("user"+user);
//        System.out.println("status"+status);
//        System.out.println("oper1comm"+comment);
//        System.out.println("user.getFirstnameEn()"+user);
//        int out = mvs.updoper1comm(Integer.parseInt(id), status, comment, user.getFirstnameEn(), "1");
//        System.out.println("out"+out);
//        if (out == 1) {
//            redirectAttributes.addFlashAttribute("successMessage", "DETAILS UPDATED SUCCESSFULLY");
//        } else {
//            redirectAttributes.addFlashAttribute("failureMessage", "ERROR WHILE UPDATING.");
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//        redirectAttributes.addFlashAttribute("failureMessage", "ERROR WHILE UPDATING.");
//    }
//    return "redirect:levelOneSearch";
//}
    @RequestMapping(value = "/leveloneSubmit", method = RequestMethod.GET)
    public String saveLevelOneData(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            HttpsURLConnection urlConnection = null;
            BufferedReader reader = null;
            OutputStream ouputStream = null;
            String text = null;
            JSONObject status = null;
            // String https_url = "https://qa3.mosip.net/v1/authmanager/authenticate/useridPwd";
            String https_url = "https://register.philsys.gov.ph/v1/authmanager/authenticate/useridPwd";
            logger.info("Sending request to authentication service at URL: {}", https_url);

           /* String jsonInputString = "{\r\n" + "  \"id\": \"string\",\r\n" + "  \"metadata\": {},\r\n"
                    + "  \"request\": {\r\n" + "    \"appId\": \"admin\",\r\n" + "    \"password\": \"mosip\",\r\n"
                    + "    \"userName\": \"110011\"\r\n" + "  },\r\n"
                    + "  \"requesttime\": \"2021-01-11T11:38:52.994Z\",\r\n" + "  \"version\": \"string\"\r\n" + "}\r\n"
                    + "";*/
            String jsonInputString ="{\"id\":\"string\",\"metadata\":{},\"request\":{\"appId\":\"admin\",\"password\":\"mosip\",\"userName\":\"110011\"},\"requesttime\":\"2021-01-11T11:38:52.994Z\",\"version\":\"string\"}";
            URL url = new URL(https_url);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");

            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            logger.info("Sending request body: {}", jsonInputString);


            ouputStream = urlConnection.getOutputStream();
            ouputStream.write(jsonInputString.getBytes());
            ouputStream.flush();

            if (urlConnection.getResponseCode() >= 200 && urlConnection.getResponseCode() < 400) {

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader((is)));
                String tmpStr = null;
                while ((tmpStr = reader.readLine()) != null) {
                    text = tmpStr;
                }
                logger.info("Response received from authentication service: {}", text);

              /*  text = "{\r\n" + "   \"id\":\"mosip.manual.verification.assignment\",\r\n"
                        + "   \"version\":\"1.0\",\r\n" + "   \"responsetime\":\"2019-02-14T12:40:59.768Z\",\r\n"
                        + "   \"response\":{\r\n" + "      \"regId\":\"27847657360002520181208123456\",\r\n"
                        + "      \"url\":\"<datashare url for regid>\",\r\n" + "      \"mvUsrId\":\"mono29\",\r\n"
                        + "      \"statusCode\":\"ASSIGNED\",\r\n" + "      \"gallery\":[\r\n" + "         {\r\n"
                        + "            \"matchedRegId\":\"27847657360002520181208123451\",\r\n"
                        + "            \"url\":\"<data share for matchedRegId>\",\r\n"
                        + "            \"matchedRefType\":\"RID\",\r\n" + "            \"reasonCode\":null\r\n"
                        + "         },\r\n" + "         {\r\n"
                        + "            \"matchedRegId\":\"27847657360002520181208123452\",\r\n"
                        + "            \"url\":\"<data share for matchedRegId>\",\r\n"
                        + "            \"matchedRefType\":\"RID\",\r\n" + "            \"reasonCode\":null\r\n"
                        + "         }\r\n" + "      ],\r\n" + "   },\r\n" + "   \"errors\":null\r\n" + "}";*/
                JSONParser parser = new JSONParser();

                Object obj = parser.parse(text);

                JSONObject array = (JSONObject) obj;

                JSONObject jsonresponse = (JSONObject) array.get("response");// 1
                logger.info("Authentication response details: {}", jsonresponse);

//                JSONArray gallery = (JSONArray) jsonresponse.get("gallery");
//
//                for (int j = 0; j < gallery.size(); j++) {
//                    JSONObject jo = (JSONObject) gallery.get(j);
//
//
//                }
//
//            }
//
            }
            else {
                logger.warn("Failed to receive a valid response. Response Code: {}");

            }

            //redirectAttributes.addFlashAttribute("successMessage", "APPROVED SUCCESSFULLY");

        } catch (Exception e) {
            logger.error("Exception occurred in saveLevelOneData: ", e);
        }

        return "redirect:/levelonesearch";

    }

    @RequestMapping(value = "/resetProcrssStatus")
    public String resetProcessStatus(ModelMap model, HttpServletRequest request,
                                     RedirectAttributes redirectAttributes, @RequestParam("sno") String id ){
        logger.info("Resetting process status for sno: {}", id);
        int sample = mvs.modifyProcessStatus(Integer.parseInt(id));
        logger.info("Process status reset result: {}", sample);
        return "/home";
    }

    @RequestMapping(value = "/checkoldrecords")
    public String checkoldrecordsFn(ModelMap model, HttpServletRequest request) throws java.text.ParseException {
        logger.info("checkOldRecordsFn called");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inputText = "2022-07-23";
        // Date dateDep = inputFormat.parse(inputText);
        RegisterManualVerification reg= mvs.findFirst1BySnoLessThan(13);



        req.responseForOldRecords(reg);
        int result=mvs.deleteAllByReqid(reg.getReqid());
        //if(result==1)

        return "redirect:/levelonesearch";
    }


    public static String logger(String contoller , String method, String time, String response) {
        String loggerJson = "{"+"\"controller\":\""+contoller+"\"," + "\"method\": \""+method+"\","+
                "\"time stamp\":"+time+","+"\"response\":\""+response+"\""+ "}";
        return loggerJson;
    }

}
