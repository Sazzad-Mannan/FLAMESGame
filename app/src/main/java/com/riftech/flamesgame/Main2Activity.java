package com.riftech.flamesgame;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Main2Activity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    public Intent intent;
    TextView txt1,txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        AdView mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        loadAd();

        txt1=(TextView) findViewById(R.id.textView5);
        txt2=(TextView) findViewById(R.id.textView6);

        changelang();


    }

    public void changelang() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        int selected_index = sharedPreferences.getInt("index", 0);
        switch (selected_index){
            case 0:
                txt1.setText(getString(R.string.how));
                txt2.setText(getString(R.string.fun));
                this.setTitle(getString(R.string.app_name));
                break;
            case 1:
                txt1.setText(getString(R.string.how_es));
                txt2.setText(getString(R.string.fun_es));
                this.setTitle(getString(R.string.app_name_es));
                break;
            case 2:
                txt1.setText(getString(R.string.how_fr));
                txt2.setText(getString(R.string.fun_fr));
                this.setTitle(getString(R.string.app_name_fr));
                break;
            case 3:
                txt1.setText(getString(R.string.how_it));
                txt2.setText(getString(R.string.fun_it));
                this.setTitle(getString(R.string.app_name_it));
                break;
            case 4:
                txt1.setText(getString(R.string.how_de));
                txt2.setText(getString(R.string.fun_de));
                this.setTitle(getString(R.string.app_name_de));
                break;
            case 5:
                txt1.setText(getString(R.string.how_pt));
                txt2.setText(getString(R.string.fun_pt));
                this.setTitle(getString(R.string.app_name_pt));
                break;
            case 6:
                txt1.setText(getString(R.string.how_ru));
                txt2.setText(getString(R.string.fun_ru));
                this.setTitle(getString(R.string.app_name_ru));
                break;
            default:
                txt1.setText(getString(R.string.how));
                txt2.setText(getString(R.string.fun));
                this.setTitle(getString(R.string.app_name));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(getBaseContext(), MainActivity.class);

// set the new task and clear flags
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        showInterstitial();
        //Toast.makeText(getApplicationContext(), "Hello toast!", Toast.LENGTH_SHORT).show();

    }

    public void loadAd() {
        AdRequest adRequest2 = new AdRequest.Builder().build();
//ca-app-pub-7831928589958637/4023201068
//ca-app-pub-7831928589958637/6704201737
        InterstitialAd.load(this,getString(R.string.interestial2), adRequest2,
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
                                        Main2Activity.this.mInterstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");

                                        gotoActivity(intent);
                                        //dismissed();

                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        Main2Activity.this.mInterstitialAd = null;
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