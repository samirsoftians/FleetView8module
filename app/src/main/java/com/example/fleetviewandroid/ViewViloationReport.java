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

public class ViewViloationReport extends Activity
{
	String  addviewText,resText="-",addviewTextrd,resTextrd="-",addviewTextcd,resTextcd="-",addviewTextos,resTextos="-";
	String ra,os,rd,cd,nd;
	 String data = "",datard="",dataos="",datacd="",datand="",   resTextnd ="-",addviewTextnd="",currentdate,day, logas;
	ArrayList<String> radetails=new ArrayList<String>();
	ArrayList<String> osdetails=new ArrayList<String>();
	ArrayList<String> rddetail=new ArrayList<String>();
	ArrayList<String> cddetails=new ArrayList<String>();
	ArrayList<String> nddetails=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

			Intent i=getIntent();
		 logas=i.getStringExtra("logos");
		 day=i.getStringExtra("days");
		currentdate=i.getStringExtra("date");
		System.out.println("currentdate"+currentdate+logas);
			new MyTaskra().execute(logas,currentdate,day);
			new MyTaskrd().execute(logas,currentdate,day);
			new MyTaskos().execute(logas,currentdate,day);
			new MyTaskcd().execute(logas,currentdate,day);
			new MyTasknd().execute(logas,currentdate,day);
			
	}
	private class MyTaskra extends AsyncTask<String, Integer, String>
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
         
         HttpPost httppost;        
         
         try
	   {
             // Add your data
             ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
             nameValue.add(new BasicNameValuePair("logra",valueIWantToSend[0]));
             nameValue.add(new BasicNameValuePair("date", valueIWantToSend[1]));
             nameValue.add(new BasicNameValuePair("day", valueIWantToSend[2]));

          HttpClient httpclient = new DefaultHttpClient();
//         httppost = new HttpPost("http://10.0.2.2:2020/FleetViewProject/MyServlet");
//         httppost = new HttpPost("http://192.168.2.26:9090/FleetViewProject/MyServlet");
         httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
//         httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewAndroidProject/MyServlet");
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
	private class MyTaskrd extends AsyncTask<String, Integer, String>{

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		   String viewrd=postviewDatard(params);
           return viewrd;
	}
	
	 protected void onPostExecute(String responseText){
      	//viewreport();
      	 SetList();
      
}
	 
	
     public String postviewDatard(String valueIWantToSend[]) {
         // Create a new HttpClient and Post Header
         
         HttpPost httppost;        
         
         try {
             // Add your data
             ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
             nameValue.add(new BasicNameValuePair("logrd",valueIWantToSend[0]));
             nameValue.add(new BasicNameValuePair("date", valueIWantToSend[1]));
             nameValue.add(new BasicNameValuePair("day", valueIWantToSend[2]));
             

             HttpClient httpclient = new DefaultHttpClient();
            // httppost = new HttpPost("http://10.0.2.2:2020/FleetViewProject/MyServlet");

//          httppost = new HttpPost("http://192.168.2.26:9090/FleetViewProject/MyServlet");
          httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
//          httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewAndroidProject/MyServlet");
         //httppost = new HttpPost("http://203.199.134.131:8080/FleetViewProject/MyServlet");
           //httppost = new HttpPost("http://203.199.134.131:8080/Second/MyServlet");
            // httppost = new HttpPost("http://203.199.134.131:8080/First/Hello");
              //httppost = new HttpPost("http://192.168.2.73:8080/First/Hello");
            httppost.setEntity(new UrlEncodedFormEntity(nameValue));
      
             HttpResponse resp= httpclient.execute(httppost);
//             HttpEntity httpentity=response.getEntity();
//             System.out.println("httpentity==================="+httpentity);
//             InputStream is=httpentity.getContent();
//             System.out.println("is==================="+is);
//      
             addviewTextrd=inputStringdatard(resp);
         
          Log.d("response", addviewTextrd);
         } 
   catch (ClientProtocolException e) {
             // TODO Auto-generated catch block
         } 
   catch (IOException e) {
             // TODO Auto-generated catch block
         }
        resTextrd =  addviewTextrd.toString();
     
  
       return resTextrd;
       
     }
 	
 	 private String inputStringdatard(HttpResponse i)
	 {
 		 String datard = "";
 		 StringBuilder tot = new StringBuilder();
 		 InputStream ins=null;
 		 try {
 			
 		        ins = i.getEntity().getContent();
 		 // Wrap a BufferedReader around the InputStream
 		 BufferedReader rd = new BufferedReader(new InputStreamReader(ins));
 		 // Read response until the end
 		 try {
 		 while ((datard = rd.readLine()) != null) {
 		 tot.append(datard);
 		 }
 		 datard=tot.toString().trim();
 			

 		 } catch (IOException e) {
 		 e.printStackTrace();
 		 }
 		 // Return full string
 		
 		 }catch(Exception e)
 		 {
 			 
 		 }
 		 return datard;
 	 }
	
	}
	
	private class MyTaskos extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			   String viewos=postviewDataos(params);
	           return viewos;
		}
		
		 protected void onPostExecute(String responseText){
	      	 SetList();
	      
	}
		 
		
	     public String postviewDataos(String valueIWantToSend[]) {
	         // Create a new HttpClient and Post Header
	         
	         HttpPost httppost;        
	         
	         try {
	             // Add your data
	             ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	             nameValue.add(new BasicNameValuePair("logos",valueIWantToSend[0]));
	             nameValue.add(new BasicNameValuePair("date", valueIWantToSend[1]));
	             nameValue.add(new BasicNameValuePair("day", valueIWantToSend[2]));
	            
	             HttpClient httpclient = new DefaultHttpClient();
	            // httppost = new HttpPost("http://10.0.2.2:2020/FleetViewProject/MyServlet");

//	         httppost = new HttpPost("http://192.168.2.26:9090/FleetViewProject/MyServlet");
	         httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
//	         httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewAndroidProject/MyServlet");
	       //  httppost = new HttpPost("http://203.199.134.131:8080/FleetViewProject/MyServlet");
	           //httppost = new HttpPost("http://203.199.134.131:8080/Second/MyServlet");
	            // httppost = new HttpPost("http://203.199.134.131:8080/First/Hello");
	              //httppost = new HttpPost("http://192.168.2.73:8080/First/Hello");
	            
	            httppost.setEntity(new UrlEncodedFormEntity(nameValue));
	      
	            
	             HttpResponse resp= httpclient.execute(httppost);
//	             HttpEntity httpentity=response.getEntity();
//	             System.out.println("httpentity==================="+httpentity);
//	             InputStream is=httpentity.getContent();
//	             System.out.println("is==================="+is);
//	      
	             addviewTextos=inputStringdataos(resp);
	         
	          Log.d("response", addviewTextos);
	         } 
	   catch (ClientProtocolException e) {
	             // TODO Auto-generated catch block
	         } 
	   catch (IOException e) {
	             // TODO Auto-generated catch block
	         }
	        resTextos =  addviewTextos.toString();
	     
	  
	       return resTextos;
	       
	     }
	 	
	 	 private String inputStringdataos(HttpResponse i) {
	 		 String dataos = "";
	 		 StringBuilder tot = new StringBuilder();
	 		 InputStream ins=null;
	 		 try {
	 		        ins = i.getEntity().getContent();
	 		 // Wrap a BufferedReader around the InputStream
	 		 BufferedReader rd = new BufferedReader(new InputStreamReader(ins));
	 		 // Read response until the end
	 		 try {
	 		 while ((dataos = rd.readLine()) != null) {
	 			 System.out.println("in whileloop222"+  dataos);
	 		 tot.append(dataos);
	 		 }
	 		 dataos=tot.toString().trim();
	 			

	 		 } catch (IOException e) {
	 		 e.printStackTrace();
	 		 }
	 		 // Return full string
	 		
	 		 }catch(Exception e)
	 		 {
	 			 
	 		 }
	 		 return dataos;
	 	 }
		
		}
	
	
	private class MyTaskcd extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			   String viewcd=postviewDatacd(params);
	           return viewcd;
		}
		
		 protected void onPostExecute(String responseText){
	      	 SetList();
	      
	}
		 
		
	     public String postviewDatacd(String valueIWantToSend[]) {
	         // Create a new HttpClient and Post Header
	         
	         HttpPost httppost;        
	         
	         try {
	             // Add your data
	             ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	             nameValue.add(new BasicNameValuePair("logcd",valueIWantToSend[0]));
	             nameValue.add(new BasicNameValuePair("date", valueIWantToSend[1]));
	             nameValue.add(new BasicNameValuePair("day", valueIWantToSend[2]));
	             
	            
	           
	             HttpClient httpclient = new DefaultHttpClient();
	          //   httppost = new HttpPost("http://10.0.2.2:2020/FleetViewProject/MyServlet");

//	        httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
	        httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
//	        httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewAndroidProject/MyServlet");
//	        httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewAndroidProject/MyServlet");
//	        httppost = ne`w HttpPost("http://MyFleetView.com:8181/FleetViewAndroidProject/MyServlet");
//	        httppost = new HttpPost("http://192.168.2.26:9090/FleetViewProject/MyServlet");
	        // httppost = new HttpPost("http://203.199.134.131:8080/FleetViewProject/MyServlet");
	           //httppost = new HttpPost("http://203.199.134.131:8080/Second/MyServlet");
	            // httppost = new HttpPost("http://203.199.134.131:8080/First/Hello");
	              //httppost = new HttpPost("http://192.168.2.73:8080/First/Hello");
	            
	            httppost.setEntity(new UrlEncodedFormEntity(nameValue));
	      
	             HttpResponse resp= httpclient.execute(httppost);
	      
//	             HttpEntity httpentity=response.getEntity();
//	             System.out.println("httpentity==================="+httpentity);
//	             InputStream is=httpentity.getContent();
//	             System.out.println("is==================="+is);
//	      
	             addviewTextcd=inputStringdatacd(resp);
	         
	          Log.d("response", addviewTextcd);
	         } 
	   catch (ClientProtocolException e) {
	             // TODO Auto-generated catch block
	         } 
	   catch (IOException e) {
	             // TODO Auto-generated catch block
	         }
	        resTextcd =  addviewTextcd.toString();
	     
	  
	       return resTextcd;
	       
	     }
	 	
	 	 private String inputStringdatacd(HttpResponse i) {
	 		
	 		 String datacd = "";
	 		 StringBuilder tot = new StringBuilder();
	 		 InputStream ins=null;
	 		 try {
	 		
	 		        ins = i.getEntity().getContent();
	 		       
	 		 // Wrap a BufferedReader around the InputStream
	 		 BufferedReader rd = new BufferedReader(new InputStreamReader(ins));
	 		 // Read response until the end
	 		 try {
	 			
	 		 while ((datacd = rd.readLine()) != null) {
	 			
	 		 tot.append(datacd);
	 		 }
	 		 
	 		 datacd=tot.toString().trim();
	 		 	

	 		 } catch (IOException e) {
	 		 e.printStackTrace();
	 		 }
	 		 // Return full string
	 		
	 		 }catch(Exception e)
	 		 {
	 			 
	 		 }
	 		 return datacd;
	 	 }
		
		}	

	private class MyTasknd extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			   String viewnd=postviewDatand(params);
	         
	           return viewnd;
		}
		
		 protected void onPostExecute(String responseText){
			 if(responseText.equals("0"))
	          {
				 runOnUiThread(new Runnable() {
					 public void run() {
						 
			        	  Toast.makeText(getApplicationContext(), "Data Not Available Try Again Later", Toast.LENGTH_LONG).show();
					     }
					 });
	          }
	      	 SetList();
	      
	}
		 
		
	     public String postviewDatand(String valueIWantToSend[]) {
	         // Create a new HttpClient and Post Header
	         
	         HttpPost httppost;        
	         
	         try {
	             // Add your data
	      
	             ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	      
	             nameValue.add(new BasicNameValuePair("lognd",valueIWantToSend[0]));
	             nameValue.add(new BasicNameValuePair("date", valueIWantToSend[1]));
	             nameValue.add(new BasicNameValuePair("day", valueIWantToSend[2]));
	             
	           
	             HttpClient httpclient = new DefaultHttpClient();
	            // httppost = new HttpPost("http://10.0.2.2:2020/FleetViewProject/MyServlet");

//	          httppost = new HttpPost("http://192.168.2.26:9090/FleetViewProject/MyServlet");
	          httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
//	          httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewAndroidProject/MyServlet");
	        // httppost = new HttpPost("http://203.199.134.131:8080/FleetViewProject/MyServlet");
	           //httppost = new HttpPost("http://203.199.134.131:8080/Second/MyServlet");
	            // httppost = new HttpPost("http://203.199.134.131:8080/First/Hello");
	              //httppost = new HttpPost("http://192.168.2.73:8080/First/Hello");
	      
	            httppost.setEntity(new UrlEncodedFormEntity(nameValue));
	      
	             HttpResponse resp= httpclient.execute(httppost);
	      
//	             HttpEntity httpentity=response.getEntity();
//	             System.out.println("httpentity==================="+httpentity);
//	             InputStream is=httpentity.getContent();
//	             System.out.println("is==================="+is);
//	      
	             addviewTextnd=inputStringdatand(resp);
	         
	          Log.d("response", addviewTextnd);
	         } 
	   catch (ClientProtocolException e) {
	             // TODO Auto-generated catch block
	         } 
	   catch (IOException e) {
	             // TODO Auto-generated catch block
	         }
	        resTextnd =  addviewTextnd.toString();
	       return resTextnd;
	       
	     }
	 	
	 	 private String inputStringdatand(HttpResponse i) {
	 		
	 		 String datand = "";
	 		 StringBuilder tot = new StringBuilder();
	 		 InputStream ins=null;
	 		 try {
	 		
	 		        ins = i.getEntity().getContent();
	 		 
	 		 // Wrap a BufferedReader around the InputStream
	 		 BufferedReader rd = new BufferedReader(new InputStreamReader(ins));
	 		 // Read response until the end
	 		 try {
	 		
	 		 while ((datand = rd.readLine()) != null) {
	 		
	 		 tot.append(datand);
	 		 }
	 		
	 		 datand=tot.toString().trim();
	 		 } catch (IOException e) {
	 		 e.printStackTrace();
	 		 }
	 		 // Return full string
	 		
	 		 }catch(Exception e)
	 		 {
	 			 
	 		 }
	 		 return datand;
	 	 }		
		}	

	public void SetList()
	{
		 Typeface face = Typeface.createFromAsset(getAssets(),
	             "arial.ttf");
		
			 ScrollView sv = new ScrollView(ViewViloationReport.this);
			 sv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			sv.setBackgroundColor(0xF000000);
			//sv.setBackgroundResource(R.drawable.black);
		            LinearLayout ll = new LinearLayout(ViewViloationReport.this);
		           // ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		            ll.setOrientation(LinearLayout.VERTICAL);
		           // ll.setBackgroundColor(0xFFded4e0);
		            //ll.setBackgroundResource(R.drawable.bgimag);
		            ll.setPadding(5, 10, 0, 0);
		            sv.addView(ll);
		            TextView heading = new TextView(ViewViloationReport.this);
		            heading.setText("Violations");
		            heading.setTextColor(Color.parseColor("#000000"));

		            heading.setGravity(Gravity.CENTER_HORIZONTAL);
		            heading.setTypeface(face);
		            heading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		            
		            ll.addView(heading);
		            
		            /*//Ruler
		            View ruler1 = new View(AppointmentDetails.this);
		            ruler1.setBackgroundColor(0xFF00FF00); // line color
		            ll.addView(ruler1,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, 2));
		            */
//		            for(int i=0;i<radetails.size();i++)
//			        {
//		            	for(int j=0;j<osdetails.size();j++)
//		            	{
//		            		for(mint k=0;k<rddetail.size();k++)
//		            		{
//
//			            		for(int l=0;l<cddetails.size();l++)
//			            		{
//			            				
//
//				            		for(int m=0;m<nddetails.size();m++)
//				            		{
				            			
			            // Create TextView
			           
			            final TextView name = new TextView(ViewViloationReport.this);
			            name.setTypeface(face);
			           name.setTextColor(Color.parseColor("#000000"));
			           name.setText(" RA : "+"  "+resText);
			            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			            name.setPadding(5, 20, 0, 0);
			            ll.addView(name);
			            
			            //Ruler
			            View ruler = new View(ViewViloationReport.this);
			            ruler.setBackgroundColor(0xFFb7c3c2); // line color
			            ll.addView(ruler,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
			            ViewViloationReport.this.setContentView(sv);   
			            	
		            	
			        final TextView alert = new TextView(ViewViloationReport.this);
			        alert.setTypeface(face);
		            alert.setTextColor(Color.parseColor("#000000"));
		            alert.setText(" OS : "+"  "+resTextos);
		            alert.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		            alert.setPadding(5, 20, 0, 0);
		            ll.addView(alert);
		                       
		            //Ruler
		            View ruler1 = new View(ViewViloationReport.this);
		            ruler1.setBackgroundColor(0xFFb7c3c2); // line color
		            ll.addView(ruler1,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
		            ViewViloationReport.this.setContentView(sv);   
		            
		             final TextView time = new TextView(ViewViloationReport.this);
		             time.setTypeface(face);
			         time.setTextColor(Color.parseColor("#000000"));
		            time.setText(" RD : "+"  "+resTextrd);
		            time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		            time.setPadding(5, 20, 0, 0);
		            ll.addView(time);
		            
		            //Ruler
		            View ruler2 = new View(ViewViloationReport.this);
		            ruler2.setBackgroundColor(0xFFb7c3c2); // line color
		            ll.addView(ruler2,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
		            ViewViloationReport.this.setContentView(sv);   
		           
	            
		            final TextView cd = new TextView(ViewViloationReport.this);
		            cd.setTypeface(face);
		            cd.setTextColor(Color.parseColor("#000000"));
		            cd.setText(" CD : "+"  "+resTextcd);
		            cd.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		            cd.setPadding(5, 20, 0, 0);
		            ll.addView(cd);
		            
		            //Ruler
		            View ruler3 = new View(ViewViloationReport.this);
		            ruler3.setBackgroundColor(0xFFb7c3c2); // line color
		            ll.addView(ruler3,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
		            ViewViloationReport.this.setContentView(sv);   
		           
		            final TextView nd = new TextView(ViewViloationReport.this);
		            nd.setTextColor(Color.parseColor("#000000"));
		            nd.setTypeface(face);
		            nd.setText(" ND : "+"  "+resTextnd);
		            nd.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		            nd.setPadding(5, 20, 0, 0);
		            ll.addView(nd);
		            
		            //Ruler
		            View ruler4 = new View(ViewViloationReport.this);
		            ruler4.setBackgroundColor(0xFFb7c3c2); // line color
		            ll.addView(ruler4,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));
		            ViewViloationReport.this.setContentView(sv);   
		            
		            
		            name.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							int j=0;
							String s=name.getText().toString().trim();
							System.out.println("textview get"+s);
							String[] data=s.split(":");
						
							String ra=data[j];

							if(day.equals("Today"))
							{
								Intent i=new Intent(ViewViloationReport.this,RARDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
								
							}
							if(day.equals("Yesturday"))
							{
								Intent i=new Intent(ViewViloationReport.this,RARDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
								
							}
							if(day.equals("Week"))
							{
								Intent i=new Intent(ViewViloationReport.this,RARDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
							if(day.equals("Month"))
							{
								Intent i=new Intent(ViewViloationReport.this,RARDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
							
						}
					});
		            
		            alert.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							System.out.println("in on alert click");
							
							int j=0;
							String s=alert.getText().toString().trim();
							System.out.println("textview get"+s);
							String[] data=s.split(":");
						
							String ra=data[j];
	
							
							
							if(day.equals("Today"))
							{
								
								Intent i=new Intent(ViewViloationReport.this,TodayViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
							if(day.equals("Yesturday"))
							{
								Intent i=new Intent(ViewViloationReport.this,TodayViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
							if(day.equals("Week"))
							{
								Intent i=new Intent(ViewViloationReport.this,TodayViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
							if(day.equals("Month"))
							{
								Intent i=new Intent(ViewViloationReport.this,TodayViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}

						}
					});
		            
		            
		            time.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							System.out.println("in on time click");	
							
							
							int j=0;
							String s=time.getText().toString().trim();
							System.out.println("textview get"+s);
							String[] data=s.split(":");
						
							String ra=data[j];
		
							
							if(day.equals("Today"))
							{
								
								Intent i=new Intent(ViewViloationReport.this,RARDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
							if(day.equals("Yesturday"))
							{
								Intent i=new Intent(ViewViloationReport.this,RARDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
							if(day.equals("Week"))
							{
								Intent i=new Intent(ViewViloationReport.this,RARDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
							if(day.equals("Month"))
							{
								Intent i=new Intent(ViewViloationReport.this,RARDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}

						}
					});
		            
		            cd.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							System.out.println("in on cd click");
							int j=0;
							String s=cd.getText().toString().trim();
							System.out.println("textview get"+s);
							String[] data=s.split(":");
						
							String ra=data[j];
							
							
							if(day.equals("Today"))
							{
								
								Intent i=new Intent(ViewViloationReport.this,CDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
							if(day.equals("Yesturday"))
							{
								Intent i=new Intent(ViewViloationReport.this,CDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
							if(day.equals("Week"))
							{
								Intent i=new Intent(ViewViloationReport.this,CDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
							if(day.equals("Month"))
							{
								Intent i=new Intent(ViewViloationReport.this,CDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}

						}
					});
		            
		            nd.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							System.out.println("in on nd click");
							
							int j=0;
							String s=nd.getText().toString().trim();
							System.out.println("textview get"+s);
							String[] data=s.split(":");
						
							String ra=data[j];
							
							if(day.equals("Today"))
							{
								Intent i=new Intent(ViewViloationReport.this,CDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
								
							}
							if(day.equals("Yesturday"))
							{
								Intent i=new Intent(ViewViloationReport.this,CDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
							if(day.equals("Week"))
							{
								Intent i=new Intent(ViewViloationReport.this,CDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
							if(day.equals("Month"))
							{
								Intent i=new Intent(ViewViloationReport.this,CDViolationdetails.class);
								i.putExtra("date", currentdate);
								i.putExtra("name", logas);
								i.putExtra("day", day);
								i.putExtra("ra", ra);
								startActivity(i);
							}
						}
					});           
			}
    	}