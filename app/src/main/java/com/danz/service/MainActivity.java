package com.danz.service;
 
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity { 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StaticActivity.Start(this);
        
    }
	
} 
