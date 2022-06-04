package com.riftech.flamesgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    private InterstitialAd mInterstitialAd,mInterstitialAd2;
    private EditText et1,et2;
    private Integer code=1;
    private String jsonString="";
    private Integer percentage=null;
    private String percentage2="";
    private ProgressBar pgsBar;
    String status,s = null;
    private View v;
    public Intent intent,intent2;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        loadAd2();

        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        et1=(EditText) findViewById(R.id.editText2);
        et2=(EditText) findViewById(R.id.editText3);
    }




    public void show_how( final View view) {

        intent2 = new Intent(getBaseContext(), Main2Activity.class);
        //showInterstitial2();
        gotoActivity2(intent2);

    }
    public void start_match( final View view) {
        String name1=et1.getText().toString();
        String name2=et2.getText().toString();
        if (name1.matches("")||name2.matches("")) {
            Toast.makeText(this, "You did not enter a name", Toast.LENGTH_SHORT).show();
            return;
        }
        pgsBar.setVisibility(v.VISIBLE);


        percentage2=calculate(name1,name2);

        pgsBar.setVisibility(v.GONE);
        intent = new Intent(getBaseContext(), MainActivity2.class);
        //intent.putExtra("percentage", percentage);
        intent.putExtra("percentage2", percentage2);
        intent.putExtra("names", name1+" & "+name2);

        gotoActivity(intent);



    }

    private String calculate(String name1, String name2) {

        String word1=name1.toLowerCase();
        String word3=word1;
        String word2=name2.toLowerCase();



        for (int j = 0;  j<word1.length(); j++) {
            char letter=word1.charAt(j);

            for (int i = 0; i < word2.length(); i++) {
                if (word2.charAt(i) == letter) {
                    word3=word3.replaceFirst(String.valueOf(letter), "");
                    word2=word2.replaceFirst(String.valueOf(letter), "");
                    break;

                }
            }

        }
        int number=word3.length()+word2.length();
        String flames="FLAMES";
        int result=number%6;

        for(int i=0;i<5;i++){
            result=number%flames.length();
            flames=flames.substring(0,result)+flames.substring(result+1);
        }

        return flames;
    }

    private void gotoActivity(Intent intent) {
        startActivity(intent);
    }

    private void gotoActivity2(Intent intent2) {
        startActivity(intent2);
    }



    public void loadAd2() {
        AdRequest adRequest3 = new AdRequest.Builder().build();
//ca-app-pub-3940256099942544/1033173712
//ca-app-pub-7831928589958637/3640763896
        InterstitialAd.load(this,"ca-app-pub-7831928589958637/3640763896", adRequest3,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd2 = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        //Toast.makeText(MainActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MainActivity.this.mInterstitialAd2 = null;
                                        Log.d("TAG", "The ad was dismissed.");

                                        loadAd2();
                                        gotoActivity2(intent2);
                                        //dismissed();

                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MainActivity.this.mInterstitialAd2 = null;
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
                        mInterstitialAd2 = null;

                    }

                });
    }


    private void showInterstitial2() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd2 != null) {
            mInterstitialAd2.show(this);


        }

        else {
            //Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            loadAd2();
            gotoActivity2(intent2);
            //startGame();
        }
    }

}