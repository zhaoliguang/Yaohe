package runze.yaohe.ui;
import runze.yaohe.constant.MyConstant;
import runze.yaohe.domain.Express;
import runze.yaohe.domain.Truckinfo;
import runze.yaohe.mycomponent.MyPointBuilder;
import runze.yaohe.mycomponent.MyProgressDialog;
import runze.yaohe.netservice.ExpressManage;
import runze.yaohe.netservice.GetExpress;
import runze.yaohe.netservice.GetTruckInfo;
import runze.yaohe.netservice.TruckinfoManage;
import runze.yaohe.util.ServiceUtil;
import runze.yaohe.util.SharedPreferencesUtil;

import com.runze.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class ExpressInfoActivity extends Activity{
	
	private EditText etExpressName,etPhoneNumber,etPostCompany,etOtherMsg;
	private String imei;
	private Express express;
	private MyProgressDialog myProgressDialog;
	private String expressName,expressCompany,othMsg,phoneNumber;
	private SharedPreferences expressPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_post_info);
		etExpressName=(EditText) findViewById(R.id.postName);
		etPhoneNumber=(EditText) findViewById(R.id.phoneNumber);
		etPostCompany=(EditText) findViewById(R.id.postCompany);
		etOtherMsg=(EditText) findViewById(R.id.postMessage);
		expressPreferences=getSharedPreferences("express", 0);
		initData() ;
		
	}
	/**
	 * 初始化数据
	 */
	private void initData() {
		expressName=expressPreferences.getString("expressName", "");
		expressCompany=expressPreferences.getString("expressCompany", "");
		othMsg=expressPreferences.getString("othMsg", "");
		TelephonyManager telephonyMgr = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE); 
		phoneNumber = telephonyMgr.getLine1Number();
		if (phoneNumber.startsWith("+"))
		{
			phoneNumber=phoneNumber.substring(3);
		}
		imei = telephonyMgr.getDeviceId();
		etPostCompany.setText(expressCompany);
		etOtherMsg.setText(othMsg);
		etPhoneNumber.setText(phoneNumber);
		etExpressName.setText(expressName);
		

	}
	public void back(View view){
		this.finish();
	}
	public void post(View view){
		expressName=etExpressName.getText().toString().trim();
		expressCompany=etPostCompany.getText().toString().trim();
		othMsg=etOtherMsg.getText().toString();
		phoneNumber=etPhoneNumber.getText().toString().trim();
		if(expressName.equals("")){
			new MyPointBuilder(this, "提示", "您必须先输入您的姓名", "确定");
		}else if(expressCompany.equals("")){
			new MyPointBuilder(this, "提示", "您必须先输入您所属公司", "确定");
		}else if(phoneNumber.equals("")){
			new MyPointBuilder(this, "提示", "您必须先输入您的手机号码", "确定");
		}else{
			if(phoneNumber.length()!=11){
				new MyPointBuilder(this, "提示", "手机号码必须是11位", "确定");
			}else{
				express = new Express(imei, expressName,phoneNumber, expressCompany,othMsg, 
						0,0,null,null,null,null);
				myProgressDialog=new MyProgressDialog(this, "正在注册，请稍后");
				new MyAsyncTaskAddExpress().execute("");
			}
		}
	}
//	public class MyAsyncTaskGetExpress extends AsyncTask<String, String, Express>{
//
//		@Override
//		protected Express doInBackground(String... params) {
//			Express express= GetExpress.getExpressByImei(ServiceUtil.getImei(ExpressInfoActivity.this));
//			return express;
//		}
//
//		@Override
//		protected void onPostExecute(Express express) {
//			if(express!=null){
//				
//				etPhoneNumber.setText(express.getPhoneNumber());
//				etOtherMsg.setText(express.getOtherMsg());
//				etExpressName.setText(express.getName());
//			}
//		}
//	}
	public class MyAsyncTaskAddExpress extends AsyncTask<String, String, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			int flag = ExpressManage.addExpress(express);
			return flag;
		}

		@Override
		protected void onPostExecute(Integer flag) {
			myProgressDialog.close();
			switch (flag) {
			case MyConstant.SUCCESS:
				//SharedPreferencesUtil.addExpressRegister(ExpressInfoActivity.this, true);
				Editor postEditor=expressPreferences.edit();
				postEditor.putBoolean("express_register", true);
				postEditor.putString("expressName", expressName);
				postEditor.putString("expressCompany", expressCompany);
				postEditor.putString("othMsg", othMsg);
				postEditor.commit();
				Toast.makeText(ExpressInfoActivity.this, "恭喜你，注册成功", Toast.LENGTH_LONG).show();
				break;
			case MyConstant.FAIL:
				new MyPointBuilder(ExpressInfoActivity.this, "提示", "抱歉发布信息失败", "确定");
				break;
			case MyConstant.CONNECT_ERROR:
				Toast.makeText(ExpressInfoActivity.this, "网络不通，请稍候再试", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
