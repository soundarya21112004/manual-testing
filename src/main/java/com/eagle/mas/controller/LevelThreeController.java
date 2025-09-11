package com.eagle.mas.controller;

import com.eagle.mas.bean.GalleryBean;
import com.eagle.mas.common.ReadImage;
import com.eagle.mas.config.ConstantValue;
import com.eagle.mas.dao.ResponseMvsDao;
import com.eagle.mas.model.BioScore;
import com.eagle.mas.model.RegisterManualVerification;
import com.eagle.mas.model.ResponseMvs;
import com.eagle.mas.model.Userdetails;
import com.eagle.mas.regproc.model.AbisRequest;
import com.eagle.mas.regproc.model.AbisResponse;
import com.eagle.mas.regproc.model.RegBioRef;
import com.eagle.mas.regproc.repo.AbisRequestRepo;
import com.eagle.mas.regproc.repo.AbisResponseRepo;
import com.eagle.mas.regproc.repo.BioRefRepo;
import com.eagle.mas.repository.BioScoreRepository;
import com.eagle.mas.repository.UserCaseAssignmentRepo;
import com.eagle.mas.service.ManualVerificationService;


import com.eagle.mas.service.MvJsonService;
import com.eagle.mas.service.ResponseMvsService;
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
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@Scope("session")
public class LevelThreeController {
    @Autowired
    ResponseMvsDao ResponseMvsDao;
    @Autowired
    private  ResponseMvsService ResponseMvsService;

    @Autowired
    private ManualVerificationService mvs;

    @Autowired
    private UserdetailsService userdetailsService;

    @Autowired
    BioScoreRepository bioRepository;

    @Autowired
    MvJsonService mvJsonService;

    @Autowired
    BioRefRepo regBioRef;

    @Autowired
    AbisResponseRepo abisResponseRepo;

    @Autowired
    AbisRequestRepo abisRequestRepo;


    @Autowired
    UserCaseAssignmentRepo userCaseAssignmentRepo;

    private static final Logger logger = LoggerFactory.getLogger(LevelThreeController.class);

    File catalinaBase = new File(System.getProperty("catalina.base")).getAbsoluteFile();
    File propertyFile = new File(catalinaBase, "bin/mvs/");

    public static String getCurrentUtcTime() {

//        String utcTime;
//
//        OffsetDateTime  utcTime1 = OffsetDateTime.now(ZoneOffset.UTC);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");

        String convertDate = dateFormat.format(new Date());

        return convertDate;
    }

    //    @PostMapping("/testPose")
//    public String responseRequest(@RequestParam("ReqId") String ReqId,@RequestParam("regId") String regId,@RequestParam("result") int result){
    public String responseRequest(String ReqId,String regId,int result){
        try {
            JSONArray ja = new JSONArray();
            int i=0,j=0;
            ArrayList<RegisterManualVerification> roles = (ArrayList<RegisterManualVerification>) mvs.listForCandiat(ReqId);

            int count = 0;
            if(result==0){

                org.json.JSONObject fraudJson = new org.json.JSONObject();
                org.json.JSONObject candidateList = new org.json.JSONObject();
                org.json.JSONArray candidatesArray = new org.json.JSONArray();

                fraudJson.put("returnValue","0");
                fraudJson.put("requestId",ReqId);
                fraudJson.put("responsetime",getCurrentUtcTime());
                fraudJson.put("id","mosip.manual.adjudication.adjudicate");
                fraudJson.put("candidateList",candidateList);
                candidateList.put("candidates",candidatesArray);
                candidateList.put("count",roles.size());

                for (i=0;i < roles.size();i++){
                    RegisterManualVerification manualVerification = roles.get(i);
                    org.json.JSONObject candidate = new org.json.JSONObject();
                    org.json.JSONObject analytics = new org.json.JSONObject();
                    candidatesArray.put(i,candidate);
                    candidate.put("analytics",analytics);
                    candidate.put("referenceId",manualVerification.getMatchedRefId());
                    analytics.put("primaryOperatorID",manualVerification.getOp1userId());
                    analytics.put("secondaryOperatorID",manualVerification.getOp2userId());
                    analytics.put("primaryOperatorComments",manualVerification.getOp1Comment());
                    analytics.put("secondaryOperatorComments",manualVerification.getOp2Comment());
                }


                ResponseMvs responseObj = new ResponseMvs();
                int sno = ResponseMvsService.getResponseSno();
                responseObj.setSno(sno);
                responseObj.setOrid(regId);
                responseObj.setResponse_Json(fraudJson.toString());
                responseObj.setStatus("NEW");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
                String convertDate = dateFormat.format(new Date());
                Date date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'").parse(convertDate);
                responseObj.setCrDate(date1);
                ResponseMvsDao.saveAll(responseObj);
            }
            if(result==1) {
                ResponseMvs responseObj = new ResponseMvs();
                int sno = ResponseMvsService.getResponseSno();
                JSONObject obj2 = new JSONObject();
                obj2.put("count", 0);
                obj2.put("candidates", ja);
                JSONObject obj1 = new JSONObject();
                obj1.put("id", "mosip.manual.adjudication.adjudicate");
                obj1.put("requestId", ReqId);
                obj1.put("responsetime", getCurrentUtcTime());
                obj1.put("returnValue", result);
                obj1.put("candidateList", obj2);
                responseObj .setSno(sno);
                responseObj.setOrid(regId);
                responseObj.setResponse_Json(obj1.toString());
                responseObj.setStatus("NEW");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
                String convertDate = dateFormat.format(new Date());
                Date date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'").parse(convertDate);
                responseObj.setCrDate(date1);
                ResponseMvsDao.saveAll(responseObj);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return "redirect:errorPage";
        }
        return "sample";
    }

    @RequestMapping(value = "/levelthreeSearch", method = RequestMethod.GET)
    public String showHomePage(ModelMap model, HttpServletRequest request) {
        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("userID")==null) {
                return "redirect:redirectlogin";
            }
        }
        catch(Exception e){
            logger.error("Error occurred while fetching session in /levelthreeSearch: {}", e.getMessage(), e);
        }

