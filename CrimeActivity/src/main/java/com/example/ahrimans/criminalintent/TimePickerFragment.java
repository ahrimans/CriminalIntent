package com.example.ahrimans.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Zoom on 2017/5/23.
 */

public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_Time =
            "com.bignerdranch.android.criminalintent.time";
    private static final String ARG_Time = "time";
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(long time) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_Time, time);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, long time) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_Time, time);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        long time = (long) getArguments().getSerializable(ARG_Time);
        Date date = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);
        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(min);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int cHour = mTimePicker.getCurrentHour();
                                int cMin = mTimePicker.getCurrentMinute();
                                Date date = new Date();
                                date.setHours(cHour);
                                date.setMinutes(cMin);
                                sendResult(Activity.RESULT_OK, date.getTime());
                            }
                        })
                .create();
    }
}
