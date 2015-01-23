package io.ap1.fritolays.fritolaysapp.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
 
public class UserData {
 
	private String _id;
	private String _survey;
	private UserData.UserDataArray data;
//	private ArrayList<String> enterlist;
//	private ArrayList<String> acceptedlist;
//	private ArrayList<String> surveylist;
//	private ArrayList<String> redeemlist;
//	private ArrayList<String> finishlist;
	
	public UserData(String userID, String surveyID){
		_id = userID;
		_survey = surveyID;
		setData(new UserDataArray());

//		enterlist = new ArrayList<String>();
//		acceptedlist = new ArrayList<String>();
//		surveylist = new ArrayList<String>();
//		redeemlist = new ArrayList<String>();
//		finishlist = new ArrayList<String>();
	}
	
    public String toJson(){
    	Gson g = new Gson();
		return g.toJson(this + getData().toJson());
    	
    }
	
 
	//getter and setter methods
	
	
//	public void loadList(){
//		data.add(enterlist);
//		data.add(acceptedlist);
//		data.add(surveylist);
//		data.add(redeemlist);
//		//list.add(finishlist);
//	}
	
//	private String stringTo(){
//		String string = "";
//		for (ArrayList<String> L : list){
//			for (String S : L){
//				string += S;
//			}
//			   
//		}
//		        // 
//		return null;
//	}
	
	@Override
	public String toString() {
	   return "{ \"_id\":" + _id + " , \"_survey\":\"" + _survey +"\", \"data\":[{" + getData() + "}] }";
	}
    
//	@Override
//	public String toString() {
//	   return "{ _id:" + _id + ", _survey:" + _survey +", data:[{" + getData() + "}] }";
//	}

	public UserDataArray getDataArray() {
		// TODO Auto-generated method stub
		return this.getData();
	}
	
	public UserData.UserDataArray getData() {
		return data;
	}

	public void setData(UserData.UserDataArray data) {
		this.data = data;
	}

	@SuppressWarnings("serial")
	public class UserDataArray extends ArrayList<Map<String, String>> {
		
		private Map<String,String> enterlist;
		private Map<String,String> acceptedlist;
		private Map<String,String> surveylist;
		private Map<String,String> redeemlist;

//		private ArrayList<String> finishlist;
		
	    // declare singleton instance
	    protected UserDataArray instance;

	    // private constructor
	    public UserDataArray(){

			enterlist = new HashMap<String,String>();
			acceptedlist = new HashMap<String,String>();
			surveylist = new HashMap<String,String>();
			redeemlist = new HashMap<String,String>();
//			finishlist = new ArrayList<String>();
			this.add(enterlist);
			this.add(acceptedlist);
			this.add(surveylist);
			this.add(redeemlist); 
		}
	    
	    public String toJson(){
	    	Gson g = new Gson();
			return g.toJson(this);
	    	
	    }

	    // get instance of class - singleton
	    public UserDataArray getInstance(){
	        if (instance == null){
	            instance = new UserDataArray();
	        }
	        return instance;
	    }
	    
		public void setEnterTime(String type,String time){
			enterlist.put(type, time);
		}
		
		public void setAccept(String type,String string){
			acceptedlist.put(type, string);
		}
		
		public void setSurvey(String type, String string){
			surveylist.put(type, string);
		}
		
		public void setRedeem(String type, String string){
			redeemlist.put(type, string);
		}
		
		@Override
		public String toString() {
			Gson gson = new Gson();
		   return " \"entered\":" + gson.toJson(enterlist) + ", \"accepted_survey\":" + gson.toJson(acceptedlist) + ", \"choice_survey\":" + gson.toJson(surveylist) + ", \"coupon_redeem\":" + gson.toJson(redeemlist) ;
		}
		
//		@Override
//		public String toString() {
//		   return " enterlist:" + enterlist + ", acceptedlist:" + acceptedlist + ", surveylist:" + surveylist + ", redeemlist:" + redeemlist;
//		}
		
//		public void setFinish(String string){
//			finishlist.add(string);
//		}
	}
 
}
