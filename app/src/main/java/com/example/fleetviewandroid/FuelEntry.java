	package com.example.fleetviewandroid;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class FuelEntry extends Activity
{

	Button submit;
	int activity=1;
	EditText  litres,odometer,petrol,remark;
	TextView fromdate,time;
	Spinner vehno,spdriver;
	DatePicker date;
	CheckBox fulltank;
	static final int TIME_DIALOG_ID = 999;
	private int year;
	private int month;
	private int day;
	private int toyear;
	private int tomonth;
	private int today;
	private int hour;
	private int minute;
	static final int DATE_PICKER_ID = 1111;
	String vhno,lit,odo,pet,remar,driver,fueldate,fuelhh,fuelmm,fuelchk;
	String addreqText,resText,loginuser,resTxt,responsetext;
	String getdata;
	HttpPost httppost;
	ArrayList<String> array=new ArrayList<String>();
	String[] drivername={"Jose","Mr. Amin"," MAHADEVA RASAL"," Balaji"," Faheem Khan"," Auraggajeb"};
	Date sysdate;

	String curdate;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fuelentry);
		litres=(EditText)findViewById(R.id.liters);
		odometer=(EditText) findViewById(R.id.odometer);
		petrol=(EditText) findViewById(R.id.Petrol);
		remark=(EditText) findViewById(R.id.remark);	
		vehno=(Spinner) findViewById(R.id.spnvehno);
		spdriver=(Spinner) findViewById(R.id.spndriver);
		submit=(Button) findViewById(R.id.submit1);
		fromdate=(TextView)findViewById(R.id.date);
		time=(TextView) findViewById(R.id.time);
		fulltank=(CheckBox) findViewById(R.id.checkbox);


		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sysdate = new Date();
		curdate=dateFormat.format(sysdate);

		Typeface face = Typeface.createFromAsset(getAssets(),
		             "arial.ttf");
		litres.setTypeface(face);
		  odometer.setTypeface(face);
		  petrol.setTypeface(face);
		  remark.setTypeface(face);
		  
	     System.out.println("in fuel entry"); 
	     Intent i=getIntent();
			loginuser=i.getStringExtra("name");
			array=i.getExtras().getStringArrayList("vehlist");
	     new MyAsynTask().execute(loginuser);
	     spinner();
	     

	        final Calendar c = Calendar.getInstance();
	        year  = c.get(Calendar.YEAR);
	        month = c.get(Calendar.MONTH);
	        day   = c.get(Calendar.DAY_OF_MONTH);
	 
			final Calendar c1 = Calendar.getInstance();
			hour = c1.get(Calendar.HOUR_OF_DAY);
			minute = c1.get(Calendar.MINUTE);
	 
			// set current time into textview
			time.setText(
	                    new StringBuilder().append(pad(hour))
	                                       .append(":").append(pad(minute)));
	 


	        // Show current date
	         
	        fromdate.setText(new StringBuilder()
	                // Month is 0 based, just add 1
	                .append(day).append("-").append(month+1).append("-")
	                .append(year));
	       String fm=fromdate.getText().toString();
	        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
		       
	        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
	       
	      String formatdate;
		try
		{
			formatdate = sdf2.format(sdf1.parse(fm));
			 fromdate.setText(formatdate);
			
			
	       }
		catch(Exception e)
		{
			
		}
            time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 showDialog(TIME_DIALOG_ID);
				
			}
		});
		fromdate.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);
			}
		});			
		submit.setOnClickListener
				(
				new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{

					// TODO Auto-generated method stub
				vhno=vehno.getSelectedItem().toString();
				driver=spdriver.getSelectedItem().toString();
				lit=litres.getText().toString();
				odo=odometer.getText().toString();
				remar=remark.getText().toString();
				pet=petrol.getText().toString();
				fueldate=fromdate.getText().toString();
				//fuelchk=fulltank.getText().toString();
				Calendar temp_c1 = Calendar.getInstance();		//Message body 2
				 
			    SimpleDateFormat temp_df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	//Message body 2
			    String temp_formattedDate = temp_df3.format(temp_c1.getTime());					//Message body 2
				String mailname="TWSupports";
				String desc="Security Alert";
				String body="Add Request entry Sucessfully";
				String subject="You have enter the add request ";
				String toid=loginuser;
				String status="Pending";
				String senddate=temp_formattedDate;
				String sendername="Transworld";
				String tocc="-";
				String tobcc="-";
	            
				if(fulltank.isChecked())
				{
					System.out.println("is checked");
					fulltank.setText("Yes");
					fuelchk="yes";
					System.out.println("is checked"+fulltank);

				}
				else
				{
					fulltank.setTag("No");

				}
				System.out.println("in submit button"+vhno+driver+lit+odo+remar+pet+""+hour+""+minute+fuelchk);
				new MyAsynInsert().execute(loginuser);
				//new Mymail().execute(mailname,desc,body,subject,toid,tocc,tobcc,status,senddate,sendername);
				
			}
			
		});  
	vehno.setOnItemSelectedListener(new OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
	
			String selecteditem=vehno.getSelectedItem().toString();
			System.out.println("selecteditem"+selecteditem);			 
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{
			// TODO Auto-generated method stub
			
		}
	});
		
	
	}
	public void spinner()
	{
		 ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
		            android.R.layout.simple_spinner_item, array);
		
		    // Drop down layout style - list view with radio button
		    spinnerAdapter
		            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    
		    // attaching data adapter to spinner
		    vehno.setAdapter(spinnerAdapter);

	}

	private class MyAsynInsert extends AsyncTask<String, Integer, String>
	{

//		ProgressDialog progressDialog;
		   
		String sp;
		String ccId="d_pandilwar@transworld-compressor.com";

		@Override
		protected void onPreExecute()
		{

/*
			progressDialog=new ProgressDialog(getApplicationContext());
			progressDialog.setTitle(" Please Wait");
			progressDialog.show();
*/

						super.onPreExecute();
		}

		@Override
        protected String doInBackground(String... params)
		{

			Log.i("FV"," In the do in bg ");

            // TODO Auto-generated method stub
			System.out.println("in submit button"+vhno+driver+lit+odo+remar+pet+""+hour+""+minute+fuelchk);
        	sp=insertmenu(vhno, fueldate, hour+"",minute+"", driver,lit,pet,fuelchk,odo ,"App");
			Log.i("FV"," In the do in bg "+sp);

			return sp;

        }
        protected void onPostExecute(String responseText)
		{

//			progressDialog.dismiss();

         	System.out.println("onPostExecute"+responseText);
         	if(responseText!=null)
         	{
         		Toast.makeText(getApplicationContext(), "Data Inserted Sucessfully", Toast.LENGTH_SHORT).show();
         		Intent i=new Intent(FuelEntry.this,MainForm.class);
         		i.putExtra("username", loginuser);
         		startActivity(i);
         	}

		}
	}
	

	public String insertmenu(String vehs,String fueldate,String fueltime1,String fueltime2,String drivers,String lits,String petpump,String fulltank,String odometer ,String App)
	{

		Log.i("FV"," In the Insert Menu..");

		ArrayList<NameValuePair> pList = new ArrayList<NameValuePair>();
		pList.add(new BasicNameValuePair("vehicleno", vehs));
		pList.add(new BasicNameValuePair("date",curdate));
		pList.add(new BasicNameValuePair("user", LoginForm.name));
		pList.add(new BasicNameValuePair("fulltank", fulltank));
//		pList.add(new BasicNameValuePair("fueltime2", fueltime2));
		pList.add(new BasicNameValuePair("driver", drivers));
		pList.add(new BasicNameValuePair("liters", lits));
		pList.add(new BasicNameValuePair("odometer", odometer));
		pList.add(new BasicNameValuePair("petrolpump", petpump));
		pList.add(new BasicNameValuePair("remark", App));


		Log.i("FV","Parameter to be sent  "+vehs+" "+fueldate+" "+fueltime1+" "+fulltank+" "+drivers+" "+odometer+" "+petpump+" "+App);


		System.out.println("===============inserting============");
		try
		{
			HttpPost httpPost;
			HttpClient client = new DefaultHttpClient();
//	httpPost = new HttpPost("http://10.0.2.2:8080/FleetView/fuelentryinsrt.jsp");
//	httpPost = new HttpPost("http://MyFleetView.com:8181/FleetViewProjectnew/MyServlet");
//	httpPost = new HttpPost("http://192.168.2.33:8080/FleetViewProjectnew/MyServlet");

	httpPost = new HttpPost("http://192.168.2.248:8080/FleetViewProject/MyServlet");

			Log.i("FV"," Parameter to be send... ");

			httpPost.setEntity(new UrlEncodedFormEntity(pList));
			HttpResponse entity = client.execute(httpPost);
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


		Log.i("FV"," Response  "+responsetext);

		return responsetext;
      
    }
	
	 private String inputdataString(HttpResponse i)
	 {
		 System.out.println("httpres22222222222"+i);
		 String data = "";
		 StringBuilder tot = new StringBuilder();
		 InputStream ins=null;
		 try {
			 System.out.println("in inputStreamToString()2222222");
		        ins = i.getEntity().getContent();
		        System.out.println("in inputStreamTo222222222"+  ins);
		 // Wrap a BufferedReader around the InputStream
		 BufferedReader rd = new BufferedReader(new InputStreamReader(ins));
		 // Read response until the end
		 try {
			 System.out.println("in inputStreamTo1in try22"+  ins);
		 while ((data = rd.readLine()) != null) {
			 System.out.println("in whileloop222"+  data);
		 tot.append(data);
		 }
		 System.out.println("in whileloopend22"+  data);
		 data=tot.toString().trim();
		 System.out.println("in line data222"+  data);
			

		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 // Return full string
		
		 }catch(Exception e)
		 {
			 
		 }
		 return data;
	 }

	class DoTask extends AsyncTask<String,Integer,String>
	{

		@Override
		protected void onPreExecute()
		{

				super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params)
		{

			String addreq=postreqData(params);

			return addreq;

		}

		@Override
		protected void onPostExecute(String s)
		{

				super.onPostExecute(s);
		}


		public String postreqData(String valueIWantToSend[])
		{
			HttpPost httppost;
			try
			{
				ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();

				nameValue.add(new BasicNameValuePair("transporter",valueIWantToSend[0]));
				HttpClient httpclient = new DefaultHttpClient();
//				httppost  = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
				httppost  = new HttpPost("http://192.168.2.248:8080/FleetViewProject/MyServlet");

				httppost.setEntity(new UrlEncodedFormEntity(nameValue));
				HttpResponse resp= httpclient.execute(httppost);

				addreqText=inputString(resp);

				Log.d("response", addreqText);
			}
			catch (ClientProtocolException e)
			{
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

		private class MyAsynTask extends AsyncTask<String, Integer, String>
		{


			@Override
			protected void onPreExecute()
			{
				super.onPreExecute();



			}



			@Override
	        protected String doInBackground(String... params) {
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
	                // Add your data
	            
	                ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	            
	                nameValue.add(new BasicNameValuePair("transporter",valueIWantToSend[0]));
					HttpClient httpclient = new DefaultHttpClient();
//			       httppost  = new HttpPost("http://myfleetview.com:8181/FleetView/MyServlet");
			       httppost  = new HttpPost("http://192.168.2.248:8080/FleetViewProject/MyServlet");

	               httppost.setEntity(new UrlEncodedFormEntity(nameValue));

				HttpResponse resp= httpclient.execute(httppost);
	               
 	                addreqText=inputString(resp);
	            
	             Log.d("response", addreqText);
	            } 
	      catch (ClientProtocolException e) {
	                // TODO Asuto-generated catch block
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
	    		 
	    		 } catch (IOException e)
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


			ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,drivername);
			arrayAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			spdriver.setAdapter(arrayAdapter);

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


		
	 @Override
	    protected Dialog onCreateDialog(int id)
	 {
	        switch (id)
			{
	        case DATE_PICKER_ID:
	             
	            // open datepicker dialog.
	            // set date picker for current date
	            // add pickerListener listner to date picker
	            return new DatePickerDialog(this, pickerListener, year, month,day);
	            
	       
	        case TIME_DIALOG_ID:
    			// set time picker as current time
    			return new TimePickerDialog(this,  timePickerListener, hour, minute,false);
  
	         
	        }
	        return null;
	    }
	 private TimePickerDialog.OnTimeSetListener timePickerListener = 
             new TimePickerDialog.OnTimeSetListener() {
 		public void onTimeSet(TimePicker view, int selectedHour,
 				int selectedMinute) {
 			hour = selectedHour;
 			minute = selectedMinute;
  
 			// set current time into textview
 			time.setText(new StringBuilder().append(pad(hour))
 					.append(":").append(pad(minute)).append(":").append("00"));
  		
 		}
 	};
 	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
			else
		   return "0" + String.valueOf(c);
	} 
 
	    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
	 
	        // when dialog box is closed, below method will be called.
	        @Override
	        public void onDateSet(DatePicker view, int selectedYear,
	                int selectedMonth, int selectedDay) {
	             
	            toyear  = selectedYear;
	            tomonth = selectedMonth;
	            today   = selectedDay;
	 
	            // Show selected date
	            fromdate.setText(new StringBuilder().append(today)
	                    .append("-").append(tomonth + 1).append("-").append(toyear)
	                    .append(" "));
	     
	    		
	            String fm=fromdate.getText().toString();
	            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
	    	       
	            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
	           
	          String formatdate;
	    	try
			{
	    		formatdate = sdf2.format(sdf1.parse(fm));
	    		 fromdate.setText(formatdate);
	            
	           }
	    	catch(Exception e)
	    	{
	    		
	    	}
	            
	           }
	        };
	        
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
		    		intent.putExtra("arr", array);
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
//				           httppost = new HttpPost("http://10.0.2.2:8080/FleetView/MyServlet");
//				           httppost = new HttpPost("http://192.168.2.33:8080/FleetViewProject/MyServlet");
				           httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
//				           httppost = new HttpPost("http://192.168.2.248:8080/FleetViewProject/MyServlet");
				            System.out.println("calling servlet");
				           
				              //   httppost = new HttpPost("http://103.241.181.36:8080/DeviceIMEITracker/DemoServlet");
				            	   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				            	   HttpResponse response = httpclient.execute(httppost);
				            	   origresponseText=readContent(response);
				            	   /* 
				            	    * closing the connection
				            	    */
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
			            		try
								{
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
