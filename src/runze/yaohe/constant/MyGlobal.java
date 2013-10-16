package runze.yaohe.constant;

import java.util.List;

import runze.yaohe.domain.Express;
import runzhe.yaohe.map.ExpressOverlayItem;
import runzhe.yaohe.map.TruckOverlayItem;

import com.baidu.platform.comapi.basestruct.GeoPoint;


public class MyGlobal {
	
	public static List<TruckOverlayItem> truckOverlayItems;
	public static List<ExpressOverlayItem> expressOverlayItems;

	public static GeoPoint myLocation;
	public static boolean CONNECT_ERROR_TRUCK_LIST = false;
	public static boolean CONNECT_ERROR_EXPRESS_LIST = false;
}
