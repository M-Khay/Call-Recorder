package com.premium.callrecorderproplus;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.TelephonyManager;
import android.telephony.PhoneStateListener;
import android.util.Log;
import android.widget.Toast;

public class CallBroadcastReceiver extends BroadcastReceiver
{
	String incomingNumber,callerName,outgoingNumber;
    public void onReceive(Context context, Intent intent) {
        Log.d("CallRecorder", "CallBroadcastReceiver::onReceive got Intent: " + intent.toString());
        
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.d(", call to: " , outgoingNumber);
          // incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
           
    	 callerName = getContactNameFromNumber(context, outgoingNumber);
            Log.d("CallRecorder", "CallBroadcastReceiver intent has EXTRA_PHONE_NUMBER: " + outgoingNumber);
            String messager;
            if(callerName != null){
		    	  messager =  callerName ;

			      RecordService.message = messager;  
            }else{
            	messager=outgoingNumber;
			      RecordService.message = messager;
            }
		       }

        PhoneListener phoneListener = new PhoneListener(context);
        TelephonyManager telephony = (TelephonyManager)
            context.getSystemService(Context.TELEPHONY_SERVICE);
        
        telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        Log.d("PhoneStateReceiver::onReceive", "set PhoneStateListener");
       if(telephony.getCallState()==TelephonyManager.CALL_STATE_IDLE)
			
		{
			/*ourSong1.stop();
			try {
				ourSong1.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//context.startService(serviceIntent);
*/			
		}
		else if(telephony.getCallState()==TelephonyManager.CALL_STATE_RINGING)
		{
			   incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		    	 callerName = getContactNameFromNumber(context, incomingNumber);
		    	 
		        
			Log.i("AutoAnswerReceiver", "RINGING, number: " + incomingNumber);
		    Log.i("AutoAnswerReceiver", "RINGING, name: " + callerName);
		    String messager = null;
		    String edtexta;
		    String edtextb;
		     SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(context);
		      edtexta = pref2.getString("welcome_messagea", "");
		      edtextb = "Date";//pref2.getString("welcome_messageb", "");
		      Log.i("AutoAnswerReceiver", "CHECKINGb, messageb: " + edtextb);
		      
		      
		      if (incomingNumber != null){
		    	  StringBuffer sb = new StringBuffer();
		    	  for(int i = 0; i < incomingNumber.length(); i++){
		    		  sb = sb.append(""+ incomingNumber.charAt(i));
		    		  
		    	  }
		    	 // message = callerName + " " + edtextb;
		    	 messager = edtexta + " " + sb;
		    	 }
		      
		    	 
		      //context.startService(serviceIntent);
		      
		      if(callerName != null){
		    	  messager = edtexta + " " + callerName;
		      }
		      
		      RecordService.message = messager;
		      Log.i("AutoAnswerReceiver", "RINGING, messager: " + messager);
		      Log.i("AutoAnswerReceiver", "CHECKING, messagea: " + edtexta);
		      context.startService(new Intent(context, RecordService.class));
			//Toast.makeText(context, "Call Ringing", Toast.LENGTH_LONG).show();
		}
		else if(telephony.getCallState()==TelephonyManager.CALL_STATE_OFFHOOK)
		{
			
			
		}
	}

	private String getContactNameFromNumber(Context context, String number) {
		String name = null;

		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(number));
		Cursor cursor = context.getContentResolver().query(uri,
				new String[] { PhoneLookup.DISPLAY_NAME }, null, null, null);

		if (cursor.moveToFirst()) {
			name = cursor.getString(cursor
					.getColumnIndex(PhoneLookup.DISPLAY_NAME));
		}
		

		return name;
	}
	
    }

