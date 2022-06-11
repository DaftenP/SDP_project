package com.jpl.sdp_project.retrofit;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Body {
    @SerializedName("pageNo")
    @Expose
    private Integer pageNo;
    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("numOfRows")
    @Expose
    private Integer numOfRows;
    @SerializedName("items")
    @Expose
    private ArrayList<Item> items = null;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }

    public List<Item>  getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

}
