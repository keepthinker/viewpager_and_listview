package com.example.learnpager2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

public class PagerActivity extends Activity {
	ViewPager viewPager;
	ArrayList<View> pagesArrayList;// 添加listview 然后传给MyPagerAdapter 充当媒介
	String[] strs;
	String[] strs2;
	ListView listView;
	Button button1;
	Button button2;
	Button button3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pager);
		iniParams();
		iniViews();

	}
	private void iniParams(){
		pagesArrayList=new ArrayList<View>();
		strs=new String[]{"a","b","c","a","b","c","a","b","c"};
		strs2=new String[]{"1","2","3","1","2","3","1","2","3"};


	}
	private void iniViews(){
		viewPager=(ViewPager)findViewById(R.id.viewPager);
		//第一个页面的viewpager第1个page里的listview
		LayoutInflater layoutInflater=getLayoutInflater();

		listView=(ListView)
				(layoutInflater.inflate(R.layout.page,null).findViewById(R.id.listview));
		ArrayAdapter<String> arrrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strs);
		listView.setAdapter(arrrayAdapter);
		pagesArrayList.add(listView);


		//第个页面的viewpager第2个page里的listview
		listView=(ListView)
				(layoutInflater.inflate(R.layout.page,null).findViewById(R.id.listview));
		ArrayAdapter<String> arrrayAdapter2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strs2);
		listView.setAdapter(arrrayAdapter2);
		pagesArrayList.add(listView);

		//第三个面的viewpager第3个page里的listview
		SimpleAdapter simpleAdapter3=new SimpleAdapter(this,getDatasForListView(),
				R.layout.listviewitem,new String[]{"title","image"},new int[]{R.id.textView,R.id.imageView});
		listView=(ListView)
				(layoutInflater.inflate(R.layout.page, null).findViewById(R.id.listview));
		listView.setAdapter(simpleAdapter3);
		pagesArrayList.add(listView);


		viewPager.setAdapter(new MyPagerAdapter(pagesArrayList));
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setCurrentItem(0);

		//viewPager.setOffscreenPageLimit(2);//必须设置为大于page数量的值,我也不懂为什么必须设置4个不然的话instatiateItem会出错
		button1=(Button)findViewById(R.id.button1);
		button2=(Button)findViewById(R.id.button2);
		button3=(Button)findViewById(R.id.button3);
	}
	public List<Map<String, Object>> getDatasForListView(){
		Log.e("3", "");
		List<Map<String, Object>> listMaps=new ArrayList<Map<String,Object>>();

		String[] strings=new String[]{"图片1","图片2","图片3"};
		int[] images=new int[]{R.drawable.p1,R.drawable.p1,R.drawable.p1};

		for(int i=0;i<strings.length;i++){
			listMaps.add(ListViewItemFactory.generate(new Object[]{strings[i],images[0]}));
		}
		return listMaps;
	}
	static class ListViewItemFactory{
		static Map<String,Object> generate(Object[] obj){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("title", obj[0]);
			map.put("image",obj[1]);
			return map;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pager, menu);
		return true;
	}
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {

			Log.d("destroyItem", ""+arg0+" "+arg1);
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			Log.d("instantiateItem", ""+arg0+" "+arg1);
			try { 
				if(mListViews.get(arg1).getParent()==null)
					((ViewPager) arg0).addView(mListViews.get(arg1), 0);  
				else{
					//					很难理解新添加进来的view会自动绑定一个父类，由于一个儿子view不能与两个父类相关，所以得解绑
					//不这样做否则会产生 viewpager java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
					((ViewGroup)mListViews.get(arg1).getParent()).removeView(mListViews.get(arg1));

					((ViewPager) arg0).addView(mListViews.get(arg1), 0); 
				}
			} catch (Exception e) {  
				// TODO Auto-generated catch block  
				Log.d("parent=", ""+mListViews.get(arg1).getParent()); 
				e.printStackTrace();  
			}  
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
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
	class MyOnPageChangeListener implements OnPageChangeListener{

		@Override
		public   void onPageScrollStateChanged (int state){
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// TODO Auto-generated method stub

		}
		Color preColor;
		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			Log.d("page",  "pos="+position);
			switch(position)
			{
			case 0: //button1.setBackgroundColor(0x00FF00);break;
			case 1:// button1.setBackgroundColor(0xFF0000);break;
			case 2:
			}

		}

	}
}
