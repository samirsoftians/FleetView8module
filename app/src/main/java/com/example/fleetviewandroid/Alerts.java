package com.example.fleetviewandroid;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Alerts extends Activity implements OnClickListener
{
	Button today,yesturday,lastweek,lastmonth;
//	TextView heading;
	String logas,days,alerts;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alerts_new_layout);

		Intent i=getIntent();
 		 alerts=i.getStringExtra("Alerts");
			System.out.println("alerts======="+alerts);
		 Typeface face = Typeface.createFromAsset(getAssets(),
	             "arial.ttf");

	String name=i.getStringExtra("Alerts");

	TextView textView= (TextView) findViewById(R.id.namesetter);
		textView.setText(" "+name);

	today=(Button)findViewById(R.id.alerts_today);
	yesturday=(Button)findViewById(R.id.alerts_yestrday);
	lastweek=(Button) findViewById(R.id.alerts_lastweek);
	lastmonth=(Button)findViewById(R.id.alerts_lastmonth);
	today.setTypeface(face);
	yesturday.setTypeface(face);
	lastweek.setTypeface(face);
	lastmonth.setTypeface(face);
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
			
			 days="Today";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = df.format(c.getTime());
			Intent i=new Intent(this,L1Report.class);
			i.putExtra("date", formattedDate);
			i.putExtra("name",LoginForm.name );
			i.putExtra("days", days);
			startActivity(i);
			Toast.makeText(this, "Wait Till Process", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.alerts_yestrday:
			Calendar c1 = Calendar.getInstance();
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			String date = dft.format(c1.getTime());
			days="Yesturday";
			 c1.add(Calendar.DATE, -1);
			 String ydate=dft.format(c1.getTime());
			Intent in=new Intent(this,L1Report.class);
			in.putExtra("date",ydate );
			in.putExtra("name",LoginForm.name);
			in.putExtra("days", days);
			startActivity(in);
			Toast.makeText(this, "Wait Till Process", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.alerts_lastweek:
			days="Week";
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			String dat = sdf.format(cal.getTime());
			 cal.add(Calendar.DATE, -7);
			 String oneweek=sdf.format(cal.getTime());
			Intent intent=new Intent(this,L1Report.class);
			intent.putExtra("date",oneweek );
			intent.putExtra("name",LoginForm.name);
			intent.putExtra("days",days);
			startActivity(intent);
			Toast.makeText(this, "Wait Till Process", Toast.LENGTH_SHORT).show();
		break;
		case R.id.alerts_lastmonth		:
			days="Month";
			Calendar calendar = Calendar.getInstance();
			
			SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd");
			String dates = sd.format(calendar.getTime());
			calendar.add(Calendar.DATE, -30);
			String onemonth=sd.format(calendar.getTime());
			Intent inn=new Intent(this,L1Report.class);
			inn.putExtra("date",onemonth);
			inn.putExtra("name",LoginForm.name);
			inn.putExtra("days", days);
			startActivity(inn);
			Toast.makeText(this, "Wait Till Process", Toast.LENGTH_SHORT).show();
			break;
		}
	}

}
