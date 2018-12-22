package com.example.fleetviewandroid;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CurrentPosition extends Activity
{

    String vdate,vtime,vlat,vlng,vlocation;
    ListView listView;
    EditText serachitem;
    Button btngo;

    ArrayList<String> stringArrayList;
    public String vehicleno=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_position2);

        Intent intent1=getIntent();

        stringArrayList=intent1.getStringArrayListExtra("Vehlist");

        listView= (ListView) findViewById(R.id.list_view);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(CurrentPosition.this,android.R.layout.simple_dropdown_item_1line,stringArrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                vehicleno= (String) ((TextView) view).getText();
                new OnlineThread9().execute();

//                startActivity(new Intent(getApplicationContext(),MapsActivity.class));

            }
        });

    }
    public class OnlineThread9 extends AsyncTask<Void, String, Boolean>
    {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog=new ProgressDialog(CurrentPosition.this);
            progressDialog.setMessage("Vehicle Location.....");
            progressDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params)
        {

            // try { // Simulate network access.

            String response = "";
            try
            {

                String url = "http://103.241.181.36:8080/AppForFleetView/OnlineData?vehiclecode="
                        + vehicleno;

                url = url.replaceAll(" ", "%20");
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);

                HttpResponse execute = client.execute(httpGet);
                InputStream content = execute.getEntity().getContent();

                BufferedReader buffer = new BufferedReader(
                        new InputStreamReader(content));

                String s = "";
                while ((s = buffer.readLine()) != null)
                {
                    response += s;

                }
                // System.out.println("The response =>" + response);

            }
             catch (Exception e)
            {
                System.out.println("Exception occured!!" + e);
                return false;
            }

            if (!(response.equals("No_Data")))
            {

                try
                {
                    String[] rows = response.split("\\$");


                    Log.i("FLEETVIEW"," SERVER RESPONSE "+rows);

                    publishProgress(rows);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            }
            else
            {
                return false;
            }

        }
        // TODO: register the new account here. //return true;
        @Override
        protected void onProgressUpdate(String... arg)
        {

            super.onProgressUpdate(arg);

            vdate=arg[0];
            vtime=arg[1];
            vlat=arg[2];
            vlng=arg[3];
            vlocation=arg[4];

        }
        @Override
        protected void onPostExecute(final Boolean success)
        {

            progressDialog.dismiss();

            //Pass the server response to the next activity

//            Toast.makeText(CurrentPosition.this, " "+vdate+" "+vtime+" "+vlat+" "+vlng+" "+vlocation, Toast.LENGTH_SHORT).show();


            Toast.makeText(CurrentPosition.this, " Vehicle No:  "+vehicleno, Toast.LENGTH_SHORT).show();


            Intent intent1=new Intent(getApplicationContext(),MapsActivity.class);
            intent1.putExtra("VDATE",vdate);
            intent1.putExtra("VTIME",vtime);
            intent1.putExtra("VLAT",vlat);
            intent1.putExtra("VLNG",vlng);
            intent1.putExtra("VLOCATION",vlocation);
            intent1.putExtra("VNUM ",vehicleno);

            startActivity(intent1);

        }
    }

}
