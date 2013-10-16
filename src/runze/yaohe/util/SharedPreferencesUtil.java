package runze.yaohe.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {
	
	public static void addIsLoaded(Context context,boolean isLoaded){
		SharedPreferences sharedPreferences=context.getSharedPreferences("truckIsLoaded", 0);
		Editor editor=sharedPreferences.edit();
		editor.putBoolean("is_loaded", isLoaded);
		editor.commit();
	}
	
	public static boolean getIsLoaded(Context context){
		SharedPreferences sharedPreferences=context.getSharedPreferences("truckIsLoaded", 0);
		boolean isLoaded=sharedPreferences.getBoolean("is_loaded",false);
		return isLoaded;
	}

	public static void addTruckRegister(Context context,boolean isRegister){
		SharedPreferences truckPreferences=context.getSharedPreferences("truck", 0);
		Editor truckEditor=truckPreferences.edit();
		truckEditor.putBoolean("truck_register", isRegister);
		truckEditor.commit();
	}
	public static void addExpressRegister(Context context,boolean isRegister){
		SharedPreferences postPreferences=context.getSharedPreferences("post", 0);
		Editor postEditor=postPreferences.edit();
		postEditor.putBoolean("express_register", isRegister);
		postEditor.commit();
	}
	
	public static boolean getTruckIsRegister(Context context){
	    SharedPreferences sharedPreferences=context.getSharedPreferences("truck",0);
		boolean isRegister = sharedPreferences.getBoolean("truck_register", false); 
		return isRegister;
	}
	public static boolean getExpressIsRegister(Context context){
	    SharedPreferences sharedPreferences=context.getSharedPreferences("post",0);
		boolean isRegister = sharedPreferences.getBoolean("express_register", false); 
		return isRegister;
	}
}
