package io.ap1.fritolays.fritolaysapp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.ap1.fritolays.fritolaysapp.Data.DBUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ThankYou extends Activity {

	Button getCoupon;
	//Button exit;
	Calendar calendar;
	final HTTPClient client = MonitoringActivity.client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.thank_you);

		getCoupon = (Button) findViewById(R.id.begin_coupon);
		//exit = (Button) findViewById(R.id.refuse_coupon);
		
		TextView thank = (TextView)findViewById(R.id.thank_txt);

		thank.setText(client.getThank_msg());

		getCoupon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Toast.makeText(AskCoupon.this, "get coupon pressed",
				// Toast.LENGTH_LONG).show();
				// Intent intent = new Intent(ThankYou.this, ListCoupon.class);
				// startActivity(intent);
				calendar = Calendar.getInstance();
				SimpleDateFormat dt = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Timestamp currentTimestamp = new java.sql.Timestamp(calendar
						.getTime().getTime());
				String date = dt.format(currentTimestamp);
				System.out.println(date);
				DBUtil.getInstance().setTime(4, date);
				DBUtil.getInstance().setChoice(4, "Accepted");
				Intent i = new Intent();
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setClass(ThankYou.this, Coupon.class);
				startActivity(i);
			}
		});

		/*
        exit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				calendar = Calendar.getInstance();
				Toast.makeText(ThankYou.this,
						"Thanks for taking the survery! Have a great day :)",
						Toast.LENGTH_LONG).show();
				SimpleDateFormat dt = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Timestamp currentTimestamp = new java.sql.Timestamp(calendar
						.getTime().getTime());
				String date = dt.format(currentTimestamp);
				System.out.println(date);
				DBUtil.getInstance().setTime(4, date);
				DBUtil.getInstance().setChoice(4, "Declined");
				try {
					Thread.currentThread().join(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Intent homeIntent = new Intent(Intent.ACTION_MAIN);
				homeIntent.addCategory(Intent.CATEGORY_HOME);
				homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(homeIntent);
			}
		});
        */
	}

}
