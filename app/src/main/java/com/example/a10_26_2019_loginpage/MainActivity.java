package com.example.a10_26_2019_loginpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.a10_26_2019_loginpage.concierge.ConciergeMainActivity;
import com.example.a10_26_2019_loginpage.manager.ManagerMainActivity;
import com.example.a10_26_2019_loginpage.resident.ResidentMainActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    int backButtonCount = 0;
    EditText mIDEditText;
    EditText mPasswordEditText;
    CardView mLoginButton;
    TextView mwrongCredentialEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIDEditText = findViewById(R.id.idEditText);
        mPasswordEditText = findViewById(R.id.passwordEditText);
        mLoginButton = findViewById(R.id.loginButton);
        mwrongCredentialEditText = findViewById(R.id.wrongCredentialEditText);

        mLoginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                fetchData process = new fetchData();
                process.work(mIDEditText.getText().toString(),mPasswordEditText.getText().toString());

                while(process.thread.isAlive());

                DataSingleton dataHolder = DataSingleton.getInstance();
                if(dataHolder.getAccountType()==2){
                    Intent registerIntent = new Intent(MainActivity.this, ConciergeMainActivity.class);
                    startActivity(registerIntent);
                }else if(dataHolder.getAccountType()==3){
                    Intent registerIntent = new Intent(MainActivity.this, ResidentMainActivity.class);
                    startActivity(registerIntent);
                }else if(dataHolder.getAccountType()==1){
                    Intent registerIntent = new Intent(MainActivity.this, ManagerMainActivity.class);
                    startActivity(registerIntent);
                    
                }else{
                    if(mwrongCredentialEditText.getVisibility() == View.INVISIBLE){
                        mwrongCredentialEditText.setVisibility(View.VISIBLE);
                    }
                    if(mwrongCredentialEditText.getCurrentTextColor() == Color.WHITE){
                        mwrongCredentialEditText.setTextColor(Color.RED);
                    }else{
                        mwrongCredentialEditText.setTextColor(Color.WHITE);
                    }

                }

            }
        });
    }

}
