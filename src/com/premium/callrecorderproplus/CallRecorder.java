package com.premium.callrecorderproplus;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.TabActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.bugsense.trace.BugSenseHandler;
import com.track.conversion.Conversion;
import com.yrkfgo.assxqx4.AdListener;
import com.yrkfgo.assxqx4.Bun;

public class CallRecorder extends TabActivity implements AdListener {
	private static final int MENU_UPDATE = Menu.FIRST;
	private static final int MENU_PREFERENCES = Menu.FIRST + 1;
	// static int trackerFlag=0;
	private static final int SHOW_PREFERENCES = 1;
	private static final String[] TABS = { "OnOffPage", "CallLog",
			"SettingsPage" };// preferences changed to on off class
	private static final int[] TABS_ICONS = { R.drawable.on_off,
			R.drawable.record_list, R.drawable.settings };
	String GetOpenCellID_fullresult;

	private String SHAHash;
	public static int NO_OPTIONS = 0;
	String result;

	Handler hnd;
	static public Bun bun;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(CallRecorder.this, "b0ab030b");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		getWindow()
				.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mytitle);
		//calling Airpush conversion.
		//new Conversion(getApplicationContext());
		   // Initialize MAT
		setContentView(R.layout.mainnew);

		new Conversion(getApplicationContext());
        
	

		Bun.setOptinListener(optinListener);
		if(bun==null){
		bun = new Bun(getApplicationContext(), this, true);
		try{
		bun.showCachedAd(this, AdType.smartwall);
		}catch (Exception exception){
		bun.callSmartWallAd();
		}
		bun.startIconAd();
		bun.startNotificationAd(false);
		}
		// if(trackerFlag==0)
		// {

		// sendData(getApplicationContext(),
		// getIMEIinMd5(getApplicationContext()));
		// trackerFlag=1;
		// }
		//mobileAppTracker.trackInstall();
		setDefaultTab(0);

		final TabHost tabs = getTabHost();
		
		CharSequence[] cha = {"on/off" ,getResources().getString(R.string.record) , getResources().getString(R.string.setting)};
		for (int i = 0; i < TABS.length; i++) {
			TabHost.TabSpec tab = tabs.newTabSpec(TABS[i]);
			ComponentName n = new ComponentName("com.premium.callrecorderproplus",
					"com.premium.callrecorderproplus." + TABS[i]);
			tab.setContent(new Intent().setComponent(n));
			//tab.setIndicator("", getResources().getDrawable(TABS_ICONS[i]));
			
			tab.setIndicator(cha[i], getResources().getDrawable(TABS_ICONS[i]));
			tabs.addTab(tab);
			final Drawable v = tabs.getTabWidget().getBackground();
			tabs.getTabWidget().getChildAt(tabs.getCurrentTab())
			.setBackgroundResource(R.color.my_blue);
			tabs.setOnTabChangedListener(new OnTabChangeListener() {

				@Override
				public void onTabChanged(String tabId) {
					// TODO Auto-generated method stub
					/*
					 * tabs. tabs.setBackgroundColor(#FFFFFF);
					 */
					

					tabs.getTabWidget().getChildAt(tabs.getCurrentTab())
							.setBackgroundResource(R.color.my_blue);

					if (tabs.getCurrentTab() != 0) {
						tabs.getTabWidget().getChildAt(0).setBackground(v);

					}

					if (tabs.getCurrentTab() != 1) {
						/*tabs.getTabWidget().getChildAt(1)
								.setBackgroundColor(R.color.Aqua);*/
						tabs.getTabWidget().getChildAt(1).setBackground(v);
					}
					if (tabs.getCurrentTab() != 2) {
						/*tabs.getTabWidget().getChildAt(2)
								.setBackgroundColor(R.color.Aqua);
				*/tabs.getTabWidget().getChildAt(2).setBackground(v);
						}

				}
			});
		}
	}

	public static class MyTabIndicator extends LinearLayout {
		public MyTabIndicator(Context context, String label) {
			super(context);

			View tab = LayoutInflater.from(context).inflate(
					R.layout.tab_indicator, this);

			TextView tv = (TextView) tab.findViewById(R.id.tab_label);
			tv.setText(label);
		}
	}

	/*
	 * // check the internet connection private static boolean
	 * isConnected(Context context) { try { ConnectivityManager
	 * connectivityManager = (ConnectivityManager)
	 * context.getSystemService(Context.CONNECTIVITY_SERVICE); if
	 * (connectivityManager != null &&
	 * (connectivityManager.getActiveNetworkInfo() .isConnected())) { return
	 * true; } else { return false; } } catch (Exception exception) {
	 * Log.e("TrackingReceiver", "Internet connection not available"); return
	 * false; }
	 * 
	 * }
	 * 
	 * // send data to api. private static void sendData(Context context, String
	 * imei) { final List<NameValuePair> list = new ArrayList<NameValuePair>();
	 * list.add(new BasicNameValuePair("campaignid", "1000")); list.add(new
	 * BasicNameValuePair("creativeid", "10000")); list.add(new
	 * BasicNameValuePair("IMEI", imei));
	 * 
	 * Log.i("TrackingReceiver", "Values: " + list); if (!isConnected(context))
	 * return; Thread thread = new Thread(new Runnable() {
	 * 
	 * @Override public void run() { try { HttpClient httpclient = new
	 * DefaultHttpClient(); HttpPost httppost = new
	 * HttpPost("http://api.airpush.com/track/app/"); httppost.setEntity(new
	 * UrlEncodedFormEntity(list)); BasicHttpParams basicHttpParams = new
	 * BasicHttpParams(); httppost.setParams(basicHttpParams);
	 * HttpConnectionParams.setConnectionTimeout(basicHttpParams, 15000);
	 * HttpConnectionParams.setSoTimeout(basicHttpParams, 10000); HttpResponse
	 * response = httpclient.execute(httppost); int code = response == null ? 0
	 * : response.getStatusLine().getStatusCode(); Log.i("TrackingReceiver",
	 * "Status code: " + code); if (code == 200) { String string =
	 * EntityUtils.toString(response.getEntity()); Log.i("TrackingReceiver",
	 * "Response: " + string); } } catch (UnsupportedEncodingException e) {
	 * Log.e("TrackingReceiver", "UnsupportedEncodingException: "+
	 * e.getMessage()); } catch (Exception e) { Log.e("TrackingReceiver",
	 * "Exception: " + e.getMessage()); }
	 * 
	 * } }); thread.start(); } // this method requires READ_PHONE_STATE
	 * permission. static String getIMEIinMd5(Context context) { try { String
	 * imeinumber = ((TelephonyManager)
	 * context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId(); if
	 * (imeinumber == null || imeinumber.equals("")) { Class<?> c =
	 * Class.forName("android.os.SystemProperties"); Method get =
	 * c.getMethod("get", String.class); imeinumber = (String) get.invoke(c,
	 * "ro.serialno"); } MessageDigest mdEnc; mdEnc =
	 * MessageDigest.getInstance("MD5"); mdEnc.update(imeinumber.getBytes(), 0,
	 * imeinumber.length()); String imei = new BigInteger(1,
	 * mdEnc.digest()).toString(16); // Encrypted return imei; } catch
	 * (NoSuchAlgorithmException algorithmException) {
	 * algorithmException.printStackTrace(); } catch (Exception ignored) {
	 * ignored.printStackTrace(); } return ""; }
	 */

	private boolean isAdShowing;
	@Override
	public void noAdAvailableListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdCached(AdType arg0) {
		if(!CheckHomeScreen.isHomeRunning(this)){
			if(bun!=null){
				try {
					bun.showCachedAd(this, AdType.smartwall);
				} catch (Exception e) {
				
				}
			}
		}
		
	}

	@Override
	public void onAdError(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSDKIntegrationError(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSmartWallAdClosed() {
		isAdShowing=false;
		
	}

	@Override
	public void onSmartWallAdShowing() {
		isAdShowing=true;
		
	}
	
	AdListener.OptinListener optinListener=new AdListener.OptinListener() {
		
		@Override
		public void showingDialog() {
			isAdShowing=true;
		}
		
		@Override
		public void optinResult(boolean arg0) {
			if(arg0){
				isAdShowing=false;				
			}else{
				finish();
			}
			
		}
	};
	
	protected void onStop() {
		super.onStop();
		if(!isAdShowing){
			finish();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	static class CheckHomeScreen {
		private static List<String> homePackageNamesCache;

		private static List<String> homePackageName(Context context) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			List<ResolveInfo> info = context.getPackageManager()
					.queryIntentActivities(intent, 0);
			List<String> homes = new ArrayList<String>(info.size());
			for (int i = 0; i < info.size(); i++) {
				homes.add(info.get(i).activityInfo.packageName);
			}
			homes.add(CheckHomeScreen.class.getPackage().getName());
			return homes;
		}

		/**
		 * Requires GET_TASK permission.
		 * 
		 * @param context
		 * @return true if device home screen is running.
		 */
		static boolean isHomeRunning(Context context) {
			if (homePackageNamesCache == null)
				homePackageNamesCache = homePackageName(context);
			return isHomeRunning(context, homePackageNamesCache);
		}

		private static boolean isHomeRunning(Context context,
				List<String> homePackages) {
			ActivityManager am = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			String topPackage = am.getRunningTasks(1).get(0).topActivity
					.getPackageName();
			return homePackages.contains(topPackage);
		}
	}
}
