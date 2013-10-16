package runze.yaohe.netservice;

import java.util.List;

import runze.yaohe.constant.MyConstant;
import runze.yaohe.domain.Express;
import runze.yaohe.domain.Truck;
import runze.yaohe.util.JsonUtil;
import runze.yaohe.util.PostAndGetDateUtil;

import com.baidu.platform.comapi.basestruct.GeoPoint;


public class GetExpress {
	
	public static Express getExpressByImei(String imei){
		Express express=null;
		String url = MyConstant.URL+"/Truck2/GetExpressServlet?type=getExpressByImei&imei="+imei;
		String response = PostAndGetDateUtil.postAndGetDaet(url);
		if(response!=null&&!"null".equals(response)){
			express=JsonUtil.jsonToPostInfo(response);
		}
		return express;
	}
	
	public static List<Express> getExpressByCoordinate(GeoPoint geoPoint,int pageSize,int pageNow){
		String url = MyConstant.URL+"/Truck2/GetExpressServlet?type=getExpressByCoordinate&latitudeE6="+geoPoint.getLatitudeE6()
				+"&longitudeE6="+geoPoint.getLongitudeE6()+"&pageSize="+pageSize+"&pageNow="+pageNow;
		String jsonArray = PostAndGetDateUtil.postAndGetDaet(url); 
	
		List<Express> list=JsonUtil.jsonToExpressList(jsonArray);
		return list; 
	}

}
