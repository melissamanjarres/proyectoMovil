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
public class jsonDecoder extends AsyncTask<Void,ArrayList<Position>, Void> {
    private ProgressDialog pDialog;
    List<Position> position;
    MapsActivity mapa;
    private String TAG = "JsonRunning";
    private static String Url;

    public jsonDecoder(ProgressDialog pDialog, MapsActivity mapa, String Url, List<Position> position) {
        this.pDialog = pDialog;
        this.mapa = mapa;
        this.position = position;
        this.Url = Url;
    }



    @Override
    protected Void doInBackground(Void... voids) {
        String response = getData();
        if (response != null) {
            try {
                Log.d(TAG, response);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray positions = jsonObject.getJSONArray("result");
                for (int i = 0; i < positions.length(); i++) {
                    JSONObject c = positions.getJSONObject(i);
                    for (int j=0; j<c.length(); j++){
                        String lt = c.getString("lt").toString();
                        String lng = c.getString("lng").toString();
                        position.add(j, new Position(lt,lng));
                    }


                }

                Log.d(TAG, position.size()+"");
                mapa.markers.addAll(position);



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.setMessage("Wait a minute, please....");
        pDialog.setCancelable(false);
        pDialog.show();

    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }




    }

    protected static String getData(){
        String response = null;
        try {
            URL url = null;
            url = new URL(Url);
            URLConnection uConnect = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uConnect.getInputStream()));
            String Line;
            while ((Line = in.readLine()) != null){
                response = Line;
            }
            response ="{'result':"+response+"}";
            in.close();
            return response;

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    

}
