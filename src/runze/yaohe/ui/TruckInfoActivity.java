package runze.yaohe.ui;

import runze.yaohe.constant.MyConstant;
import runze.yaohe.domain.Truckinfo;
import runze.yaohe.mycomponent.MyPointBuilder;
import runze.yaohe.mycomponent.MyProgressDialog;
import runze.yaohe.netservice.GetTruckInfo;
import runze.yaohe.netservice.TruckinfoManage;
import runze.yaohe.util.ServiceUtil;
import runze.yaohe.util.SharedPreferencesUtil;

import com.runze.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class TruckInfoActivity extends Activity {

	private EditText edittext_weight,edittext_length,edittext_width,edittext_phone_number,edittext_other,edittext_name;
	private String imei;
	private String weight,length,width,phone_number,othMsg,name;
	private Truckinfo truckinfo;
	private MyProgressDialog myProgressDialog;
	private SharedPreferences truckPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_truck_info);
		truckPreferences=getSharedPreferences("truck", 0);
		initComponent();
		initData();
		//new MyAsyncTaskGetTruck().execute("");
		setTextWatch();
	}



	/**
	 * 初始化数据
	 */
	private void initData() {
		TelephonyManager telephonyMgr = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE); 
		phone_number = telephonyMgr.getLine1Number();
		name=truckPreferences.getString("name", "");
		weight=truckPreferences.getString("weight", "");
		length=truckPreferences.getString("length", "");
		width=truckPreferences.getString("width", "");
		othMsg=truckPreferences.getString("othMsg", "");
		if (phone_number.startsWith("+"))
		{
			phone_number=phone_number.substring(3);
		}
		imei = telephonyMgr.getDeviceId();
		edittext_phone_number.setText(phone_number);
		edittext_name.setText(name);
		edittext_weight.setText(weight);
		edittext_length.setText(length);
		edittext_width.setText(width);
		edittext_other.setText(othMsg);

	}

	/**
	 * 初始化组件
	 */
	public void initComponent(){
		edittext_weight = (EditText)findViewById(R.id.truck_edittext_weight);
		edittext_length = (EditText)findViewById(R.id.truck_edittext_length);
		edittext_width = (EditText)findViewById(R.id.truck_edittext_width);
		edittext_phone_number = (EditText)findViewById(R.id.truck_edittext_phone_number);
		edittext_other = (EditText)findViewById(R.id.truck_edittext_other);
		edittext_name = (EditText)findViewById(R.id.truck_edittext_name);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.truck, menu);
		return true;
	}

	public void back(View view){
		this.finish();
	}

	public void post(View view){
		name=edittext_name.getText().toString().trim();
		weight=edittext_weight.getText().toString().trim();
		length=edittext_length.getText().toString().trim();
		width=edittext_width.getText().toString().trim();
		phone_number=edittext_phone_number.getText().toString().trim();
		othMsg=edittext_other.getText().toString().trim();
		if(name.equals("")){
			new MyPointBuilder(this, "提示", "您必须先输入您的姓名", "确定");
		}else if(weight.equals("")){
			new MyPointBuilder(this, "提示", "您必须先输入您的卡车核载重量", "确定");
		}else if(length.equals("")){
			new MyPointBuilder(this, "提示", "您必须先输入您的卡车长度", "确定");
		}else if(width.equals("")){
			new MyPointBuilder(this, "提示", "您必须先输入您的卡车宽度", "确定");
		}else if(phone_number.equals("")){
			new MyPointBuilder(this, "提示", "您必须先输入您的手机号码", "确定");
		}else{
			if(length.endsWith(".")){
				edittext_length.setText(length.substring(0, length.length()-1));
			}
			if(weight.endsWith(".")){
				edittext_weight.setText(weight.substring(0, weight.length()-1));
			}
			if(width.endsWith(".")){
				edittext_width.setText(width.substring(0, width.length()-1));
			}
			
			Long long1=Long.parseLong(phone_number);
			if(phone_number.length()!=11){
				new MyPointBuilder(this, "提示", "手机号码必须是11位", "确定");
			}else{
				truckinfo = new Truckinfo(imei, weight,length ,width,othMsg, 
						phone_number,name);
				myProgressDialog=new MyProgressDialog(this, "正在注册，请稍后");
				new MyAsyncTaskAddTruck().execute("");
			}
		}
	}

	public class MyAsyncTaskAddTruck extends AsyncTask<String, String, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			int flag = TruckinfoManage.addTruckinfo(truckinfo);
			return flag;
		}

		@Override
		protected void onPostExecute(Integer flag) {
			myProgressDialog.close();
			switch (flag) {
			case MyConstant.SUCCESS:
				//SharedPreferencesUtil.addTruckRegister(TruckInfoActivity.this, true);
				Editor truckEditor=truckPreferences.edit();
				truckEditor.putBoolean("truck_register", true);
				truckEditor.putString("name", name);
				truckEditor.putString("weight", weight);
				truckEditor.putString("length", length);
				truckEditor.putString("width", width);
				truckEditor.putString("phone_number", phone_number);
				truckEditor.putString("othMsg", othMsg);
				truckEditor.commit();
				Toast.makeText(TruckInfoActivity.this, "恭喜你，注册成功", Toast.LENGTH_LONG).show();
				break;
			case MyConstant.FAIL:
				new MyPointBuilder(TruckInfoActivity.this, "提示", "抱歉添加失败", "确定");
				break;
			case MyConstant.CONNECT_ERROR:
				Toast.makeText(TruckInfoActivity.this, "网络不通，请稍候再试", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	}

//	public class MyAsyncTaskGetTruck extends AsyncTask<String, String, Truckinfo>{
//
//		@Override
//		protected Truckinfo doInBackground(String... params) {
//			Truckinfo truckinfo= GetTruckInfo.getTruckInfoByImei(ServiceUtil.getImei(TruckInfoActivity.this));
//			return truckinfo;
//		}
//
//		@Override
//		protected void onPostExecute(Truckinfo truckinfo) {
//			if(truckinfo!=null){
//				edittext_weight.setText(truckinfo.getWeight());
//				edittext_length.setText(truckinfo.getLength());
//				edittext_width.setText(truckinfo.getWidth());
//				edittext_phone_number.setText(truckinfo.getPhoneNumber());
//				edittext_other.setText(truckinfo.getOtherMsg());
//				edittext_name.setText(truckinfo.getName());
//			}
//		}
//	}

	private void setTextWatch() {
		edittext_length.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				try{
					Float length=Float.parseFloat(s.toString());
					if(length>25){
						new MyPointBuilder(TruckInfoActivity.this, "提示", "您的卡车车厢长度不能大于25米", "确定");
						edittext_length.setText("25");
					}else if(length<1){
						new MyPointBuilder(TruckInfoActivity.this, "提示", "您的卡车车厢长度不能小于1米", "确定");
						edittext_length.setText("1");
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		edittext_width.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				try{
					Float width=Float.parseFloat(s.toString());
					if(width>3.5){
						new MyPointBuilder(TruckInfoActivity.this, "提示", "您的卡车车厢宽度不能大于3.5米", "确定");
						edittext_width.setText("3.5");
					}else if(width<1){
						new MyPointBuilder(TruckInfoActivity.this, "提示", "您的卡车车厢宽度不能小于1米", "确定");
						edittext_width.setText("1");
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		edittext_weight.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				try{
					Float weight=Float.parseFloat(s.toString());
					if(weight>30){
						new MyPointBuilder(TruckInfoActivity.this, "提示", "您的卡车核载重量不能大于30吨", "确定");
						edittext_weight.setText("25");
					}else if(weight<0.5){
						new MyPointBuilder(TruckInfoActivity.this, "提示", "您的卡车核载重量不能小于0.5吨", "确定");
						edittext_weight.setText("1");
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(myProgressDialog!=null){
			myProgressDialog.close();
		}
	}

}
