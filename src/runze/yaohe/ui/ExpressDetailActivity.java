package runze.yaohe.ui;

import java.text.SimpleDateFormat;

import runze.yaohe.domain.Express;

import com.runze.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class ExpressDetailActivity extends Activity {
	private Express express;
	private TextView tvName;
	private TextView tvPhoneNumber;
	private TextView tvLocation;
	private TextView tvCompany;
	private TextView tvLocationLastTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_post_details);
		express=(Express) getIntent().getSerializableExtra("express");
		tvName=(TextView) findViewById(R.id.post_details_name);
		tvPhoneNumber=(TextView) findViewById(R.id.post_details_phone_number);
		tvLocation=(TextView) findViewById(R.id.post_details_location);
		tvCompany=(TextView) findViewById(R.id.post_details_company);
		tvLocationLastTime=(TextView) findViewById(R.id.post_details_location_last_time);
		tvName.setText(express.getName());
		tvPhoneNumber.setText(express.getPhoneNumber());
		tvCompany.setText(express.getCompany());
		tvLocation.setText(express.getCity()+express.getDistrict()+express.getStreet());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		tvLocationLastTime.setText(df.format(express.getLastTimeLocation())+"");
	
	}
	public void back(View view){
		finish();
	}
	public void phoneCall(View view){
		Intent phoneIntent = new Intent("android.intent.action.CALL",Uri.parse("tel:" + express.getPhoneNumber()));
		startActivity(phoneIntent);
	}

}
