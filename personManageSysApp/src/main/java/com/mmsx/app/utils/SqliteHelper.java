package com.mmsx.app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteHelper extends SQLiteOpenHelper {	
	private static final int VERSION=4;//版本
	public static final String DB_NAME = "liandatabase.db";//数据库名字

	//创建数据库 
	public SqliteHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		Log.d("database", "Successful start");
	}
	/**
	 * 创建新数据库表
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
			/**
			 * 人事信息主要包括: 员工编号、所在部门、职务、薪资等级、薪资、联系方式等内容。
			 * (2) 部门信息主要包括: 部门编号、部门名称等内容
			 */
		    db.execSQL("create table if not exists staff(_id integer primary key autoincrement,pnumber varchar(20)," +
					"pname varchar(20),psex varchar(5),page integer,job varchar(20),pdepartment varchar(50),grade varchar(20),salary varchar(20),phone varchar(11),rewards varchar(11)," +
					"money varchar(11), family varchar(11),  fname varchar(11))");
		    db.execSQL("create table if not exists department(_id integer primary key autoincrement,departmentID varchar(20),departmentName varchar(50),principal varchar(20),tel varchar(11),personNum integer)");
		    Log.i("Database table", "Created successfully！");
		}
		catch(Exception e){
			Log.i("Database table", "Creation failure！");
			Log.i("Database table", e.getMessage());
		}
	}
	/**
	 * 当检测与前一次创建数据库版本不一样时，先删除表再创建新表
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // Create tables again
		db.execSQL("DROP TABLE IF EXISTS staff");
		db.execSQL("DROP TABLE IF EXISTS department");
	    onCreate(db);
	}
}
