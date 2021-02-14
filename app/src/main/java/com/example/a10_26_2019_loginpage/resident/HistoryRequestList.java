package com.example.a10_26_2019_loginpage.resident;

public class HistoryRequestList {

    int id;
    String issue_type;
    String _to;
    String detail;
    String support_send_time;
    String support_result_time;
    String response;

    double cost;
    int deliverySeq;
    int deliverirID;
    String deliveryDate;
    String deliveryStatus;
    String content;

    public String getIssue_type() {
        return issue_type;
    }

    public void setIssue_type(String issue_type) {
        this.issue_type = issue_type;
    }

    public String get_to() {
        return _to;
    }

    public void set_to(String _to) {
        this._to = _to;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSupport_send_time() {
        return support_send_time;
    }

    public void setSupport_send_time(String support_send_time) {
        this.support_send_time = support_send_time;
    }

    public String getSupport_result_time() {
        return support_result_time;
    }

    public void setSupport_result_time(String support_result_time) {
        this.support_result_time = support_result_time;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getDeliverySeq() {
        return deliverySeq;
    }

    public void setDeliverySeq(int deliverySeq) {
        this.deliverySeq = deliverySeq;
    }

    public int getDeliverirID() {
        return deliverirID;
    }

    public void setDeliverirID(int deliverirID) {
        this.deliverirID = deliverirID;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
