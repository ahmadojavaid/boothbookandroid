package com.jobesk.boothbook.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignatureReceiveModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("submitted_data")
    @Expose
    private SubmittedData submittedData;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SubmittedData getSubmittedData() {
        return submittedData;
    }

    public void setSubmittedData(SubmittedData submittedData) {
        this.submittedData = submittedData;
    }

}