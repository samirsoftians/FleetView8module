package com.example.fleetviewandroid;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class OdoMeter extends Activity 
{

	Spinner sp1,sp2;
	Button submit;
	EditText sp3;
	TextView time;
	TextView date;
	TextView dbdate;
	TextView odometer;
	HttpPost httpPost=new HttpPost();
	private int year;
    private int month;
    private int day;
    int activity=1;
    private int hour;
	private int minute;
	HttpPost httppost;
    TextView ovehno,ocurrentdate,otime,odriver,oremark,odo_entry;
	static final int TIME_DIALOG_ID = 999;
//	ArrayList<String> array=new ArrayList<String>();
    static final int DATE_PICKER_ID = 1111; 
    String spveh,dobStr,spdrivername,odom,dbdat,tobr, resTxt, responsetext,getdata;
	String responseText, origresponseText="",addreqText,resText,loginuser;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.odometer);

		ovehno=(TextView) findViewById(R.id.odo_vehno);
		ocurrentdate=(TextView) findViewById(R.id.odo_currentdate);
		otime=(TextView) findViewById(R.id.odo_time);
		odriver=(TextView) findViewById(R.id.odo_driver);
		oremark=(TextView) findViewById(R.id.odo_remark);
		odo_entry=(TextView)findViewById(R.id.odo_entry);
		sp1=(Spinner) findViewById(R.id.vehnopinner);
	sp2=(Spinner) findViewById(R.id.driverspinner);
	sp3=(EditText) findViewById(R.id.remark);
	date=(TextView) findViewById(R.id.date);
	time=(TextView) findViewById(R.id.time);
	odometer=(EditText) findViewById(R.id.odometer);
	dbdate=new TextView(this);
	submit=(Button) findViewById(R.id.odosubmitbutton);
	  Typeface face = Typeface.createFromAsset(getAssets(),
	             "arial.ttf");
	  ovehno.setTypeface(face);
	  ocurrentdate.setTypeface(face);
	  otime.setTypeface(face);
	  odriver.setTypeface(face);
	  oremark.setTypeface(face);
	  sp3.setTypeface(face);
	  date.setTypeface(face);
	  time.setTypeface(face);
	  odometer.setTypeface(face);
	  dbdate.setTypeface(face);
	  submit.setTypeface(face);
	  odo_entry.setTypeface(face);
		Intent i=getIntent();
		loginuser=i.getStringExtra("name");

         spinner();
		System.out.println("loginuser"+loginuser);
 		new MyAsynTask().execute(loginuser);

		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
 
		// set current time into textview
		time.setText(
                    new StringBuilder().append(pad(hour))
                                       .append(":").append(pad(minute)));
 
		
		
		  // Get current date by calender
        
        final Calendar ct = Calendar.getInstance();
        year  = ct.get(Calendar.YEAR);
        month = ct.get(Calendar.MONTH);
        day   = ct.get(Calendar.DAY_OF_MONTH);
 
        // Show current date
        date.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month + 1).append("-")
                .append(year).append(" "));
        String dat=date.getText().toString();
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
	       
        SimpleDateFormat sdf4 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
       
      String tdate;
	try
	{

		tdate = sdf4.format(sdf3.parse(dat));
		 date.setText(tdate);

       }
	catch(Exception e)
	{
		
	}
        date.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);
			}
        });
		time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 showDialog(TIME_DIALOG_ID);
				
			}
		});
		
		submit.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
			
			System.out.println("on click+++++++++++activity2");	
			 dbdate.setText(new StringBuilder().append(year)
	                    .append("-").append(month+1).append("-").append(day)
	                    .append(" "));   
			 
			 dbdat=dbdate.getText().toString();
			String Str=date.getText().toString();
			
			 tobr=time.getText().toString();
			 dobStr=dbdat+""+tobr;
 			 odom=odometer.getText().toString();
						spveh=sp1.getSelectedItem().toString();
			
			 spdrivername=sp2.getSelectedItem().toString();
			 String spcomments=sp3.getText().toString();
			 Calendar temp_c1 = Calendar.getInstance();		//Message body 2
				 
			    SimpleDateFormat temp_df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	//Message body 2
			    String temp_formattedDate = temp_df3.format(temp_c1.getTime());					//Message body 2
				String mailname="TWSupports";
				String desc="Security Alert";
				String body=" Add Odometer entry Sucessfully";
				String subject="You have enter the odometer entry";
				String toid=loginuser;
				String status="Pending";
				String senddate=temp_formattedDate;
				String sendername="Transworld";
				String tocc="-";
				String tobcc="-";


			new MyAsynInsert().execute(loginuser);
			new Mymail().execute(mailname,desc,body,subject,toid,tocc,tobcc,status,senddate,sendername);
			
			}
		}).start();
			
			}
		});
		
		
	}

	private class MyAsynInsert extends AsyncTask<String, Integer, String>
	{

        @Override
        protected String doInBackground(String... params)
		{
            // TODO Auto-generated method stub
        	Log.d("tag1","in do ");
           
        	String s=insertodometer(spveh,dobStr,spdrivername,odom,loginuser);
			return s;

        }
        
        protected void onPostExecute(String responseText)
		{

			System.out.println("onPostExecute"+responseText);
         	if(responseText!=null)
         	{
         		Toast.makeText(getApplicationContext(), "Data Inserted  Sucessfully", Toast.LENGTH_SHORT).show();
         		Intent i=new Intent(OdoMeter.this,MainForm.class);
         		i.putExtra("loginuser", loginuser);
			finish();
         		startActivity(i);
 
         	}
}
	}
	
	
	private class MyTask extends AsyncTask<String, Integer, String>
	{
		   

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
        	Log.d("tag1","in do ");
            String s=postData(params);
           
            
            return s;
            
        }

        protected void onPostExecute(String responseText){
         	
         	spinner();
}
	

	
	public String postData(String valueIWantToSend[])
	{

        
        try {
            // Add your data
        	
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            
            nameValuePairs.add(new BasicNameValuePair("name",valueIWantToSend[0]));
            
          
            HttpClient httpclient = new DefaultHttpClient();

       httpPost  = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");


           httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
     
           
            HttpResponse response = httpclient.execute(httpPost);
           
          origresponseText=inputStreamToString(response);
        
         Log.d("response", origresponseText);
        } 
  catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } 
  catch (IOException e) {
            // TODO Auto-generated catch block
        }
       responseText = origresponseText.toString();
      

      return responseText;
      
    }
	
	 private String inputStreamToString(HttpResponse is)
	 {
		 
		 String line = "";
		 StringBuilder total = new StringBuilder();
		 InputStream in=null;
		 try {
			 
		        in = is.getEntity().getContent();
		     
		 // Wrap a BufferedReader around the InputStream
		 BufferedReader rd = new BufferedReader(new InputStreamReader(in));
		 // Read response until the end
		 try {
			 
		 while ((line = rd.readLine()) != null) {
			 
		 total.append(line);
		 }
		 
		 line=total.toString().trim();
		 		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 // Return full string
		
		 }catch(Exception e)
		 {
			 
		 }
		 return line;
	 }
}	
	private void spinner()
	{

		 ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
		            android.R.layout.simple_spinner_item,LoginForm.arrayList);
		    spinnerAdapter
		            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		   
		    sp1.setAdapter(spinnerAdapter);

	}

	private class MyAsynTask extends AsyncTask<String, Integer, String>
	{
        @Override
        protected String doInBackground(String... params)
		{
            // TODO Auto-generated method stub
        	Log.d("tag1","in do ");
           
            String addreq=postreqData(params);
            
            return addreq;
            
        }
        protected void onPostExecute(String responseText)
		{

         	System.out.println("onPostExecute"+responseText);
         	spinnerdriver();

		}
        public String postreqData(String valueIWantToSend[])
		{
			HttpPost httppost;
            try
			{
                ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
            
                nameValue.add(new BasicNameValuePair("transporter",valueIWantToSend[0]));
                            HttpClient httpclient = new DefaultHttpClient();
        httppost  = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");

               httppost.setEntity(new UrlEncodedFormEntity(nameValue));
         
               
                HttpResponse resp= httpclient.execute(httppost);
               
                 addreqText=inputString(resp);
            
             Log.d("response", addreqText);
            } 
      catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } 
      catch (IOException e) {
                // TODO Auto-generated catch block
            }
           resText = addreqText.toString();
        
     
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
    		 
    		 }
			 catch (IOException e)
			 {
    		 e.printStackTrace();
    		 }
    		 // Return full string
    		
    		 }catch(Exception e)
    		 {
    			 
    		 }
    		 return data;
    	 }
	}
	private void spinnerdriver()
	{
		if(resText!=null)
     	{

     	ArrayList<String> iddetails=new ArrayList<String>();
     	String comment;
     	ArrayList<Comment> arraylist=parseJson(resText);
			Iterator iter=arraylist.iterator();	
		
			System.out.println("9");
			while(iter.hasNext())
			{
				System.out.println("10");
				Comment data=(Comment)iter.next();
				System.out.println("11");
				
				comment=data.getDrvname();
				iddetails.add(comment);
				System.out.println("iddetails.add(data)"+iddetails);
			
			}
	
     	
		System.out.println("afetr myasktask()"+iddetails.size());
		 ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,
		            android.R.layout.simple_spinner_item, iddetails);
		 System.out.println("spinnerAdapter"+Adapter);
		    // Drop down layout style - list view with radio button
		    Adapter
		            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    System.out.println("spinnerAdapter2"+Adapter);
		    // attaching data adapter to spinner
		    sp2.setAdapter(Adapter);
     	}else
     	{
     		System.out.println("spinner not set");

}
	}

	
	public void insertmenu(String vecode,String fdate,String tdate,String vehcom,String entrby)
	{
		ArrayList<NameValuePair> pList = new ArrayList<NameValuePair>();
		pList.add(new BasicNameValuePair("vecode", vecode));
		pList.add(new BasicNameValuePair("fdate", fdate));
		pList.add(new BasicNameValuePair("tdate", tdate));
		pList.add(new BasicNameValuePair("vehcom", vehcom));
		pList.add(new BasicNameValuePair("entrby", entrby));
		
		try
		{
			HttpPost httpPost;
			HttpClient client = new DefaultHttpClient();

			httpPost  = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");

			httpPost.setEntity(new UrlEncodedFormEntity(pList));
			
			HttpResponse entity = client.execute(httpPost);
			
		}
		catch (Exception e)
		{

		}
	
	}
		
	public ArrayList<Comment> parseJson(String result)
	{
		ArrayList<Comment> mainObj = new ArrayList<Comment>();
		try
		{
			
			JSONArray mainMEnu = new JSONArray(result);
			for (int i = 0; i < mainMEnu.length(); i++)
			{
				
				JSONObject jsonObj = mainMEnu.getJSONObject(i);
				
				Comment menu = new Comment();
				menu.setDrvname(jsonObj.getString("DriverName"));
				mainObj.add(menu);

			}

		}
		catch (Exception e)
		{

		}
		return mainObj;
	}


	public ArrayList<Comment> parseJsondata(String result)
	{

		ArrayList<Comment> mainObj = new ArrayList<Comment>();
		try
		{
			
			JSONArray mainMEnu = new JSONArray(result);
			
			for (int i = 0; i < mainMEnu.length(); i++)
			{
				
				JSONObject jsonObj = mainMEnu.getJSONObject(i);
				
				Comment menu = new Comment();
				menu.setComment(jsonObj.getString("comment"));
				
				mainObj.add(menu);
			}

		} catch (Exception e) {

		}
		return mainObj;
	}
	public String insertodometer(String spveh,String dattm,String spdrivername,String odom,String loginuser)
	{
		ArrayList<NameValuePair> pList = new ArrayList<NameValuePair>();
		pList.add(new BasicNameValuePair("spveh", spveh));
		pList.add(new BasicNameValuePair("dat", dattm));
		pList.add(new BasicNameValuePair("spdrivername", spdrivername));
		pList.add(new BasicNameValuePair("odom", odom));
		pList.add(new BasicNameValuePair("loginuser", loginuser));
		try
		{
			HttpPost httpPost;
			HttpClient client = new DefaultHttpClient();
		 	httpPost  = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
			httpPost.setEntity(new UrlEncodedFormEntity(pList));
			
			HttpResponse entity = client.execute(httpPost);
			//HttpResponse resp= client.execute(httpPost);
			
			
			
			resTxt=inputdataString(entity);
	         
	         Log.d("response", resTxt);
        } 
  catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } 
  catch (IOException e) {
            // TODO Auto-generated catch block
        }
       responsetext = resTxt.toString();
    
 
      return responsetext;
      
    }
	
	 private String inputdataString(HttpResponse i) {
		
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


	
	
	 @Override
	    protected Dialog onCreateDialog(int id)
	 {

	           switch (id)
			   {
	        case DATE_PICKER_ID:
	             
	             return new DatePickerDialog(this, pickerListener, year, month,day);
	  		case TIME_DIALOG_ID:
    			return new TimePickerDialog(this,
                                            timePickerListener, hour, minute,false);
  
	        
	        }
	        return null;
	    }
	 
	    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
	 
	        // when dialog box is closed, below method will be called.
	        @Override
	        public void onDateSet(DatePicker view, int selectedYear,
	                int selectedMonth, int selectedDay) {
	             
	            year  = selectedYear;
	            month = selectedMonth;
	            day   = selectedDay;
	 
	            // Show selected date
	            date.setText(new StringBuilder().append(day)
	                    .append("-").append(month+1).append("-").append(year)
	                    .append(" "));
	       
	            String dat=date.getText().toString();
	            SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
	    	       
	            SimpleDateFormat sdf4 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
	           
	          String tdate;
	    	try {
	    		tdate = sdf4.format(sdf3.parse(dat));
	    		 date.setText(tdate);
	    		
	           }
	    	catch(Exception e)
	    	{
	    		
	    	}
	        }
	        };
	        
	    	private TimePickerDialog.OnTimeSetListener timePickerListener = 
	                new TimePickerDialog.OnTimeSetListener()
					{
	    		public void onTimeSet(TimePicker view, int selectedHour,
	    				int selectedMinute)
				{

					hour = selectedHour;
	    			minute = selectedMinute;
					time.setText(new StringBuilder().append(pad(hour))
	    					.append(":").append(pad(minute)).append(":").append("00"));
	     
	    			
	    		}
	    	};
	     
	    	private static String pad(int c)
			{
	    		if (c >= 10)
	    		   return String.valueOf(c);
	    		else
	    		   return "0" + String.valueOf(c);
	    	} 
	    	
	    	  @Override
		    	public void onBackPressed()
			  {
		    		// TODO Auto-generated method stub
		    		

		    		 if (activity == 1)
				 {
		    			
		    			homePage();

				 }
				 else
				 {

		    			Toast.makeText(getApplicationContext(), "You are in HomePage..",
		    					Toast.LENGTH_SHORT).show();
		    		}
		    		 
		    		 

		    	}

		        public void homePage()
			  {
		    		Intent intent = new Intent(this,MainForm.class);
		    		intent.putExtra("username", loginuser);
				  finish();
				  startActivity(intent);
		    	}

		        private class Mymail extends AsyncTask<String, Integer, String>
				{
					@Override
				    protected String doInBackground(String... params) 
					{
						String s=postData(params);
				       return s;
				    }//doInBackground(-..)		                                                                                                                                                                  
					protected void onPostExecute(String result)
					{
						getdata = result;
						Log.d("getdata from server", getdata);
						
							if(getdata!=null)
							{
								Toast.makeText(getApplicationContext(), "Email send Sucessfully", Toast.LENGTH_LONG).show();
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Email Not Send", Toast.LENGTH_LONG).show();
								
							}
				    }//onPostExecute(-)

					public String postData(String valueIWantToSend[])
					{
				       
						String origresponseText="";
				           
						try
						{
//							 if(request.getParameter("desc")!=null && request.getParameter("body")!=null &&request.getParameter("subject")!=null &&request.getParameter("toid")!=null &&request.getParameter("status")!=null &&request.getParameter("senddate")!=null &&request.getParameter("sendername")!=null)

				            	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				                // Add your data...............
				            	nameValuePairs.add(new BasicNameValuePair("mailname",valueIWantToSend[0]));
				            	nameValuePairs.add(new BasicNameValuePair("desc",valueIWantToSend[1]));
				            	nameValuePairs.add(new BasicNameValuePair("body",valueIWantToSend[2]));
				            	nameValuePairs.add(new BasicNameValuePair("subject",valueIWantToSend[3]));
				            	nameValuePairs.add(new BasicNameValuePair("toid",valueIWantToSend[4]));
				            	nameValuePairs.add(new BasicNameValuePair("tocc",valueIWantToSend[5]));
				            	nameValuePairs.add(new BasicNameValuePair("tobcc",valueIWantToSend[6]));
				            	nameValuePairs.add(new BasicNameValuePair("status",valueIWantToSend[7]));
				            	nameValuePairs.add(new BasicNameValuePair("senddate",valueIWantToSend[8]));
				            	nameValuePairs.add(new BasicNameValuePair("sendername",valueIWantToSend[9]));
				            	 
				            	 HttpClient httpclient = new DefaultHttpClient();
				          // httppost = new HttpPost("http://103.241.181.36:8080/DeviceRegistered/DemoServlet");
//				           httppost = new HttpPost("http://192.168.2.26:9090/FleetViewProject/MyServlet");
//				           httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewAndroidProject/MyServlet");
				           httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
				            System.out.println("calling servlet");
				           
				              //   httppost = new HttpPost("http://103.241.181.36:8080/DeviceIMEITracker/DemoServlet");
				            	   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				            	   HttpResponse response = httpclient.execute(httppost);
				            	   origresponseText=readContent(response);
				            	
				            	httpclient.getConnectionManager().shutdown();
				            }//try 
				        	catch (IOException e) 
				        	{
				            	e.printStackTrace();
				            }//catch
				            	
						 
				            return origresponseText.toString();
					}//postData(String []) String

				
					String readContent(HttpResponse response)
			        {
			       
			            String text = "";
			            InputStream in =null;
			          
			            try
						{
			            		in = response.getEntity().getContent();
			            
			            		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			            		
			            		StringBuilder sb = new StringBuilder();
			                
			            		String line = null;
			                
			            		while ((line = reader.readLine()) != null) 
			            		{
			            				//sb.append(line + "\n");
			            				sb.append(line);
			                    }//while
			                in.close();
			                    text = sb.toString();
			                    
			                }//try 
			            	catch (IllegalStateException e) 
			            	{
			            		e.printStackTrace();
			               
			            	}
			            	catch (IOException e) 
			            	{
			                  e.printStackTrace();
			            	}
			            	finally 
			            	{
			            		try {
			            				if (in != null) 
			            				{
			            					in.close();
			            				}//if
			            			}//try
			            			catch (Exception ex) 
			            			{
			            				ex.printStackTrace();
			            			}//catch
			               }//finally
			            
			            return text;
			        }//readContent() String
			
			}
      
}