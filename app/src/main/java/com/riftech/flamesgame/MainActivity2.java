package com.riftech.flamesgame;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity2 extends AppCompatActivity {
      private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    public Intent intent,intent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        loadAd();

        TextView txt1 = (TextView) findViewById(R.id.textView2);
        TextView txt2 = (TextView) findViewById(R.id.textView3);
        TextView txt3 = (TextView) findViewById(R.id.textView);
        //TextView txt7 = (TextView) findViewById(R.id.textView7);

        Bundle bundle = getIntent().getExtras();
        String percentage2 = bundle.getString("percentage2","h");
        //Integer percentage = Integer.parseInt(percentage2);
        String name = bundle.getString("names","Romeo & Juliet");
        String result = null;
        String[] array={"Friends","Love","Affection","Marriage","Enemy","Siblings/Soulmate"};
        switch (percentage2) {
            case "F":
                result = "Friends";
                break;
            case "L":
                result = "Love";
                break;
            case "A":
                result = "Affection";
                break;
            case "M":
                result = "Marriage";
                break;
            case "E":
                result = "Enemy";
                break;
            case "S":
                result = "Siblings/Soulmate";
                break;
        }

        txt1.setText(result);
        txt2.setText("Relation between "+name+" is:");
        txt3.setText("Result");
        //txt7.setText("");

    }

    @Override
    public void onBackPressed() {
        intent = new Intent(getBaseContext(), MainActivity.class);

        showInterstitial();
        //Toast.makeText(getApplicationContext(), "Hello toast!", Toast.LENGTH_SHORT).show();

    }

    public void loadAd() {
        AdRequest adRequest2 = new AdRequest.Builder().build();
//ca-app-pub-7831928589958637/4023201068
//ca-app-pub-7831928589958637/6704201737
        InterstitialAd.load(this,"ca-app-pub-7831928589958637/4023201068", adRequest2,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        //Toast.makeText(MainActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MainActivity2.this.mInterstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");

                                        gotoActivity(intent);
                                        //dismissed();

                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MainActivity2.this.mInterstitialAd = null;
                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;

                    }

                });
    }
    private void gotoActivity(Intent intent) {
        startActivity(intent);
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);


        }

        else {
            //Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            loadAd();
            gotoActivity(intent);
            //startGame();
        }
    }
}