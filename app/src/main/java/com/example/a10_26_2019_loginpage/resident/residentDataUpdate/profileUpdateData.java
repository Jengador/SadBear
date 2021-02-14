package com.example.a10_26_2019_loginpage.resident.residentDataUpdate;
import com.example.a10_26_2019_loginpage.fetchData;
import com.example.a10_26_2019_loginpage.DataSingleton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class profileUpdateData {

    private DataSingleton main = DataSingleton.getInstance();
    Thread thread;
    Thread thread2;
    Thread thread3;
    Thread thread4;
    fetchData process;
    public void updateProfile(){

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Integer id = main.getUserID();
                String password = main.getPassword();
                Integer account_type = main.getAccountType();
                Integer status = main.getStatus();
                String name=main.getName();
                String hometown=main.getHometown();
                String birthday=main.getBirthday();
                String phone=main.getPhone();
                Boolean garbage_flag = main.isGarbageFlag();
                Double balance = main.getBalance();
                Integer door_no = main.getDoorNumber();

                fetchData process = new fetchData();
                process.work((String.valueOf(main.getUserID())),main.getPassword());
                while(process.thread.isAlive());

                main.setName(name);
                main.setPhone(phone);
                main.setBirthdayUpdateProfile(birthday);
                main.setHometown(hometown);

                process.update((String.valueOf(main.getUserID())),
                        main.getPassword(),
                        (String.valueOf(main.getAccountType())),
                        (String.valueOf(main.getStatus())),
                        (String.valueOf(main.getName())),
                        main.getHometown(),
                        main.getBirthdayUpdateProfile(),
                        main.getPhone(),
                        (String.valueOf(main.isGarbageFlag())),
                        (String.valueOf(main.getBalance())),
                        (String.valueOf(main.getDoorNumber()))
                );

                main.setUserID(id);
                main.setPassword(password);
                main.setAccountType(account_type);
                main.setStatus(status);
                main.setName(name);
                main.setPhone(phone);
                main.setBirthdayUpdateProfile(birthday);
                main.setHometown(hometown);
                main.setGarbageFlag(garbage_flag);
                main.setBalance(balance);
                main.setDoorNumber(door_no);
            }
        });

        thread.start();
        while(thread.isAlive());
    }
    public void updateCheckbox(){

        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {

                fetchData process = new fetchData();
                process.GarbageUpdate((String.valueOf(main.getUserID())));
                while(process.thread.isAlive());
            }
        });

        thread2.start();
        while(thread2.isAlive());
    }
    public void createSup(final String receiver, final String type, final String detail){



        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {

                fetchData process = new fetchData();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                process.createSupport(type,String.valueOf(main.getUserID()),receiver,detail,date);
            }
        });

        thread3.start();
        while(thread3.isAlive());
    }
    public void createDel(final String description){



        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {

                fetchData process = new fetchData();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                process.createDelivery(String.valueOf(main.getUserID()),date,description);
            }
        });

        thread3.start();
    }
    public void supportlist(){

        thread4 = new Thread(new Runnable() {
            @Override
            public void run() {

                fetchData process = new fetchData();

                process.supportList(String.valueOf(main.getUserID()));
            }
        });
        thread4.start();
        while(thread4.isAlive());
    }
}
