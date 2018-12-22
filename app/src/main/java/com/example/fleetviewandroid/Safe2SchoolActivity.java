package com.example.fleetviewandroid;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Safe2SchoolActivity extends Activity
{

    String username,vnum,vcode,status,date,time,lat,log,location,speed;
    String[] USERNAME;
    String[] typeuser;
    private String[] markerArray;
    private String numberOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe2_school);

            Intent intent1=getIntent();
        username=intent1.getStringExtra("USERNAME");


        ImageView imageView= (ImageView) findViewById(R.id.imageview);

        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new logintask().execute();

            }
        });

    }
    class logintask extends AsyncTask<Void,String,Boolean>
    {

        String response;

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog=new ProgressDialog(Safe2SchoolActivity.this);
            progressDialog.setMessage(" Please Wait...");
            progressDialog.show();



        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            String nameOfUser = "";

            try
            {

                 String url = "http://103.241.181.36:8080/AndrFleetApp3/LoginServlet?username="
                        +username
                        + "&password="
                        + username;

                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);

                HttpResponse execute = client.execute(httpGet);
                InputStream content = execute.getEntity().getContent();

                BufferedReader buffer = new BufferedReader(
                        new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null)
                {

                    nameOfUser += s;

                }
                typeuser=nameOfUser.split("#");

                // publishProgress(nameOfUser);
                System.out.println("The response login servlet =>" + nameOfUser);
                System.out.println("The response login servlet=>" + typeuser);
                System.out.println("The response login servlet=>" + s);

                Log.i("TM", "RESPONSE FROM Login SERVLET " +nameOfUser+" "+typeuser+" "+s);

//                    Thread.sleep(2000);
            }
            catch (Exception e)
            {
                System.out.println("Exception occured!!" + e);
                // return false;
            }
