package com.uninorte.pokemongogo;

import android.app.ProgressDialog;
import android.location.Location;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 13/09/16.
 */
public class jsonDecoder extends AsyncTask<Void,Void,List<Position>> {
    private ProgressDialog pDialog;
    List<Position> position;
    MapsActivity mapa;
    private String TAG = "JsonRunning";
    private String Url;


    public jsonDecoder(ProgressDialog pDialog, MapsActivity mapa, List<Position> position, String Url) {
        this.pDialog = pDialog;
        this.position = position;
        this.mapa = mapa;
        this.Url = Url;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.setMessage("Wait a minute, please....");
        pDialog.setCancelable(false);
        pDialog.show();

    }


    @Override
    protected List<Position> doInBackground(Void... voids) {
        String response = getData(Url);
        ArrayList<Position> positionI = new ArrayList<Position>();
        if (response != null) {
            try {
                Log.d(TAG,response);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray positions = jsonObject.getJSONArray("result");
                for (int i = 0; i < positions.length(); i++) {
                    JSONObject c = positions.getJSONObject(i);
                    Log.d(TAG,c.toString());
                    positionI.add(i, new Position(c.getString("lt"),c.getString("lng")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return positionI;
    }



    @Override
    protected void onPostExecute(List<Position> aVoid) {
        super.onPostExecute(aVoid);
        position.addAll(aVoid);
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }


    }

    private static String getData(String URL){
        String response = "{'results':";
        try{
            java.net.URL url = new URL(URL);
            URLConnection urlConnection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while((line= in.readLine()) != null){
                response = line;
            }
            in.close();
            response ="{'result':"+response+"}";
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return response;
        }

    }
    

}
