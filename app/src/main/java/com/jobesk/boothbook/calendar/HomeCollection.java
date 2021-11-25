package com.jobesk.boothbook.calendar;

import java.util.ArrayList;

public class HomeCollection {



    public String date = "";
    public String name = "";
    public String subject = "";
    public String description = "";
    public String Status = "";




    //status
    // 1 for today(Current day)
    //  2 for selected day
    // 3 for nomal days

    public static ArrayList<HomeCollection> date_collection_arr;

    public HomeCollection(String date, String name, String subject, String description, String Status) {

        this.date = date;
        this.name = name;
        this.subject = subject;
        this.description = description;
        this.Status = Status;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
