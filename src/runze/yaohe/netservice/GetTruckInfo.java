package runze.yaohe.netservice;


import runze.yaohe.constant.MyConstant;
import runze.yaohe.domain.Truckinfo;
import runze.yaohe.util.JsonUtil;
import runze.yaohe.util.PostAndGetDateUtil;

public class GetTruckInfo {

	public static Truckinfo getTruckInfoByImei(String imei){
		Truckinfo truckinfo=null;
		String url = MyConstant.URL+"/Truck2/GetTruckinfoServlt?type=getTruckByImei&imei="+imei;
		String response = PostAndGetDateUtil.postAndGetDaet(url);
		if(response!=null&&!"null".equals(response)){
			truckinfo=JsonUtil.jsonToTruckInfo(response);
		}
		return truckinfo;
	}
}
