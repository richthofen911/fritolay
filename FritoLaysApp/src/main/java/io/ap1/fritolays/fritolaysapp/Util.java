package io.ap1.fritolays.fritolaysapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Utility class
 * 
 * @author Samson Kirk-Koffi
 * @author Ramanathan
 * @since 01/14/2014
 *
 */
public class Util {
	 private static final String TAG = "Util";

    /**
     * validate string for not null and not empty
     * 
     * @param val string value
     * @return true if not null and not empty
     */
    public static boolean validateString(String val) {
        if(val != null && !val.isEmpty() && !val.equalsIgnoreCase("null"))
            return true;
        else
            return false;
    } // end validateString
    
    public static String jsonGet(String key, JSONObject jsonObj) {
        assert key != null : jsonObj;
        assert jsonObj != null;
        Object val = jsonObj.opt(key);
        if (val == null)
            return null;
        if (JSONObject.NULL.equals(val))
            return null;
        String s = val.toString();
        return s;
    } // end jsonGet

    @SuppressWarnings("deprecation")
    public static Date parseDate(String c) {
        Date _createdAt = new Date();
        return _createdAt;
    } // end parseDate

    @SuppressLint("SimpleDateFormat")
    static final DateFormat dfMarko = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String unencode(String text) {
        if (text == null)
            return null;
        
        text = text.replace("&quot;", "\"");
        text = text.replace("&apos;", "'");
        text = text.replace("&nbsp;", " ");
        text = text.replace("&amp;", "&");
        text = text.replace("&gt;", ">");
        text = text.replace("&lt;", "<");

        // zero-byte chars are a rare but annoying occurrence
        if (text.indexOf(0) != -1) {
            text = text.replace((char) 0, ' ').trim();
        } // end if
        
        return text;
    } // end unencode

    /**
     * Convert to a URI, or return null if this is badly formatted
     */
    public static URI URI(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            return null; // Bad syntax
        } // end try-catch
    } // end URI

    /**
     * get image from URL as a bitmap
     * 
     * @param src
     * @return
     */
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } // end try-catch
    } // end getBitmapFromURL
    
} // end Util
