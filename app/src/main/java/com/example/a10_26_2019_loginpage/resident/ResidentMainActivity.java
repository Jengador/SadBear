package com.example.a10_26_2019_loginpage.resident;
import com.example.a10_26_2019_loginpage.MainActivity;
import com.example.a10_26_2019_loginpage.fetchData;
import com.example.a10_26_2019_loginpage.resident.residentDataUpdate.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Transition;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a10_26_2019_loginpage.DataSingleton;
import com.example.a10_26_2019_loginpage.R;
import com.example.a10_26_2019_loginpage.resident.ResidentDeliveryActivity;
import com.example.a10_26_2019_loginpage.resident.ResidentHistoryActivity;
import com.example.a10_26_2019_loginpage.resident.ResidentMainActivity;
import com.example.a10_26_2019_loginpage.resident.ResidentSupportActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

public class ResidentMainActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        DataSingleton singleton = DataSingleton.getInstance();
        singleton.destroy();
        Intent registerIntent = new Intent(ResidentMainActivity.this, MainActivity.class);
        startActivity(registerIntent);
    }

    BottomNavigationView navigtion;
    FrameLayout closedFrame;
    FrameLayout openedViewFrame;
    FrameLayout openedEditFrame;
    ImageButton editProfileButton;
    ImageButton confirmEditButton;
    ImageButton cancelEditButton;

    EditText userNameEdit;
    EditText phoneNumberEdit;
    EditText hometownEdit;
    Spinner dayOfBirthEdit;
    Spinner monthOfBirthEdit;
    Spinner yearOfBirthEdit;
    TextView editErrorMessage;

    TextView userNameView;
    TextView phoneNumberView;
    TextView hometownView;
    TextView birthdateView;
    TextView doorNumberView;
    TextView balanceView;

    CheckBox garbageCheckBox;

    TextView deliveryDateView;
    TextView deliveryStatusView;
    TextView deliveryCostView;
    TextView deliveryContentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final DataSingleton dataHolder = DataSingleton.getInstance();

        fetchData process = new fetchData();
        process.work((String.valueOf(dataHolder.getUserID())),dataHolder.getPassword());

        while(process.thread.isAlive());



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_main_frame);

        navigtion = findViewById(R.id.residentNavigation);
        navigtion.setSelectedItemId(R.id.barMainFrame);

        closedFrame = findViewById(R.id.residentProfileFrameLayoutClosed);
        openedViewFrame = findViewById(R.id.residentProfileFrameLayoutOpenedView);
        openedEditFrame = findViewById(R.id.residentProfileFrameLayoutOpenedEdit);
        editProfileButton = findViewById(R.id.profileEditButton);
        confirmEditButton = findViewById(R.id.profileEditConfirmButtonEdit);
        cancelEditButton = findViewById(R.id.profileEditCancelButtonEdit);

        userNameEdit = findViewById(R.id.profileNameEditText);
        phoneNumberEdit = findViewById(R.id.profilePhoneEditText);
        hometownEdit = findViewById(R.id.profileHometownEditText);
        dayOfBirthEdit = findViewById(R.id.spinnerDayBirthdate);
        monthOfBirthEdit = findViewById(R.id.spinnerMonthBirthdate);
        yearOfBirthEdit = findViewById(R.id.spinnerYearBirthdate);
        editErrorMessage = findViewById(R.id.profileViewEditErrorMessage);

        userNameView = findViewById(R.id.profileNameView);
        phoneNumberView = findViewById(R.id.profilePhoneView);
        hometownView = findViewById(R.id.profileHometownView);
        birthdateView = findViewById(R.id.profileBirthdateView);
        doorNumberView = findViewById(R.id.profileDoornoView);
        balanceView = findViewById(R.id.residentBalanceView);

        deliveryDateView = findViewById(R.id.residentHighlightedDeliveryDateView);
        deliveryStatusView = findViewById(R.id.residentHighlightedDeliveryStatusView);
        deliveryCostView = findViewById(R.id.residentHighlightedDeliveryCostView);
        deliveryContentView = findViewById(R.id.residentHighlightedDeliveryContentView);

        residentDataFetch deliveryFetcher = new residentDataFetch();
        deliveryFetcher.fetchLastDelivery(ResidentMainActivity.this);

        if(deliveryFetcher.getOrderTime() != null){
            deliveryDateView.setText(deliveryFetcher.getOrderTime().substring(0,deliveryFetcher.getOrderTime().indexOf('T')));
            deliveryStatusView.setText(deliveryFetcher.getStatus());
            deliveryCostView.setText(String.valueOf(deliveryFetcher.getCost())+ "$");
            deliveryContentView.setText(deliveryFetcher.getContent());
        }else{
            deliveryDateView.setText("NO PREVIOUS DATA");
            deliveryStatusView.setText("CLOSED");
            deliveryCostView.setText("0.0$");
            deliveryContentView.setText("You haven't ordered anything before!");
        }


        garbageCheckBox = findViewById(R.id.garbageCheckBoxResident);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.MonthList, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        monthOfBirthEdit.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.DayList, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        dayOfBirthEdit.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.YearList, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        yearOfBirthEdit.setAdapter(adapter);

        try{
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(monthOfBirthEdit);
            popupWindow.setHeight(1000);
            popupWindow = (android.widget.ListPopupWindow) popup.get(dayOfBirthEdit);
            popupWindow.setHeight(1500);
            popupWindow = (android.widget.ListPopupWindow) popup.get(yearOfBirthEdit);
            popupWindow.setHeight(1500);

        }catch (Exception e){
            /* TODO */
        }

        garbageCheckBox.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                garbageCheckBox.setEnabled(false);
                profileUpdateData update = new profileUpdateData();
                update.updateCheckbox();
            }
        });

        navigtion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent registerIntent;
                switch (menuItem.getItemId()) {
                    case R.id.barHistoryFrame:
                        registerIntent = new Intent(ResidentMainActivity.this, ResidentHistoryActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barDeliveryFrame:
                        registerIntent = new Intent(ResidentMainActivity.this, ResidentDeliveryActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.barSupportFrame:
                        registerIntent = new Intent(ResidentMainActivity.this, ResidentSupportActivity.class);
                        startActivity(registerIntent);
                        overridePendingTransition(0, 0);
                        break;

                }
                return false;
            }
        });

        int i = 1;
        if (dataHolder.getName() == null || dataHolder.getName().equals("")) {  //if user has not initialized the profile
            closedFrame.setVisibility(View.VISIBLE);
        } else {
            userNameView.setText(dataHolder.getName());
            phoneNumberView.setText(dataHolder.getPhone());
            hometownView.setText(dataHolder.getHometown());
            birthdateView.setText(dataHolder.getBirthday());
            doorNumberView.setText(String.valueOf(dataHolder.getDoorNumber()));
            openedViewFrame.setVisibility(View.VISIBLE);
            garbageCheckBox.setChecked(dataHolder.isGarbageFlag());
            if(garbageCheckBox.isChecked()){
                garbageCheckBox.setEnabled(false);
            }
            balanceView.setText(dataHolder.getBalance() + "$");
        }

        editProfileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userNameEdit.setText(dataHolder.getName());
                phoneNumberEdit.setText(dataHolder.getPhone());
                hometownEdit.setText(dataHolder.getHometown());
                dayOfBirthEdit.setSelection(dataHolder.getDayOfBirth()-1);
                monthOfBirthEdit.setSelection(dataHolder.getMonthOfBirth()-1);
                yearOfBirthEdit.setSelection(dataHolder.getYearOfBirth() - 1920);

                editErrorMessage.setVisibility(View.INVISIBLE);

                openedViewFrame.setVisibility(View.INVISIBLE);
                openedEditFrame.setVisibility(View.VISIBLE);
            }
        });

        closedFrame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closedFrame.setVisibility(View.INVISIBLE);
                openedEditFrame.setVisibility(View.VISIBLE);
            }
        });

        confirmEditButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(userNameEdit.getText().toString().length() < 3 ){
                    if(editErrorMessage.getVisibility() == View.INVISIBLE){
                        editErrorMessage.setVisibility(View.VISIBLE);
                    }
                    if(editErrorMessage.getCurrentTextColor() == Color.BLACK){
                        editErrorMessage.setTextColor(Color.RED);
                    }else{
                        editErrorMessage.setTextColor(Color.BLACK);
                    }
                    editErrorMessage.setText("Name must be longer than 3 characters!");
                    editErrorMessage.setVisibility(View.VISIBLE);
                    return;
                }else if(phoneNumberEdit.getText().toString().length() != 10){
                    if(editErrorMessage.getVisibility() == View.INVISIBLE){
                        editErrorMessage.setVisibility(View.VISIBLE);
                    }
                    if(editErrorMessage.getCurrentTextColor() == Color.BLACK){
                        editErrorMessage.setTextColor(Color.RED);
                    }else{
                        editErrorMessage.setTextColor(Color.BLACK);
                    }
                    editErrorMessage.setText("Phone number should be 10 characters!");
                    editErrorMessage.setVisibility(View.VISIBLE);
                    return;
                }else if(hometownEdit.getText().toString().length() < 3){
                    if(editErrorMessage.getVisibility() == View.INVISIBLE){
                        editErrorMessage.setVisibility(View.VISIBLE);
                    }
                    if(editErrorMessage.getCurrentTextColor() == Color.BLACK){
                        editErrorMessage.setTextColor(Color.RED);
                    }else{
                        editErrorMessage.setTextColor(Color.BLACK);
                    }
                    editErrorMessage.setText("Hometown cannot be shorter than 3 characters!");
                    editErrorMessage.setVisibility(View.VISIBLE);
                    return;
                }else{
                    dataHolder.setName(userNameEdit.getText().toString());
                    dataHolder.setPhone(phoneNumberEdit.getText().toString());
                    dataHolder.setDayOfBirth(Integer.parseInt(dayOfBirthEdit.getSelectedItem().toString()));
                    dataHolder.setMonthOfBirth(monthOfBirthEdit.getSelectedItemPosition() + 1);
                    dataHolder.setYearOfBirth(Integer.parseInt(yearOfBirthEdit.getSelectedItem().toString()));
                    dataHolder.setHometown(hometownEdit.getText().toString());

                    profileUpdateData updater = new profileUpdateData();
                    updater.updateProfile();

                    userNameView.setText(dataHolder.getName());
                    phoneNumberView.setText(dataHolder.getPhone());
                    hometownView.setText(dataHolder.getHometown());
                    birthdateView.setText(dataHolder.getBirthday());

                    editErrorMessage.setVisibility(View.INVISIBLE);

                    openedEditFrame.setVisibility(View.INVISIBLE);
                    openedViewFrame.setVisibility(View.VISIBLE);
                }

            }
        });

        cancelEditButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userNameEdit.setText("");
                phoneNumberEdit.setText("");
                hometownEdit.setText("");
                dayOfBirthEdit.setSelection(0);
                monthOfBirthEdit.setSelection(0);
                yearOfBirthEdit.setSelection(0);
                openedEditFrame.setVisibility(View.INVISIBLE);
                if(dataHolder.getName() == null || dataHolder.getName().equals("")){
                    closedFrame.setVisibility(View.VISIBLE);
                }else {
                    openedViewFrame.setVisibility(View.VISIBLE);
                }
                editErrorMessage.setVisibility(View.INVISIBLE);
            }
        });

    }
}