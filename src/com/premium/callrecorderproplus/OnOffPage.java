package com.premium.callrecorderproplus;

import java.util.Locale;


import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OnOffPage extends Activity {
	Button onOff;
	static public final String PREF_RECORD_CALLS = "PREF_RECORD_CALLS";
	TextView tv_line1, tv_line2,completeText;
	String text_on=null, text_off, text_on_secondLine, text_off_secondLine;
	String language_check;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width=dm.widthPixels;
		int height=dm.heightPixels;
		int dens=dm.densityDpi;
		double wi=(double)width/(double)dens;
		double hi=(double)height/(double)dens;
		double x = Math.pow(wi,2);
		double y = Math.pow(hi,2);
		double screenInches = Math.sqrt(x+y);
		
		Log.e("width and height", "- "+width+height+ "Screen inches is " + screenInches);
		if(width >400 && width <620 && height > 700 && height <900)
		{
			setContentView(R.layout.activity_on_off_page);
		}
		
		else if(width >620 && width <820 && height > 1000 && height <1300 && screenInches<5)
		{
			setContentView(R.layout.activity_on_off_page_hdpi);
			
		}else if(width >820 && width <1180 && height > 1820 && height <2000 && screenInches<5.9)
		{
			setContentView(R.layout.activity_on_off_page_xhdpi);
			
		}
		else if(screenInches > 6)
		{
			 setContentView(R.layout.layout_on_off_xxhdpi);
				
			
		}else if(screenInches <6 && screenInches >5)
		{
			setContentView(R.layout.activity_on_off_page_xhdpi);
			
		}
		else if(screenInches <5)
		{
			setContentView(R.layout.activity_on_off_page_hdpi);
		}
	
        		Context c = getApplicationContext();
		tv_line1 = (TextView) findViewById(R.id.on_text);
		language_check=Locale.getDefault().getDisplayLanguage();
		System.out.println("the language is "+ language_check);
		tv_line2 = (TextView) findViewById(R.id.on_second_line);
		completeText=(TextView) findViewById(R.id.complete_text);
		text_on =getResources().getString(R.string.turnon);
		text_on_secondLine =getResources().getString(R.string.turnsecond);
		text_off_secondLine=getResources().getString(R.string.turnoff);
		text_off =getResources().getString(R.string.textoff);

		final Context context = getApplicationContext();
		// context.stopService(new Intent(context,RecordService.class));
		onOff = (Button) findViewById(R.id.on_off);
		SharedPreferences prefs;
		prefs = context.getSharedPreferences("com.premium.callrecorderproplus",
				Context.MODE_WORLD_WRITEABLE);
		if (!prefs.getBoolean(Preferences.PREF_RECORD_CALLS, false)) {

//			tv_line1.setText(text_off);
//			tv_line2.setText(text_off_secondLine);
//

//			completeText.setText("  ");
//			
//			tv_line2.setText("  ");
			completeText.setText(text_on+" "+text_on_secondLine);
			onOff.setBackgroundResource(R.drawable.off_button);
			prefs.edit().putBoolean(Preferences.PREF_RECORD_CALLS, false)
					.commit();

			boolean checking = prefs.getBoolean(Preferences.PREF_RECORD_CALLS,
					false);
			Log.e("are we checkin in onOff class", "cheking  " + checking);
		} else {
//			tv_line1.setText(text_on);
//			tv_line2.setText(text_on_secondLine);
//			completeText.setText("  ");
			completeText.setText(text_off+" "+text_off_secondLine);
			prefs.edit().putBoolean(Preferences.PREF_RECORD_CALLS, true)
					.commit();
			onOff.setBackgroundResource(R.drawable.on_button);
			boolean checking = prefs.getBoolean(Preferences.PREF_RECORD_CALLS,
					false);
			Log.e("are we checkin in onOff class", "cheking  " + checking);

		}

		onOff.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Context context = getApplicationContext();
				// context.stopService(new Intent(context,
				// RecordService.class));
				SharedPreferences prefs;
				prefs = context.getSharedPreferences(
						"com.premium.callrecorderproplus",
						Context.MODE_WORLD_WRITEABLE);
				if (prefs.getBoolean(Preferences.PREF_RECORD_CALLS, false)) {

//					tv_line1.setText(text_off);
//					tv_line2.setText(text_off_secondLine);
					completeText.setText("  ");
//					if (tv_line1.getLineCount()>1)
//					{
						
//						tv_line1.setText("  ");
//						completeText.setText("  ");
						
//						tv_line2.setText("  ");
						completeText.setText(text_on+" "+text_on_secondLine);
//					}

					onOff.setBackgroundResource(R.drawable.off_button);
					prefs.edit()
							.putBoolean(Preferences.PREF_RECORD_CALLS, false)
							.commit();

					boolean checking = prefs.getBoolean(
							Preferences.PREF_RECORD_CALLS, false);
					Log.e("are we checking in onOff page", "cheking  "
							+ checking);
			} else {
//					tv_line1.setText(text_on);
//					tv_line2.setText(text_on_secondLine);
					completeText.setText("  ");
//					System.out.println(tv_line1.getLineCount()+ " onClick of button"+ tv_line1.getWidth());
//					if (tv_line1.getLineCount()>1)
//					{
//						tv_line1.setText("  ");
//
//						tv_line2.setText("  ");

//						completeText.setText("  ");
						completeText.setText(text_off+" "+text_off_secondLine);
//					}
					prefs.edit()
							.putBoolean(Preferences.PREF_RECORD_CALLS, true)
							.commit();
					onOff.setBackgroundResource(R.drawable.on_button);
					boolean checking = prefs.getBoolean(
							Preferences.PREF_RECORD_CALLS, false);
					Log.e("are we checking in onOff page", "cheking  "
							+ checking);

				}
			}
		});
		
		
		
		
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
}
	

}
