package com.javierc.beta.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonHttpHandler {
    static InputStream is = null;
    static JSONObject jObj = new JSONObject();
    static String json = ""; 
    
    public JSONObject getJSONfromUrl(String url) throws JSONException, IllegalStateException, IOException{
        
        StringBuilder sb = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        StatusLine status = response.getStatusLine();
        
        
        int statusCode = status.getStatusCode();
        if (statusCode == 200) {
        	Log.d("http-200", "status 200");
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            
            while ((line = reader.readLine()) != null) {
            	sb.append(line);
            }
            
            json = sb.toString();            
            jObj = new JSONObject(json);
        }
        else {
        	Log.e("Error status code: ", String.valueOf(statusCode));   
        }
         
        return jObj;
    }
    
    public void postJSONfromUrl(String url, JSONObject data) throws ClientProtocolException, IOException{
        
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new StringEntity(data.toString()));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        client.execute(httpPost);

        return;
    }
}
