package com.jpl.sdp_project.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("INGR_NAME")
    @Expose
    private String ingrName;
    @SerializedName("ITEM_NAME")
    @Expose
    private String itemName;
    @SerializedName("ENTP_NAME")
    @Expose
    private String entpName;
    @SerializedName("COMP_DRUG_GB_KOR")
    @Expose
    private String compDrugGbKor;
    @SerializedName("SHAPE_CODE_NAME")
    @Expose
    private String shapeCodeName;
    @SerializedName("BIOEQ_NOTICE_DATE")
    @Expose
    private String bioeqNoticeDate;

    public String getIngrName() {
        return ingrName;
    }

    public void setIngrName(String ingrName) {
        this.ingrName = ingrName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getEntpName() {
        return entpName;
    }

    public void setEntpName(String entpName) {
        this.entpName = entpName;
    }

    public String getCompDrugGbKor() {
        return compDrugGbKor;
    }

    public void setCompDrugGbKor(String compDrugGbKor) {
        this.compDrugGbKor = compDrugGbKor;
    }

    public String getShapeCodeName() {
        return shapeCodeName;
    }

    public void setShapeCodeName(String shapeCodeName) {
        this.shapeCodeName = shapeCodeName;
    }

    public String getBioeqNoticeDate() {
        return bioeqNoticeDate;
    }

    public void setBioeqNoticeDate(String bioeqNoticeDate) {
        this.bioeqNoticeDate = bioeqNoticeDate;
    }

}