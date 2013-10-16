package runze.yaohe.netservice;

import runze.yaohe.constant.MyConstant;
import runze.yaohe.domain.ExpressLocation;
import runze.yaohe.domain.TruckLocation;
import runze.yaohe.util.JsonUtil;
import runze.yaohe.util.ObjectToUrlString;
import runze.yaohe.util.PostAndGetDateUtil;

public class LocationManage {
	public static boolean updateTruckLocation(TruckLocation truckLocation){
		String url = MyConstant.URL+"/Truck2/TruckLocationManageServlet?type=updateTruckLocation"+ObjectToUrlString.objectToUrlString(truckLocation);
		String response = PostAndGetDateUtil.postAndGetDaet(url);
		boolean success = JsonUtil.isSuccess(response);
		return success;
	}
	public static boolean updateExpressLocation(ExpressLocation expressLocation){
		String url = MyConstant.URL+"/Truck2/ExpressManageServlet?type=updateExpressLocation"+ObjectToUrlString.objectToUrlString(expressLocation);
		String response = PostAndGetDateUtil.postAndGetDaet(url);
		boolean success = JsonUtil.isSuccess(response);
		return success;
	}
}
