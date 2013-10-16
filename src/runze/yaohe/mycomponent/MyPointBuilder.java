package runze.yaohe.mycomponent;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
/**
 * 一个确定（取消）按钮加提示的对话框
 * @author lp
 *
 */
public class MyPointBuilder extends Builder{

	AlertDialog alertDialog;
	public MyPointBuilder(Context arg0) {
		super(arg0);
	}

	/**
	 * 创建一个只有一个按钮的对话框并立即显示
	 * @param context  Context 对象
	 * @param title  对话框标题
	 * @param message   对话框信息
	 * @param negativeButtonText  按钮字符
	 */
	public MyPointBuilder(Context context,String title,String message,String negativeButtonText){
		super(context);
		this.setTitle(title);
		this.setMessage(message);
		if(negativeButtonText!=null){
			this.setNegativeButton(negativeButtonText, null);
			alertDialog=this.create();
			//显示对话框
			alertDialog.show();
		}
	}
	/**
	 * 创建一个只有一个按钮的对话框但不立即显示
	 * @param context  Context 对象
	 * @param title  对话框标题
	 * @param message   对话框信息
	 * @param negativeButtonText  按钮字符
	 * return MyPointBuilder
	 */
	public MyPointBuilder setDetailsWithOutShow(String title,String message,String negativeButtonText) {
		this.setTitle(title);
		this.setMessage(message);
		if(negativeButtonText!=null){
			this.setNegativeButton(negativeButtonText, null);
			this.alertDialog=this.create();
		}
		return this;
	}

	/**
	 * 关闭对话框
	 */
	public void closeDialog(){
		alertDialog.dismiss();
	}
}
