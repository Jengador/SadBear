package com.example.a10_26_2019_loginpage;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by İbrahim Yilgor on 27/11/2019.
 */



public class fetchData {
    String data = "";
    String data2 = "";
    String dataParsed = "";
    String singleParsed = "";
    private DataSingleton main = DataSingleton.getInstance();
    public Thread thread;

    public void work(final String... params) {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/users_account/?ApiKey=hunter2&Content-Type=application-json");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
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
                        if (String.valueOf(JO.getInt("id")).equals(params[0]) && (JO.getString("password").equals(params[1])) && JO.getInt("status") == 1) {
                            main.setUserID(JO.getInt("id"));
                            main.setPassword(JO.getString("password"));
                            main.setName(JO.getString("name"));
                            main.setAccountType(JO.getInt("account_type"));
                            main.setStatus(JO.getInt("status"));
                            main.setBirthday(JO.getString("birth_date"));
                            main.setHometown(JO.getString("hometown"));
                            main.setPhone(JO.getString("phone"));
                            main.setDoorNumber(JO.getInt("door_no"));
                            if(main.getAccountType() == 3){
                                main.setGarbageFlag(JO.getBoolean("garbage_flag"));
                            }
                            main.setBalance(JO.getDouble("balance"));
                            return;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }



        });
        thread.start();
    }
    public void update(final String... params) {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/users_account/update/?ApiKey=hunter2&Content-Type=application-json");
                    Map<String,Object> paramss = new LinkedHashMap<>();
                    paramss.put("id", params[0]);
                    paramss.put("password", params[1]);
                    paramss.put("account_type", params[2]);
                    paramss.put("status", params[3]);
                    paramss.put("name", params[4]);
                    paramss.put("hometown", params[5]);
                    paramss.put("birth_date", params[6]);
                    paramss.put("phone", params[7]);
                    paramss.put("garbage_flag", params[8]);
                    paramss.put("balance", params[9]);
                    paramss.put("door_no", params[10]);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : paramss.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("PUT");
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.getOutputStream().write(postDataBytes);
                    InputStream inputstream=httpURLConnection.getInputStream();

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
    public void createSupport(final String... params) {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/supports/create/?ApiKey=hunter2&Content-Type=application-json");
                    Map<String,Object> paramss = new LinkedHashMap<>();
                    Integer _to;
                    Integer issueType;
                    if(params[0].equals("Technical Support"))
                        issueType=0;
                    else if(params[0].equals("Managerial Support"))
                        issueType=1;
                    else if(params[0].equals("Product pool extension"))
                        issueType=2;
                    else if(params[0].equals("Noise complaint"))
                        issueType=3;
                    else if(params[0].equals("Guest Park"))
                        issueType=4;
                    else
                        issueType=5;
                    paramss.put("issue_type", issueType);
                    paramss.put("_from", Integer.parseInt(params[1]));
                    if(params[2].equals("Concierge"))
                        _to=5;
                    else
                        _to=7;
                    paramss.put("_to",_to);
                    paramss.put("detail", params[3]);
                    paramss.put("support_send_time", params[4]);
                    paramss.put("status", "ACTIVE");

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : paramss.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.getOutputStream().write(postDataBytes);
                    InputStream inputstream=httpURLConnection.getInputStream();

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
    public void GarbageUpdate(final String... params) {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {

                try{
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/users_account/update_garbage/?ApiKey=hunter2&Content-Type=application-json");
                    Map<String,Object> paramss = new LinkedHashMap<>();
                    paramss.put("id", Integer.parseInt(params[0]));
                    paramss.put("garbage_flag", true);
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
    public void createDelivery(final String... params) {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String data="";
                    Character control=' ';
                    Double cost=0.0;
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/delivery/create/?ApiKey=hunter2&Content-Type=application-json");
                    Map<String,Object> paramss = new LinkedHashMap<>();
                    paramss.put("id", Integer.parseInt(params[0]));
                    paramss.put("order_time", params[1]);
                    paramss.put("status","ACTIVE");
                    paramss.put("cost",cost);
                    for(int i=0;i<params[2].length();i++)
                    {
                        control=params[2].charAt(i);
                        if(control.equals('\n')&&i!=0)
                            data+=",";
                        else if(i!=0)
                            data+=params[2].charAt(i);
                    }
                    paramss.put("content", data);

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
                            httpURLConnection.setRequestMethod("POST");
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
    public void supportList(final String... params) {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/supports/?ApiKey=hunter2&Content-Type=application-json");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    InputStream inputStream = httpURLConnection.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while (line != null) {
                        line = bufferedReader.readLine();
                        data = data + line;
                    }

                    JSONArray JA = new JSONArray(data);

                    String latest="0000-00-00T";

                    main.setLatestRequestDate("0000-00-00T");
                    main.setLatestRequestReceiver(0);
                    main.setLatestRequestStatus("-");
                    main.setLatestRequestType(6);
                    main.setLatestRequestResponse("NO RESPONSE");

                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject JO = (JSONObject) JA.get(i);
                        if (String.valueOf(JO.getInt("_from")).equals(params[0]) && (JO.getString("status").equals("RESOLVED")) && JO.getString("support_result_time").compareTo(latest)>0&&(JO.getString("support_result_time").length()>6)) {
                            latest=JO.getString("support_result_time");
                            main.setLatestRequestDate(latest);
                            main.setLatestRequestReceiver(JO.getInt("_to"));
                            main.setLatestRequestStatus(JO.getString("status"));
                            main.setLatestRequestType(JO.getInt("issue_type"));
                            main.setLatestRequestResponse(JO.getString("response"));
                        }
                    }
                    return;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }



        });
        thread.start();
        while(thread.isAlive());
    }
}