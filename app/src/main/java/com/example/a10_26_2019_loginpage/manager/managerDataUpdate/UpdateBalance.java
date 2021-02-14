package com.example.a10_26_2019_loginpage.manager.managerDataUpdate;

import com.example.a10_26_2019_loginpage.manager.Balance;

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

public class UpdateBalance {
    public Thread thread;
    public void updateBalance(final int id, final double balance) {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {

                try{
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/users_account/add_balance/?ApiKey=hunter2&Content-Type=application-json");
                    Map<String,Object> paramss = new LinkedHashMap<>();
                    paramss.put("id", id);
                    paramss.put("balance", balance);
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
