package runze.yaohe.mycomponent;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class MySingleChoiceItemDialog{

	private Builder builder;
	private AlertDialog alertDialog;
	public MySingleChoiceItemDialog(Context context) {
		builder=new Builder(context);
	}
	
	public void setDataAndListener(int itemsId,int checkedItem,OnClickListener onClickListener){
		builder.setSingleChoiceItems(itemsId,checkedItem,onClickListener);
	}
	/**
	 * ÏÔÊ¾Ñ¡Ôñ¿ò
	 */
	public void showDialog(){
		alertDialog=builder.create();
		alertDialog.show();
	}
	
	public void closeDialog(){
		alertDialog.cancel();
	}
	
}
