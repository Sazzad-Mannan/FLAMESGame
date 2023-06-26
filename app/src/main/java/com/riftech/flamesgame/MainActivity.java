package com.riftech.flamesgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    Button btn1;
    TextView txt1;
    AlertDialog.Builder builder;
    AlertDialog customAlertDialog;
    SharedPreferences sharedPreferences;
    String selected_lang,st;
    String[] countries;
    int selected_index;
    String toast_st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Storing data into SharedPreferences
        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        if(!sharedPreferences.contains("index")) {
// Creating an Editor object to edit(write to the file)
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
            myEdit.putInt("index",0);

// Once the changes have been made, we need to commit to apply those changes made,
// otherwise, it will throw an error
            myEdit.apply();
        }


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        et1=(EditText) findViewById(R.id.editText2);
        et2=(EditText) findViewById(R.id.editText3);
        btn1=(Button) findViewById(R.id.button);
        txt1=(TextView) findViewById(R.id.textView4);

        changelang();


    }




    public void show_how( final View view) {

        intent2 = new Intent(getBaseContext(), Main2Activity.class);
        gotoActivity2(intent2);

    }
    public void start_match( final View view) {
        String name1=et1.getText().toString();
        String name2=et2.getText().toString();
        if (name1.matches("")||name2.matches("")) {
            Toast.makeText(this, toast_st, Toast.LENGTH_SHORT).show();
            return;
        }
        pgsBar.setVisibility(v.VISIBLE);


        percentage2=calculate(name1,name2);

        pgsBar.setVisibility(v.GONE);
        intent = new Intent(getBaseContext(), MainActivity2.class);
        //intent.putExtra("percentage", percentage);
        intent.putExtra("percentage2", percentage2);
        intent.putExtra("name1", name1);
        intent.putExtra("name2", name2);


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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.change, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorite) {
            // do something here
            // single item array instance to store which element is selected by user initially
            // it should be set to zero meaning none of the element is selected by default
            selected_index = sharedPreferences.getInt("index", 0);
            final int[] checkedItem = {selected_index};

            // AlertDialog builder instance to build the alert dialog
            builder = new AlertDialog.Builder(MainActivity.this);

            // set the custom icon to the alert dialog
            builder.setIcon(R.drawable.change);

            // title of the alert dialog
            builder.setTitle("Change Language:");

            // list of the items to be displayed to the user in the
            // form of list so that user can select the item from
            final String[] listItems = new String[]{"English", "Español", "Français", "Italiano","Deutsch","Português","Русский"};


            // the function setSingleChoiceItems is the function which
            // builds the alert dialog with the single item selection
            builder.setSingleChoiceItems(listItems, checkedItem[0], (dialog, which) -> {
                // update the selected item which is selected by the user so that it should be selected
                // when user opens the dialog next time and pass the instance to setSingleChoiceItems method
                checkedItem[0] = which;

                // now also update the TextView which previews the selected item
                //tvSelectedItemPreview.setText("Selected Item is : " + listItems[which]);




                SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                myEdit.putInt("index",which);

// Once the changes have been made, we need to commit to apply those changes made,
// otherwise, it will throw an error
                myEdit.apply();

                changelang();
                // when selected an item the dialog should be closed with the dismiss method
                dialog.dismiss();


            });

            // set the negative button if the user is not interested to select or change already selected item
            builder.setNegativeButton("Cancel", (dialog, which) -> {

            });

            // create and build the AlertDialog instance with the AlertDialog builder instance
            customAlertDialog = builder.create();

            // show the alert dialog when the button is clicked
            customAlertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void changelang() {

        selected_index = sharedPreferences.getInt("index", 0);
        switch (selected_index){
            case 0:
                et1.setHint(getString(R.string.hint1));
                et2.setHint(getString(R.string.hint2));
                btn1.setText(getString(R.string.btn));
                txt1.setText(getString(R.string.link));
                this.setTitle(getString(R.string.app_name));
                toast_st=getString(R.string.toast);
                break;
            case 1:
                et1.setHint(getString(R.string.hint1_es));
                et2.setHint(getString(R.string.hint2_es));
                btn1.setText(getString(R.string.btn_es));
                txt1.setText(getString(R.string.link_es));
                this.setTitle(getString(R.string.app_name_es));
                toast_st=getString(R.string.toast_es);
                break;
            case 2:
                et1.setHint(getString(R.string.hint1_fr));
                et2.setHint(getString(R.string.hint2_fr));
                btn1.setText(getString(R.string.btn_fr));
                txt1.setText(getString(R.string.link_fr));
                this.setTitle(getString(R.string.app_name_fr));
                toast_st=getString(R.string.toast_fr);
                break;
            case 3:
                et1.setHint(getString(R.string.hint1_it));
                et2.setHint(getString(R.string.hint2_it));
                btn1.setText(getString(R.string.btn_it));
                txt1.setText(getString(R.string.link_it));
                this.setTitle(getString(R.string.app_name_it));
                toast_st=getString(R.string.toast_it);
                break;
            case 4:
                et1.setHint(getString(R.string.hint1_de));
                et2.setHint(getString(R.string.hint2_de));
                btn1.setText(getString(R.string.btn_de));
                txt1.setText(getString(R.string.link_de));
                this.setTitle(getString(R.string.app_name_de));
                toast_st=getString(R.string.toast_de);
                break;
            case 5:
                et1.setHint(getString(R.string.hint1_pt));
                et2.setHint(getString(R.string.hint2_pt));
                btn1.setText(getString(R.string.btn_pt));
                txt1.setText(getString(R.string.link_pt));
                this.setTitle(getString(R.string.app_name_pt));
                toast_st=getString(R.string.toast_pt);
                break;
            case 6:
                et1.setHint(getString(R.string.hint1_ru));
                et2.setHint(getString(R.string.hint2_ru));
                btn1.setText(getString(R.string.btn_ru));
                txt1.setText(getString(R.string.link_ru));
                this.setTitle(getString(R.string.app_name_ru));
                toast_st=getString(R.string.toast_ru);
                break;
            default:
                et1.setHint(getString(R.string.hint1));
                et2.setHint(getString(R.string.hint2));
                btn1.setText(getString(R.string.btn));
                txt1.setText(getString(R.string.link));
                this.setTitle(getString(R.string.app_name));
                break;
        }
    }



}