package com.gstsgy.base.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author zpj
 * @Date 2020/07/16 10:21
 * @Description
 **/
public class DateUtil {


	/**
	 * 日期时间格式:yyyy-MM-dd HH:mm:ss
	 */
	public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");// 日期时间格式
	/**
	 * 日期时间字符串格式:yyyyMMddHHmmss
	 */
	public static final DateTimeFormatter dateTimeStrFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");// 日期时间字符串格式
	/**
	 * 日期格式：yyyy-MM-dd
	 */
	public static final DateTimeFormatter dateFormaat = DateTimeFormatter.ofPattern("yyyy-MM-dd");// 日期格式

	/**
	 * 时间格式:HH:mm:ss
	 */
	public static final DateTimeFormatter timeFormaat = DateTimeFormatter.ofPattern("HH:mm:ss");// 时间格式


	/**
	 * 日期格式 yyMMdd
	 **/
	public static final DateTimeFormatter YYMMddFormat = DateTimeFormatter.ofPattern("yyMMdd");



	public static String format(LocalDateTime dateTime, String formatPattern) {
		if (dateTime == null) {
			return "";
		}
		DateTimeFormatter f = DateTimeFormatter.ofPattern(formatPattern);
		return dateTime.format(f);
	}

	public static String format(LocalDate date, String formatPattern) {
		if (date == null) {
			return "";
		}
		DateTimeFormatter f = DateTimeFormatter.ofPattern(formatPattern);
		return date.format(f);
	}


	public static String getDateTime(){
		return LocalDateTime.now().format(dateTimeFormat);
	}

	public static String getDate(){
		return LocalDate.now().format(dateFormaat);
	}

	public static String getTime(){
		return LocalTime.now().format(timeFormaat);
	}



	public static String getSeq(){
		return LocalDateTime.now().format(dateTimeStrFormat);
	}

	public static String getYYMMdd(){
		return LocalDate.now().format(YYMMddFormat);
	}

	/**
	 * 字符串转为时间
	 * @Author zpj
	 * @Date 2020/07/16 10:14
	 * @Description
	 **/
	public static LocalDate parseDate(String date,String format)  {
		DateTimeFormatter f = DateTimeFormatter.ofPattern(format);
    	return LocalDate.parse(date,f);
	}

	/**
	 * 字符串转为时间
	 * @Author zpj
	 * @Date 2020/07/16 10:14
	 * @Description
	 **/
	public static LocalDateTime parseDateTime(String date,String format){
		DateTimeFormatter f = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.parse(date,f);
	}

	/**
	 * 字符串转为时间
	 * @Author zpj
	 * @Date 2020/07/16 10:14
	 * @Description
	 **/
	public static LocalTime parseTime(String time,String format)  {
		DateTimeFormatter f = DateTimeFormatter.ofPattern(format);
		return LocalTime.parse(time,f);
	}
}
