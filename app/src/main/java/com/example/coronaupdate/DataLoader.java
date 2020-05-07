package com.example.coronaupdate;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DataLoader extends AsyncTask<String, Void, Document> {
    interface OnDownloadComplete {
        void setDataCompleteListener(String Data, String TotalData);
    }

     Document document;
     String data;
     OnDownloadComplete onDownloadComplete;
    Context context;

    DataLoader(Context context) {
        onDownloadComplete = (OnDownloadComplete) context;
        this.context =  context;
    }

    @Override
    protected Document doInBackground(String... strings) {
        try {
            document = Jsoup.connect(strings[0]).get();

//                Log.i("information", document.title());
        } catch (IOException e) {
            Toast.makeText(context,"something went wrong",Toast.LENGTH_SHORT).show();
        }

        return document;
    }

    @Override
    protected void onPostExecute(Document s) {
        super.onPostExecute(s);
        onDownloadComplete.setDataCompleteListener(s.title(),s.outerHtml());
    }




}