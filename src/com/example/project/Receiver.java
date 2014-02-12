package com.example.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;



public class Receiver extends BroadcastReceiver {

  final String LOG_TAG = "myLogs";
  

  @Override
  public void onReceive(Context ctx, Intent intent) {
	  
    Log.d(LOG_TAG, "onReceive");
    Log.d(LOG_TAG, "action = " + intent.getAction());
    Log.d(LOG_TAG, "extra = " + intent.getStringExtra("extra"));
    
    String tmp = intent.getAction();
    
    if (tmp.equals("action 1")) {
    	Toast.makeText(ctx, "—работал одноразовый будильник є 1", Toast.LENGTH_LONG).show();
    	
   
    	
	}   
    
    if (tmp.equals("action 2")) {
    	Toast.makeText(ctx, "—работал повтор€ющийс€ будильник є 2", Toast.LENGTH_LONG).show();
	}
    
    // вызываем активити в топ поверх всего
//    Intent intn = new Intent (ctx, MainActivity.class);
//    intn.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
//    ctx.startActivity (intn);
  }
}