package com.example.fleetviewandroid;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
public class LoginForm extends Activity
{
    EditText u_name, u_pass;
    Button login;
    ProgressDialog progressDialog;

    static ArrayList<String> arrayList = new ArrayList<>();
    public static String name, pass;
    private String[] vno;

    public String result;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            System.out.println("*** My thread is now configured to allow connection");
        }

        u_name = (EditText) findViewById(R.id.loginform_user_name);
        u_pass = (EditText) findViewById(R.id.loginform_user_password);

        login = (Button) findViewById(R.id.login_form_login_button);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                name = u_name.getText().toString();
                pass = u_pass.getText().toString();

                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

                if (!name.equals("") && !pass.equals("")) {

                    if (networkInfo != null && networkInfo.isConnected())
                    {
                        Log.i("SS"," IN THE NETWORK INFO");

//                        RequestLogin(name, pass);

                        new LoginTask(name,pass).execute();

                    }
                    else
                    {


                        Toast.makeText(LoginForm.this, "Please Check the Internet Connection", Toast.LENGTH_SHORT).show();

                    }

                }
                else
                {

                    u_name.setError("Enter the valid Name");
                    u_pass.setError("Enter the valid Password");

                }
            }
        });


    }

    class LoginTask extends AsyncTask<String, String, String>
    {
        String name, password;
        public LoginTask(String name, String password)
        {
            Log.i("SS"," IN THE LOGIN TASK");
            this.name = name;
            this.password = password;

        }
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog=new ProgressDialog(LoginForm.this);
            progressDialog.setMessage("Pleae Wait....");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params)
        {

            int status = 0;
            InputStream is = null;
            String line = null;

               String url = "http://twtech.in:8080/FleetViewProject/MyServlet";

            //String url = "http://192.168.2.248:8080/FleetViewProject/MyServlet";
        //    String url = "http://MyFleetView.com:8181/FleetViewProject/MyServlet";
            ArrayList<String> vehicle_no = new ArrayList<>();
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("name", "" + name));
            nameValuePairs.add(new BasicNameValuePair("password", "" + password));

            try
            {

                Log.i("SS"," IN THE BG METHOD ");

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.e("pass 1", "connection success ");

            }
            catch (Exception e)
            {
                Log.i("FLEET"," SERVER IS OFFLINE "+e);

            }
            try
            {
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    arrayList.add(line);

                    Log.i("FleetView"," ArrayList "+arrayList);
                }
                is.close();
                result = sb.toString();
                Log.d("FLEET", "LOG" + result);

                if (result != null)
                {
                    String item = result.substring(0, result.length() - 1);
                    Log.i("FleetView", " Result " + result);
                    String item1 = "," + item;
                    int commas = 0;
                    for (int i = 0 ;i < item1.length(); i++) {

                        if (item1.charAt(i) == ',')
                            commas++;
                    }

                    for (int j = 1; j <= commas; j++)
                    {
                        vno = item1.split(",");
                        //System.out.println("=======in activitycccccccccc ============"+aa[j]);
//                    arrayList.add(vno[j]);
                    }

                    Log.d("Result", "" + result);
                    Log.e("pass 2", "connection success ");

                }

            }
            catch (Exception e)
            {
                Log.e("Fail 2", e.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s)
        {

            Log.i("FLEET"," VALUE OF S"+s);
            Log.i("FLEET"," VALUE OF result: "+result);

            super.onPostExecute(s);
            progressDialog.dismiss();

            if (s.equals(""))
                {


                    u_name.setError(" Please Enter valid Username ");
                    Toast.makeText(LoginForm.this, "Check your credentilas....", Toast.LENGTH_SHORT).show();


                }
            else if (Character.isDigit(name.charAt(0)))
            {

                    Intent intent1=new Intent(getApplicationContext(),Safe2SchoolActivity.class);
                    intent1.putExtra("USERNAME",name);
                    startActivity(intent1);
            }
            else
                {
                    Intent intent = new Intent(getApplicationContext(), MainForm.class);
                    intent.putExtra("Username", name);
                    intent.putExtra("password", pass);
                    finish();
                    startActivity(intent);

                }
        }
    }
}


