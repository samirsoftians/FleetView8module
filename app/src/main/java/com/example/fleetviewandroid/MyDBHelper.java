package com.example.fleetviewandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
//import android.database.DatabaseErrorHandler;

public class MyDBHelper extends SQLiteOpenHelper
{
	public MyDBHelper(Context context)
	{
		super(context, "fleetdb", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try
		{

			db.execSQL("create table onlineInfo(vehicleCode text primary key,vNum text,date text,time text,lat text,lng text,location text,status text,speed text)");
			db.execSQL("create table vehicleInfo(vehicleCode integer,vehicleRegNum text primary key,active text)");

		}
		catch(SQLiteException e)
		{



		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
			db.execSQL("DROP TABLE IF EXISTS vehicleInfo");
		 	db.execSQL("DROP TABLE IF EXISTS onlineInfo");
		    onCreate(db);              
	}
}
