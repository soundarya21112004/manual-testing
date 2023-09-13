/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eagle.mas.common;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author boobalan
 */
public class CustomDate {

	public CustomDate() {
	}

	public String getFormatDate(Date dateValue, String format) {
		try {
			SimpleDateFormat sdfFormat = new SimpleDateFormat(format);
			return sdfFormat.format(dateValue);
		} catch (Exception e) {
			return "Invalid Format";
		}
	}

	public Date getParsedDate(String format, String strDate) {
		try {
			SimpleDateFormat sdfFormat = new SimpleDateFormat(format);
			return sdfFormat.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getAddedDate(String format, int value, String type) {
		try {
			SimpleDateFormat sdfFormat = new SimpleDateFormat(format);
			Calendar now = Calendar.getInstance();
			Calendar working;
			working = (Calendar) now.clone();
			if (type.equalsIgnoreCase("m")) {
				working.add(Calendar.MONTH, +value);
			} else if (type.equalsIgnoreCase("y")) {
				working.add(Calendar.YEAR, +value);
			}

			//// System.out.println(""+sdfFormat.format(working.getTime()));
			return sdfFormat.format(working.getTime());

		} catch (Exception e) {
			return "Invalid Format";
		}
	}

	public int dateDiff(String sdate1, String sdate2, String fmt, String type) {
		int returnValue = 0;
		TimeZone tz = null;
		SimpleDateFormat df = new SimpleDateFormat(fmt);
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = df.parse(sdate1);
			date2 = df.parse(sdate2);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		Calendar cal1 = null;
		Calendar cal2 = null;
		if (tz == null) {
			cal1 = Calendar.getInstance();
			cal2 = Calendar.getInstance();
		} else {
			cal1 = Calendar.getInstance(tz);
			cal2 = Calendar.getInstance(tz);
		}
		// different date might have different offset
		cal1.setTime(date1);
		long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);
		cal2.setTime(date2);
		long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);
		// Use integer calculation, truncate the decimals
		int hr1 = (int) (ldate1 / 3600000); // 60*60*1000
		int hr2 = (int) (ldate2 / 3600000);
		int days1 = (int) hr1 / 24;
		int days2 = (int) hr2 / 24;
		int dateDiff = days2 - days1;
		//// System.out.println("dateDiff==>"+dateDiff);
		int weekOffset = (cal2.get(Calendar.DAY_OF_WEEK) - cal1.get(Calendar.DAY_OF_WEEK)) < 0 ? 1 : 0;

		// Finding week difference
		int weekDiff = dateDiff / 7 + weekOffset;
		// Finding year difference
		int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
		// Finding month year
		int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
		if (type.equalsIgnoreCase("w")) {
			return weekDiff;
		} else if (type.equalsIgnoreCase("m")) {
			return monthDiff;
		} else if (type.equalsIgnoreCase("y")) {
			return yearDiff;
		} else if (type.equalsIgnoreCase("d")) {
			return dateDiff;
		} else {
			return 0;
		}
	}

	public int dateDiff(Date startDate, Date endDate, String type) {
		int returnValue = 0;
		TimeZone tz = null;

		Date date1 = startDate;
		Date date2 = endDate;
		Calendar cal1 = null;
		Calendar cal2 = null;
		if (tz == null) {
			cal1 = Calendar.getInstance();
			cal2 = Calendar.getInstance();
		} else {
			cal1 = Calendar.getInstance(tz);
			cal2 = Calendar.getInstance(tz);
		}
		// different date might have different offset
		cal1.setTime(date1);
		long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);
		cal2.setTime(date2);
		long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);
		// Use integer calculation, truncate the decimals
		int hr1 = (int) (ldate1 / 3600000); // 60*60*1000
		int hr2 = (int) (ldate2 / 3600000);
		int days1 = (int) hr1 / 24;
		int days2 = (int) hr2 / 24;
		int dateDiff = days2 - days1;
		//// System.out.println("dateDiff==>"+dateDiff);
		int weekOffset = (cal2.get(Calendar.DAY_OF_WEEK) - cal1.get(Calendar.DAY_OF_WEEK)) < 0 ? 1 : 0;

		// Finding week difference
		int weekDiff = dateDiff / 7 + weekOffset;
		// Finding year difference
		int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
		// Finding month year
		int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
		if (type.equalsIgnoreCase("w")) {
			return weekDiff;
		} else if (type.equalsIgnoreCase("m")) {
			return monthDiff;
		} else if (type.equalsIgnoreCase("y")) {
			return yearDiff;
		} else if (type.equalsIgnoreCase("d")) {
			return dateDiff;
		} else {
			return 0;
		}
	}

