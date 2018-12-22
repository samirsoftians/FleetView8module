    package com.example.fleetviewandroid;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelperClass
{
private static final String DATABASE_NAME = "Your database name"; 
private static final int DATABASE_VERSION = 1; 
private static final String DATABASE_CREATE_USERS = "create table TABLE_NAME (UserID integer primary key autoincrement, "
        + "Username text, Password text);"; 
private static final String DATABASE_SELECT_USERS="users";
public static final String USER_ID = "UserID";
public static final String USER_NAME = "Username";
public static final String USER_PASSWORD = "Password ";
private final Context context;
private DatabaseHelper DBHelper;
private SQLiteDatabase db;  

DbHelperClass(Context ctx)
{
this.context     = ctx;
DBHelper = new DatabaseHelper(context);
}
private static class DatabaseHelper extends SQLiteOpenHelper
        {
            DatabaseHelper(Context context) 
            {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            public void onCreate(SQLiteDatabase db) 
            {
System.out.println("Creating table");

db.execSQL(DATABASE_CREATE_USERS);
            }

    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

            }
        }   

public DbHelperClass open() throws SQLException 
        {
            db = DBHelper.getWritableDatabase();
            return this;
        }

public void close() 
        {
            DBHelper.close();
        }
public Cursor fetchUser(String username, String password) 
        {

            Cursor myCursor = db.query(DATABASE_SELECT_USERS,
          new String[]
                  { USER_ID, USER_NAME, USER_PASSWORD },
                         USER_NAME + "='" + username + "' AND " +   
                         USER_PASSWORD + "='" + password + "'", null, null, null, null);  

               if (myCursor != null) {  
                    myCursor.moveToFirst();  
               }  
               return myCursor;  
          }  
        public void InsertData(String username, String password)
        {
            String sql="INSERT INTO users (Username,Password) VALUES('"+username+"','"+password+"')";
            db.execSQL(sql);
        }
}