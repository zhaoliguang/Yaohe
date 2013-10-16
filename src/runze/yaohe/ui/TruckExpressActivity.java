package runze.yaohe.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import runze.yaohe.constant.MyGlobal;
import runze.yaohe.domain.Express;
import runze.yaohe.domain.Truck;
import runze.yaohe.mycomponent.ExpressListAdapter;
import runze.yaohe.mycomponent.TruckListAdapter;
import runze.yaohe.netservice.GetExpress;
import runze.yaohe.netservice.GetTruck;
import runzhe.yaohe.map.ExpressOverlayItem;
import runzhe.yaohe.map.TruckOverlayItem;

import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.runze.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

public class TruckExpressActivity extends Activity implements OnTabChangeListener,OnItemClickListener{
	
	private TabHost tabHost;
	private ListView lvTruckList,lvExpressList;
	private List<Truck> truckList;
	private List<Express> expressList;
	private int truckPageNow=2,expressPageNow=2;
	private View footview;
	private List<Map<String,String>> truckListData;
	private List<Map<String,String>> expressListData;
	private TruckListAdapter truckAdapter;
	private ExpressListAdapter expressAdapter;
	private List<TruckOverlayItem> truckOverlayItems;
	private List<ExpressOverlayItem> expressOverlayItems;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);  
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_truck_post);  
        lvTruckList=(ListView) findViewById(R.id.truckList);
        lvExpressList=(ListView) findViewById(R.id.expressList);
        truckListData=new ArrayList<Map<String,String>>();
        expressListData=new ArrayList<Map<String,String>>();
        truckAdapter=new TruckListAdapter(this, truckListData);
        expressAdapter=new ExpressListAdapter(this, expressListData);
        footview=getLayoutInflater().inflate(R.layout.listview_footview_loadmore, null);
        lvTruckList.addFooterView(footview);
        lvTruckList.setAdapter(truckAdapter);
        lvExpressList.addFooterView(footview);
        lvExpressList.setAdapter(expressAdapter);
       
        truckOverlayItems=MyGlobal.truckOverlayItems;
        expressOverlayItems=MyGlobal.expressOverlayItems;
        
        if(MyGlobal.truckOverlayItems.size()==0){
			truckPageNow=1;
		}
        if(MyGlobal.expressOverlayItems.size()==0){
			expressPageNow=1;
		}
        lvTruckList.setOnItemClickListener(this);
        lvExpressList.setOnItemClickListener(this);
        tabHost = (TabHost) findViewById(R.id.tabhost);  
        tabHost.setup();  
        TabWidget tabWidget=tabHost.getTabWidget();
        tabHost.addTab(tabHost.newTabSpec("�����б�")  
                .setIndicator("�����б�")  
                .setContent(R.id.truckList));  
        tabHost.addTab(tabHost.newTabSpec("����б�")  
                .setIndicator("����б�",getResources().getDrawable(R.drawable.icon_back_truck_list_title))  
                .setContent(R.id.expressList));  
        tabHost.setCurrentTab(0);
        tabHost.setOnTabChangedListener(this);
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            TextView tv=(TextView)tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setGravity(BIND_AUTO_CREATE);
            tv.setPadding(10, 10,10, 10);
            tv.setTextSize(16);//��������Ĵ�С��
            tv.setTextColor(Color.WHITE);//�����������ɫ
        }
        getOrUpdateTruckData();
        getOrUpdateExpressData();
        updateTruckList();
        updateExpressList();
        
        
	}

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		if(tabId.equals("�����б�")){
			tabHost.setCurrentTab(0);
		}else{
			tabHost.setCurrentTab(1);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent;
		switch(arg0.getId()){
		case R.id.truckList:
			if(arg2==truckOverlayItems.size()){
				new truckAsyncTask().execute("");
			}else{
				intent=new Intent(TruckExpressActivity.this,TruckDetailActivity.class);
				intent.putExtra("truck", truckOverlayItems.get(arg2).getTruck());
				startActivity(intent);
			}
			break;
		case R.id.expressList:
			if(arg2==expressOverlayItems.size()){
				new ExpressAsyncTask().execute("");
			}else{
				intent=new Intent(TruckExpressActivity.this,ExpressDetailActivity.class);
				intent.putExtra("express", expressOverlayItems.get(arg2).getExpress());
				startActivity(intent);
			}
			break;
			
		}
	};
	private void getOrUpdateTruckData() {
		truckListData.clear();
		for(TruckOverlayItem myOverlayItem:truckOverlayItems){
			Map<String,String> map=new HashMap<String, String>();
			Truck truck=myOverlayItem.getTruck();
			map.put("weight",truck.getWeight()+"��");
			map.put("length",truck.getLength()+"��");
			map.put("width",truck.getWidth()+"��");
			truckListData.add(map);
		}
		truckAdapter.notifyDataSetChanged();
	}
	private void getOrUpdateExpressData() {
		expressListData.clear();
		for(ExpressOverlayItem myOverlayItem:expressOverlayItems){
			Map<String,String> map=new HashMap<String, String>();
			System.out.println("������"+expressListData.size());
			Express express=myOverlayItem.getExpress();
			map.put("name",express.getName());
			map.put("phoneNumber",express.getPhoneNumber());
			map.put("company",express.getCompany());
			expressListData.add(map);
		}
		expressAdapter.notifyDataSetChanged();
	}
	public void updateTruckList(){
		if(truckOverlayItems.size()<20){
			System.out.println("remove");
			lvTruckList.removeFooterView(footview);
		}
		truckAdapter.notifyDataSetChanged();
	} 
	public void updateExpressList(){
		if(expressOverlayItems.size()<20){
			System.out.println("remove");
			lvExpressList.removeFooterView(footview);
		}
		expressAdapter.notifyDataSetChanged();
	} 
	
	public class truckAsyncTask extends AsyncTask<String, String, Boolean>{
		@Override
		protected Boolean doInBackground(String... params) {
		 	truckList=GetTruck.getTruckByCoordinate(MyGlobal.myLocation, 20,truckPageNow);
			return null;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			if(truckList.size()>0){
				System.out.println("��ͼtruck������"+truckList.size());
				truckPageNow++;
				for(Truck truck:truckList){
					GeoPoint tempGeoPoint=new GeoPoint(truck.getLatitudeE6(),truck.getLongitudeE6());
					truckOverlayItems.add(new TruckOverlayItem(tempGeoPoint, truck.getCity(), truck.getCity(),truck));
				}
				getOrUpdateTruckData();
				truckAdapter.notifyDataSetChanged();
			}else{
				if(MyGlobal.CONNECT_ERROR_TRUCK_LIST){
					Toast.makeText(TruckExpressActivity.this, "��Ǹ���粻ͨ", Toast.LENGTH_LONG).show();
					MyGlobal.CONNECT_ERROR_TRUCK_LIST=false;
				}else{
					Toast.makeText(TruckExpressActivity.this, "���п����������", Toast.LENGTH_SHORT).show();
				}
			}
		}
		
	} 
	public class ExpressAsyncTask extends AsyncTask<String, String, Boolean>{
		@Override
		protected Boolean doInBackground(String... params) {
		 	expressList=GetExpress.getExpressByCoordinate(MyGlobal.myLocation, 20,expressPageNow);
			return null;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			if(expressList.size()>0){
				System.out.println("��ͼexpress������"+expressList.size());
				expressPageNow++;
				for(Express express:expressList){
					GeoPoint tempGeoPoint=new GeoPoint(express.getLatitudeE6(),express.getLongitudeE6());
					truckOverlayItems.add(new TruckOverlayItem(tempGeoPoint, express.getCity(), express.getCity(),express));
				}
				getOrUpdateExpressData();
				expressAdapter.notifyDataSetChanged();
			}else{
				if(MyGlobal.CONNECT_ERROR_TRUCK_LIST){
					Toast.makeText(TruckExpressActivity.this, "��Ǹ���粻ͨ", Toast.LENGTH_LONG).show();
					MyGlobal.CONNECT_ERROR_TRUCK_LIST=false;
				}else{
					Toast.makeText(TruckExpressActivity.this, "���п��Ա�������", Toast.LENGTH_SHORT).show();
				}
			}
		}
		
	}
}
