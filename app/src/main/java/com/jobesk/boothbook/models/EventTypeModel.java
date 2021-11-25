package com.jobesk.boothbook.models;

import com.google.gson.annotations.SerializedName;

public class EventTypeModel {


    public static final String TABLE_NAME = "types";


    public static final String COLUMN_ID = "id";

    public static final String column_CATEGORY_ID = "type_id";
    public static final String column_label = "label";

    int row_id;

    @SerializedName("id")
    private String cateGoryID;
    private String label;


    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public String getCateGoryID() {
        return cateGoryID;
    }

    public void setCateGoryID(String cateGoryID) {
        this.cateGoryID = cateGoryID;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + column_CATEGORY_ID + " TEXT,"
                    + column_label + " TEXT"
                    + ")";


}
