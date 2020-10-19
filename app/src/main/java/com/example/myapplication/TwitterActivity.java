package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.TwitterException;

public class TwitterActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.GeoTrending.MESSAGE";

    public static final String EXTRA_WOEID = "com.example.GeoTrending.WOEID";

    public boolean network = true;

    private ResponseList<Location> locations;

    private List<String> locationsString = new ArrayList<>();

    private List<String> locationsName = new ArrayList<>();

    private List<Integer> locationsWoeid = new ArrayList<>();

    private String[] locationsArrayName;

    private Integer[] locationsArrayWoeid;

    private Toast currentToast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);

        currentToast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);

        start();
    }

    public void start() {
        try {
            LocationThread locationThread = new LocationThread();
            locationThread.start();
            locationThread.join();
            AutoCompleteTextView autoLocation = findViewById(R.id.locationSearch);
            autoLocation.setAdapter(new ArrayAdapter<>
                    (TwitterActivity.this, android.R.layout.simple_list_item_1, locationsArrayName));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            network = false;

            currentToast.setText("Please check your network");
            currentToast.show();
        }
    }

    // Action bar menu from https://www.youtube.com/watch?v=STl3JmL6VFg

    public void searchLocation(View view) {
        Intent intent = new Intent(this, TopTenTrending.class);
        AutoCompleteTextView locationSearch = findViewById(R.id.locationSearch);
        String message = locationSearch.getText().toString();

        try
        {
            if (!network) {
                start();
            } else if (!(message.equals("") || message == null)) {
                int number = 0;
                for (int i = 0; i < locationsArrayName.length; i++) {
                    String formatted = locationsArrayName[i].replaceAll("\\s+", "");
                    String messageFormatted = message.replaceAll("\\s+", "");
                    if (messageFormatted.toLowerCase().contains(formatted.toLowerCase())) {
                        number = i;
                        break;
                    }
                }
                intent.putExtra(EXTRA_MESSAGE, locationsArrayName[number]);
                intent.putExtra(EXTRA_WOEID, locationsArrayWoeid[number]);
                startActivity(intent);
            } else {
                currentToast.setText("Location search empty");
                currentToast.show();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Error : "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //Initial locations available
    class LocationThread extends Thread {
        @Override
        public void run() {
            OAuth oAuth = new OAuth();
            try {
                locations = oAuth.getTwitter().getAvailableTrends();
                for (Location location : locations) {
                    locationsString.add((location.getName() + " (woeid:" + location.getWoeid() + ")"));
                    locationsName.add(location.getName());
                    locationsWoeid.add(location.getWoeid());
                }
                locationsArrayName = locationsName.toArray(new String[0]);
                locationsArrayWoeid = locationsWoeid.toArray(new Integer[0]);
                network = true;
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
    }
}
