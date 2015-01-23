package io.ap1.fritolays.fritolaysapp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import io.ap1.fritolays.fritolaysapp.Data.UserData;


public class HTTPClient {

    private String id;
    private String title;
    private String thank_msg;
    private String question;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String updated_at;
    private String created_at;
    private String logo;
    private String barcode;
    private String icon1;
    private String icon2;
    private String icon3;
    private String icon4;

    //private static String BASE_URL = "http://69.172.254.176/api/v1/survey";
    private static String BASE_URL = "http://69.172.254.176/api/v1/survey/53ec8e8a7f8b9aaa3662b3fb";
    private String json;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThank_msg() {
        return thank_msg;
    }

    public void setThank_msg(String thank_msg) {
        this.thank_msg = thank_msg;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getIcon1() {
        return icon1;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }

    public String getIcon2() {
        return icon2;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2;
    }

    public String getIcon3() {
        return icon3;
    }

    public void setIcon3(String icon3) {
        this.icon3 = icon3;
    }

    public String getIcon4() {
        return icon4;
    }

    public void setIcon4(String icon4) {
        this.icon4 = icon4;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public static String getSurveyUrl() {
        return BASE_URL;
    }

    public static void setSurveyUrl(String surveyUrl) {
        HTTPClient.BASE_URL = surveyUrl;
    }


    public void getServerData() {
        InputStream inputStream = null;
        String result = "";
        try {
            result=this.getDataFromUrl(BASE_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result != null) {

            json = result.replace("\\", "");
            Log.i("ImageLoadTask", "Successfully loaded :" + json);
            //jsonArray
            try {
                JSONObject jsonObject = new JSONObject(json);
                Log.d("xxxxx", "xxxxx" + jsonObject.getString("title"));
                id = jsonObject.getString("_id");
                title = jsonObject.getString("title");
                thank_msg = jsonObject.getString("thank_msg");
                question = jsonObject.getString("question");
                choice1 = jsonObject.getString("choice1");
                choice2 = jsonObject.getString("choice2");
                choice3 = jsonObject.getString("choice3");
                choice4 = jsonObject.getString("choice4");
                updated_at = jsonObject.getString("updated_at");
                created_at = jsonObject.getString("created_at");
                logo = jsonObject.getString("logo");
                barcode = jsonObject.getString("barcode");
                icon1 = jsonObject.getString("icon1");
                icon2 = jsonObject.getString("icon2");
                icon3 = jsonObject.getString("icon3");
                icon4 = jsonObject.getString("icon4");


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else Log.e("JsonLoadTask", "Failed to load ");

    }

    public static String getDataFromUrl(String url) throws IOException {
        String result = "";
        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        if(conn.getResponseCode() == 200){
            String encode = conn.getContentEncoding();
            InputStream in = conn.getInputStream();
            result = getStrFromInput(in, encode);
        }
        return result;
    }

    private static String getStrFromInput(InputStream in, String encode) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];

        int len = 0;

        while((len = in.read(buffer)) != -1){
            out.write(buffer, 0, len);
        }
        out.flush();
        out.close();
        return new String(out.toByteArray(), "UTF-8");
    }



    public void sendServerData(UserData user) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            con = (HttpURLConnection) ( new URL(BASE_URL)).openConnection();
            con.setRequestMethod("POST");
            //	con.setDoInput(true);
            //	con.setDoOutput(true);
            //	con.connect();

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes("data{" + user.toString() + "}");
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + BASE_URL);
            System.out.println("Post parameters : " + user);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            con.disconnect();

            //print result
            System.out.println(response.toString());

        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

    }

    public void postData(UserData user) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(BASE_URL);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("data", user.toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            System.out.println("Post : "+ nameValuePairs);
            System.out.println("Response Code : " + response);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
    }

//	public byte[] getImage(String code) {
//		HttpURLConnection con = null ;
//		InputStream is = null;
//		try {
//			con = (HttpURLConnection) ( new URL(IMG_URL + code)).openConnection();
//			con.setRequestMethod("GET");
//			con.setDoInput(true);
//			con.setDoOutput(true);
//			con.connect();
//
//			// Let's read the response
//			is = con.getInputStream();
//			byte[] buffer = new byte[1024];
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//			while ( is.read(buffer) != -1)
//				baos.write(buffer);
//
//			return baos.toByteArray();
//	    }
//		catch(Throwable t) {
//			t.printStackTrace();
//		}
//		finally {
//			try { is.close(); } catch(Throwable t) {}
//			try { con.disconnect(); } catch(Throwable t) {}
//		}
//
//		return null;
//
//	}


}
