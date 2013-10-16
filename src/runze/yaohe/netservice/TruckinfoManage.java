package runze.yaohe.netservice;

import runze.yaohe.constant.MyConstant;
import runze.yaohe.domain.Truckinfo;
import runze.yaohe.util.JsonUtil;
import runze.yaohe.util.ObjectToUrlString;
import runze.yaohe.util.PostAndGetDateUtil;

public class TruckinfoManage {
	
	public static int addTruckinfo(Truckinfo truckinfo){
		int flag;
		String url = MyConstant.URL+"/Truck2/TruckinfoManageServlet?type=addTruckinfo"+ObjectToUrlString.objectToUrlString(truckinfo);
		String response = PostAndGetDateUtil.postAndGetDaet(url);
		if("connect_error".equals(response)){
			flag=MyConstant.CONNECT_ERROR;
		}else{
			if(JsonUtil.isSuccess(response)){
				flag=MyConstant.SUCCESS;
			}else{
				flag=MyConstant.FAIL;
			}
		}
		return flag;
	}

}
