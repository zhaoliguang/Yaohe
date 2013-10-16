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
					"BMapManager  ��ʼ������!", Toast.LENGTH_LONG).show();
		}
	}

	private static MyMapApplication getInstance() {
		return myMapApplication;
	}

	public static class MyGeneralListener implements MKGeneralListener{

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(MyMapApplication.getInstance().getApplicationContext(), "���������������",
						Toast.LENGTH_LONG).show();
			}
			else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(MyMapApplication.getInstance().getApplicationContext(), "������ȷ�ļ���������",
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
				//��ȨKey����
				Toast.makeText(MyMapApplication.getInstance().getApplicationContext(), 
						"����ʹ����ȷ�İٶȵ�ͼ��ȨKey��", Toast.LENGTH_LONG).show();
				MyMapApplication.getInstance().m_bKeyRight = false;
			}
		}
	}

}
