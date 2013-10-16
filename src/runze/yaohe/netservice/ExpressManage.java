package runze.yaohe.netservice;

import runze.yaohe.constant.MyConstant;
import runze.yaohe.domain.Express;
import runze.yaohe.util.JsonUtil;
import runze.yaohe.util.ObjectToUrlString;
import runze.yaohe.util.PostAndGetDateUtil;

public class ExpressManage {
	public static int addExpress(Express express){
		int flag;
		String url = MyConstant.URL+"/Truck2/ExpressManageServlet?type=addExpress"+ObjectToUrlString.objectToUrlString(express);
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
