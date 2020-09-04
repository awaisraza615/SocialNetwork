package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
public class DetailedScholarshipActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView titleTExtView, detailTextView;
    private String detailString,baseUrl,detailUrl;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_scholarship);
        imageView = findViewById(R.id.imageView);
        titleTExtView = findViewById(R.id.textView);
        /*  detailTextView = findViewById(R.id.detailTextView);*/
        webView=findViewById(R.id.webview);
        detailUrl = getIntent().getStringExtra("detailUrl");

        titleTExtView.setText(getIntent().getStringExtra("title"));
        Picasso.get().load(getIntent().getStringExtra("image")).into(imageView);
        webView.loadUrl(detailUrl);
       /* Content content = new Content();
        content.execute();*/
    }

/*    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("details", "doInBackground: "+detailString);

            detailTextView.setText(detailString);


        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                 baseUrl = "https://scholarshipscorner.website/pakistan-scholarships/";
                 detailUrl = getIntent().getStringExtra("detailUrl");


                String url = baseUrl + detailUrl;

                Document doc = Jsoup.connect(detailUrl).get();

                Elements data = doc.select("div.post-wrap");

                detailString = data.select("div.post-wrap")
                        .text();



            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }*/
}