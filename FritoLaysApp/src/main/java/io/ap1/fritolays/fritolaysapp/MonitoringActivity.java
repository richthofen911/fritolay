package io.ap1.fritolays.fritolaysapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import io.ap1.fritolays.fritolaysapp.Data.DBUtil;
import io.ap1.fritolays.fritolaysapp.Data.UserData;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
//import com.radiusnetworks.ibeacon.IBeacon;
//import com.radiusnetworks.ibeacon.IBeaconConsumer;
//import com.radiusnetworks.ibeacon.IBeaconManager;
//import com.radiusnetworks.ibeacon.MonitorNotifier;
//import com.radiusnetworks.ibeacon.RangeNotifier;
//import com.radiusnetworks.ibeacon.Region;
import com.google.gson.Gson;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;


/**
 * ...
 * 
 * @author Samson Kirk-Koffi
 * @author Ashar
 * 
 * @since 07/23/2014
 * 
 * @param <MyThread>
 */
public class MonitoringActivity<MyThread> extends Service implements
		BeaconConsumer {
	
	BeaconManager beaconManager = BeaconManager
			.getInstanceForApplication(this);

	
	SharedPreferences prefs;
	
	static HTTPClient client = new HTTPClient ();
	
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	String json;

	
	protected static boolean exited;
	private Handler myHandler;
	private UserData user;
	private Calendar calendar;


	@Override
	public void onCreate() {
		myHandler = new Handler();
		prefs = this.getSharedPreferences(
			      "ca.frito.fritolays", Context.MODE_PRIVATE);
	}
	
//	@SuppressWarnings("deprecation")
//	@Override
//	public void onStart(Intent i, int startId) {
//		super.onStart(i, startId);
//		
//		iBeaconManager.bind(this);
//	} // end onStart
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		
		//iBeaconManager.bind(this);
        beaconManager.setDebug(true);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);
		return START_STICKY;
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		beaconManager.unbind(this);
	} // end onDestroy


	@Override
	public void onBeaconServiceConnect() {
		beaconManager.setMonitorNotifier(new MonitorNotifier() {


			@Override
			public void didEnterRegion(Region region) {
				new httpThread().execute("a");
				Log.w("ApBeacon", "Region Entered");

				if (exited) {
					myHandler.removeCallbacks(mExitRunnable);
					exited = false;
					Log.w("ApBeacon", "Handler Call Back Removed");
				}
				if(!(prefs.getString("survey_id", "null").equals(client.getId()))){
		        	  calendar = Calendar.getInstance();
		              SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        	  Timestamp currentTimestamp = new Timestamp(calendar.getTime().getTime());
		              String date = dt.format(currentTimestamp);
		              System.out.println(date);
		              if(DBUtil.getInstance() == null)
		            	  DBUtil.createInstance(getApplicationContext());
		      		  DBUtil.getInstance().setTime(1, date);
		      		 // DBUtil.getInstance().setChoice(1, String.valueOf(getListView().getCheckedItemPosition()));
		      		 // DBUtil.getInstance().setTime(1, currentTimestamp.toString());sys
		        	  	System.out.println(currentTimestamp);
		        	  	System.out.println(currentTimestamp.toString());
				}

			} // end didEnterRegion

			@Override
			public void didExitRegion(Region region) {
				myHandler.postDelayed(mExitRunnable, 25000);
				exited = true;
				Log.e("ApBeacon", "Region Exited");

			} // end didExitRegion

			@Override
			public void didDetermineStateForRegion(int state, Region region) {
			} // end didDetermineStateForRegion
		});

		try {

//			beaconManager.startMonitoringBeaconsInRegion(new Region(
//			"myMonitoringUniqueId", Identifier.parse("2F234454CF6D4A0FADF2F4911BA9FFA6"),
//			null, null));
            beaconManager.startMonitoringBeaconsInRegion(new Region("02b988d1-0200-4a0f-adf2-111111111111", null, null, null));
		} catch (RemoteException e) {
			Log.e("IBeaconManager", "Remote Exception" + e);
		} // end try-catch		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Runnable mExitRunnable = new Runnable() {
		@Override
		public void run() {

			try {
				if(!prefs.contains("post_id")){
					Editor editor = prefs.edit();
					editor.putString("post_id", prefs.getString("survey_id", "null"));
					editor.commit();
					System.out.println("Shared Preferences POST");
					user = new UserData(prefs.getString("id", "null"), client.getId());
					user.getDataArray().setEnterTime("_timestamp",DBUtil.getInstance().getTime(1));
					user.getDataArray().setAccept("_timestamp",DBUtil.getInstance().getTime(2));
					user.getDataArray().setSurvey("_timestamp",DBUtil.getInstance().getTime(3));
					user.getDataArray().setRedeem("_timestamp",DBUtil.getInstance().getTime(4));
					
					user.getDataArray().setAccept("_choice",DBUtil.getInstance().getChoice(2));
					user.getDataArray().setSurvey("_choice",DBUtil.getInstance().getChoice(3));
					user.getDataArray().setRedeem("_choice",DBUtil.getInstance().getChoice(4));
//					user.getDataArray().loadList();
					json = gson.toJson(user.toJson());
					System.out.println(user);
					new httpSendThread().execute("a");
				}else{
					if(!(prefs.getString("post_id", "null").equals(prefs.getString("survey_id", "null")))){
						Editor editor = prefs.edit();
						editor.putString("post_id", prefs.getString("survey_id", "null"));
						editor.commit();
						System.out.println("Shared Preferences POST");
						user = new UserData(prefs.getString("id", "null"), client.getId());
						user.getDataArray().setEnterTime("_timestamp",DBUtil.getInstance().getTime(1));
						user.getDataArray().setAccept("_timestamp",DBUtil.getInstance().getTime(2));
						user.getDataArray().setSurvey("_timestamp",DBUtil.getInstance().getTime(3));
						user.getDataArray().setRedeem("_timestamp",DBUtil.getInstance().getTime(4));
						
						user.getDataArray().setAccept("_choice",DBUtil.getInstance().getChoice(2));
						user.getDataArray().setSurvey("_choice",DBUtil.getInstance().getChoice(3));
						user.getDataArray().setRedeem("_choice",DBUtil.getInstance().getChoice(4));
//						user.getDataArray().loadList();
						json = gson.toJson(user.toJson());
						System.out.println(user);
						new httpSendThread().execute("a");
					}
				}


				exited = false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	private Runnable chatheadRunnable = new Runnable() {
		@Override
		public void run() {

			try {
				
				  Intent intent = new Intent(MonitoringActivity.this, io.ap1.fritolays.fritolaysapp.Service.ChatHeadService.class);
			      intent.putExtra("title", "Frito Lay's Survey");
				  intent.putExtra("text", "Would you like to complete our survey for a free pepsi?");
				  startService(intent);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	
	class httpThread extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			//Log.d("http client",client.getServerData());
			client.getServerData();
			return null;
		}
		
		protected void onPostExecute(String Result){
  			System.out.println(prefs.getString("survey_id", "null"));
  			System.out.println(client.getId());
  			System.out.println((prefs.getString("survey_id", "null").equals(client.getId())));
  			System.out.println(prefs.contains("survey_id"));
			if(!prefs.contains("survey_id")){
				Editor editor = prefs.edit();
				editor.putString("survey_id", client.getId());
				editor.commit();
				System.out.println("Shared Preferences");
				myHandler.postDelayed(chatheadRunnable, 10000);
			}else{
				if(!(prefs.getString("survey_id", "null").equals(client.getId()))){
					Editor editor = prefs.edit();
					editor.putString("survey_id", client.getId());
					editor.commit();
					System.out.println("Shared Preferences");
					myHandler.postDelayed(chatheadRunnable, 10000);
				}
			}
			new ChatHeadThread().execute("a");
		}
		
		class ChatHeadThread extends AsyncTask<String, String, String> {

			@Override
			protected String doInBackground(String... params) {
				//Log.d("http client",client.getServerData());
        	  	if(prefs.contains("survey_id")){
        	  		if(!(prefs.getString("survey_id", "null").equals(client.getId()))){
        	  			System.out.println(prefs.getString("survey_id", "null"));
        	  			System.out.println(client.getId());
        	  			myHandler.postDelayed(chatheadRunnable, 10000);
        	  		}
				}else{
					System.out.println("POOOP");
        	  		myHandler.postDelayed(chatheadRunnable, 10000);
				}
				return null;
			}

		}

	}
	
	class httpSendThread extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
//			client.sendServerData(user);
			client.postData(user);
			return null;
		}

	}	
} // end MonitorinActivity
