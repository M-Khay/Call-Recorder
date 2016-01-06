package com.premium.callrecorderproplus;

import android.content.Intent;
import android.content.Context;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.telephony.PhoneStateListener;
import android.util.Log;

public class PhoneListener extends PhoneStateListener
{
	public static String  callerContactNumber;
    private Context context;
  static public  String incomingNumber;
    public PhoneListener(Context c) {
        Log.i("CallRecorder", "PhoneListener constructor");
        context = c;
    }

    public void onCallStateChanged (int state, String incomingNumber)
    {
    	callerContactNumber= incomingNumber;
//    	sendCallerNumber(incomingNumber);
    
        Log.d("this is i'm m llooking for",", incomingNumber"+incomingNumber);
        switch (state) {
        case TelephonyManager.CALL_STATE_IDLE:
            Log.d("CallRecorder", "CALL_STATE_IDLE, stoping recording");
            Boolean stopped = context.stopService(new Intent(context, RecordService.class));
            Log.i("CallRecorder", "stopService for RecordService returned " + stopped);
            break;
        case TelephonyManager.CALL_STATE_RINGING:
        	PhoneListener.callerContactNumber= incomingNumber;
        	SharedPreferences prefs2;

    		prefs2 = context.getSharedPreferences("com.premium.callrecorderbeta",
    				Context.MODE_WORLD_WRITEABLE);
    		prefs2.edit().putString(Preferences.CALLER_CONTACT, incomingNumber).commit();
    	String contact = prefs2.getString(Preferences.CALLER_CONTACT, "fuck me");
        	Log.d("CallRecorder", "CALL_STATE_RINGING  ="+ contact);
            break;
        case TelephonyManager.CALL_STATE_OFFHOOK:
            Log.d("CallRecorder", "CALL_STATE_OFFHOOK starting recording");
            Intent callIntent = new Intent(context, RecordService.class);
            Log.d("+++++CallRecorde++++++", incomingNumber);
            ComponentName name = context.startService(callIntent);
            if (null == name) {
                Log.e("CallRecorder", "startService for RecordService returned null ComponentName");
            } else {
                Log.i("CallRecorder", "startService returned " + name.flattenToString());
            }
            break;
        }
    }
}
