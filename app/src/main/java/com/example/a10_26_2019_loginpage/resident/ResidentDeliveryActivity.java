package com.example.a10_26_2019_loginpage.resident;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a10_26_2019_loginpage.DataSingleton;
import com.example.a10_26_2019_loginpage.MainActivity;
import com.example.a10_26_2019_loginpage.R;
import com.example.a10_26_2019_loginpage.concierge.dataProcessor.productPoolDataProcessor;
import com.example.a10_26_2019_loginpage.resident.residentDataUpdate.PendingDeliveryDataProcessor;
import com.example.a10_26_2019_loginpage.resident.residentDataUpdate.profileUpdateData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResidentDeliveryActivity extends AppCompatActivity {

    BottomNavigationView navigtion;

    @Override
    public void onBackPressed() {
        DataSingleton singleton = DataSingleton.getInstance();
        singleton.destroy();
        Intent registerIntent = new Intent(ResidentDeliveryActivity.this, MainActivity.class);
        startActivity(registerIntent);
    }

    ArrayList<String> productList = new ArrayList<String>();
    ArrayList<String> productNameList = new ArrayList<String>();
    ArrayList<String> productBrandList = new ArrayList<String>();

    TextView newDeliveryDescription;
    Spinner newDeliveryQuantity;
    Spinner newDeliveryItemNameSpinner;
    Spinner newDeliveryItemBrandSpinner;

    TextView invalidNewDelivery;
    TextView deliverySentMessage;
    TextView basketFullMessage;

    CardView addToBasketButton;
    CardView submitButton;
    CardView clearButton;

    ArrayList<PendingDeliveryInfo> pendingDeliveryList =new ArrayList<PendingDeliveryInfo>();
    private List<String> listDataHeader;
    HashMap<String, List<PendingDeliveryInfo>> listDataChild;
    ExpandableListView expListView;
    ExpandableListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_delivery);


        addToBasketButton = findViewById(R.id.residentDeliveryNewDeliveryAddItemButton);
        submitButton = findViewById(R.id.residentDeliveryNewDeliverySubmitButton);
        clearButton = findViewById(R.id.residentDeliveryNewDeliveryClearButton);

        newDeliveryQuantity = findViewById(R.id.residentDeliveryNewDeliveryCountSpinner);
        newDeliveryItemNameSpinner = findViewById(R.id.residentDeliveryNewDeliveryItemNameSpinner);
        newDeliveryItemBrandSpinner = findViewById(R.id.residentDeliveryNewDeliveryItemBrandSpinner);
        newDeliveryDescription = findViewById(R.id.residentDeliveryNewDeliveryBasketView);

        newDeliveryDescription.setMovementMethod(new ScrollingMovementMethod());

        invalidNewDelivery = findViewById(R.id.invalidNewDeliveryLabel);
        deliverySentMessage = findViewById(R.id.newDeliverySentMessage);
        basketFullMessage = findViewById(R.id.newDeliveryBasketFullMessage);

        productPoolDataProcessor addProcess = new productPoolDataProcessor();
        addProcess.fetchProduct(productList, ResidentDeliveryActivity.this);

        if(productList != null){
            for(int i = 0; i< productList.size(); i++){
                String temp = productList.get(i);
                temp = temp.substring(0,temp.indexOf('('));

                if(!productNameList.contains(temp)){
                    productNameList.add(temp);
                }
            }
        }

        ArrayAdapter<String> nameAdapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, productNameList);
        nameAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        newDeliveryItemNameSpinner.setAdapter(nameAdapter);

        nameAdapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, productBrandList);
        nameAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        newDeliveryItemBrandSpinner.setAdapter(nameAdapter);

        newDeliveryItemNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                productBrandList.clear();
                newDeliveryItemBrandSpinner.setEnabled(true);
                for(int i = 0; i< productList.size(); i++){

                    String temp = productList.get(i);
                    temp = temp.substring(0,temp.indexOf('('));

                    if (temp.equals(parentView.getSelectedItem())){
                        temp = productList.get(i);
                        temp = temp.substring(temp.indexOf('(')+1);
                        temp = temp.substring(0,temp.indexOf(')'));

                        if(!productBrandList.contains(temp)){
                            productBrandList.add(temp);
                        }
                    }
                }

                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, productBrandList);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                newDeliveryItemBrandSpinner.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                newDeliveryItemBrandSpinner.setEnabled(false);
            }

        });


        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(this,
                R.array.newDeliveryQuantityArray, android.R.layout.simple_spinner_dropdown_item);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        newDeliveryQuantity.setAdapter(quantityAdapter);

        addToBasketButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String typedDescription =  newDeliveryDescription.getText().toString();

                int counter = 0;

                if(newDeliveryItemNameSpinner.getSelectedItemPosition() == -1 || newDeliveryItemBrandSpinner.getSelectedItemPosition() == -1){
                    basketFullMessage.setText("  You have to pick both brand and name for the product!  ");
                    basketFullMessage.setVisibility(View.VISIBLE);
                    return;
                }

                for(int i = 0; i < typedDescription.length() ; i++){
                    if(typedDescription.charAt(i) == '\n'){
                        counter++;
                    }

                }

                if(counter == 5){
                    basketFullMessage.setText("  Your basket is full you cannot add more!  ");
                    if(basketFullMessage.getVisibility() == View.INVISIBLE){
                        basketFullMessage.setVisibility(View.VISIBLE);
                    }
                    if(basketFullMessage.getCurrentTextColor() == Color.BLACK){
                        basketFullMessage.setTextColor(Color.RED);
                    }else{
                        basketFullMessage.setTextColor(Color.BLACK);
                    }
                    return;
                }
                deliverySentMessage.setVisibility(View.INVISIBLE);
                newDeliveryDescription.setText(typedDescription + "\n" + newDeliveryQuantity.getSelectedItem().toString() + "  " + newDeliveryItemNameSpinner.getSelectedItem().toString()
                + "(" + newDeliveryItemBrandSpinner.getSelectedItem().toString() + ")");
                newDeliveryQuantity.setSelection(0);
                newDeliveryItemNameSpinner.setSelection(0);
            }

        });

        clearButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                newDeliveryDescription.setText("");
                basketFullMessage.setVisibility(View.INVISIBLE);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String typedDescription =  newDeliveryDescription.getText().toString();

                if(typedDescription == null || typedDescription.length() == 0 ){
                    deliverySentMessage.setVisibility(View.INVISIBLE);
                    if(invalidNewDelivery.getVisibility() == View.INVISIBLE){
                        invalidNewDelivery.setVisibility(View.VISIBLE);
                    }
                    if(invalidNewDelivery.getCurrentTextColor() == Color.BLACK){
                        invalidNewDelivery.setTextColor(Color.RED);
                    }else{
                        invalidNewDelivery.setTextColor(Color.BLACK);
                    }
                    return;
                }

                invalidNewDelivery.setVisibility(View.INVISIBLE);
                profileUpdateData delupdate = new profileUpdateData();
                delupdate.createDel(typedDescription);

                deliverySentMessage.setVisibility(View.VISIBLE);
                if(deliverySentMessage.getVisibility() == View.INVISIBLE){
                    deliverySentMessage.setVisibility(View.VISIBLE);
                }
                if(deliverySentMessage.getCurrentTextColor() == Color.RED){
                    deliverySentMessage.setTextColor(Color.BLACK);
                }else{
                    deliverySentMessage.setTextColor(Color.RED);
                }

                basketFullMessage.setVisibility(View.INVISIBLE);

                newDeliveryQuantity.setSelection(0);
                newDeliveryItemNameSpinner.setSelection(0);
                newDeliveryDescription.setText("");

            }

        });

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<PendingDeliveryInfo>>();
        expListView = (ExpandableListView) findViewById(R.id.residentDeliveryFrameExpandable);

        listDataHeader.add("PENDING DELIVERIES");

        PendingDeliveryDataProcessor requestHistoryDataProcessor = new PendingDeliveryDataProcessor();
        requestHistoryDataProcessor.list(pendingDeliveryList, ResidentDeliveryActivity.this);

        listDataChild.put(listDataHeader.get(0), pendingDeliveryList);

        listAdapter = new ExpandableListAdapterPendingDelivery(this, listDataHeader, listDataChild) {
        };
        expListView.setAdapter(listAdapter);

        expListView.expandGroup(0);


        navigtion = findViewById(R.id.residentNavigation);
        navigtion.setSelectedItemId(R.id.barDeliveryFrame);

        navigtion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent registerIntent;
                switch(menuItem.getItemId()){
                    case R.id.barMainFrame:
                        registerIntent = new Intent(ResidentDeliveryActivity.this, ResidentMainActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barHistoryFrame:
                        registerIntent = new Intent(ResidentDeliveryActivity.this, ResidentHistoryActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barSupportFrame:
                        registerIntent = new Intent(ResidentDeliveryActivity.this, ResidentSupportActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;

                }
                return false;
            }
        });
    }
}
