package runze.yaohe.netservice;

import runze.yaohe.constant.MyConstant;
import runze.yaohe.domain.TruckIsLoaded;
import runze.yaohe.util.JsonUtil;
import runze.yaohe.util.ObjectToUrlString;
import runze.yaohe.util.PostAndGetDateUtil;

public class TruckIsLoadedManage {

	public static boolean updateTruckIsLoad(TruckIsLoaded truckIsLoaded){
		String url = MyConstant.URL+"/Truck2/TruckIsLoadedServlet?type=updateTruckIsLoaded"+ObjectToUrlString.objectToUrlString(truckIsLoaded);
		String response = PostAndGetDateUtil.postAndGetDaet(url);
		boolean success = JsonUtil.isSuccess(response);
		return success;
	}
}
