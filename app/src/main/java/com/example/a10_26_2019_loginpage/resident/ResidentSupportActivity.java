package com.example.a10_26_2019_loginpage.resident;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a10_26_2019_loginpage.DataSingleton;
import com.example.a10_26_2019_loginpage.MainActivity;
import com.example.a10_26_2019_loginpage.R;
import com.example.a10_26_2019_loginpage.resident.residentDataUpdate.profileUpdateData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ResidentSupportActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        DataSingleton singleton = DataSingleton.getInstance();
        singleton.destroy();
        Intent registerIntent = new Intent(ResidentSupportActivity.this, MainActivity.class);
        startActivity(registerIntent);
    }

    BottomNavigationView navigtion;

    private DataSingleton dataHolder = DataSingleton.getInstance();

    Spinner receiverSpinner;
    Spinner typeSpinner;
    EditText newRequestDescription;
    CardView submitButton;

    TextView latestRequestDate;
    TextView latestRequestReceiver;
    TextView latestRequestStatus;
    TextView latestRequestType;
    TextView latestRequestResponse;

    TextView invalidNewRequest;
    TextView requestSentMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_support);


        invalidNewRequest = findViewById(R.id.invalidNewRequestLabel);
        requestSentMessage = findViewById(R.id.newRequestSentMessage);


        newRequestDescription = findViewById(R.id.residentSupportNewRequestDescriptionEditText);
        latestRequestDate = findViewById(R.id.residentSupportLatestRequestDateView);
        latestRequestReceiver = findViewById(R.id.residentSupportLatestRequestReceiverView);
        latestRequestStatus = findViewById(R.id.residentSupportLatestRequestStatusView);
        latestRequestType = findViewById(R.id.residentSupportLatestRequestTypeView);
        latestRequestResponse = findViewById(R.id.residentSupportLatestRequestResponseDescriptionView);

        profileUpdateData support_list = new profileUpdateData();
        support_list.supportlist();

        if(dataHolder.getLatestRequestReceiver()==7)
            latestRequestReceiver.setText("Manager");
        else if(dataHolder.getLatestRequestReceiver()==5)
            latestRequestReceiver.setText("Concierge");
        else
            latestRequestReceiver.setText(" ");

        if(dataHolder.getLatestRequestType()==0)
            latestRequestType.setText("Technical Support");
        else if(dataHolder.getLatestRequestType()==1)
            latestRequestType.setText("Managerial Support");
        else if(dataHolder.getLatestRequestType()==2)
            latestRequestType.setText("Product pool extension");
        else if(dataHolder.getLatestRequestType()==3)
            latestRequestType.setText("Noise complaint");
        else if(dataHolder.getLatestRequestType()==4)
            latestRequestType.setText("Guest Park");
        else if(dataHolder.getLatestRequestType()==5)
            latestRequestType.setText("Other");
        else
            latestRequestType.setText(" ");

        latestRequestDate.setText(dataHolder.getLatestRequestDate());
        //latestRequestReceiver.setText(Integer.toString(dataHolder.getLatestRequestReceiver()).toString());
        latestRequestStatus.setText(dataHolder.getLatestRequestStatus());
        //latestRequestType.setText(Integer.toString(dataHolder.getLatestRequestType()).toString());
        latestRequestResponse.setText(dataHolder.getLatestRequestResponse());

        submitButton = findViewById(R.id.residentSupportNewRequestSubmitButton);

        typeSpinner = findViewById(R.id.residentSupportNewRequestTypeSpinner);
        receiverSpinner = findViewById(R.id.residentSupportNewRequestTargetSpinner);
        navigtion = findViewById(R.id.residentNavigation);
        navigtion.setSelectedItemId(R.id.barSupportFrame);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.requestReceiverArray, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        receiverSpinner.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.requestTypeArray, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        typeSpinner.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String typedReceiver =  receiverSpinner.getSelectedItem().toString();
                String typedType =  typeSpinner.getSelectedItem().toString();
                String typedDescription =  newRequestDescription.getText().toString();

                if(typedDescription.length() <= 10){
                    requestSentMessage.setVisibility(View.INVISIBLE);
                    if(invalidNewRequest.getVisibility() == View.INVISIBLE){
                        invalidNewRequest.setVisibility(View.VISIBLE);
                    }
                    if(invalidNewRequest.getCurrentTextColor() == Color.BLACK){
                        invalidNewRequest.setTextColor(Color.RED);
                    }else{
                        invalidNewRequest.setTextColor(Color.BLACK);
                    }
                    return;
                }

                invalidNewRequest.setVisibility(View.INVISIBLE);
                profileUpdateData supportupdate = new profileUpdateData();
                supportupdate.createSup(typedReceiver,typedType,typedDescription);

                requestSentMessage.setVisibility(View.VISIBLE);
                if(requestSentMessage.getVisibility() == View.INVISIBLE){
                    requestSentMessage.setVisibility(View.VISIBLE);
                }
                if(requestSentMessage.getCurrentTextColor() == Color.RED){
                    requestSentMessage.setTextColor(Color.BLACK);
                }else{
                    requestSentMessage.setTextColor(Color.RED);
                }

                receiverSpinner.setSelection(0);
                typeSpinner.setSelection(0);
                newRequestDescription.setText("");


            }

        });


        navigtion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent registerIntent;
                switch(menuItem.getItemId()){
                    case R.id.barMainFrame:
                        registerIntent = new Intent(ResidentSupportActivity.this, ResidentMainActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barHistoryFrame:
                        registerIntent = new Intent(ResidentSupportActivity.this, ResidentHistoryActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barDeliveryFrame:
                        registerIntent = new Intent(ResidentSupportActivity.this, ResidentDeliveryActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });
    }
}
