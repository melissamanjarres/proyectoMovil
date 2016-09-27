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
        String response = getData("https://raw.githubusercontent.com/FTorrenegraG/Pokemon_json_example/master/example.json");
        ArrayList<Pokemon> pokemonI = new ArrayList<Pokemon>();
        if (response != null) {
            try {
                Log.d(TAG, response);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray poke = jsonObject.getJSONArray("result");
                for (int i = 0; i < poke.length(); i++) {
                    JSONObject c = poke.getJSONObject(i);
                    Log.d(TAG, c.toString());
                    String id = c.getString("Id").toString();
                    String name = c.getString("Name").toString();
                    String ImgFront = c.getString("ImgFront").toString();
                    pokemonI.add(i, new Pokemon(id,name, ImgFront));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG,""+pokemonI.size());
        }
        return pokemonI;
    }

    @Override
    protected void onPostExecute(List<Pokemon> aVoid) {
        super.onPostExecute(aVoid);
        pokemon.addAll(aVoid);
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }


    }

    protected static String getData(String Url){
        String response = "";
        try {
            URL url = null;
            url = new URL(Url);
            URLConnection uConnect = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uConnect.getInputStream()));
            String Line;
            while ((Line = in.readLine()) != null){
                response += Line;
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
