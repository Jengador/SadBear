package com.example.a10_26_2019_loginpage.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.a10_26_2019_loginpage.DataSingleton;
import com.example.a10_26_2019_loginpage.MainActivity;
import com.example.a10_26_2019_loginpage.R;
import com.example.a10_26_2019_loginpage.manager.managerDataUpdate.managerDataFetch;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManagerMainActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        DataSingleton singleton = DataSingleton.getInstance();
        singleton.destroy();
        Intent registerIntent = new Intent(ManagerMainActivity.this, MainActivity.class);
        startActivity(registerIntent);
    }

    BottomNavigationView navigtion;
    ArrayList<Balance> listItems = new ArrayList<>();
    private List<String> listDataHeader;
    HashMap<String, List<Balance>> listDataChild;
    ExpandableListView expListView;
    ExpandableListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main_frame);

        navigtion = findViewById(R.id.managerNavigation);
        navigtion.setSelectedItemId(R.id.barMainFrame);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Balance>>();
        expListView = (ExpandableListView) findViewById(R.id.lvExp1);
        listDataHeader.add("BALANCES");

        managerDataFetch balanceFetcher = new managerDataFetch();
        balanceFetcher.fetchAllResidentsBalance(listItems, ManagerMainActivity.this);

        listDataChild.put(listDataHeader.get(0), listItems);
        listAdapter = new com.example.a10_26_2019_loginpage.manager.ExpandableListAdapter(this, listDataHeader, listDataChild){};
        expListView.setAdapter(listAdapter);

        expListView.expandGroup(0);

        navigtion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent registerIntent;
                switch (menuItem.getItemId()) {
                    case R.id.barHistoryFrame:
                        registerIntent = new Intent(ManagerMainActivity.this, ManagerHistoryActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barDeliveryFrame:
                        registerIntent = new Intent(ManagerMainActivity.this, ManagerDeliveryActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barSupportFrame:
                        registerIntent = new Intent(ManagerMainActivity.this, ManagerSupportActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;

                }
                return false;
            }
        });
    }
}