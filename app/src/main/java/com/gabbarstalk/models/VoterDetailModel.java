package com.gabbarstalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SUHAS on 09/03/2017.
 */

public class VoterDetailModel implements Serializable{

    private Integer iD;
    @SerializedName("ID")
    @Expose
    private Integer recordID;
    @SerializedName("PrimaryKey")
    @Expose
    private String primaryKey;
    @SerializedName("LoksabhaId")
    @Expose
    private Integer loksabhaId;
    @SerializedName("VidhansabhaId")
    @Expose
    private Integer vidhansabhaId;
    @SerializedName("Wardnumber")
    @Expose
    private Integer wardnumber;
    @SerializedName("ListNo")
    @Expose
    private Integer listNo;
    @SerializedName("PSRNO")
    @Expose
    private Integer pSRNO;
    @SerializedName("SRNO")
    @Expose
    private Integer sRNO;
    @SerializedName("FNAME")
    @Expose
    private String fNAME;
    @SerializedName("MNAME")
    @Expose
    private String mNAME;
    @SerializedName("LNAME")
    @Expose
    private String lNAME;
    @SerializedName("EFNAME")
    @Expose
    private String eFNAME;
    @SerializedName("MFNAME")
    @Expose
    private String mFNAME;
    @SerializedName("MMNAME")
    @Expose
    private String mMNAME;
    @SerializedName("MLNAME")
    @Expose
    private String mLNAME;
    @SerializedName("MFULLNAME")
    @Expose
    private String mFULLNAME;
    @SerializedName("FATHERNAME")
    @Expose
    private String fATHERNAME;
    @SerializedName("SEX")
    @Expose
    private String sEX;
    @SerializedName("Age")
    @Expose
    private String age;
    @SerializedName("HNO")
    @Expose
    private String hNO;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("VoterID")
    @Expose
    private String voterID;
    @SerializedName("BID")
    @Expose
    private Integer bID;
    @SerializedName("Active")
    @Expose
    private Integer active;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Integer getLoksabhaId() {
        return loksabhaId;
    }

    public void setLoksabhaId(Integer loksabhaId) {
        this.loksabhaId = loksabhaId;
    }

    public Integer getVidhansabhaId() {
        return vidhansabhaId;
    }

    public void setVidhansabhaId(Integer vidhansabhaId) {
        this.vidhansabhaId = vidhansabhaId;
    }

    public Integer getWardnumber() {
        return wardnumber;
    }

    public void setWardnumber(Integer wardnumber) {
        this.wardnumber = wardnumber;
    }

    public Integer getListNo() {
        return listNo;
    }

    public void setListNo(Integer listNo) {
        this.listNo = listNo;
    }

    public Integer getPSRNO() {
        return pSRNO;
    }

    public void setPSRNO(Integer pSRNO) {
        this.pSRNO = pSRNO;
    }

    public Integer getSRNO() {
        return sRNO;
    }

    public void setSRNO(Integer sRNO) {
        this.sRNO = sRNO;
    }

    public String getFNAME() {
        return fNAME;
    }

    public void setFNAME(String fNAME) {
        this.fNAME = fNAME;
    }

    public String getMNAME() {
        return mNAME;
    }

    public void setMNAME(String mNAME) {
        this.mNAME = mNAME;
    }

    public String getLNAME() {
        return lNAME;
    }

    public void setLNAME(String lNAME) {
        this.lNAME = lNAME;
    }

    public String getEFNAME() {
        return eFNAME;
    }

    public void setEFNAME(String eFNAME) {
        this.eFNAME = eFNAME;
    }

    public String getMFNAME() {
        return mFNAME;
    }

    public void setMFNAME(String mFNAME) {
        this.mFNAME = mFNAME;
    }

    public String getMMNAME() {
        return mMNAME;
    }

    public void setMMNAME(String mMNAME) {
        this.mMNAME = mMNAME;
    }

    public String getMLNAME() {
        return mLNAME;
    }

    public void setMLNAME(String mLNAME) {
        this.mLNAME = mLNAME;
    }

    public String getMFULLNAME() {
        return mFULLNAME;
    }

    public void setMFULLNAME(String mFULLNAME) {
        this.mFULLNAME = mFULLNAME;
    }

    public String getFATHERNAME() {
        return fATHERNAME;
    }

    public void setFATHERNAME(String fATHERNAME) {
        this.fATHERNAME = fATHERNAME;
    }

    public String getSEX() {
        return sEX;
    }

    public void setSEX(String sEX) {
        this.sEX = sEX;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHNO() {
        return hNO;
    }

    public void setHNO(String hNO) {
        this.hNO = hNO;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVoterID() {
        return voterID;
    }

    public void setVoterID(String voterID) {
        this.voterID = voterID;
    }

    public Integer getBID() {
        return bID;
    }

    public void setBID(Integer bID) {
        this.bID = bID;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }

    @Override
    public String toString() {
        return "VoterDetailModel{" +
                "iD=" + iD +
                ", recordID=" + recordID +
                ", primaryKey='" + primaryKey + '\'' +
                ", loksabhaId=" + loksabhaId +
                ", vidhansabhaId=" + vidhansabhaId +
                ", wardnumber=" + wardnumber +
                ", listNo=" + listNo +
                ", pSRNO=" + pSRNO +
                ", sRNO=" + sRNO +
                ", fNAME='" + fNAME + '\'' +
                ", mNAME='" + mNAME + '\'' +
                ", lNAME='" + lNAME + '\'' +
                ", eFNAME='" + eFNAME + '\'' +
                ", mFNAME='" + mFNAME + '\'' +
                ", mMNAME='" + mMNAME + '\'' +
                ", mLNAME='" + mLNAME + '\'' +
                ", mFULLNAME='" + mFULLNAME + '\'' +
                ", fATHERNAME='" + fATHERNAME + '\'' +
                ", sEX='" + sEX + '\'' +
                ", age='" + age + '\'' +
                ", hNO='" + hNO + '\'' +
                ", address='" + address + '\'' +
                ", voterID='" + voterID + '\'' +
                ", bID=" + bID +
                ", active=" + active +
                '}';
    }
}
