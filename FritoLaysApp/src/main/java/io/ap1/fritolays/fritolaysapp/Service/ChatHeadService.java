package io.ap1.fritolays.fritolaysapp.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.ap1.fritolays.fritolaysapp.IntroActivity;
import io.ap1.fritolays.fritolaysapp.SplashActivity;
import io.ap1.fritolays.fritolaysapp.R;
import io.ap1.fritolays.fritolaysapp.Survey;
import io.ap1.fritolays.fritolaysapp.Data.DBUtil;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TextView;

public class ChatHeadService extends Service {

	private WindowManager windowManager;
	private List<View> chatHeads;
	private LayoutInflater inflater;
	private Calendar calendar;
	private SharedPreferences prefs;
	

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		inflater = LayoutInflater.from(this);
		chatHeads = new ArrayList<View>();
		prefs = this.getSharedPreferences(
			      "io.ap1.fritolays.fritolaysapp", Context.MODE_PRIVATE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		final View chatHead = inflater.inflate(R.layout.chathead, null);

		TextView txt_title = (TextView) chatHead.findViewById(R.id.txt_title);
		TextView txt_text = (TextView) chatHead.findViewById(R.id.txt_text);

		// if(intent.getStringExtra("title") != null){
		// txt_title.setText(intent.getStringExtra("title"));
		// txt_text.setText(intent.getStringExtra("text"));
		// }else{
		txt_title.setText("Frito Lay's Survey");
		txt_text.setText("Would you like to complete our survey for a free pepsi?");
		// }

		chatHead.findViewById(R.id.btn_dismiss).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						calendar = Calendar.getInstance();
						SimpleDateFormat dt = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Timestamp currentTimestamp = new java.sql.Timestamp(
								calendar.getTime().getTime());
						String date = dt.format(currentTimestamp);
						System.out.println(date);
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
						
						if (DBUtil.getInstance() == null)
							DBUtil.createInstance(getApplicationContext());
						DBUtil.getInstance().setTime(2, date);
						DBUtil.getInstance().setChoice(2, "No");
						windowManager.removeView(chatHead);
					}
				});

		chatHead.findViewById(R.id.btn_accept).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						calendar = Calendar.getInstance();
						SimpleDateFormat dt = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Timestamp currentTimestamp = new java.sql.Timestamp(
								calendar.getTime().getTime());
						String date = dt.format(currentTimestamp);
						System.out.println(date);
						if (DBUtil.getInstance() == null)
							DBUtil.createInstance(getApplicationContext());
						DBUtil.getInstance().setTime(2, date);
						DBUtil.getInstance().setChoice(2, "Yes");
						if(!prefs.contains("survey_accept")){
							Editor editor = prefs.edit();
							editor.putString("survey_accept", "true");
							editor.commit();
						}else{
							if(!(prefs.getString("survey_accept", "null").equals("true"))){
								Editor editor = prefs.edit();
								editor.putString("survey_accept", "true");
								editor.commit();
							}
						}
						System.out.println("Shared Preferences POST");
						Intent intent = new Intent(ChatHeadService.this,
								Survey.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						windowManager.removeView(chatHead);
					}
				});

		final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE, 0,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.CENTER;

		chatHead.findViewById(R.id.txt_title).setOnTouchListener(
				new View.OnTouchListener() {
					private int initialX;
					private int initialY;
					private float initialTouchX;
					private float initialTouchY;

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							initialX = params.x;
							initialY = params.y;
							initialTouchX = event.getRawX();
							initialTouchY = event.getRawY();
							return true;
						case MotionEvent.ACTION_UP:
							return true;
						case MotionEvent.ACTION_MOVE:
							params.x = initialX
									+ (int) (event.getRawX() - initialTouchX);
							params.y = initialY
									+ (int) (event.getRawY() - initialTouchY);
							windowManager.updateViewLayout(chatHead, params);
							return true;
						}
						return false;
					}
				});

		addChatHead(chatHead, params);

		return START_NOT_STICKY;
	}

	public void addChatHead(View chatHead, LayoutParams params) {
		chatHeads.add(chatHead);
		windowManager.addView(chatHead, params);
	}

	public void removeChatHead(View chatHead) {
		chatHeads.remove(chatHead);
		windowManager.removeView(chatHead);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		for (View chatHead : chatHeads) {
			removeChatHead(chatHead);
		}
	}
}
