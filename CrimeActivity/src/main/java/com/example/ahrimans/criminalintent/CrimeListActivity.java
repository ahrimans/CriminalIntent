package com.example.ahrimans.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Zoom on 2017/5/22.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, CrimeListActivity.class);
        return intent;
    }
    @Override
    protected int getLayoutResId() {
        //return R.layout.activity_twopane;
        return R.layout.activity_masterdetail;
    }
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
