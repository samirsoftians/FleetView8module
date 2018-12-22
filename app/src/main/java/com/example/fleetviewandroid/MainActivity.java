package com.example.fleetviewandroid;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class MainActivity extends Activity implements OnClickListener
{
	TextView text;
	EditText username,password;
	Button login,cancel;
	String user ,passwd;
	 static boolean status=false;
	 HttpPost httppost;

	private ProgressBar pb;
		    ArrayList<String> arraylist=new ArrayList<String>();
			 String[] aa;
			 String data;

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lognact);
        text=(TextView)findViewById(R.id.heading);
        pb=(ProgressBar)findViewById(R.id.progressBar1);
	        pb.setVisibility(View.GONE);
        username=(EditText) findViewById(R.id.emailid);
        password=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);
       // cancel=(Button) findViewById(R.id.cancel);

		 Typeface face = Typeface.createFromAsset(getAssets(),
	             "HEMIHEAD.TTF");
		  text.setTypeface(face);
	
        login.setOnClickListener(this);
        //cancel.setOnClickListener(this); 
   	 	 user = username.getText().toString();
      	passwd = password.getText().toString();
    	 System.out.println("in the strt of welcome activity");

    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login:
	System.out.println("inlogin");
	boolean bool = isOnline();
	if (bool == true) {
	if(status=true)
	{
	            user = username.getText().toString();
                 passwd = password.getText().toString();
       // user="d_pandilwar@transworld-compressor.com";   
        //passwd="transworld";

		Log.i("FLEET","IN HTTP REQUEST"+user);
		Log.i("FLEET","IN HTTP REQUEST"+passwd);


		pb.setVisibility(View.VISIBLE);
		if(user.equals("")||passwd.equals(""))
                 {
                	 
                	 Toast.makeText(this, "Please Enter All The Feilds", Toast.LENGTH_SHORT).show();
                 }
			else
                 {

            	new MyTask().execute(user,passwd,"App");

                 }
             
                 }
	}
	 else
	{
			Toast.makeText(getApplicationContext(),
					"No Internet connection.", Toast.LENGTH_LONG)
					.show();

	}			break;

		case R.id.cancel:
			Intent i=new Intent(this,MainActivity.class);
			startActivity(i);
			break;
		}
	}
	
	private class MyTask extends AsyncTask<String, Integer, String>
	{
				
			@Override
		    protected String doInBackground(String... params) 
			{

				Log.i("FLEET","IN BG");

				String s=postData(params);
		       return s;
		    }//doInBackground(-..)
			protected void onProgressUpdate(Integer... progress){
	            pb.setProgress(progress[0]);
	        }
			protected void onPostExecute(String result)
			{

				Log.i("FLEET","IN THE POST EXECUTE");

				String result1=result;
				System.out.println("result=============="+result);
				  //String ss="GROUP Crest Premedia Rakshak MH 12 EQ 6472, MH 12 FZ 7693, MH 12 HB 9102, MH 12 HB 1523, MH 12 HB 2133, MH 14 CW 0031, MH 12 HB 1572, MH 14 CW 1547, MH 14 BA 9940, MH 14 BA 9754, MH 12 HB 0319, MH 14 CW 0229, MH 14 TW 2768, MH 12 HB 560, MH 12 HB 1903, MH 12 FZ 8194, MH 12 KQ 1578, MH 12 HB 1503, MH 12 HB 6863, MH 12 HB 1502, MH 12 HB 1500, MH 12 KQ 1581, Dipti, Raisha Datta, Major Soni, Prajakta, Praveen Dhere, Mandar Kulkarni, Exception java.lang.ArrayIndexOutOfBoundsException: 28"; 
			       //  String temp=res.replace("GROUP Crest Premedia Rakshak", "")  ;   
				//result="[MH 04 GF 9265,UI_8415,NL 01 L 7224]";
				if(result.equals("Vehicles Not Available"))
				
				{
					Toast.makeText(getApplicationContext()
							, result, Toast.LENGTH_LONG).show();
				}
				else
				{

					Log.i("FLEET","IN THE POST EXECUTE"+result);


					String item=result.substring(2, result.length()-1);
		//System.out.println("item=============="+item);
         String   item1=","+item;
		  int commas = 0;
			for(int i = 0; i < item1.length(); i++)
			{
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
	  //today comment                  
	                     System.out.println("arraylist size******************************"+arraylist.size());
        	    
	        	                if(result!=null)
					    {
	        	                	Toast.makeText(getApplicationContext(),"Login Sucessfully", Toast.LENGTH_LONG).show();
	        	                  // sharedPrefernces();
	        	               	 	Intent intent_name = new Intent();
	        	                	//intent_name.setClass(getApplicationContext(),SelectUser.class);
	        	                	intent_name.setClass(getApplicationContext(),MainForm.class);

						    Log.i("FLEET","IN THE RESULT"+arraylist);

						    intent_name.putExtra("username", user);
						    Log.i("FLEET","IN THE POST EXECUTE"+username);
						    Log.i("FLEET","IN THE POST EXECUTE"+passwd);

						    System.out.println("arrsize"+arraylist.size());
	        	                	intent_name.putExtra("arr", arraylist);
	        	                	//intent_name.putExtra("select", result1);
	        	                	intent_name.putExtra("passwd", passwd);

	        	                	startActivity(intent_name);
	        	                  	        	
	        	                	username.setText("");
	        	                	password.setText("");
	        	                }
	        	                	else
					    {
	        	                		Toast.makeText(getApplicationContext(),"Login Unsuccessful Please try again", Toast.LENGTH_LONG).show();
	        	                		}  		
	        	                		}
			}
			public String postData(String valueIWantToSend[])
			{
				httppost= new HttpPost();
				String origresponseText="";

				try
				{

					String url ="http://192.168.2.26:8080/AndrFleetApp/LoginServlet?username="
							+ username.getText().toString()
							+ "&password="
							+ password.getText().toString();



		            /*	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		                // Add your data...............
		            	 nameValuePairs.add(new BasicNameValuePair("user",valueIWantToSend[0]));
		            	 nameValuePairs.add(new BasicNameValuePair("Password",valueIWantToSend[1]));
		            	 nameValuePairs.add(new BasicNameValuePair("App",valueIWantToSend[2]));
					HttpClient httpclient = new DefaultHttpClient();
					Log.i("FLEET","IN HTTP REQUEST"+httpclient);
					Log.i("FLEET","IN HTTP REQUEST"+httppost);
*/

					//	 httppost = new HttpPost("http://10.0.2.2:8080/FleetView/userinfo.jsp");
//					 httppost = new HttpPost("http://103.8.126.138:8080/FleetViewTest/userinfo.jsp");

//						httppost = new HttpPost("http://myfleetview.com:8181/FleetView/userinfodipti.jsp");
//						httppost = new HttpPost("http://103.241.181.36:8080/AndrFleetApp/LoginServlet?username=");
//					httppost = new HttpPost("http://myfleetview.com:8181/FleetView/LoginServlet?username=");

//          	 	httppost = new HttpPost("http://MyFleetView.com:8181/FleetView/userinfodipti.jsp");
//          	 	httppost = new HttpPost("http://103.241.181.36:8080/AndrFleetApp/LoginServlet?username=");
//          	 	httppost = new HttpPost("http://192.168.2.26:8080/FleetViewProject/MyServlet?username=");

						   /*httppost.setEntity(new UrlEncodedFormEntity(url));
						   HttpResponse response = httpclient.execute(httppost);
						   origresponseText=readContent(response);
*/
					DefaultHttpClient client = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url);

					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

//					origresponseText=readContent(response);


					Log.i("FLEET","IN HTTP REQUEST"+user);
					Log.i("FLEET","IN HTTP REQUEST"+passwd);



					Log.i("FLEET","IN HTTP RESPONSE"+httppost);
						Log.i("FLEET","IN HTTP RESPONSE"+origresponseText);

//						Log.i("FLEET","IN HTTP RESPONSE"+httpclient);

	
//						httpclient.getConnectionManager().shutdown();
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

				Log.i("FLEET","IN HTTP REQUEST"+in);


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

			  Log.i("FLEET","REPONSE"+text);

	            return text;
	        }//readContent() String
	}

	public boolean isOnline()
	{

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		boolean result = false;
		if (ni != null) {
			if (ni.getState() == NetworkInfo.State.CONNECTED) {
				result = true;
			}
		}

		return result;

	}
		}