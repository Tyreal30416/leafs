package com.example.leafclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class resultActivity extends Activity {
	private SQLiteOpenHelper helper = null;
	private LinearLayout  resultlayout = null;
	private ListView lv = null;
	private SimpleAdapter adapter = null;
	private LayoutParams layoutparams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
	private List<Map<String,Object>> all = null;
	private int allRecorders = 0;
	int ressum = 6;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.resultlayout);
		
		this.helper = new MyDataBaseHelper(this);
		this.helper.getWritableDatabase();
		
		
		this.resultlayout = (LinearLayout) super.findViewById(R.id.resultlayout);
		
		Intent it = super.getIntent();
		String dis = it.getStringExtra("result");
		System.out.println("dis2"+dis);
		String ids[] = new String[ressum];
		ids = dis.split(" ");
		SQLiteDatabase db = this.helper.getReadableDatabase();
		MytabCursor cur = new MytabCursor(db);
		this.allRecorders = cur.getCount();
		this.all = new ArrayList<Map<String,Object>>();
		for(int i = 0 ; i<ressum ; i++){
			Map<String,Object> map = new HashMap<String,Object>();
			map = cur.find(Integer.parseInt(ids[i]));
			all.add(map);
		}
		db.close();
		this.lv = new ListView(this);
		this.adapter = new SimpleAdapter(this, this.all, R.layout.tab_info,new String[]{"id","name"},new int[]{R.id.id,R.id.name});
		this.lv.setAdapter(this.adapter);
		
		
		this.resultlayout.addView(lv);
		this.lv.setOnItemClickListener(new ItemClickListenerImp());
	}
	public class ItemClickListenerImp implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			Map map = (Map<Object,String>)resultActivity.this.adapter.getItem(position);
			int _id =  (Integer) map.get("id");
			System.out.println("id:"+id);
			String name = (String) map.get("name");
			Intent it = new Intent(resultActivity.this,ReviewActivity.class);
			it.putExtra("name", name);
			resultActivity.this.startActivity(it);
			//resultActivity.this.info.setText("选中的数据项id是： "+_id+" 书名是: "+name);
		}
		
	}
}