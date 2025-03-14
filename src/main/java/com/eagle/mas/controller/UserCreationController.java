package com.eagle.mas.controller;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.eagle.mas.model.MstRoles;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eagle.mas.bean.MstRolesBean;
import com.eagle.mas.common.MacAddress;
//import com.eagle.mas.common.SimpleOTPGenerator;
import com.eagle.mas.dao.UserManagementDAO;
import com.eagle.mas.model.MstRoleGroup;
import com.eagle.mas.model.MstRolegroupToUser;
import com.eagle.mas.model.Userdetails;
import com.eagle.mas.service.MstRoleGroupService;
import com.eagle.mas.service.MstRoleGroupToUserService;
import com.eagle.mas.service.UserdetailsService;



@Controller
public class UserCreationController {

	@Autowired
	UserManagementDAO userdao;

	@Autowired
	UserdetailsService userService;
	@Autowired

	MstRoleGroupService mstRoleGroupService;
	@Autowired
	MstRoleGroupToUserService mstRoleGroupToUserService;
	Logger logger = LoggerFactory.getLogger(LoginController.class);


	public String getUtcTime(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");

		String convertDate = dateFormat.format(new Date());
		return convertDate;

	}

//created by paramu
@RequestMapping(value = "/userCreation", method = RequestMethod.GET)
public String showHomePage(ModelMap model, @ModelAttribute("mstRolesBean") MstRolesBean mstRolesBean,
						    BindingResult result, HttpServletRequest request) {
	try {
//		HttpSession session = request.getSession(false);
		//session.setMaxInactiveInterval(600);
		HttpSession session = request.getSession();
		if(session.getAttribute("userID")==null){
			return "redirect:redirectlogin";
		}

		Iterable<MstRoleGroup> loadRoleGroup = mstRoleGroupService.listRoleToGroup();
		for (MstRoleGroup allname:loadRoleGroup) {
			System.out.println("allRoles>>"+allname.getGroupName());
		}
		model.addAttribute("loadRoleGroup", loadRoleGroup);

	} catch (Exception e) {
		logger.error(logger("UserCreationController","showHomePage",getUtcTime(), e.toString()));

	}
	return "userCreation";

}

	@RequestMapping(value = "/UserManagement_", method = RequestMethod.GET)
	public String loadRoleToGroup(ModelMap model, @ModelAttribute("mstRoleGroup") MstRoleGroup mstRoleGroup,
			BindingResult result, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			session.setMaxInactiveInterval(600);
			Iterable<MstRoleGroup> loadRoleGroup = mstRoleGroupService.listRoleToGroup();
			model.addAttribute("loadRoleGroup", loadRoleGroup);

		} catch (Exception e) {
			logger.error("UserCreationController","loadRoleToGroup",getUtcTime(),e.toString());

		}

