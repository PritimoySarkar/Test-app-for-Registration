package com.p2ms.guideapp.session;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class LocalSessionStore {
    private SharedPreferences preferences;
    private SharedPreferences.Editor spEditor;
    private Context context;
    public LocalSessionStore(Context context) {
        this.context=context;
        //Asking for shared preference access in the current context
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void storeData(String key, String value){
        spEditor=preferences.edit(); //changing thr mode of shared pref. to writable
        spEditor.putString(key, value); //passing the data into shared pref
        spEditor.commit();
    }
    public String getData(String key){
        return preferences.getString(key,""); //retriving the data from shared pref.
    }
    public void clearData(){
        spEditor=preferences.edit();
        spEditor.clear(); //to clear all the data of current context saved
        //spEditor.remove("key"); //to remove particular data
        spEditor.commit();
    }


}
