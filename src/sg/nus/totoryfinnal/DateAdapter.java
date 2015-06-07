package sg.nus.totoryfinnal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import sg.nus.totoryfinnal.MainActivity;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint({ "ViewHolder", "InflateParams" }) public class DateAdapter extends BaseAdapter {
	private Context mContext;
	private ListView mListView;
	private List<Date> mList;
	private int itemWidth;
	private int currentMonth;
	private DisplayMetrics display;
	final int CAMERA_PIC_REQUEST = 0;
	TakeSavePhoto myCallBack=null;

	public DateAdapter(Context context, int month, ListView listview) {
		mContext = context;
		mListView = listview;

		display = mContext.getResources().getDisplayMetrics();
		itemWidth = Math.round(display.widthPixels / 7);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);

		currentMonth = calendar.get(Calendar.MONTH);

		calendar.set(Calendar.DATE, 1);
		int dateOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - dateOfWeek);

		mList = new ArrayList<Date>();
		for (int i = 0; i < 42; i++) {
			mList.add(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
		}

	}

	@Override
	public int getCount() {

		return 6;
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = LayoutInflater.from(mContext)
				.inflate(R.layout.item, null);
		
		int[] itemIds = new int[] { R.id.linearLayout1, R.id.linearLayout2, R.id.linearLayout3,
				R.id.linearLayout4, R.id.linearLayout5, R.id.linearLayout6, R.id.linearLayout7 };
		int[] textIds = new int[] { R.id.textView1, R.id.textView2, R.id.textView3,
				R.id.textView4, R.id.textView5, R.id.textView6, R.id.textView7 };
		Log.i("tag"," itemWidth*7:"+ itemWidth*7);
		for (int i = 0; i < textIds.length; i++) {
			TextView textView = (TextView) convertView.findViewById(textIds[i]);
			View itemView = convertView.findViewById(itemIds[i]);
			if (i==3||i==4) {
				int addWidth = (display.widthPixels-itemWidth*7)/2;
				itemView.setLayoutParams(new LinearLayout.LayoutParams(itemWidth+addWidth,itemWidth));
			}else{
				itemView.setLayoutParams(new LinearLayout.LayoutParams(itemWidth,itemWidth));
			}
			
			Date date = mList.get(position * 7 + i);
//			String dateStr = format.format(date);
			Log.i("tag", "zz"+date.getDate()+"zz");
			textView.setText(""+date.getDate());

			// 不是当前月的，设置字体颜色为灰色
			if (currentMonth != date.getMonth()) {
				textView.setTextColor(Color.GRAY);
				textView.setText("");
			}

			itemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.i("mydebug","take photo");
					// 改变选中项的背景颜色和去掉上一次选中项的颜色
					takePhoto();
				}
						
				
			});

			// Log.i("tag", " mToday:"+mToday+"  position*7+i:"+(position*7+i));
			//设置当日的背景颜色
			Date currentDate = new Date();
			if (currentMonth==date.getMonth() && date.getMonth() == currentDate.getMonth()&&date.getDate()==currentDate.getDate()) {
				textView.setBackgroundColor(0x00000000);
				itemView.setBackgroundColor(0xff6bd697);
				itemView.setId(0xff);
			}
		}
		
		convertView.setLayoutParams(new ListView.LayoutParams(
				LayoutParams.FILL_PARENT, itemWidth));
		return convertView;
	}
	public void takePhoto(){
		//create an intent to use the phones camera application
		if(myCallBack!=null){
         myCallBack.gotoPhotoFragment();
        
         Log.i("mydebug","take befor jump photo2");
		}
        //start activity and wait for specific call back
       
        
       
	}
	public interface TakeSavePhoto{
		public void gotoPhotoFragment();
	}
	public void setTakeSavePhotoCallBack(TakeSavePhoto lisener){
		myCallBack=lisener;	
	}
}
