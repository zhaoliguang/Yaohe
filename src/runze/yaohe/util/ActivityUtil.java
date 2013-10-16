package runze.yaohe.util;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;

public class ActivityUtil{
	private List<Activity> activities=new ArrayList<Activity>();
	private Activity current_activity;
	private static ActivityUtil exitUtil;
	public static ActivityUtil getInstance(){
		if(exitUtil==null){
			exitUtil=new ActivityUtil();
		}
		return exitUtil;	
	}

	public Activity getCurrent_activity() {
		return current_activity;
	}

	public void setCurrent_activity(Activity current_activity) {
		this.current_activity = current_activity;
	}

	public void addActivity(Activity activity){
		activities.add(activity);
	}
}
