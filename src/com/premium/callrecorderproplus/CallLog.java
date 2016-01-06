package com.premium.callrecorderproplus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;

/*
 Ok, so, in theory this should work.  However, the behavior of the 
 android.widget.MediaController leaves something to be desired.
 Will have to see if we can get this figured out.
 */

public class CallLog extends Activity {
	private final String TAG = "CallRecorder";
	
	private MyAdapter adapter = null;
	private ListView fileList = null;
	private ArrayAdapter<String> fAdapter = null;
	private ArrayList<String> recordingNames = null;
	private MediaController controller = null;
	ImageButton delete,share;

	/*
	 * private void loadRecordingsFromProvider() {
	 * 
	 * fAdapter.clear(); ContentResolver cr = getContentResolver(); Cursor c =
	 * cr.query(RecordingProvider.CONTENT_URI, null, null, null, null); String[]
	 * names = new String[c.getCount()]; int i = 0;
	 * 
	 * if (c.moveToFirst()) { do { // Extract the recording names
	 * fAdapter.add(c.getString(RecordingProvider.DETAILS_COLUMN)); i++; } while
	 * (c.moveToNext()); }
	 * 
	 * fAdapter.notifyDataSetChanged(); }
	 */

	private void loadRecordingsFromDir() {

		int i=0;
		File dir = new File(RecordService.DEFAULT_STORAGE_LOCATION);
		String[] dlist = dir.list();
		int playPause = R.drawable.play;
		if (dir.list() == null) {
			Log.e("heell", "Probblemm file is null");
		}else {
			
		}
		adapter = new MyAdapter(dlist, playPause, CallLog.this);

	}

	/*private class CallItemClickListener implements
			AdapterView.OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			CharSequence s = (CharSequence) parent.getItemAtPosition(position);
			Log.w(TAG, "CallLog just got an item clicked: " + s);
			File f = new File(RecordService.DEFAULT_STORAGE_LOCATION + "/"
					+ s.toString());

			if (f != null) {
				Intent viewIntent = new Intent(Intent.ACTION_VIEW);
				viewIntent.setDataAndType(Uri.fromFile(f), "audio/*");
				startActivity(Intent.createChooser(viewIntent, null));
			}
		}
	}*/
	
	/*public void playFile()
	{
		CharSequence s = (CharSequence) parent.getItemAtPosition(position);
		Log.w(TAG, "CallLog just got an item clicked: " + s);
		File f = new File(RecordService.DEFAULT_STORAGE_LOCATION + "/"
				+ s.toString());

		if (f != null) {
			Intent viewIntent = new Intent(Intent.ACTION_VIEW);
			viewIntent.setDataAndType(Uri.fromFile(f), "audio/*");
			startActivity(Intent.createChooser(viewIntent, null));
	}*/

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.call_log);
       
		// recordingNames. = new String[0];
		fileList = (ListView) findViewById(R.id.play_file_list);

		Context context = getApplicationContext();
		// if(controller != null)

		loadRecordingsFromDir();

		System.out.print("Calling files to load");

		try {
			fileList.setAdapter(adapter);
		} catch (NullPointerException e) {
			Log.e("Error", "NULL pointer Exeception");
		}
		adapter.notifyDataSetChanged();
	//	fileList.setOnItemClickListener(new CallItemClickListener());
		
		
	}

	/*
	 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
	 * arg2, long arg3) { // TODO Auto-generated method stub Log.e("Error",
	 * "NULL pointer Exeception"); System.out.println("Helll youuuuu"); }
	 */

	public void onStart() {
		super.onStart();
		Log.i(TAG, "CallLog onStart");
	}

	public void onRestart() {
		super.onRestart();
		Log.i(TAG, "CallLog onRestart");

	}

	public void onResume() {
		super.onResume();
		Log.i(TAG,
				"CallLog onResume about to load recording list again, does this work?");
		try {
			if (controller != null)
				loadRecordingsFromDir();

			fileList.setAdapter(adapter);

		} catch (NullPointerException e) {
			Log.e("Error", "NULL pointer Exeception");
		}
	}

	/*
	 * private void playFile(String fName) { Log.i(TAG, "playFile: " + fName);
	 * 
	 * fileList = (ListView) findViewById(R.id.play_file_list); Context context
	 * = getApplicationContext(); Intent playIntent = new Intent(context,
	 * PlayService.class); playIntent.putExtra(PlayService.EXTRA_FILENAME,
	 * RecordService.DEFAULT_STORAGE_LOCATION + "/" + fName); ComponentName name
	 * = context.startService(playIntent); if (null == name) { Log.w(TAG,
	 * "CallLog unable to start PlayService with intent: " +
	 * playIntent.toString()); } else { Log.i(TAG, "CallLog started service: " +
	 * name); } }
	 */

	/*public void onDestroy() {
		Context context = getApplicationContext();
		Intent playIntent = new Intent(context, PlayService.class);
		context.stopService(playIntent);

		super.onDestroy();
	
	}*/
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
}
}