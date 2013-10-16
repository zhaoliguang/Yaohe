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

public class ExpressListAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;  
	private List< Map<String, String>> mSelfData;  
	
	public ExpressListAdapter(Context context, List< Map<String, String>> data) {
		// TODO Auto-generated constructor stub
		this.mSelfData = data;  
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mSelfData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mSelfData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=mLayoutInflater.inflate(R.layout.listview_express_list, null);
		}
		TextView postName=(TextView) convertView.findViewById(R.id.lvpostName);
		TextView phoneNumber=(TextView) convertView.findViewById(R.id.lvPhoneNumber);
		postName.setText(mSelfData.get(position).get("name").toString());
		phoneNumber.setText(mSelfData.get(position).get("phoneNumber").toString());
		postName.getPaint().setFakeBoldText(true);
		phoneNumber.getPaint().setFakeBoldText(true);
		((TextView)convertView.findViewById(R.id.textView5)).getPaint().setFakeBoldText(true);
	    ((TextView)convertView.findViewById(R.id.textView6)).getPaint().setFakeBoldText(true);
		return convertView;
	}

}
