package com.eagle.mas.controller;

import com.eagle.mas.bean.GalleryBean;
import com.eagle.mas.common.ReadImage;
import com.eagle.mas.model.BioScore;
import com.eagle.mas.model.RegisterManualVerification;
import com.eagle.mas.model.Userdetails;
import com.eagle.mas.repository.BioScoreRepository;
import com.eagle.mas.service.ManualVerificationService;
import com.eagle.mas.service.MvJsonService;
import com.eagle.mas.service.ReportsService;
import org.jose4j.base64url.Base64Url;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("session")
public class LevelTwoController {

    // private static final NativeJSON JSON = null ;
    @Autowired
    private ManualVerificationService mvs;

    @Autowired
    MvJsonService mvJsonService;

    @Autowired
    LevelThreeController req;

    @Autowired
    BioScoreRepository bioRepository;

    File catalinaBase = new File(System.getProperty("catalina.base")).getAbsoluteFile();
    File propertyFile = new File(catalinaBase, "bin/mvs/");
    private String Right;
    Logger logger = (Logger) LoggerFactory.getLogger(LoginController.class);

    public String getUtcTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");

        String convertDate = dateFormat.format(new Date());
        return convertDate;

    }

    @RequestMapping(value = "/levelTwoSearch", method = RequestMethod.GET)
    public String showHomePage(ModelMap model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("userID")==null){
                return "redirect:loginPage";
            }
            Userdetails user = (Userdetails) session.getAttribute("userdetails");
            System.out.println("user" + user.getUserid());

            ArrayList<RegisterManualVerification> roles = (ArrayList<RegisterManualVerification>) mvs.listOfRidsForL2();
            model.addAttribute("galleryList", roles);
            logger.info(logger("LevelTwoController", "showHomePage", getUtcTime(), "userId " + user.getUserid()));
        }catch (Exception e){
            logger.error(logger("LevelTwoController","showHomePage",getUtcTime(), e.toString()));
            return "redirect:errorPage";

        }

        return "levelTwoSearch";

    }

    @RequestMapping(value = "/leveltwodetails", method = RequestMethod.GET)
    public String showLevelTwoDetailsPage(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (redirectAttributes.equals(true)) {
            redirectAttributes.addFlashAttribute("successMessage", "APPROVED SUCCESSFULLY");
        } else {
            redirectAttributes.addFlashAttribute("faliureMessage", "REJECTED SUCCESSFULLY");
        }

        return "leveltwodetails";
    }


    @RequestMapping(value = "/leveltwoSearchByName", method = RequestMethod.GET)
    public String leveltwoSearchByName(ModelMap model, HttpServletRequest request, @RequestParam("id") String id, @RequestParam("probe") String probe,
                                       @RequestParam("candidate") String candidate,@RequestParam("requestId") String requestId,@RequestParam("op1Comment")String op1Comment,
                                       @RequestParam("op1verifyStatus") String op1verifyStatus,@RequestParam("op2verifyStatus") String op2verifyStatus,@RequestParam("op2Comment")String op2Comment
    ) {

        HttpSession session = request.getSession();
        session.setAttribute("regId",probe );
        session.setAttribute("matchId",candidate);

        try {
            if (session.getAttribute("userID") == null) {
                return "redirect:loginPage";
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

        model.addAttribute("Can", candidate);
        model.addAttribute("Prob", probe);
        model.addAttribute("id", id);
        model.addAttribute("requestId", requestId);
        System.out.println("id"+id);
        System.out.println("probe"+probe);
        System.out.println("candidate"+candidate);
        System.out.println("op1verifyStatus"+op1verifyStatus);
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
                System.out.println("probe*********"+probe);
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
                model.addAttribute("count",count-1);
                try{

                    BioScore score = bioRepository.findFirstByRegIDAndMatchedRefIdAndResponseTextNotNull(probe,probe);
                    BioScore getBioRefId = bioRepository.findFirstByMatchedRefIdAndBioRefIdIsNotNull(candidate);
//                    score.setResponseText("{\"id\":\"mosip.abis.identify\",\"requestId\":\"bc9b3ddb-8ee0-4c1a-ab4c-8fec48c66ef2\",\"returnValue\":\"1\",\"responsetime\":\"2022-07-14T10:29:39.284Z\",\"candidateList\":{\"count\":\"1\",\"candidates\":[{\"referenceId\":\"84240b4d-f61b-42fc-979f-94d99b7f2949\",\"analytics\":{\"internalScore\":\"22130.0\",\"rank\":\"2\"},\"modalities\":[{\"biometricType\":\"IIR\",\"analytics\":{\"internalScore\":\"16635.0\"}},{\"biometricType\":\"FIR\",\"analytics\":{\"internalScore\":\"22280.0\"}}]}]}}");
                    System.out.println("Response Text :"+score.getResponseText());
//                    getBioRefId.setBioRefId("84240b4d-f61b-42fc-979f-94d99b7f2949");
                    System.out.println("BIOref_id :"+getBioRefId.getBioRefId());
                    org.json.JSONObject matchedScore = new org.json.JSONObject(score.getResponseText());
                    System.out.println("matchedScore"+matchedScore.toString());
                    org.json.JSONObject matchedCandidatesList = matchedScore.getJSONObject("candidateList");
                    org.json.JSONArray matchedCandidates = matchedCandidatesList.getJSONArray("candidates");

                    for (int i=0; i < matchedCandidates.length(); i++){
                        org.json.JSONObject getCandidate = matchedCandidates.getJSONObject(i);
                        if (getBioRefId.getBioRefId().equals(getCandidate.get("referenceId"))){
                            System.out.println("Test refid :" +getCandidate.get("referenceId"));
                            org.json.JSONArray modalities = getCandidate.getJSONArray("modalities");
                            for (int j=0; j < modalities.length(); j++){
                                org.json.JSONObject matchedDetails = modalities.getJSONObject(j);
                                org.json.JSONObject analytics = matchedDetails.getJSONObject("analytics");
                                if (matchedDetails.get("biometricType") != null && matchedDetails.get("biometricType").equals("FIR")){
                                    model.addAttribute("fir",analytics.get("internalScore"));
                                    System.out.println("FIR :"+analytics.get("internalScore"));
                                }
                                if (matchedDetails.get("biometricType") != null && matchedDetails.get("biometricType").equals("IIR")){
                                    model.addAttribute("iir",analytics.get("internalScore"));
                                    System.out.println("IIR :"+analytics.get("internalScore"));
                                }
                                if (matchedDetails.get("biometricType") != null && matchedDetails.get("biometricType").equals("FID")){
                                    model.addAttribute("fid",analytics.get("internalScore"));
                                    System.out.println("FID :"+analytics.get("internalScore"));
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
                                System.out.println("Test refid comment section:" +getCCandidate.get("referenceId"));
                                org.json.JSONArray adjudicationDetailsComment = getCCandidate.getJSONArray("adjudicationDetails");
                                // for (int j=0; j < adjudicationDetailsComment.length(); j++){
                                //   org.json.JSONObject matchedDetails = adjudicationDetailsComment.getJSONObject(j);
                                org.json.JSONObject matchedDetails = adjudicationDetailsComment.getJSONObject(0);
                                String comment =(String) matchedDetails.get("comment");
                                org.json.JSONObject matchedDetails1 = adjudicationDetailsComment.getJSONObject(1);
                                String comment1 = (String) matchedDetails1.get("comment");
                                model.addAttribute("comment1ABIS",comment1);
                                model.addAttribute("commentABIS",comment);
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
                    JSONObject jsonObject1 = (JSONObject) jsonParser1.parse(mvJsonService.getProbJson(probe,requestId));
                    /*Reading Document from JSON PROBE Start*/
                    try{
                        if(jsonObject1.get("documents")!=null) {
                            JSONObject jsonObjectResponse = (JSONObject) ((JSONObject) jsonObject1).get("documents");
                            if(jsonObjectResponse.get("proofOfIdentity")!=null) {
                                base64StringPOI = (String) jsonObjectResponse.get("proofOfIdentity");
                                pdfFileI = "data:application/pdf;base64," + base64StringPOI;
                            }
                            if(jsonObjectResponse.get("proofOfAddress")!=null) {
                                base64StringPOA = (String) jsonObjectResponse.get("proofOfAddress");
                                pdfFileA = "data:application/pdf;base64," + base64StringPOA;
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
                        logger.error(logger("LevelOneController","leveloneSearchByName",getUtcTime(), e.toString()));
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
                        System.out.println("firstName" + valueFrm);
                        beanProbe.setFirstName(valueFrm);
                    }
                    if(jsonObj1.get("presentAddressLine1")!=null){
                        data1 =  (String) jsonObj1.get("presentAddressLine1");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        jsonObject3 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject3.get("value");
                        System.out.println("presentAddressLine1"+valueFrm);
                        beanProbe.setPresentAddressLine1(valueFrm);
                    }

                    if(jsonObj1.get("presentBarangay")!=null){
                        data1 =  (String) jsonObj1.get("presentBarangay");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        jsonObject3 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject3.get("value");
                        System.out.println("presentBarangay"+valueFrm);
                        beanProbe.setPresentBarangay(valueFrm);}

                    if(jsonObj1.get("presentProvince")!=null){
                        data1 =  (String) jsonObj1.get("presentProvince");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("presentProvince"+valueFrm);
                        beanProbe.setPresentProvince(valueFrm);}

                    String can_poa = "";
                    if(jsonObj1.get("pobCity")!=null){
                        data1 =  (String) jsonObj1.get("pobCity");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        can_poa = valueFrm +", ";
                        System.out.println("pobCity"+valueFrm);
//                            beanCan.setPobCountry(valueFrm);
                    }

                    if(jsonObj1.get("pobCountry")!=null){
                        data1 =  (String) jsonObj1.get("pobCountry");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("pobCountry"+valueFrm);
                        can_poa = can_poa + valueFrm;
                        beanProbe.setPobCountry(can_poa);}

                    if(jsonObj1.get("presentCountry")!=null){
                        data1 =  (String) jsonObj1.get("presentCountry");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("presentCountry"+valueFrm);
                        beanProbe.setPresentCountry(valueFrm);}

                    if(jsonObj1.get("gender")!=null){
                        data1 =  (String) jsonObj1.get("gender");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("gender"+valueFrm);
                        beanProbe.setGender(valueFrm);}

                    if(jsonObj1.get("presentCity")!=null){
                        data1 =  (String) jsonObj1.get("presentCity");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("presentCity"+valueFrm);
                        beanProbe.setPresentCity(valueFrm);}

                    if(jsonObj1.get("middleName")!=null){
                        data1 =  (String) jsonObj1.get("middleName");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("middleName"+valueFrm);
                        beanProbe.setMiddleName(valueFrm);}

                    if(jsonObj1.get("lastName")!=null){
                        data1 =  (String) jsonObj1.get("lastName");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("lastName"+valueFrm);
                        beanProbe.setLastName(valueFrm);}

                    if(jsonObj1.get("suffix")!=null){
                        data1 =  (String) jsonObj1.get("suffix");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("suffix"+valueFrm);
                        beanProbe.setSuffix(valueFrm);}

                    if(jsonObj1.get("dateOfBirth")!=null){
                        String data7 = (String) jsonObj1.get("dateOfBirth");
                        System.out.println("jsonDate******" + data7);
                        String[] split = data7.split("/");
                        String monthOfBirth = split[1].toString();
                        System.out.println("monthOfBirth" + monthOfBirth);
                        String dayOfBirth = split[2].substring(0, 2).toString();
                        System.out.println("dayOfBirth" + dayOfBirth);
                        String yearOfBirth = split[0].toString();
                        System.out.println("yearOfBirth" + yearOfBirth);
                        beanProbe.setMonthOfBirth(monthOfBirth);
                        beanProbe.setDayOfBirth(dayOfBirth);
                        beanProbe.setYearOfBirth(yearOfBirth);}

                    if(jsonObject1.get("metaInfo")!=null){
                        data1 =  (String) jsonObject1.get("metaInfo");
                        System.out.println("Mta"+data1);
                        JSONObject jsonObjmet = (JSONObject) jsonParser1.parse(data1);
                        valueFrm= (String) jsonObjmet.get("registrationId");
                        beanProbe.setRegistrationId(valueFrm);
                        System.out.println("registrationId************"+valueFrm);

                        String crDate= (String) jsonObjmet.get("creationDate");
                        System.out.println("creation date************"+crDate);
                        beanProbe.setCreationdate(crDate);
                        System.out.println(crDate);

                        //   if(jsonObject1.get("operationsData")!=null){
                        data1 =  (String) jsonObjmet.get("operationsData");
                        System.out.println("Data"+data1);

                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        JSONObject jsonObject5 = (JSONObject) jsonArray1.get(1);
                        JSONObject jsonObject6 = (JSONObject) jsonArray1.get(2);
//                        JSONObject jsonObject = (JSONObject) jsonObject4.get(0);
                        String  officer= (String) jsonObject4.get("value");
                        String  valueFrm4= (String) jsonObject4.get("value");
                        String valueFrm5= (String) jsonObject5.get("value");
                        String valueFrm6= (String) jsonObject6.get("value");
                        System.out.println("Json4*****"+jsonObject4);
                        System.out.println("Json5*****"+jsonObject5);
                        System.out.println("Json6*****"+jsonObject6);
                        System.out.println("officerId*****"+officer);
                        beanProbe.setOfficer(officer);
                        //crDate= (String) jsonObjmet.get("creationDate");
                        System.out.println("creation date************"+crDate);
                        String[] dateprobes=crDate.split("T");
                        beanProbe.setCreationdate(dateprobes[0]);
                        beanProbe.setCreationtime("T"+dateprobes[1]);

                    }

                    byte[] decodedBytes = Base64.getUrlDecoder().decode((String) jsonObject1.get("biometrics"));
                    String decodedBioXml = new String(decodedBytes);
//
//                    try{
//                        //jsonObject = (JSONObject) jsonParser.parse(reader2);
//
//                        OutputStream out = new FileOutputStream(pathname+"/mvs/datajsonProgram.xml");
//                        out.write(decodedBioXml.getBytes());
//                        out.close();
//                    }
//                    catch(Exception e) {
//                        logger.error(logger("LevelTwoController","leveltwoSearchByName",getUtcTime(), e.toString()));
//                    }
//                    File file = new File(pathname+"/mvs/"+"datajsonProgram.xml");
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    System.out.println("Root getNodeName: ");

                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document document = (Document) db.parse(new InputSource(new StringReader(decodedBioXml)));
                    System.out.println("Root getNodeName: " + document.getDocumentElement().getNodeName());
                    NodeList nodeList = document.getElementsByTagName("BIR");
                    System.out.println( " nodeListelement: "+nodeList);
                    for (int i = 0; i < nodeList.getLength(); ++i) {
                        Node node = nodeList.item(i);
//                        System.out.println("\nNode Name :"
//                                + node.getNodeName());
                        //  if (node.getNodeType()== Node.ELEMENT_NODE) {
                        Element tElement = (Element)node;
                        String type=tElement.getElementsByTagName("Type").item(1).getTextContent();
                        System.out.println("Root type: " + type);
                        if (type.equalsIgnoreCase("IRIS")) {
                            String Subtypeiris=tElement.getElementsByTagName("Subtype").item(0).getTextContent();
                            System.out.println("Subtypeiris: " + Subtypeiris);
                            imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                            imag = new ReadImage().covertasImage(imageData,146);
//                            imageData = IrisDecoder.convertFaceISO19794_6_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
                            //imageData = IrisDecoder.convertFaceISO19794_6_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
                            // imageData = cDecoder.eISO19794_6_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
//                            BufferedImage image = JDeli.read(imageData);
//                            File opFile = new File(pathname+"/mvs/" + probe+i + ".jpg");
//                            JDeli.write(image, "jpg", opFile);
//                            String enCodeFile = null;
//                            FileInputStream tmpInputStream = new FileInputStream(opFile);
//                            byte[] viewImage = new byte[(int) opFile.length()];
//                            tmpInputStream.read(viewImage);
//                            enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                            imag = "data:image/jpg;base64," + enCodeFile;
//                            tmpInputStream.close();
                            GalleryBean irscoresProb = new GalleryBean();
                            if(Subtypeiris.equalsIgnoreCase("Left")){
                                String leftiris = irscoresProb.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("leftiris*****"+leftiris);
                                beanBioProbe.setLeftiris(leftiris); }
                            if(Subtypeiris.equalsIgnoreCase("Right")){
                                String rightiris = irscoresProb.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("rightiris*****"+rightiris);
                                beanBioProbe.setRightiris(rightiris); }
                            // JSONObject qualityJson = (JSONObject) jsonObjValue.get("Quality");
                            // irscores.setScore(qualityJson.get("Score").toString());
                            // irscores.setUrl(jsonObjValue.get("Subtype").toString());
                            irscoresProb.setProbeIrisImage(imag);
                            if(irisProbScore.size()<2)
                                irisProbScore.add(irscoresProb);
                        }else if (type.equalsIgnoreCase("Finger")) {
                            String Subtype=tElement.getElementsByTagName("Subtype").item(0).getTextContent();
                            System.out.println("Subtype: " + Subtype);
                            imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                            imag = new ReadImage().covertasImage(imageData,138);
//                            imageData = FingerDecoder.convertFingerISO19794_4_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                            BufferedImage image = JDeli.read(imageData);
//                            File opFile = new File(pathname+ "/mvs/" + probe + ".jpg");
//                            JDeli.write(image, "jpg", opFile);
//                            String enCodeFile = null;
//                            FileInputStream tmpInputStream = new FileInputStream(opFile);
//                            byte[] viewImage = new byte[(int) opFile.length()];
//                            tmpInputStream.read(viewImage);
//                            enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                            imag = "data:image/jpg;base64," + enCodeFile;
//                            tmpInputStream.close();
                            GalleryBean score = new GalleryBean();
                            if(Subtype.equalsIgnoreCase("Left MiddleFinger")){
                                String leftmiddlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("leftmiddlefinger*****"+leftmiddlefinger);
                                beanBioProbe.setLeftmiddlefinger(leftmiddlefinger); }
                            if(Subtype.equalsIgnoreCase("Left IndexFinger")){
                                String leftindexfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("leftindexfinger"+leftindexfinger);
                                beanBioProbe.setLeftindexfinger(leftindexfinger);}
                            if(Subtype.equalsIgnoreCase("Left LittleFinger")){
                                String leftlittlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("leftlittlefinger"+leftlittlefinger);
                                beanBioProbe.setLeftlittlefinger(leftlittlefinger);}
                            if(Subtype.equalsIgnoreCase("Left RingFinger")){
                                String leftringfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("leftringfinger"+leftringfinger);
                                beanBioProbe.setLeftringfinger(leftringfinger);}
                            if(Subtype.equalsIgnoreCase("Left Thumb")){
                                String leftthumb = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("leftthumb"+leftthumb);
                                beanBioProbe.setLeftthumb(leftthumb);}
                            if(Subtype.equalsIgnoreCase("Right MiddleFinger")){
                                String rightmiddlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("rightmiddlefinger"+rightmiddlefinger);
                                beanBioProbe.setRightmiddlefinger(rightmiddlefinger);}
                            if(Subtype.equalsIgnoreCase("Right IndexFinger")){
                                String rightindexfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("rightindexfinger"+rightindexfinger);
                                beanBioProbe.setRightindexfinger(rightindexfinger);}
                            if(Subtype.equalsIgnoreCase("Right LittleFinger")){
                                String rightlittlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("rightlittlefinger"+rightlittlefinger);
                                beanBioProbe.setRightlittlefinger(rightlittlefinger);}
                            if(Subtype.equalsIgnoreCase("Right RingFinger")){
                                String rightringfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("rightringfinger"+rightringfinger);
                                beanBioProbe.setRightringfinger(rightringfinger);}
                            if(Subtype.equalsIgnoreCase("Right Thumb")){
                                String rightthumb = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("rightthumb"+rightthumb);
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

                        } else if (type.equalsIgnoreCase("FACE")) {
                            //imageData = FaceDecoder.convertFaceISO19794_5_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
                            imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                            probFaceImage = new ReadImage().covertasImage(imageData,136);
//                            imageData = FaceDecoder.convertFaceISO19794_5_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                            BufferedImage image = JDeli.read(imageData);
//                            File opFile = new File(pathname + "/mvs/" + probe + ".jpg");
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
//                    model.addAttribute("uiFields", beanProbe);
//                    model.addAttribute("leftfingerScore", leftfingerScore);
//                    model.addAttribute("rightfingerScore", rightfingerScore);
//                   model.addAttribute("irisScore", irisScore);
//                   model.addAttribute("mosipimage", mosipImage);
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
                System.out.println("candidate*********"+candidate);
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

//                    String jsonString=; (FileReader reader = new FileReader(pathname+"/mvs/" +"samplemv2.json"))
                    JSONObject jsonObject1 = (JSONObject) jsonParser1.parse(mvJsonService.getJson(probe,candidate,requestId));
                    /*Reading Document from JSON CANDIDATE Start*/
                    try {
                        if (jsonObject1.get("documents") != null) {
                            JSONObject jsonObjectResponse = (JSONObject) ((JSONObject) jsonObject1).get("documents");
                            if (jsonObjectResponse.get("proofOfIdentity") != null) {
                                base64StringCPOI = (String) jsonObjectResponse.get("proofOfIdentity");
                                pdfFileCPOI = "data:application/pdf;base64," + base64StringCPOI;
                            }
                            if (jsonObjectResponse.get("proofOfAddress") != null) {
                                base64StringCPOA = (String) jsonObjectResponse.get("proofOfAddress");
                                pdfFileCPOA = "data:application/pdf;base64," + base64StringCPOA;
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
                        logger.error(logger("LevelTwoController","leveltwoSearchByName",getUtcTime(), e.toString()));
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
                        System.out.println("firstName" + valueFrm);
                        beanCan.setFirstName(valueFrm);
                    }
                    if(jsonObj1.get("presentAddressLine1")!=null){
                        data1 =  (String) jsonObj1.get("presentAddressLine1");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        jsonObject3 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject3.get("value");
                        System.out.println("presentAddressLine1"+valueFrm);
                        beanCan.setPresentAddressLine1(valueFrm);
                    }

                    if(jsonObj1.get("presentBarangay")!=null){
                        data1 =  (String) jsonObj1.get("presentBarangay");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        jsonObject3 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject3.get("value");
                        System.out.println("presentBarangay"+valueFrm);
                        beanCan.setPresentBarangay(valueFrm);}

                    if(jsonObj1.get("presentProvince")!=null){
                        data1 =  (String) jsonObj1.get("presentProvince");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("presentProvince"+valueFrm);
                        beanCan.setPresentProvince(valueFrm);}
                    String can_poa = "";
                    if(jsonObj1.get("pobCity")!=null){
                        data1 =  (String) jsonObj1.get("pobCity");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        can_poa = valueFrm+", ";
                        System.out.println("pobCity"+valueFrm);
//                        beanCan.setPobCountry(valueFrm);
                    }

                    if(jsonObj1.get("pobCountry")!=null){
                        data1 =  (String) jsonObj1.get("pobCountry");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("pobCountry"+valueFrm);
                        can_poa = can_poa + valueFrm;
                        beanCan.setPobCountry(can_poa);}

                    if(jsonObj1.get("presentCountry")!=null){
                        data1 =  (String) jsonObj1.get("presentCountry");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("presentCountry"+valueFrm);
                        beanCan.setPresentCountry(valueFrm);}

                    if(jsonObj1.get("gender")!=null){
                        data1 =  (String) jsonObj1.get("gender");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("gender"+valueFrm);
                        beanCan.setGender(valueFrm);}

                    if(jsonObj1.get("presentCity")!=null){
                        data1 =  (String) jsonObj1.get("presentCity");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("presentCity"+valueFrm);
                        beanCan.setPresentCity(valueFrm);}

                    if(jsonObj1.get("middleName")!=null){
                        data1 =  (String) jsonObj1.get("middleName");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("middleName"+valueFrm);
                        beanCan.setMiddleName(valueFrm);}

                    if(jsonObj1.get("lastName")!=null){
                        data1 =  (String) jsonObj1.get("lastName");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("lastName"+valueFrm);
                        beanCan.setLastName(valueFrm);}

                    if(jsonObj1.get("suffix")!=null){
                        data1 =  (String) jsonObj1.get("suffix");
                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        valueFrm= (String) jsonObject4.get("value");
                        System.out.println("suffix"+valueFrm);
                        beanCan.setSuffix(valueFrm);}

                    if(jsonObj1.get("dateOfBirth")!=null){
                        String data7 = (String) jsonObj1.get("dateOfBirth");
                        System.out.println("jsonDate******" + data7);
                        String[] split = data7.split("/");
                        String monthOfBirth = split[1].toString();
                        System.out.println("monthOfBirth" + monthOfBirth);
                        String dayOfBirth = split[2].substring(0, 2).toString();
                        System.out.println("dayOfBirth" + dayOfBirth);
                        String yearOfBirth = split[0].toString();
                        System.out.println("yearOfBirth" + yearOfBirth);
                        beanCan.setMonthOfBirth(monthOfBirth);
                        beanCan.setDayOfBirth(dayOfBirth);
                        beanCan.setYearOfBirth(yearOfBirth);}
//                    model.addAttribute("CanDemoFields", beanCan);
                    if(jsonObject1.get("metaInfo")!=null){
                        data1 =  (String) jsonObject1.get("metaInfo");
                        System.out.println("Mta"+data1);
                        JSONObject jsonObjmet = (JSONObject) jsonParser1.parse(data1);
                        valueFrm= (String) jsonObjmet.get("registrationId");
                        beanCan.setRegistrationId(valueFrm);
                        System.out.println("registrationId************"+valueFrm);

                        String crDate= (String) jsonObjmet.get("creationDate");
                        System.out.println("creation date************"+crDate);
                        beanCan.setCreationdate(crDate);

                        //   if(jsonObject1.get("operationsData")!=null){
                        data1 =  (String) jsonObjmet.get("operationsData");
                        System.out.println("Data"+data1);

                        jsonArray1 = (JSONArray) jsonParser.parse(data1);
                        JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);
                        JSONObject jsonObject5 = (JSONObject) jsonArray1.get(1);
                        JSONObject jsonObject6 = (JSONObject) jsonArray1.get(2);
//                        JSONObject jsonObject = (JSONObject) jsonObject4.get(0);
                        String  officer= (String) jsonObject4.get("value");
                        String  valueFrm4= (String) jsonObject4.get("value");
                        String valueFrm5= (String) jsonObject5.get("value");
                        String valueFrm6= (String) jsonObject6.get("value");
                        System.out.println("Json4*****"+jsonObject4);
                        System.out.println("Json5*****"+jsonObject5);
                        System.out.println("Json6*****"+jsonObject6);
                        System.out.println("officerId*****"+officer);
                        beanCan.setOfficer(officer);
                        String[] datecandi=crDate.split("T");
                        beanCan.setCreationdate(datecandi[0]);
                        beanCan.setCreationtime("T"+datecandi[1]);

                    }


                    byte[] decodedBytes = Base64.getUrlDecoder().decode((String) jsonObject1.get("biometrics"));
                    String decodedBioXml = new String(decodedBytes);
//                    try{
//                        //jsonObject = (JSONObject) jsonParser.parse(reader2);
//
//                     //   OutputStream out = new FileOutputStream("E:\\Final MVS\\canjsonProgram.xml");
//                        OutputStream out = new FileOutputStream(pathname+"\\mvs\\canjsonProgram.xml");
//                        out.write(decodedBioXml.getBytes());
//                        out.close();
//                    }
//                    catch(Exception e) {
//                        e.printStackTrace();
//                    }
//                    File file = new File(pathname+"/mvs/" +"canjsonProgram.xml");
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    System.out.println("Root getNodeName: ");

                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document document = (Document) db.parse(new InputSource(new StringReader(decodedBioXml)));
                    System.out.println("Root getNodeName: " + document.getDocumentElement().getNodeName());
                    NodeList nodeList = document.getElementsByTagName("BIR");
                    System.out.println( " nodeListelement: "+nodeList);
                    for (int i = 0; i < nodeList.getLength(); ++i) {
                        Node node = nodeList.item(i);
//                        System.out.println("\nNode Name :"
//                                + node.getNodeName());
                        //  if (node.getNodeType()== Node.ELEMENT_NODE) {
                        Element tElement = (Element)node;
                        String type=tElement.getElementsByTagName("Type").item(1).getTextContent();
                        System.out.println("Root type: " + type);
                        if (type.equalsIgnoreCase("IRIS")) {
                            String Subtypeiris=tElement.getElementsByTagName("Subtype").item(0).getTextContent();
                            System.out.println("Subtypeiris: " + Subtypeiris);
                            imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                            CanFaceImage = new ReadImage().covertasImage(imageData,146);
//                            imageData = IrisDecoder.convertFaceISO19794_6_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                            imageData = IrisDecoder.convertFaceISO19794_6_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
//                             imageData = cDecoder.eISO19794_6_2011ToImage(Base64.getDecoder().decode(jsonvalue.toString()));
//                            BufferedImage image = JDeli.read(imageData);
//                            File opFile = new File(pathname+ "/mvs/" + probe+i + ".jpg");
//                            JDeli.write(image, "jpg", opFile);
//                            String enCodeFile = null;
//                            FileInputStream tmpInputStream = new FileInputStream(opFile);
//                            byte[] viewImage = new byte[(int) opFile.length()];
//                            tmpInputStream.read(viewImage);
//                            enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                            CanFaceImage = "data:image/jpg;base64," + enCodeFile;
//                            tmpInputStream.close();
                            GalleryBean irscores = new GalleryBean();
                            if(Subtypeiris.equalsIgnoreCase("Left")){
                                String leftiris = irscores.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("leftiris*****"+leftiris);
                                beanBioCan.setLeftiris(leftiris); }
                            if(Subtypeiris.equalsIgnoreCase("Right")){
                                String rightiris = irscores.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("rightiris*****"+rightiris);
                                beanBioCan.setRightiris(rightiris); }
                            // JSONObject qualityJson = (JSONObject) jsonObjValue.get("Quality");
                            // irscores.setScore(qualityJson.get("Score").toString());
                            // irscores.setUrl(jsonObjValue.get("Subtype").toString());
                            irscores.setProbeIrisImage(CanFaceImage);
                            if(irisCanScore.size()<2)
                                irisCanScore.add(irscores);
                        }else if (type.equalsIgnoreCase("Finger")) {
                            String Subtype=tElement.getElementsByTagName("Subtype").item(0).getTextContent();
                            System.out.println("Subtype: " + Subtype);
                            imageData = Base64Url.decode(tElement.getElementsByTagName("BDB").item(0).getTextContent());
                            CanFaceImage = new ReadImage().covertasImage(imageData,138);
//                            imageData = FingerDecoder.convertFingerISO19794_4_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                            BufferedImage image = JDeli.read(imageData);
////                            String pathname = new FileSystemResource("").getFile().getAbsolutePath();
//                            File opFile = new File(propertyFile + probe + ".jpg");
//                            JDeli.write(image, "jpg", opFile);
//                            String enCodeFile = null;
//                            FileInputStream tmpInputStream = new FileInputStream(opFile);
//                            byte[] viewImage = new byte[(int) opFile.length()];
//                            tmpInputStream.read(viewImage);
//                            enCodeFile = Base64.getEncoder().encodeToString(viewImage);
//                            CanFaceImage = "data:image/jpg;base64," + enCodeFile;
//                            tmpInputStream.close();
                            GalleryBean score = new GalleryBean();
                            if(Subtype.equalsIgnoreCase("Left MiddleFinger")){
                                String leftmiddlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("leftmiddlefinger*****"+leftmiddlefinger);
                                beanBioCan.setLeftmiddlefinger(leftmiddlefinger); }
                            if(Subtype.equalsIgnoreCase("Left IndexFinger")){
                                String leftindexfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("leftindexfinger"+leftindexfinger);
                                beanBioCan.setLeftindexfinger(leftindexfinger);}
                            if(Subtype.equalsIgnoreCase("Left LittleFinger")){
                                String leftlittlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("leftlittlefinger"+leftlittlefinger);
                                beanBioCan.setLeftlittlefinger(leftlittlefinger);}
                            if(Subtype.equalsIgnoreCase("Left RingFinger")){
                                String leftringfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("leftringfinger"+leftringfinger);
                                beanBioCan.setLeftringfinger(leftringfinger);}
                            if(Subtype.equalsIgnoreCase("Left Thumb")){
                                String leftthumb = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("leftthumb"+leftthumb);
                                beanBioCan.setLeftthumb(leftthumb);}
                            if(Subtype.equalsIgnoreCase("Right MiddleFinger")){
                                String rightmiddlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("rightmiddlefinger"+rightmiddlefinger);
                                beanBioCan.setRightmiddlefinger(rightmiddlefinger);}
                            if(Subtype.equalsIgnoreCase("Right IndexFinger")){
                                String rightindexfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("rightindexfinger"+rightindexfinger);
                                beanBioCan.setRightindexfinger(rightindexfinger);}
                            if(Subtype.equalsIgnoreCase("Right LittleFinger")){
                                String rightlittlefinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("rightlittlefinger"+rightlittlefinger);
                                beanBioCan.setRightlittlefinger(rightlittlefinger);}
                            if(Subtype.equalsIgnoreCase("Right RingFinger")){
                                String rightringfinger = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("rightringfinger"+rightringfinger);
                                beanBioCan.setRightringfinger(rightringfinger);}
                            if(Subtype.equalsIgnoreCase("Right Thumb")){
                                String rightthumb = score.setScore(tElement.getElementsByTagName("Score").item(0).getTextContent());
                                System.out.println("rightthumb"+rightthumb);
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
//                            imageData = FaceDecoder.convertFaceISO19794_5_2011ToImage(Base64.getDecoder().decode(tElement.getElementsByTagName("BDB").item(0).getTextContent()));
//                            BufferedImage image = JDeli.read(imageData);
//                            File opFile = new File(pathname+"/mvs/" + probe + ".jpg");
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
//                    model.addAttribute("uiFields", beanProbe);
//                    model.addAttribute("leftfingerScore", leftfingerScore);
//                    model.addAttribute("rightfingerScore", rightfingerScore);
//                   model.addAttribute("irisScore", irisScore);
//                   model.addAttribute("mosipimage", mosipImage);
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
        return "mvsLevelTwoDetail";

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
                                  RedirectAttributes redirectAttributes, @RequestParam("sno") String id,
                                  @RequestParam("verifyStatus") String status,
                                  @RequestParam("statusComment") String comment,
                                        @RequestParam("requestId")String requestId) {
        System.out.println("Successssslevel2");
        try {
            HttpSession session = request.getSession();
            Userdetails user = (Userdetails) session.getAttribute("userdetails");
            System.out.println("user"+user);
            System.out.println("id"+id);
            System.out.println("user"+user);
            System.out.println("status"+status);
            System.out.println("requestId"+requestId);
            System.out.println("statusComment"+comment);
            System.out.println("user.getFirstnameEn()"+user);
            String probe= (String) session.getAttribute("regId" );
            String canditate= (String) session.getAttribute("matchId");
            int out = mvs.updateRIDTwo(Integer.parseInt(id), status, comment, user.getUserid(), user.getFirstnameEn(), requestId,"2");
            System.out.println("out"+out);
//            if(status.equals("decidelater"))
//                out = mvs.updateRIDTwo(Integer.valueOf(id), status, comment, user.getFirstnameEn(), "1");
//            else
//                out = mvs.updateRIDTwo(Integer.valueOf(id), status, comment, user.getFirstnameEn(), "2");
            int check = mvs.supervisorVerifiedNohit(Integer.parseInt(id));
            int hitCheck = mvs.supervisorVerifiedHit(Integer.parseInt(id));
            int hitUpdate = 0;
            int noHitUpdate = 0;
            if(check == 1){
                noHitUpdate = mvs.operatorUpdateNohit(Integer.parseInt(id));
            }
            if(hitCheck == 1){
                hitUpdate = mvs.operatorUpdateHit(Integer.parseInt(id));
            }
            if(hitUpdate == 1 || noHitUpdate == 1){
                String ReqId = mvs.getReqId(Integer.parseInt(id));
                int reqCount = mvs.getReqIdCount(ReqId);
                int finalIndicateCount = mvs.getFinIndicate(ReqId);
                reqCount = reqCount-1;
                if(reqCount == finalIndicateCount ){
                    int NHCount=mvs.getCountforResponse(ReqId);
                    String regId=mvs.getRegId(Integer.parseInt(id),requestId);
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
            logger.info(logger("LevelTwoController","saveMVSL1ResultDetail",getUtcTime(),
                    "OperatorName:"+user.getFirstnameEn()+","+"Command :"+comment+","+"Status :"+status+","+"regId :"+probe+","+"matchedRefId  :"+canditate));
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failureMessage", "ERROR WHILE UPDATING.");

        }
        return "redirect:/levelTwoSearch";
    }




    public static String logger(String contoller , String method, String time, String response) {
        String loggerJson = "{"+"\"controller\":\""+contoller+"\"," + "\"method\": \""+method+"\","+
                "\"time stamp\":"+time+","+"\"response\":\""+response+"\""+ "}";
        return loggerJson;
    }

}