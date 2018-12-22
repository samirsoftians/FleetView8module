package com.example.fleetviewandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class TodayViolationdetails extends Activity
{
String date,name,day,selected,os,rd,addviewText="",resText="",fromdate,fromtime,todate,totime,speed,duration;
ArrayList<String> fromdetails=new ArrayList<String>();
ArrayList<String> fromtmdetails=new ArrayList<String>();
ArrayList<String> todatedetails=new ArrayList<String>();
ArrayList<String> totmdetails=new ArrayList<String>();
ArrayList<String> speeddetails=new ArrayList<String>();
ArrayList<String> durationdetails=new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.violationdetails);
		
		
		Intent i=getIntent();
		date=i.getStringExtra("date");
		name=i.getStringExtra("name");
		day=i.getStringExtra("day");
		selected=i.getStringExtra("ra");

		new MyTaskos().execute(name,date,day,selected);
		
				
	}
	
	private class MyTaskos extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			   String viewl1=postviewData(params);
	           return viewl1;
		}
		
		 protected void onPostExecute(String responseText){
	      	
	      	viewreport();
	     	SetListOSCD();
	      	 
	}
	     public String postviewData(String valueIWantToSend[]) {
	         // Create a new HttpClient and Post Header
	         
	         HttpPost httppost;        
	         
	         try {
	             // Add your data
	      
	             ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	      
	             nameValue.add(new BasicNameValuePair("logedos",valueIWantToSend[0]));
	             nameValue.add(new BasicNameValuePair("dateos", valueIWantToSend[1]));
	      
	             nameValue.add(new BasicNameValuePair("daysos", valueIWantToSend[2]));
	             nameValue.add(new BasicNameValuePair("selected", valueIWantToSend[3]));

	          HttpClient httpclient = new DefaultHttpClient();
	         //httppost = new HttpPost("http://10.0.2.2:2020/FleetViewProject/MyServlet");
	       httppost = new HttpPost("http://103.8.126.138:8080/FleetViewProject/MyServlet");
	           //httppost = new HttpPost("http://203.199.134.131:8080/Second/MyServlet");
	            // httppost = new HttpPost("http://203.199.134.131:8080/First/Hello");
	              //httppost = new HttpPost("http://192.168.2.73:8080/First/Hello");
	        
	            httppost.setEntity(new UrlEncodedFormEntity(nameValue));
	             HttpResponse resp= httpclient.execute(httppost);
	        
	             addviewText=inputString(resp);
	         
	          Log.d("response", addviewText);
	         } 
	   catch (ClientProtocolException e) {
	             // TODO Auto-generated catch block
	         } 
	   catch (IOException e) {
	             // TODO Auto-generated catch block
	         }
	        resText =  addviewText.toString();
	     
	       return resText;
	     }
	 	
	 	 private String inputString(HttpResponse i) {
	 		
	 		 String d = "";
	 		 StringBuilder tot = new StringBuilder();
	 		 InputStream ins=null;
	 		 try {
	 		
	 		        ins = i.getEntity().getContent();
	 		
	 		 // Wrap a BufferedReader around the InputStream
	 		 BufferedReader rd = new BufferedReader(new InputStreamReader(ins));
	 		 // Read response until the end
	 		 try {
	 		
	 		 while ((d = rd.readLine()) != null) {
	 		
	 		 tot.append(d);
	 		 }
	 		
	 		 d=tot.toString().trim();
	 		 } catch (IOException e) {
	 		 e.printStackTrace();
	 		 }
	 		 // Return full string
	 		
	 		 }catch(Exception e)
	 		 {
	 			 
	 		 }
	 		 return d;
	 	 }

	}
	

