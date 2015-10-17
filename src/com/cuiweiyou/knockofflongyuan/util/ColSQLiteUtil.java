package com.cuiweiyou.knockofflongyuan.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.cuiweiyou.knockofflongyuan.bean.CollectionBean;

/**
 * sqlite数据库工具<br/>
 * 重写onCreate、onUpgrade方法<br/>
 * 增加自定义增删改查方法
 * @author Administrator
 */
public class ColSQLiteUtil extends SQLiteOpenHelper{

	/**
	 * sqlite数据库工具<br/>
	 * 必须的构造方法
	 * @param context 上下文
	 * @param name    db文件名
	 * @param factory 游标工厂
	 * @param version 始于1的版本号
	 */
	public ColSQLiteUtil(Context context, String name, CursorFactory factory, int version) {
		super(context, "db_ly.db", null, 1);
	}
	
	/**
	 * 初次连接时执行此方法，创建db文件
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		//除了查找都是用execSQL
		db.execSQL(
				"CREATE TABLE db_ly (" +
					"_id INTEGER PRIMARY KEY, " +
					"title varchar, " +
					"titleid varchar" +
				");" );
	}

	/**
	 * 数据库版本更新时。即构造方法的version参数值增加<br/>
	 * oldVersion：原版本
	 * newVersion：新版本
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(newVersion > oldVersion){
			// 再原表结构的基础上，插入新的列
			db.execSQL("ALTER TABLE db_ly ADD date varchar");
			// 如果有数据的话，可用继续导入操作
			// ...
			
			/* 
			 * 如果新版app业务复杂，可能须要重构数据库
			 * 1.将现在的表重命名为临时表，tmp_xxx
			 * 2.创建新的表，db_yyy，完全新设计的表结构
			 * 3.从tmp_xxx表将数据导入db_yyy
			 * 4.删除tmp_xxx
			 */
		}
	}
	
	/**
	 * 插入数据<br/>
	 * 插入是个写操作，通过 getWritableDatabase() 得到可写的数据库实例<br/>
	 * 与getReadableDatabase()比较<br/>
	 * 相同点:都可以获取SqliteDatabase对象
	 * 不同点:如果数据库的存储达到了上限，只能使用getReadableDatabase()获取一个只读的数据库对象
	 **/;
	public void insert(String title, String titleID){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO db_ly (title, titleid) values ('" + title + "', '" + titleID + "')");
		db.close();
	
		/*
	 	 * "too many terms in compound SELECT"
		 * 每次执行向数据库插入数据的条数上限是500条
		 * 
		 * SQLite版本3.0则对单个记录容量没有限制
		 * 
		 * SQLite supports databases up to 140 terabytes in size
		 * 
		 * 各种最属性：http://www.sqlite.org/limits.html
		 */
	}

	/**
	 * 删除记录
	 * @param titleid 条件
	 */
	public void delete(String titleid){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM db_ly WHERE titleid = '" + titleid + "'");
		db.close();
	}

//	/**
//	 * 更新记录
//	 * @param title 更新标题
//	 * @param _id 条件
//	 */
//	public void update(String title , String _id){
//		SQLiteDatabase db = getWritableDatabase();
//		db.execSQL("UPDATE db_ly SET title = '" + title + "' WHERE _id = " + _id);
//		db.close();
//	}

	/**
	 * 查询全部记录<br/>
	 * getReadableDatabase()得到只读实例
	 * @return 封装后的记录bean集合
	 */
	public List<CollectionBean> selectAll(){

		List<CollectionBean>list = new ArrayList<CollectionBean>();
		
		SQLiteDatabase db = getReadableDatabase();
		
		// entry记录集合对象
		//Cursor cursor = db.rawQuery("SELECT * FROM db_ly WHERE _id > ? ;", new String[]{1});
		Cursor cursor = db.rawQuery("SELECT * FROM db_ly;", null);
		
		// 开始遍历entry记录
		while(cursor.moveToNext()){
			//int index = cursor.getColumnIndex("_id");			// 根据列名得到列索引
			int title = cursor.getColumnIndex("title");
			int titleID = cursor.getColumnIndex("titleid");
			
			list.add(new CollectionBean(cursor.getString(titleID), cursor.getString(title)));
			
		}
		
		cursor.close();
		db.close();
		
		return list;
	}
	
	/**
	 * 根据条件查询
	 * @param arg 条件
	 * @return
	 */
	public boolean query(String arg){

		SQLiteDatabase db = getReadableDatabase();
		
		// entry记录集合对象
		Cursor cursor = db.rawQuery("SELECT * FROM db_ly WHERE titleid = ?;", new String[]{arg});
		return cursor.moveToNext();
	}
	 
//	/**
//	 * @return
//	 */
//	public Cursor select(){
//		SQLiteDatabase db = getReadableDatabase();
//		Cursor  cursor = db.rawQuery("select * from db_ly", null);
//		return cursor;
//	}
	
}
