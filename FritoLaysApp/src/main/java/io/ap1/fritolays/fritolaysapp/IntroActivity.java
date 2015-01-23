package io.ap1.fritolays.fritolaysapp;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.Window;
import android.view.WindowManager;


public class IntroActivity extends FragmentActivity {

    private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.intro_page);
        prefs = this.getSharedPreferences(
			      "ca.frito.fritolays", Context.MODE_PRIVATE);
	}
    
	@Override
	protected void onDestroy(){
		super.onDestroy();
		if(!prefs.contains("survey_accept")){
			Editor editor = prefs.edit();
			editor.putString("survey_id", "null");
			editor.commit();
		}else{
			if(!(prefs.getString("survey_accept", "null").equals("true"))){
				Editor editor = prefs.edit();
				editor.putString("survey_id", "null");
				editor.commit();
			}
		}
	}
	
}
