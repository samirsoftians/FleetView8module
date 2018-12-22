package com.example.fleetviewandroid;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothClass;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.R.string.no;
public class DeviceStatus extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_status);

        final EditText editText= (EditText) findViewById(R.id.unitid);
        Button btnsend= (Button) findViewById(R.id.sendreq);

        btnsend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Log.i("FLEETVIEW"," IN THE BTN SEND ");

                String s1=editText.getText().toString();

                new SENDREQ(s1).execute();

            }
        });
    }

    class SENDREQ extends AsyncTask<String,String,String>
    {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog=new ProgressDialog(DeviceStatus.this);
            progressDialog.setTitle(" Please Wait");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(String s)
        {

            super.onPostExecute(s);
            progressDialog.dismiss();


        }
        String s1;
        public SENDREQ(String s1)
        {
            this.s1=s1;
        }
        @Override
        protected String doInBackground(String... params)
        {

            Log.i("FLEETVIEW"," IN THE BG METHOD");


            int status = 0;
            String is = null;
            String line = null;

//            String url = "http://MyFleetView.com:8181/FleetViewProject/MyServlet";
            String url = "http://192.168.2.248:8080/FleetViewProject/UnitData";
//            String url = "http://192.168.2.33:8080/FleetViewProject/MyServlet";

            ArrayList<String> vehicle_no = new ArrayList<>();
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("unitid", "" + s1));

            Log.i("FLEETVIEW"," name value pair "+s1);

            try
            {

                Log.i("SS"," IN THE BG METHOD ");
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = readresponse(response);

                Log.e("pass 1", "connection success "+is);

            }
            catch (Exception e)
            {

                Toast.makeText(DeviceStatus.this, "Server is Offline..Please Try Again ", Toast.LENGTH_LONG).show();

            }
            return null;

        }

        private String readresponse(HttpResponse response)
        {
            InputStream is=null;
            String return_text="";
            try
            {

                is=response.getEntity().getContent();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                String line="";
                StringBuffer sb=new StringBuffer();

                while ((line=bufferedReader.readLine())!=null)
                {
                
                    sb.append(line);

                }

                return_text=sb.toString();
            }
            catch (Exception e)
            {


            }
            Log.i("FLEETVIEW"," IN THE RETURN TEXT "+return_text);

            return return_text;

        }
    }

}

