package com.mikchaelsoloviev.simplegallery.Interface;

import android.graphics.Bitmap;

/**
 * Created by Sol on 07.04.2016.
 */
public interface ISelectable {
    void setReference(String reference);

    void setBitmap(Bitmap bitmap);

    String getReference();

    Bitmap getBitmap();
}
