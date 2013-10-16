package runze.yaohe.netservice;

import java.util.List;

import runze.yaohe.constant.MyConstant;
import runze.yaohe.domain.Truck;
import runze.yaohe.util.JsonUtil;
import runze.yaohe.util.PostAndGetDateUtil;

import com.baidu.platform.comapi.basestruct.GeoPoint;

public class GetTruck {

	public static List<Truck> getTruckByDistrict(String district){
		String url = MyConstant.URL+"/Truck2/GetTruckServlet?type=getTruckByDistrict&district=°üºÓÇø";
		String jsonArray = PostAndGetDateUtil.postAndGetDaet(url); 
		List<Truck> list=JsonUtil.jsonToTruckList(jsonArray);
		return list; 
	}
	public static List<Truck> getTruckByCoordinate(GeoPoint geoPoint,int pageSize,int pageNow){
		String url = MyConstant.URL+"/Truck2/GetTruckServlet?type=getTruckByCoordinate&latitudeE6="+geoPoint.getLatitudeE6()
				+"&longitudeE6="+geoPoint.getLongitudeE6()+"&pageSize="+pageSize+"&pageNow="+pageNow;
		String jsonArray = PostAndGetDateUtil.postAndGetDaet(url); 
		List<Truck> list=JsonUtil.jsonToTruckList(jsonArray);
		return list; 
	}
}
