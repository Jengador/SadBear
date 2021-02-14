package com.example.a10_26_2019_loginpage.resident;
import com.example.a10_26_2019_loginpage.DataSingleton;
import com.example.a10_26_2019_loginpage.MainActivity;
import com.example.a10_26_2019_loginpage.resident.residentDataUpdate.*;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.a10_26_2019_loginpage.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResidentHistoryActivity extends Activity {

    @Override
    public void onBackPressed() {
        DataSingleton singleton = DataSingleton.getInstance();
        singleton.destroy();
        Intent registerIntent = new Intent(ResidentHistoryActivity.this, MainActivity.class);
        startActivity(registerIntent);
    }

    BottomNavigationView navigtion;

    ArrayList<HistoryRequestList> deliveryList =new ArrayList<HistoryRequestList>();
    ArrayList<HistoryRequestList> requestList =new ArrayList<HistoryRequestList>();
    private List<String> listDataHeader;
    HashMap<String, List<HistoryRequestList>> listDataChild;
    ExpandableListView expListView;
    ExpandableListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_history);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<HistoryRequestList>>();
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        listDataHeader.add("CLOSED DELIVERIES");
        listDataHeader.add("CLOSED REQUESTS");

        navigtion = findViewById(R.id.residentNavigation);
        navigtion.setSelectedItemId(R.id.barHistoryFrame);

        residentDataFetch deliveryFetcher = new residentDataFetch();
        deliveryFetcher.fetchAllDeliveries(deliveryList, ResidentHistoryActivity.this);
        deliveryFetcher.fetchAllRequests(requestList, ResidentHistoryActivity.this);

        listDataChild.put(listDataHeader.get(0), deliveryList);
        listDataChild.put(listDataHeader.get(1), requestList);

        listAdapter = new com.example.a10_26_2019_loginpage.resident.ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        navigtion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent registerIntent;
                switch(menuItem.getItemId()){
                    case R.id.barMainFrame:
                        registerIntent = new Intent(ResidentHistoryActivity.this, ResidentMainActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barDeliveryFrame:
                        registerIntent = new Intent(ResidentHistoryActivity.this, ResidentDeliveryActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barSupportFrame:
                        registerIntent = new Intent(ResidentHistoryActivity.this, ResidentSupportActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;

                }
                return false;
            }
        });
    }
}