//###########################################################################################################################3
            if (!(nameOfUser.equals("Not_OK")) && nameOfUser != null)
            {

                try
                {

                    Log.i("TM", "REQ TO CURRENT POSIITON  ");

//                    String url = "http://103.241.181.36:8080/FleetAndrProject/CurrentPosition?typevalue="
//                    String url = "http://192.168.2.26:8080/AndrFleetApp1/CurrentPosition?typevalue="
//                    String url = "http://192.168.2.26:8080/FleetAndrProject/CurrentPosition?typevalue="

                    Log.i("TM"," TYPE OF USER "+typeuser);

                    Log.i("TM"," TYPE OF USER "+typeuser[0]+" TYPE OG USER 2"+typeuser[1]);

                    String url = "http://103.241.181.36:8080/AndrFleetApp3/CurrentPosition?typevalue="
                            +typeuser[0]+"&TypeofUser="+typeuser[1]+"&username="+username;
                    url = url.replaceAll(" ", "%20");
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader
                            (
                                    new InputStreamReader(content));

                    Log.i("TM", " RESOPNSE FROM SERVLET ");

                    String s = "";
                    while ((s = buffer.readLine()) != null)
                    {
                        response += s;
                    }
                    Log.i("TM", "RESPONSE FROM CP SERVLET " + response);
                    System.out.println("The response =>" + response);
//                            Thread.sleep(2000);

                }
                catch (Exception e)
                {
                    System.out.println("Exception occured!!" + e);
                    return false;
                }

                if (!(nameOfUser.equals("No_Data")))
                {
                    Log.i("TM", "RESPONSE IS EQUAL TO DATA");

                    try {
                        int count = 0;
                        String[] str = response.split(" # ");
                        while (count < str.length)
                        {
                            String line = str[count];
                            String[] rowsnew = line.split("\\$");
                            publishProgress(rowsnew);
                            count++;

                            Log.i("TM", " RESPONSE " + rowsnew);
                            Log.i("TM", "RESULT" + rowsnew);
                            Log.i("TM", "RESULT 1" +nameOfUser);


                            USERNAME=nameOfUser.split("#");


                        }
                    }
                    catch (Exception e)
                    {

                        e.printStackTrace();


                    }
                    return true;
                } else
                {

                    return false;


                }

            }
            else
            {
                return false;

            }
        }

        @Override
        protected void onProgressUpdate(String... values)
        {
            super.onProgressUpdate(values);


            progressDialog.dismiss();


            vcode = values[0];
            vnum = values[1];
            status = values[2];

            numberOnly = vcode.replaceAll("[^0-9]", "");

            Log.i("TM"," VCODE "+vcode+" VNUM "+vnum+" status "+status);
            new RouteFinderReq().execute();

        }


    }

    class RouteFinderReq extends AsyncTask<String,String,String>
    {


        @Override
        protected String doInBackground(String... params)
        {
            int i = 0;

            String response= "";
            try
            {

                String url = "http://103.241.181.36:8080/AndrFleetApp3/RouteFinder?user="
                        +username;
                i++;
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

                Log.i("TM", "RESPONSE FROM ROUTE FINDER SERVLET " + response);

                Log.i("TM"," ROUTE FINDER  "+response);
                // System.out.println("The response =>" + response);

//                    Thread.sleep(2000);
            } catch (Exception e)
            {
                System.out.println("Exception occured!!" + e);
            }
            if (!(response.equals("No_Data"))) {

                try
                {

                    String[] rows = response.split("\\$");
                    publishProgress(rows);
                    Log.i(" TM "," RESPONSE  "+rows);

                    markerArray=rows;

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
            else
            {


            }
            return response;
        }

        @Override
        protected void onPostExecute(String s)
        {

            super.onPostExecute(s);
            new CurrentPositonRequest().execute();

        }
    }
    class CurrentPositonRequest extends AsyncTask<String,String,String>
    {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog=new ProgressDialog(Safe2SchoolActivity.this);
            progressDialog.setMessage(" Please Wait...");
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... params)
        {
            int i = 0;

            String response= "";
            try
            {

                Log.i("TM"," VNUM "+vnum);

                String url = "http://103.241.181.36:8080/AndrFleetApp3/OnlineData?vehiclecode="
                        +numberOnly;
                i++;
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
                    Log.i("TM"," VNUM "+response);


                }
 }
            catch (Exception e)
            {
                System.out.println("Exception occured!!" + e);
            }
            if (!(response.equals("No_Data")))
            {
                try
                {

                    String[] rows = response.split("\\$");
                    publishProgress(rows);
                    Log.i("TM"," VNUM "+rows);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {



            }

            Log.i("TM"," VNUM "+response);

            return response;
        }

        @Override
        protected void onProgressUpdate(String... values)
        {
            super.onProgressUpdate(values);

            Log.i("TM"," VNUM "+values);

            date = values[0];
            time = values[1];
            lat = values[2];
            log = values[3];
            location = values[4];
            speed = values[5];

            Log.i("TM"," VNUM "+date+" lat "+lat+" lng "+log+" location "+location+" "+speed);

        }
        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            progressDialog.dismiss();

            Intent intent2=new Intent(Safe2SchoolActivity.this,MapSafe2School.class);
            intent2.putExtra("LAT",lat);
            intent2.putExtra("LONG",log);
            intent2.putExtra("DATE",date);
            intent2.putExtra("TIME",time);
            intent2.putExtra("LOCATION",location);
            intent2.putExtra("VNUM",vnum);
            intent2.putExtra("SPEED",speed);
            intent2.putExtra("VCODE",vcode);
            intent2.putExtra("UNAME",username);
            intent2.putExtra("SPEED",speed);
            intent2.putExtra("POINTS",markerArray);


            Log.i("TM"," VNUM "+lat+" "+log+" "+date+" "+speed);

            startActivity(intent2);



        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuItem menuItem;

        menuItem=menu.add(0,1,1,"LOG OUT");

        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==1)
        {

            Intent intent=new Intent(getApplicationContext(),LoginForm.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            LoginForm.arrayList.clear();
            finish();
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

}
