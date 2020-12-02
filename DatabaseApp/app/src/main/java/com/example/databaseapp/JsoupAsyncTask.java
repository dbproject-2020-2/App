package com.example.databaseapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import static android.content.ContentValues.TAG;

class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

    String url = "https://openapi.gg.go.kr/Resrestrtcvnstr"
            + "?Key=" + "d09a48eee15444f4b98d7ee271ceb2b7"
            + "&SIGUN_NM=수원시";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect(url).get();
            Log.d(TAG,"Msg : " + document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


