package com.eagle.mas.bean;

import java.io.Serializable;
import com.eagle.mas.model.MstRoleGroup;
import com.eagle.mas.model.MstRoleToGroup;
import com.eagle.mas.model.MstRolegroupToUser;
import com.eagle.mas.model.MstRoles;
import com.eagle.mas.model.Userdetails;

public class MstRolesBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MstRoles mstRoles;
	private MstRoleGroup mstRoleGroup;
	private MstRoleToGroup mstRoleToGroup;
	private MstRolegroupToUser mstRolegroupToUser;
	private Userdetails userdetails;
	private Iterable<String> activeRoles;
	public MstRoles getMstRoles() {
		return mstRoles;
	}
	public void setMstRoles(MstRoles mstRoles) {
		this.mstRoles = mstRoles;
	}
	public MstRoleGroup getMstRoleGroup() {
		return mstRoleGroup;
	}
	public void setMstRoleGroup(MstRoleGroup mstRoleGroup) {
		this.mstRoleGroup = mstRoleGroup;
	}
	public MstRoleToGroup getMstRoleToGroup() {
		return mstRoleToGroup;
	}
	public void setMstRoleToGroup(MstRoleToGroup mstRoleToGroup) {
		this.mstRoleToGroup = mstRoleToGroup;
	}
	public MstRolegroupToUser getMstRolegroupToUser() {
		return mstRolegroupToUser;
	}
	public void setMstRolegroupToUser(MstRolegroupToUser mstRolegroupToUser) {
		this.mstRolegroupToUser = mstRolegroupToUser;
	}
	public Userdetails getUserdetails() {
		return userdetails;
	}
	public void setUserdetails(Userdetails userdetails) {
		this.userdetails = userdetails;
	}
	public Iterable<String> getActiveRoles() {
		return activeRoles;
	}
	public void setActiveRoles(Iterable<String> activeRoles) {
		this.activeRoles = activeRoles;
	}

}
