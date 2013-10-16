package runze.yaohe.constant;

import runze.yaohe.constant.MyConstant;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class MyMapApplication extends Application {

	private static MyMapApplication myMapApplication = null;
	public BMapManager bMapManager = null;
	public boolean m_bKeyRight = true;
	@Override
	public void onCreate() {
		super.onCreate();
		myMapApplication = this;
		init(this);
	}
	private void init(Context context) {
		if(bMapManager==null){
			bMapManager=new BMapManager(context);
		}
		if(!bMapManager.init(MyConstant.MAPKEY, new MyGeneralListener())){
			Toast.makeText(MyMapApplication.getInstance().getApplicationContext(), 
					"BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
		}
	}

	private static MyMapApplication getInstance() {
		return myMapApplication;
	}

	public static class MyGeneralListener implements MKGeneralListener{

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(MyMapApplication.getInstance().getApplicationContext(), "您的网络出错啦！",
						Toast.LENGTH_LONG).show();
			}
			else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(MyMapApplication.getInstance().getApplicationContext(), "输入正确的检索条件！",
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
				//授权Key错误：
				Toast.makeText(MyMapApplication.getInstance().getApplicationContext(), 
						"请在使用正确的百度地图授权Key！", Toast.LENGTH_LONG).show();
				MyMapApplication.getInstance().m_bKeyRight = false;
			}
		}
	}

}
