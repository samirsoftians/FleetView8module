package com.example.fleetviewandroid;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class L1Report extends Activity
{
	String logas,day,currentdate,addviewText,resText="-",resTextview="-",addview="",onlinetext="",addonline="";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

			Intent i=getIntent();
		 logas=i.getStringExtra("name");
		 day=i.getStringExtra("days");
		currentdate=i.getStringExtra("date");
		
			new MyTaskl1report().execute(logas,currentdate,day);
			new MyTaskl2report().execute(logas,currentdate,day);
			new MyTaskonline().execute(logas,currentdate,day);
	}
	private class MyTaskl1report extends AsyncTask<String, Integer, String>
	{

		@Override
		protected String doInBackground(String... params)
		{
			// TODO Auto-generated method stub
			   String viewl1=postviewData(params);
	           
	           return viewl1;
		}
		 protected void onPostExecute(String responseText)
		 {
			 
			 SetList();
			 	 
		 }
	     public String postviewData(String valueIWantToSend[]) {
	         // Create a new HttpClient and Post Header
	         
	         HttpPost httppost=new HttpPost();        
		     try
		   {
	             // Add your data
	         	
	             ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	            
	             nameValue.add(new BasicNameValuePair("l1user",valueIWantToSend[0]));
	             nameValue.add(new BasicNameValuePair("l1date", valueIWantToSend[1]));
	             nameValue.add(new BasicNameValuePair("l1day", valueIWantToSend[2]));

	          HttpClient httpclient = new DefaultHttpClient();
	        // httppost = new HttpPost("http://10.0.2.2:2020/FleetViewProject/MyServlet");
//	        httppost = new HttpPost("http://192.168.2.26:9090/FleetViewProject/MyServlet");
//	        httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewAndroidProject/MyServlet");
	        httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
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
	 	
	 	 private String inputString(HttpResponse i)
		 {
	 		 
	 		 String data = "";
	 		 StringBuilder tot = new StringBuilder();
	 		 InputStream ins=null;
	 		 try {
	 			 
	 		        ins = i.getEntity().getContent();
	 		     
	 		 // Wrap a BufferedReader around the InputStream
	 		 BufferedReader rd = new BufferedReader(new InputStreamReader(ins));
	 		 // Read response until the end
	 		 try {
	 			 
	 		 while ((data = rd.readLine()) != null) {
	 			 
	 		 tot.append(data);
	 		 }
	 		
	 		 data=tot.toString().trim();
	 		
	 		 } catch (IOException e) {
	 		 e.printStackTrace();
	 		 }
	 		 // Return full string
	 		
	 		 }catch(Exception e)
	 		 {
	 			 
	 		 }
	 		 return data;
	 	 }
		
		}

	private class MyTaskl2report extends AsyncTask<String, Integer, String>
	{

		@Override
		protected String doInBackground(String... params)
		{
			// TODO Auto-generated method stub
			   String viewl1=postview(params);
	           
	           return viewl1;
		}
		
		 protected void onPostExecute(String responseText){
	      	
	      	 SetList();
	      	 
	}
	     public String postview(String valueIWantToSend[]) {
	         // Create a new HttpClient and Post Header
	         
	         HttpPost httppost;        
	         
	         try {
	             // Add your data
	        
	             ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	        
	             nameValue.add(new BasicNameValuePair("l2user",valueIWantToSend[0]));
	             nameValue.add(new BasicNameValuePair("l2date", valueIWantToSend[1]));
	        
	             nameValue.add(new BasicNameValuePair("l2day", valueIWantToSend[2]));
	           
	          HttpClient httpclient = new DefaultHttpClient();
	     //  httppost = new HttpPost("http://10.0.2.2:2020/FleetViewProject/MyServlet");
	      httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
//	      httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
//	      httppost = new HttpPost("http://192.168.2.26:9090/FleetViewProject/MyServlet");
	           //httppost = new HttpPost("http://203.199.134.131:8080/Second/MyServlet");
	            // httppost = new HttpPost("http://203.199.134.131:8080/First/Hello");
	              //httppost = new HttpPost("http://192.168.2.73:8080/First/Hello");
	        
	            httppost.setEntity(new UrlEncodedFormEntity(nameValue));
	      
	        
	             HttpResponse resp= httpclient.execute(httppost);
	        
	             addview=inputString(resp);
	         
	          Log.d("response", addview);
	         } 
	   catch (ClientProtocolException e) {
	             // TODO Auto-generated catch block
	         } 
	   catch (IOException e) {
	             // TODO Auto-generated catch block
	         }
	        resTextview =  addview.toString();
	     
	       return resTextview;
	     }
	 	
	 	 private String inputString(HttpResponse i) {
	 		
	 		 String data = "";
	 		 StringBuilder tot = new StringBuilder();
	 		 InputStream ins=null;
	 		 try {
	 		
	 		        ins = i.getEntity().getContent();
	 		
	 		 // Wrap a BufferedReader around the InputStream
	 		 BufferedReader rd = new BufferedReader(new InputStreamReader(ins));
	 		 // Read response until the end
	 		 try {
	 		
	 		 while ((data = rd.readLine()) != null) {
	 		
	 		 tot.append(data);
	 		 }
	 		
	 		 data=tot.toString().trim();
	 			 		 } catch (IOException e) {
	 		 e.printStackTrace();
	 		 }
	 		 // Return full string
	 		
	 		 }catch(Exception e)
	 		 {
	 			 
	 		 }
	 		 return data;
	 	 }
		
		}

	private class MyTaskonline extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			   String viewl1=postdata(params);
	           
	           return viewl1;
		}
		
		 protected void onPostExecute(String responseText){
	      	System.out.println("onPostExecute"+responseText);
	      	if(responseText.equals("0"))
			 {
				 runOnUiThread(new Runnable() {
					 public void run()
					 {
						 
						 
			        	  Toast.makeText(getApplicationContext(), "Data Not Available Try Again Later", Toast.LENGTH_LONG).show();
					     }
					 });
	          } 
				      	 SetList();
	      	 
	}
	     public String postdata(String valueIWantToSend[]) {
	         // Create a new HttpClient and Post Header
	         
	         HttpPost httppost;        
	         
	         try {
	             // Add your data
	      
	             ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	      
	             nameValue.add(new BasicNameValuePair("online",valueIWantToSend[0]));
	             nameValue.add(new BasicNameValuePair("ondate", valueIWantToSend[1]));
	      
	             nameValue.add(new BasicNameValuePair("onday", valueIWantToSend[2]));
	           
	          HttpClient httpclient = new DefaultHttpClient();
	       // httppost = new HttpPost("http://10.0.2.2:2020/FleetViewProject/MyServlet");
//	         httppost = new HttpPost("http://192.168.2.26:9090/FleetViewProject/MyServlet");
//	         httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewAndroidProject/MyServlet");
	         httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
	           //httppost = new HttpPost("http://203.199.134.131:8080/Second/MyServlet");
	            // httppost = new HttpPost("http://203.199.134.131:8080/First/Hello");
	              //httppost = new HttpPost("http://192.168.2.73:8080/First/Hello");
	      
	            httppost.setEntity(new UrlEncodedFormEntity(nameValue));
	      
	             HttpResponse resp= httpclient.execute(httppost);
	         
	             addonline=inputString(resp);
	         
	          Log.d("response", addonline);
	         } 
	   catch (ClientProtocolException e) {
	             // TODO Auto-generated catch block
	         } 
	   catch (IOException e) {
	             // TODO Auto-generated catch block
	         }
	        onlinetext =  addonline.toString();
	     
	       return onlinetext;
	     }
	 	
	 	 private String inputString(HttpResponse i) {
	 		 
	 		 String data = "";
	 		 StringBuilder tot = new StringBuilder();
	 		 InputStream ins=null;
	 		 try {
	 			
	 		        ins = i.getEntity().getContent();
	 		    
	 		 // Wrap a BufferedReader around the InputStream
	 		 BufferedReader rd = new BufferedReader(new InputStreamReader(ins));
	 		 // Read response until the end
	 		 try {
	 			
	 		 while ((data = rd.readLine()) != null) {
	 			
	 		 tot.append(data);
	 		 }

	 		 data=tot.toString().trim();

	 		 } catch (IOException e) {
	 		 e.printStackTrace();
	 		 }
	 		 // Return full string
	 		
	 		 }catch(Exception e)
	 		 {
	 			 
	 		 }
	 		 return data;
	 	 }
		
		}
	
	
	public void SetList()
	{
		 Typeface face = Typeface.createFromAsset(getAssets(),
	             "arial.ttf");
		
			 ScrollView sv = new ScrollView(L1Report.this);
			 sv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			sv.setBackgroundColor(0xF000000);
				//sv.setBackgroundResource(R.drawable.black);

		            LinearLayout ll = new LinearLayout(L1Report.this);
		            ll.setOrientation(LinearLayout.VERTICAL);
		           // ll.setBackgroundColor(0xFFded4e0);
			           // ll.setBackgroundColor(0xFFded4e0);

		            ll.setPadding(5, 10, 0, 0);
		            sv.addView(ll);
		            TextView heading = new TextView(L1Report.this);
		            heading.setText("L1-L2 Alerts");
		            heading.setTextColor(Color.parseColor("#000000"));
		            heading.setGravity(Gravity.CENTER_HORIZONTAL);
		            heading.setTypeface(face);
		            heading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		            
		            ll.addView(heading);
		            
	
				            			
			            // Create TextView
			           
			            final TextView name = new TextView(L1Report.this);
			            name.setTypeface(face);
			            name.setTextColor(Color.parseColor("#000000"));
			            name.setText(" L1  : "+"  "+resText);
			            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			            name.setPadding(5, 20, 0, 0);
			            ll.addView(name);
			            
			            //Ruler
			            View ruler = new View(L1Report.this);
			            ruler.setBackgroundColor(0xFFb7c3c2); // line color
			            ll.addView(ruler,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
			            L1Report.this.setContentView(sv);   
			            	
		            	
			        final TextView alert = new TextView(L1Report.this);
			        alert.setTypeface(face);
			        alert.setTextColor(Color.parseColor("#000000"));
		            alert.setText(" L2  : "+"  "+resTextview);
		            alert.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		            alert.setPadding(5, 20, 0, 0);
		            ll.addView(alert);
		                       
		            //Ruler
		            View ruler1 = new View(L1Report.this);
		            ruler1.setBackgroundColor(0xFFb7c3c2); // line color
		            ll.addView(ruler1,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
		            L1Report.this.setContentView(sv);   
		        
		            
		            final TextView online= new TextView(L1Report.this);
		            online.setTypeface(face);
		            online.setTextColor(Color.parseColor("#000000"));
		            online.setText(" Online  : "+"  "+onlinetext);
		            online.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		            online.setPadding(5, 20, 0, 0);
		            ll.addView(online);
		                       
		            //Ruler
		            View ruler2 = new View(L1Report.this);
		            ruler2.setBackgroundColor(0xFFb7c3c2); // line color
		            ll.addView(ruler2,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
		            L1Report.this.setContentView(sv);   
		        
		            name.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							int j=0;
							String s=name.getText().toString().trim();
						
							String[] data=s.split(":");
						
							String l1=data[j];
							System.out.println("sssssssssss"+l1);
							if(day.equals("Today"))
							{
								Intent i=new Intent(L1Report.this,ViewL1Report.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("status", l1);
								startActivity(i);
								
							}
							if(day.equals("Yesturday"))
							{
								Intent i=new Intent(L1Report.this,ViewL1Report.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("status", l1);
								startActivity(i);
								
							}
							if(day.equals("Week"))
							{
								System.out.println("L1 Alerts++++++++++++++++++++++++++++++++++++++++++");
								Intent i=new Intent(L1Report.this,ViewL1Report.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("status", l1);
								startActivity(i);
							}
							if(day.equals("Month"))
							{
								Intent i=new Intent(L1Report.this,ViewL1Report.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("status", l1);
								startActivity(i);
							}
				
							
						}
					});

		            alert.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							
							
							int j=0;
							String s=alert.getText().toString().trim();
							
							String[] data=s.split(":");
						
							String l2=data[j];
							System.out.println("dddddddddddddddddddddd"+l2);

							if(day.equals("Today"))
							{
								Intent i=new Intent(L1Report.this,ViewL1Report.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("status", l2);
								startActivity(i);
								
							}
							if(day.equals("Yesturday"))
							{
								Intent i=new Intent(L1Report.this,ViewL1Report.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("status", l2);
								startActivity(i);
								
							}
							if(day.equals("Week"))
							{
								Intent i=new Intent(L1Report.this,ViewL1Report.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("status", l2);
								startActivity(i);
							}
							if(day.equals("Month"))
							{
								Intent i=new Intent(L1Report.this,ViewL1Report.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("status", l2);
								startActivity(i);
							}
							
							
							
						}
					});
		            
		            online.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
	
							int j=0;
					
						
							String onlin="-";
							
							if(day.equals("Today"))
							{
								Intent i=new Intent(L1Report.this,ViewL1Report.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("status", onlin);
								startActivity(i);
								
							}
							if(day.equals("Yesturday"))
							{
								Intent i=new Intent(L1Report.this,ViewL1Report.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("status", onlin);
								startActivity(i);
								
							}
							if(day.equals("Week"))
							{
								Intent i=new Intent(L1Report.this,ViewL1Report.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("status", onlin);
								startActivity(i);
							}
							if(day.equals("Month"))
							{
								Intent i=new Intent(L1Report.this,ViewL1Report.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("status", onlin);
								startActivity(i);
							}						
						}
						
					});
}
	
}