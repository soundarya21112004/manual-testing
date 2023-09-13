package com.eagle.mas.controller;

import com.eagle.mas.bean.MstRolesBean;
import com.eagle.mas.dao.AccessControlDAO;
import com.eagle.mas.model.MstRoleGroup;
import com.eagle.mas.model.MstRoleToGroup;
import com.eagle.mas.model.MstRoles;
import com.eagle.mas.model.Userdetails;
import com.eagle.mas.service.MstRoleGroupService;
import com.eagle.mas.service.MstRoleToGroupService;
import com.eagle.mas.service.RolesAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class AccessControlController {

	@Autowired
	RolesAccessService rolesAccessService;
	@Autowired
	AccessControlDAO accessControlDAO;
	@Autowired
	MstRoleGroupService mstRoleGroupService;
	@Autowired
	MstRoleToGroupService mstRoleToGroupService;

	@RequestMapping(value = "/userGroupCreation", method = RequestMethod.GET)
	public String createUserGroup(ModelMap model, @ModelAttribute("mstRolesBean") MstRolesBean mstRolesBean,
								  BindingResult result, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			if(session.getAttribute("userID")==null){
				return "redirect:loginPage";
			}
			System.out.println("createUserGroup test1********" );
			Iterable<String> mainMenu = rolesAccessService.findRoleName();
			model.addAttribute("mainMenu", mainMenu);
			Iterable<MstRoles> allRoles = rolesAccessService.findAllRoles();
			model.addAttribute("allRoles", allRoles );
		for (MstRoles allname:allRoles) {
				System.out.println("allRoles>>"+allname.getRoleDetails());
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("createUserGroup test1" );
		return "userGroupCreation";
	}

	@RequestMapping(value = "/editUserViewList", method = RequestMethod.GET)
	public String editUserViewGroupList(ModelMap model, @ModelAttribute("mstRolesBean") MstRolesBean mstRolesBean,
										BindingResult result, HttpServletRequest request) {
		try {
			System.out.println("createUserGroup test1" );


			//	HttpSession session = request.getSession(false);

		/*	Iterable<String> mainMenu = rolesAccessService.findRoleName();
			model.addAttribute("mainMenu", mainMenu);*/
			Iterable<MstRoles> allRoles = rolesAccessService.findAllRoles();

			model.addAttribute("allRoles", allRoles );
		for (MstRoles allname:allRoles) {
				System.out.println("allRoles>>"+allname.getRoleDetails());
			}
		} catch (Exception e) {
			System.out.println("createUserGroup exception" );
			e.printStackTrace();
		}
		System.out.println("createUserGroup end" );

		return "editUserViewGroupList";
	}

	@RequestMapping(value = "/createRoleForGroup", method = RequestMethod.POST)
	public String createRoleGroupToUser(ModelMap model,@RequestParam("selectedRoleGroups")ArrayList<String> selectedRoleGroups ,@ModelAttribute("mstRolesBean") MstRolesBean mstRolesBean,
			RedirectAttributes redirectAttributes,HttpServletRequest request) {
		try {
			System.out.println("Testing +++createRoleForGroup");
			HttpSession session = request.getSession(false);
			//session.setMaxInactiveInterval(600);
			//Userdetails user = (Userdetails) session.getAttribute("userdetails");
			MstRoleGroup mstRoleGroup = mstRolesBean.getMstRoleGroup();
			Set<MstRoleToGroup> rolegroupsNew = new HashSet<MstRoleToGroup>(0);
            for (Iterator<String> it1 = selectedRoleGroups.iterator(); it1.hasNext();) {
                String id = it1.next();
                MstRoleToGroup userrole = new MstRoleToGroup();
                MstRoles roles = rolesAccessService.findbyRoleId(id);
                userrole.setMstRoles(roles);
                rolegroupsNew.add(userrole);
            }

            int groupId = mstRoleGroupService.groupId();
            mstRoleGroup.setSno(groupId);
            mstRoleGroup.setGroupId(groupId);
            mstRoleGroup.setEnteredDate(new Date());
            mstRoleGroup.setActivestatus('1');
		//	mstRoleGroup.setEnteredBy(user.getUserid());
            
            boolean out = accessControlDAO.saveAll(mstRoleGroup);
            
            boolean out1 = true;
            System.out.println("print testing"+out);
           if(out) {
            for (Iterator<MstRoleToGroup> it = rolegroupsNew.iterator(); it.hasNext();) {
                MstRoleToGroup userToRole = it.next();
                int sno1 = mstRoleToGroupService.sno();
                userToRole.setSno(sno1);
                userToRole.setMstRoleGroup(mstRoleGroup);
                userToRole.setActive('1');   
                out1 = accessControlDAO.saveRoleToGroup(userToRole);
                if (out1) {
    				redirectAttributes.addFlashAttribute("successMessage",
    						"USER GROUP CREATED SUCCESSFULLY.");
					System.out.println("create user group");
    			} else {
    				redirectAttributes.addFlashAttribute("errorMessage", "ERROR WHILE CREATING USER GROUP");
    			}         
            }
           }
           
		System.out.println("create user group output");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return "redirect:/createUserGroup";
		return "userGroupCreation";
	}
	
	@RequestMapping(value = "/editUserGroup", method = RequestMethod.GET)
	public String editUserGroupView(ModelMap model, @ModelAttribute("mstRolesBean") MstRolesBean mstRolesBean,
			BindingResult result, HttpServletRequest request) {
		try {
		//	HttpSession session = request.getSession(false);
			//session.setMaxInactiveInterval(600);
			System.out.println("editUserGroup"  );
			Iterable<MstRoleGroup> loadRoleGroup = mstRoleGroupService.listRoleToGroup();
			model.addAttribute("loadRoleGroup", loadRoleGroup);
			System.out.println("s Role Group");
			for (MstRoleGroup allname:loadRoleGroup) {
				System.out.println("allRoles>>" + allname.getGroupName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "editUserGroup";
	}



	@RequestMapping(value = "/searchEditUserGroup", method = RequestMethod.POST)
	public String searchEditUserGroup(ModelMap model, @ModelAttribute("mstRolesBean") MstRolesBean mstRolesBean,
									  @RequestParam("groupId") Integer groupId, BindingResult result, HttpServletRequest request) {

		try {

		//	session.setMaxInactiveInterval(600);
			//MstRoleGroup mstRoleGroup = mstRolesBean.getMstRoleGroup();
			System.out.println("groupId >>>>>>>> <<<"+groupId);
			MstRoleGroup mstRoleGroup2 = mstRoleGroupService.findbygroupId(groupId);
			System.out.println("Testing"+mstRoleGroup2);
			model.addAttribute("groupName", mstRoleGroup2.getGroupName());
			model.addAttribute("groupId", mstRoleGroup2.getGroupId());
			System.out.println("Testing Group"+mstRoleGroup2);
			Iterable<String> mainMenu = rolesAccessService.findRoleName();
			model.addAttribute("mainMenu", mainMenu);
			System.out.println("Testing12"+mainMenu);
			Iterable<MstRoles> allRoles = rolesAccessService.findAllRoles();
			model.addAttribute("allRoles", allRoles);
			System.out.println("Testing34"+mstRoleGroup2);
			ArrayList<String> allRoles1 = rolesAccessService.findAllRolesId();
			model.addAttribute("allRoles1", allRoles1);
			System.out.println("Testing56"+allRoles1);
		} catch (Exception e) {
			System.out.println("Exception"+e);
			e.printStackTrace();
		}

		return "editUserGroupForRoles";
	}
	
	
	
	@RequestMapping(value = "/updateUserGroupEdit", method = RequestMethod.POST)
	public String updateUserGroupEdit(ModelMap model,@RequestParam("selectedRoleGroups")ArrayList<String> selectedRoleGroups ,@ModelAttribute("mstRolesBean") MstRolesBean mstRolesBean,
			RedirectAttributes redirectAttributes,HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			//session.setMaxInactiveInterval(600);
			System.out.println("<<<update User Grooup>>");
			Userdetails user = (Userdetails) session.getAttribute("userdetails");
			MstRoleGroup mstRoleGroup = mstRolesBean.getMstRoleGroup();
			Set<MstRoleToGroup> rolegroupsNew = new HashSet<MstRoleToGroup>(0);
            for (Iterator<String> it1 = selectedRoleGroups.iterator(); it1.hasNext();) {
                String id = it1.next();
                MstRoleToGroup userrole = new MstRoleToGroup();
                MstRoles roles = rolesAccessService.findbyRoleId(id);
                userrole.setMstRoles(roles);
                rolegroupsNew.add(userrole);
            }
            boolean out = true;
            List<MstRoleToGroup> existingRoles = mstRoleToGroupService.findbyAllRoles(mstRoleGroup.getGroupId());
            for (Iterator<MstRoleToGroup> it = existingRoles.iterator(); it.hasNext();) {
            	  MstRoleToGroup userToRole1 = it.next();           	
                  out = accessControlDAO.updateRoleToGroup(userToRole1);
            }
           
            
            boolean out1 = true;
            if(out) {
            for (Iterator<MstRoleToGroup> it = rolegroupsNew.iterator(); it.hasNext();) {
                MstRoleToGroup userToRole = it.next();
                int sno1 = mstRoleToGroupService.sno();
                userToRole.setSno(sno1);
                userToRole.setMstRoleGroup(mstRoleGroup);
                userToRole.setActive('1');                 
                out1 = accessControlDAO.saveRoleToGroup(userToRole);
                if (out1) {
    				redirectAttributes.addFlashAttribute("successMessage",
    						"USER GROUP UPDATED SUCCESSFULLY.");
    			} else {
    				redirectAttributes.addFlashAttribute("errorMessage", "ERROR WHILE CREATING USER GROUP");
    			}         
            }  
            }
		} catch (Exception e) {
			System.out.println("EXception"+ e);
			e.printStackTrace();
		}
		return "redirect:/editUserGroupView";
	}
}
