package com.example.fleetviewandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapSafe2School extends Activity implements OnMapReadyCallback
{

    GoogleMap mMap;

    String vlat,vlng,vdate,vtime,vlocation,vnum,vspeed;

    String[] routefinder_response;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_safe2_school);

        Intent intent1=getIntent();

        vlat=intent1.getStringExtra("LAT");
        vlng=intent1.getStringExtra("LONG");
        vdate=intent1.getStringExtra("DATE");
        vtime=intent1.getStringExtra("TIME");
        vlocation=intent1.getStringExtra("LOCATION");
        vnum=intent1.getStringExtra("VNUM");
        vspeed=intent1.getStringExtra("SPEED");

        routefinder_response=intent1.getStringArrayExtra("POINTS");


        MapFragment mapFragment= (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        mMap = googleMap;
        LatLng latLng1 = new LatLng(Double.parseDouble(vlat),Double.parseDouble(vlng));
        MarkerOptions options2 = new MarkerOptions();
        options2.title("" + vnum +"" + vdate +""+ vtime)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_marker));
        options2.position(latLng1);
        options2.snippet("Location:" + vlocation);

        mMap.addMarker(options2);

        Log.i("FV"," IN THE ON MAP READY ");

        int arraylength=routefinder_response.length;

        for (int i=0;i<arraylength;i+=3)
        {

            Log.i("FV"," ARRAY LENGTH "+routefinder_response[i]);
            Log.i("FV"," ARRAY LENGTH "+arraylength);

            LatLng ponit1 = new LatLng(Double.parseDouble(routefinder_response[i]), Double.parseDouble(routefinder_response[i + 1]));
            MarkerOptions options3 = new MarkerOptions();
            options3.title(routefinder_response[i + 2]);
            options3.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ponit1, 12));
            options3.position(ponit1);

//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ponit1,12));
            mMap.addMarker(options3);

            if(i<arraylength-3)
            {

                LatLng point2 = new LatLng(Double.parseDouble(routefinder_response[i+3]), Double.parseDouble(routefinder_response[i + 4]));
                MarkerOptions options6 = new MarkerOptions();
                options6.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                options6.title(routefinder_response[i + 5]);
                options6.position(point2);
                mMap.addMarker(options6);

                Log.i("FV"," GO TO THE DOWNLOAD TASK.... ");

                String url13 = getDirectionsUrl(ponit1, point2);
                DowwnloadTask dowwnloadTask13 = new DowwnloadTask();
                dowwnloadTask13.execute(url13);


            }
        }

    }
    class  DowwnloadTask extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... params)
        {

            Log.i("FV"," IN THE DOWNLOAD TASK ");

            String data = "";

            try
            {
                data = downloadUrl(params[0]);
            }
            catch (Exception e)
            {
                Log.d("Background Task", e.toString());
            }
            return data;

        }
        @Override
        protected void onPostExecute(String s)
        {

            MyTask myTask=new MyTask();

            myTask.execute(s);

            super.onPostExecute(s);
        }
    }

    class MyTask extends AsyncTask<Object, Object, List<List<HashMap<String, String>>>>
    {


        @Override
        protected List<List<HashMap<String, String>>> doInBackground(Object... params)
        {

            Log.i("FV"," IN THE MY TASK.... ");

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try
            {
                jObject = new JSONObject((String) params[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes=parser.parse(jObject);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return routes;

        }
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists)
        {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            Log.i("TM","lists"+lists);
            Log.i("TM","lists"+lists);

            for (int i = 0; i < lists.size(); i++)
            {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = lists.get(i);

                // Log.i("TM","PATH"+path);

                for (int j = 0; j < path.size(); j++)
                {
                    HashMap point = path.get(j);

                    double lat = Double.parseDouble((String) point.get("lat"));
                    double lng = Double.parseDouble((String) point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route


            if (lineOptions!=null)
            {

                mMap.addPolyline(lineOptions);
                super.onPostExecute(lists);
            }
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest)
    {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }


    private String downloadUrl(String strUrl) throws IOException
    {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        try
        {

            Log.i("FV"," DOWNLOAD URL ");


            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        }
        catch (Exception e)
        {
            Log.d("Exception", e.toString());
        }
        finally
        {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

}
