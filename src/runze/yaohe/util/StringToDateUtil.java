package runze.yaohe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

public class StringToDateUtil {

	/**
	 * ��String ת������������  ��1991-10-11��--->1991-10-11������
	 * @param dateString  String
	 * @return  Date
	 */
	public static Date stringToDate(String dateString){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
		Date date=new Date();
		try {
			date=simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * ��String ת������������  ��1991-10-11 12��00��00��--->1991-10-11 12��00��00������
	 * @param dateString  String
	 * @return  Date
	 */
	public static Date stringToDateTime(String dateString){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
		Date date=new Date();
		try {
			date=simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * ��JsonObject ת������������  ��{year:1991,..}��--->1991-10-11������
	 * @param jsonObject  JSONObject
	 * @return  Date
	 */
	@SuppressWarnings("deprecation")
	public static Date jsonObjetToDate(JSONObject jsonObject){
		Date date;
		try {
			date = new Date(jsonObject.getInt("year"), jsonObject.getInt("month"), jsonObject.getInt("date"));
		} catch (JSONException e) {
			date=null;
			e.printStackTrace();
		}
		return date;
	} 
	/**
	 * ��JsonObject ת������������ ��{year:1991,..}��--->1991-10-11 12��00��00������
	 * @param jsonObject  JSONObject
	 * @return  Date
	 */
	@SuppressWarnings("deprecation")
	public static Date jsonObjetToDateTime(JSONObject jsonObject){
		Date date;
		try {
			date = new Date(jsonObject.getInt("year"), jsonObject.getInt("month"), jsonObject.getInt("date")
					,jsonObject.getInt("hours"),jsonObject.getInt("minutes"));
		} catch (JSONException e) {
			date=null;
			e.printStackTrace();
		}
		return date;
	}
}
