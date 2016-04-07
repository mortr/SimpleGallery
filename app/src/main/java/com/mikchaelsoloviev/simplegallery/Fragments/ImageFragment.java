package com.mikchaelsoloviev.simplegallery.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mikchaelsoloviev.simplegallery.R;

/**
 * Created by Sol on 07.04.2016.
 */
public class ImageFragment extends Fragment {


    public static final String ARGS_BITMAP = "ARGS_BITMAP";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ImageView) view.findViewById(R.id.image))
                .setImageBitmap((Bitmap) getArguments()
                        .getParcelable(ARGS_BITMAP));
    }

    public static ImageFragment newInstance(Parcelable bitmap) {

        Bundle args = new Bundle();
        args.putParcelable(ARGS_BITMAP, bitmap);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
