package runze.yaohe.ui;
import java.util.ArrayList;
import java.util.List;

import runze.yaohe.constant.MyConstant;
import runze.yaohe.constant.MyGlobal;
import runze.yaohe.constant.MyMapApplication;
import runze.yaohe.domain.Express;
import runze.yaohe.domain.Truck;
import runze.yaohe.netservice.GetExpress;
import runze.yaohe.netservice.GetTruck;
import runze.yaohe.util.ActivityUtil;
import runzhe.yaohe.map.ExpressItemizedOverlay;
import runzhe.yaohe.map.ExpressOverlayItem;
import runzhe.yaohe.map.TruckItemizedOverlay;
import runzhe.yaohe.map.TruckOverlayItem;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.runze.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class CustomerMapActivity extends Activity {

	private TruckAsyncTask truckAsyncTask;
	private ExpressAsyncTask expressAsyncTask;
	private MapView mapView;
	private MapController mapController;
	private LocationClient locationClient;
	private GeoPoint geoPoint;
	private MyLocationOverlay myLocationOverlay;
	private List<Truck> truckList;
	private List<Express> expressList;
	private List<TruckOverlayItem> truckOverlayItems=new ArrayList<TruckOverlayItem>();
	private List<ExpressOverlayItem> expressOverlayItems=new ArrayList<ExpressOverlayItem>();
	private TruckItemizedOverlay truckItemizedOverlay;
	private ExpressItemizedOverlay expressItemizedOverlay;
	private SharedPreferences sharedPreferences;
	private LocationData myLocationData;
	private boolean isTruckTaskRuning=false;    //是否线程执行true--->有
	private boolean isExpressTaskRuning=false;
	private MyMapApplication myMapApplication;
	private Drawable drawable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("onCreate");
		myMapApplication = (MyMapApplication)this.getApplication();
		if (myMapApplication.bMapManager == null) {
			myMapApplication.bMapManager = new BMapManager(this);
			myMapApplication.bMapManager.init(MyConstant.MAPKEY,new MyMapApplication.MyGeneralListener());
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_customer_map);
		truckAsyncTask= new TruckAsyncTask();
		expressAsyncTask=new ExpressAsyncTask();
		initMapData();
		initLocation();
		
		mapController.setZoom(15);
		ActivityUtil.getInstance().setCurrent_activity(this);
	}

	/**
	 * 初始化位置数据
	 */
	private void initMapData() {
		geoPoint=new GeoPoint(0, 0);
		sharedPreferences=getSharedPreferences("settings", 0);
		geoPoint.setLatitudeE6(sharedPreferences.getInt("latitudeE6",(int)(33.33321*1E6)));
		geoPoint.setLongitudeE6(sharedPreferences.getInt("longitudeE6", (int)(116.404*1E6)));
		myLocationData=new LocationData();
		myLocationData.latitude=geoPoint.getLatitudeE6()/1E6;
		myLocationData.longitude=geoPoint.getLongitudeE6()/1E6;
	}

	/**
	 * 初始化位置相关信息
	 */
	private void initLocation() {
		mapView=(MapView)findViewById(R.id.mapView);
		mapController=mapView.getController();
		locationClient = new LocationClient(this);
		locationClient.registerLocationListener(new MyLocationListener());
		LocationClientOption option = new LocationClientOption();
		option.setAddrType("all");//返回的定位结果包含地址信息
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02	
		option.setScanSpan(10000);
		locationClient.setLocOption(option);
		locationClient.start();
		locationClient.requestLocation();
		myLocationOverlay=new MyLocationOverlay(mapView);
		myLocationOverlay.setData(myLocationData);
		Drawable myLocation =getResources().getDrawable(R.drawable.map_my_location);
		myLocationOverlay.setMarker(myLocation);
		mapView.getOverlays().add(myLocationOverlay);
		mapController.setCenter(geoPoint);
		mapController.animateTo(geoPoint);
		mapView.getController().setZoom(12);
		mapView.setBuiltInZoomControls(true);
		mapView.regMapViewListener(myMapApplication.bMapManager, new MyMKMapViewListener());
		drawable=getResources().getDrawable(R.drawable.map_truck_empty);
		truckItemizedOverlay=new TruckItemizedOverlay(drawable, mapView);
		drawable=getResources().getDrawable(R.drawable.map_express);
		expressItemizedOverlay=new ExpressItemizedOverlay(drawable, mapView);
		mapView.getOverlays().add(truckItemizedOverlay);
		mapView.getOverlays().add(expressItemizedOverlay);
		mapView.refresh();
	}


	/**
	 * 监听返回键
	 * @param view
	 */
	public void TruckExpressList(View view){
		Intent intent=new Intent(this,TruckExpressActivity.class);
		//Intent intent=new Intent(this,TruckListActivity.class);
		MyGlobal.truckOverlayItems=truckOverlayItems;
		MyGlobal.expressOverlayItems=expressOverlayItems;
		MyGlobal.myLocation=geoPoint;
		startActivity(intent);
	}

	public void getMyLocation(View view){
		mapController.animateTo(geoPoint);
		truckAsyncTask=new TruckAsyncTask();
		expressAsyncTask=new ExpressAsyncTask();
		if(!isTruckTaskRuning){
			truckAsyncTask.execute(geoPoint);
		}
		if(!isExpressTaskRuning){
			expressAsyncTask.execute(geoPoint);
		}
	}

