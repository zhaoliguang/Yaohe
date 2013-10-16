package runze.yaohe.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import runze.yaohe.constant.MyGlobal;
import runze.yaohe.domain.Truck;
import runze.yaohe.mycomponent.TruckListAdapter;
import runze.yaohe.netservice.GetTruck;
import runzhe.yaohe.map.TruckOverlayItem;

import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.runze.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class TruckListActivity extends Activity {

	private ListView listView;
	private List<TruckOverlayItem> myOverlayItems;
	private int pageNow=2;
	private List<Truck> trucks;
	private List<Map<String,String>> listData=new ArrayList<Map<String,String>>();
	private TruckListAdapter simpleAdapter;
	private View footview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_truck_list);
		myOverlayItems=MyGlobal.truckOverlayItems;
		if(MyGlobal.truckOverlayItems.size()==0){
			pageNow=1;
		}
	    footview=getLayoutInflater().inflate(R.layout.listview_footview_loadmore, null);
		listView=(ListView)findViewById(R.id.truck_list_listview);
		getOrUpdateData();
		
	    simpleAdapter=new TruckListAdapter(this, listData);
		listView.addFooterView(footview);
		listView.setAdapter(simpleAdapter);
		listView.setOnItemClickListener(new MyListListener());
		updateList();
	}
	private void getOrUpdateData() {
		listData.clear();
		for(TruckOverlayItem myOverlayItem:myOverlayItems){
			Map<String,String> map=new HashMap<String, String>();
			Truck truck=myOverlayItem.getTruck();
			map.put("weight",truck.getWeight()+"吨");
			map.put("length",truck.getLength()+"米");
			map.put("width",truck.getWidth()+"米");
		    listData.add(map);
		}
	}
	
	public void updateList(){
		if(myOverlayItems.size()<20){
			System.out.println("remove");
			listView.removeFooterView(footview);
		}
		simpleAdapter.notifyDataSetChanged();
	}
	
	public void back(View view){
		setResult(0);
		finish();
	}
	
	public class MyListListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			System.out.println(arg2);
			if(arg2==myOverlayItems.size()){
				new MyAsyncTask().execute("");
			}else{
				Intent intent=new Intent(TruckListActivity.this,TruckDetailActivity.class);
				intent.putExtra("truck", myOverlayItems.get(arg2).getTruck());
				startActivity(intent);
			}
		}
	}
	
	public class MyAsyncTask extends AsyncTask<String, String, Boolean>{
		@Override
		protected Boolean doInBackground(String... params) {
		 	trucks=GetTruck.getTruckByCoordinate(MyGlobal.myLocation, 20,pageNow);
			return null;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			if(trucks.size()>0){
				System.out.println("地图truck数量："+trucks.size());
				pageNow++;
				for(Truck truck:trucks){
					GeoPoint tempGeoPoint=new GeoPoint(truck.getLatitudeE6(),truck.getLongitudeE6());
					myOverlayItems.add(new TruckOverlayItem(tempGeoPoint, truck.getCity(), truck.getCity(),truck));
				}
				getOrUpdateData();
				simpleAdapter.notifyDataSetChanged();
			}else{
				if(MyGlobal.CONNECT_ERROR_TRUCK_LIST){
					Toast.makeText(TruckListActivity.this, "抱歉网络不通", Toast.LENGTH_LONG).show();
					MyGlobal.CONNECT_ERROR_TRUCK_LIST=false;
				}else{
					Toast.makeText(TruckListActivity.this, "所有卡车加载完毕", Toast.LENGTH_SHORT).show();
				}
			}
		}
		
	} 

}
