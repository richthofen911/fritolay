package io.ap1.fritolays.fritolaysapp.Data;

import android.content.Context;

public class DBUtil {
	
	private static myDatabase myDB;
	
	
    public DBUtil(Context c) {
    	myDB = new myDatabase(c);
	}
	
    public static void createInstance(Context c) {
        if (myDB == null) {
        	myDB = new myDatabase(c);
        } // end if
    } // end createInstance

    public static myDatabase getInstance() {
        return myDB;
    } // end getInstance

	public String getTime(int stageID){
    	String myReturn = myDB.getTime(stageID);
    	myDB.close();
    	return myReturn;
    }
       
    public int setTime(int stageID, String timestamp){
    	int myReturn = myDB.setTime(stageID, timestamp);
    	myDB.close();
    	return myReturn;
    }
    
    public String getChoice(int stageID){
    	String myReturn = myDB.getChoice(stageID);
    	myDB.close();
    	return myReturn;
    }
       
    public int setChoice(int stageID, String timestamp){
    	int myReturn = myDB.setChoice(stageID, timestamp);
    	myDB.close();
    	return myReturn;
    }
}
