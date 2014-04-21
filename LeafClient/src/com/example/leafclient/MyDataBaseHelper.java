package com.example.leafclient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper {
	private static final String DATABASENAME = "leafdatabase";
	private static final int DATABASEVERSION = 1;
	private static final String TABLENAME = "leafs";
	public MyDataBaseHelper(Context context) {
		super(context, DATABASENAME, null, DATABASEVERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE "+TABLENAME+"("+"id	INTEGER PRIMARY KEY ,"+"name VARCHAR(50) NOT NULL,"+"loc VARCHAR(200) NOT NULL"+")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "DROP TABLE IF EXISTS "+TABLENAME;
		db.execSQL(sql);
		this.onCreate(db);
	}

}
