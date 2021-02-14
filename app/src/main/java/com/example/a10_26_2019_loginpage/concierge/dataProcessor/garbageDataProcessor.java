package com.example.a10_26_2019_loginpage.concierge.dataProcessor;

import android.content.Context;
import android.widget.Toast;

import com.example.a10_26_2019_loginpage.concierge.GarbageList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class garbageDataProcessor {

    String data = "";
    public Thread thread;
    public Thread thread2;
    int status = 1;

    public void work(final ArrayList<GarbageList> garbagerList, final Context packageContext) {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/users_account/?ApiKey=hunter2&Content-Type=application-json");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while (line != null) {
                        line = bufferedReader.readLine();
                        data = data + line;
                    }


                    JSONArray JA = new JSONArray(data);

                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject JO = (JSONObject) JA.get(i);
                        if(JO.getBoolean("garbage_flag")){
                            GarbageList newOne = new GarbageList();
                            newOne.setDoor_number(JO.getInt("door_no"));
                            newOne.setName(JO.getString("name"));
                            newOne.setId(JO.getInt("id"));
                            garbagerList.add(newOne);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    status = 0;
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }



        });
        thread.start();

        while(thread.isAlive());

        if(status == 0){
            Toast.makeText(packageContext, "Server is not responding try again a little later", Toast.LENGTH_SHORT).show();
            status = 1;
        }
    }

    public void collectGarbage(final int id) {
        thread2 = new Thread(new Runnable() {

            @Override
            public void run() {

            try{
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/users_account/update_garbage/?ApiKey=hunter2&Content-Type=application-json");
                    Map<String,Object> paramss = new LinkedHashMap<>();
                    paramss.put("id", id);
                    paramss.put("garbage_flag", false);
                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : paramss.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    InputStream inputstream;
                    while(true){
                        try{
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestMethod("PUT");
                            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.getOutputStream().write(postDataBytes);
                            inputstream = httpURLConnection.getInputStream();
                            break;
                        }catch (IOException e){
                            try{
                                Thread.sleep(1000);
                            }catch (Exception y){
                                y.printStackTrace();
                            }
                        }
                    }

                    Reader in = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"));

                    for (int c; (c = in.read()) >= 0;)
                        System.out.print((char)c);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    status = 0;
                    e.printStackTrace();
                }
            }
        });

        thread2.start();
    }
}
