package com.uninorte.pokemongogo;

import android.app.ProgressDialog;
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

/**
 * Created by admin on 26/09/16.
 */
public class DecoderPoke extends AsyncTask<Void,Void,List<Pokemon>> {
    private ProgressDialog pDialog;
    MapsActivity mapa;
    private String TAG = "Json2Running";
    List<Pokemon> pokemon;

    public DecoderPoke(ProgressDialog pDialog, MapsActivity mapa, List<Pokemon> pokemon) {
        this.pDialog = pDialog;
        this.mapa = mapa;
        this.pokemon = pokemon;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.setMessage("Wait a minute, please....");
        pDialog.setCancelable(false);
        pDialog.show();

    }
    @Override
    protected List<Pokemon> doInBackground(Void... voids) {
        String response = getData("http://190.144.171.172/ejemplo");
        ArrayList<Pokemon> pokemonI = new ArrayList<Pokemon>();
        if (response != null) {
            try {
                Log.d(TAG, response);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray poke = jsonObject.getJSONArray("result");
                for (int i = 0; i < poke.length(); i++) {
                    JSONObject c = poke.getJSONObject(i);
                    for (int j=0; j<c.length(); j++){
                        String id = c.getString("id").toString();
                        String name = c.getString("name").toString();
                        String ImgFront = c.getString("ImgFront").toString();
                        pokemonI.add( j, new Pokemon(id,name, ImgFront));
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return pokemonI;
    }

    @Override
    protected void onPostExecute(List<Pokemon> aVoid) {
        super.onPostExecute(aVoid);
        pokemon.addAll(aVoid);
        Log.d(TAG,mapa.pokes.size()+"");
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }


    }

    protected static String getData(String Url){
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
