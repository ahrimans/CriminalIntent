package com.example.ahrimans.criminalintent;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.io.File;
import java.util.UUID;

public class PictureFragment extends DialogFragment {
    private static final String ARG_CRIME_ID = "crime_id";

    // TODO: Rename and change types of parameters
    private ImageView mPhotoView;
    private File mPhotoFile;
    private Crime mCrime;
    private ViewTreeObserver observer;

    public static PictureFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        PictureFragment fragment = new PictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_picture, null);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        mPhotoView = (ImageView) view.findViewById(R.id.image_view_show);
        mPhotoFile = CrimeLab.get(getActivity()).getPhotoFile(mCrime);
        observer = mPhotoView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                updatePhotoView();
            }
        });
        //updatePhotoView();
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("陋习现场")
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}
