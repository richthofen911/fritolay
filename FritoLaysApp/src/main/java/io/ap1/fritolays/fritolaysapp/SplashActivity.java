package io.ap1.fritolays.fritolaysapp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import io.ap1.fritolays.fritolaysapp.R;
import io.ap1.fritolays.fritolaysapp.Data.DBUtil;
import io.ap1.fritolays.fritolaysapp.Data.myDatabase;

import com.facebook.Request;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
//import com.radiusnetworks.ibeacon.IBeaconManager;
import org.altbeacon.beacon.BeaconManager;


public class SplashActivity extends Activity {

	private static int SPLASH_TIME_OUT = 2500;
	private static final int REQUEST_ENABLE_BT = 1;
	
	private ImageButton facebutton;
	private static ProgressDialog dialog;
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	String regid;
	String SENDER_ID = "390813975856";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.splash);
        if(DBUtil.getInstance() == null)
      	  DBUtil.createInstance(getApplicationContext());

		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"io.ap1.fritolays.fritolaysapp",
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {
		}
		// verifyInternet();
		startApp();
	}

	private void startApp() {
		if (verifyBluetooth()) {
			if (!isNetworkConnected()) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SplashActivity.this);

				builder.setTitle("Could not connect to the server! Check your internet connection.");
				builder.setMessage("Would you like to try again?");

				builder.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								startApp();
								dialog.dismiss();
							}

						});

				builder.setNegativeButton("NO",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								System.exit(0);
								dialog.dismiss();
							}
						});

				AlertDialog alert = builder.create();
				alert.show();
			} else {
				new Handler().postDelayed(new Runnable() {

					/*
					 * Showing splash screen with a timer. This will be useful
					 * when you want to show case your app logo / company
					 */


					@Override
					public void run() {
						// This method will be executed once the timer is over
						// Start your app main activity
						
						//finish();
						
//						ImageButton signinbutton = (ImageButton) findViewById(R.id.facebutton);
//						signinbutton.setImageResource(R.drawable.facebtn);
						ParseUser currentUser = ParseUser.getCurrentUser();
						System.out.println(currentUser);
						if ((currentUser != null)
								&& ParseFacebookUtils.isLinked(currentUser)) {
								kke k = new kke();
								k.execute("a");
								Intent intent = new Intent(SplashActivity.this,
										IntroActivity.class);
								startActivity(intent);
						}else{
							facebutton = (ImageButton) findViewById(R.id.facebutton);
							//facebutton.setVisibility(ImageButton.GONE);
							facebutton.setVisibility(ImageButton.VISIBLE);
							// facebtn.setVisibility(View.GONE);
							facebutton.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									
									
									FBlogin();

	
								} // end onClick
	
								
							});
						}

					}
				}, SPLASH_TIME_OUT);
			}
		}

	}

	
	public void FBlogin() {
		
		dialog = ProgressDialog.show(SplashActivity.this,
				"Connecting to Facebook...", "Please wait...", false);
		dialog.show();
		ParseFacebookUtils.logIn((Arrays.asList(
				ParseFacebookUtils.Permissions.User.EMAIL,
				ParseFacebookUtils.Permissions.User.BIRTHDAY,
				ParseFacebookUtils.Permissions.User.STATUS,
				ParseFacebookUtils.Permissions.User.PHOTOS)), this, 3,
				new LogInCallback() {
					@Override
					public void done(ParseUser user,
							com.parse.ParseException err) {
	
						final Session session = ParseFacebookUtils.getSession();
						new FAccessTask().execute(session);

						// }

					}
				});
//		Intent intent = new Intent(SplashActivity.this,
//		Survey.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//		startActivity(intent);
	}
	private class FAccessTask extends AsyncTask<Session, Void, Boolean> {

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Boolean doInBackground(final Session... session) {
			
			boolean status = true;
			Request request = Request.newMeRequest(session[0],
					new Request.GraphUserCallback() {

						@Override
						public void onCompleted(GraphUser fuser,
								com.facebook.Response response) {
							// If the response is successful
							if (session[0] == Session.getActiveSession()) {
								if (fuser != null) {

									try {
										String upLoadServerUri = "http://69.172.254.176/api/v1/login";

										MultipartUtility multipart = new MultipartUtility(
												upLoadServerUri, "UTF-8");

										// multipart.addHeaderField("User-Agent",
										// "CodeJava");
										// multipart.addHeaderField("Test-Header",
										// "Header-Value");
										multipart.addFormField("user_name",
												fuser.getName());
//										String x= fuser.asMap().get("email").toString();
										String y = (fuser.asMap().get("email") == null) ? "" : fuser
												.asMap().get("email")
												.toString() ;
										multipart.addFormField("user_email", y);
										multipart
												.addFormField(
														"user_img",
														"http://graph.facebook.com/"
																+ fuser.getId()
																+ "/picture?type=large");
										multipart.addFormField("user_gender",
												fuser.asMap().get("gender")
														.toString());
										multipart.addFormField("user_age",
												fuser.getBirthday());
										multipart.addFormField("platform",
												"Android");
										multipart.addFormField("facebookID",
												fuser.getId());
										multipart
												.addFormField(
														"user_regID",getRegistrationId(getApplicationContext()));
										multipart
												.addFormField("macAddress", getMacAddress(getApplicationContext()));
										// multipart.addFilePart("fileUpload",
										// sourceFile);

										List<String> responseList = multipart
												.finish();

										System.out.println("SERVER REPLIED:");

										JsonElement resp = new JsonParser()
												.parse(responseList.get(0));

										if (resp.isJsonObject()) {

											JsonObject pref = resp
													.getAsJsonObject();
											SharedPreferences sharedPref = getSharedPreferences(
													"ca.frito.fritolays", 0);
											SharedPreferences.Editor editor = sharedPref
													.edit();
											editor.putString("id",
													pref.get("_id").toString());
											editor.putString("name",
													pref.get("name").toString());
											editor.putString("userImage", pref
													.get("img").toString());
											editor.commit();

										}
									} catch (IOException ex) {
										System.err.println(ex);
									}

									long id = Long.parseLong(fuser.getId());
									

								}
							}
							if (response.getError() != null) {
								// Handle error

							}
						}

					});
			request.executeAndWait();
			return status;
		}

		@Override
		protected void onPostExecute(Boolean status) {
			dialog.dismiss();
			if (status == true) {
				Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
				kke k = new kke();
				k.execute("a");
			}
		}
	}

	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo NetInfo = cm.getActiveNetworkInfo();
		if (null != NetInfo) {
			if (NetInfo.getType() == ConnectivityManager.TYPE_WIFI)
				return true;

			if (NetInfo.getType() == ConnectivityManager.TYPE_MOBILE)
				return true;
		}
		return false;
	}

	private boolean verifyBluetooth() {
		try {
			if (!BeaconManager.getInstanceForApplication(this)
					.checkAvailability()) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						this);
				builder.setTitle("Bluetooth is not enabled");
				builder.setMessage("Please enable bluetooth.");
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								startActivityForResult(
										new Intent(
												BluetoothAdapter.ACTION_REQUEST_ENABLE),
										REQUEST_ENABLE_BT);
							}

						});

				builder.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								System.exit(0);
								dialog.dismiss();
							}
						});
				builder.show();
				return false;
			} // end if
		} catch (RuntimeException e) {

		} // end try-catch
		return true;
	} // end verifyBluetooth

	// The result of startActivityForResult for bluetooth ON/OFF
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode== REQUEST_ENABLE_BT) {
			new getBluetoothConnection().execute();
		}else if (requestCode== 3) {
			ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
		}else{
			super.onActivityResult(requestCode, resultCode, data);
		}
		

	}
	

	public class getBluetoothConnection extends AsyncTask<Void, Void, Boolean> {
		ProgressDialog pdialog;

		protected void onPreExecute() {
			pdialog = new ProgressDialog(SplashActivity.this);
			pdialog.setMessage("Connecting ...");
			pdialog.setCancelable(false);
			pdialog.show();
			startApp();
		}

		protected Boolean doInBackground(Void... params) {
			return true;
		}

		protected void onPostExecute(Boolean b) {
			pdialog.dismiss();

		}
		

			
	}


	public static class BroadCastActivity extends BroadcastReceiver {

		public static int TYPE_WIFI = 1;
		public static int TYPE_MOBILE = 2;
		public static int TYPE_NOT_CONNECTED = 0;

		@Override
		public void onReceive(final Context context, final Intent intent) {

			String status = getConnectivityStatusString(context);

			Toast.makeText(context, status, Toast.LENGTH_LONG).show();
		}

		// Checks for connectivity returns the type of connectivity
		public static int getConnectivityStatus(Context context) {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			if (null != activeNetwork) {
				if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
					return TYPE_WIFI;

				if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
					return TYPE_MOBILE;
			}
			return TYPE_NOT_CONNECTED;
		}

		public static String getConnectivityStatusString(Context context) {
			int conn = getConnectivityStatus(context);
			String status = null;

			if (conn == TYPE_WIFI) {
				status = "Wifi is connected. You can log back in!";
			} else if (conn == TYPE_MOBILE) {
				status = "Mobile data is connected. You can log back in!";
			} else if (conn == TYPE_NOT_CONNECTED) {
				status = "Internet connection has been lost. Try getting back online.";
				// MainActivity.Logout();
			}
			return status;
		}

	}

	
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGcmPreferences(getApplicationContext());
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			registerInBackground();
			registrationId = prefs.getString(PROPERTY_REG_ID, "");
			return registrationId;

		}

		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			registerInBackground();
			registrationId = prefs.getString(PROPERTY_REG_ID, "");
			return registrationId;
		}
		return registrationId;
	}

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and the app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging
								.getInstance(getApplicationContext());
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;
					storeRegistrationId(getApplicationContext(), regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();

				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
			}
		}.execute(null, null, null);
	}
	
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGcmPreferences(getApplicationContext());
		int appVersion = getAppVersion(getApplicationContext());
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGcmPreferences(Context context) {
		// App persists the registration ID in shared preferences .
		return getSharedPreferences(SplashActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}
	public String getMacAddress(Context context) {
		WifiManager wimanager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		String macAddress = wimanager.getConnectionInfo().getMacAddress();
		if (macAddress == null) {
			macAddress = "";
		}
		return macAddress;
	}
	
	class kke extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
		
			Intent intService = new Intent(getApplicationContext(),
					MonitoringActivity.class);
			intService.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startService(intService);
			return null;
		}

	}
	
}