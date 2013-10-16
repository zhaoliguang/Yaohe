package runze.yaohe.service;

import runze.yaohe.domain.ExpressLocation;
import runze.yaohe.domain.TruckLocation;
import runze.yaohe.netservice.LocationManage;
import runze.yaohe.util.ServiceUtil;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UpdateLocationService extends Service{

	private LocationClient locationClient;
	private GeoPoint geoPoint;
	private static int i=0;
	private static int j=0;
	private String city;
	private String district;
	private String street;
	private GeoPoint last_geopoint=new GeoPoint(0, 0);
	

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		initLocationClient();
		Thread thread=new Thread(new MyRun());
		thread.start();
	}

	/**
	 * ��ʼ��λ�ü�������
	 */
	private void initLocationClient() {
		geoPoint=new GeoPoint(0, 0);
		locationClient = new LocationClient(this);
		locationClient.registerLocationListener(new MyLocationListener());
		LocationClientOption option = new LocationClientOption();
		option.setAddrType("all");//���صĶ�λ���������ַ��Ϣ
		option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02	
		locationClient.setLocOption(option);
		locationClient.start();
	}

	/**
	 * λ�ü�����Ӧ��
	 * @author lp
	 *
	 */
	public class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation bdLocation) {
			city=bdLocation.getCity();
			district=bdLocation.getDistrict();
			street=bdLocation.getStreet();
			geoPoint.setLatitudeE6((int) (bdLocation.getLatitude()*1E6)); 
			geoPoint.setLongitudeE6((int) (bdLocation.getLongitude()*1E6));
//			LogUtil.LogUtilInstance().addLog("λ�ø���"+"la"+bdLocation.getLatitude()*1E6+"lo"+bdLocation.getLongitude()*1E6);
		} 

		@Override
		public void onReceivePoi(BDLocation bdLocation) {
		}

	}

	/**
	 * ��̨���������߳�
	 * @author lp
	 *
	 */
	public class MyRun implements Runnable{
		@Override
		public void run() {
			while(true){
				try { 
					locationClient.requestLocation();    
					Thread.sleep(10000);
//					LogUtil.LogUtilInstance().addLog("˯600��ǰ�����·�����ǰ�ж�λ��"+geoPoint.getLatitudeE6()+"  "+geoPoint.getLongitudeE6());
					if(geoPoint.getLatitudeE6()>21845937){
						int distance=Math.abs(last_geopoint.getLatitudeE6()-geoPoint.getLatitudeE6())+Math.abs(last_geopoint.getLongitudeE6()-geoPoint.getLongitudeE6());
						if(distance>500){
							boolean truckSuccess=LocationManage.updateTruckLocation(new TruckLocation(ServiceUtil.getImei(UpdateLocationService.this)
									,geoPoint.getLatitudeE6(), geoPoint.getLongitudeE6(),city,district,street));
							boolean expressSuccess=LocationManage.updateExpressLocation(new ExpressLocation(ServiceUtil.getImei(UpdateLocationService.this)
									,geoPoint.getLatitudeE6(), geoPoint.getLongitudeE6(),city,district,street));
							System.out.println("����λ�ã�"+city+district+street);
							
							if(truckSuccess||expressSuccess){
								last_geopoint=geoPoint;
								
								i++;   
							}else{
								j++;
							}
						}
					}else{
//						LogUtil.LogUtilInstance().addLog("λ�û�ȡ����"+geoPoint.getLatitudeE6()+"   "+geoPoint.getLongitudeE6());
						j=j+10000;
						i=i+10000;
					}
					Thread.sleep(600000);
//					LogUtil.LogUtilInstance().addLog("˯��600���");
				} catch (InterruptedException e) {
//					LogUtil.LogUtilInstance().addLog("����"+e.getMessage());
					Thread thread=new Thread(new MyRun());
					thread.start();
//					LogUtil.LogUtilInstance().addLog("�߳���������");
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
//		LogUtil.LogUtilInstance().addLog("service destroy");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	} 
}

