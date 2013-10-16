package runze.yaohe.mycomponent;

import java.util.List;
import java.util.Map;

import com.runze.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TruckListAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;  
	private List< Map<String, String>> mSelfData;  
	
	public TruckListAdapter(Context context, List< Map<String, String>> data) {  
        this.mSelfData = data;  
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
    }  
	
	@Override
	public int getCount() {
		return mSelfData.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mSelfData.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=mLayoutInflater.inflate(R.layout.listview_truck_list, null);
		}
		TextView weight=(TextView) convertView.findViewById(R.id.truck_list_listview_weight);
		TextView length=(TextView) convertView.findViewById(R.id.truck_list_listview_length);
		TextView width=(TextView) convertView.findViewById(R.id.truck_list_listview_width);
		weight.setText(mSelfData.get(position).get("weight").toString());
		length.setText(mSelfData.get(position).get("length").toString());
		width.setText(mSelfData.get(position).get("width").toString());
		weight.getPaint().setFakeBoldText(true);
		length.getPaint().setFakeBoldText(true);
		width.getPaint().setFakeBoldText(true);
		((TextView)convertView.findViewById(R.id.textView1)).getPaint().setFakeBoldText(true);
	    ((TextView)convertView.findViewById(R.id.textView2)).getPaint().setFakeBoldText(true);
	    ((TextView)convertView.findViewById(R.id.textView3)).getPaint().setFakeBoldText(true);
		return convertView;
	}

}
