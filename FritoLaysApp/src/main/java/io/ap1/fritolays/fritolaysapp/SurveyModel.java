package io.ap1.fritolays.fritolaysapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SurveyModel {
	private String name;


	private String imgUrl;
	private Bitmap image;
	private SurveyAdapter sta;
	
	public SurveyAdapter getSta() {
		return sta;
	}
	public void setSta(SurveyAdapter sta) {
		this.sta = sta;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public void loadImage(SurveyAdapter sta) {
        // HOLD A REFERENCE TO THE ADAPTER
		 this.sta = sta;
        if (imgUrl != null && !imgUrl.equals("")) {
            new ImageLoadTask().execute(imgUrl);
           
        }
    }
	
	private class ImageLoadTask extends AsyncTask<String, String, Bitmap> {
		 
        @Override
        protected void onPreExecute() {
            Log.i("ImageLoadTask", "Loading image...");
        }
 
        // param[0] is img url
        protected Bitmap doInBackground(String... param) {
            Log.i("ImageLoadTask", "Attempting to load image URL: " + param[0]);
            try {
                //Bitmap b = ImageService.getBitmapFromURLWithScale(param[0]);
                Bitmap b = BitmapFactory.decodeStream((InputStream)new URL(param[0]).getContent());
               
                return b;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
//            try {
//                URL url = new URL(param[0]);
//                HttpGet httpRequest = null;
//
//                try {
//					httpRequest = new HttpGet(url.toURI());
//				} catch (URISyntaxException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
//
//                HttpEntity entity = response.getEntity();
//                BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
//                InputStream input = b_entity.getContent();
//
//                Bitmap bitmap = BitmapFactory.decodeStream(input);
//                return bitmap;
//
//            } catch (MalformedURLException e) {
//                Log.e("log", "bad url", e);
//                return null;
//            } catch (IOException e) {
//                Log.e("log", "io error", e);
//                return null;
//            }
        }
 
        protected void onProgressUpdate(String... progress) {
            // NO OP
        }
 
        protected void onPostExecute(Bitmap ret) {
            if (ret != null) {
                Log.i("ImageLoadTask", "Successfully loaded " + name + " image");
                image = ret;
                if (sta != null) {
                    // WHEN IMAGE IS LOADED NOTIFY THE ADAPTER
                    sta.notifyDataSetChanged();
                }
            } else {
                Log.e("ImageLoadTask", "Failed to load " + name + " image");
            }
        }
    }
	

}
