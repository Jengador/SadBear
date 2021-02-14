package com.example.a10_26_2019_loginpage.manager.managerDataUpdate;

import android.content.Context;
import android.widget.Toast;

import com.example.a10_26_2019_loginpage.DataSingleton;
import com.example.a10_26_2019_loginpage.manager.Balance;

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

public class managerDataFetch {

    int status = 1;

    String data = "";
    private DataSingleton main = DataSingleton.getInstance();
    public Thread thread;

    int doorNo;
    double balance;

    String name;
    public int getdoorNo() {
        return doorNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getname() {
        return name;
    }

    /*public void fetchLastResidentBalance(){
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

                    int latest = 0;

                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject JO = (JSONObject) JA.get(i);
                            try{
                                if(latest < JO.getInt("seq")){
                                    latest = i;
                                }
                            }catch(Exception e){
                                //@TODO error handling
                            }
                    }
                    if (latest == 0) {
                        name = null;
                    }else{
                        JSONObject JO = (JSONObject) JA.get(latest);
                        name = JO.getString("name");
                        balance = JO.getDouble("balance");
                        doorNo = JO.getInt("door_no");
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
        while(thread.isAlive());
    }*/

    public void fetchAllResidentsBalance(final ArrayList<Balance> listItems, final Context packageContext){
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

                    int counter = 0;

                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject JO = (JSONObject) JA.get(i);
                            try{
                                Balance item = new Balance();
                                item.setName(JO.getString("name"));
                                item.setDoor_number(JO.getInt("door_no"));
                                item.setBalance_amount(JO.getDouble("balance"));
                                item.setId(JO.getInt("id" ));
                                item.setStatus("NOT UPDATED");
                                listItems.add(item);
                                counter++;
                            }catch(Exception e){
                                //@TODO error handling
                            }

                    }

                    if(counter == 0){
                        listItems.add(new Balance());
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
