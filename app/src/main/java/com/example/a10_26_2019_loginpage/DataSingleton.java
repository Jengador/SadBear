package com.example.a10_26_2019_loginpage;

public class DataSingleton {

    private int userID;
    private String password;
    private String name;
    private int status;
    private String hometown;
    private String phone;
    private int doorNumber;
    private int accountType;
    private int dayOfBirth;
    private int monthOfBirth;
    private int yearOfBirth;
    private boolean garbageFlag;
    private double balance;

    private String latestRequestDate;
    private int latestRequestReceiver;
    private String latestRequestStatus;
    private int latestRequestType;
    private String latestRequestResponse;

    public String getLatestRequestDate() {
        return latestRequestDate;
    }

    public void setLatestRequestDate(String latestRequestDate) {
        String temp=latestRequestDate;
        temp = temp.substring(0,temp.indexOf('T'));
        this.latestRequestDate = temp;
    }

    public int getLatestRequestReceiver() {
        return latestRequestReceiver;
    }

    public void setLatestRequestReceiver(int latestRequestReceiver) {
        this.latestRequestReceiver = latestRequestReceiver;
    }

    public String getLatestRequestStatus() {
        return latestRequestStatus;
    }

    public void setLatestRequestStatus(String latestRequestStatus) {
        this.latestRequestStatus = latestRequestStatus;
    }

    public int getLatestRequestType() {
        return latestRequestType;
    }

    public void setLatestRequestType(int latestRequestType) {
        this.latestRequestType = latestRequestType;
    }

    public String getLatestRequestResponse() {
        return latestRequestResponse;
    }

    public void setLatestRequestResponse(String latestRequestResponse) {
        this.latestRequestResponse = latestRequestResponse;
    }

    private static DataSingleton INSTANCE;
    private DataSingleton(){

    }

    public static DataSingleton getInstance(){
        if(INSTANCE == null){
            INSTANCE = new DataSingleton();
        }

        return INSTANCE;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(int doorNumber) {
        this.doorNumber = doorNumber;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(int dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public int getMonthOfBirth() {
        return monthOfBirth;
    }

    public void setMonthOfBirth(int monthOfBirth) {
        this.monthOfBirth = monthOfBirth;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void setBirthday(String birth_date){
        if(birth_date==null){
            return;
        }
        String temp = (birth_date.substring(birth_date.indexOf('-')+1));
        setYearOfBirth(Integer.parseInt(birth_date.substring(0,birth_date.indexOf('-'))));
        setMonthOfBirth(Integer.parseInt(temp.substring(0,temp.indexOf('-'))));
        temp = temp.substring(temp.indexOf('-')+1);
        temp = temp.substring(0,temp.indexOf('T'));
        setDayOfBirth(Integer.parseInt(temp));

    }
    public void setBirthdayUpdateProfile(String birth_date){
        if(birth_date==null){
            return;
        }
        String temp = (birth_date.substring(birth_date.indexOf('-')+1));
        setDayOfBirth(Integer.parseInt(birth_date.substring(0,birth_date.indexOf('-'))));
        setMonthOfBirth(Integer.parseInt(temp.substring(0,temp.indexOf('-'))));
        temp = temp.substring(temp.indexOf('-')+1);
        setYearOfBirth(Integer.parseInt(temp));

    }

    public String getBirthday(){
        return getDayOfBirth() + "-" + getMonthOfBirth() + "-" + getYearOfBirth();
    }

    public String getBirthdayUpdateProfile(){
        return getYearOfBirth() + "-" + getMonthOfBirth() + "-" + getDayOfBirth();
    }

    public boolean isGarbageFlag() {
        return garbageFlag;
    }

    public void setGarbageFlag(boolean garbageFlag) {
        this.garbageFlag = garbageFlag;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void destroy(){
        INSTANCE = null;
    }

}
