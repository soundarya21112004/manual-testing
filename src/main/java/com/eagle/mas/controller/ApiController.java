package com.eagle.mas.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.eagle.mas.common.SendEmail;
import com.eagle.mas.common.SendSMS;
//import com.eagle.mas.common.SimpleOTPGenerator;
//import com.eagle.mas.common.UtilClass;

import com.eagle.mas.model.MstRoleGroup;
import com.eagle.mas.model.MstRoleToGroup;

import com.eagle.mas.service.MstRoleGroupService;
import com.eagle.mas.service.MstRoleToGroupService;
import com.eagle.mas.service.RolesAccessService;

import com.eagle.mas.service.UserdetailsService;

@RestController
public class ApiController {

	@Autowired
	MstRoleToGroupService mstRoleToGroupService;
	@Autowired
	RolesAccessService rolesAccessService;
	@Autowired
	MstRoleGroupService mstRoleGroupService;

	@Autowired
	UserdetailsService userService;

	@GetMapping("loadEditUserGroup/{val}")
	public List<String> loadEditUserGroup(@PathVariable int val, HttpServletRequest request) {
		/*
		 * HttpSession session = request.getSession(false);
		 * session.setMaxInactiveInterval(600);
		 */
		System.out.println("loadEditUserGroup-------");
		ArrayList<MstRoleToGroup> assignedRoles = mstRoleToGroupService.findbyGroupId(val);
		ArrayList<String> mst = new ArrayList<String>();
		for (MstRoleToGroup mstRoleToGroup : assignedRoles) {
			// MstRoles mstRoles =
			// rolesAccessService.findbyRoleId(mstRoleToGroup.getMstRoles().getRoleid());
			mst.add(mstRoleToGroup.getMstRoles().getRoleid());
		}

		return mst;
	}

	@PostMapping("checkGroupName/{val}")
	public Boolean checkGroupName(@PathVariable String val, HttpServletRequest request) {

		boolean isGroupName = true;
		try {
			/*
			 * HttpSession session = request.getSession(false);
			 * session.setMaxInactiveInterval(600);
			 */
			List<MstRoleGroup> roleGroup = mstRoleGroupService.findByGroupName(val);
			if (roleGroup != null && roleGroup.size() > 0) {
				isGroupName = true;
			} else {
				isGroupName = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isGroupName;
	}

	@GetMapping("contactnum/{contactnumber}")
	public String getuserPhonenum(@PathVariable String contactnumber, HttpServletRequest request) {
		/*
		 * HttpSession session = request.getSession(false);
		 * session.setMaxInactiveInterval(600);
		 */
		String phonenumber = userService.findByphone(contactnumber);
		return phonenumber;
	}

	@GetMapping("contactnumById/{contactnumber}/{userid}")
	public String getuserPhonenumByuserId(@PathVariable String contactnumber, @PathVariable String userid,
			HttpServletRequest request) {
		/*
		 * HttpSession session = request.getSession(false);
		 * session.setMaxInactiveInterval(600);
		 */
		String phonenumber = userService.findByphoneByuserid(contactnumber, userid);
		return phonenumber;
	}

	/*
	 * @PostMapping("sendingOtpByEmailId") public String
	 * sendingOtpByEmailId(@ModelAttribute("applicantbean") ApplicantBean app,
	 * HttpServletRequest request) { String otp = null; String otpenc = null; //
	 * String plantId = null; try {
	 * System.out.println("app.getApplicant().getFirstname()>>" +
	 * app.getApplicant().getFirstname());
	 * System.out.println("app.getApplicant().getEmail()>>>" +
	 * app.getApplicant().getEmail());
	 * 
	 * HttpSession session = request.getSession(false);
	 * session.setMaxInactiveInterval(600);
	 * 
	 * otpenc = new SimpleOTPGenerator().random(4); otp = new
	 * UtilClass().encrypt(otpenc);
	 * 
	 * String template =
	 * "Dear {0}, <br>\n<br>\n\t BANGLADESH ROAD TRANSPORT AUTHORITY WELCOMES YOU!<br>\n<br>\n Your One Time Password for Learner License Registration is : {1}.<br>\n<br>\n kindly enter your OTP to register into the system.<br>\n<br>\nThanks and Best Regards<br>\n<br>\nBANGLADESH ROAD TRANSPORT AUTHORITY."
	 * ; String message = MessageFormat.format(template,
	 * app.getApplicant().getFirstname(), otpenc); if (new
	 * ApiController().sendmailprocess(app.getApplicant().getEmail(),
	 * "BANGLADESH ROAD TRANSPORT AUTHORITY - ONE TIME PASSWORD", message)) {
	 * System.out.println("OtpSend"); // plantId = "otpsend"; } else {
	 * System.out.println("not send"); // plantId = " "; } // String smsMessage =
	 * "ONE TIME PASSWORD - BANGLADESH ROAD TRANSPORT AUTHORITY // :" + otpenc; //
	 * new ApiController().sendSMSprocess(mobilenumber, smsMessage); } catch
	 * (Exception e) { e.printStackTrace(); }
	 * 
	 * return otpenc; }
	 */

	public boolean sendmailprocess(String tomail, String subject, String mailMessage) {
		boolean flag = false;
		try {
			System.out.println("sending mail");
			SendEmail email = new SendEmail();
			email.configfile();
			flag = email.sendExternalMail(tomail, subject, mailMessage);
		} catch (IOException ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	public boolean sendmailprocessWithattach(String tomail, String subject, String mailMessage, String filename,
			DataSource attach) {
		boolean flag = false;
		try {
			System.out.println("sending attach mail");
			SendEmail email = new SendEmail();
			email.configfile();
			flag = email.sendExternalMailWithAttachement(tomail, subject, mailMessage, filename, attach);
		} catch (IOException ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	public boolean sendSMSprocess(String tomail, String mailMessage) {
		boolean flag = false;
		try {
			SendSMS email = new SendSMS();
			email.configfile();
			flag = email.sendExternalSMS(tomail, mailMessage);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return flag;
	}

//	@GetMapping("otpDecrypt/{generatedOTP}")
//	public String otpDecrypt(@PathVariable String generatedOTP, HttpServletRequest request) {
//		String otpDec = null;
//		try {
//			/*
//			 * HttpSession session = request.getSession(false);
//			 * session.setMaxInactiveInterval(600);
//			 */
//			System.out.println("generatedOTP>>>>>>>>" + generatedOTP);
//			otpDec = new UtilClass().decrypt(generatedOTP);
//			System.out.println("otpDec:" + otpDec);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return otpDec;
//	}

}
