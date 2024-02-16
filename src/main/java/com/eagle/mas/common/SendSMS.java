/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eagle.mas.common;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


/**
 *
 * @author Santhosh
 */
public class SendSMS {

    String strServerName;
    String strServerPort;
    String strUserName;
    String strPassword;
    String shortCode;
    
   // final static Logger log = Logger.getLogger(SendSMS.class);

    public void configfile() throws IOException {
       // log.setLevel(Level.INFO);
        try {
			/*
			 * File catalinaBase = new
			 * File(System.getProperty("catalina.base")).getAbsoluteFile(); File
			 * propertyFile = new File(catalinaBase, "bin/Conf/sms_rsb.conf");
			 */
        	File propertyFile = new File("bin/Conf/sms_rsb.conf");
            InputStream in = new FileInputStream(propertyFile);
            ResourceBundle resource = new PropertyResourceBundle(in);

            strServerName = resource.getString("Sms.IP");
            strServerPort = resource.getString("Sms.Port");
            strUserName = resource.getString("Sms.Username");
            strPassword = resource.getString("Sms.Password");

        } catch (IOException e) {
			/*
			 * log.setLevel(Level.ERROR); log.error("configfile", e);
			 */
            e.printStackTrace();
			/*
			 * ErrorLog errorLog = new ErrorLog(); errorLog.writeError("SendSMS",
			 * "configfile", e);
			 */
        }
    }

    public boolean sendExternalSMS(String tm, String mailMsg) {
       // log.setLevel(Level.INFO);
        boolean flag = false;
        String to = tm;
        final String username = strUserName;
        final String password = strPassword;

        String host = strServerName;
        String port = strServerPort;

        try{

            String requestUrl = "http://" + host + ":" + port + "/smshttpquery/qs?"
                    + "REQUESTTYPE=SMSSubmitReq&USERNAME=" + URLEncoder.encode(username, "UTF-8")
                    + "&PASSWORD=" + URLEncoder.encode(password, "UTF-8")
                    + "&ORIGIN_ADDR=RSB&TYPE=0"
                    + "&MOBILENO=" + to
                    + "&MESSAGE=" + URLEncoder.encode(mailMsg, "UTF-8");

            System.out.println("requestUrl :" + requestUrl);
           // ErrorLog.writeErrorString("New Registration", "SMS message URL", requestUrl);

            URL url = new URL(requestUrl);

            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            System.out.println(uc.getResponseMessage());
           // ErrorLog.writeErrorString("New Registration", "SMS message Response", uc.getResponseMessage());
            uc.disconnect();

           // ErrorLog.writeErrorString("New Registration", "SMS message", "Sent SMS successfully....");

            flag = true;
        } catch (java.net.ConnectException e) {
			/*
			 * log.setLevel(Level.ERROR); log.error("sendExternalSMS", e);
			 * ErrorLog.writeErrorString("New Registration", "Exception",
			 * "Connection time out");
			 */
            e.printStackTrace();

        } catch (MalformedURLException ex) {
			/*
			 * log.setLevel(Level.ERROR); log.error("sendExternalSMS", ex);
			 * ErrorLog.writeErrorString("New Registration", "Exception", ex.getMessage());
			 */
        } catch (IOException ex) {
           // ErrorLog.writeErrorString("New Registration", "Exception", ex.getMessage());
        }
        return flag;
    }
}
