package com.example.leafclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MytabCursor {
	private static final String TABLENAME = "leafs";
	private SQLiteDatabase db = null;
	public MytabCursor(SQLiteDatabase db){
		this.db = db;
	}
	public int getCount(){
		
		int count = 0;
		String sql = "SELECT COUNT(id) FROM "+TABLENAME;
		Cursor result = db.rawQuery(sql,null);
		for(result.moveToFirst() ; !result.isAfterLast() ; result.moveToNext()){
			count = result.getInt(0);
		}
		return count;
	}
	public List<Map<String,Object>> findAll(){
		List<Map<String,Object> > all = new ArrayList<Map<String,Object> >();
		
		String sql = "SELECT id,name,loc FROM "+TABLENAME;
		//String args[] = {id};
		Cursor result = db.rawQuery(sql,null);
		//for(result.moveToFirst() ; !result.isAfterLast() ; result.moveToNext()){
		result.moveToFirst();
		if(!result.isAfterLast()){
			Map<String ,Object> map = new HashMap<String ,Object>();
			map.put("id", result.getInt(0));
			map.put("name", result.getString(1));
			map.put("loc",result.getString(2));
			all.add(map);
		}
//		this.db.close();
		return all;
	}
	public Map<String,Object> find(int id){
		Map<String ,Object> map = new HashMap<String ,Object>();
		
		String sql = "SELECT id,name,loc FROM "+TABLENAME+" WHERE id="+id;
		//String args[] = {id};
		Cursor result = db.rawQuery(sql,null);
		//for(result.moveToFirst() ; !result.isAfterLast() ; result.moveToNext()){
		result.moveToFirst();
		if(!result.isAfterLast()){
			
			map.put("id", result.getInt(0));
			map.put("name", result.getString(1));
			//map.put("loc",result.getString(2));
			//all.add(map);
		}
//		this.db.close();
		return map;
		
		
	}
	public List<Integer> findbyName(String name){
		List<Integer> all = new ArrayList<Integer>();
		
		String sql = "SELECT id FROM "+TABLENAME+" WHERE name=?";
		String args[] = {name};
		Cursor result = db.rawQuery(sql,args);
		for(result.moveToFirst() ; !result.isAfterLast() ; result.moveToNext()){
//		if(!result.isAfterLast()){
//			Map<String ,Object> map = new HashMap<String ,Object>();
//			map.put("id", result.getInt(0));
//			map.put("name", result.getString(1));
//			map.put("loc",result.getString(2));
			all.add(result.getInt(0));
		}
		this.db.close();
		return all;
		
		
	}
	public List<Map<String,Object>> find(int currentPage,int lineSize,String keyword){
		List<Map<String,Object> > all = new ArrayList<Map<String,Object> >();
		String sql = "SELECT id,name,loc FROM "+TABLENAME+" WHERE name LIKE ? OR loc LIKE ? LIMIT ?,?";
		String args[] = {"%"+keyword+"%","%"+keyword+"%",String.valueOf((currentPage-1)*lineSize),String.valueOf(lineSize)};
		Cursor result = db.rawQuery(sql, args);
		
		
//		String[] columns = {"name","loc"};
//		String keyword  = "3";
//		String selectionargs[] = {"%"+keyword+"%","%"+keyword+"%"};
//		String selection = "name LIKE ? OR loc LIKE ?";
//		Cursor result = db.query(TABLENAME, columns , selection, selectionargs, null, null, null);
//		
		for(result.moveToFirst() ; !result.isAfterLast() ; result.moveToNext()){
			Map<String ,Object> map = new HashMap<String ,Object>();
			int id = result.getInt(0);
			if(id == 1||id%3 == 0){
				map.put("id", id);
				map.put("name", result.getString(1));
				map.put("loc",result.getString(2));
				all.add(map);
			}
		}
		this.db.close();
		return all;
	}
}
