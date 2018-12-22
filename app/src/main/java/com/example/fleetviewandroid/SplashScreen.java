package com.example.fleetviewandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by sushant on 13/3/17.
 */
public class SplashScreen extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent=new Intent(getApplicationContext(),LoginForm.class);
        startActivity(intent);
        finish();

    }
}
