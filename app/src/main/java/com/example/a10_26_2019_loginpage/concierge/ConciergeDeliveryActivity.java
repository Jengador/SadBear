package com.example.a10_26_2019_loginpage.concierge;

import com.example.a10_26_2019_loginpage.DataSingleton;
import com.example.a10_26_2019_loginpage.MainActivity;
import com.example.a10_26_2019_loginpage.concierge.dataProcessor.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a10_26_2019_loginpage.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ConciergeDeliveryActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        DataSingleton singleton = DataSingleton.getInstance();
        singleton.destroy();
        Intent registerIntent = new Intent(ConciergeDeliveryActivity.this, MainActivity.class);
        startActivity(registerIntent);
    }

    BottomNavigationView navigtion;

    EditText addProductNameEditText;
    EditText addProductBrandEditText;

    Spinner productNameSpinner;
    Spinner productBrandSpinner;

    TextView addProductMessageTextView;
    TextView deleteProductMessageTextView;

    Button addProductButton;
    Button removeProductButton;

    ArrayList<String> productList = new ArrayList<String>();
    ArrayList<String> productNameList = new ArrayList<String>();
    ArrayList<String> productBrandList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concierge_delivery);

        addProductBrandEditText = findViewById(R.id.addProductProductBrandEdit);
        addProductNameEditText = findViewById(R.id.addProductProductNameEdit);
        addProductMessageTextView = findViewById(R.id.addProductMessage);
        addProductButton = findViewById(R.id.addProductAddButton);

        productPoolDataProcessor addProcess = new productPoolDataProcessor();
        addProcess.fetchProduct(productList, ConciergeDeliveryActivity.this);

        if(productList != null){


            for(int i = 0; i< productList.size(); i++){
                String temp = productList.get(i);
                temp = temp.substring(0,temp.indexOf('('));

                if(!productNameList.contains(temp)){
                    productNameList.add(temp);
                }
            }


        }

        productNameSpinner = findViewById(R.id.deleteProductNameSpinner);
        productBrandSpinner = findViewById(R.id.deleteProductBrandSpinner);
        removeProductButton = findViewById(R.id.deleteProductRemoveButton);
        deleteProductMessageTextView = findViewById(R.id.deleteProductMessage);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, productNameList);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        productNameSpinner.setAdapter(adapter);

        adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, productBrandList);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        productBrandSpinner.setAdapter(adapter);


        productNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                productBrandList.clear();
                productBrandSpinner.setEnabled(true);
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
                productBrandSpinner.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                productBrandSpinner.setEnabled(false);
            }

        });



        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(addProductNameEditText.getText().toString().length() < 2 || addProductNameEditText.getText().toString().contains("(") || addProductNameEditText.getText().toString().contains(")")){
                    addProductMessageTextView.setText("  Please enter a valid product name!  ");
                }else if(addProductBrandEditText.getText().toString().length() < 2 || addProductBrandEditText.getText().toString().contains("(") || addProductBrandEditText.getText().toString().contains(")")){
                    addProductMessageTextView.setText("  Please enter a valid product brand!  ");
                }else{
                    try{

                        productPoolDataProcessor addProcess = new productPoolDataProcessor();

                        String temp = addProductNameEditText.getText().toString();
                        productList.add(temp + "(" + addProductBrandEditText.getText().toString() + ")");
                        if(!productNameList.contains(temp)){
                            productNameList.add(temp);

                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, productNameList);
                        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                        productNameSpinner.setAdapter(adapter);

                        adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, productBrandList);
                        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                        productBrandSpinner.setAdapter(adapter);

                        addProcess.addProduct(addProductNameEditText.getText().toString() + "(" + addProductBrandEditText.getText().toString() + ")", ConciergeDeliveryActivity.this);
                        addProductMessageTextView.setText("  Item has been added!  ");
                        addProductBrandEditText.setText("");
                        addProductNameEditText.setText("");


                    }catch (Exception e){
                        addProductMessageTextView.setText("  Please try later, server is not responding!  ");
                    }

                }

            }
        });

        removeProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productNameSpinner.getSelectedItemPosition() != -1 && productBrandSpinner.getSelectedItemPosition() != -1){
                    productPoolDataProcessor removeProcess = new productPoolDataProcessor();
                    removeProcess.removeProduct(productNameSpinner.getSelectedItem() + "(" + productBrandSpinner.getSelectedItem() + ")", ConciergeDeliveryActivity.this);

                    productBrandList.remove(productBrandSpinner.getSelectedItem().toString());
                    productList.remove(productNameSpinner.getSelectedItem() + "(" + productBrandSpinner.getSelectedItem() + ")");

                    if(productBrandList.size() == 0){
                        productNameList.remove(productNameSpinner.getSelectedItem().toString());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, productNameList);
                        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                        productNameSpinner.setAdapter(adapter);
                    }else{
                        productBrandSpinner.setSelection(-1);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, productBrandList);
                        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                        productBrandSpinner.setAdapter(adapter);

                    }

                    deleteProductMessageTextView.setText("Item has been deleted!");
                }else{
                    deleteProductMessageTextView.setText("Please select all the requirements!");
                }
            }
        });


        navigtion = findViewById(R.id.conciergeNavigation);
        navigtion.setSelectedItemId(R.id.barDeliveryFrame);

        navigtion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent registerIntent;
                switch(menuItem.getItemId()){
                    case R.id.barMainFrame:
                        registerIntent = new Intent(ConciergeDeliveryActivity.this, ConciergeMainActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barHistoryFrame:
                        registerIntent = new Intent(ConciergeDeliveryActivity.this, ConciergeHistoryActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barSupportFrame:
                        registerIntent = new Intent(ConciergeDeliveryActivity.this, ConciergeSupportActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;

                }
                return false;
            }
        });
    }
}
