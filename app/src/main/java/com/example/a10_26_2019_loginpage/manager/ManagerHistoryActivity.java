package com.example.a10_26_2019_loginpage.manager;

import com.example.a10_26_2019_loginpage.DataSingleton;
import com.example.a10_26_2019_loginpage.MainActivity;
import com.example.a10_26_2019_loginpage.manager.dataProcessor.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.a10_26_2019_loginpage.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManagerHistoryActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        DataSingleton singleton = DataSingleton.getInstance();
        singleton.destroy();
        Intent registerIntent = new Intent(ManagerHistoryActivity.this, MainActivity.class);
        startActivity(registerIntent);
    }

    BottomNavigationView navigtion;

    ArrayList<RequestList> requestList =new ArrayList<RequestList>();
    private List<String> listDataHeader;
    HashMap<String, List<RequestList>> listDataChild;
    ExpandableListView expListView;
    ExpandableListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_history);

        navigtion = findViewById(R.id.managerNavigation);
        navigtion.setSelectedItemId(R.id.barHistoryFrame);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<RequestList>>();
        expListView = (ExpandableListView) findViewById(R.id.managerHistoryFrameExpendable);

        listDataHeader.add("CLOSED REQUESTS");

        requestHistoryDataProcessor requestHistoryDataProcessor = new requestHistoryDataProcessor();
        requestHistoryDataProcessor.fetchClosedRequests(requestList, ManagerHistoryActivity.this);

        listDataChild.put(listDataHeader.get(0), requestList);

        listAdapter = new ExpandableListAdapterRequestHistory(this, listDataHeader, listDataChild) {
        };
        expListView.setAdapter(listAdapter);

        expListView.expandGroup(0);

        navigtion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent registerIntent;
                switch(menuItem.getItemId()){
                    case R.id.barMainFrame:
                        registerIntent = new Intent(ManagerHistoryActivity.this, ManagerMainActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barDeliveryFrame:
                        registerIntent = new Intent(ManagerHistoryActivity.this, ManagerDeliveryActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barSupportFrame:
                        registerIntent = new Intent(ManagerHistoryActivity.this, ManagerSupportActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;

                }
                return false;
            }
        });
    }
}
