package com.premium.callrecorderproplus;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SelectorActivity extends Activity {
	Button play, delete, share;
	static int position_list;
	String [] dlist ;
	static public String position,temp;
public static Uri uri ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_selector);
		
		Bundle extras = getIntent().getExtras();
		position_list = extras.getInt("list_position");

		position = String.valueOf(position_list);
		dlist = extras.getStringArray("list");
		if(dlist==null)
		{
			Log.e("list is", "null");
		}else
		{
			Log.e("list is ","   "+ dlist[0]);
				
		}
		Log.e("List positoni is  :", position);
		play = (Button) findViewById(R.id.play);
		play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File f = new File(RecordService.DEFAULT_STORAGE_LOCATION + "/"
						+ dlist[position_list].toString());
				// if(CallLog.actionBarThread.is)

				if (f != null) {
					Intent viewIntent = new Intent(Intent.ACTION_VIEW);
					viewIntent.setDataAndType(Uri.fromFile(f), "audio/*");
					startActivity(Intent.createChooser(viewIntent, null));
				}
			}
		});
		delete = (Button) findViewById(R.id.delete);
		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File f = new File(RecordService.DEFAULT_STORAGE_LOCATION + "/"
						+ dlist[position_list].toString());

				if (f!= null) {
					try {

						boolean delete = f.delete();
						Toast.makeText(getApplicationContext(), dlist[position_list].toString() + "Has been deleted", Toast.LENGTH_SHORT).show();
					} catch (NullPointerException n) {

						
					}
					Intent in=new Intent(getApplicationContext(),CallLog.class);
					startActivity(in);
					finish();

				}
			}
		});
		share =(Button) findViewById(R.id.share);
		share.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/callrecorder"+ "/"
						+ dlist[position_list].toString());
				
				
				if (f != null) {
					Intent viewIntent = new Intent(Intent.ACTION_SEND);
					viewIntent.setType("audio/*");
					/* viewIntent.setType("message/rfc822");*/
					uri = Uri.parse("file://" +f.toString());
					viewIntent.putExtra(Intent.EXTRA_STREAM, uri);
					viewIntent.putExtra("subject", "Recording:" + dlist[position_list].toString());
					viewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(Intent.createChooser(viewIntent, null));
			}
				Intent i = new Intent(SelectorActivity.this, CallLog.class);
				startActivity(i);
				finish();
			}
		});
	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selector, menu);
		return true;
	}

}

