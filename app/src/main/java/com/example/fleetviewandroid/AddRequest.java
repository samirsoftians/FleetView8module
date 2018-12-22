package com.example.fleetviewandroid;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
public class AddRequest extends Activity
{
	TextView fromdate,todate;
	Spinner sp1,sp2;
	Button submit;
	EditText other;
	private int year;
	private int month;
	private int day;
	TextView fmdate,tmdate;
	int activity=1;
	HttpPost httppost;
    private int toyear;
    private int tomonth;
    private int today;
    static final int DATE_PICKER_ID = 1111; 
    static final int DATE_PICKER_ToID = 1; 
	HttpPost httpPost=new HttpPost();
	TextView vehno,fmdat,tdat,spcmt;
	String spvehicle,dobStr	,TobStr,spcomments,responsetext,resTxt,getdata;
	String responseText,spother, origresponseText="",addreqText,resText,loginuser;
	private String Fromdate;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addingrequest);
		fromdate=(TextView)findViewById(R.id.fromdate);

		todate=(TextView)findViewById(R.id.todate);
		sp1=(Spinner)findViewById(R.id.vehiclespinner);
		sp2=(Spinner)findViewById(R.id.spinnercomments);
		other=(EditText) findViewById(R.id.othercomments);
		vehno=(TextView) findViewById(R.id.vehno);
		fmdat=(TextView) findViewById(R.id.fmdate);
		tdat=(TextView) findViewById(R.id.tdate);
		submit=(Button)findViewById(R.id.submit);
			spcmt=(TextView) findViewById(R.id.spcmmt);
		
		  Typeface face = Typeface.createFromAsset(getAssets(),
		             "arial.ttf");
		  fromdate.setTypeface(face);
		  todate.setTypeface(face);
		  other.setTypeface(face);
		  submit.setTypeface(face);
		   vehno.setTypeface(face);
		   tdat.setTypeface(face);
		   fmdat.setTypeface(face);
		   spcmt.setTypeface(face);

		fmdate=new TextView(this);
		tmdate=new TextView(this);

		other.setVisibility(View.GONE);

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
		Fromdate= sdf.format(new Date());

		sp2.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
		
				String selecteditem=sp2.getSelectedItem().toString();
							 
				 if(selecteditem.equals("Other"))
				 {

			      		other.setVisibility(View.VISIBLE);
			     			other.setTextColor(Color.BLACK);
			      		spother=other.getText().toString();

				 }

				}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		//get the vehicleno and comments to spinner

		Intent i=getIntent();
		loginuser=i.getStringExtra("name");
		new MyTask().execute(loginuser);
		
		new MyAsyncTask().execute(loginuser);
		

        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);
 
        // Show current date
         
        fromdate.setText(new StringBuilder()
                // Month is 0 based, just add 14
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

		if ( formatdate.equals("") )
		{

			Toast.makeText(AddRequest.this, "Please Select the date", Toast.LENGTH_SHORT).show();

		}

	}
	catch(Exception e)
	{

		
	}
        fromdate.setOnClickListener(new OnClickListener()
	  {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);
			}
		});
        
        
        // Show current date
        
        todate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month + 1).append("-")
                .append(year).append(" "));
        String todat=todate.getText().toString();
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
		SimpleDateFormat sdf4 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
       
      String tdate;
	try
	{
		tdate = sdf4.format(sdf3.parse(todat));
		 todate.setText(tdate);
	}
	catch(Exception e)
	{
		
	}    
        todate.setOnClickListener
				(

			 	  new OnClickListener()
				  {
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				showDialog(DATE_PICKER_ToID);
			
				 
			}
		});

		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0)
			{


				Log.i("FLEETVIEW","In the On click");
				
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								
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



								fmdate.setText(new StringBuilder().append(toyear)
										.append("-").append(tomonth + 1).append("-").append(today)
										.append(" "));
								Log.i("FLEETVIEW", "Append the from data");

								// Show selected date
					            tmdate.setText(new StringBuilder().append(year)
					     	               .append("-").append(month + 1).append("-").append(day)
					                    .append(" "));

								Log.i("FLEETVIEW","Append the to date");

								spvehicle=sp1.getSelectedItem().toString();
						 dobStr=fmdate.getText().toString();
						 TobStr=tmdate.getText().toString();
					     spcomments=sp2.getSelectedItem().toString();
					 
						new MyAsynInsert().execute(loginuser);

						new Mymail().execute(mailname,desc,body,subject,toid,tocc,tobcc,status,senddate,sendername);
							}
				}).start();		
			}
		});
	}
		
	private class MyAsynInsert extends AsyncTask<String, Integer, String>
	{


		String sp;
		String ccId="s_suryawanshi@transworld-compressor.com";


		@Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
        	Log.d("tag1","in do");
        	if(spother!=null)
			 {
        		
        	
		      spother=other.getText().toString();
	        	sp=insertmenu(spvehicle,dobStr,TobStr,spother,loginuser,"Adding Vehicle Intimation Entry Sucessfully", loginuser, ccId, "", "Add Request Entry ", "", "", "", "Add Request Entry Report", "", "");

               //mail send entry in table t_allpendingmailtable
	      
	        	//String str, String toID, String ccID, String Customer,String heading,  String date1, String date2, String displayedReportNo, String ReportName,  String FileName,String AttachFilePath)
	       // template.sendmail("Adding Vehicle Intimation Entry Sucessfully", loginuser, ccId, "", "Add Request Entry ", "", "", "", "Add Request Entry Report", "", "");
	      

			 }
        	 else
        	 {
        	
        	String s=insertmenu(spvehicle,dobStr,TobStr,spcomments,loginuser,"Adding Vehicle Intimation Entry Sucessfully", loginuser, ccId, "", "Add Request Entry ", "", "", "", "Add Request Entry Report", "", "");
        

        	//template.sendmail("Adding Vehicle Intimation Entry Sucessfully", loginuser, ccId, "", "Add Request Entry ", "", "", "", "Add Request Entry Report", "", "");

			return s;
        	 }
			return sp;

        }
        
       
        protected void onPostExecute(String responseText)
	  {


         	System.out.println("onPostExecute"+responseText);
         	if(responseText!=null)
         	{
         		Toast.makeText(getApplicationContext(), "Data Inserted Sucessfully", Toast.LENGTH_SHORT).show();
         		Intent i=new Intent(AddRequest.this,MainForm.class);
         		i.putExtra("username", loginuser);
			finish();
//			i.putExtra("vehiclelist",array);
         		startActivity(i);
         	}
}
	}

	private class MyAsyncTask extends AsyncTask<String, Integer, String>
	{

        @Override
        protected String doInBackground(String... params)
	  {
            // TODO Auto-generated method stub
        	Log.d("tag1","in do ");
            String s=postData(params);
            return s;
            
        }



        protected void onPostExecute(String responseText)
	  {
         	System.out.println("onPostExecute"+responseText);
         	spinner();

	  }

	public String postData(String valueIWantToSend[])
	{

        try
	  {
            // Add your data
        	
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("user",valueIWantToSend[0]));

            HttpClient httpclient = new DefaultHttpClient();

		  httpPost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
//		  httpPost = new HttpPost("http://192.168.2.33:8080/FleetViewProject/MyServlet");

           httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httpPost);

         origresponseText=inputStreamToString(response);
        
         Log.d("response", origresponseText);
        } 
  catch (ClientProtocolException e)
  {
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


		 ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,LoginForm.arrayList);
		
		    // Drop down layout style - list view with radio button
		    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    
		    // attaching data adapter to spinner
		    sp1.setAdapter(spinnerAdapter);

	}

	private class MyTask extends AsyncTask<String, Integer, String>
	{
		   

        @Override
        protected String doInBackground(String... params)
	  {
            // TODO Auto-generated method stub
        	Log.d("tag1","in do ");
           
            String addreq=postreqData(params);
		  Log.i("FLEET "," Addreq " +addreq);
		  return addreq;


            
        }

        protected void onPostExecute(String responseText)
	  {
         	System.out.println("onPostExecute"+responseText);
         	spinnercomment();

	  }
	
        public String postreqData(String valueIWantToSend[]) {
            // Create a new HttpClient and Post Header
            
            HttpPost httppost;

			try
		{
                // Add your data
            	System.out.println("in Addrequestactivity22222 postdata");
                ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
                System.out.println("in postdata1");
                nameValue.add(new BasicNameValuePair("user",valueIWantToSend[0]));
                System.out.println("in postdata2");
              
                HttpClient httpclient = new DefaultHttpClient();
        //  httppost = new HttpPost("http://10.0.2.2/FleetViewProject/MyServlet");
        httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
//        httppost = new HttpPost("http://192.168.2.33:8080/FleetViewProject/MyServlet");
                System.out.println("in postdata4");

               httppost.setEntity(new UrlEncodedFormEntity(nameValue));
         
                System.out.println("in postdata5");
                HttpResponse resp= httpclient.execute(httppost);
                System.out.println("response=========222222222=========="+resp);
//                HttpEntity httpentity=response.getEntity();
//                System.out.println("httpentity==================="+httpentity);
//                InputStream is=httpentity.getContent();
//                System.out.println("is==================="+is);
//         
                addreqText=inputString(resp);
            
             Log.d("response", addreqText);
            } 
      catch (ClientProtocolException e)
	  {
                // TODO Auto-generated catch block

            }
      catch (IOException e)
	  {
                // TODO Auto-generated catch block

            }
           resText = addreqText.toString();
     
          return resText;
          
        }
    	
		private String inputString(HttpResponse i)
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
	}
	private void spinnercomment()
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
				
				comment=data.getComment();
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
     	}
		else
     	{
     		System.out.println("spinner not set");

}
	}

	public String insertmenu(String vecode,String fdate,String tdate,String vehcom,String entrby,String body,String to,String cc,String customer,String heading,String data1,String data2, String data3,String reportname,String filename,String attach)
	{
		
		ArrayList<NameValuePair> pList = new ArrayList<NameValuePair>();
		pList.add(new BasicNameValuePair("vecode", vecode));
		pList.add(new BasicNameValuePair("fdate", Fromdate));
		pList.add(new BasicNameValuePair("tdate", tdate));
		pList.add(new BasicNameValuePair("vehcom", vehcom));
		pList.add(new BasicNameValuePair("entrby", entrby));
		pList.add(new BasicNameValuePair("body", body));
		pList.add(new BasicNameValuePair("to",to));
		pList.add(new BasicNameValuePair("cc", cc));
		pList.add(new BasicNameValuePair("customer", customer));
		pList.add(new BasicNameValuePair("heading", heading));
		pList.add(new BasicNameValuePair("data1", data1));
		pList.add(new BasicNameValuePair("data2", data2));
		pList.add(new BasicNameValuePair("data3", data3));
		pList.add(new BasicNameValuePair("report", reportname));
		pList.add(new BasicNameValuePair("filename", filename));
		pList.add(new BasicNameValuePair("attach", attach));

		System.out.println("===============inserting============");
		try
		{
			HttpPost httpPost;
			HttpClient client = new DefaultHttpClient();
 	httpPost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
// 	httpPost = new HttpPost("http://192.168.2.33:8080/FleetViewProject/MyServlet");
 			System.out.println("on netcheck :" + pList);
			httpPost.setEntity(new UrlEncodedFormEntity(pList));
			HttpResponse entity = client.execute(httpPost);

			resTxt=inputdataString(entity);
	         
	         Log.d("response", resTxt);
        } 
  catch (ClientProtocolException e)
  {
            // TODO Auto-generated catch block
        } 
  catch (IOException e)
  {
            // TODO Auto-generated catch block
        }
       responsetext = resTxt.toString();

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
	 			
	public ArrayList<Comment> parseJson(String result)
	{
		ArrayList<Comment> mainObj = new ArrayList<Comment>();
		try
		{
			System.out.println("11s");
			JSONArray mainMEnu = new JSONArray(result);
			System.out.println("22s");
			for (int i = 0; i < mainMEnu.length(); i++)
			{
				System.out.println("33s");
				JSONObject jsonObj = mainMEnu.getJSONObject(i);
				System.out.println("44s");

				//Pojo class is used for the getter and setter
				Comment menu = new Comment();
				System.out.println("55s");
				menu.setComment(jsonObj.getString("comment"));
				System.out.println("66s");
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
	            
	        case DATE_PICKER_ToID:
	        	return new DatePickerDialog(this, pickerListener1, toyear, tomonth,today);
	            
	        }
	        return null;
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

			if ( fromdate.equals("") )
			{

				Toast.makeText(AddRequest.this, "Please Select the Date", Toast.LENGTH_SHORT).show();
			}
	    		
	    		
	            
	           }
	    	catch(Exception e)
	    	{
	    		
	    	}
	            
	           }
	        };

	        private DatePickerDialog.OnDateSetListener pickerListener1 = new DatePickerDialog.OnDateSetListener() {
	       	 
		        // when dialog box is closed, below method will be called.
		        @Override
		        public void onDateSet(DatePicker view, int selectedYear,
		                int selectedMonth, int selectedDay) {
		             
		            year  = selectedYear;
		            month = selectedMonth;
		            day   = selectedDay;
		 
		            // Show selected date
		            todate.setText(new StringBuilder().append(day)
		                    .append("-").append(month + 1).append("-").append(year)
		                    .append(" "));
		     
		            String todat=todate.getText().toString();
		            SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
		    	       
		            SimpleDateFormat sdf4 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
		           
		          String tdate;
		    	try {

				 tdate = sdf4.format(sdf3.parse(todat));
		    		 todate.setText(tdate);
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
		    		 if (activity == 1) {
		    			

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
		    		Intent intent = new Intent(AddRequest.this,MainForm.class);
		    		intent.putExtra("username", loginuser);
		    		finish();
				  startActivity(intent);
		    	}
		    	private class Mymail extends AsyncTask<String, Integer, String>
				{
					ProgressDialog progressDialog;


					@Override
					protected void onPreExecute()
					{
						super.onPreExecute();

						progressDialog=new ProgressDialog(AddRequest.this);

						progressDialog.setMessage(" Please Wait....");

						progressDialog.show();


					}

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

						progressDialog.dismiss();


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
							     httppost = new HttpPost("http://MyFleetView.com:8181/FleetViewProject/MyServlet");
//							     httppost = new HttpPost("http://192.168.2.33:8080/FleetViewProject/MyServlet");

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
			          
			            try {
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
/*
* you got to go angry angry all of my honosty
* you know i try but i dont do too well with apologies */