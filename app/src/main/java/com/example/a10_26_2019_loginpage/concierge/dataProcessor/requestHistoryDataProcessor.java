package com.example.a10_26_2019_loginpage.concierge.dataProcessor;

import android.content.Context;
import android.widget.Toast;

import com.example.a10_26_2019_loginpage.concierge.RequestList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class requestHistoryDataProcessor {

    public Thread thread;
    String data = "";
    int status = 1;

    public void fetchClosedRequests(final ArrayList<RequestList> requestList, final Context packageContext){
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
                        if (JO.getInt("_to") == 5 && JO.getString("status").equals("RESOLVED") ) {
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
                           requestListItem.setResponse(JO.getString("response"));
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
                            requestListItem.setSupport_result_time(JO.getString("support_result_time").substring(0,JO.getString("support_result_time").indexOf('T')));
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
}
