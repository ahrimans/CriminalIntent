package com.example.ahrimans.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Zoom on 2017/5/19.
 */

public class Crime {


    public UUID getId() {
        return mId;
    }

    private UUID mId;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    private String mTitle;

    public void setId(UUID id) {
        mId = id;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    private Date mDate;
    private boolean mSolved;
    private String mSuspect;

    public String getSuspectPhoneId() {
        return mSuspectPhoneId;
    }

    public void setSuspectPhoneId(String suspectPhoneId) {
        mSuspectPhoneId = suspectPhoneId;
    }

    private String mSuspectPhoneId;
    public String getSuspect() {
        return mSuspect;
    }
    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }
    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }

}
