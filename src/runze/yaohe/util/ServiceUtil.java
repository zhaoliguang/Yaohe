package runze.yaohe.util;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.telephony.TelephonyManager;

public class ServiceUtil {
	public static String imei=null;
	public static String getImei(Context context){
		if(imei==null){
			TelephonyManager telephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
			imei = telephonyMgr.getDeviceId();
		}
		return imei;
	}

	public static boolean isRuning(Context mContext,String className ){
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager)
				mContext.getSystemService(Context.ACTIVITY_SERVICE); 
		List<ActivityManager.RunningServiceInfo> serviceList 
		= activityManager.getRunningServices(30);
		if (!(serviceList.size()>0)) {
			return false;
		}
		for (int i=0; i<serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

}
