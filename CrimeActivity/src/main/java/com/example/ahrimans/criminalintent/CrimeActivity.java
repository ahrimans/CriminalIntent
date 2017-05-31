package com.example.ahrimans.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MyTest", "CrimeActivity onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MyTest", "CrimeActivity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MyTest", "CrimeActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MyTest", "CrimeActivity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MyTest", "CrimeActivity onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MyTest", "CrimeActivity onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MyTest", "CrimeActivity onRestart");
    }
}
