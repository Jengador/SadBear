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

public class deliveryDataProcessor {

    String data = "";
    public Thread thread;
    int status = 1;

    public void list(final ArrayList<GarbageList> deliveryList, final Context packageContext){
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/delivery/?ApiKey=hunter2&Content-Type=application-json");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while (line != null) {
                        line = bufferedReader.readLine();
                        data = data + line;
                    }


                    JSONArray JA = new JSONArray(data);

                    URL url2 = new URL("https://sadbear-node-api.herokuapp.com/users_account/?ApiKey=hunter2&Content-Type=application-json");
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
                    InputStream inputStream2 = httpURLConnection2.getInputStream();
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
                    line = "";
                    data = "";
                    while (line != null) {
                        line = bufferedReader2.readLine();
                        data = data + line;
                    }
                    JSONArray JA2 = new JSONArray(data);

                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject JO = (JSONObject) JA.get(i);
                        if(!JO.getString("status").equals("CLOSED")){

                            GarbageList newOne = new GarbageList();

                            int _from = JO.getInt("id");

                            for(int j = 0; j<JA2.length(); j++){
                                JSONObject JO2 = (JSONObject) JA2.get(j);
                                if(JO2.getInt("id") == _from){
                                    newOne.setDeliverirName("(No: "+ JO2.getInt("door_no") + ") " + JO2.getString("name"));
                                }
                            }
                            newOne.setDeliverySeq(JO.getInt("seq"));
                            newOne.setDeliverirID(JO.getInt("id"));
                            newOne.setContent(JO.getString("content"));
                            newOne.setCost(JO.getDouble("cost"));
                            newOne.setDeliveryDate(JO.getString("order_time").substring(0,JO.getString("order_time").indexOf('T')));
                            newOne.setDeliveryStatus(JO.getString("status"));
                            deliveryList.add(newOne);
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

    public void updateDeliveryCost(final GarbageList deliveryList){
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/delivery/update_seq/?ApiKey=hunter2&Content-Type=application-json");
                    Map<String,Object> paramss = new LinkedHashMap<>();
                    paramss.put("seq", deliveryList.getDeliverySeq());
                    paramss.put("cost", deliveryList.getCost());
                    paramss.put("status", "PENDING");

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
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }
}
