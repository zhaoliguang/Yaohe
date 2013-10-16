package runze.yaohe.mycomponent;

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgressDialog extends ProgressDialog{

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
	}
	public MyProgressDialog(Context context, String message) {
		super(context);
		this.setMessage(message);
		this.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		this.show();
		
	}
	
	public void close(){
		if(this.isShowing()){
			this.cancel();
		}
	}

}
