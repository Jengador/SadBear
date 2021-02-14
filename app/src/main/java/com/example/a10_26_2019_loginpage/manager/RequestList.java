package com.example.a10_26_2019_loginpage.manager;

public class RequestList {

    int id;
    String issue_type;
    String _from;
    String detail;
    String support_send_time;
    String support_result_time;
    String response;

    public String getIssue_type() {
        return issue_type;
    }

    public void setIssue_type(String issue_type) {
        this.issue_type = issue_type;
    }

    public String get_from() {
        return _from;
    }

    public void set_from(String _to) {
        this._from = _to;
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
}

