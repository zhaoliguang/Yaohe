package runze.yaohe.ui;

import runze.yaohe.service.UpdateLocationService;
import runze.yaohe.util.ServiceUtil;
import runze.yaohe.util.SharedPreferencesUtil;

import com.runze.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity {
	private boolean isRegister = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		//Æô¶¯Service
		if(SharedPreferencesUtil.getTruckIsRegister(this)){
			if(!ServiceUtil.isRuning(this, "linpeng.app.service.UpdateLocationService")){
				Intent i = new Intent(this, UpdateLocationService.class); 
				startService(i); 
			}
		}
	}

	
	public void driverClick(View view){
		isRegister = SharedPreferencesUtil.getTruckIsRegister(this);
		if(isRegister){
			startActivity(new Intent(MainActivity.this, TruckIsLoadedActivity.class));
		}else{
			startActivityForResult(new Intent(MainActivity.this, TruckInfoActivity.class),0);
		}
	}
	public void customerClick(View view){
		startActivity(new Intent(MainActivity.this, CustomerMapActivity.class)); 
	}
	public void postClick(View view){
		isRegister = SharedPreferencesUtil.getExpressIsRegister(this);
//		if(isRegister){
//			startActivity(new Intent(MainActivity.this, PostInfoActivity.class));
//		}else{
//			startActivityForResult(new Intent(MainActivity.this, PostInfoActivity.class),0);
//		}
		startActivity(new Intent(MainActivity.this, ExpressInfoActivity.class));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==1){
			startActivity(new Intent(MainActivity.this, TruckIsLoadedActivity.class));
		}
		
	}
	
}