		return "loadRoleGroup";
	}

	@RequestMapping(value = "/searchRoleDetails", method = RequestMethod.POST)
	public String registerPage(ModelMap model, @ModelAttribute("mstRolesBean") MstRolesBean mstRolesBean,
			@RequestParam("groupId") int groupId, BindingResult result, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			session.setMaxInactiveInterval(600);
			MstRoleGroup mstRoleGroup = mstRoleGroupService.findbygroupId(groupId);
			model.addAttribute("groupName", mstRoleGroup.getGroupName());
			model.addAttribute("groupId", mstRoleGroup.getGroupId());


		} catch (Exception e) {
			logger.error(logger("UserCreationController","registerPage",getUtcTime(), e.toString()));

		}

		return "userCreation";
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public String saveUserDetails(@ModelAttribute("mstRolesBean") MstRolesBean mstRolesBean, @RequestParam("groupId") Integer groupId,
								  ModelMap model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		System.out.println("Testing");


		try {
			System.out.println("Testing");

			HttpSession session = request.getSession(false);
		//	session.setMaxInactiveInterval(600);
			Userdetails user = mstRolesBean.getUserdetails();

			MstRoleGroup  mstRoleGroup = mstRoleGroupService.findbygroupId(groupId);

			System.out.println("print Group Name" +mstRoleGroup.getGroupName());
			Integer maxSno = 0;
			String mfid = null;
			int userIdGenerate = userService.maxUserid();

			if (userIdGenerate != 0) {

				maxSno = userIdGenerate;
			} else {
				maxSno = 1;
			}
			if (maxSno.toString().length() == 1) {
				mfid = "0000" + maxSno;
			}
			if (maxSno.toString().length() == 2) {
				mfid = "000" + maxSno;
			}
			if (maxSno.toString().length() == 3) {
				mfid = "00" + maxSno;
			}
			if (maxSno.toString().length() == 4) {
				mfid = "0" + maxSno;
			}

			System.out.println("user id: " + userIdGenerate);
			user.setUserid(mfid);
			user.setSno(userIdGenerate);
			user.setEnteredDate(new Date());
			user.setActivestatus('0');
			user.setBelongsTo(mstRoleGroup.getGroupName());
			user.setIsloggedin('0');

//			String password = new SimpleOTPGenerator().getSaltString(12);
			String password = user.getEmail();
			user.setVerifycodePwd(password);
			String encpassword = new MacAddress().md5Encode(password.getBytes());
			user.setPassword(encpassword);
			boolean out = userdao.saveAll(user);
			boolean out1 = true;
			if (out) {
				int sno2 = mstRoleGroupToUserService.sno();
				MstRolegroupToUser roletouser = new MstRolegroupToUser();
				roletouser.setSno(sno2);
				roletouser.setActivestatus('1');
				roletouser.setUserdetails(user);
				roletouser.setRoleGroupId(groupId);
				out1 = userdao.saveUserToRole(roletouser);
				System.out.println("message sucess");
				redirectAttributes.addFlashAttribute("successMessage", "USER CREATED SUCCESSFULLY.");
			}
			else {
				redirectAttributes.addFlashAttribute("errorMessage", "ERROR WHILE CREATING USER GROUP");
			}
			/*if (out1) {
				String template = "Dear {0}, <br>\n<br>\n\t BANGLADESH ROAD TRANSPORT AUTHORITY WELCOMES YOU!<br>\n<br>\n "
						+ "You can access the same using following credentials.<br>\n Username : {1} <br>\n Password : {2} <br>\n<br>\n"
						+ "You can change password at any time for security reasons.<br>\n<br>\n "
						+ "Thanks and Best Regards<br>\n<br>\n " + "BANGLADESH ROAD TRANSPORT AUTHORITY.";
				//String message = MessageFormat.format(template, mstRolesBean.getUserdetails().getFirstnameEn());
                String message = MessageFormat.format(template, mstRolesBean.getUserdetails().getFirstnameEn(), mstRolesBean.getUserdetails().getEmail(), password);
				new ApiController().sendmailprocess(mstRolesBean.getUserdetails().getEmail(), "BANGLADESH ROAD TRANSPORT AUTHORITY", message);
				redirectAttributes.addFlashAttribute("successMessage", "USER CREATED SUCCESSFULLY.");
			} else {
				redirectAttributes.addFlashAttribute("errorMessage", "ERROR WHILE CREATING USER GROUP");
			}
*/
		} catch (Exception e) {
			logger.error(logger("UserCreationController","saveUserDetails",getUtcTime(), e.toString()));
			return "redirect:errorPage";

		}

		return "redirect:userCreation";
	}

