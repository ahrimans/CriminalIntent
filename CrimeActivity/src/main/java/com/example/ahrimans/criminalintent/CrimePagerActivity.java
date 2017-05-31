package com.example.ahrimans.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;
import java.util.UUID;

/**
 * Created by Zoom on 2017/5/23.
 */

public class CrimePagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MyTest", "CrimePagerActivity onCreate");
        setContentView(R.layout.activity_crime_pager);
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MyTest", "CrimePagerActivity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MyTest", "CrimePagerActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MyTest", "CrimePagerActivity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MyTest", "CrimePagerActivity onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MyTest", "CrimePagerActivity onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MyTest", "CrimePagerActivity onRestart");
    }
}
