package com.mmsx.app.personManagement;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.mmsx.app.utils.SqliteHelper;

import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
    private SqliteHelper helper;
    private static PersonDAO dao = null;

    //写入 ，不然会是出错，是空指针
    public PersonDAO(Context context) {
        helper = new SqliteHelper(context);
    }

    public static PersonDAO getInstance(Context context){
        if (dao == null){
            dao = new PersonDAO(context);
        }
        return dao;
    }


    /**
     * Add personnel
     */
    public void add(Person person) {
        SQLiteDatabase sqLiteDatabase = this.helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("phone", person.getPhone());
        cv.put("pname", person.getName());
        cv.put("pnumber", person.getNumber());
        cv.put("psex", person.getSex());
        cv.put("salary", person.getSalary());
        cv.put("job", person.getJob());
        cv.put("grade", person.getGrade());
        cv.put("pdepartment", person.getDepartment());
        cv.put("page", person.getAge());

        cv.put("rewards", person.getRewards());
        cv.put("money", person.getMoney());
        cv.put("family", person.getFamily());
        cv.put("fname", person.getFname());

        sqLiteDatabase.insert("staff", null, cv);
    }

    @SuppressLint("Range")
    public List<Person> getAllData(){
        List<Person> list = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql="select * from staff "; // Query all data
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            Person person = new Person();
            person.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            person.setName(cursor.getString(cursor.getColumnIndex("pname")));
            person.setNumber(cursor.getString(cursor.getColumnIndex("pnumber")));
            person.setSex(cursor.getString(cursor.getColumnIndex("psex")));
            person.setSalary(cursor.getString(cursor.getColumnIndex("salary")));
            person.setJob(cursor.getString(cursor.getColumnIndex("job")));
            person.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
            person.setDepartment(cursor.getString(cursor.getColumnIndex("pdepartment")));
            person.setAge(cursor.getInt(cursor.getColumnIndex("page")));
            person.setRewards(cursor.getString(cursor.getColumnIndex("rewards")));
            person.setMoney(cursor.getString(cursor.getColumnIndex("money")));
            person.setFamily(cursor.getString(cursor.getColumnIndex("family")));
            person.setFname(cursor.getString(cursor.getColumnIndex("fname")));
            list.add(person);
        }

        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }

        return list;
    }

    @SuppressLint("Range")
    public List<Person> queryAll(int mark, String text) {
        SQLiteDatabase db = helper.getWritableDatabase();

        List<Person> list = new ArrayList<>();
        Person person;
        String sql ;
        Cursor cursor = null;
        if (TextUtils.isEmpty(text)){
            sql="select * from staff "; // Query all data
        }else {
            if(mark==0){
                sql = "SELECT * FROM staff WHERE pname LIKE '%" + text + "%'" + " order by _id desc"; // Build SQL statement
            }else {
                sql = "SELECT * FROM staff WHERE pnumber LIKE '%" + text + "%'" + " order by _id desc"; // Build SQL statement
            }
        }
        cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            person = new Person();
            person.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            person.setName(cursor.getString(cursor.getColumnIndex("pname")));
            person.setNumber(cursor.getString(cursor.getColumnIndex("pnumber")));
            person.setSex(cursor.getString(cursor.getColumnIndex("psex")));
            person.setSalary(cursor.getString(cursor.getColumnIndex("salary")));
            person.setJob(cursor.getString(cursor.getColumnIndex("job")));
            person.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
            person.setDepartment(cursor.getString(cursor.getColumnIndex("pdepartment")));
            person.setAge(cursor.getInt(cursor.getColumnIndex("page")));
            person.setRewards(cursor.getString(cursor.getColumnIndex("rewards")));
            person.setMoney(cursor.getString(cursor.getColumnIndex("money")));
            person.setFamily(cursor.getString(cursor.getColumnIndex("family")));
            person.setFname(cursor.getString(cursor.getColumnIndex("fname")));
            list.add(person);
        }

        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }

        return list;
    }

    /**
     * Delete an employee with a specified number
     */
    public void delete(String... id) {
        if (id.length > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < id.length; i++) {
                sb.append("?").append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            SQLiteDatabase database = helper.getWritableDatabase();
            String sql = "delete from staff where pnumber in (" + sb + ")";
            database.execSQL(sql, (Object[]) id);
        }
    }

    /**
     * Delete all data in the table
     */
    public void delelteall() {
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql = "delete from staff";
        database.execSQL(sql);
    }

    /**
     * Modify data according to employee number
     */
    public void update(Person person) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("phone", person.getPhone());
        cv.put("pname", person.getName());
        cv.put("pnumber", person.getNumber());
        cv.put("psex", person.getSex());
        cv.put("salary", person.getSalary());
        cv.put("job", person.getJob());
        cv.put("grade", person.getGrade());
        cv.put("pdepartment", person.getDepartment());
        cv.put("page", person.getAge());
        cv.put("rewards", person.getRewards());
        cv.put("money", person.getMoney());
        cv.put("family", person.getFamily());
        cv.put("fname", person.getFname());
        db.update("staff", cv, "_id=?", new String[]{person.getId() + ""});
        db.close();
    }

    /**
     * Find information about the specified employee number
     */
    @SuppressLint("Range")
    public Person find(String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from staff where pnumber=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            Person person = new Person();
            person.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            person.setName(cursor.getString(cursor.getColumnIndex("pname")));
            person.setNumber(cursor.getString(cursor.getColumnIndex("pnumber")));
            person.setSex(cursor.getString(cursor.getColumnIndex("psex")));
            person.setSalary(cursor.getString(cursor.getColumnIndex("salary")));
            person.setJob(cursor.getString(cursor.getColumnIndex("job")));
            person.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
            person.setDepartment(cursor.getString(cursor.getColumnIndex("pdepartment")));
            person.setAge(cursor.getInt(cursor.getColumnIndex("page")));
            person.setRewards(cursor.getString(cursor.getColumnIndex("rewards")));
            person.setMoney(cursor.getString(cursor.getColumnIndex("money")));
            person.setFamily(cursor.getString(cursor.getColumnIndex("family")));
            person.setFname(cursor.getString(cursor.getColumnIndex("fname")));
            return person;
        }
        return null;
    }

    /**
     * Find information about the name of the specified employee
     */
    @SuppressLint("Range")
    public Person findName(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from staff where pname=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(name)});
        if (cursor.moveToNext()) {
//			  pnumber varchar(20)," +
//			  "pname varchar(20),psex varchar(5),page integer,job varchar(20),pdepartment varchar(50),grade varchar(20),salary varchar(20) )

            Person person = new Person();
            person.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            person.setName(cursor.getString(cursor.getColumnIndex("pname")));
            person.setNumber(cursor.getString(cursor.getColumnIndex("pnumber")));
            person.setSex(cursor.getString(cursor.getColumnIndex("psex")));
            person.setSalary(cursor.getString(cursor.getColumnIndex("salary")));
            person.setJob(cursor.getString(cursor.getColumnIndex("job")));
            person.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
            person.setDepartment(cursor.getString(cursor.getColumnIndex("pdepartment")));
            person.setAge(cursor.getInt(cursor.getColumnIndex("page")));

            person.setRewards(cursor.getString(cursor.getColumnIndex("rewards")));
            person.setMoney(cursor.getString(cursor.getColumnIndex("money")));
            person.setFamily(cursor.getString(cursor.getColumnIndex("family")));
            person.setFname(cursor.getString(cursor.getColumnIndex("fname")));

            return person;

        }
        return null;
    }

    /**
     * Display employee information
     */
    public Cursor select() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("staff", null, null, null, null, null, "_id asc");
        return cursor;
    }
}
