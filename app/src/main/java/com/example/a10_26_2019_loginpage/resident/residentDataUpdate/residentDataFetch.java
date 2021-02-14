package com.example.a10_26_2019_loginpage.resident.residentDataUpdate;

import android.content.Context;
import android.widget.Toast;

import com.example.a10_26_2019_loginpage.DataSingleton;
import com.example.a10_26_2019_loginpage.resident.HistoryRequestList;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class residentDataFetch {

    int exceptionStatus = 1;

    String data = "";
    private DataSingleton main = DataSingleton.getInstance();
    public Thread thread;
    public Thread thread2;

    private String orderTime;
    private String status;
    private double cost;
    private String content;

    private String lastUpdate;
    private String receiver;
    private String requestStatus;
    private int type;
    private String response;

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void fetchLastDelivery(final Context packageContext){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://sadbear-node-api.herokuapp.com/delivery/?ApiKey=hunter2&Content-Type=application-json");
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

                    int latest = 0;

                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject JO = (JSONObject) JA.get(i);
                        if (JO.getInt("id") == main.getUserID()) {
                            try{
                                if(latest < JO.getInt("seq")){
                                    latest = i;
                                }
                            }catch(Exception e){
                                //@TODO error handling
                            }
                        }
                    }
                    if (latest == 0) {
                        orderTime = null;
                    }else{
                        JSONObject JO = (JSONObject) JA.get(latest);
                        orderTime = JO.getString("order_time");
                        status = JO.getString("status");
                        cost = JO.getDouble("cost");
                        content = JO.getString("content");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    exceptionStatus = 0;
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();
        while(thread.isAlive());

        if(exceptionStatus == 0){
            Toast.makeText(packageContext, "Server is not responding try again a little later", Toast.LENGTH_SHORT).show();
            exceptionStatus = 1;
        }
    }

    public void fetchAllDeliveries(final ArrayList<HistoryRequestList> requestList, final Context packageContext){
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("https://sadbear-node-api.herokuapp.com/delivery/?ApiKey=hunter2&Content-Type=application-json");
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

                        for (int i = 0; i < JA.length(); i++) {
                            JSONObject JO = (JSONObject) JA.get(i);
                            if(JO.getString("status").equals("CLOSED") && JO.getInt("id") == main.getUserID()){
                                HistoryRequestList newOne = new HistoryRequestList();
                                newOne.setDeliverySeq(JO.getInt("seq"));
                                newOne.setDeliverirID(JO.getInt("id"));
                                newOne.setContent(JO.getString("content"));
                                newOne.setCost(JO.getDouble("cost"));
                                newOne.setDeliveryDate(JO.getString("order_time").substring(0,JO.getString("order_time").indexOf('T')));
                                newOne.setDeliveryStatus(JO.getString("status"));
                                requestList.add(newOne);
                            }
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        exceptionStatus = 0;
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        });

        thread.start();
        while(thread.isAlive());

        if(exceptionStatus == 0){
            Toast.makeText(packageContext, "Server is not responding try again a little later", Toast.LENGTH_SHORT).show();
            exceptionStatus = 1;
        }
    }

    public void fetchAllRequests(final ArrayList<HistoryRequestList> requestList,final Context packageContext ){
        thread2 = new Thread(new Runnable() {
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

                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject JO = (JSONObject) JA.get(i);
                        if (JO.getInt("_from") == main.getUserID() && JO.getString("status").equals("RESOLVED") ) {
                            //if (!JO.getString("status").equals("ACTIVE") && !JO.getString("support_result_time").equals("null")) {
                            HistoryRequestList requestListItem = new HistoryRequestList();

                            int _to = JO.getInt("_to");

                            if(_to == 5){
                                requestListItem.set_to("Concierge");
                            }else{
                                requestListItem.set_to("Manager");
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
                    exceptionStatus = 0;
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        thread2.start();
        while(thread2.isAlive());

        if(exceptionStatus == 0){
            Toast.makeText(packageContext, "Server is not responding try again a little later", Toast.LENGTH_SHORT).show();
            exceptionStatus = 1;
        }
    }
}
