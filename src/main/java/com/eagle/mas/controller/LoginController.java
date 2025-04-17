package com.eagle.mas.controller;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.eagle.mas.model.TokenDetails;
import com.eagle.mas.security.jwt.JwtService;
import com.eagle.mas.repository.TokenRepository;
import com.eagle.mas.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.eagle.mas.common.MacAddress;
import com.eagle.mas.dao.LoginDAO;
import com.eagle.mas.dao.UserManagementDAO;
import com.eagle.mas.model.MstRoleToGroup;
import com.eagle.mas.model.Userdetails;

@Controller
@Scope("session")
public class LoginController {
	@Autowired
	LoginDAO logindao;

	@Autowired
	RolesService roleservice;
//	@Autowired
//	private TokenManager tokenManager;
	@Autowired
	UserdetailsService userservice;
	@Autowired
	UserManagementDAO userDao;
	private Scanner scanner;
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private JwtService jwtService;

	@Value("${admin.jwt.security.access.expiration}")
	private long expiration;

	@Autowired
	private ManualVerificationService mvs;

	@Autowired
	DashBoardService dashService;

	public static Logger logger = LoggerFactory.getLogger(LoginController.class);

	private final static String AUTHORIZATIONBEARER = "MvsToken";



public String getUtcTime(){
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");

	String convertDate = dateFormat.format(new Date());
	return convertDate;

}
//
//public String leadToLogin(HttpServletRequest request){
//	HttpSession session = request.getSession();
//
//	boolean check= (boolean) session.getAttribute("isLoggedin");
//	if(check==false){
//		return "redirect: loginPage";
//	}
//
//	return "txt";
//}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLoginPage(HttpServletResponse response) {
		Cookie cookie = new Cookie(AUTHORIZATIONBEARER, "");
		cookie.setMaxAge(0);
		cookie.setPath("/MVS");
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		System.out.println("------Redirecting to login page------");
		return "login";
	}
	@RequestMapping(value = "/errorPage", method = RequestMethod.GET)
	public String errorpage(ModelMap model) {
		return "errorPage";
	}

	@RequestMapping("/redirectlogin")
	public String tokenexpriy(ModelMap model, RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {
		model.addAttribute("errorMessage", "Session is expired redirect to loginPage");
		return "login";
	}

	@RequestMapping("/redirectLogin")
	public String tokenExpired(ModelMap model, RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {
		model.addAttribute("errorMessage", "Session is expired redirect to loginPage");
		return "login";
	}

	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String showLoginPage1(ModelMap model) {
//		System.out.println("testing login ----------");
		return "login";
	}

	@RequestMapping(value = "/dashBoard", method = RequestMethod.GET)
	public String showDashboard(ModelMap model, HttpServletRequest request) {

		HttpSession session = request.getSession();
//		session.setMaxInactiveInterval(20);

		session.getAttribute("userID");
//		String check= (String) session.getAttribute("isLoggedin");
//		System.out.println("bye"+check);

//		if(check=="false"){
//			System.out.println("hello");
//			return "redirect:loginPage";
//		}

		System.out.println("useri id"+session.getAttribute("userID"));
		if(session.getAttribute("userID")==null){
			return "redirect:redirectloginn";
		}
		String id= (String) session.getAttribute("userID");
		System.out.println("testing login ----------for user id"+id);


		String usertype=userservice.getUserType(id);
		System.out.println("belongs to user type"+usertype);
		model.addAttribute("usertype",usertype);

		if(usertype.equalsIgnoreCase("OPERATOR")){
			try{
				int count=dashService.numberofHitsOp(id);


				int count1=dashService.numberofNoHitsOp(id);
				int count2=dashService.numberofUnverifiedRecordsOp(id);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

				String convertDate = dateFormat.format(new Date());
				System.out.println(convertDate);

				String year=convertDate.split("-")[0];
				String month=convertDate.split("-")[1];
				System.out.println(year+"year"+month+"month");
				System.out.println(Integer.parseInt(year)+"year"+Integer.parseInt(month)+"month");


				int hitMonthcount=dashService.hitsThisMonthOp(Integer.parseInt(year),Integer.parseInt(month),id);
//		int totalReocrdsPerMonth=mvs.toalRecordsPerMonth(Integer.parseInt(year),Integer.parseInt(month));
				int totalRecords= dashService.toalRecordsOp();
				int verifiedCount=count+count1;
				model.addAttribute("numberofCount",count);
				model.addAttribute("numberofnohitCount",count1);
				model.addAttribute("numberofRecords",count2);
				model.addAttribute("numberofVerifiedRecords",verifiedCount);
				model.addAttribute("hitThisMonthcount",hitMonthcount);
				model.addAttribute("totalRecords",totalRecords);

				logger.info(logger("LoginController","showDashboard",getUtcTime(), String.valueOf(hitMonthcount)));
			}
			catch (Exception e){
				logger.error(logger("LoginController","showDashboard",getUtcTime(), e.toString()));
			}
//	model.addAttribute("operatorLogin",usertype);
	return "dashboard";
}
else if(usertype.equalsIgnoreCase("SUPERVISOR")){
			try{
				int count=dashService.numberofHitsSuper(id);


				int count1=dashService.numberofNoHitsSuper(id);
				int count2=dashService.numberofUnverifiedRecordsSuper();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

				String convertDate = dateFormat.format(new Date());
				System.out.println(convertDate);

				String year=convertDate.split("-")[0];
				String month=convertDate.split("-")[1];
				System.out.println(year+"year"+month+"month");
				System.out.println(Integer.parseInt(year)+"year"+Integer.parseInt(month)+"month");


				int hitMonthcount=dashService.hitsThisMonthSuper(Integer.parseInt(year),Integer.parseInt(month),id);
//		int totalReocrdsPerMonth=mvs.toalRecordsPerMonth(Integer.parseInt(year),Integer.parseInt(month));
				int totalRecords= dashService.toalRecordsSuper();
				int verifiedCount=count+count1;
				model.addAttribute("numberofCount",count);
				model.addAttribute("numberofnohitCount",count1);
				model.addAttribute("numberofRecords",count2);
				model.addAttribute("numberofVerifiedRecords",verifiedCount);
				model.addAttribute("hitThisMonthcount",hitMonthcount);
				model.addAttribute("totalRecords",totalRecords);

				logger.info(logger("LoginController","showDashboard",getUtcTime(), String.valueOf(hitMonthcount)));
			}
			catch (Exception e){
				logger.error(logger("LoginController","showDashboard",getUtcTime(), e.toString()));
			}
//	model.addAttribute("operatorLogin",usertype);

	return "dashboard";
}

		try{
			int count=mvs.numberofHits();


		int count1=mvs.numberofNoHits();
		int count2=mvs.numberofUnverifiedRecords();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String convertDate = dateFormat.format(new Date());
		System.out.println(convertDate);

		String year=convertDate.split("-")[0];
		String month=convertDate.split("-")[1];
		System.out.println(year+"year"+month+"month");
		System.out.println(Integer.parseInt(year)+"year"+Integer.parseInt(month)+"month");


		int hitMonthcount=mvs.hitsThisMonth(Integer.parseInt(year),Integer.parseInt(month));
//		int totalReocrdsPerMonth=mvs.toalRecordsPerMonth(Integer.parseInt(year),Integer.parseInt(month));
			int totalRecords= mvs.toalRecords();
		int verifiedCount=count+count1;
			model.addAttribute("numberofCount",count);
			model.addAttribute("numberofnohitCount",count1);
			model.addAttribute("numberofRecords",count2);
			model.addAttribute("numberofVerifiedRecords",verifiedCount);
			model.addAttribute("hitThisMonthcount",hitMonthcount);
			model.addAttribute("totalRecords",totalRecords);

			logger.info(logger("LoginController","showDashboard",getUtcTime(), String.valueOf(hitMonthcount)));
		}
		catch (Exception e){
			logger.error(logger("LoginController","showDashboard",getUtcTime(), e.toString()));
		}
		return "dashboard";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String showWelcomePage(ModelMap model, RedirectAttributes redirectAttributes, HttpServletRequest request, @RequestParam String username,
								  HttpServletResponse response, @RequestParam String password ) {
		try {
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(600);
			password = new MacAddress().md5Encode(password.getBytes());
			Userdetails user = logindao.getAllPersons(username, password);
			if(user==null){
				System.out.println("User not found");
//				model.addAttribute("invalidPass","please enter vaild Email and password");
				redirectAttributes.addFlashAttribute("errorMessage", "please enter valid Email and password");
				return "redirect:loginPage";
			}

//			model.addAttribute("invalidPass","please enter vaild user and password");
			Userdetails userdetails= userservice.passwordChangeDetails(username);

			if(userdetails.getLoginStatus()!=null && userdetails.getActivestatus()=='1') {
				if (user != null) {
					Set<String> userRoles = (Set<String>) roleservice.findAllByUserid(user.getUserid());
					Set<String> userSubRoles = (Set<String>) roleservice.findAllSubrolesByUserid(user.getUserid());
					Set<String> userSubRolesCount = (Set<String>) roleservice.findAllSubrolesByUseridCount(user.getUserid());
					ArrayList<MstRoleToGroup> roles = (ArrayList<MstRoleToGroup>) roleservice.findAllGroups(user.getUserid());
					System.out.println("userSubRolesCount::" + userSubRolesCount);

					model.put("name", username);
					model.put("password", password);
					session.setAttribute("headerMenu", userRoles);
					session.setAttribute("userdetails", user);
					session.setAttribute("subMenu", roles);
					session.setAttribute("subRoles", userSubRoles);
					session.setAttribute("subRolesCount", userSubRolesCount);
					session.setAttribute("userID", user.getUserid());
					session.setAttribute("isLoggedin","true");

					final String token = jwtService.GenerateToken(user.getEmail());
					System.out.println("token = " + token);
					final String refreshToken = jwtService.GenerateRefreshToken(user.getEmail());
					Optional<TokenDetails> tokenDetailsOptional = tokenRepository.findByUserdetailsAndExpiredAndRevoked(user, false, false);
					if (tokenDetailsOptional.isPresent()) {
						TokenDetails tokenDetails = tokenDetailsOptional.get();
						tokenDetails.setToken(token);
						tokenDetails.setAccess_createdAt(new Date(System.currentTimeMillis()));
						tokenDetails.setAccess_updatedAt(new Date(System.currentTimeMillis()));
						tokenDetails.setAccess_expiredAt(new Date(System.currentTimeMillis() + expiration));
						tokenDetails.setRefresh_token(refreshToken);
						tokenRepository.save(tokenDetails);
					}else{
						tokenRepository.save(TokenDetails.builder()
								.expired(false)
								.revoked(false)
								.token(token)
								.refresh_token(refreshToken)
								.access_createdAt(new Date(System.currentTimeMillis()))
								.access_updatedAt(new Date(System.currentTimeMillis()))
								.access_expiredAt(new Date(System.currentTimeMillis() + expiration))
								.userdetails(user)
								.build());
						tokenRepository.flush();
					}

					Cookie cookie = new Cookie(AUTHORIZATIONBEARER,token);
					cookie.setHttpOnly(true);
					cookie.setPath("/MVS");
					cookie.setSecure(true);
					response.addCookie(cookie);
					return "redirect:dashBoard";
				} else {
					System.out.println("else:: error");
					model.put("errorMessage1", "Live Fingerprint Required");
					model.addAttribute("invalidPass","please enter vaild password");
					return "login";
				}
			}
			else if(userdetails.getLoginStatus()==null && userdetails.getActivestatus()=='1'){
				return "firstLogin_changePwd";
			}
			else if(userdetails.getLoginStatus()==null && userdetails.getActivestatus()=='0'){
				redirectAttributes.addFlashAttribute("errorMessage", "please enter vaild Email and password");
				return "redirect:loginPage";
			}
		} catch (Exception e) {
			System.out.println("userSubRolesCount:: error");
			logger.error(logger("LoginController","showWelcomePage",getUtcTime(), e.toString()));
		}
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("logout=====>");
		HttpSession session = request.getSession();
		session.getAttribute("userID");
		session.setAttribute("isLoggedin","false");
		session.invalidate();

		Cookie cookie = new Cookie("Authorization", null);
		cookie.setMaxAge(0);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "login";
	}

	@RequestMapping(value = "/logout1", method = RequestMethod.GET)
	public String logout1(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("LOGOUT 1 METHOD");
		HttpSession session = request.getSession();
		session.getAttribute("userID");
		session.setAttribute("isLoggedin","false");
		session.invalidate();

		Cookie cookie = new Cookie("Authorization", "");
		cookie.setMaxAge(0);
		cookie.setPath("/MVS");
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		return "redirect:/";
	}

//	@RequestMapping(value = "/home", method = RequestMethod.GET)
//	public String showHomePage(ModelMap model, HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		session.setMaxInactiveInterval(600);
//		return "dashboard";
//	}

	@RequestMapping(value = "/firstLoginChangePassword")
	public String firstLoginChangePassword(ModelMap model, RedirectAttributes redirectAttributes, @RequestParam String userOldPassword,
			@RequestParam String password, HttpServletRequest request) {

		System.out.println("old pass "+userOldPassword);
		System.out.println(" pass "+password);

		String oldPasswordEncrip = null;
		String newPasswordEncrip = null;
		try {

			if (userOldPassword != null) {
				oldPasswordEncrip = new MacAddress().md5Encode(userOldPassword.getBytes());
			}
			if (password != null) {
				newPasswordEncrip = new MacAddress().md5Encode(password.getBytes());
			}
			Userdetails user = userservice.findbyOldPwd(userOldPassword);
			if (user != null) {
				if (user.getPassword().equals(oldPasswordEncrip)) {

					if (!oldPasswordEncrip.equals(newPasswordEncrip)) {

						user.setPassword(newPasswordEncrip);
						user.setVerifycodePwd(password);
						user.setLoginStatus("pwdChanged");

						boolean out = userDao.UpdateStatus(user, user.getUserid());
						// int out = userservice.updatePasswordDetails("pwdChanged", user.getUserid());

						if (out) {
//							model.put("successMessage", "PASSWORD UPDATED SUCCESSFULLY.");
							redirectAttributes.addFlashAttribute("successMessage", "PASSWORD UPDATED SUCCESSFULLY.");

						} else {

//							model.put("errorMessage", "Error While Processing.Please Try Again Later");
							redirectAttributes.addFlashAttribute("errorMessage", "Error While Processing.Please Try Again Later");
						}
					} else {
//						model.put("errorMessage", "Old Password and New Password should not be same ");
						redirectAttributes.addFlashAttribute("errorMessage", "Old Password and New Password should not be same ");
					}

				} else {
//					model.put("errorMessage", "Invalid Old Password");
					redirectAttributes.addFlashAttribute("errorMessage", "Invalid Old Password");
				}
			} else {
//				model.put("errorMessage", "No Data Found");
				redirectAttributes.addFlashAttribute("errorMessage", "No Data Found");
			}

		} catch (Exception e) {
			logger.error(logger("LoginController","firstLoginChangePassword",getUtcTime(),e.toString()));
		}

		return "redirect:loginPage";
	}


	@RequestMapping(value = "/forgotPasswordDetails")
	public String forgotPasswordDetails (){
		return "forgotPassword";

	}
	@RequestMapping(value = "/forgotPasswordController")
	public String forgotPassword (Model model, RedirectAttributes redirectAttributes, @RequestParam String email,
								  @RequestParam String password){
		System.out.println(email);
		System.out.println(password);

		String newPasswordEncrip = null;
		try {
			if (password != null) {
				newPasswordEncrip = new MacAddress().md5Encode(password.getBytes());
			}
			Userdetails user = userservice.findUserdetailsByEmail(email);
			if(user == null){
				redirectAttributes.addFlashAttribute("errorMessage","EMAIL DOES NOT EXIST");

				return "redirect:forgotPasswordDetails";

			}
			System.out.println("activestatus  "+user.getActivestatus());

			if(user.getActivestatus() != '0'){
				user.setPassword(newPasswordEncrip);
				user.setVerifycodePwd(password);
				user.setLoginStatus("pwdChanged");

				boolean out = userDao.UpdateStatus(user, user.getUserid());
				System.out.println("out"+out);
				if (out) {
//							model.put("successMessage", "PASSWORD UPDATED SUCCESSFULLY.");
					redirectAttributes.addFlashAttribute("successMessage", "PASSWORD UPDATED SUCCESSFULLY.");
					return "redirect:loginPage";
				}

			}
			else{
				System.out.println("user not activated");
				redirectAttributes.addFlashAttribute("errorMessage", "USER IS NOT ACTIVATED");
				return "redirect:forgotPasswordDetails";
			}
		}
		catch (Exception e){
			logger.error(logger("LoginController","forgotPassword",getUtcTime(), e.toString()));
			return "redirect:errorPage";

		}


		return "redirect:forgotPasswordDetails";

	}




	@RequestMapping(value = "/changePasswordDetails")
	public String changePasswordDetails(HttpServletRequest request, ModelMap model) {
		try {
			HttpSession session = request.getSession(false);
			session.setMaxInactiveInterval(600);
			Userdetails user = (Userdetails) session.getAttribute("userdetails");

			if (user != null) {
				model.addAttribute("userdetail", user);
			}
		} catch (Exception e) {
			logger.error(logger("LoginController","changePasswordDetails",getUtcTime(), e.toString()));
			return "redirect:errorPage";

		}
		return "changePassword";
	}

	@RequestMapping(value = "/updatePassword")
	public String updatePassword(HttpServletRequest request,RedirectAttributes redirectAttributes, ModelMap model, @RequestParam String password, @RequestParam String userOldPassword) {
		try {
			HttpSession session = request.getSession(false);
			session.setMaxInactiveInterval(600);
			Userdetails user = (Userdetails) session.getAttribute("userdetails");

			String encpassword = new MacAddress().md5Encode(password.trim().getBytes());
			String oldPass = userservice.getUserOldPassword(user.getUserid());
			System.out.println("oldPass"+oldPass);
			System.out.println("userOldPassword"+userOldPassword);

			if(oldPass.equals(userOldPassword)){

			int out = userservice.updatePasswordDetails(encpassword, password, user.getUserid());
			if (out > 0) {
				session.invalidate();
				redirectAttributes.addFlashAttribute("successMessage", "PASSWORD UPDATED SUCCESSFULLY. PLEASE LOGIN TO CONTINUE..");

			} else {
				redirectAttributes.addFlashAttribute("errorMessage", "ERROR WHILE UPDATING PASSWORD!!PLEASE TRY LATER");
				return "redirect:changePasswordDetails";

			}}
			else{
				redirectAttributes.addFlashAttribute("errorMessage", "OLD PASSWORD DOES NOT EXIST");
				return "redirect:changePasswordDetails";

			}
		} catch (Exception e) {
			logger.error(logger("LoginController","updatePassword",getUtcTime(), e.toString()));
			return "redirect:errorPage";
		}

		return "redirect:loginPage";
	}
	public static String logger(String contoller , String method, String time, String response) {
		String loggerJson = "{"+"\"controller\":\""+contoller+"\"," + "\"method\": \""+method+"\","+
				"\"time stamp\":"+time+","+"\"response\":\""+response+"\""+ "}";
		return loggerJson;
	}
}
