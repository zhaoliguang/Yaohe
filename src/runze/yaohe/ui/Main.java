package runze.yaohe.ui;
import java.util.ArrayList;  
import java.util.List;  
import java.util.concurrent.atomic.AtomicInteger;  

import runze.yaohe.service.UpdateLocationService;
import runze.yaohe.util.ServiceUtil;
import runze.yaohe.util.SharedPreferencesUtil;

import com.runze.R;

  
import android.annotation.SuppressLint;  
import android.app.Activity;  
import android.content.Intent;
import android.os.Bundle;  
import android.os.Handler;  
import android.os.Message;  
import android.os.Parcelable;  
import android.support.v4.view.PagerAdapter;  
import android.support.v4.view.ViewPager;  
import android.support.v4.view.ViewPager.OnPageChangeListener;  
import android.view.MotionEvent;  
import android.view.View;  
import android.view.Window;
import android.view.View.OnTouchListener;  
import android.view.ViewGroup;  
import android.view.ViewGroup.LayoutParams;  
import android.widget.ImageView;  
  
@SuppressLint("HandlerLeak")  
public class Main extends Activity {  
    private ImageView[] imageViews = null;  
    private ImageView imageView = null;  
    private ViewPager viewPager = null;  
    private AtomicInteger what = new AtomicInteger(0);  
    private boolean isContinue = true;  
    private boolean isTruckRegister = false,isExpressRegister=false;
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);  
    	//启动Service
		if(SharedPreferencesUtil.getTruckIsRegister(this)||SharedPreferencesUtil.getExpressIsRegister(this)){
			if(!ServiceUtil.isRuning(this, "linpeng.app.service.UpdateLocationService")){
				Intent i = new Intent(this, UpdateLocationService.class); 
				startService(i); 
			}
		}
        initViewPager();  
    }  
  
    public void truckClick(View view){
    	isTruckRegister = SharedPreferencesUtil.getTruckIsRegister(this);
		if(isTruckRegister){
			startActivity(new Intent(Main.this, TruckIsLoadedActivity.class));
		}else{
			startActivity(new Intent(Main.this, TruckInfoActivity.class));
		}
	}
	public void userClick(View view){
		startActivity(new Intent(Main.this, CustomerMapActivity.class)); 
	}
	public void expressClick(View view){
		startActivity(new Intent(Main.this, ExpressInfoActivity.class));
	}
	public void aboutClick(View view){
		startActivity(new Intent(Main.this, AboutSoft.class));
	}
    private void initViewPager() {  
        viewPager = (ViewPager) findViewById(R.id.viewPager);  
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);  
//      这里存放的是四张广告背景  
        List<View> advPics = new ArrayList<View>();  
  
        ImageView header = new ImageView(this);  
        header.setBackgroundResource(R.drawable.main_header);  
        advPics.add(header);  
 
//      对imageviews进行填充  
        imageViews = new ImageView[advPics.size()];  
////小图标  
//        for (int i = 0; i < advPics.size(); i++) {  
//            imageView = new ImageView(this);  
//            imageView.setLayoutParams(new LayoutParams(20, 20));  
//            imageView.setPadding(5, 5, 5, 5);  
//            imageViews[i] = imageView;  
//            group.addView(imageViews[i]);  
//        }  
  
        viewPager.setAdapter(new AdvAdapter(advPics));  
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());  
        viewPager.setOnTouchListener(new OnTouchListener() {  
              
            @Override  
            public boolean onTouch(View v, MotionEvent event) {  
                switch (event.getAction()) {  
                case MotionEvent.ACTION_DOWN:  
                case MotionEvent.ACTION_MOVE:  
                    isContinue = false;  
                    break;  
                case MotionEvent.ACTION_UP:  
                    isContinue = true;  
                    break;  
                default:  
                    isContinue = true;  
                    break;  
                }  
                return false;  
            }  
        });  
        new Thread(new Runnable() {  
  
            @Override  
            public void run() {  
                while (true) {  
                    if (isContinue) {  
                        viewHandler.sendEmptyMessage(what.get());  
                        whatOption();  
                    }  
                }  
            }  
  
        }).start();  
    }  
  
      
    private void whatOption() {  
        what.incrementAndGet();  
        if (what.get() > imageViews.length - 1) {  
            what.getAndAdd(-4);  
        }  
        try {  
            Thread.sleep(5000);  
        } catch (InterruptedException e) {  
              
        }  
    }  
  
    private final Handler viewHandler = new Handler() {  
  
        @Override  
        public void handleMessage(Message msg) {  
            viewPager.setCurrentItem(msg.what);  
            super.handleMessage(msg);  
        }  
  
    };  
  
    private final class GuidePageChangeListener implements OnPageChangeListener {  
  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
  
        }  
  
        @Override  
        public void onPageSelected(int arg0) {  
            what.getAndSet(arg0);  
            for (int i = 0; i < imageViews.length; i++) {  
                imageViews[arg0]  
                        .setBackgroundResource(R.drawable.page_indicator_focused);  
                if (arg0 != i) {  
                    imageViews[i]  
                            .setBackgroundResource(R.drawable.page_indicator_unfocused);  
                }  
            }  
  
        }  
  
    }  
  
    private final class AdvAdapter extends PagerAdapter {  
        private List<View> views = null;  
  
        public AdvAdapter(List<View> views) {  
            this.views = views;  
        }  
  
        @Override  
        public void destroyItem(View arg0, int arg1, Object arg2) {  
            ((ViewPager) arg0).removeView(views.get(arg1));  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
  
        }  
  
        @Override  
        public int getCount() {  
            return views.size();  
        }  
  
        @Override  
        public Object instantiateItem(View arg0, int arg1) {  
            ((ViewPager) arg0).addView(views.get(arg1), 0);  
            return views.get(arg1);  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
  
        }  
  
    }  
  
}  
