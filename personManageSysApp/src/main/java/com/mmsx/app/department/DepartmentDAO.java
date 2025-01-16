package com.mmsx.app.department;



import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mmsx.app.utils.SqliteHelper;

public class DepartmentDAO {
	private SqliteHelper helper;
	 //Write, otherwise it's an error. It's a null pointer
	 public DepartmentDAO(Context context){
		 helper=new SqliteHelper(context);
	 }
	 /**
	  * Add department
	  */
	 public void add(Department department){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into department(departmentID,departmentName,principal,tel) values(?,?,?,?)";
		 db.execSQL(sql, new Object[]{department.getDepartmentID(),department.getDepartmentName(),
				    department.getPrincipal(),department.getTel()});
		 db.close();
	 }
	 /**
	  * Delete the department with the specified number
	  */
	  public void delete(String...id){
		  if(id.length>0){
			 StringBuffer sb=new StringBuffer();
			 for(int i=0;i<id.length;i++){
				 sb.append("?").append(",");
			 }
			 sb.deleteCharAt(sb.length()-1);
			 SQLiteDatabase database=helper.getWritableDatabase();
			 String sql="delete from department where departmentID in ("+sb+")";
			 database.execSQL(sql, (Object[])id);
		  }
	  }
	  /**
	   * Delete all data in the table
	   */
	  public void delelteall(){
			 SQLiteDatabase database=helper.getWritableDatabase();
			 String sql = "delete from department";
			 database.execSQL(sql);
	  }
	  /**
	   * Modify the data according to the number
	   */
	  public void update(Department department){
		  SQLiteDatabase db=helper.getWritableDatabase();
	 	  String sql="update department set departmentName=?,principal=?,tel=?  where departmentID=?";
	 	  db.execSQL(sql, new Object[]{department.getDepartmentName(),department.getPrincipal(),department.getTel(),department.getDepartmentID()});
	  }
	  /**
	   * Finds information for the specified department number
	   */
	  @SuppressLint("Range")
	  public Department find(String  id){
		  SQLiteDatabase db=helper.getWritableDatabase();
	 	  String sql="select departmentID,departmentName,principal,tel  from department where departmentID=?";
	 	  Cursor cursor=db.rawQuery(sql, new String[]{String.valueOf(id)});
	 	  if(cursor.moveToNext()){
	 		 return new Department(
	 				 cursor.getString(cursor.getColumnIndex("departmentID")),
	 				 cursor.getString(cursor.getColumnIndex("departmentName")), 
	 		         cursor.getString(cursor.getColumnIndex("principal")),
	 		         cursor.getString(cursor.getColumnIndex("tel")));
	 	 }
	 	 return null;
	 }
	/**
	 * Show department information
	 */
	public Cursor select() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("department",
			null, null, null, null,null,"_id asc");
		return cursor;
	}
}
