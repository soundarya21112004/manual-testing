package com.eagle.mas.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacAddress {
	
	
	  public static String MacAddressWindows() throws IOException {
	        String macAddress = null;
	        String command = "ipconfig /all";
	        Process pid = Runtime.getRuntime().exec(command);
	        BufferedReader in = new BufferedReader(new InputStreamReader(pid.getInputStream()));
	        boolean ethernet = false;
	        while (true) {
	            String line = in.readLine();
	            if (line == null) {
	                break;
	            }
	            if (line.startsWith("Ethernet adapter")) {
	                ethernet = true;
	            }
	            if (ethernet) {
	                Pattern p = Pattern.compile(".*Physical Address.*: (.*)");
	                Matcher m = p.matcher(line);
	                if (m.matches()) {
	                    macAddress = m.group(1);
	                    break;
	                }
	            }
	        }
	        System.out.println("Mac id :" + macAddress);
	        in.close();
	        return macAddress;
	    }


	  public String MacAddressLinux() {
	        String line = null;
	     
	        try {
	            String macAddress = null;
	            String command = "/sbin/ifconfig";
	            Pattern p = Pattern.compile("([a-fA-F0-9]{1,2}(-|:)){5}[a-fA-F0-9]{1,2}");
	            Process pa = Runtime.getRuntime().exec(command);
	            pa.waitFor();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    pa.getInputStream()));

	            Matcher m;
	            while ((line = reader.readLine()) != null) {

	                m = p.matcher(line);

	                if (!m.find()) {
	                    continue;
	                }
	                line = m.group();
	                break;

	            }
	        } catch (Exception e) {
	        }
	        return line;
	    }
	  
	  
	  public String MacAddress() throws IOException {
	        String line = null;
	        String command = "/sbin/ifconfig";
	        try {
	            String sOsName = System.getProperty("os.name");
	            if (sOsName.startsWith("Windows")) {
	                command = "ipconfig /all";
	                line = MacAddressWindows();
	            } else {

	                if ((sOsName.startsWith("Linux")) || (sOsName.startsWith("Mac"))
	                        || (sOsName.startsWith("HP-UX"))) {
	                    command = "/sbin/ifconfig";
	                    line = MacAddressLinux();
	                } else {
	                    System.out.println("The current operating system '" + sOsName
	                            + "' is not supported.");
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return line;
	    }
	  
	  public String md5Encode(byte[] src)
	            throws NoSuchAlgorithmException {
	        StringBuilder sb = new StringBuilder();
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] dst = md.digest(src);

	        for (int i = 0; i < dst.length; i++) {
	            sb.append(Integer.toHexString(dst[i] >> 4 & 0xF));
	            sb.append(Integer.toHexString(dst[i] & 0xF));
	        }

	        return sb.toString();
	    }
	  
}
