package com.example.fleetviewandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Violation extends Activity implements OnClickListener
{

	Button today,yesturday,lastweek,lastmonth;
//	TextView heading;
	String logas,days,violation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alerts_new_layout);

		
		Intent i=getIntent();
		logas=i.getStringExtra("name");
	//	logas="vinod.motha@se1.bp.com";
		violation=i.getStringExtra("violations");
		
		 Typeface face = Typeface.createFromAsset(getAssets(),
	             "arial.ttf");


	String name=i.getStringExtra("violations");
	TextView textView= (TextView) findViewById(R.id.namesetter);
		textView.setText(""+name);

	today=(Button)findViewById(R.id.alerts_today);
	yesturday=(Button)findViewById(R.id.alerts_yestrday);
	lastweek=(Button) findViewById(R.id.alerts_lastweek);
	lastmonth=(Button)findViewById(R.id.alerts_lastmonth);
//	heading=(TextView) findViewById(R.id.viotext);
	today.setTypeface(face);
	yesturday.setTypeface(face);
	lastweek.setTypeface(face);
	lastmonth.setTypeface(face);
//	heading.setTypeface(face);
//	heading.setText(violation);
	today.setOnClickListener(this);
	yesturday.setOnClickListener(this);
	lastweek.setOnClickListener(this);
	lastmonth.setOnClickListener(this);

	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
	
		
		switch (v.getId()) {
		case R.id.alerts_today:
			Calendar c = Calendar.getInstance();
			System.out.println("Current time =&gt; "+c.getTime());
			 days="Today";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = df.format(c.getTime());
			// Now formattedDate have current date/time
			Intent i=new Intent(this,ViewViloationReport.class);
			Log.d("FLEET","Today"+formattedDate);
			i.putExtra("date", formattedDate);
			i.putExtra("logos",LoginForm.name);
			i.putExtra("days", days);
			startActivity(i);		
			Toast.makeText(this, "Wait Till Process", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.alerts_yestrday:
			Calendar c1 = Calendar.getInstance();
			System.out.println("Current time =&gt; "+c1.getTime());
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			String date = dft.format(c1.getTime());
			days="Yesturday";
			 c1.add(Calendar.DATE, -1);
			 System.out.println("Yesterday's date was "+dft.format(c1.getTime()));  
			 String ydate=dft.format(c1.getTime());
			Log.d("FLEET","YEST"+ydate);
			Intent in=new Intent(this,ViewViloationReport.class);
			in.putExtra("date",ydate );
			in.putExtra("logos",LoginForm.name);
			in.putExtra("days", days);
			startActivity(in);
			Toast.makeText(this, "Wait Till Process", Toast.LENGTH_SHORT).show();

			break;
			
		case R.id.alerts_lastweek:
			days="Week";
			Calendar cal = Calendar.getInstance();
			System.out.println("Current time =&gt; "+cal.getTime());
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			String dat = sdf.format(cal.getTime());
			 cal.add(Calendar.DATE, -7);
			 System.out.println("one week date was "+sdf.format(cal.getTime()));  
			 String oneweek=sdf.format(cal.getTime());
			Log.d("FLEET","YEST"+oneweek);

			Intent intent=new Intent(this,ViewViloationReport.class);
			intent.putExtra("date",oneweek );
			intent.putExtra("logos",LoginForm.name);
			intent.putExtra("days",days);
			startActivity(intent);
			Toast.makeText(this, "Wait Till Process", Toast.LENGTH_SHORT).show();
		break;
		case R.id.alerts_lastmonth:
			days="Month";
			Calendar calendar = Calendar.getInstance();
			System.out.println("Current time =&gt; "+calendar.getTime());
			SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd");
			String dates = sd.format(calendar.getTime());
			calendar.add(Calendar.DATE, -30);
			System.out.println("one month date was "+sd.format(calendar.getTime()));  
			String onemonth=sd.format(calendar.getTime());
			Log.d("FLEET","YEST"+onemonth);
			Intent inn=new Intent(this,ViewViloationReport.class);
			inn.putExtra("date",onemonth);
			inn.putExtra("logos",LoginForm.name);
			inn.putExtra("days", days);
			startActivity(inn);	
			Toast.makeText(this, "Wait Till Process", Toast.LENGTH_SHORT).show();

			break;
		}
	}
}
