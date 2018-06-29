package com.example.haako.policymaker.JSON;
import org.apache.http.HttpEntity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpResponse;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class JSONParser {

    static InputStream is = null;
    static JSONObject jsonObj ;
    static String json = "";

    // default no argument constructor for jsonpaser class
    public JSONParser() {

    }


    public JSONObject getJSONFromURL(final String url) {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
        HttpConnectionParams.setSoTimeout(httpParameters, 5000);
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(url);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder str = new StringBuilder();
            String strLine = null;
            while ((strLine = reader.readLine()) != null) {
                str.append(strLine + "\n");
            }
            is.close();
            json = str.toString();
        } catch (Exception e) {
            Log.e("Error", " something wrong with converting result " + e.toString());
        }
        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("json Parser", "" + e.toString());
        }
        return jsonObj;
    }




    public JSONObject makeHttpRequest(String url, String method, JSONObject object) {

        try {
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);
            if(method == "POST"){
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost httpPost = new HttpPost(url);
                StringEntity se = new StringEntity(object.toString(), HTTP.UTF_8);
                httpPost.setEntity(se);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            }else if(method == "GET"){
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                StringEntity se = new StringEntity(object.toString());
                url += "?" + se;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder str = new StringBuilder();
            String strLine;
            while ((strLine = reader.readLine()) != null) {
                str.append(strLine + "\n");
            }
            is.close();
            json = str.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

}