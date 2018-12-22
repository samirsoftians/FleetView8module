package com.example.fleetviewandroid;

import android.app.Activity;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
// Service is used for the perfroming the long background operation

public class MyService extends Service
{

	MyDBHelper dbHelper;
	// ArrayList<String> vehicles;
	ArrayList<String> vehicles = null;

	String[] arr = null;
	SQLiteDatabase db1 = null;
	SQLiteDatabase db = null;
	OnlineThread thread = null;
	String date = null, time = null, lat = null, log = null, location = null;
	int k = 0, i = 0;
	int size = 0;
	String speed = " ";
	ArrayList<String> codes;
	MyDBHelper help;
	Cursor c;
	// ProgressDialog progressDialog;
	@Override
	public void onStart(Intent intent, int startId)
	{
		// TODO Auto-generated method stub
		// super.onStart(intent, startId);
		// mt("inside service");
		vehicles = new ArrayList<String>();
		codes = new ArrayList<String>();
			dbHelper = new MyDBHelper(MyService.this);
		help = new MyDBHelper(MyService.this);
		db = help.getWritableDatabase();
		// getVehicleList();
		SharedPreferences pref = getSharedPreferences("vList",
				Activity.MODE_PRIVATE);
		int count = pref.getInt("count", -1);
		for (int i = 0; i < count; i++) {
			vehicles.add(pref.getString("vehicle" + i, null));
		}
		getCode();

		boolean bool = isOnline();
		if (bool == true)
		{
			// mDataLodingStatusMessageView.setText(R.string.data_fetching_progress_in);

			for (int i = 0; i < vehicles.size(); i++)
			{
				// mt("bool is true");

				thread = new OnlineThread();
				thread.execute((Void) null);

			}

		}
		else
		{
			Toast.makeText(getApplicationContext(), "No Internet connection.",
					Toast.LENGTH_LONG).show();
		}
	}



	private void getCode()
	{
		int x = vehicles.size();
		arr = new String[x];
		for (int i = 0; i < x; i++)
		{
			arr[i] = vehicles.get(i);
		}

		//dbHelper = new MyDBHelper(MyService.this);
		db1 = dbHelper.getReadableDatabase();
		for (int i = 0; i < vehicles.size(); i++) {
			String[] selectionArgs = { arr[i] };
			c = db1.query("onlineInfo", null, "vNum = ?",
					selectionArgs, null, null, null);
			c.move(0);
			while (c.moveToNext()) {
				// mt(c.getString(1));
				codes.add(c.getString(0));
			}			
		}
		//c.close();
		db1.close();
	}
	public boolean isOnline() {

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		boolean result = false;
		if (ni != null) {
			if (ni.getState() == NetworkInfo.State.CONNECTED) {
				result = true;
			}
		}

		return result;

	}

	public class OnlineThread extends AsyncTask<Void, String, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			/*
			 * progressDialog = new ProgressDialog(getApplication());
			 * progressDialog.setTitle("Fleetview App");
			 * progressDialog.setMessage("Accessing data....");
			 * progressDialog.setIcon(R.drawable.transworldlogo1);
			 * progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			 * progressDialog.show();
			 */
		}

		@Override
		protected Boolean doInBackground(Void... params)
		{

			// try { // Simulate network access.

			String response = "";
			try {

				String url = "http://103.241.181.36:8080/AndrFleetApp/OnlineData?vehiclecode=";
						 codes.get(i);
				i++;
				url = url.replaceAll(" ", "%20");
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);

				HttpResponse execute = client.execute(httpGet);
				InputStream content = execute.getEntity().getContent();

				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));

				String s = "";
				while ((s = buffer.readLine()) != null) {
					response += s;
				}
				// System.out.println("The response =>" + response);
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			} catch (Exception e) {
				System.out.println("Exception occured!!" + e);
				return false;
			}

			if (!(response.equals("No_Data"))) {

				try {
					String[] rows = response.split("\\$");

					publishProgress(rows);

				} catch (Exception e) {
					e.printStackTrace();
				}

				return true;
			} else {
				return false;
			}

		}

		// TODO: register the new account here. //return true;

		@Override
		protected void onProgressUpdate(String... arg) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(arg);
			// name = values[0];

			date = arg[0];
			time = arg[1];
			lat = arg[2];
			log = arg[3];
			location = arg[4];
			speed = arg[5];
			ContentValues values = new ContentValues();
			
			values.put("date", date);
			values.put("time", time);
			values.put("lat", lat);
			values.put("lng", log);
			values.put("location", location);
			values.put("speed", speed);
			// db.update("onlineInfo", values, null, null);

			Log.i(" TM "," LAT "+lat+" LNG "+log);

			String[] whereArgs = { codes.get(k) };
			db.update("onlineInfo", values, "vehicleCode=?", whereArgs);
			// long n = db.insert("onlineInfo", null, values);
			if (k < codes.size())
			{

				k++;

			}
			// db.close();
		}

		@Override
		protected void onPostExecute(final Boolean success)
		{




 		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private void mt(String text) {
		Toast.makeText(MyService.this, text, Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();

 	}
}
