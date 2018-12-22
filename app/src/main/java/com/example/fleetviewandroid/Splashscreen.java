package com.example.fleetviewandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splashscreen extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginact);
		  

        Thread logoTimer = new Thread() 
        {
            @SuppressLint("NewApi")
			public void run()
            {
                try
                {
                    int logoTimer = 0;
                    while(logoTimer < 5000)
                    {
                        sleep(100);
                        logoTimer = logoTimer +100;
                    };
                    
                }catch(Exception e)
                {
                	
                	
                }
            }
        };
        logoTimer.start();
        Intent i=new Intent(this,LoginForm.class);
        startActivity(i);
        
    }
}