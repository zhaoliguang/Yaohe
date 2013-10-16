package runze.yaohe.ui;

import runze.yaohe.domain.TruckIsLoaded;
import runze.yaohe.mycomponent.MyPointBuilder;
import runze.yaohe.mycomponent.MyProgressDialog;
import runze.yaohe.netservice.TruckIsLoadedManage;
import runze.yaohe.util.ServiceUtil;
import runze.yaohe.util.SharedPreferencesUtil;

import com.runze.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TruckIsLoadedActivity extends Activity implements OnCheckedChangeListener{

	private ToggleButton toggle_is_loaded;
	MyProgressDialog progressDialog;
	private boolean firstFlag=true;
	private boolean changeResult=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_truck_is_loaded);
		toggle_is_loaded = (ToggleButton)findViewById(R.id.truck_toggle_is_loaded);
		toggle_is_loaded.setOnCheckedChangeListener(this);
		boolean isLoaded=SharedPreferencesUtil.getIsLoaded(this);
		toggle_is_loaded.setChecked(isLoaded);
		firstFlag=false;
	}

	public void updateTruckInfo(View view){
		startActivity(new Intent(TruckIsLoadedActivity.this,TruckInfoActivity.class));
	}

	public void updateTruckIsLoaded(View view){
		toggle_is_loaded.performClick();
	}

	public void back(View view){
		this.finish();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		System.out.println("��ť�޸�״̬" +isChecked);
		String isLoaded = "0";
		if(isChecked){
			isLoaded="1";
		}
		if(!firstFlag){
			
			TruckIsLoaded truckIsLoaded=new TruckIsLoaded(ServiceUtil.getImei(this), isLoaded);
			MyAsyncTask myAsyncTask=new MyAsyncTask();
			progressDialog=new MyProgressDialog(TruckIsLoadedActivity.this,"�����޸�״̬");
			myAsyncTask.execute(truckIsLoaded);
		}
	}



	public class MyAsyncTask  extends AsyncTask<TruckIsLoaded, String, Boolean>{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
		}

		@Override
		protected Boolean doInBackground(TruckIsLoaded... truckIsLoaded) {
			System.out.println("�޸�״̬");
			boolean success = TruckIsLoadedManage.updateTruckIsLoad(truckIsLoaded[0]);
			System.out.println("�޸Ľ����"+success);
			return success;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			progressDialog.close();
			if(success){
				System.out.println("�޸ĳɹ�");
				
				Toast.makeText(TruckIsLoadedActivity.this, "��ϲ���޸�״̬�ɹ�", Toast.LENGTH_SHORT).show();
				
			}else{
				System.out.println("�޸�ʧ��");
				new MyPointBuilder(TruckIsLoadedActivity.this, "��ʾ", "��Ǹ����״̬ʧ�ܣ�������������", "ȷ��");
//				flag=false;
				firstFlag=true;
				toggle_is_loaded.setChecked(!toggle_is_loaded.isChecked());
			}
			firstFlag=false;
			SharedPreferencesUtil.addIsLoaded(TruckIsLoadedActivity.this, toggle_is_loaded.isChecked());
		}
	}

}
