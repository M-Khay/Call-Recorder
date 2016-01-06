package com.premium.callrecorderproplus;

import java.io.File;
import java.io.IOException;
import java.lang.Exception;
import java.util.Date;
import java.text.SimpleDateFormat;

import android.os.Environment;
import android.os.IBinder;
import android.app.Service;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.widget.Toast;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;

//import java.security.KeyPairGenerator;
//import java.security.KeyPair;
//import java.security.Key;

import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Iterator;

public class RecordService extends Service implements
		MediaRecorder.OnInfoListener, MediaRecorder.OnErrorListener {
	private static final String TAG = "CallRecorder";
	public static final String DEFAULT_STORAGE_LOCATION = "/sdcard/callrecorder";
	public static final String INTERNAL_STORAGE_LOCATION = "/media/callrecorder";
	private static final int RECORDING_NOTIFICATION_ID = 1;
	public static String message;
	private MediaRecorder recorder = null;
	private boolean isRecording = false;
	private File recording = null;;

	private File makeOutputFile(SharedPreferences prefs) {
		File dir = new File(DEFAULT_STORAGE_LOCATION);
		
		if (!dir.exists()) {
			try {
				dir.mkdirs();
			} catch (Exception e) {
				Log.e("CallRecorder",
						"RecordService::makeOutputFile unable to create directory "
								+ dir + ": " + e);
				Toast t = Toast.makeText(getApplicationContext(),
						"CallRecorder was unable to create the directory "
								+ dir + " to store recordings: " + e,
						Toast.LENGTH_LONG);
			/*	dir = new File (INTERNAL_STORAGE_LOCATION);
				if (!dir.exists()) {
					try {
						dir.mkdirs();
					} catch (Exception ee) {
						Log.e("CallRecorder",
								"RecordService::makeOutputFile unable to create directory  in internal storage"
										+ dir + ": " + ee);
						Toast tt = Toast.makeText(getApplicationContext(),
								"internal storgae problem"
										+ dir + " to store recordings: " + ee,
								Toast.LENGTH_LONG);
				t.show();
				
				return null;
					
			}
		}*/
			}
		}else {
			if (!dir.canWrite()) {
				Log.e(TAG,
						"RecordService::makeOutputFile does not have write permission for directory: "
								+ dir);
				Toast t2 = Toast.makeText(getApplicationContext(),
						"CallRecorder does not have write permission for the directory directory "
								+ dir + " to store recordings",
						Toast.LENGTH_LONG);
				t2.show();
				return null;
			}
		}

		Date today_date = new Date();
		String date =  DateFormat.format("yyyy-mm-dd", new Date()).toString();
		String prefix = "Date: " + date;
		
		SharedPreferences prefs2;
		Context c2 = getApplicationContext();
		prefs2 = c2.getSharedPreferences("com.premium.callrecorderproplus",
				Context.MODE_WORLD_WRITEABLE);

		int audiosource = prefs2.getInt(Preferences.PREF_AUDIO_SOURCE, 1);
		String contact = prefs2.getString (Preferences.CALLER_CONTACT,"No Number");
		prefix += "\n \n"+"contact -"+contact;
		String suffix ="\nDate-" ;

		int formatType = prefs2.getInt(Preferences.PREF_AUDIO_FORMAT, 1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd \t HH:mm:ss");
		Date date2 = new Date();
		suffix +=dateFormat.format(date2).toString();
		Log.d("data is", ""+suffix);
		 
/*		switch (formatType) {
		case MediaRecorder.OutputFormat.THREE_GPP:
			suffix = ".3gpp";
			break;
		case MediaRecorder.OutputFormat.MPEG_4:
			suffix = ".mpg";
			break;
		case MediaRecorder.OutputFormat.RAW_AMR:
			suffix = ".amr";
			break;
		}*/

	
			//File f=File.createTempFile(prefix, suffix,dir);// parameter ,dir has been remobved from here
			//File ff= File.createTempFile(prefix, suffix);
			//Log.e("file name", prefix+suffix+"fff");
			 /*File fi= new File(dir, prefix+suffix+"fff");
			
			 f.toString().replace("32202694", "  ");
		f.renameTo(fi);
		 */
			System.out.println("message: "+message);
			File file=new File(DEFAULT_STORAGE_LOCATION, message+suffix);
	//	f.renameTo(file);	
			
			 return file;
		
			
			}

	public void onCreate() {
		super.onCreate();

		recorder = new MediaRecorder();
		
	}

	public void onStart(Intent intent, int startId) {

		if (isRecording) {
			return;
		}

		SharedPreferences prefs2;
		Context c2 = getApplicationContext();

		prefs2 = c2.getSharedPreferences("com.premium.callrecorderproplus",
				Context.MODE_WORLD_WRITEABLE);

		Boolean shouldRecord = prefs2.getBoolean(Preferences.PREF_RECORD_CALLS,
				true);
		Log.e("checkingfdsdf", "fdsg if it should record " + shouldRecord);
		Context c = getApplicationContext();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(c);
		if (!shouldRecord) {
			Log.e("CallRecord",
					"RecordService::onStartCommand with PREF_RECORD_CALLS false, not recording");
			return;
		}

		prefs2 = c2.getSharedPreferences("com.premium.callrecorderbeta",
				Context.MODE_WORLD_WRITEABLE);

		int audioformat = prefs2.getInt(Preferences.PREF_AUDIO_FORMAT, 1);
		int audiosource = prefs2.getInt(Preferences.PREF_AUDIO_SOURCE, 1);

		recording = makeOutputFile(prefs);
		if (recording == null) {
			recorder = null;
			return; // return 0;
		}

		try {

			if (recorder == null)
				recorder = new MediaRecorder();
		
		Toast t = Toast.makeText(getApplicationContext(),
				"CallRecorder was recorder",
				Toast.LENGTH_LONG);
			recorder.reset();
			recorder.setAudioSource(audiosource);
			Log.d("CallRecorder", "set audiosource " + audiosource);
			recorder.setOutputFormat(audioformat);
			Log.d("CallRecorder", "set output " + audioformat);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			Log.d("CallRecorder", "set encoder default");
			recorder.setOutputFile(recording.getAbsolutePath());
			Log.d("CallRecorder", "set file: " + recording);

			recorder.setOnInfoListener(this);
			recorder.setOnErrorListener(this);

			try {
				recorder.prepare();
			} catch (java.io.IOException e) {
				Log.e("CallRecorder",
						"RecordService::onStart() IOException attempting recorder.prepare()\n");
				Toast t2 = Toast.makeText(getApplicationContext(),
						"CallRecorder was unable to start recording: " + e,
						Toast.LENGTH_LONG);
				t2.show();
				recorder = null;
				return; // return 0; //START_STICKY;
			}

			try {

				recorder.start();
			} catch (RuntimeException r) {
				Log.d("runtimeerror with recorder", " " + r);
				recorder.release();
				recorder.reset();
				recorder.prepare();
				recorder.start();
			}

			isRecording = true;
			updateNotification(true);
		} catch (java.lang.Exception e) {
			Toast t = Toast.makeText(getApplicationContext(),
					"CallRecorder was unable to start recording: " + e,
					Toast.LENGTH_LONG);
			t.show();

			Log.e("CallRecorder",
					"RecordService::onStart caught unexpected exception", e);
			recorder = null;
		}

		return; // return 0; //return START_STICKY;
	}

	public void onDestroy() {
		super.onDestroy();

		if (null != recorder) {
			Log.i("CallRecorder",
					"RecordService::onDestroy calling recorder.release()");
			
			isRecording = false;
			recorder.release();
			

		}

		updateNotification(false);
	}

	public IBinder onBind(Intent intent) {
		return null;
	}

	public boolean onUnbind(Intent intent) {
		return false;
	}

	public void onRebind(Intent intent) {
	}

	private void updateNotification(Boolean status) {
		Context c = getApplicationContext();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(c);
		SharedPreferences prefs2;
		prefs2 = c.getSharedPreferences("com.premium.callrecorderbeta",
				Context.MODE_WORLD_WRITEABLE);
		int checking = prefs2.getInt(Preferences.PREF_NOTIFICATION, 100);

		int notification_check = prefs2
				.getInt(Preferences.PREF_NOTIFICATION, 1);
		Log.e("checking fr prefrencse", "cheking  " + checking + "    "
				+ notification_check);
		if (notification_check == 1) {
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

			if (status) {
				int icon = R.drawable.rec;
				CharSequence tickerText = "Recording call from channel "
						+ prefs.getString(Preferences.PREF_AUDIO_SOURCE, "1");
				long when = System.currentTimeMillis();

				Notification notification = new Notification(icon, tickerText,
						when);

				Context context = getApplicationContext();
				CharSequence contentTitle = "CallRecorder Status";
				CharSequence contentText = "Recording call from channel...";
				Intent notificationIntent = new Intent(this,
						RecordService.class);
				PendingIntent contentIntent = PendingIntent.getActivity(this,
						0, notificationIntent, 0);

				notification.setLatestEventInfo(context, contentTitle,
						contentText, contentIntent);
				mNotificationManager.notify(RECORDING_NOTIFICATION_ID,
						notification);
			} else {
				mNotificationManager.cancel(RECORDING_NOTIFICATION_ID);
			}
		}
	}

	// MediaRecorder.OnInfoListener
	public void onInfo(MediaRecorder mr, int what, int extra) {
		Log.i("CallRecorder",
				"RecordService got MediaRecorder onInfo callback with what: "
						+ what + " extra: " + extra);
		isRecording = false;
	}

	// MediaRecorder.OnErrorListener
	public void onError(MediaRecorder mr, int what, int extra) {
		Log.e("CallRecorder",
				"RecordService got MediaRecorder onError callback with what: "
						+ what + " extra: " + extra);
		isRecording = false;
		mr.release();
	}
}
