package runze.yaohe.ui;

import java.text.SimpleDateFormat;

import runze.yaohe.domain.Truck;

import com.runze.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class TruckDetailActivity extends Activity {

	private Truck truck;
	private TextView weight;
	private TextView length;
	private TextView width;
	private TextView phoneNumber;
	private TextView isLoaded;
	private TextView isLoadedLastTime;
	private TextView location;
	private TextView locationLastTime;
	private TextView name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_truck_details);
		
		initComponent();
		weight.setText(truck.getWeight()+"¶Ö");
		length.setText(truck.getLength()+"Ã×");
		width.setText(truck.getWidth()+"Ã×");
		phoneNumber.setText(truck.getPhoneNumber());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		isLoadedLastTime.setText(df.format(truck.getLastTimeIsLoaded())+"");
		location.setText(truck.getCity()+truck.getDistrict()+truck.getStreet());
		
		locationLastTime.setText(df.format(truck.getLastTimeLocation())+"");
		name.setText(truck.getName());
		if("1".equals(truck.getIsLoaded())){
		     isLoaded.setText("ÊÇ");
		}else{
			isLoaded.setText("·ñ");
		}
	}
	
	public void back(View view){
		finish();
	}
	
	public void phoneCall(View view){
		Intent phoneIntent = new Intent("android.intent.action.CALL",Uri.parse("tel:" + truck.getPhoneNumber()));
		startActivity(phoneIntent);
	}

	private void initComponent() {
		truck=(Truck) getIntent().getSerializableExtra("truck");
		weight=(TextView)findViewById(R.id.truck_details_weight);
		length=(TextView)findViewById(R.id.truck_details_length);
		width=(TextView)findViewById(R.id.truck_details_width);
		phoneNumber=(TextView)findViewById(R.id.truck_details_phone_number);
		isLoaded=(TextView)findViewById(R.id.truck_details_is_loaded);
		isLoadedLastTime=(TextView)findViewById(R.id.truck_details_is_loaded_last_time);
		location=(TextView)findViewById(R.id.truck_details_location);
		locationLastTime=(TextView)findViewById(R.id.truck_details_location_last_time);
		name=(TextView)findViewById(R.id.truck_details_name);
	}

}
