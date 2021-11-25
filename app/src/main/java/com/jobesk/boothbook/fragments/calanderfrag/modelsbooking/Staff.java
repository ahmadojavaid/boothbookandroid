package com.jobesk.boothbook.fragments.calanderfrag.modelsbooking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Staff {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    @SerializedName("written")
    @Expose
    private String written;


    public String getWritten() {
        return written;
    }

    public void setWritten(String written) {
        this.written = written;
    }
}
