package com.example.a10_26_2019_loginpage.manager.dataProcessor;

import android.content.Context;
import android.widget.Toast;

import com.example.a10_26_2019_loginpage.manager.RequestList;

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
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class newRequestSupportDataProcessor {
    public Thread thread;
    String data = "";
    int status = 1;

    public void fetchActiveRequests(final ArrayList<RequestList> requestList,  final Context packageContext){
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/supports/?ApiKey=hunter2&Content-Type=application-json");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    data = "";
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
                        if (JO.getInt("_to") == 7 && !JO.getString("status").equals("RESOLVED") ) {
                            //if (!JO.getString("status").equals("ACTIVE") && !JO.getString("support_result_time").equals("null")) {
                            RequestList requestListItem = new RequestList();

                            int _from = JO.getInt("_from");

                            for(int j = 0; j<JA2.length(); j++){
                                JSONObject JO2 = (JSONObject) JA2.get(j);
                                if(JO2.getInt("id") == _from){
                                    requestListItem.set_from("(No: "+ JO2.getInt("door_no") + ") " + JO2.getString("name"));
                                }
                            }

                            requestListItem.setId(JO.getInt("id"));
                            requestListItem.setDetail(JO.getString("detail"));
//                            requestListItem.setResponse(JO.getString("response"));
                            int requestType = JO.getInt("issue_type");
                            if(requestType==0)
                                requestListItem.setIssue_type("Technical Support");
                            else if(requestType==1)
                                requestListItem.setIssue_type("Managerial Support");
                            else if(requestType==2)
                                requestListItem.setIssue_type("Product pool extension");
                            else if(requestType==3)
                                requestListItem.setIssue_type("Noise complaint");
                            else if(requestType==4)
                                requestListItem.setIssue_type("Guest Park");
                            else
                                requestListItem.setIssue_type("Other");
                            requestListItem.setSupport_send_time(JO.getString("support_send_time").substring(0,JO.getString("support_send_time").indexOf('T')));
//                            requestListItem.setSupport_result_time(JO.getString("support_result_time").substring(0,JO.getString("support_result_time").indexOf('T')));
                            requestList.add(requestListItem);
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

    public void transferToConcierge(final int id){
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/supports/update_s/?ApiKey=hunter2&Content-Type=application-json");
                    Map<String,Object> paramss = new LinkedHashMap<>();
                    paramss.put("id", id);
                    paramss.put("status", "DIRECTED");
                    paramss.put("_to", 5);
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
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void respondRequest(final RequestList requestList){
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/supports/update_rtss/?ApiKey=hunter2&Content-Type=application-json");
                    Map<String,Object> paramss = new LinkedHashMap<>();
                    paramss.put("id", requestList.getId());
                    paramss.put("status", "RESOLVED");
                    paramss.put("response", requestList.getResponse());
                    paramss.put("support_result_time", Calendar.getInstance().getTime());
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
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
