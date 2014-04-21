package com.example.leafclient;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class MytabOperation {
	private static final String TABLENAME = "leafs";
	private SQLiteDatabase db = null;
	public MytabOperation(SQLiteDatabase db){
		this.db = db ;
	}
	public void init(){
		
			this.insert(1,"���ݻ�", "hangzhouhuang");
			this.insert(2,"���ݻ�", "hangzhouhuang");
			this.insert(3,"�޺���", "luohansong");
			this.insert(4,"�޺���", "luohansong");
			this.insert(5,"�޺���", "luohansong");
			this.insert(6,"ӳɽ��", "yingshanhong");
			this.insert(7,"ӳɽ��", "yingshanhong");
			this.insert(8,"ӳɽ��", "yingshanhong");
			this.insert(9,"����", "langyu");
			this.insert(10,"����", "langyu");
			this.insert(11,"����", "langyu1");
			this.insert(12,"����", "tangqi");
			this.insert(13,"����", "tangqi");
			this.insert(14,"����", "tangqi");
			this.insert(15,"��ë�ž�", "cimaodujuan");
			this.insert(16,"��ë�ž�", "cimaodujuan");
			this.insert(17,"��ë�ž�", "cimaodujuan");
			this.insert(18,"��ܰ", "wanxin");
			this.insert(19,"��ܰ", "wanxin");
			this.insert(20,"��ܰ", "wanxin");
			this.insert(21,"������", "yangzhizhu");
			this.insert(22,"������", "yangzhizhu");
			this.insert(23,"������", "yangzhizhu");
			this.insert(24,"�ļ���", "sijigui");
			this.insert(25,"�ļ���", "sijigui");
			this.insert(26,"�ļ���", "sijigui");
			this.insert(27,"ɽ���Ƥ��", "shanchahoupixiang");
			this.insert(28,"ɽ���Ƥ��", "shanchahoupixiang");
			this.insert(29,"ɽ���Ƥ��", "shanchahoupixiang");
			this.insert(30,"��Ҷ���", "boyejingui");
			this.insert(31,"��Ҷ���", "boyejingui");
			
			this.insert(32,"��Ҷ���", "boyejingui");
			this.insert(33,"����", "zuijihong");
			this.insert(34,"����", "zuijihong");
			this.insert(35,"����", "zuijihong");
			this.insert(36,"ҶA", "a");
			this.insert(37,"ҶA", "a");
			this.insert(38,"ҶA", "a");
			this.insert(39,"ҶB", "b");
			this.insert(40,"ҶB", "b");
			this.insert(41,"ҶB", "b");
			this.insert(42,"ҶC", "c");
			this.insert(43,"ҶC", "c");
			this.insert(44,"ҶC", "c");
			this.insert(45,"ҶD", "d");
			this.insert(46,"ҶD", "d");
			this.insert(47,"ҶD", "d");
			this.insert(48,"ҶE", "e");
			this.insert(49,"ҶE", "e");
			this.insert(50,"ҶE", "e");
			this.insert(51,"ҶF", "f");
			this.insert(52,"ҶF", "f");
			
			this.insert(53,"ҶF", "f");
			this.insert(54,"ҶG", "g");
			this.insert(55,"ҶG", "g");
			this.insert(56,"ҶG", "g");
			this.insert(57,"ҶH", "h");
			this.insert(58,"ҶH", "h");
			this.insert(59,"ҶH", "h");
			this.insert(60,"ҶI", "i");
			this.insert(61,"ҶI", "i");
			this.insert(62,"ҶI", "i");
			this.insert(63,"ҶJ", "j");
			this.insert(64,"ҶJ", "j");
			this.insert(65,"ҶJ", "j");
			this.insert(66,"ҶK", "k");
			this.insert(67,"ҶK", "k");
			this.insert(68,"ҶK", "k");
			this.insert(69,"ҶL" , "l");
			this.insert(70,"ҶL" , "l");
			this.insert(71,"ҶL" , "l");
			this.insert(72,"ҶM" , "m");
			this.insert(73,"ҶM" , "m");
			this.insert(74,"ҶM" , "m");
			this.insert(75,"ҶN" , "n");
			this.insert(76,"ҶN" , "n");
			this.insert(77,"ҶN" , "n");
			this.insert(78,"ҶO" , "o");
			this.insert(79,"ҶO" , "o");
			this.insert(80,"ҶO" , "o");
			this.insert(81,"ҶP" , "p");
			this.insert(82,"ҶP" , "p");
			this.insert(83,"ҶP" , "p");
			this.insert(84,"ҶQ" , "q");
			this.insert(85,"ҶQ" , "q");
			this.insert(86,"ҶQ" , "q");
			this.insert(87,"ҶR" , "r");
			this.insert(88,"ҶR" , "r");
			this.insert(89,"ҶR" , "r");
			this.insert(90,"ҶS" , "s");
			this.insert(91,"ҶS" , "s");
			this.insert(92,"ҶS" , "s");
			this.insert(93,"ҶT" , "t");
			this.insert(94,"ҶT" , "t");
			this.insert(95,"ҶT" , "t");
			
			this.db.close();
		
		return ;
	}
	public void insert(int id,String name,String loc){
		//String sql = "INSERT INTO "+TABLENAME+" (name,loc) VALUES ('"+name+"','"+loc+"')";
		//����һ
//		String sql = "INSERT INTO "+TABLENAME+" (name,loc) VALUES (?,?)";
//		Object arg[] = new Object[]{name,loc}; 
//		this.db.execSQL(sql,arg);
		//����2
		this.db.beginTransaction();
		try{
//			String sql = "INSERT INTO "+TABLENAME+" (id,name,loc) VALUES (?,?,?)";
//			Object arg[] = new Object[]{id,name,loc}; 
//			this.db.execSQL(sql,arg);
			ContentValues cv = new ContentValues();
			cv.put("name", name);
			cv.put("loc", loc);
			db.insert(TABLENAME, null, cv);
			this.db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.db.endTransaction();
		}
		
		
//		this.db.close();
	}
	public void update(int id,String name,String loc){
//		String sql = "UPDATE "+TABLENAME+" SET name=?,loc=? WHERE id=?";
//		Object arg[] = new Object[]{name,loc,id}; 
//		this.db.execSQL(sql,arg);
		this.db.beginTransaction();
		try{
			ContentValues cv = new ContentValues();
			cv.put("name", name);
			cv.put("loc", loc);
			String whereClause = "id=?";
			String whereArgs[] = new String[]{String.valueOf(id)};
			db.update(TABLENAME, cv, whereClause, whereArgs);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.db.endTransaction();
		}
		this.db.close();
	}
	public void delete(){
		this.db.beginTransaction();
		try{
			String sql = "DELETE FROM "+TABLENAME;
//			Object arg[] = new Object[]{id}; 
			this.db.delete(TABLENAME, null, null);
			System.out.println("OK");
//			this.db.execSQL(sql);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("nook");
		}finally{
			this.db.endTransaction();
		}
		this.db.close();
	}
	public void delete(int id){
		this.db.beginTransaction();
		try{
			String sql = "DELETE FROM "+TABLENAME+" WHERE id=?";
			Object arg[] = new Object[]{id}; 
			this.db.execSQL(sql,arg);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.db.endTransaction();
		}
		this.db.close();
	}
}
