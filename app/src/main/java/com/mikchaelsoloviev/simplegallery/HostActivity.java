package com.mikchaelsoloviev.simplegallery;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mikchaelsoloviev.simplegallery.Fragments.GalleryFragment;
import com.mikchaelsoloviev.simplegallery.Interface.IFragmentSetter;
import com.mikchaelsoloviev.simplegallery.Interface.ISelectable;

public class HostActivity extends AppCompatActivity implements IFragmentSetter, ISelectable {
    public static final String TAG="SG_HostActivity";
    private String mReferenceSelectedItem;
    private Bitmap mBitmapSelectedItem;
    public static final String KEY_SELECTED_REFERENCE="KEY_SELECTED_REFERENCE";
    public static final String KEY_SELECTED_BITMAP="KEY_SELECTED_BITMAP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState!=null) {
            Log.d(TAG,"onCreate ");
            mBitmapSelectedItem = (Bitmap) savedInstanceState.getParcelable(KEY_SELECTED_BITMAP);
            mReferenceSelectedItem = savedInstanceState.getString(KEY_SELECTED_REFERENCE);
        }else {
            Log.d(TAG, "onCreate 1");
            setFragment(GalleryFragment.newInstance(mReferenceSelectedItem, mBitmapSelectedItem));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_host, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState (mBitmapSelectedItem==null)-- "+ String.valueOf(mBitmapSelectedItem==null));
        outState.putParcelable(KEY_SELECTED_BITMAP,mBitmapSelectedItem);
        outState.putString(KEY_SELECTED_REFERENCE,mReferenceSelectedItem);
    }

    public void setFragment(Fragment fragment) {
        Log.d(TAG,"setFragment ");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void setReference(String reference) {
        this.mReferenceSelectedItem = reference;
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.mBitmapSelectedItem = bitmap;
    }

    @Override
    public String getReference() {
        return mReferenceSelectedItem;
    }

    @Override
    public Bitmap getBitmap() {
        return mBitmapSelectedItem;
    }
}
