package runzhe.yaohe.map;

import runze.yaohe.domain.Express;
import runze.yaohe.domain.Truck;

import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class TruckOverlayItem extends OverlayItem{

	private Truck truck;
	public TruckOverlayItem(GeoPoint arg0, String arg1, String arg2) {
		super(arg0, arg1, arg2);
	}
	public TruckOverlayItem(GeoPoint arg0, String arg1, String arg2,Truck truck) {
		super(arg0, arg1, arg2);
		this.truck=truck;
	}
	public TruckOverlayItem(GeoPoint arg0, String arg1, String arg2,Express post) {
		super(arg0, arg1, arg2);
	}
	public Truck getTruck() {
		return truck;
	}
	public void setTruck(Truck truck) {
		this.truck = truck;
	}

}
