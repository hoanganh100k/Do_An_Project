package com.example.lib.Model;

import com.google.firebase.Timestamp;

public class RewardModel {
    private String type,upperLimit,lowerLimit,discORamt,coupanBody,coupanId;
    private Timestamp timestamp;
    private boolean alreadyUsed;

    public RewardModel(String coupanId,String type, String upperLimit, String lowerLimit, String discORamt, String coupanBody,boolean alreadyUsed) { //, Date timestamp
        this.coupanId=coupanId;
        this.type = type;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.discORamt = discORamt;
        this.coupanBody = coupanBody;
        this.timestamp = timestamp;
        this.alreadyUsed=alreadyUsed;
    }
    public RewardModel(String coupanId, String type, String upperLimit, String lowerLimit, String discORamt, String coupanBody, Timestamp timestamp, boolean alreadyUsed) { //, Date timestamp
        this.coupanId=coupanId;
        this.type = type;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.discORamt = discORamt;
        this.coupanBody = coupanBody;
        this.timestamp = timestamp;
        this.alreadyUsed=alreadyUsed;
    }

    public String getCoupanId() {
        return coupanId;
    }

    public void setCoupanId(String coupanId) {
        this.coupanId = coupanId;
    }

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }

    public void setAlreadyUsed(boolean alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }

    public String getDiscORamt() {
        return discORamt;
    }

    public void setDiscORamt(String discORamt) {
        this.discORamt = discORamt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(String upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(String lowerLimit) {
        this.lowerLimit = lowerLimit;
    }


    public String getCoupanBody() {
        return coupanBody;
    }

    public void setCoupanBody(String coupanBody) {
        this.coupanBody = coupanBody;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
//    private String title;
//    private String expiryDate;
//    private String coupenBody;
//
//    public RewardModel(String title, String expiryDate, String coupenBody) {
//        this.title = title;
//        this.expiryDate = expiryDate;
//        this.coupenBody = coupenBody;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getExpiryDate() {
//        return expiryDate;
//    }
//
//    public void setExpiryDate(String expiryDate) {
//        this.expiryDate = expiryDate;
//    }
//
//    public String getCoupenBody() {
//        return coupenBody;
//    }
//
//    public void setCoupenBody(String coupenBody) {
//        this.coupenBody = coupenBody;
//    }
}
