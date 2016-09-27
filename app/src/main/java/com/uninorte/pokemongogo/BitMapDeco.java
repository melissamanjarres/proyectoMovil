package com.uninorte.pokemongogo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by admin on 26/09/16.
 */
public class BitMapDeco extends AsyncTask<Void,Void,Bitmap> {
    private String TAG = "Img";
    private String URL;

    public BitMapDeco(String URL) {
        this.URL = URL;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap data = getData(URL);
        if(data!=null){
            return data;
        }
        else{
            Log.d(TAG, "Error cargando Imagen");
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }

    private static Bitmap getData(String URL){
        try {
            URL url = new URL(URL);
            URLConnection uConnect = url.openConnection();
            InputStream input = uConnect.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
