package com.example.a10_26_2019_loginpage.concierge;

import com.example.a10_26_2019_loginpage.DataSingleton;
import com.example.a10_26_2019_loginpage.MainActivity;
import com.example.a10_26_2019_loginpage.concierge.dataProcessor.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Transition;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a10_26_2019_loginpage.R;
import com.example.a10_26_2019_loginpage.concierge.ConciergeDeliveryActivity;
import com.example.a10_26_2019_loginpage.concierge.ConciergeHistoryActivity;
import com.example.a10_26_2019_loginpage.concierge.ConciergeMainActivity;
import com.example.a10_26_2019_loginpage.concierge.ConciergeSupportActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConciergeMainActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        DataSingleton singleton = DataSingleton.getInstance();
        singleton.destroy();
        Intent registerIntent = new Intent(ConciergeMainActivity.this, MainActivity.class);
        startActivity(registerIntent);
    }

    BottomNavigationView navigtion;
    private List<String> listDataHeader;
    HashMap<String, List<GarbageList>> listDataChild;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    ArrayList<GarbageList> garbagerList =new ArrayList<GarbageList>();
    ArrayList<GarbageList> deliveryList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concierge_main_frame);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<GarbageList>>();
        expListView = (ExpandableListView) findViewById(R.id.conciergeMainFrameExpandable);

        listDataHeader.add("GARBAGE SERVICE");
        listDataHeader.add("DELIVERY SERVICE");

        garbageDataProcessor garbageProcess = new garbageDataProcessor();
        garbageProcess.work(garbagerList, ConciergeMainActivity.this);

        deliveryDataProcessor deliveryProcess = new deliveryDataProcessor();
        deliveryProcess.list(deliveryList, ConciergeMainActivity.this);

        listDataChild.put(listDataHeader.get(0), garbagerList);
        listDataChild.put(listDataHeader.get(1), deliveryList);

        listAdapter = new com.example.a10_26_2019_loginpage.concierge.ExpandableListAdapter(this, listDataHeader, listDataChild) {
        };
        expListView.setAdapter(listAdapter);

        expListView.expandGroup(0);
        expListView.expandGroup(1);

        navigtion = findViewById(R.id.conciergeNavigation);
        navigtion.setSelectedItemId(R.id.barMainFrame);

        navigtion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent registerIntent;
                switch (menuItem.getItemId()) {
                    case R.id.barHistoryFrame:
                        registerIntent = new Intent(ConciergeMainActivity.this, ConciergeHistoryActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barDeliveryFrame:
                        registerIntent = new Intent(ConciergeMainActivity.this, ConciergeDeliveryActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barSupportFrame:
                        registerIntent = new Intent(ConciergeMainActivity.this, ConciergeSupportActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;

                }
                return false;
            }
        });
    }
}