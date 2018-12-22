package com.example.fleetviewandroid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

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

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewL1Report extends Activity
{
	String  addviewText,resText;
	ListView listView;
	String vehnumber,alertsatus,updatedttm, logas,status,day,currentdate;
	Comment data;
	int activity=1;
	ArrayList<String> iddetails=new ArrayList<String>();
	ArrayList<String> alertdetails=new ArrayList<String>();
	ArrayList<String> updatedattmdetail=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.l1generate);

	Intent i=getIntent();
	logas=i.getStringExtra("name");
	status=i.getStringExtra("status");
	day=i.getStringExtra("day");
	currentdate=i.getStringExtra("date");	
    new MyTask().execute(logas,status,day,currentdate);		
	}

	private class MyTask extends AsyncTask<String, Integer, String>{

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		   String viewl1=postviewData(params);
           return viewl1;
	}

	 protected void onPostExecute(String responseText){
      	viewreport();
      	 SetList();
      
}
	 
	
     public String postviewData(String valueIWantToSend[]) {
         // Create a new HttpClient and Post Header
         
         HttpPost httppost;        
         
         try {
             // Add your data
             ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
             nameValue.add(new BasicNameValuePair("loginas",valueIWantToSend[0]));
             nameValue.add(new BasicNameValuePair("status",valueIWantToSend[1]));
             nameValue.add(new BasicNameValuePair("day",valueIWantToSend[2]));
             nameValue.add(new BasicNameValuePair("currentdate",valueIWantToSend[3]));
           
             HttpClient httpclient = new DefaultHttpClient();
        //  httppost = new HttpPost("http://10.0.2.2:2020/FleetViewProject/MyServlet");
           httppost = new HttpPost("http://myfleetview.com:8181/FleetView/MyServlet");
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
	
		
	
public void viewreport()
{
	if(resText!=null)
 	{

 	ArrayList<Comment> arraylist=parseJson(resText);
		Iterator iter=arraylist.iterator();	
	
		while(iter.hasNext())
		{
			Comment data=(Comment)iter.next();
			
			vehnumber=data.getVehregno();
			alertsatus=data.getAlertstatus();
			updatedttm=data.getUpdatetime();
			
			
			  SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.ENGLISH);
		       
		        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.ENGLISH);
		      String formatdate;
			
			try{
					formatdate = sdf2.format(sdf1.parse(updatedttm));
				
			
			iddetails.add(vehnumber);
			alertdetails.add(alertsatus);
			updatedattmdetail.add(formatdate);
			
			
			
			}catch(Exception e)
			{
				
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
			
				
				menu.setAlertstatus(jsonObj.getString("VehIdleAlertStatus"));
				menu.setUpdatetime(jsonObj.getString("updatedDateTime"));
				menu.setVehregno(jsonObj.getString("VehicleRegNumber"));
				
			
				mainObj.add(menu);
			
			}
			

		} catch (Exception e) {

		}
		return mainObj;
	}
	public void SetList()
	{
		if(iddetails!=null)
		{
			
			 Typeface face = Typeface.createFromAsset(getAssets(),
		             "arial.ttf");
			 ScrollView sv = new ScrollView(ViewL1Report.this);
			 sv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
				//sv.setBackgroundResource(R.drawable.black);

		            LinearLayout ll = new LinearLayout(ViewL1Report.this);
		            ll.setOrientation(LinearLayout.VERTICAL);
		            ll.setPadding(5, 10, 0, 0);
		         //  ll.setBackgroundResource(R.drawable.bgimag);
		           ll.setBackgroundColor(0xFFded4e0);
		          // ll.setBackgroundColor(0xF000000);

		            sv.addView(ll);
		           
		            TextView heading = new TextView(ViewL1Report.this);
		            heading.setText(status+"Alerts");
		            
		            heading.setGravity(Gravity.CENTER_HORIZONTAL);
		            heading.setTypeface(face);
		           heading.setTextColor(0X000000);

		            heading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		            
		            ll.addView(heading);
		            
		            
		            for(int j=0;j<iddetails.size();j++)
			        {
		            	for(int k=0;k<alertdetails.size();k++)
		            	{
		            		for(int i=0;i<updatedattmdetail.size();i++)
		            		{
		            	
			            // Create TextView
			            //..... for name
			            TextView name = new TextView(ViewL1Report.this);
			            name.setTypeface(face);
			            name.setTextColor(0Xffffff);

			            name.setPadding(5, 20, 0, 0);
			            name.setText("VehNumber   : "+" "+iddetails.get(j));
			            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			            ll.addView(name);
			          
		            TextView alert = new TextView(ViewL1Report.this);
		            alert.setTypeface(face);
		            alert.setTextColor(0Xffffff);

		            name.setPadding(5, 20, 0, 0);
		            alert.setText("AlertStatus     : "+"  "+alertdetails.get(k));
		            alert.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		            ll.addView(alert);

		            TextView time = new TextView(ViewL1Report.this);
		            time.setTypeface(face);
		            time.setTextColor(0Xffffff);

		            name.setPadding(5, 20, 0, 0);
		            time.setText("DateTime       : "+"  "+updatedattmdetail.get(i));
		            time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		            ll.addView(time);
		           
		            //Ruler
		            View ruler = new View(ViewL1Report.this);
		            ruler.setBackgroundColor(0xFFb7c3c2); // line color
		            ll.addView(ruler,new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, 2));  
		            ViewL1Report.this.setContentView(sv);  	    
				}
		            	}
			        }

		}
	}
	
	
    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		
    	
		 if ( activity== 1) {
			
			homePage();
		} else{

			Toast.makeText(getApplicationContext(), "You are in HomePage..",
					Toast.LENGTH_SHORT).show();
		}
		 
	}

    public void homePage() {
		Intent intent = new Intent(ViewL1Report.this,MainForm.class);
		intent.putExtra("username", logas);
		startActivity(intent);
	}
}