//	public void getMsg(View view){
//		if(!isTruckTaskRuning){
//			truckAsyncTask.execute(geoPoint);
//		}
//		if(!isExpressTaskRuning){
//			expressAsyncTask.execute(geoPoint);
//		}
//	}

	/**
	 * 用户位置信息监听
	 * @author lp
	 *
	 */
	public class MyLocationListener implements BDLocationListener{
		@Override
		public void onReceiveLocation(BDLocation bdLocation) {
			geoPoint.setLatitudeE6((int) (bdLocation.getLatitude()*1E6));
			geoPoint.setLongitudeE6((int) (bdLocation.getLongitude()*1E6));
			LocationData locationData=new LocationData();
			locationData.latitude=bdLocation.getLatitude();
			locationData.longitude=bdLocation.getLongitude();
			myLocationOverlay.setData(locationData);
			mapController.setCenter(geoPoint);
			mapController.animateTo(geoPoint);
			if(mapView!=null){
				try{
					mapView.refresh();
				}catch (Exception e) {
				}
			}
		}
		@Override
		public void onReceivePoi(BDLocation bdLocation) {

		}
	}
	//请求卡车位置
	public class TruckAsyncTask extends AsyncTask<GeoPoint, String, Boolean>{
		@Override
		protected Boolean doInBackground(GeoPoint... geoPoints) {
			System.out.println("start");
			isTruckTaskRuning=true;
			truckList=GetTruck.getTruckByCoordinate(geoPoints[0], 20,1);
			return null;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			System.out.println("in");
			try{
				if(truckList.size()!=0){
					System.out.println("地图请求卡车数量："+truckList.size());
					truckOverlayItems.clear();
					truckItemizedOverlay.removeOverLayItem(truckOverlayItems);
					drawable=getResources().getDrawable(R.drawable.map_truck_loaded);
					for(Truck truck:truckList){
						GeoPoint tempGeoPoint=new GeoPoint(truck.getLatitudeE6(),truck.getLongitudeE6());
						TruckOverlayItem myOverlayItem=new TruckOverlayItem(tempGeoPoint, truck.getCity(), truck.getCity(),truck);
						if(truck.getIsLoaded().equals("1")){
							myOverlayItem.setMarker(drawable);
						}
						truckOverlayItems.add(myOverlayItem);
					}
					truckItemizedOverlay.addAllOverlayItem(truckOverlayItems);
					mapView.refresh();
					
				}else{
					if(MyGlobal.CONNECT_ERROR_TRUCK_LIST){
						Toast.makeText(CustomerMapActivity.this, "抱歉网络不通", Toast.LENGTH_SHORT).show();
						MyGlobal.CONNECT_ERROR_TRUCK_LIST=false;
					}
				}
			}catch (Exception e) {
			}
			isTruckTaskRuning=false;
		}

	}
	//请求快递员位置
	public class ExpressAsyncTask extends AsyncTask<GeoPoint, String, Boolean>{
		@Override
		protected Boolean doInBackground(GeoPoint... geoPoints) {
			isExpressTaskRuning=true;
			expressList=GetExpress.getExpressByCoordinate(geoPoints[0], 20,1);
			System.out.println("expressList"+expressList.size());
			return null;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			System.out.println("in");
			
			try{
				if(expressList.size()!=0){
					System.out.println("邮递员数量："+expressList.size());
					expressOverlayItems.clear();
					expressItemizedOverlay.removeOverLayItem(expressOverlayItems);
					Drawable expressDrawable=getResources().getDrawable(R.drawable.map_express);
					for(Express express:expressList){
						GeoPoint tempGeoPoint=new GeoPoint(express.getLatitudeE6(),express.getLongitudeE6());
						ExpressOverlayItem myOverlayItem=new ExpressOverlayItem(tempGeoPoint, express.getCity(), express.getCity(),express);
						expressOverlayItems.add(myOverlayItem);
					}
						expressItemizedOverlay.addAllOverlayItem(expressOverlayItems);
						mapView.refresh();
				}else{
					if(MyGlobal.CONNECT_ERROR_EXPRESS_LIST){
						Toast.makeText(CustomerMapActivity.this, "抱歉网络不通", Toast.LENGTH_SHORT).show();
						MyGlobal.CONNECT_ERROR_EXPRESS_LIST=false;
					}
				}
			}catch (Exception e) {
				
			}
			isExpressTaskRuning=false;
		}

	}

	public class MyMKMapViewListener implements MKMapViewListener{

		private GeoPoint lastPoint=new GeoPoint(0, 0);

		@Override
		public void onClickMapPoi(MapPoi arg0) {
		}

		@Override
		public void onGetCurrentMap(Bitmap arg0) {
		}

		@Override
		public void onMapAnimationFinish() {
		}

		@Override
		public void onMapLoadFinish() {
			System.out.println("onMapLoadFinish");
			if(truckAsyncTask!=null){
				truckAsyncTask.cancel(true);
			}
			if(expressAsyncTask!=null){
				expressAsyncTask.cancel(true);
			}
		
			truckAsyncTask=new TruckAsyncTask();
			expressAsyncTask=new ExpressAsyncTask();
			truckAsyncTask.execute(mapView.getMapCenter());
			expressAsyncTask.execute(mapView.getMapCenter());
		}

		@Override
		public void onMapMoveFinish() {
			System.out.println("onMapMoveFinish");
			GeoPoint tempGeoPoint=mapView.getMapCenter();
			int distance=Math.abs(lastPoint.getLatitudeE6()-tempGeoPoint.getLatitudeE6())+Math.abs(lastPoint.getLongitudeE6()-tempGeoPoint.getLongitudeE6());
			if(distance>10000){
				lastPoint=mapView.getMapCenter();
				if(truckAsyncTask!=null){
					truckAsyncTask.cancel(true);
				}
				if(expressAsyncTask!=null){
					expressAsyncTask.cancel(true);
				}
				truckAsyncTask=new TruckAsyncTask();
				expressAsyncTask=new ExpressAsyncTask();
				truckAsyncTask.execute(mapView.getMapCenter());
				expressAsyncTask.execute(mapView.getMapCenter());
				
			}
		}

	}

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		switch (resultCode) {
//		case 0:
//			truckOverlayItems=MyGlobal.truckOverlayItems;
//			expressOverlayItems=MyGlobal.expressOverlayItems;
//			
//			if(truckItemizedOverlay!=null){
//				Drawable drawable=getResources().getDrawable(R.drawable.map_truck_loaded);
//				for(TruckOverlayItem myOverlayItem:truckOverlayItems){
//					if(myOverlayItem.getTruck().getIsLoaded().equals("1")){
//						myOverlayItem.setMarker(drawable);
//					}
//				}
//				truckItemizedOverlay.removeAll();
//				expressItemizedOverlay.removeAll();
//				truckItemizedOverlay.addAllOverlayItem(truckOverlayItems);
//				expressItemizedOverlay.addAllOverlayItem(expressOverlayItems);
//				mapView.refresh();
//			}
//			break;
//
//		default:
//			break;
//		}
//	}

	/**
	 * 重写activity的生命周期并执行相关操作
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(locationClient!=null)
			locationClient.stop();
		mapView.destroy();
		SharedPreferences sharedPreferences=getSharedPreferences("settings", 0);
		Editor editor=sharedPreferences.edit();
		editor.putInt("latitudeE6",geoPoint.getLatitudeE6());
		editor.putInt("longitudeE6", geoPoint.getLongitudeE6());
		editor.commit();

	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}
    @Override
    protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            mapView.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
            super.onRestoreInstanceState(savedInstanceState);
            mapView.onRestoreInstanceState(savedInstanceState);
    }

}
