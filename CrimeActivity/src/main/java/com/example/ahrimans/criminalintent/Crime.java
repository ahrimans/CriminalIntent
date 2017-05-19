package com.example.ahrimans.criminalintent;

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

    public Crime() {
        // Generate unique identifier
        mId = UUID.randomUUID();
    }

}
