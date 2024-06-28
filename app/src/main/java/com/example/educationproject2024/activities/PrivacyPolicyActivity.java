package com.example.educationproject2024.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educationproject2024.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;

public class PrivacyPolicyActivity extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        pdfView = findViewById(R.id.pdf_view);

//        AssetManager assetManager = getApplicationContext().getAssets();
//        try {
////            Toast.makeText(this, Arrays.toString(assetManager.list("")), Toast.LENGTH_LONG).show();
//            Log.d("777", Arrays.toString(assetManager.list("")));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


        InputStream json = null;
        try {
            json = getAssets().open("policy.pdf");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pdfView.fromStream(json);
        //Log.d("777", "execute");

        //initializePdfView();
    }

//    private void initializePdfView() {
//        String url = "https://drive.google.com/file/d/1Ny878q5e65XENPJy8XQokRpR1UucU3bj/view";
//        new GetPDF().execute(url);
//    }
//
//    class GetPDF extends AsyncTask<String, Void, InputStream> {
//        // we are calling async task and performing
//        // this task to load pdf in background.
//        @Override
//        protected InputStream doInBackground(String... strings) {
//            // below line is for declaring
//            // our input stream.
//            InputStream pdfStream = null;
//            try {
//                // creating a new URL and passing
//                // our string in it.
//                URI url = new URI(strings[0]);
//
//                // creating a new http url connection and calling open
//                // connection method to open http url connection.
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                if (httpURLConnection.getResponseCode() == 200) {
//                    // if the connection is successful then
//                    // we are getting response code as 200.
//                    // after the connection is successful
//                    // we are passing our pdf file from url
//                    // in our pdfstream.
//                    pdfStream = new BufferedInputStream(httpURLConnection.getInputStream());
//                }
//
//            } catch (IOException e) {
//                // this method is
//                // called to handle errors.
//                return null;
//            }
//            // returning our stream
//            // of PDF file.
//            return pdfStream;
//        }
//
//        @Override
//        protected void onPostExecute(InputStream inputStream) {
//            // after loading stream we are setting
//            // the pdf in your pdf view.
//            pdfView.fromStream(inputStream).load();
//            Log.d("777", "execute");
//        }
//    }

    public void RegistrationFromPrivacyPolicy(View view) {
        startActivity(new Intent(PrivacyPolicyActivity.this, RegistrationActivity.class));
    }
}