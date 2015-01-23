package io.ap1.fritolays.fritolaysapp;



import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.ap1.fritolays.fritolaysapp.Data.DBUtil;
import io.ap1.fritolays.fritolaysapp.SurveyAdapter;
import io.ap1.fritolays.fritolaysapp.SurveyAdapter.ViewHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Survey extends ListActivity{
	 
	Button confirm; 
	Button cancel; 
	Calendar calendar;
	String date;
	
	 private List<SurveyModel> serveyModelList;
	 
     private SurveyAdapter adapter;     
     private Handler jsonHandler = new Handler();
     final HTTPClient client = MonitoringActivity.client;

     SurveyModel sm1;
     SurveyModel sm2;
     SurveyModel sm3;
     SurveyModel sm4;
     
	@Override
	    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.survey);
		
		TextView question = (TextView)findViewById(R.id.question_txt);
		TextView header = (TextView)findViewById(R.id.header_txt);

		question.setText(client.getQuestion());
		header.setText(client.getTitle());
		serveyModelList=new ArrayList<SurveyModel>();
		
		sm1=new SurveyModel();		
		sm2=new SurveyModel();		
		sm3=new SurveyModel();
		sm4=new SurveyModel();
		
		serveyModelList.add(sm1);
		serveyModelList.add(sm2);
		serveyModelList.add(sm3);
		serveyModelList.add(sm4);
		adapter = new SurveyAdapter(getApplicationContext(), serveyModelList);
			
		this.setListAdapter(adapter);
		
		final HTTPClient client = MonitoringActivity.client;
		System.out.println(client.getChoice1());

		new Thread(new Runnable() {
			  public void run() {

//				  se.load(surveyUrl);

				  jsonHandler.post(new Runnable() {
					public void run() {
					 sm1.setName(client.getChoice1());sm1.setImgUrl(client.getIcon1());
					 sm2.setName(client.getChoice2());sm2.setImgUrl(client.getIcon2());
					 sm3.setName(client.getChoice3());sm3.setImgUrl(client.getIcon3());
					 sm4.setName(client.getChoice4());sm4.setImgUrl(client.getIcon4());
					 
					for (SurveyModel s : serveyModelList) {
			            // START LOADING IMAGES FOR EACH STUDENT
			            s.loadImage(adapter);
						}
					}
				  });
			  }
		       }).start();
		
		//adapter.notifyDataSetChanged();
		getListView().setItemsCanFocus(false);   
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE); 
		getListView().setOnItemClickListener(new OnItemClickListener() {


			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
//				// TODO Auto-generated method stub
				ViewHolder h = (ViewHolder) view.getTag();
	            adapter.setSelectedPosition(position);

		          calendar = Calendar.getInstance();
		          System.out.println(getListView().getCheckedItemPosition());
	              SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        	  Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
	              date = dt.format(currentTimestamp);
	              System.out.println(date);
	      		  DBUtil.getInstance().setTime(3, date);
	      		  DBUtil.getInstance().setChoice(3, ("Choice" + String.valueOf(getListView().getCheckedItemPosition()+1)));
	        	  Intent i = new Intent();
	        	  i.setClass(Survey.this, ThankYou.class);
//	        	  i.setClass(view.getContext(), ThankYou.class);
//	        	  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        	  startActivity(i);

			
			}
			
		});
		
		
//		
//		confirm=(Button) findViewById(R.id.confirm_survey); 
//		cancel=(Button) findViewById(R.id.cancel_survey); 
//	    	
//	    	confirm.setOnClickListener(new View.OnClickListener() {
//	        	              
//	        	 @Override
//	        	 public void onClick(View arg0) {
//	        	 // TODO Auto-generated method stub
//	        	 // Toast.makeText(Survey.this, "confirm btn pressed", Toast.LENGTH_LONG).show();
//		          calendar = Calendar.getInstance();
//		          System.out.println(getListView().getCheckedItemPosition());
//	              SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	        	  Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
//	              date = dt.format(currentTimestamp);
//	              System.out.println(date);
//	      		  DBUtil.getInstance().setTime(3, date);
//	      		  DBUtil.getInstance().setChoice(3, ("Choice" + String.valueOf(getListView().getCheckedItemPosition()+1)));
//	        	  Intent i = new Intent();
//                  i.setClass(Survey.this, ThankYou.class);
//              	  startActivity(i);
//	        	 }
//	        	});
//	    	
//	    	cancel.setOnClickListener(new View.OnClickListener() {
//	              
//	        	 @Override
//	        	 public void onClick(View arg0) {
//	        	 // TODO Auto-generated method stub
//	        	  calendar = Calendar.getInstance();
//	        	  Toast.makeText(Survey.this, " Have a great day!", Toast.LENGTH_LONG).show();
//	              SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	        	  Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
//	              date = dt.format(currentTimestamp);
//	              System.out.println(date);
//	      		  DBUtil.getInstance().setTime(3, date);
//	      		  DBUtil.getInstance().setChoice(3, "cancel");
//	        	  try {
//					Thread.currentThread().join(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	        	  //exit application
//	        	 finish(); 
//
//	        }
//	        	});

	    }
		
		@Override
		protected void onDestroy(){
			super.onDestroy();
			 calendar = Calendar.getInstance();
       	  Toast.makeText(Survey.this, " Have a great day!", Toast.LENGTH_LONG).show();
             SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       	  Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
             date = dt.format(currentTimestamp);
             System.out.println(date);
     		  DBUtil.getInstance().setTime(3, date);
     		  DBUtil.getInstance().setChoice(3, "cancel");
       	  try {
				Thread.currentThread().join(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       	  //exit application
       	 finish(); 
		}
		

}