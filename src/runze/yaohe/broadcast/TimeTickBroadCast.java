package runze.yaohe.broadcast;

import runze.yaohe.service.UpdateLocationService;
import runze.yaohe.util.ServiceUtil;
import runze.yaohe.util.SharedPreferencesUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimeTickBroadCast extends BroadcastReceiver{

	private static int flag=0;
	public TimeTickBroadCast() {
		super();
	}

	@Override
	public void onReceive(Context context, Intent arg1) {
		if(flag!=0){
			if(SharedPreferencesUtil.getTruckIsRegister(context)){
				if(!ServiceUtil.isRuning(context, "linpeng.app.service.UpdateLocationService")){
//					LogUtil.LogUtilInstance().addLog("����ر����¿���");
					Intent i = new Intent(context, UpdateLocationService.class); 
					context.startService(i); 
				}else{ 
//					LogUtil.LogUtilInstance().addLog("broadcast�����ѿ���");
				}
			}
		}else{
			flag=1;
		}
	}

}