public void viewreport()
{
if(resText!=null)
	{
	ArrayList<Comment> arraylist=parseJson(resText);
	Iterator iter=arraylist.iterator();	
	while(iter.hasNext())
	{
		
		
		System.out.println("10");
		Comment data=(Comment)iter.next();
		System.out.println("11");
		fromdate=data.getFromdate();
		fromtime=data.getFromtime();
		todate=data.getTodate();
		totime=data.getTotime();
		speed=data.getSpeed();
		duration=data.getDuration();
		
		  SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
	       
	        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
	      String formatdate,todat;
		
			try {
				formatdate = sdf2.format(sdf1.parse(fromdate));
				todat= sdf2.format(sdf1.parse(todate));
				

				fromdetails.add(formatdate);
				fromtmdetails.add(fromtime);
				todatedetails.add(todat);
				totmdetails.add(totime);
				speeddetails.add(speed);
				durationdetails.add(duration);
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

	}
	
	
	}

}

public ArrayList<Comment> parseJson(String result) {
ArrayList<Comment> mainObj = new ArrayList<Comment>();
try {
	JSONArray mainMEnu = new JSONArray(result);
	for (int i = 0; i < mainMEnu.length(); i++) {
		JSONObject jsonObj = mainMEnu.getJSONObject(i);
		Comment menu = new Comment();
		menu.setFromdate(jsonObj.getString("FromDate"));
		menu.setFromtime(jsonObj.getString("FromTime"));
		menu.setTodate(jsonObj.getString("ToDate"));
		menu.setTotime(jsonObj.getString("ToTime"));
		menu.setSpeed(jsonObj.getString("Speed"));
		menu.setDuration(jsonObj.getString("Duration"));

		mainObj.add(menu);
		
	}
	

} catch (Exception e) {

}
return mainObj;
}
	
	public void SetListOSCD()
	{
		 Typeface face = Typeface.createFromAsset(getAssets(),
	             "arial.ttf");
			 ScrollView sv = new ScrollView(TodayViolationdetails.this);
			 sv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			sv.setBackgroundColor(0xFFded4e0);
		
		            LinearLayout ll = new LinearLayout(TodayViolationdetails.this);
		            ll.setOrientation(LinearLayout.VERTICAL);
		            ll.setBackgroundColor(0xFFded4e0);
		            ll.setPadding(5, 10, 0, 0);
		            sv.addView(ll);
		            TextView heading = new TextView(TodayViolationdetails.this);
		            heading.setText(selected+"Report");
		            heading.setGravity(Gravity.CENTER_HORIZONTAL);
		            heading.setTypeface(face);
		            //heading.setTextColor(0Xffffff);
		            heading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		            
		            ll.addView(heading);
		            
		            /*//Ruler
		            View ruler1 = new View(AppointmentDetails.this);
		            ruler1.setBackgroundColor(0xFF00FF00); // line color
		            ll.addView(ruler1,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, 2));
		            */
		            for(int i=0;i<fromdetails.size();i++)
			        {
			
			            // Create TextView
			           
			            final TextView FromDate = new TextView(TodayViolationdetails.this);
			            FromDate.setTypeface(face);
			            FromDate.setText(" From Date Time:"+"  "+fromdetails.get(i)+"  "+fromtmdetails.get(i));
			            FromDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			            FromDate.setPadding(5, 20, 0, 0);
			          //  FromDate.setTextColor(0Xffffff);

			            ll.addView(FromDate);
			            
			            //Ruler
			            View ruler = new View(TodayViolationdetails.this);
			            ruler.setBackgroundColor(0xFFb7c3c2); // line color
			            ll.addView(ruler,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
			            TodayViolationdetails.this.setContentView(sv);   
			            	
		            	
//			        TextView ToDate = new TextView(TodayViolationdetails.this);
//			        ToDate.setTypeface(face);
//			        ToDate.setText(" FromTime            : "+"  "+fromtmdetails.get(i));
//			        ToDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//			        ToDate.setPadding(5, 20, 0, 0);
//		            ll.addView(ToDate);
//		            
//		            
//		            //Ruler
//		            View ruler1 = new View(TodayViolationdetails.this);
//		            ruler1.setBackgroundColor(0xFFb7c3c2); // line color
//		            ll.addView(ruler1,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
//		            TodayViolationdetails.this.setContentView(sv);   
//		           
		            
		            
		             TextView time = new TextView(TodayViolationdetails.this);
		             time.setTypeface(face);
		            time.setText(" To Date Time    : "+"  "+todatedetails.get(i)+"  "+totmdetails.get(i));
		            time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		            time.setPadding(5, 20, 0, 0);
		            //time.setTextColor(0Xffffff);

		            ll.addView(time);
		           
		            
		            
		            //Ruler
		            View ruler2 = new View(TodayViolationdetails.this);
		            ruler2.setBackgroundColor(0xFFb7c3c2); // line color
		            ll.addView(ruler2,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
		            TodayViolationdetails.this.setContentView(sv);   
		           
	            
//		            TextView Speed = new TextView(TodayViolationdetails.this);
//		            Speed.setTypeface(face);
//		            Speed.setText(" ToTime               : "+"  "+totmdetails.get(i));
//		            Speed.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//		            Speed.setPadding(5, 20, 0, 0);
//		            ll.addView(Speed);
//		            
//		            //Ruler
//		            View ruler3 = new View(TodayViolationdetails.this);
//		            ruler3.setBackgroundColor(0xFFb7c3c2); // line color
//		            ll.addView(ruler3,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
//		            TodayViolationdetails.this.setContentView(sv);   
		           
		            TextView Duration = new TextView(TodayViolationdetails.this);
		            Duration.setTypeface(face);
		          //  Duration.setTextColor(0Xffffff);
		            Duration.setText(" Speed              : "+"  "+speeddetails.get(i));
		            Duration.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		            Duration.setPadding(5, 20, 0, 0);
		            ll.addView(Duration);
		            
		            //Ruler
		            View ruler4 = new View(TodayViolationdetails.this);
		            ruler4.setBackgroundColor(0xFFb7c3c2); // line color
		            ll.addView(ruler4,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
		            TodayViolationdetails.this.setContentView(sv);   

						TextView dur = new TextView(TodayViolationdetails.this);
						dur.setTypeface(face);
			            //dur.setTextColor(0Xffffff);
						dur.setText(" Duration           : "+"  "+durationdetails.get(i));
						dur.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
						dur.setPadding(5, 20, 0, 0);
						ll.addView(dur);
    
						//Ruler
						View ruler5 = new View(TodayViolationdetails.this);
						ruler5.setBackgroundColor(0xFF000000); // line color
						ll.addView(ruler5,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
						TodayViolationdetails.this.setContentView(sv);
			        }
}		
}
