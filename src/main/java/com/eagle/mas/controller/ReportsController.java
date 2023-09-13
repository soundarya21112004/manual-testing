package com.eagle.mas.controller;


import com.eagle.mas.bean.GalleryBean;
import com.eagle.mas.model.RegisterManualVerification;
import com.eagle.mas.model.Userdetails;
import com.eagle.mas.service.ReportsDetailService;
import com.eagle.mas.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ReportsController {

    @Autowired
    ReportsService reportsService;
    @Autowired
    ReportsDetailService reportsDetailService;

    @RequestMapping(value= "/dupReportsController")
    public String duplicateReports(ModelMap model){
        ArrayList<GalleryBean> dupCount = new ArrayList<GalleryBean>();

        ArrayList<String> dups= reportsService.getRegid();
        for (String d : dups){
            GalleryBean dupBean= new GalleryBean();
            String s=d.split(",")[0];
            String s1=d.split(",")[1];
            dupBean.setRegId(s);
            dupBean.setCount(s1);
            dupCount.add(dupBean);
        }

        model.addAttribute("dupReports",dupCount);
        return "supervisorReport";
    }


    @RequestMapping(value = "/operatorController", method = RequestMethod.GET)
    public String reportsOPerator(HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userID") == null) {
                return "redirect:loginPage";
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        System.out.println("testing");
        return "reportsOperator";
    }
    @RequestMapping(value = "/commonReportsController", method = RequestMethod.GET)
    public String showAllReports(ModelMap model, HttpServletRequest request) {

        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userID") == null) {
                return "redirect:loginPage";
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        int  unverifiedCount =  reportsService.allUnverifiedReports();
        int  verifiedCount =  reportsService.allverifiedReports();
        int  deadlockedCount =  reportsService.deadlockedReports();
        int  verifieddeadlockedCount =  reportsService.verifiedDeadlockedReports();
        int  hitCasesdCount =  reportsService.hitcasesReports();
        int  hitCasesFraudCount =  reportsService.hitCasesFraudReports();
        int  nohitDecisionsCount =  reportsService.nohitDecisionsReports();
        int  hitDecisionsCount =  reportsService.hitDecisionsReports();
        int  demographicCount =  reportsService.demographicReports();
        int  biometricCount =  reportsService.biometricReports();


        model.addAttribute("allUnverifiedReports", unverifiedCount);
        model.addAttribute("allverifiedReports", verifiedCount);
        model.addAttribute("deadlockedReports", deadlockedCount);
        model.addAttribute("verifiedDeadlockedReports", verifieddeadlockedCount);
        model.addAttribute("hitcasesReports", hitCasesdCount);
        model.addAttribute("hitCasesFraudReports", hitCasesFraudCount);
        model.addAttribute("nohitDecisionsReports", nohitDecisionsCount);
        model.addAttribute("hitDecisionsReports", hitDecisionsCount);
        model.addAttribute("demographicReports", demographicCount);
        model.addAttribute("biometricReports", biometricCount);

        return "reportsCommon";
    }

    @RequestMapping(value = "/operatorReportsController", method = RequestMethod.GET)
    public String showOperatorReports(ModelMap model, HttpServletRequest request) {
        System.out.println("operator controller");
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userID") == null) {
                return "redirect:loginPage";
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        int  unverifiedCount =  reportsService.unverifiedOperator();
        int  verifiedCount =  reportsService.verifiedOperator();
        int  deadlockedCount =  reportsService.deadlockedOperator();
//        int  verifieddeadlockedCount =  reportsServ
//
//
//
//
//
//
//       ice.verifiedDeadlockedReports();
        int  hitCasesdCount =  reportsService.hitcasesOperator();
//        int  hitCasesFraudCount =  reportsService.hitCasesFraudReports();
        int  nohitDecisionsCount =  reportsService.nohitDecisionsOperator();
        int  hitDecisionsCount =  reportsService.hitDecisionsOperator();
        int  demographicCount =  reportsService.demographicOperator();
        int  biometricCount =  reportsService.biometricOperator();
//
        model.addAttribute("hide", true);
        model.addAttribute("operatorDetail", true);
        model.addAttribute("unverified", unverifiedCount);
        model.addAttribute("verified", verifiedCount);
        model.addAttribute("deadlocked", deadlockedCount);
//        model.addAttribute("verifiedDeadlockedReports", verifieddeadlockedCount);
        model.addAttribute("hitcases", hitCasesdCount);
//        model.addAttribute("hitCasesFraudReports", hitCasesFraudCount);
        model.addAttribute("nohitDecisions", nohitDecisionsCount);
        model.addAttribute("hitDecisions", hitDecisionsCount);
        model.addAttribute("demographic", demographicCount);
        model.addAttribute("biometric", biometricCount);


        return "reportsOperator";
    }


    @RequestMapping(value = "/supervisorReportsController", method = RequestMethod.GET)
    public String showsupervisorReports(ModelMap model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userID") == null) {
                return "redirect:loginPage";
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        int  unverifiedCount =  reportsService.unverifiedSupervisor();
        int  verifiedCount =  reportsService.verifiedSupervisor();
        int  deadlockedCount =  reportsService.deadlockedSupervisor();
        int  verifieddeadlockedCount =  reportsService.verifiedDeadlockedSupervisor();
        int  hitCasesdCount =  reportsService.hitcasesSupervisor();
        int  hitCasesFraudCount =  reportsService.hitCasesFraudSupervisor();
        int  nohitDecisionsCount =  reportsService.nohitDecisionsSupervisor();
        int  hitDecisionsCount =  reportsService.hitDecisionsSupervisor();
        int  demographicCount =  reportsService.demographicSupervisor();
        int  biometricCount =  reportsService.biometricSupervisor();
//
        model.addAttribute("hide", false);
        model.addAttribute("supervisorDetail", true);
        model.addAttribute("unverified", unverifiedCount);
        model.addAttribute("verified", verifiedCount);
        model.addAttribute("deadlocked", deadlockedCount);
        model.addAttribute("verifiedDeadlocked", verifieddeadlockedCount);
        model.addAttribute("hitcases", hitCasesdCount);
        model.addAttribute("hitCasesFraud", hitCasesFraudCount);
        model.addAttribute("nohitDecisions", nohitDecisionsCount);
        model.addAttribute("hitDecisions", hitDecisionsCount);
        model.addAttribute("demographic", demographicCount);
        model.addAttribute("biometric", biometricCount);


        return "reportsOperator";
    }


    @RequestMapping(value = "/commonReportsDetails", method = RequestMethod.GET)
    public String ReportsDetails(ModelMap model, HttpServletRequest request, @RequestParam int reports) {
        switch (reports){
            case 1 : ArrayList<RegisterManualVerification>  unverifiedCount = reportsDetailService.allUnverifiedReports();
                     model.addAttribute("reports", unverifiedCount);
            break;
            case 2 : ArrayList<RegisterManualVerification>  verifiedCount = reportsDetailService.allverifiedReports();
                model.addAttribute("reports", verifiedCount);
            break;
            case 3 : ArrayList<RegisterManualVerification>  deadlockedCount = reportsDetailService.deadlockedReports();
                model.addAttribute("reports", deadlockedCount);
            break;
            case 4 :  ArrayList<RegisterManualVerification>  verifieddeadlockedCount = reportsDetailService.verifiedDeadlockedReports();
                model.addAttribute("reports", verifieddeadlockedCount);
            break;
            case 5 : ArrayList<RegisterManualVerification>  hitCasesdCount = reportsDetailService.hitcasesReports();
                model.addAttribute("reports", hitCasesdCount);
            break;
            case 6 :  ArrayList<RegisterManualVerification>  hitCasesFraudCount = reportsDetailService.hitCasesFraudReports();
                model.addAttribute("reports", hitCasesFraudCount);
            break;
            case 7 :  ArrayList<RegisterManualVerification>  nohitDecisionsCount = reportsDetailService.nohitDecisionsReports();
                model.addAttribute("reports", nohitDecisionsCount);
            break;
            case 8 : ArrayList<RegisterManualVerification>  hitDecisionsCount = reportsDetailService.hitDecisionsReports();
                model.addAttribute("reports", hitDecisionsCount);
            break;
            case 9 : ArrayList<RegisterManualVerification>  demographicCount = reportsDetailService.demographicReports();
                model.addAttribute("reports", demographicCount);
            break;
            case 10 : ArrayList<RegisterManualVerification>  biometricCount = reportsDetailService.biometricReports();
                model.addAttribute("reports", biometricCount);
            break;

        }



        return "allReports";
    }


    @RequestMapping(value = "/operatorReportsDetails", method = RequestMethod.GET)
    public String OperatorReportsDetails(ModelMap model, HttpServletRequest request, @RequestParam int reports) {
        switch (reports){
            case 1 : ArrayList<RegisterManualVerification>    unverifiedCount =  reportsDetailService.unverifiedOperator();
                model.addAttribute("reports", unverifiedCount);
                     break;
            case 2 :  ArrayList<RegisterManualVerification>    verifiedCount =  reportsDetailService.verifiedOperator();
                model.addAttribute("reports", verifiedCount);
                break;
            case 3: ArrayList<RegisterManualVerification>    deadlockedCount =  reportsDetailService.deadlockedOperator();
                model.addAttribute("reports", deadlockedCount);
                break;
            case 5:  ArrayList<RegisterManualVerification>    hitCasesdCount =  reportsDetailService.hitcasesOperator();
                model.addAttribute("reports", hitCasesdCount);
                break;
            case 7:   ArrayList<RegisterManualVerification>    nohitDecisionsCount =  reportsDetailService.nohitDecisionsOperator();
                model.addAttribute("reports", nohitDecisionsCount);
                break;
            case 8:  ArrayList<RegisterManualVerification>    hitDecisionsCount =  reportsDetailService.hitDecisionsOperator();
                model.addAttribute("reports", hitDecisionsCount);
                break;
            case 9 : ArrayList<RegisterManualVerification>    demographicCount =  reportsDetailService.demographicOperator();
                model.addAttribute("reports", demographicCount);
                break;
            case 10 : ArrayList<RegisterManualVerification>    biometricCount =  reportsDetailService.biometricOperator();
                model.addAttribute("reports", biometricCount);
                break;
        }





//
        return "allReports";
    }

    @RequestMapping(value = "/supervisorReportsDetails", method = RequestMethod.GET)
    public String supervisorReportsDetails(ModelMap model, HttpServletRequest request, @RequestParam int reports) {
        switch (reports){
            case 1 : ArrayList<RegisterManualVerification>  unverifiedCount =  reportsDetailService.unverifiedSupervisor();
                model.addAttribute("reports", unverifiedCount);
                break;
            case 2 : ArrayList<RegisterManualVerification>  verifiedCount =  reportsDetailService.verifiedSupervisor();
                model.addAttribute("reports", verifiedCount);
                break;
            case 3 :  ArrayList<RegisterManualVerification>  deadlockedCount =  reportsDetailService.deadlockedSupervisor();
                model.addAttribute("reports", deadlockedCount);
                break;
            case 4 : ArrayList<RegisterManualVerification>  verifieddeadlockedCount =  reportsDetailService.verifiedDeadlockedSupervisor();
                model.addAttribute("reports", verifieddeadlockedCount);
                break;
            case 5 :  ArrayList<RegisterManualVerification>  hitCasesdCount =  reportsDetailService.hitcasesSupervisor();
                model.addAttribute("reports", hitCasesdCount);
                break;
            case 6 : ArrayList<RegisterManualVerification>  hitCasesFraudCount =  reportsDetailService.hitCasesFraudSupervisor();
                model.addAttribute("reports", hitCasesFraudCount);
                break;
            case 7 : ArrayList<RegisterManualVerification>  nohitDecisionsCount =  reportsDetailService.nohitDecisionsSupervisor();
                model.addAttribute("reports", nohitDecisionsCount);
                break;
            case 8 :  ArrayList<RegisterManualVerification>  hitDecisionsCount =  reportsDetailService.hitDecisionsSupervisor();
                model.addAttribute("reports", hitDecisionsCount);
                break;
            case 9 :ArrayList<RegisterManualVerification>  demographicCount =  reportsDetailService.demographicSupervisor();
                model.addAttribute("reports", demographicCount);
                break;
            case 10 : ArrayList<RegisterManualVerification>  biometricCount =  reportsDetailService.biometricSupervisor();
                model.addAttribute("reports", biometricCount);
                break;
        }
        return "allReports";
    }
//    @RequestMapping(value = "/allReportsController", method = RequestMethod.GET)
//    public String showAllReports(ModelMap model, HttpServletRequest request) {
//        ArrayList<RegisterManualVerification> reports = (ArrayList<RegisterManualVerification>) reportsService.allReports();
//        model.addAttribute("allReports", reports);
//        return "allReports";
//
//    }
//
//    @RequestMapping(value = "/verifiedReportsController", method = RequestMethod.GET)
//    public String showVerifiedReports(ModelMap model, HttpServletRequest request) {
//
//        ArrayList<RegisterManualVerification> reports = (ArrayList<RegisterManualVerification>) reportsService.verifiedReports();
//        model.addAttribute("verifiedReports", reports);
//        return "verifiedReports";
//
//    }
//
//    @RequestMapping(value = "/deadlockReportsController", method = RequestMethod.GET)
//    public String showDeadlockReports(ModelMap model, HttpServletRequest request) {
//
//        ArrayList<RegisterManualVerification> reports = (ArrayList<RegisterManualVerification>) reportsService.deadlockReports();
//        model.addAttribute("deadlockReports", reports);
//        return "deadlockReports";
//
//    }
//
//    @RequestMapping(value = "/supervisorReportsController", method = RequestMethod.GET)
//    public String showSupervisorReports(ModelMap model, HttpServletRequest request) {
//
//        ArrayList<RegisterManualVerification> reports = (ArrayList<RegisterManualVerification>) reportsService.supervisorReports();
//        model.addAttribute("supervisorReports", reports);
//        return "supervisorReports";
//
//    }
//
//    @RequestMapping(value = "/hitcasesReportsController", method = RequestMethod.GET)
//    public String hitcasesReports(ModelMap model, HttpServletRequest request) {
//
//        ArrayList<RegisterManualVerification> reports = (ArrayList<RegisterManualVerification>) reportsService.hitCasesReports();
//        model.addAttribute("hitCasesReports", reports);
//        return "hitCasesReports";
//
//    }
//    @RequestMapping(value = "fraudReportsController", method = RequestMethod.GET)
//    public String fraudReports(ModelMap model, HttpServletRequest request) {
//
//        ArrayList<RegisterManualVerification> reports = (ArrayList<RegisterManualVerification>) reportsService.fraudReports();
//        model.addAttribute("fraudReports", reports);
//        return "fraudReports";
//
//    }
//
//    @RequestMapping(value = "noHitDecisionsReportsController", method = RequestMethod.GET)
//    public String noHitDecisionsReports(ModelMap model, HttpServletRequest request) {
//
//        ArrayList<RegisterManualVerification> reports = (ArrayList<RegisterManualVerification>) reportsService.noHitDecisionsReports();
//        model.addAttribute("noHitReports", reports);
//        return "noHitReports";
//
//    }
//
//    @RequestMapping(value = "hitDecisionsReportsController", method = RequestMethod.GET)
//    public String hitDecisionsReports(ModelMap model, HttpServletRequest request) {
//
//        ArrayList<RegisterManualVerification> reports = (ArrayList<RegisterManualVerification>) reportsService.hitDecisionsReports();
//        model.addAttribute("hitReports", reports);
//        return "hitReports";
//
//    }
//
//    @RequestMapping(value = "demographicReportsController", method = RequestMethod.GET)
//    public String demographicReports(ModelMap model, HttpServletRequest request) {
//
//        ArrayList<RegisterManualVerification> reports = (ArrayList<RegisterManualVerification>) reportsService.demographicReports();
//        model.addAttribute("demographicReports", reports);
//        return "demographicReports";
//
//    }
//
//    @RequestMapping(value = "biometricReportsController", method = RequestMethod.GET)
//    public String biometricReports(ModelMap model, HttpServletRequest request) {
//
//        ArrayList<RegisterManualVerification> reports = (ArrayList<RegisterManualVerification>) reportsService.biometricReports();
//        model.addAttribute("biographicReports", reports);
//        return "biographicReports";
//
//    }


}
