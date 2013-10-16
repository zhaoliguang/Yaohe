package runzhe.yaohe.map;

import runze.yaohe.domain.Express;

import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class ExpressOverlayItem extends OverlayItem{

	private Express express;
	public ExpressOverlayItem(GeoPoint arg0, String arg1, String arg2) {
		super(arg0, arg1, arg2);
	}
	public ExpressOverlayItem(GeoPoint arg0, String arg1, String arg2,Express express) {
		super(arg0, arg1, arg2);
		this.express=express;
	}
	public Express getExpress() {
		return express;
	}
	public void setExpress(Express express) {
		this.express = express;
	}

}
