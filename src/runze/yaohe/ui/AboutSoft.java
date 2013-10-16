package runze.yaohe.ui;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import com.runze.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class AboutSoft extends Activity {
	
	private TextView tvAbout;
	private String about;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about_soft);
		tvAbout=(TextView)findViewById(R.id.tvAbout);
		about=readFromRaw();
		tvAbout.getPaint().setFakeBoldText(true);
		tvAbout.setText(about);
		
	}
	public void back(View view)
	{
		finish();
	}
	public String readFromRaw() { 
        String res = ""; 
        try { 
            InputStream in = getResources().openRawResource(R.raw.aboutsoft); 
            int length = in.available(); 
            byte[] buffer = new byte[length]; 
            in.read(buffer); 
            res = EncodingUtils.getString(buffer, "UTF-8"); 
            // res = new String(buffer,"GBK"); 
            in.close(); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return res; 
    } 
	

}
