package sg.nus.totoryfinnal;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import sg.nus.totoryfinnal.R;
import sg.nus.totoryfinnal.MainActivity;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;



public class MainActivity extends Activity {

	private int itemCount = 12000;//最多可以显示12000个月
	private TextView mTopdate;
	private int itemWidth;
	static private ListAdapter adapter=null;
	static public DateAdapter.TakeSavePhoto photolistener=null;
	static public calendaFragment.DetachPhotoFragment backHomeListener=null;
	calendaFragment smilefr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		creatFragment();
		creatTakephotoLisener();
		creatBackHomeLisener();
		smilefr.setDetachPhotoFragment(backHomeListener);
		mTopdate = (TextView) findViewById(R.id.top_date);
		String month = monthFormat(Calendar.getInstance());
		mTopdate.setText(month);
		
		findViewById(R.id.weekTitle).setBackgroundColor(Color.WHITE);//////
		mTopdate.postDelayed(new Runnable() {

			@Override
			public void run() {
				initViewPager();
			}
		}, 100);
		
		initWeekTitle();
    }
        public void creatFragment(){
        smilefr=new calendaFragment();
    	getFragmentManager().beginTransaction()
        .add(R.id.container, smilefr)
        .commit();
    	 }
    public void creatTakephotoLisener(){
    	photolistener =new DateAdapter.TakeSavePhoto(){

			@Override
			public void gotoPhotoFragment() {
				// TODO Auto-generated method stub
				getFragmentManager().beginTransaction().attach(smilefr).commit();
				Log.i("mydebug","attach");
			}
    	
    	};
    }
    public void creatBackHomeLisener(){
    	backHomeListener= new calendaFragment.DetachPhotoFragment() {
			
			@Override
			public void detachFragment() {
				// TODO Auto-generated method stub
				getFragmentManager().beginTransaction().detach(smilefr).commit();
			}
		};
    	
    }
	private void initWeekTitle() {
		int[] itemIds = new int[] { R.id.linearLayout1, R.id.linearLayout2, R.id.linearLayout3,
				R.id.textView4, R.id.linearLayout5, R.id.linearLayout6, R.id.linearLayout7 };
		DisplayMetrics display = getResources().getDisplayMetrics();
		itemWidth = Math.round(display.widthPixels / 7);
		for (int i = 0; i < itemIds.length; i++) {
			View itemView = findViewById(itemIds[i]);
			if (i==3||i==4) {
				int addWidth = (display.widthPixels-itemWidth*7)/2-1;
				itemView.setLayoutParams(new LinearLayout.LayoutParams(itemWidth+addWidth,(int) (display.density*35)));
			}else{
				itemView.setLayoutParams(new LinearLayout.LayoutParams(itemWidth,(int) (display.density*35)));
			}
		}
		
		
	}

	private void initViewPager() {
		ViewPager mViewPager = (ViewPager) findViewById(R.id.ViewPager1);
		mViewPager.setAdapter(new ImageAdapter());
		mViewPager.setCurrentItem(itemCount / 2);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				Calendar calendar = Calendar.getInstance();
				int num = position - itemCount / 2;
				calendar.add(Calendar.MONTH, num);
				String monthStr = monthFormat(calendar);
				mTopdate.setText(monthStr);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}
	
	//更新标题栏的年月
	private String monthFormat(Calendar calendar) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		String format = dateFormat.format(calendar.getTime());
		String[] strs = format.split("-");
		char[] charArray = strs[1].toCharArray();
		if ((charArray[0] + "").equals("0")) {
			strs[1] = charArray[1] + "";
		}
		String monthStr = strs[0] + "年" + strs[1] + "月";
		return monthStr;
	}

	private class ImageAdapter extends PagerAdapter {

		@Override
		public int getCount() {

			return itemCount;
		}
		

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = LayoutInflater.from(MainActivity.this).inflate(
					R.layout.list_item, null);
			ListView listView = (ListView) view.findViewById(R.id.listView1);
			int num = position - itemCount / 2;
			adapter= new DateAdapter(MainActivity.this, num,listView);
			listView.setAdapter(adapter);
			((DateAdapter) adapter).setTakeSavePhotoCallBack(photolistener);
			view.setTag(position);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(container.findViewWithTag(position));
		}
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

   
}