        Set<String> operators = userdetailsService.getOperator();
        model.addAttribute("operators", operators);
//        sortedRoles = mvs.listOfRidsForL3();
        return "levelThreeSearch";
    }

    @RequestMapping(value="/loadLevelThreeData", method= RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> loadLevelThreeData(ModelMap model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userID") == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching session in /loadLevelThreeData: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        ArrayList<RegisterManualVerification> data = (ArrayList<RegisterManualVerification>) mvs.listOfRidsForL3(PageRequest.of(0, ConstantValue.MAXRESULT));
        Map<String, Object> response = new HashMap<>();
        response.put("data", data); // Indicate if more data is available

        return ResponseEntity.ok(response);
    }


    @RequestMapping(value = "/loadDataThree", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> loadMoreFilterDataThree(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String operator1,
            @RequestParam String operator2,
            @RequestParam String dateType,
            @RequestParam int offSet,
            HttpServletRequest request) throws java.text.ParseException {

        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userID") == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching session in /loadDataThree: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> response = new HashMap<>();
        List<RegisterManualVerification> data;
        if(startDate.isEmpty()){
            data = mvs.listOfRidsForL3(PageRequest.of(offSet,ConstantValue.MAXRESULT));
            logger.info("Successfully fetched data without date filter: {}", data.size());
            response.put("data", data);
        }
        else{
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            // Process the data here and return a response
            logger.debug("Operator1 is empty: {}", operator1.isEmpty());
            logger.debug("Operator2 is empty: {}", operator2.isEmpty());

            if(operator1.isEmpty() && operator2.isEmpty()){
                logger.debug("Fetching data without operator filters");
                data = mvs.listOfRidsForL33(start, end, dateType, offSet);
            }
            else if (operator2.isEmpty()) {
                logger.debug("Fetching data with operator1 filter");
                data = mvs.listOfRidsForL3Op1(start, end, operator1, dateType, offSet);

            }
            else if (operator1.isEmpty()) {
                logger.debug("Fetching data with operator2 filter");
                data = mvs.listOfRidsForL3Op2(start, end, operator2, dateType, offSet);
            }
            else{
                logger.debug("Fetching data with both operator filters");
                data = mvs.listOfRidsForL3(start, end, operator1, operator2, dateType,offSet);
            }

            logger.info("Successfully fetched filtered data: {}", data.size());
            response.put("data", data);
        }
        // Indicate if more data is available

        return ResponseEntity.ok(response); // Properly return data with HTTP 200 status
    }


    @RequestMapping(value = "/levelthreedetails", method = RequestMethod.GET)
    public String showLevelThreeDetailsPage(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (redirectAttributes.equals(true)) {
            redirectAttributes.addFlashAttribute("successMessage", "APPROVED SUCCESSFULLY");
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", "REJECTED SUCCESSFULLY");
        }

        return "levelthreedetails";
    }


    @RequestMapping(value = "/levelthreeSearchByName", method = RequestMethod.GET)
    public String levelthreeSearchByName(ModelMap model, HttpServletRequest request, @RequestParam("id") String id, @RequestParam("probe") String probe,
                                         @RequestParam("candidate") String candidate,@RequestParam("requestId") String requestId,@RequestParam("op1Comment")String op1Comment,
                                         @RequestParam("op1verifyStatus") String op1verifyStatus,@RequestParam("op2Comment") String op2Comment,@RequestParam("op2verifyStatus")String op2verifyStatus,
                                         @RequestParam("supervisorComment") String supervisorComment,@RequestParam("supervisorVerifyStatus")String supervisorVerifyStatus, RedirectAttributes redirectAttributes
    ) {

        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("userID")==null){
                return "redirect:redirectlogin";
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        model.addAttribute("Can", candidate);
        model.addAttribute("Prob", probe);
        model.addAttribute("id", id);
        model.addAttribute("requestId", requestId);
//        if(matchingScore.equals(null))
//            matchingScore="0";
//        matchingScore=matchingScore+"%";
        model.addAttribute("requestId", requestId);
        model.addAttribute("op1Comment", op1Comment);
        model.addAttribute("op2Comment", op2Comment);
        model.addAttribute("supervisorComment", supervisorComment);
        if (op1verifyStatus.equals("hit"))
            model.addAttribute("op1verifyStatus","Hit");
        else if (op1verifyStatus.equals("nohit"))

            model.addAttribute("op1verifyStatus","No Hit");

        if (op2verifyStatus.equalsIgnoreCase("hit"))
            model.addAttribute("op2verifyStatus","Hit");
        else if (op2verifyStatus.equalsIgnoreCase("nohit"))
            model.addAttribute("op2verifyStatus","No Hit");

        if (supervisorVerifyStatus.equalsIgnoreCase("hit"))
            model.addAttribute("supervisorVerifyStatus","Hit");
        else if (supervisorVerifyStatus.equalsIgnoreCase("nohit"))
            model.addAttribute("supervisorVerifyStatus","No Hit");


        String pathname = new FileSystemResource("").getFile().getAbsolutePath();
        if (probe != null) {
            try {
                List<GalleryBean> leftfingerProb = new ArrayList<GalleryBean>();
                List<GalleryBean> rightfingerProb = new ArrayList<GalleryBean>();
                List<GalleryBean> irisProbScore = new ArrayList<GalleryBean>();
                GalleryBean beanProbe = new GalleryBean();
                JSONParser jsonParser = new JSONParser();
                String  imag = null;
                byte[] imageData = null;
                String probFaceImage=null;
                JSONParser jsonParser1 = new JSONParser();
                String base64StringPOI;
                String pdfFileI = null;
                String base64StringPOA;
                String base64StringPOE;
                String pdfFileA = null;
                String pdfFileE = null;
                //   String pathname1 = new FileSystemResource("").getFile().getAbsolutePath();
                int count = mvs.countAllByRegId(probe);
                model.addAttribute("count",count-1);

                String responseText;
                String bioRefID;
                try{
                    BioScore score = bioRepository.findFirstByRegIDAndMatchedRefIdAndResponseTextNotNullOrderByCrTimesRegIdDesc(probe,probe);
                    BioScore getBioRefId = bioRepository.findFirstByMatchedRefIdAndBioRefIdIsNotNull(candidate);

//                    score.setResponseText("{\"id\":\"mosip.abis.identify\",\"requestId\":\"bc39a755-ab30-4d54-b0fb-1a050d0d4112\",\"returnValue\":\"1\",\"responsetime\":\"2021-01-22T00:37:50.679Z\",\"candidateList\":{\"count\":\"1\",\"candidates\":[{\"referenceId\":\"825e5ec4-b990-408f-93b5-faf6f9a0de28\",\"analytics\":{\"internalScore\":\"3145.0\",\"rank\":\"2\"},\"modalities\":[{\"biometricType\":\"IIR\",\"analytics\":{\"internalScore\":\"3295.0\"}}]}]},\"analytics\":{\"wasAdjudicated\":true,\"candidates\":[{\"referenceId\":\"825e5ec4-b990-408f-93b5-faf6f9a0de28\",\"internalScore\":\"3145.0\",\"consistency\":\"Consistent\",\"adjudicationDetails\":[{\"decision\":\"NO_HIT\",\"operator\":\"soquindo\",\"comment\":\"Both Iris and fingerprints of the probe and candidate were found to be different\"},{\"decision\":\"NO_HIT\",\"operator\":\"ncabauatan\",\"comment\":\"Both Iris and fingerprints of the probe and candidate were found to be different\"}]}]}}");
//                    getBioRefId.setBioRefId("825e5ec4-b990-408f-93b5-faf6f9a0de28");

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
                            if (getBioRefId.getBioRefId().equals(getCCandidate.get("referenceId"))){
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
                    e.printStackTrace();
                }
                try  {
                    //                    String jsonString=;
                    JSONObject jsonObject1 =mvJsonService.getJson(probe);
                    /*
                     * Getting documents from mvs Response JSON
                     * Start
                     * */
                    /*Reading Document from JSON PROBE Start*/

                    /*List<String> documentsList = Arrays.asList("proofOfIdentity","proofOfAddress","proofOfException");
                    Map<String, String> modelAttributeMap = Map.of(
                            "proofOfIdentity", "reportPDFPOI",
                            "proofOfAddress", "reportPDFPOA",
                            "proofOfException", "reportPDFPOE"
                    );
                    try{
                        Map<String, String> docResponse = (Map<String, String>) jsonObject1.get("documents");
                        if(docResponse != null){
                            documentsList.stream().filter(docResponse::containsKey).forEach(
                                    key -> model.addAttribute(modelAttributeMap.get(key),
                                            docResponse.get(key)));
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }*/
                    try{
                        if(jsonObject1.get("documents")!=null) {
                            JSONObject jsonObjectResponse = (JSONObject) jsonObject1.get("documents");
                            if(jsonObjectResponse.get("proofOfIdentity")!=null) {
                                base64StringPOI = (String) jsonObjectResponse.get("proofOfIdentity");

//                                pdfFileI = "data:application/pdf;base64," + base64StringPOI;
                                pdfFileI = base64StringPOI;
                            }
                            if(jsonObjectResponse.get("proofOfAddress")!=null) {
                                base64StringPOA = (String) jsonObjectResponse.get("proofOfAddress");
//                                pdfFileA = "data:application/pdf;base64," + base64StringPOA;
                                pdfFileA = base64StringPOA;
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
                        e.printStackTrace();
                    }
                    /*


                     * Getting dcouments from mvs JSON
                     * End
                     * */
                    /*Reading Document from JSON PROBE  End*/

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
                        beanProbe.setPresentBarangay(valueFrm);}

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
//                            beanCan.setPobCountry(valueFrm);
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
                        beanProbe.setPresentCountry(valueFrm);}

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
                        beanProbe.setYearOfBirth(yearOfBirth);
                    }

                    if(jsonObject1.get("metaInfo")!=null){
                        data1 =  (String) jsonObject1.get("metaInfo");
                        JSONObject jsonObjmet = (JSONObject) jsonParser1.parse(data1);
                        valueFrm= (String) jsonObjmet.get("registrationId");
                        beanProbe.setRegistrationId(valueFrm);
                        String crDate= (String) jsonObjmet.get("creationDate");
                        String[] dateprobe= crDate.split("T");
                        beanProbe.setCreationdate(dateprobe[0]);
                        beanProbe.setCreationtime("T"+dateprobe[1]);
                        data1 =  (String) jsonObjmet.get("operationsData");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        String  officer= (String) jsonObject4.get("value");
                        beanProbe.setOfficer(officer);
                        if( jsonObjmet.get("metaData") != null){
                            data1 = (String) jsonObjmet.get("metaData");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject regCenterCodeVal = (JSONObject) jsonArray1.get(6);
                            beanProbe.setRegCenterId((String) regCenterCodeVal.get("value"));
                        }
                    }
                    byte[] decodedBytes = Base64.getUrlDecoder().decode((String) jsonObject1.get("biometrics"));
                    String decodedBioXml = new String(decodedBytes);
//                    try{
//                        //jsonObject = (JSONObject) jsonParser.pserarse(reader2);
//
//                        //String pathname = new FileSystemResource("").getFile().getAbsolutePath();

//                        OutputStream out = new FileOutputStream(pathname+"/"+"datajsonProgram.xml");
//                      //  OutputStream out = new FileOutputStream(pathname+"/"+"/src/main/webapp/WEB-INF/jsondata/datajsonProgram.xml");
//                        out.write(decodedBioXml.getBytes());
//                        out.close();
//                    }
//                    catch(Exception e) {
//                        e.printStackTrace();
//                    }
                    //String pathname = new FileSystemResource("").getFile().getAbsolutePath();
//                    //File file = new File(pathname+"/bin/mvs/datajsonProgram.xml");
//                    File file = new File(pathname+"/"+"datajsonProgram.xml");
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document document = (Document) db.parse(new InputSource(new StringReader(decodedBioXml)));
//                    Document document = (Document) db.parse(new InputSource(new StringReader(decodedBioXml)));
                    NodeList nodeList = document.getElementsByTagName("BIR");
                    for (int i = 0; i < nodeList.getLength(); ++i) {
                        Node node = nodeList.item(i);

                        Element tElement = (Element)node;
                        String type=tElement.getElementsByTagName("Type").item(1).getTextContent();
                        if (type.equalsIgnoreCase("IRIS")) {
                            String Subtypeiris=tElement.getElementsByTagName("Subtype").item(0).getTextContent();

                            String irisBiodata=(String)tElement.getElementsByTagName("BDB").item(0).getTextContent();

                            imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                            imag = new ReadImage().covertasImage(imageData,146);

//                            imageData = IrisDecoder.convertFaceISO19794_6_2011ToImage(Base64.getDecoder().decode(irisBiodata.toString()));
//                            System.out.println("Iris ImageData :"+imageData);
//                            //imageData = IrisDecoder.convertFaceISO19794_6_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
//                            // imageData = cDecoder.eISO19794_6_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
//                            BufferedImage image = JDeli.read(imageData);
//                           // String pathname = new FileSystemResource("").getFile().getAbsolutePath();
//                            File opFile = new File(pathname +"/"+ "/mvs/" + probe+i + ".jpg");
//                            JDeli.write(image, "jpg", opFile);
//                            String enCodeFile = null;
//                            FileInputStream tmpInputStream = new FileInputStream(opFile);
//                            byte[] viewImage = new byte[(int) opFile.length()];
//                            tmpInputStream.read(viewImage);
//                            File file1 = new File("D:\\demo\\Jdeli Read result fin- iris\\iris-result.txt");
//                            OutputStream stream = new FileOutputStream(file1);
//                            stream.write(viewImage);
//                            enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                            imag = "data:image/jpg;base64," + enCodeFile;
//                            tmpInputStream.close();
                            GalleryBean irscoresProb = new GalleryBean();
                            irscoresProb.setScore (tElement.getElementsByTagName("Score").item(0).getTextContent());
                            irscoresProb.setUrl (tElement.getElementsByTagName("Subtype").item(0).getTextContent());
                            // JSONObject qualityJson = (JSONObject) jsonObjValue.get("Quality");
                            // irscores.setScore(qualityJson.get("Score").toString());
                            // irscores.setUrl(jsonObjValue.get("Subtype").toString());
                            irscoresProb.setProbeIrisImage(imag);
                            if(irscoresProb.getUrl().equalsIgnoreCase("left")){
                                if(irisProbScore.size()<1)
                                    irisProbScore.add(irscoresProb);
                                else if(irisProbScore.get(0).getUrl().equalsIgnoreCase("right"))
                                    irisProbScore.add(irscoresProb);
                            }
                            else if(irscoresProb.getUrl().equalsIgnoreCase("right")){
                                if(irisProbScore.size()<1)
                                    irisProbScore.add(irscoresProb);
                                else if(irisProbScore.get(0).getUrl().equalsIgnoreCase("left"))
                                    irisProbScore.add(irscoresProb);
                            }
                        }else if (type.equalsIgnoreCase("Finger")) {
                            String Subtype=tElement.getElementsByTagName("Subtype").item(0).getTextContent();
                            imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                            imag = new ReadImage().covertasImage(imageData,138);

//                            imageData = FingerDecoder.convertFingerISO19794_4_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                            System.out.println("Finger imageData :"+imageData.toString());
//                            BufferedImage image = JDeli.read(imageData);
//                            File opFile = new File(pathname +"/"+ "/mvs/" + probe + ".jpg");
//                            JDeli.write(image, "jpg", opFile);
//                            String enCodeFile = null;
//                            FileInputStream tmpInputStream = new FileInputStream(opFile);
//                            byte[] viewImage = new byte[(int) opFile.length()];
//                            tmpInputStream.read(viewImage);
//                            File file1 = new File("D:\\demo\\Jdeli Read result fin- iris\\finger-result.txt");
//                            FileOutputStream stream1 = new FileOutputStream(file1);
//                            stream1.write(viewImage);
//                            enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                            imag = "data:image/jpg;base64," + enCodeFile;
//                            tmpInputStream.close();
                            GalleryBean scoreProbe = new GalleryBean();
                            scoreProbe.setScore (tElement.getElementsByTagName("Score").item(0).getTextContent());
                            scoreProbe.setUrl (Subtype);
//                            Subtype.compareToIgnoreCase("Left MiddleFinger");
//                            System.out.println("Left MiddleFinger"+Subtype.compareToIgnoreCase("Left MiddleFinger"));
//                            beanProbe.setScoreProbe(scoreProbe.setScore (tElement.getElementsByTagName("Score").item(0).getTextContent()));

                            //  JSONObject qualityJson = (JSONObject) jsonObjValue.get("Quality");
                            //scoreProbe.setScore(qualityJson.get("Score").toString());
                            // scoreProbe.setUrl(jsonObjValue.get("Subtype").toString());
                            scoreProbe.setFingerImage(imag);
                            if (Subtype.contains("Left")) {
                                leftfingerProb.add(scoreProbe);
                            } else {
                                rightfingerProb.add(scoreProbe);
                            }

                        } else if (type.equalsIgnoreCase("FACE")) {
                            imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                            probFaceImage = new ReadImage().covertasImage(imageData,136);

//                            imageData = FaceDecoder.convertFaceISO19794_5_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
//                            imageData = FaceDecoder.convertFaceISO19794_5_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                            BufferedImage image = JDeli.read(imageData);
//                            //String pathname = new FileSystemResource("").getFile().getAbsolutePath();
//                            File opFile = new File(pathname +"/"+ "/mvs/" + probe + ".jpg");
//                            JDeli.write(image, "jpg", opFile);
//                            String enCodeFile = null;
//                            FileInputStream tmpInputStream = new FileInputStream(opFile);
//                            byte[] viewImage = new byte[(int) opFile.length()];
//                            tmpInputStream.read(viewImage);
//                            enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                            probFaceImage = "data:image/jpg;base64," + enCodeFile;
//                            //  model.addAttribute("mosipimage", imag);
//                            tmpInputStream.close();
                        }
                        // }
                    }
                    model.addAttribute("probeDemoFields", beanProbe);
                    model.addAttribute("leftfingerProb", leftfingerProb);
                    model.addAttribute("rightfingerProb", rightfingerProb);
                    model.addAttribute("irisProbScore", irisProbScore);
                    model.addAttribute("probFaceImage", probFaceImage);



                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (candidate != null) {
            try {
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
                // String pathname = new FileSystemResource("").getFile().getAbsolutePath(); (FileReader reader = new FileReader(pathname+"/mvs/samplemv2.json"))
                try  {
//                    String jsonString=;
                    JSONObject jsonObject1 =mvJsonService.getJson(candidate);

                    /*Reading Document from JSON CANDIDATE Start*/
                    try {
                        if (jsonObject1.get("documents") != null) {
                            JSONObject jsonObjectResponse = (JSONObject) ((JSONObject) jsonObject1).get("documents");
                            if (jsonObjectResponse.get("proofOfIdentity") != null) {
                                base64StringCPOI = (String) jsonObjectResponse.get("proofOfIdentity");
//                                pdfFileCPOI = "data:application/pdf;base64," + base64StringCPOI;
                                pdfFileCPOI = base64StringCPOI;
                            }
                            if (jsonObjectResponse.get("proofOfAddress") != null) {
                                base64StringCPOA = (String) jsonObjectResponse.get("proofOfAddress");
//                                pdfFileCPOA = "data:application/pdf;base64," + base64StringCPOA;
                                pdfFileCPOA = base64StringCPOA;
                            }
                            if (jsonObjectResponse.get("proofOfException") != null) {
                                base64StringCPOE = (String) jsonObjectResponse.get("proofOfException");
                                model.addAttribute("reportPDFPOECan", base64StringCPOE);
//                                pdfFileCPOE = "data:application/pdf;base64," + base64StringCPOE;
                            }
                            model.addAttribute("reportPDFPOICan", pdfFileCPOI);
                            model.addAttribute("reportPDFPOACan", pdfFileCPOA);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    /*Reading Document from JSON CANDIDATE  End*/
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
//                        beanCan.setPobCountry(valueFrm);
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
//                    model.addAttribute("CanDemoFields", beanCan);
                    if(jsonObject1.get("metaInfo")!=null){
                        data1 =  (String) jsonObject1.get("metaInfo");
                        JSONObject jsonObjmet = (JSONObject) jsonParser1.parse(data1);
                        valueFrm= (String) jsonObjmet.get("registrationId");
                        beanCan.setRegistrationId(valueFrm);

                        String crDate= (String) jsonObjmet.get("creationDate");

                        String[] dates= crDate.split("T");
                        beanCan.setCreationdate(crDate);
                        beanCan.setCreationdate(dates[0]);
                        beanCan.setCreationtime("T"+dates[1]);
//                        beanCan.setOfficer("110011");
//                        data1 = (String) jsonObjmet.get("operationsData");
//                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
//                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
//                        JSONObject jsonObject5 = (JSONObject) jsonArray1.get(1);
//                        JSONObject jsonObject6 = (JSONObject) jsonArray1.get(2);
//                        String valueFrm4 = (String) jsonObject4.get("value");
//                        String valueFrm5 = (String) jsonObject5.get("value");
//                        String valueFrm6 = (String) jsonObject6.get("value");

//                        beanCan.setOfficer((String) jsonObject4.get("value"));

                        if(jsonObjmet.get("operationsData")!=null) {
                            data1 = (String) jsonObjmet.get("operationsData");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                            JSONObject jsonObject5 = (JSONObject) jsonArray1.get(1);
                            JSONObject jsonObject6 = (JSONObject) jsonArray1.get(2);
                            String valueFrm4 = (String) jsonObject4.get("value");
                            String valueFrm5 = (String) jsonObject5.get("value");
                            String valueFrm6 = (String) jsonObject6.get("value");

                            beanCan.setOfficer((String) jsonObject4.get("value"));
                        }

                        if( jsonObjmet.get("metaData") != null){
                            data1 = (String) jsonObjmet.get("metaData");
                            jsonArray1 = (JSONArray) jsonParser.parse(data1);
                            JSONObject regCenterCodeVal = (JSONObject) jsonArray1.get(6);
                            beanCan.setRegCenterId((String) regCenterCodeVal.get("value"));
                        }
                    }

                    byte[] decodedBytes = Base64.getUrlDecoder().decode((String) jsonObject1.get("biometrics"));
                    String decodedBioXml = new String(decodedBytes);

//                    try{
//                        //jsonObject = (JSONObject) jsonParser.parse(reader2);
//
//                      //  String pathname = new FileSystemResource("").getFile().getAbsolutePath();
//                        OutputStream out = new FileOutputStream(pathname+"/mvs/canjsonProgram.xml");
//                        out.write(decodedBioXml.getBytes());
//                        out.close();
//                    }
//                    catch(Exception e) {
//                        e.printStackTrace();
//                    }
//                    File file = new File(pathname+"/mvs/canjsonProgram.xml");
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document document = db.parse(new InputSource(new StringReader(decodedBioXml)));
                    NodeList nodeList = document.getElementsByTagName("BIR");

                    for (int i = 0; i < nodeList.getLength(); ++i) {
                        Node node = nodeList.item(i);

                        //  if (node.getNodeType()== Node.ELEMENT_NODE) {
                        Element tElement = (Element)node;
                        String type=tElement.getElementsByTagName("Type").item(1).getTextContent();
                        if (type.equalsIgnoreCase("IRIS")) {
                            String Subtypeiris=tElement.getElementsByTagName("Subtype").item(0).getTextContent();

                            imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                            CanFaceImage = new ReadImage().covertasImage(imageData,146);

//                            imageData = IrisDecoder.convertFaceISO19794_6_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                            //imageData = IrisDecoder.convertFaceISO19794_6_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
//                            // imageData = cDecoder.eISO19794_6_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
//                            BufferedImage image = JDeli.read(imageData);
//                            File opFile = new File(pathname + "/mvs/" + probe+i + ".jpg");
//                            JDeli.write(image, "jpg", opFile);
//                            String enCodeFile = null;
//                            FileInputStream tmpInputStream = new FileInputStream(opFile);
//                            byte[] viewImage = new byte[(int) opFile.length()];
//                            tmpInputStream.read(viewImage);
//                            enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                            CanFaceImage = "data:image/jpg;base64," + enCodeFile;
//                            tmpInputStream.close();
                            GalleryBean irscores = new GalleryBean();
                            irscores.setScore (tElement.getElementsByTagName("Score").item(0).getTextContent());
                            irscores.setUrl (tElement.getElementsByTagName("Subtype").item(0).getTextContent());

                            irscores.setProbeIrisImage(CanFaceImage);
                            if(irscores.getUrl().equalsIgnoreCase("left")){
                                if(irisCanScore.size()<1)
                                    irisCanScore.add(irscores);
                                else if(irisCanScore.get(0).getUrl().equalsIgnoreCase("right"))
                                    irisCanScore.add(irscores);
                            }
                            else if(irscores.getUrl().equalsIgnoreCase("right")){
                                if(irisCanScore.size()<1)
                                    irisCanScore.add(irscores);
                                else if(irisCanScore.get(0).getUrl().equalsIgnoreCase("left"))
                                    irisCanScore.add(irscores);
                            }
                        }else if (type.equalsIgnoreCase("Finger")) {
                            String Subtype=tElement.getElementsByTagName("Subtype").item(0).getTextContent();

                            imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                            CanFaceImage = new ReadImage().covertasImage(imageData,138);


//                            imageData = FingerDecoder.convertFingerISO19794_4_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                            BufferedImage image = JDeli.read(imageData);
//                            //String pathname = new FileSystemResource("").getFile().getAbsolutePath();
//                            File opFile = new File(pathname + "/mvs/" + probe + ".jpg");
//                            JDeli.write(image, "jpg", opFile);
//                            String enCodeFile = null;
//                            FileInputStream tmpInputStream = new FileInputStream(opFile);
//                            byte[] viewImage = new byte[(int) opFile.length()];
//                            tmpInputStream.read(viewImage);
//                            enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                            CanFaceImage = "data:image/jpg;base64," + enCodeFile;
//                            tmpInputStream.close();
                            GalleryBean score = new GalleryBean();
                            //GalleryBean scoreProbe = new GalleryBean();
                            score.setScore (tElement.getElementsByTagName("Score").item(0).getTextContent());
                            score.setUrl (Subtype);

                            score.setFingerImage(CanFaceImage);
                            if (Subtype.contains("Left")) {
                                leftfingerCan.add(score);
                            } else {
                                rightfingerCan.add(score);
                            }
                        } else if (type.equalsIgnoreCase("FACE")) {
                            //imageData = FaceDecoder.convertFaceISO19794_5_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
                            imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                            CanFaceImage = new ReadImage().covertasImage(imageData,136);

//                            imageData = FaceDecoder.convertFaceISO19794_5_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                            BufferedImage image = JDeli.read(imageData);
//                            File opFile = new File(pathname + "/mvs/" + probe + ".jpg");
//                            JDeli.write(image, "jpg", opFile);
//                            String enCodeFile = null;
//                            FileInputStream tmpInputStream = new FileInputStream(opFile);
//                            byte[] viewImage = new byte[(int) opFile.length()];
//                            tmpInputStream.read(viewImage);
//                            enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                            CanFaceImage = "data:image/jpg;base64," + enCodeFile;
//                            //  model.addAttribute("mosipimage", imag);
//                            tmpInputStream.close();
                        }
                        // }
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
//        redirectAttributes.addFlashAttribute("LevelThreeSearchByNameModel", new HashMap<>(model));
        request.getSession().setAttribute("LevelThreeSearchByNameModel", new HashMap<>(model));
        return "redirect:levelThreeDetail";

    }

    @RequestMapping(value = "/levelThreeDetail")
    public String redirectingLevelThreeDetail(Model model, HttpServletRequest request) {
//        Map<String, Object> details = (Map<String, Object>) model.getAttribute("LevelThreeSearchByNameModel");
        Map<String, Object> details = (Map<String, Object>) request.getSession().getAttribute("LevelThreeSearchByNameModel");

        model.addAllAttributes(details);
        return "mvsLevelThreeDetail";
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


    @RequestMapping(value = "/saveMVSL3Result")
    public String saveMVSL3ResultDetail(ModelMap model, HttpServletRequest request,
                                        RedirectAttributes redirectAttributes, @RequestParam("sno") String id,
                                        @RequestParam("supervisorVerifyStatus") String statusthree,
                                        @RequestParam("supervisorComment") String commentthree,@RequestParam("requestId")String requestId) {

        try {
            HttpSession session = request.getSession();
            Userdetails user = (Userdetails) session.getAttribute("userdetails");
            commentthree=commentthree.trim();
            int out = 1;//mvs.updateRIDThree(Integer.parseInt(id), statusthree, commentthree, user.getFirstnameEn(), "3");

            if (out == 1) {
                redirectAttributes.addFlashAttribute("successMessage", "DETAILS UPDATED SUCCESSFULLY");
            } else {
                redirectAttributes.addFlashAttribute("failureMessage", "ERROR WHILE UPDATING.");


            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failureMessage", "ERROR WHILE UPDATING.");

        }
        return "redirect:/levelthreeSearch";
    }
    public void responseForOldRecords(RegisterManualVerification reg){
        try {
            JSONArray ja = new JSONArray();
            int i=0,j=0;
            int count = 0;
            ResponseMvs responseObj = new ResponseMvs();
            int sno = ResponseMvsService.getResponseSno();
            JSONObject obj2 = new JSONObject();
            obj2.put("count", 0);
            obj2.put("candidates", ja);
            JSONObject obj1 = new JSONObject();
            obj1.put("id", "mosip.manual.adjudication.adjudicate");
            obj1.put("requestId", reg.getReqid());
            obj1.put("responsetime", getCurrentUtcTime());
            obj1.put("returnValue", 2);
            obj1.put("candidateList", obj2);
            responseObj.setSno(sno);
            responseObj.setOrid(reg.getRegId());
            responseObj.setResponse_Json(obj1.toString());
            responseObj.setStatus("NEW");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            String convertDate = dateFormat.format(new Date());
            Date date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'").parse(convertDate);
            responseObj.setCrDate(date1);
            ResponseMvsDao.saveAll(responseObj);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}