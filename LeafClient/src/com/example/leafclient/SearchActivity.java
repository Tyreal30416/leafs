package com.example.leafclient;

import java.util.List;
import java.util.Map;

import com.example.leafclient.resultActivity.ItemClickListenerImp;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class SearchActivity extends Activity {
	private MytabOperation mytab = null;
	private SQLiteOpenHelper helper = null;
	private LinearLayout  searchlayout = null;
	private ListView lv = null;
	private SimpleAdapter adapter = null;
	private LinearLayout loadLayout = null;
	private TextView loadInfo = null;
	private LayoutParams layoutparams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
	private int currentPage = 1;
	private int lineSize = 15;
	private List<Map<String,Object>> all = null;
	private String keyword="";
	private int allRecorders = 0;
	private int pageSize = 1;
	private int lastItem = 0;
	private EditText search = null;
	private Button confirm = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.searchlayout);
		
		this.helper = new MyDataBaseHelper(this);
		this.helper.getWritableDatabase();
		
		this.search = (EditText) super.findViewById(R.id.search);
		this.confirm = (Button) super.findViewById(R.id.confirm);
		
		this.confirm.setOnClickListener(new ConfirmListener());
		
		this.searchlayout = (LinearLayout) super.findViewById(R.id.searchlayout);
		
		
		this.loadLayout = new LinearLayout(this);
		this.loadInfo = new TextView(this);
		this.loadInfo.setText("数据加载中.....");
		this.loadInfo.setGravity(Gravity.CENTER);
		this.loadInfo.setTextSize(30.0f);
		this.loadLayout.addView(this.loadInfo,this.layoutparams);
		this.loadLayout.setGravity(Gravity.CENTER);
		
		this.showAllData();
		
		this.pageSize = (this.allRecorders+this.lineSize-1) / this.lineSize;
	}
	public class ConfirmListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SearchActivity.this.keyword = SearchActivity.this.search.getText().toString();
			System.out.println("h"+SearchActivity.this.keyword);
			//SearchActivity.this.lv.cl
			SearchActivity.this.searchlayout.removeAllViews();
			SearchActivity.this.showAllData();
		}
		
	}
	private void showAllData(){
		
		MytabCursor cur = new MytabCursor(this.helper.getReadableDatabase());
		this.allRecorders = cur.getCount();
		this.all = cur.find(this.currentPage,this.lineSize,this.keyword);
		System.out.println("size:"+this.all.size());
		this.lv = new ListView(this);
		this.adapter = new SimpleAdapter(this, this.all, R.layout.tab_info,new String[]{"id","name"},new int[]{R.id.id,R.id.name});
		this.lv.setAdapter(this.adapter);
		
		this.lv.addFooterView(this.loadLayout);
		this.lv.setOnScrollListener(new OnScollListenerImp());
		this.searchlayout.addView(lv);
		this.lv.setOnItemClickListener(new ItemClickListenerImp());
	}
	public class ItemClickListenerImp implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			Map map = (Map<Object,String>)SearchActivity.this.adapter.getItem(position);
			int _id =  (Integer) map.get("id");
			System.out.println("id:"+id);
			String name = (String) map.get("name");
			Intent it = new Intent(SearchActivity.this,ReviewActivity.class);
			it.putExtra("name", name);
			SearchActivity.this.startActivity(it);
			//resultActivity.this.info.setText("选中的数据项id是： "+_id+" 书名是: "+name);
		}
		
	}
	private class OnScollListenerImp implements OnScrollListener{

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			SearchActivity.this.lastItem = firstVisibleItem + visibleItemCount-1;
			System.out.println("lastItem:"+lastItem);
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			System.out.println(SearchActivity.this.adapter.getCount());
			if(SearchActivity.this.lastItem == SearchActivity.this.adapter.getCount()-1
					&&SearchActivity.this.currentPage < SearchActivity.this.pageSize
					&&scrollState == OnScrollListener.SCROLL_STATE_IDLE){
				SearchActivity.this.currentPage ++;
				SearchActivity.this.lv.setSelection(SearchActivity.this.lastItem);
				SearchActivity.this.appendData();
				
			}else if(SearchActivity.this.currentPage == SearchActivity.this.pageSize){
				SearchActivity.this.loadInfo.setText("已到底部");
			}
		}
		
		
	}
	private void appendData(){
		MytabCursor cur = new MytabCursor(this.helper.getReadableDatabase());
		List<Map<String,Object>> newData = cur.find(currentPage, lineSize, keyword);
		this.all.addAll(newData);
		this.adapter.notifyDataSetChanged();
	}
	private void exitDialog() {
		Dialog dialog = new AlertDialog.Builder(this).setIcon(R.drawable.pic_m)
				.setTitle("程序退出？ ").setMessage("您确定要退出本程序吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						SearchActivity.this.finish() ;
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create();

		dialog.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			this.exitDialog() ;
		}
		return false ;
	}
}