package com.example.fleetviewandroid;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainForm extends Activity implements OnClickListener
{

	String user,passwd;
	ListView listView;
	int activity=1;
	String[] list={"Add Request","Add Odometer","View Violation Report","L1 Generate Report"};
	TextView  Request,Odometer,viewreport,l1generate,fuelentry,currentposition,tripentry,devicests;
	String getusername;
	ArrayList<String> array=new ArrayList<String>();
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mainform_new);

		Request=(TextView)findViewById(R.id.img1_vehicle_intimation);
		Odometer=(TextView)findViewById(R.id.img2_odometer_reading);
		viewreport=(TextView)findViewById(R.id.img3_alerts);
		l1generate=(TextView)findViewById(R.id.img4_violation);
		fuelentry=(TextView)findViewById(R.id.img3_fuelentry);
		currentposition=(TextView)findViewById(R.id.img4_currentposition);
		tripentry=(TextView)findViewById(R.id.tripentry);
		devicests=(TextView)findViewById(R.id.devicestatus);

		  Typeface face = Typeface.createFromAsset(getAssets(),
		             "arial.ttf");
		 
		Intent ins=getIntent();
		user=ins.getStringExtra("Username");
		passwd=ins.getStringExtra("password");

		TextView textView= (TextView) findViewById(R.id.loginname);
		textView.setText(""+LoginForm.name);

		Request.setOnClickListener(this);
		Odometer.setOnClickListener(this);
		viewreport.setOnClickListener(this);
		l1generate.setOnClickListener(this);
		fuelentry.setOnClickListener(this);
		currentposition.setOnClickListener(this);
		tripentry.setOnClickListener(this);
		devicests.setOnClickListener(this);

	}
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{

		case R.id.img1_vehicle_intimation:
			Intent i=new Intent(getApplicationContext(),AddRequest.class);
			i.putExtra("name",LoginForm.name);
			finish();
//			i.putExtra("vehlist",LoginForm.arrayList);
			startActivity(i);
			break;
		case R.id.img2_odometer_reading:
			Intent in=new Intent(getApplicationContext(),OdoMeter.class);
			in.putExtra("name",LoginForm.name);
			finish();
//			in.putExtra("vehlist", array);
			startActivity(in);
			break;
		case R.id.img3_alerts:
			Intent ins=new Intent(getApplicationContext(),Alerts.class);
//			ins.putExtra("name",LoginForm.name);
			ins.putExtra("Alerts", "Alerts");
			startActivity(ins);
			break;
		case R.id.img4_violation:
			//Toast.makeText(this, "Under Construction", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(getApplicationContext(),Violation.class);
//			intent.putExtra("name",LoginForm.name);
			intent.putExtra("violations", "Violations");
			startActivity(intent);
			break;

	 	case R.id.img3_fuelentry:
			Intent intent1=new Intent(getApplicationContext(),FuelEntry.class);
			intent1.putExtra("name", LoginForm.name);
			intent1.putExtra("Fuel", "fuel");
			intent1.putExtra("vehlist",LoginForm.arrayList);
			System.out.println("--===in fuelintent");
			startActivity(intent1);
			break;

			case R.id.img4_currentposition:
			Intent intent2=new Intent(getApplicationContext(),CurrentPosition.class);
			intent2.putExtra("name", user);
			intent2.putExtra("passwd", passwd);
			intent2.putExtra("Vehlist",LoginForm.arrayList);
			System.out.println("--===in pos");
			startActivity(intent2);
			break;

			case R.id.tripentry:
				Intent intent3=new Intent(getApplicationContext(),TripEntryForm.class);
				intent3.putExtra("name", user);
				intent3.putExtra("passwd", passwd);
				intent3.putExtra("Vehlist",LoginForm.arrayList);
				System.out.println("--===in pos");
				startActivity(intent3);
				break;


			case R.id.devicestatus:
				Intent intent4=new Intent(getApplicationContext(),DeviceStatus.class);
				intent4.putExtra("name", user);
				intent4.putExtra("passwd", passwd);
				intent4.putExtra("Vehlist",LoginForm.arrayList);
				System.out.println("--===in pos");
				startActivity(intent4);
				break;



		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuItem menuItem;

		menuItem=menu.add(0,1,1,"LOG OUT");

		return super.onCreateOptionsMenu(menu);


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		if(item.getItemId()==1)
		{

			Intent intent=new Intent(getApplicationContext(),LoginForm.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			LoginForm.arrayList.clear();
			finish();
			startActivity(intent);

		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("NewApi")
	@Override
	public void onBackPressed()
	{


		Intent i=new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_HOME);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);

	}
@Override
   public void onResume(){
       super.onResume();

     /*  SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext()); 
       SharedPreferences.Editor editor;
        getusername= sharedpreferences.getString("user", null);*/
	}

}