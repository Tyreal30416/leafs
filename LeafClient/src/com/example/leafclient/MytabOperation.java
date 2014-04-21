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
		
			this.insert(1,"º¼ÖÝ»Æ", "hangzhouhuang");
			this.insert(2,"º¼ÖÝ»Æ", "hangzhouhuang");
			this.insert(3,"ÂÞººËÉ", "luohansong");
			this.insert(4,"ÂÞººËÉ", "luohansong");
			this.insert(5,"ÂÞººËÉ", "luohansong");
			this.insert(6,"Ó³É½ºì", "yingshanhong");
			this.insert(7,"Ó³É½ºì", "yingshanhong");
			this.insert(8,"Ó³É½ºì", "yingshanhong");
			this.insert(9,"ÀÆÓÜ", "langyu");
			this.insert(10,"ÀÆÓÜ", "langyu");
			this.insert(11,"ÀÆÓÜ", "langyu1");
			this.insert(12,"ÌÇéÊ", "tangqi");
			this.insert(13,"ÌÇéÊ", "tangqi");
			this.insert(14,"ÌÇéÊ", "tangqi");
			this.insert(15,"´ÌÃ«¶Å¾é", "cimaodujuan");
			this.insert(16,"´ÌÃ«¶Å¾é", "cimaodujuan");
			this.insert(17,"´ÌÃ«¶Å¾é", "cimaodujuan");
			this.insert(18,"ÍíÜ°", "wanxin");
			this.insert(19,"ÍíÜ°", "wanxin");
			this.insert(20,"ÍíÜ°", "wanxin");
			this.insert(21,"ÑòõÜõî", "yangzhizhu");
			this.insert(22,"ÑòõÜõî", "yangzhizhu");
			this.insert(23,"ÑòõÜõî", "yangzhizhu");
			this.insert(24,"ËÄ¼¾¹ð", "sijigui");
			this.insert(25,"ËÄ¼¾¹ð", "sijigui");
			this.insert(26,"ËÄ¼¾¹ð", "sijigui");
			this.insert(27,"É½²èºñÆ¤Ïã", "shanchahoupixiang");
			this.insert(28,"É½²èºñÆ¤Ïã", "shanchahoupixiang");
			this.insert(29,"É½²èºñÆ¤Ïã", "shanchahoupixiang");
			this.insert(30,"²¨Ò¶½ð¹ð", "boyejingui");
			this.insert(31,"²¨Ò¶½ð¹ð", "boyejingui");
			
			this.insert(32,"²¨Ò¶½ð¹ð", "boyejingui");
			this.insert(33,"×í¼¡ºì", "zuijihong");
			this.insert(34,"×í¼¡ºì", "zuijihong");
			this.insert(35,"×í¼¡ºì", "zuijihong");
			this.insert(36,"Ò¶A", "a");
			this.insert(37,"Ò¶A", "a");
			this.insert(38,"Ò¶A", "a");
			this.insert(39,"Ò¶B", "b");
			this.insert(40,"Ò¶B", "b");
			this.insert(41,"Ò¶B", "b");
			this.insert(42,"Ò¶C", "c");
			this.insert(43,"Ò¶C", "c");
			this.insert(44,"Ò¶C", "c");
			this.insert(45,"Ò¶D", "d");
			this.insert(46,"Ò¶D", "d");
			this.insert(47,"Ò¶D", "d");
			this.insert(48,"Ò¶E", "e");
			this.insert(49,"Ò¶E", "e");
			this.insert(50,"Ò¶E", "e");
			this.insert(51,"Ò¶F", "f");
			this.insert(52,"Ò¶F", "f");
			
			this.insert(53,"Ò¶F", "f");
			this.insert(54,"Ò¶G", "g");
			this.insert(55,"Ò¶G", "g");
			this.insert(56,"Ò¶G", "g");
			this.insert(57,"Ò¶H", "h");
			this.insert(58,"Ò¶H", "h");
			this.insert(59,"Ò¶H", "h");
			this.insert(60,"Ò¶I", "i");
			this.insert(61,"Ò¶I", "i");
			this.insert(62,"Ò¶I", "i");
			this.insert(63,"Ò¶J", "j");
			this.insert(64,"Ò¶J", "j");
			this.insert(65,"Ò¶J", "j");
			this.insert(66,"Ò¶K", "k");
			this.insert(67,"Ò¶K", "k");
			this.insert(68,"Ò¶K", "k");
			this.insert(69,"Ò¶L" , "l");
			this.insert(70,"Ò¶L" , "l");
			this.insert(71,"Ò¶L" , "l");
			this.insert(72,"Ò¶M" , "m");
			this.insert(73,"Ò¶M" , "m");
			this.insert(74,"Ò¶M" , "m");
			this.insert(75,"Ò¶N" , "n");
			this.insert(76,"Ò¶N" , "n");
			this.insert(77,"Ò¶N" , "n");
			this.insert(78,"Ò¶O" , "o");
			this.insert(79,"Ò¶O" , "o");
			this.insert(80,"Ò¶O" , "o");
			this.insert(81,"Ò¶P" , "p");
			this.insert(82,"Ò¶P" , "p");
			this.insert(83,"Ò¶P" , "p");
			this.insert(84,"Ò¶Q" , "q");
			this.insert(85,"Ò¶Q" , "q");
			this.insert(86,"Ò¶Q" , "q");
			this.insert(87,"Ò¶R" , "r");
			this.insert(88,"Ò¶R" , "r");
			this.insert(89,"Ò¶R" , "r");
			this.insert(90,"Ò¶S" , "s");
			this.insert(91,"Ò¶S" , "s");
			this.insert(92,"Ò¶S" , "s");
			this.insert(93,"Ò¶T" , "t");
			this.insert(94,"Ò¶T" , "t");
			this.insert(95,"Ò¶T" , "t");
			
			this.db.close();
		
		return ;
	}
	public void insert(int id,String name,String loc){
		//String sql = "INSERT INTO "+TABLENAME+" (name,loc) VALUES ('"+name+"','"+loc+"')";
		//·½·¨Ò»
//		String sql = "INSERT INTO "+TABLENAME+" (name,loc) VALUES (?,?)";
//		Object arg[] = new Object[]{name,loc}; 
//		this.db.execSQL(sql,arg);
		//·½·¨2
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