// //System.out.println("start date: "+objCustomDate.getFormatDate(dtmSentenceDetails.getScDateInserted(),format));
	public String dateCalcImp(String starDate, int days, String format, boolean isRemission) {
		String calcDate = "";
		int noOfDays = 0, calcDays = 0, remDays = 0;

		try {
			noOfDays = days;
			if (isRemission) {
				calcDays = noOfDays / 3;
			}
			remDays = noOfDays - calcDays;
			// calcDate = addDaysFromDate(format, starDate, remDays);

			// System.out.println("starDate : " + starDate);
			// System.out.println("noOfDays : " + noOfDays);
			// System.out.println("remDays : " + remDays);
			// System.out.println("calcDate : " + calcDate);

			return calcDate;
		} catch (Exception ex) {
			// System.out.println("test : " + ex.getMessage());
			return "";

		}
	}

	public String getLDRAndEDR(String SStarDate, int SDays, int SMonths, int Syears, int remission, String format) {
		String calcDate = "", LDR = "", EDR = "", returnDate = "";
		int totalDays = 0, temp = 0, calculatedDays = 0;

		// System.out.println("inside getLDRAndEDR");
//        Date inputdate=getParsedDate(format, SStarDate);
//        SStarDate=getFormatDate(inputdate,format);
		calcDate = SStarDate;

		try {

			if (Syears > 0) {
				calcDate = addDaysFromDate(calcDate, format, Syears, "y");
			}

			if (SMonths > 0) {
				calcDate = addDaysFromDate(calcDate, format, SMonths, "m");
			}

			if (SDays > 0) {
				calcDate = addDaysFromDate(calcDate, format, SDays, "d");
			}
			if (remission > 0) {
				calcDate = addDaysFromDate(calcDate, format, -remission, "d");
			}

			LDR = calcDate;
			totalDays = dateDiff(SStarDate, LDR, format, "d");
			if (totalDays > 30) {
				temp = totalDays / 3;
				calculatedDays = totalDays - temp;
				EDR = addDaysFromDate(SStarDate, format, calculatedDays, "d");
			} else {
				EDR = LDR;
			}
			int totalremission = temp + remission;
			returnDate = LDR + "~" + EDR + "~" + totalremission;

			return returnDate;
		} catch (Exception ex) {
			// System.out.println("calculateLDRAndEDR : " + ex.getMessage());
			return "";

		}

	}

	public String addDaysFromDate(String fromDate, String format, int value, String type) {
		try {

//            //System.out.println("inside addDaysFromDate");

			SimpleDateFormat sdfFormat = new SimpleDateFormat(format);
			// Calendar now = Calendar.getInstance();
			Calendar working = Calendar.getInstance();
			// working = (Calendar) now.clone();
			working.setTime(sdfFormat.parse(fromDate));
			if (type.equalsIgnoreCase("m")) {
				working.add(Calendar.MONTH, +value);
			} else if (type.equalsIgnoreCase("y")) {
				working.add(Calendar.YEAR, +value);
			} else if (type.equalsIgnoreCase("d")) {
				working.add(Calendar.DATE, +value);
			}

			//// System.out.println(""+sdfFormat.format(working.getTime()));
			return sdfFormat.format(working.getTime());

			// SimpleDateFormat sdf = new SimpleDateFormat(format);
//            Calendar c = Calendar.getInstance();
//            //c.setTime(new Date()); // Now use today date.
//            c.setTime(sdf.parse(fromDate));
//            c.add(Calendar.DATE, days);
//            String output = sdf.format(c.getTime());
//            //System.out.println(output);
//
//            return output;   

		} catch (Exception ex) {
			// System.out.println("addDaysFromDate : " + ex.getMessage());
			return "";
		}
	}

	public Date addDaysFromDate(Date fromDate, int value, String type) {
		try {

//            //System.out.println("inside addDaysFromDate");

			// Calendar now = Calendar.getInstance();
			Calendar working = Calendar.getInstance();
			// working = (Calendar) now.clone();
			working.setTime(fromDate);
			if (type.equalsIgnoreCase("m")) {
				working.add(Calendar.MONTH, +value);
			} else if (type.equalsIgnoreCase("y")) {
				working.add(Calendar.YEAR, +value);
			} else if (type.equalsIgnoreCase("d")) {
				working.add(Calendar.DATE, +value);
			}

			//// System.out.println(""+sdfFormat.format(working.getTime()));
			return working.getTime();

		} catch (Exception ex) {
			// System.out.println("addDaysFromDate : " + ex.getMessage());
			return null;
		}
	}
}
