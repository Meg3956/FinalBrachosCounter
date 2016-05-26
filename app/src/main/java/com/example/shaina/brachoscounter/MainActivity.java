package com.example.shaina.brachoscounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends BrachosCounterActivity
{


    final int REQUEST_CODE = 1;
    ArrayList<String> brachosDescriptions;
    ArrayList<Integer> brachosNumbers;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        setupActionBar();
        brachosDescriptions = new ArrayList<> ();
        brachosNumbers = new ArrayList<> ();
        restoreNonSettingsActivityPreferences ();
        PreferenceManager.setDefaultValues (this, R.xml.pref_general, true);

    }
    private void setupActionBar() {
        try {
            getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } catch (NullPointerException nullPointerException) {

        }
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult (requestCode, resultCode, data);
        if (resultCode == RESULT_OK&&requestCode==REQUEST_CODE) {

                    brachosNumbers=data.getIntegerArrayListExtra(sBRACHOS_NUMBERS);
                    brachosDescriptions=data.getStringArrayListExtra(sBRACHOS_DESCRIPTION);


        }
    }

    public void launchDaveningPage (View view)
    {
        Intent intent = new Intent (this, DaveningActivity.class);

        intent.putIntegerArrayListExtra(sBRACHOS_NUMBERS,brachosNumbers);
        intent.putStringArrayListExtra(sBRACHOS_DESCRIPTION,brachosDescriptions);
       startActivityForResult (intent, REQUEST_CODE);
    }

    public void launchAddYourOwnPage (View view)
    {
        Intent intent = new Intent (this, AddYourOwnActivity.class);

        intent.putIntegerArrayListExtra(sBRACHOS_NUMBERS,brachosNumbers);
        intent.putStringArrayListExtra(sBRACHOS_DESCRIPTION,brachosDescriptions);
        startActivityForResult (intent, REQUEST_CODE);
    }

    public void launchFoodDrink (View view)
    {
        Intent intent = new Intent (this, BrachosActivity.class);
        String[] foodBrachos = {"Hamotzi", "Mezonos", "Hagafen", "Haetz", "Ha'adama", "Shehakol",
                                "Birkas Hamazon", "Al Hamichya", "Borei Nefashos"};
        intent.putExtra (getString (R.string.brachosList), foodBrachos);

        intent.putIntegerArrayListExtra(sBRACHOS_NUMBERS,brachosNumbers);
        intent.putStringArrayListExtra(sBRACHOS_DESCRIPTION,brachosDescriptions);
        startActivityForResult (intent, REQUEST_CODE);
    }

    public void launchHolidays(View view) {
        Intent intent = new Intent(this, BrachosActivity.class);
        String[] holidayBrachos = {"L'hadlik Ner Chanuka", "She'asa Nisim", "Shehechiyanu", "Lulav and Esrog",
                "Leisheiv BaSukkah", "Mikra Megillah"};
        intent.putExtra(getString(R.string.brachosList), holidayBrachos);

        intent.putIntegerArrayListExtra(sBRACHOS_NUMBERS,brachosNumbers);
        intent.putStringArrayListExtra(sBRACHOS_DESCRIPTION,brachosDescriptions);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void launchBirchosHanehenin(View view) {
        Intent intent = new Intent(this, BrachosActivity.class);
        String[] haneheninBrachos = {"Minei Besamim", "Atzei Besamim", "Isvei Besamim", "Reiach tov l'peiros"};
        intent.putExtra(getString(R.string.brachosList), haneheninBrachos);

        intent.putIntegerArrayListExtra(sBRACHOS_NUMBERS,brachosNumbers);
        intent.putStringArrayListExtra(sBRACHOS_DESCRIPTION,brachosDescriptions);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void launchMiscellaneous(View view) {
        Intent intent = new Intent(this, BrachosActivity.class);
        String[] miscBrachos = {"Sheva Brachos Bracha","Sefiras Haomer", "Mezuzah", "Tevilas Keilim",
                "Hafrashas Challah", "Maaser", "Oseh Maaseh Beraishis", "Shekocho Ugvuraso",
                "Seeing a rainbow", "Seeing an ocean", "Meshaneh Habriyos", "Seeing a blooming tree",
                "Seeing a Torah scholar", "Seeing a secular scholar", "Chacham Harazim", "Dayan Haemes",
                "HaTov V'hameitiv", "Sha'asa li nes", "Shehechiyanu", "Asher Yatzar", "Tefillas Haderech"};
        intent.putExtra(getString(R.string.brachosList), miscBrachos);

        intent.putIntegerArrayListExtra(sBRACHOS_NUMBERS,brachosNumbers);
        intent.putStringArrayListExtra(sBRACHOS_DESCRIPTION,brachosDescriptions);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void launchTotalBreakdown(View view) {

       launchTotalBreakdown(brachosDescriptions,brachosNumbers);
    }


    //TODO: Do we need an onResume to restore things?
   /* @Override
    protected void onResume ()
    {
        super.onResume ();
    }*/

    @Override
    protected void onStop ()
    {
        super.onStop ();
        customOnStop (brachosDescriptions,brachosNumbers);
    }
    private void restoreNonSettingsActivityPreferences () {

        SharedPreferences settings = getSharedPreferences(sPREFS_FIELDS, MODE_PRIVATE);
        String descriptionString = settings.getString(sBRACHOS_DESCRIPTION, "");
        if (!descriptionString.isEmpty()) {
            brachosDescriptions = restoreStringListFromJSON(descriptionString);
            String brachosNumbersString = settings.getString(sBRACHOS_NUMBERS, "[]");


            ArrayList<Integer> brachosArrayList = restoreIntegerListFromJSON(brachosNumbersString);


            brachosNumbers = brachosArrayList;


        }
    }
   /*private void restorePreferencesSavedFromSettingsActivity ()
    {
        String currentKey;
        String currentDefaultValue;

        // Get handle to custom preferences (not from settings menu)
        // Used for persisting state to storage

        // First, get handle to user settings/preferences
          SharedPreferences defaultSharedPreferences =
             PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //TODO: Set to our preferences!!!
        // Show Background Picture Preference
       currentKey = getString(R.string.male_option);
        mPrefMale = defaultSharedPreferences.getBoolean(currentKey, false);

        currentKey = getString(R.string.male_option);
        mPrefMale = defaultSharedPreferences.getBoolean(currentKey, false);
    }*/


    /*private void restoreNonSettingsActivityPreferences ()
    {
        SharedPreferences settings = getSharedPreferences (sPREFS_FIELDS, MODE_PRIVATE);
        String descriptionString = settings.getString (sBRACHOS_DESCRIPTION, "");
        if (!descriptionString.isEmpty ()) {
            brachosDescriptions = restoreStringListFromJSON (descriptionString);
            String brachosNumbersString=settings.getString (sBRACHOS_NUMBERS, "[]");


            ArrayList brachosArrayList=restoreIntegerListFromJSON (brachosNumbersString);

            brachosNumbers = (ArrayList<Integer>) brachosArrayList;


        }





    }*/

   /* private void saveNonSettingsActivityPreferences ()
    {
        SharedPreferences settings = getSharedPreferences (sPREFS_FIELDS, MODE_PRIVATE); //MP==0
        SharedPreferences.Editor settingsEditor = settings.edit ();

        settingsEditor.clear ();

        String jsonBrachosDescriptions = getJSON (brachosDescriptions);
        String jsonBrachosNumbers = getJSON (brachosNumbers);
        settingsEditor.putString (sBRACHOS_DESCRIPTION, jsonBrachosDescriptions);
        settingsEditor.putString (sBRACHOS_NUMBERS, jsonBrachosNumbers);
        // Tax and tip are derived from values stored automatically via Settings Activity
        // So we need to store only the other two EditTexts
        //TODO: add our prefs*/
     /*
        */
        // settingsEditor.putString (mSUBTOTAL_PREF_KEY, mSubTotalField.getText ().toString ());
        //settingsEditor.putString (mPAYERS_PREF_KEY, mPayersField.getText ().toString ());

     //   settingsEditor.apply ();
  //  }



  /*  private void addBrachosFromRestoredActivity ()
    {
        //TODO: Increment instead of add all
        brachosDescriptions.addAll (brachosDescriptionsToAdd);
        brachosDescriptionsToAdd.clear ();

        brachosNumbers.addAll (brachosNumbersToAdd);
        brachosNumbersToAdd.clear ();
    }*/

    public void viewTotalBrachos (View view)
    {
        viewTotalBrachos();
    }


    public void viewTotalBrachos ()
    {
        showSnackbar(getString(R.string.total_brachos) + " " + getTotalBrachos(brachosNumbers));
    }

    public void clearBrachos (View view)
    {
        super.clearBrachos(brachosDescriptions,brachosNumbers);
        showSnackbar("Brachos cleared.");
    }


   /* public <T> void setList(String key, List<T> list)
    {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        set(key, json);
    }*/

    @Override public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater ().inflate (R.menu.menu_main, menu);
        return true; //super.onCreateOptionsMenu (menu);
    }

    @Override public boolean onOptionsItemSelected (MenuItem item)
    {
        int id = item.getItemId ();

        //noinspection SimplifiableIfStatement
        switch (id)
        {

                case R.id.action_settings: {
                    launchSettings(brachosDescriptions,brachosNumbers);
                    return true;
                }


            case R.id.action_view_total: {
                viewTotalBrachos();
                return true;
            }
            case R.id.about:{
                showAbout();
                return true;
            }

        }

        return super.onOptionsItemSelected (item);
    }

    // LG work-around
    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event)
    {
        boolean isOldLG = ((keyCode == KeyEvent.KEYCODE_MENU) &&
                                   (Build.VERSION.SDK_INT <= 16) &&
                                   (Build.MANUFACTURER.compareTo ("LGE") == 0));

        //noinspection SimplifiableConditionalExpression
        return isOldLG ? true : super.onKeyDown (keyCode, event);
    }

    @Override
    public boolean onKeyUp (int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_MENU) &&
                (Build.VERSION.SDK_INT <= 16) &&
                (Build.MANUFACTURER.compareTo ("LGE") == 0)) {
            openOptionsMenu ();
            return true;
        }
        return super.onKeyUp (keyCode, event);
    }


    private void showSnackbar(String snackbarText){
        final View cl = findViewById (R.id.activity_main);
        Snackbar sb = Snackbar.make (cl, snackbarText,
                Snackbar.LENGTH_LONG);
        sb.show();
    }
    // This method is called from the onClick property of the menu item "About"
    @SuppressWarnings ( {"UnusedParameters", "unused"})
    public void showAbout (MenuItem item)
    {
        showAbout ();
    }
    protected void showAbout ()
    {

        // Create listener for use with dialog window (could also be created anonymously in setButton...
        DialogInterface.OnClickListener dialogOnClickListener =
                new DialogInterface.OnClickListener ()
                {
                    @Override
                    public void onClick (DialogInterface dialog, int which)
                    {
                        // Nothing needed to do here
                    }
                };

        // Create dialog window
        AlertDialog alertDialogAbout = new AlertDialog.Builder (MainActivity.this).create ();
        alertDialogAbout.setTitle (getString (R.string.aboutDialog_title));;
        alertDialogAbout.setMessage (getString (R.string.aboutDialog_banner));
        alertDialogAbout.setButton (DialogInterface.BUTTON_NEUTRAL,
                getString (R.string.OK), dialogOnClickListener);

        // Show the dialog window
        alertDialogAbout.show ();
    }
}

