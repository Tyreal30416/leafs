package com.example.leafclient;


import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class IndexActivity extends Activity {
	private SQLiteOpenHelper helper = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.mylayout);
		
		this.helper = new MyDataBaseHelper(this);
		this.helper.getWritableDatabase();
//		MytabOperation op1 = new MytabOperation(this.helper.getWritableDatabase());
//		op1.delete();
		SQLiteDatabase db = this.helper.getReadableDatabase();
		MytabCursor cur = new MytabCursor(db);
		int count = cur.getCount();
		System.out.println("c"+count);
		
//		this.helper = new MyDataBaseHelper(this);
		//cur = new MytabCursor(this.helper.getReadableDatabase());
		//List<Map<String,Object>> all = cur.findAll();
		//System.out.println(all.size()+" "+all.get(0).get("name"));
		if(count == 0){
			db = this.helper.getWritableDatabase();
			//this.helper = new MyDataBaseHelper(this);
			MytabOperation op = new MytabOperation(db);
			op.init();
		}
		db.close();
//		this.helper = new MyDataBaseHelper(this);
//		count = cur.getCount();
//		System.out.println(count);
		
		
		
	}
}