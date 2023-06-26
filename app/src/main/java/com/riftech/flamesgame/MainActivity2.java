package com.riftech.flamesgame;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    public Intent intent;
    String arra_st;
    TextView txt1,txt2,txt3;
    String name1,name2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        loadAd();

        txt1 = (TextView) findViewById(R.id.textView2);
        txt2 = (TextView) findViewById(R.id.textView3);
        txt3 = (TextView) findViewById(R.id.textView);
        //TextView txt7 = (TextView) findViewById(R.id.textView7);






        Bundle bundle = getIntent().getExtras();
        String percentage2 = bundle.getString("percentage2","F");
        //Integer percentage = Integer.parseInt(percentage2);
        name1 = bundle.getString("name1","Romeo");
        name2 = bundle.getString("name2","Juliet");
        String result = null;

        changelang();

        String[] arra=arra_st.split(",");
        switch (percentage2) {
            case "F":
                result = arra[0];
                break;
            case "L":
                result = arra[1];
                break;
            case "A":
                result = arra[2];
                break;
            case "M":
                result = arra[3];
                break;
            case "E":
                result = arra[4];
                break;
            case "S":
                result = arra[5];
                break;
        }

        txt1.setText(result);


        //txt7.setText("");

    }

    public void changelang() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        int selected_index = sharedPreferences.getInt("index", 0);
        switch (selected_index){
            case 0:
                arra_st=getString(R.string.arra);
                txt3.setText(getString(R.string.res));
                txt2.setText("Relation between "+name1+" & "+name2+" is:");
                this.setTitle(getString(R.string.app_name));
                break;
            case 1:
                arra_st=getString(R.string.arra_es);
                txt3.setText(getString(R.string.res_es));
                txt2.setText("La relación entre "+name1+" e "+name2+" es:");
                this.setTitle(getString(R.string.app_name_es));
                break;
            case 2:
                arra_st=getString(R.string.arra_fr);
                txt3.setText(getString(R.string.res_fr));
                txt2.setText("La relation entre "+name1+" et "+name2+" est:");
                this.setTitle(getString(R.string.app_name_fr));
                break;
            case 3:
                arra_st=getString(R.string.arra_it);
                txt3.setText(getString(R.string.res_it));
                txt2.setText("La relazione tra "+name1+" e "+name2+" è:");
                this.setTitle(getString(R.string.app_name_it));
                break;
            case 4:
                arra_st=getString(R.string.arra_de);
                txt3.setText(getString(R.string.res_de));
                txt2.setText("Die Beziehung zwischen "+name1+" und "+name2+" ist:");
                this.setTitle(getString(R.string.app_name_de));
                break;
            case 5:
                arra_st=getString(R.string.arra_pt);
                txt3.setText(getString(R.string.res_pt));
                txt2.setText("A relação entre "+name1+" e "+name2+" é:");
                this.setTitle(getString(R.string.app_name_pt));
                break;
            case 6:
                arra_st=getString(R.string.arra_ru);
                txt3.setText(getString(R.string.res_ru));
                txt2.setText("Отношение между "+name1+" и "+name2+" :");
                this.setTitle(getString(R.string.app_name_ru));
                break;
            default:
                arra_st=getString(R.string.arra);
                txt3.setText(getString(R.string.res));
                txt2.setText("Relation between "+name1+" & "+name2+" is:");
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
        InterstitialAd.load(this,getString(R.string.interestial1), adRequest2,
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