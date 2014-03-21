package com.hasta.htweaker;

import com.hasta.htweaker.Utils;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.widget.Toast;

public class Battery extends PreferenceFragment implements OnSharedPreferenceChangeListener {
    
	private static ContentResolver cr;
	private static final String INITD = "/system/etc/init.d";
	private static final String DEEP= "/d/cpuidle/deepest_state";
    private static final String one = "deep";
    private static final String two = "sleepers";
    
    public void ShowToast(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    public int getInt(String key, int defValue) {
        return Settings.System.getInt(cr, key, defValue);
    }

    public void putInt(String key, int val) {
        Settings.System.putInt(cr, key, val);
    }
   
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.battery);
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("battery",0);
        cr = getActivity().getContentResolver();
        onSharedPreferenceChanged(sharedPref,"deep");
        onSharedPreferenceChanged(sharedPref,"sleepers");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key)
    {
    	if(key.equals("deep")) handleDEEP();
    	if(key.equals("sleepers")) handleSLEEPERS();
        
        }

    public void handleDEEP(){
        PreferenceScreen prefs = getPreferenceScreen();
        ListPreference mListPref = (ListPreference) prefs.findPreference("deep");
        mListPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                putInt(one, Integer.parseInt((String) newValue));
                if (Integer.valueOf((String) newValue) == 1) {
                	Utils.mRunAsSU("echo 3 > "+DEEP);}
                else if  (Integer.valueOf((String) newValue) == 2){
                	Utils.mRunAsSU("echo 4 > "+DEEP);
                    Utils.copyAssets("deepest_state",INITD,777,getActivity().getApplicationContext());
                    Utils.mRunAsSU("echo -e \\#\\!/system/bin/sh'\n'echo 4 '>' /d/cpuidle/deepest_state >" + "/system/etc/init.d/deepest_state");
                }
                else if  (Integer.valueOf((String) newValue) == 3){
                	Utils.mRunAsSU("echo 5 > "+DEEP);
                    Utils.copyAssets("deepest_state",INITD,777,getActivity().getApplicationContext());
                    Utils.mRunAsSU("echo -e \\#\\!/system/bin/sh'\n'echo 5 '>' /d/cpuidle/deepest_state >" + "/system/etc/init.d/deepest_state");
                }
                return true;
            }
        });
    }
    
    public void handleSLEEPERS() {
		final CheckBoxPreference sleepers = (CheckBoxPreference) findPreference("sleepers");
        int a = getInt(two, 0);
        sleepers.setChecked(a != 0);
        sleepers.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
                public boolean onPreferenceClick(Preference preference) {
                    if (sleepers.isChecked()) {
                        putInt(two, 1);                                               
                        Utils.copyAssets("sleepers",INITD,777,getActivity().getApplicationContext());
                    } else {
                    	putInt(two, 0);	                                           	
                        Utils.mRunAsSU("rm /system/etc/init.d/sleepers");
                    }
                    return false;
                }
        });
    }

   
    }