package io.ap1.fritolays.fritolaysapp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import io.ap1.fritolays.fritolaysapp.Data.DBUtil;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Coupon extends Activity implements OnRefreshListener<ListView> {
	
//	Button confirm; 
//	Button cancel; 
	Calendar calendar;
	
	BarcodeModel barcode;
	
    public static ListView listCoupon;
    private Handler jsonHandler = new Handler();

    private BarcodeAdapter adapter;
	private List<BarcodeModel> BarModelList;     

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.coupon);
		BarModelList=new ArrayList<BarcodeModel>();
		barcode = new BarcodeModel();	
//		confirm=(Button) findViewById(R.id.begin_coupon); 
//		cancel=(Button) findViewById(R.id.refuse_coupon); 
		
		final HTTPClient client = MonitoringActivity.client;

		BarModelList.add(barcode);
		adapter = new BarcodeAdapter(getApplicationContext(), BarModelList);
		listCoupon = (ListView) findViewById(R.id.coupon_list);
		listCoupon.setAdapter(adapter);
			
		new Thread(new Runnable() {
			  public void run() {

//				  se.load(surveyUrl);

				  jsonHandler.post(new Runnable() {
					public void run() {

						barcode.setImgUrl(client.getBarcode());
					for (BarcodeModel s : BarModelList) {
			            // START LOADING IMAGES FOR EACH STUDENT
			            s.loadImage(adapter);
			            
						}
					}
				  });
			  }
		       }).start();
		barcode.loadImage(adapter);
		listCoupon.setClickable(false);	

//    	confirm.setOnClickListener(new View.OnClickListener() {
//        	              
//        	 @Override
//        	 public void onClick(View arg0) {
//        	 // TODO Auto-generated method stub
//        	 // Toast.makeText(Survey.this, "confirm btn pressed", Toast.LENGTH_LONG).show();
//	          calendar = Calendar.getInstance();
//              SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        	  Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
//              String date = dt.format(currentTimestamp);
//              System.out.println(date);
//      		  DBUtil.getInstance().setTime(3, date);
//      		  DBUtil.getInstance().setChoice(3, "0");
//        	  Intent i = new Intent();
//              i.setClass(Coupon.this, IntroActivity.class);
//          	  startActivity(i);
//        	 }
//        	});
//    	
//    	cancel.setOnClickListener(new View.OnClickListener() {
//              
//        	 @Override
//        	 public void onClick(View arg0) {
//        	 // TODO Auto-generated method stub
//        	  calendar = Calendar.getInstance();
//        	  Toast.makeText(Coupon.this, " Have a great day!", Toast.LENGTH_LONG).show();
//              SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        	  Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
//              String date = dt.format(currentTimestamp);
//              System.out.println(date);
//      		  DBUtil.getInstance().setTime(3, date);
//      		  DBUtil.getInstance().setChoice(3, "1");
//        	  try {
//				Thread.currentThread().join(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        	  //exit application
//        	 finish(); 
////      		Thread delayTimer = new Thread() {
////      			public void run(){
////      				try{
////      					int logoTimer = 0;
////      					while(logoTimer < 3000){
////      						sleep(100);
////      						logoTimer = logoTimer +100;
////      					};
////      	        	  android.os.Process.killProcess(android.os.Process.myPid());
////                      System.exit(1);
////      				} 
////      				 
////      				catch (InterruptedException e) {
////      					// TODO Auto-generated catch block
////      					e.printStackTrace();
////      				}
////      				 
////      				finally{
////      					finish();
////      				}
////      			}
////      		};
////      		 
////      		delayTimer.start();
//        }
//        	});
//
    }
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}
	


}
