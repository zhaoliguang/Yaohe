package runzhe.yaohe.map;

import java.util.List;

import runze.yaohe.ui.ExpressDetailActivity;
import runze.yaohe.util.ActivityUtil;
import runze.yaohe.util.ViewBitmapUtil;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.runze.R;

public class ExpressItemizedOverlay extends ItemizedOverlay<ExpressOverlayItem>{

	private MapView mapView;
	private View linear;
	private PopupOverlay popupOverlay;
	private int index_point; 
	
	public ExpressItemizedOverlay(Drawable arg0, MapView mapView) {
		super(arg0, mapView);
		this.mapView=mapView;
	}
	
//	public void removeAllItem(){
////		this.myOverlayItems.clear();
//		this.removeAll();
//	}
	public void removeOverLayItem(List<ExpressOverlayItem> myOverlayItems)
	{
		for(ExpressOverlayItem myOverlayItem:myOverlayItems){
			this.removeItem(myOverlayItem);
		}

	}
	public void addAllOverlayItem(List<ExpressOverlayItem> myOverlayItems){
//		this.myOverlayItems=myOverlayItems;
		for(ExpressOverlayItem myOverlayItem:myOverlayItems){
			this.addItem(myOverlayItem);
		}
	}

	@Override
	protected boolean onTap(int index) {
		System.out.println(index+"index");
		index_point=index;
		Activity activity=ActivityUtil.getInstance().getCurrent_activity();
		View viewCache = activity.getLayoutInflater().inflate(R.layout.map_express_popup, null);
		linear = (View) viewCache.findViewById(R.id.popup_map_linear);
		((TextView)viewCache.findViewById(R.id.popup_express_name)).setText(((ExpressOverlayItem)getItem(index_point)).getExpress().getName());
		((TextView)viewCache.findViewById(R.id.popup_express_phone)).setText(((ExpressOverlayItem)getItem(index_point)).getExpress().getPhoneNumber());
		Bitmap[] bitmaps={ViewBitmapUtil.getBitmapFromView(linear)};
		popupOverlay=new PopupOverlay(mapView, new PopupClickListener() {
			@Override
			public void onClickedPopup(int arg0) {
				Intent intent=new Intent(ActivityUtil.getInstance().getCurrent_activity(),ExpressDetailActivity.class);
				intent.putExtra("express", ((ExpressOverlayItem)getItem(index_point)).getExpress());
				ActivityUtil.getInstance().getCurrent_activity().startActivity(intent);
			}
		});
		popupOverlay.showPopup(bitmaps, ((ExpressOverlayItem)getItem(index_point)).getPoint(), 32);
		return super.onTap(index);
	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView arg1) {
		if(popupOverlay!=null){
			popupOverlay.hidePop();
		}
		return super.onTap(arg0, arg1);
	}

}
