package com.premium.callrecorderproplus;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingsPage extends Activity {

	ListView list;
	List<RowItem> rowItems;

	RelativeLayout notification_layout;
	CheckBox notification;
	
	public static final Integer images[] = { R.drawable.im, R.drawable.im };
String Voice_UpLink,Down_Link,Mic,Three_gpp,Mpeg_4,Raw_Amr;
String AudioFileFormat,AudioChannels;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_settings_page);
		rowItems = new ArrayList<RowItem>();
		
		    Voice_UpLink=getResources().getString(R.string.voice);
		    Down_Link=getResources().getString(R.string.down);
		    Mic=getResources().getString(R.string.mic);
		    Three_gpp=getResources().getString(R.string.three);
		    Mpeg_4=getResources().getString(R.string.mpe);
		    Raw_Amr=getResources().getString(R.string.raw);
		    AudioFileFormat=getResources().getString(R.string.audio);
		    AudioChannels=getResources().getString(R.string.channel);
	
		    final String titles[] = { AudioFileFormat,
			AudioChannels };
		    for (int i = 0; i < titles.length; i++) {
			    RowItem item = new RowItem(images[i], titles[i]);
			    rowItems.add(item);
		    //String[] items = { "Audio File Format", "Recording Source" };
		//String[]images={R.drawable.};
		final Context context = this;
		list = (ListView) findViewById(R.id.list);
		CustomBaseAdapter adapter = new CustomBaseAdapter(this, rowItems);
	list.setAdapter(adapter);
		
		/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(),
				android.R.layout.simple_dropdown_item_1line, items);
		list.setAdapter(adapter);*/

		/*
		 * notification_layout = (RelativeLayout)
		 * findViewById(R.id.notification_layout);
		 * notification_layout.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Log.e("checking fr prefrencse", "cheking");
		 * 
		 * } });
		 */
		notification = (CheckBox) findViewById(R.id.notification);
		SharedPreferences prefs;
		prefs = context.getSharedPreferences("com.premium.callrecorderbeta",
				Context.MODE_WORLD_WRITEABLE);
		if (prefs.getInt(Preferences.PREF_NOTIFICATION, 1) == 1)
			notification.setChecked(true);
		else
			notification.setChecked(false);

		notification
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) { // TODO Auto-generated method
													// stub
						SharedPreferences prefs;
						prefs = context.getSharedPreferences(
								"com.premium.callrecorderbeta",
								Context.MODE_WORLD_WRITEABLE);
						if (prefs.getInt(Preferences.PREF_NOTIFICATION, 1) == 1) {
							prefs.edit()
									.putInt(Preferences.PREF_NOTIFICATION, 0)
									.commit();
							int checking = prefs.getInt(
									Preferences.PREF_NOTIFICATION, 100);
							Log.e("checking fr prefrencse", "cheking  "
									+ checking);
						} else {
							prefs.edit()
									.putInt(Preferences.PREF_NOTIFICATION, 1)
									.commit();
							int checking = prefs.getInt(
									Preferences.PREF_NOTIFICATION, 100);

						}
					}
				});

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int postition, long id) {
				// TODO Auto-generated method stub
				// custom dialog

				switch (postition) {
				case 0:
					final Dialog dialog = new Dialog(SettingsPage.this,R.style.cust_dialog);
					dialog.setContentView(R.layout.custom);
					dialog.setTitle(AudioFileFormat);
				
				
				
					SharedPreferences prefs;
					prefs = context.getSharedPreferences(
							"com.premium.callrecorderbeta",
							Context.MODE_WORLD_WRITEABLE);
					if (prefs.getInt(Preferences.PREF_AUDIO_FORMAT, 1) == 1) {
						RadioButton text_check1 = (RadioButton) dialog
								.findViewById(R.id.text1_check);
						text_check1.setChecked(true);
					} else if (prefs.getInt(Preferences.PREF_AUDIO_FORMAT, 1) == 2) {
						RadioButton text_check2 = (RadioButton) dialog
								.findViewById(R.id.text2_check);
						text_check2.setChecked(true);
					} else if (prefs.getInt(Preferences.PREF_AUDIO_FORMAT, 1) == 3) {
						RadioButton text_check3 = (RadioButton) dialog
								.findViewById(R.id.text3_check);
						text_check3.setChecked(true);
					}

					// set the custom dialog components - text, image and
					// button
					/*
					 * RadioButton text1_check = (RadioButton)
					 * findViewById(R.id.text1_check); text1_check.setOn
					 */
					RadioGroup rg = (RadioGroup) dialog
							.findViewById(R.id.selected);
					rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							SharedPreferences prefs;
							// TODO Auto-generated method stub
							switch (checkedId) {

							case R.id.text1_check:
								Log.e("hee", "Helllooo");

								prefs = context.getSharedPreferences(
										"com.premium.callrecorderbeta",
										Context.MODE_WORLD_WRITEABLE);
								prefs.edit()
										.putInt(Preferences.PREF_AUDIO_FORMAT,
												1).commit();

								int checking = prefs.getInt(
										Preferences.PREF_AUDIO_FORMAT, 1);
								Log.e("checking fr prefrencse", "cheking  "
										+ checking);
								dialog.dismiss();
								break;
							case R.id.text2_check:
								Log.e("hee", "Helllooo");

								prefs = context.getSharedPreferences(
										"com.premium.callrecorderbeta",
										Context.MODE_WORLD_WRITEABLE);
								prefs.edit()
										.putInt(Preferences.PREF_AUDIO_FORMAT,
												2).commit();

								checking = prefs.getInt(
										Preferences.PREF_AUDIO_FORMAT, 1);
								Log.e("checking fr prefrencse", "cheking  "
										+ checking);
								dialog.dismiss();
								break;
							case R.id.text3_check:
								Log.e("hee", "Helllooo");

								prefs = context.getSharedPreferences(
										"com.premium.callrecorderbeta",
										Context.MODE_WORLD_WRITEABLE);
								prefs.edit()
										.putInt(Preferences.PREF_AUDIO_FORMAT,
												3).commit();

								checking = prefs.getInt(
										Preferences.PREF_AUDIO_FORMAT, 1);
								Log.e("checking fr prefrencse", "cheking  "
										+ checking);
								dialog.dismiss();
								break;
							}

						}
					});
					TextView text1 = (TextView) dialog.findViewById(R.id.text1);
					text1.setText("Three_gpp");
					text1.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Log.e("hee", "Helllooo");
							SharedPreferences prefs;
							prefs = context.getSharedPreferences(
									"com.premium.callrecorderbeta",
									Context.MODE_WORLD_WRITEABLE);
							prefs.edit()
									.putInt(Preferences.PREF_AUDIO_FORMAT, 1)
									.commit();

							int checking = prefs.getInt(
									Preferences.PREF_AUDIO_FORMAT, 1);
							Log.e("checking fr prefrencse", "cheking  "
									+ checking);
							dialog.dismiss();

						}
					});
					TextView text2 = (TextView) dialog.findViewById(R.id.text2);
					text2.setText("Mpeg_4");
					text2.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Log.e("hee", "Helllooo");
							SharedPreferences prefs;
							prefs = context.getSharedPreferences(
									"com.premium.callrecorderbeta",
									Context.MODE_WORLD_WRITEABLE);
							prefs.edit()
									.putInt(Preferences.PREF_AUDIO_FORMAT, 2)
									.commit();

							int checking = prefs.getInt(
									Preferences.PREF_AUDIO_FORMAT, 1);
							Log.e("checking fr prefrencse", "cheking  "
									+ checking);
							dialog.dismiss();
						}
					});
					TextView text3 = (TextView) dialog.findViewById(R.id.text3);
					text3.setText("Raw_Amr");
					text3.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							Log.e("hee", "Helllooo");
							SharedPreferences prefs;
							prefs = context.getSharedPreferences(
									"com.premium.callrecorderbeta",
									Context.MODE_WORLD_WRITEABLE);
							prefs.edit()
									.putInt(Preferences.PREF_AUDIO_FORMAT, 3)
									.commit();

							int checking = prefs.getInt(
									Preferences.PREF_AUDIO_FORMAT, 1);
							Log.e("checking fr prefrencse", "cheking  "
									+ checking);
							dialog.dismiss();
						}
					});

					dialog.show();
					break;
				case 1:
					final Dialog dialog2 = new Dialog(SettingsPage.this,R.style.cust_dialog);
					dialog2.setContentView(R.layout.custom);
					dialog2.setTitle(AudioChannels);
				
					prefs = context.getSharedPreferences(
							"com.premium.callrecorderbeta",
							Context.MODE_WORLD_WRITEABLE);
					if (prefs.getInt(Preferences.PREF_AUDIO_SOURCE, 1) == 1) {
						RadioButton text_check1 = (RadioButton) dialog2
								.findViewById(R.id.text1_check);
						text_check1.setChecked(true);
					} else if (prefs.getInt(Preferences.PREF_AUDIO_SOURCE, 1) == 2) {
						RadioButton text_check2 = (RadioButton) dialog2
								.findViewById(R.id.text2_check);
						text_check2.setChecked(true);
					} else if (prefs.getInt(Preferences.PREF_AUDIO_SOURCE, 1) == 3) {
						RadioButton text_check3 = (RadioButton) dialog2
								.findViewById(R.id.text3_check);
						text_check3.setChecked(true);
					}/*
					prefs = context.getSharedPreferences(
							"com.premium.callrecorderbeta",
							Context.MODE_WORLD_WRITEABLE);
					if (prefs.getInt(Preferences.PREF_AUDIO_FORMAT, 1) == 1) {
						RadioButton text_check1 = (RadioButton) dialog2
								.findViewById(R.id.text1_check);
						text_check1.setChecked(true);
					} else if (prefs.getInt(Preferences.PREF_AUDIO_FORMAT, 1) == 2) {
						RadioButton text_check2 = (RadioButton) dialog2
								.findViewById(R.id.text2_check);
						text_check2.setChecked(true);
					} else if (prefs.getInt(Preferences.PREF_AUDIO_FORMAT, 1) == 3) {
						RadioButton text_check3 = (RadioButton) dialog2
								.findViewById(R.id.text3_check);
						text_check3.setChecked(true);
					}*/
					// set the custom dialog components - text, image and
					// button
					RadioGroup rg2 = (RadioGroup) dialog2
							.findViewById(R.id.selected);
					rg2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							SharedPreferences prefs;
							// TODO Auto-generated method stub
							switch (checkedId) {

							case R.id.text1_check:
								Log.e("hee", "Helllooo");

								prefs = context.getSharedPreferences(
										"com.premium.callrecorderbeta",
										Context.MODE_WORLD_WRITEABLE);
								prefs.edit()
										.putInt(Preferences.PREF_AUDIO_SOURCE,
												1).commit();

								int checking = prefs.getInt(
										Preferences.PREF_AUDIO_SOURCE, 1);
								Log.e("checking fr prefrencse", "cheking  "
										+ checking);
								dialog2.dismiss();
								break;
							case R.id.text2_check:
								Log.e("hee", "Helllooo");

								prefs = context.getSharedPreferences(
										"com.premium.callrecorderbeta",
										Context.MODE_WORLD_WRITEABLE);
								prefs.edit()
										.putInt(Preferences.PREF_AUDIO_SOURCE,
												2).commit();

								checking = prefs.getInt(
										Preferences.PREF_AUDIO_SOURCE, 1);
								Log.e("checking fr prefrencse", "cheking  "
										+ checking);
								dialog2.dismiss();
								break;
							case R.id.text3_check:
								Log.e("hee", "Helllooo");

								prefs = context.getSharedPreferences(
										"com.premium.callrecorderbeta",
										Context.MODE_WORLD_WRITEABLE);
								prefs.edit()
										.putInt(Preferences.PREF_AUDIO_SOURCE,
												3).commit();

								checking = prefs.getInt(
										Preferences.PREF_AUDIO_SOURCE, 1);
								Log.e("checking fr prefrencse", "cheking  "
										+ checking);
								dialog2.dismiss();
								break;
							}

						}
					});
					TextView text4 = (TextView) dialog2
							.findViewById(R.id.text1);
					text4.setText("Mic");
					text4.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							SharedPreferences prefs = context
									.getSharedPreferences(
											"com.premium.callrecorderbeta",
											Context.MODE_WORLD_WRITEABLE);
							prefs.edit()
									.putInt(Preferences.PREF_AUDIO_SOURCE, 1)
									.commit();

							int checking = prefs.getInt(
									Preferences.PREF_AUDIO_SOURCE, 1);
							Log.e("checking fr prefrencse", "cheking  "
									+ checking);
							dialog2.dismiss();
						}
					});
					TextView text5 = (TextView) dialog2
							.findViewById(R.id.text2);
					text5.setText("Voice_Downlink");
					text5.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Log.e("hee", "Helllooo");
							SharedPreferences prefs = context
									.getSharedPreferences(
											"com.premium.callrecorderbeta",
											Context.MODE_WORLD_WRITEABLE);
							prefs.edit()
									.putInt(Preferences.PREF_AUDIO_SOURCE, 1)
									.commit();

							int checking = prefs.getInt(
									Preferences.PREF_AUDIO_SOURCE, 1);
							Log.e("checking fr prefrencse", "cheking  "
									+ checking);
							dialog2.dismiss();
						}
					});
					TextView text6 = (TextView) dialog2
							.findViewById(R.id.text3);
					text6.setText("Voice_Uplink");
					text6.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Log.e("hee", "Helllooo");
							SharedPreferences prefs = context
									.getSharedPreferences(
											"com.premium.callrecorderbeta",
											Context.MODE_WORLD_WRITEABLE);
							prefs.edit()
									.putInt(Preferences.PREF_AUDIO_SOURCE, 3)
									.commit();

							int checking = prefs.getInt(
									Preferences.PREF_AUDIO_SOURCE, 1);
							Log.e("checking fr prefrencse", "cheking  "
									+ checking);
							dialog2.dismiss();
						}
					});
					dialog2.show();
				}

			}
		});

	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings_page, menu);
		return true;
	}

}
