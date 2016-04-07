package com.mikchaelsoloviev.simplegallery.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sol on 07.04.2016.
 */
public class Util {
    public static <T> int getPosition(T[] array, T value) {
        int lengthArray = array.length;
        for (int i = 0; i < lengthArray; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public static byte[] getByteArray(String urlS) throws IOException {
        InputStream inputStream = null;
        try {
            URL url = new URL(urlS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, byteCount);
            }
            outputStream.close();
            return outputStream.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public static byte[] getByteArrayFromFile(String name) {
        return null;
    }

    public static  void setByteArrayIntoFile(String name,byte[] bytes){

    }

    public static Bitmap convertByteArrayToBitmap(byte[] bytes) {

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static boolean isNetworkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable()) {
            return true;
        }
        return false;
    }

}