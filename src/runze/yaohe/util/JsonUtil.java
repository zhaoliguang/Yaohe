package runze.yaohe.util;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import runze.yaohe.domain.Express;
import runze.yaohe.domain.Truck;
import runze.yaohe.domain.Truckinfo;

public class JsonUtil {

	public static boolean isSuccess(String response){
		boolean success=false;
		try{
			JSONObject jsonObject=new JSONObject(response);
			String is_success=jsonObject.getString("success");
			if(is_success.equals("true")){
				success=true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public static Truckinfo jsonToTruckInfo(String jsonString){
		Truckinfo truckinfo=new Truckinfo();
		try{
			JSONObject jsonObject=new JSONObject(jsonString);
			Method methods[]=truckinfo.getClass().getMethods();
			for(Method method:methods){
				if(method.getName().contains("set")){
					if(method.getParameterTypes()[0].getSimpleName().equals("Integer")){
						int value = (Integer) jsonObject.get(method.getName().substring(3,4).toLowerCase()+method.getName().substring(4));
						method.invoke(truckinfo, value);
					}else if(method.getParameterTypes()[0].getSimpleName().equals("Timestamp")){
						JSONObject jsonObjectValue = (JSONObject) jsonObject.get(method.getName().substring(3,4).toLowerCase()+method.getName().substring(4));
						Date date=StringToDateUtil.jsonObjetToDateTime(jsonObjectValue);
						method.invoke(truckinfo, new Timestamp(date.getTime()));
					}else{
						String value = (String) jsonObject.get(method.getName().substring(3,4).toLowerCase()+method.getName().substring(4));
						method.invoke(truckinfo, value);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return truckinfo;
	}
	public static Express jsonToPostInfo(String jsonString){
		Express express=new Express();
		try{
			JSONObject jsonObject=new JSONObject(jsonString);
			Method methods[]=express.getClass().getMethods();
			for(Method method:methods){
				if(method.getName().contains("set")){
					if(method.getParameterTypes()[0].getSimpleName().equals("Integer")){
						int value = (Integer) jsonObject.get(method.getName().substring(3,4).toLowerCase()+method.getName().substring(4));
						method.invoke(express, value);
					}else if(method.getParameterTypes()[0].getSimpleName().equals("Timestamp")){
						JSONObject jsonObjectValue = (JSONObject) jsonObject.get(method.getName().substring(3,4).toLowerCase()+method.getName().substring(4));
						Date date=StringToDateUtil.jsonObjetToDateTime(jsonObjectValue);
						method.invoke(express, new Timestamp(date.getTime()));
					}else{
						String value = (String) jsonObject.get(method.getName().substring(3,4).toLowerCase()+method.getName().substring(4));
						method.invoke(express, value);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return express;
	}
	public static Truck jsonToTruck(String jsonString){
		Truck truck=new Truck();
		try{
			JSONObject jsonObject=new JSONObject(jsonString);
			Method methods[]=truck.getClass().getMethods();
			for(Method method:methods){
				if(method.getName().contains("set")){
					if(method.getParameterTypes()[0].getSimpleName().equals("Integer")){
						int value = (Integer) jsonObject.get(method.getName().substring(3,4).toLowerCase()+method.getName().substring(4));
						method.invoke(truck, value);
					}else if(method.getParameterTypes()[0].getSimpleName().equals("Timestamp")){
						JSONObject jsonObjectValue = (JSONObject) jsonObject.get(method.getName().substring(3,4).toLowerCase()+method.getName().substring(4));
						Date date=StringToDateUtil.jsonObjetToDateTime(jsonObjectValue);
						method.invoke(truck, new Timestamp(date.getTime()));
					}else{
						String value = (String) jsonObject.get(method.getName().substring(3,4).toLowerCase()+method.getName().substring(4));
						method.invoke(truck, value);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return truck;
	}
	public static Express jsonToExpress(String jsonString){
		Express express=new Express();
		try{
			JSONObject jsonObject=new JSONObject(jsonString);
			Method methods[]=express.getClass().getMethods();
			for(Method method:methods){
				if(method.getName().contains("set")){
					if(method.getParameterTypes()[0].getSimpleName().equals("Integer")){
						int value = (Integer) jsonObject.get(method.getName().substring(3,4).toLowerCase()+method.getName().substring(4));
						method.invoke(express, value);
					}else if(method.getParameterTypes()[0].getSimpleName().equals("Timestamp")){
						JSONObject jsonObjectValue = (JSONObject) jsonObject.get(method.getName().substring(3,4).toLowerCase()+method.getName().substring(4));
						Date date=StringToDateUtil.jsonObjetToDateTime(jsonObjectValue);
						method.invoke(express, new Timestamp(date.getTime()));
					}else{
						String value = (String) jsonObject.get(method.getName().substring(3,4).toLowerCase()+method.getName().substring(4));
						method.invoke(express, value);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return express;
	}
	public static List<Truck> jsonToTruckList(String jsonString){
		List<Truck> trucks=new ArrayList<Truck>();
		try{
			JSONArray jsonArray=new JSONArray(jsonString);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject=jsonArray.getJSONObject(i);
                Truck truck=jsonToTruck(jsonObject.toString());
                trucks.add(truck);
			}
		}catch (Exception e) {
				e.printStackTrace();
		}
		return trucks;
	}
	
	public static List<Express> jsonToExpressList(String jsonString){
		List<Express> expressList=new ArrayList<Express>();
		try{
			JSONArray jsonArray=new JSONArray(jsonString);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject=jsonArray.getJSONObject(i);
                Express express=jsonToExpress(jsonObject.toString());
                expressList.add(express);
			}
		}catch (Exception e) {
				e.printStackTrace();
		}
		return expressList;
	}

}
