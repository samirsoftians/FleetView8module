package com.example.fleetviewandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SelectUser extends Activity
{
AutoCompleteTextView text;
String user,passwd, gettext,selectdata;
Button submit;
HttpPost httppost;
String[] aa;
ArrayList<String> array=new ArrayList<String>();
ArrayList<String> arraylist=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectusername);
		  text=(AutoCompleteTextView)findViewById(R.id.autotransprter);
         submit=(Button)findViewById(R.id.submit);
		Intent ins=getIntent();
		user=ins.getStringExtra("username");
		passwd=ins.getStringExtra("passwd");
		selectdata=ins.getStringExtra("select");
        
		array=ins.getExtras().getStringArrayList("arr");

		System.out.println("arrrrrrrrrrrrrrrrrrrrrrrrr"+array.size()+"00000000000000000000000000"+selectdata);
		
		
	  
		
          text.addTextChangedListener(new TextWatcher() {
				
      				@Override
      				public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
      					// TODO Auto-generated method stub
      					
      					
      					// new MyTask().execute(gettext,uname);
      				//	GetAppointment.this.adapter.getFilter().filter(arg0);  
      						
      				}
      				
      				@Override
      				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
      						int arg3) {
      					
      				}
      				
      				@Override
      				public void afterTextChanged(Editable arg0) {
      					// TODO Auto-generated method stub
      					
      					 gettext=text.getText().toString();	
      					
      					validate(gettext);
      				}

      				
      			});
	
          submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				System.out.println("======in asynctask======");
	            new MyAsyncTask().execute(gettext);

				
			}
		});
	}
	
	public void validate(String gettext)
	{
		 Collections.sort(array);
		 
		  ArrayAdapter<String> adapter = new ArrayAdapter<String>  
            (this,android.R.layout.select_dialog_item,array);  
          text.setThreshold(1);  
		  text.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView  
           text.setTextColor(Color.BLACK);
	}
	
	
	private class MyAsyncTask extends AsyncTask<String,Integer ,String>{
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
        	System.out.println("===in MyTask");
			String s=postData(params);
		       return s;
        }
      
       
		protected void onPostExecute(String result){
         
			System.out.println("result in post==="+result);
			
        
/*        	
        	if(result.equals("Vehicles Not Available"))
				
			{
				Toast.makeText(getApplicationContext()
						, result, Toast.LENGTH_LONG).show();
			}
			else
			{
			//System.out.println("result=============="+result);

	String item=result.substring(2, result.length()-1); 
	//System.out.println("item=============="+item);
     String   item1=","+item;
	  int commas = 0;
		for(int i = 0; i < item1.length(); i++) {
   			 if(item1.charAt(i) == ',')
   				 commas++;
			}
				//System.out.println("=====ccccccccccccccccccccccccccccc==count"+commas);
		          
                    for(int j=1;j<=commas;j++)
		        	   {
			     aa=item1.split(",");			  
					//System.out.println("=======in activitycccccccccc ============"+aa[j]);
					arraylist.add(aa[j]);
	           
			      }
                            	    
        	                if(result!=null){
        	                	//Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG).show();
        	                  // sharedPrefernces();
        	                	Intent intent_name = new Intent();
        	                	intent_name.setClass(getApplicationContext(),MainForm.class);

        	                	intent_name.putExtra("username", user);
        	                	System.out.println("arrsize"+arraylist.size());
        	                	intent_name.putExtra("arr", arraylist);
        	                	intent_name.putExtra("passwd", passwd);

        	                	startActivity(intent_name);
        	                  	        	
        	                	
        	                }
        	                	else {
        	                		Toast.makeText(getApplicationContext(),"You have not selectuser", Toast.LENGTH_LONG).show();
        	                		}  		
        	
	}
*/ 
        	
        }
       
        
        
			public String postData(String valueIWantToSend[]) {
	            // Create a new HttpClient and Post Header
	            
	            //HttpPost httppost = new HttpPost("http://10.0.3.2:8080/AndroidServlet/LoginServlet");
	            
	             String origresponseText="";
	            try {
	                // Add your data
	            	
	                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	                System.out.println("in postdata1"+array.size());
	               nameValuePairs.add(new BasicNameValuePair("selectuser",valueIWantToSend[0]));
	               nameValuePairs.add(new BasicNameValuePair("grouplist",selectdata));

	           // for(int i=0;i<array.size();i++)
	            //{
	                //nameValuePairs.add(new BasicNameValuePair("grouplist",array.get(i)));
	            	//System.out.println("in postdata"+array.get(i));

	            	//}
	                HttpClient httpclient = new DefaultHttpClient();
	       //      httppost = new HttpPost("http:///FleetViewProject/MyServlet");
	        // httppost  = new HttpPost("http://10.0.2.2:80800/FleetView/validateselecteduser.jsp?selectuser="+gettext+"&grouplist="+array+" ");
	              //httppost = new HttpPost("http://203.199.134.131:8080/Second/MyServlet");
	               // httppost = new HttpPost("http://203.199.134.131:8080/First/Hello");
	                 //httppost = new HttpPost("http://192.168.2.73:8080/First/Hello");
	   	         httppost  = new HttpPost("http://myfleetview.com:8181/FleetView/validateselecteduser.jsp");

	                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	       	                System.out.println("in postdata5");
	                HttpResponse response = httpclient.execute(httppost);
	               // System.out.println("response"+response);
	                  //HttpEntity rp = response.getEntity();
	                  
	origresponseText=readContent(response);

	Log.d("response", origresponseText);
	            
	            }
	      catch (ClientProtocolException e) {
	                // TODO Auto-generated catch block
	            } 
	      catch (IOException e) {
	                // TODO Auto-generated catch block
	            }
	            String responseText = origresponseText.toString();
	            Log.d("ResponseText", responseText);
	     
	          return responseText;
	            
	        }
	      
	    String readContent(HttpResponse response)
	    {
	    	
	        String text = "";
	        InputStream in =null;
	         
	        try {
	            in = response.getEntity().getContent();
	            
	            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	            	//Log.d("", line);
	            	sb.append(line + "\n");
	                 
	                }
	                text = sb.toString();
	                //text = line.toString();
	                Log.d("TEXT", text);
	                
	                
	                
	        } 
	        catch (IllegalStateException e) {
	            e.printStackTrace();
	           
	        } catch (IOException e) {
	              e.printStackTrace();
	        }
	        finally {
	            try {

	              in.close();
	            } catch (Exception ex) {
	            }
	            }

	return text;

	    }
	   
	    }

}