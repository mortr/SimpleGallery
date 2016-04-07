package com.mikchaelsoloviev.simplegallery.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.mikchaelsoloviev.simplegallery.Interface.IFragmentSetter;
import com.mikchaelsoloviev.simplegallery.Interface.ISelectable;
import com.mikchaelsoloviev.simplegallery.R;
import com.mikchaelsoloviev.simplegallery.Util.Util;

import java.io.IOException;

/**
 * Created by Sol on 07.04.2016.
 */
public class GalleryFragment extends Fragment {
    public static final String TAG = "SG_GalleryFragment";
    public static final String ARGS_REFERENCE = "ARGS_REFERENCE";
    public static final String ARGS_BITMAP = "ARGS_BITMAP";
    String[] mImageReferences;
    Bitmap[] mBitmap;
    ISelectable imageSelectable;
    IFragmentSetter mFragmentSetter;
    GridView mGridView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        mImageReferences = context.getResources().getStringArray(R.array.image_reference);

        if (context instanceof ISelectable) {
            imageSelectable = (ISelectable) context;
        }
        if (context instanceof IFragmentSetter) {
            mFragmentSetter = (IFragmentSetter) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (Util.isNetworkConnection(getActivity())) {
            (new ImageDownload()).execute(mImageReferences);
        } else {
            Toast.makeText(getActivity(), R.string.network_is_appsent, Toast.LENGTH_LONG).show();
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ");
        return inflater.inflate(R.layout.fragment_galery, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated" + mImageReferences);
        super.onViewCreated(view, savedInstanceState);
        mGridView = (GridView) view.findViewById(R.id.gv_gallery);

        setupAdapter();
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments().getParcelable(ARGS_BITMAP) == null) {
                    Snackbar.make(view, R.string.selected_item_appsent, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {

                    mFragmentSetter.setFragment(ImageFragment.newInstance(getArguments().getParcelable(ARGS_BITMAP)));
                }
            }
        });
    }

    void setupAdapter() {
        if (getActivity() == null || mGridView == null) {
            return;
        }
        GalleryAdapter galleryAdapter = null;
        if (mBitmap != null) {
            galleryAdapter = new GalleryAdapter(mBitmap, Util.getPosition(mImageReferences, getArguments().get(ARGS_REFERENCE)));
        }
        mGridView.setAdapter(galleryAdapter);


    }

    public static GalleryFragment newInstance(String referenceItemSelected, Bitmap bitmapItemSelected) {

        Bundle args = new Bundle();
        args.putString(ARGS_REFERENCE, referenceItemSelected);
        args.putParcelable(ARGS_BITMAP, bitmapItemSelected);
        GalleryFragment fragment = new GalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private class ImageDownload extends AsyncTask<String, Void, Bitmap[]> {

        @Override
        protected Bitmap[] doInBackground(String... params) {
            int countUrls = params.length;
            Bitmap[] bitmaps = new Bitmap[countUrls];

            try {
                for (int i = 0; i < countUrls; i++) {
                    Log.d(TAG, "getBitmaps " + params[i]);
                    Log.d(TAG, "getBitmaps " + Util.getByteArray(params[i]));
                    byte[] bytes = Util.getByteArray(params[i]);
                    Util.setByteArrayIntoFile(params[i], bytes);
                    bitmaps[i] = Util.convertByteArrayToBitmap(bytes);
                }

            } catch (IOException e) {
                Log.d(TAG, "getBitmaps " + e);
            }
            return bitmaps;
        }

        @Override
        protected void onPostExecute(Bitmap[] bitmap) {
            mBitmap = bitmap;
            setupAdapter();

        }
    }

    private class GalleryAdapter extends ArrayAdapter<Bitmap> {
        private int selectedId;

        public GalleryAdapter(Bitmap[] bitmaps, int selectedId) {
            super(getActivity(), 0, bitmaps);
            this.selectedId = selectedId;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_galery, parent, false);
            }
            ImageView imageView = ((ImageView) view.findViewById(R.id.iv_item_gallery));
            imageView.setImageBitmap(mBitmap[position]);
            View imageContainer = view.findViewById(R.id.container_item_gallery);
            if (position == selectedId) {
                imageContainer.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
            } else {
                imageContainer.setBackgroundColor(imageView.getDrawingCacheBackgroundColor());
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageSelectable != null) {
                        imageSelectable.setBitmap(mBitmap[position]);
                        imageSelectable.setReference(mImageReferences[position]);
                        getArguments().putParcelable(ARGS_BITMAP, mBitmap[position]);
                        getArguments().putString(ARGS_REFERENCE, mImageReferences[position]);
                        selectedId = position;
                        notifyDataSetChanged();
                    }
                }
            });
            return view;
        }
    }

}
