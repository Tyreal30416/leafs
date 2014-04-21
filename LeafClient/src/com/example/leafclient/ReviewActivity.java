package com.example.leafclient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ViewSwitcher.ViewFactory;

public class ReviewActivity extends Activity {
	private Gallery gallery = null;
	private SimpleAdapter simpleadapter = null;
	List<Map<String,Integer>> list = new ArrayList<Map<String,Integer>>();
	private ImageSwitcher imgSwitcher = null;
	private List<Integer> all = null;
	private SQLiteOpenHelper helper = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.gallarylayout);
		
		
		
		this.helper = new MyDataBaseHelper(this);
		Intent it = super.getIntent();
		String name = it.getStringExtra("name");
		System.out.println("name:"+name);
		all = new ArrayList<Integer>();
		MytabCursor cur = new MytabCursor(this.helper.getReadableDatabase());
		all = cur.findbyName(name);
		this.gallery = (Gallery) this.findViewById(R.id.imggallery);
//		this.gallery.setAdapter(new GallaryAdapter(this));
		this.initAdapter();
		this.gallery.setAdapter(this.simpleadapter);
		this.gallery.setOnItemClickListener(new OnItemClickListenerImp());
		
		this.imgSwitcher = (ImageSwitcher) this.findViewById(R.id.imgswitcher);
		imgSwitcher.setFactory(new ViewFactoryImp());
		
		
		System.out.println("res"+all.get(0));
	}
	public class ViewFactoryImp implements ViewFactory{

		@Override
		public View makeView() {
			ImageView img = new ImageView(ReviewActivity.this);
			img.setBackgroundColor(0xFFFFFFFF);
			img.setScaleType(ImageView.ScaleType.CENTER);
			img.setLayoutParams(new ImageSwitcher.LayoutParams
					(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
			return img;
		}
		
	}
	public void initAdapter(){
		Field [] fields = R.drawable.class.getDeclaredFields();
		boolean b = false;
		int index = 0;
		for(int i = 0 ;i < fields.length ; i++){
			index = 0;
			for(index = 0 ; index < this.all.size() ; index++){
				String temp = "y"+all.get(index);
				System.out.println("temp:"+temp);
				if(fields[i].getName().equalsIgnoreCase(temp)){
					b = true;
					break;
				}
			}
			if(b){
				Map<String,Integer> map = new HashMap<String,Integer>();
				
				try {
					map.put("img", fields[i].getInt(R.drawable.class));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				this.list.add(map);
				b = false;
			}
		}
		this.simpleadapter = new SimpleAdapter(this,this.list,R.layout.imagelayout,new String[]{"img"},new int[]{R.id.img});
	}
	public class OnItemClickListenerImp implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
//			Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
			@SuppressWarnings("unchecked")
			Map<String,Integer> map = (Map<String, Integer>) parent.getAdapter().getItem(position);
			ReviewActivity.this.imgSwitcher.setImageResource(map.get("img"));
		}
		
	}
}