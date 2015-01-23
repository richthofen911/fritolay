package io.ap1.fritolays.fritolaysapp;



import android.app.Application;


import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseTwitterUtils;



public class MyApplication extends Application 
{     
    
	
	
	public void onCreate() {
		  Parse.initialize(this, "EM8CiZiPFh7pSBA0c3Xq2TrGjG6mG6jafIv4T9SD", "92CM6nRMEoCRAe3vLAm3EBOANRtTDaCodcyDrQmg");
		  ParseFacebookUtils.initialize("1473813582866254");
//		  ParseTwitterUtils.initialize(Constant.TWITTER_CONSUMER_KEY, Constant.TWITTER_CONSUMER_SECRET);
//		  PushService.setDefaultPushCallback(getApplicationContext(), MainActivity.class, R.drawable.ic_launcher);
		  ParseInstallation.getCurrentInstallation().saveInBackground();
		}
}