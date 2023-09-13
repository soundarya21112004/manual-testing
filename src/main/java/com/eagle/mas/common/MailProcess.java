package com.eagle.mas.common;

import java.io.IOException;

import javax.activation.DataSource;

public class MailProcess {

	
	 public boolean sendmailprocess(String tomail, String subject, String mailMessage) {
	        boolean flag = false;
	        try {
	            SendEmail email = new SendEmail();
	            email.configfile();
	            flag = email.sendExternalMail(tomail, subject, mailMessage);

	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        return flag;
	    }
	 
	 
	 public boolean sendmailprocesswithattachment(String tomail, String subject, String mailMessage, String filename, DataSource aAttachement) {
	        boolean flag = false;
	        try {
	            SendEmail email = new SendEmail();
	            email.configfile();
	            flag = email.sendExternalMailWithAttachement(tomail, subject, mailMessage, filename, aAttachement);

	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        return flag;
	    }
}
