package com.example.ahrimans.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Zoom on 2017/5/22.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