//	searchRoleToGroupForEdit
	@RequestMapping(value = "/loadRoleGroupForEdit", method = RequestMethod.GET)
	public String editUserView(ModelMap model, @ModelAttribute("mstRoleGroup") MstRoleGroup mstRoleGroup,
							   BindingResult result, HttpServletRequest request) {
		try {
			//HttpSession session = request.getSession(false);
			//session.setMaxInactiveInterval(600);
			Iterable<MstRoleGroup> loadRoleGroup = mstRoleGroupService.listRoleToGroup();
			model.addAttribute("loadRoleGroup", loadRoleGroup);

		} catch (Exception e) {
			logger.error(logger("UserCreationController","editUserView",getUtcTime(), e.toString()));

		}

		return "loadRoleGroupForEdit";
	}




	@RequestMapping(value = "/searchUserDetailsForEdit", method = RequestMethod.POST)
	public String searchUserDetailsForEdit(ModelMap model, @ModelAttribute("mstRolesBean") MstRolesBean mstRolesBean,
										   @RequestParam("groupName") String groupName, BindingResult result, HttpServletRequest request) {
		try {HttpSession session = request.getSession(false);
			session.setMaxInactiveInterval(600);
			System.out.println("groupName>>>>>" + groupName);
			Userdetails user = mstRolesBean.getUserdetails();

			List<Userdetails> userDetails = userService.findbyGroupName(groupName);
			for (Userdetails users :userDetails) {
				System.out.println("user details"+ users.getUserid() +users.getEmail()+users.getFirstnameEn());

			}

			model.addAttribute("userDetails", userDetails);
		} catch (Exception e) {
			logger.error(logger("UserCreationController","searchUserDetailsForEdit",getUtcTime(), e.toString()));

		}
		return "editUserView";

		//return "editUserViewList";
	}


	@RequestMapping(value = "/userDetailsEdit", method = RequestMethod.GET)
	public String userDetailsEdit(ModelMap model, @ModelAttribute("mstRolesBean") MstRolesBean mstRolesBean,
			@RequestParam("userId") String userId, BindingResult result, HttpServletRequest request) {
		try {
			//HttpSession session = request.getSession(false);
		///	session.setMaxInactiveInterval(600);

			System.out.println("print the user details");
			Userdetails user = mstRolesBean.getUserdetails();
			user = userdao.findbyUserId(userId);
			mstRolesBean.setUserdetails(user);
			model.addAttribute("userdetails", user);

		} catch (Exception e) {
			logger.error(logger("UserCreationController","userDetailsEdit",getUtcTime(), e.toString()));

		}

		return "editUserManagement";
	}

	
	
	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public String updateUserDetails(@ModelAttribute("mstRolesBean") MstRolesBean mstRolesBean, ModelMap model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		try {
			//HttpSession session = request.getSession(false);
			//session.setMaxInactiveInterval(600);
			System.out.println("edit User");
			Userdetails user = mstRolesBean.getUserdetails();						
			//user.setLicenseissueoffice(user.getLicenseissueoffice());		
			boolean out = userdao.updateAll(user);			
			if (out) {
				System.out.println("if condition checked" +out);
				redirectAttributes.addFlashAttribute("successMessage", "USER UPDATED SUCCESSFULLY.");
			} else {
				redirectAttributes.addFlashAttribute("errorMessage", "ERROR WHILE CREATING USER GROUP");
			}

		} catch (Exception e) {
			logger.error(logger("UserCreationController","editUser",getUtcTime(), e.toString()));

		}
		return "editUserView";
	}


	@RequestMapping(value = "/userApproval", method = RequestMethod.GET)
	public String approveUsers(ModelMap model, HttpServletRequest request){

		try {
			HttpSession session = request.getSession();
			if (session.getAttribute("userID") == null) {
				return "redirect:redirectlogin";
			}
			ArrayList<Userdetails> list = userService.getUserdetails();

			model.addAttribute("userDetails", list);
		}catch(Exception e){
			logger.error(logger("UserCreationController","approveUsers",getUtcTime(), e.toString()));

			return "redirect:errorPage";

		}
		return "userApproval";

	}

	@RequestMapping(value = "/userApproveDecline", method = RequestMethod.GET)
	public String approveOrDecline(@RequestParam(value = "status",required = false) String status,
								   @RequestParam(value = "userid",required = false) String userid,RedirectAttributes redirectAttributes) {
//		@RequestParam(value = "status",required = false) String status

//String status="";
System.out.println(status);

try {
	if (status.equalsIgnoreCase("approved")) {
		userService.setActivestatus(userid);
		userService.setUserApproval(status, userid);

		Userdetails userdetails = userService.getUserdetails1(userid);
		String pass = userdetails.getVerifycodePwd();

//		String decodepass= new MacAddress().(pass.getBytes());


		String template = "Dear " + userdetails.getFirstnameEn() + ", <br>\n<br>\n\tYour Registration in Philippine's manual Verification System - MVS has been \n" +
				"                                 approved.You can access the same using following credentials.<br>\n Username : " + userdetails.getEmail() + " <br>\n Password : " + userdetails.getVerifycodePwd() + " <br>\n<br>\n\n" +
				"                                 Please access the MVS System by clicking http://172.19.33.138/MVS <br>\n<br>\n\n" +
				"                                 You can change password at any time for security reasons.<br>\n<br>\n\n" +
				"                                 Note : While changing the password for the first time and then every time you change the password, user are advised to use strong password that should have atleast 8 digits that are combination of lower case letters,  upper case letters, numbers and special characters <br>\n<br>\n\n" +
				"                                 Thanks & Regards,  <br>\n<br>\n\n" +
				"                                 IDENTIFICATION SYSTEM OF PHILIPPINE.";
//		String emailId = "shiyamalakgisl@gmail.com";
//		String emailId = "gusantonylara@gmail.com";
//		String emailId= "mageshk069@gmail.com";
		new ApiController().sendmailprocess(userdetails.getEmail(), "PHILIPPINE", template);
		redirectAttributes.addFlashAttribute("successMessage", "APPROVED SUCCESSFULLY");
	} else if (status.equalsIgnoreCase("declined")) {
		userService.setUserApproval(status, userid);
		userService.setActivestatus2(userid);
		redirectAttributes.addFlashAttribute("successMessage", "DECLINED  SUCCESSFULLY");

	} else {
		System.out.println("works");
		redirectAttributes.addFlashAttribute("failureMessage", "ERROR WHILE PROCESSING.");
	}

}
catch (Exception e){
	logger.error(logger("UserCreationController","userApproveDecline",getUtcTime(), e.toString()));
	return "redirect:errorPage";

}





		return "redirect:userApproval";
	}
	public static String logger(String contoller , String method, String time, String response) {
		String loggerJson = "{"+"\"controller\":\""+contoller+"\"," + "\"method\": \""+method+"\","+
				"\"time stamp\":"+time+","+"\"response\":\""+response+"\""+ "}";
		return loggerJson;
	}

	@GetMapping("/exitsemail")
	@ResponseBody boolean exitsEmail(@RequestParam("email") String email){
		int n = userService.checkmail(email);
		System.out.println("email addresss  ="+email);
		return n != 0;
	}


//	redirectAttributes.addFlashAttribute("failureMessage","email already exits");
//			return "redirect:userCreation";


}
