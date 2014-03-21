package com.hasta.htweaker;

import com.hasta.htweaker.Utils;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.widget.Toast;

public class Misc extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	private static final String FBDELAY = "/sys/module/fbearlysuspend/parameters/fbdelay";
    private static final String FBDELAY_MS = "/sys/module/fbearlysuspend/parameters/fbdelay_ms";
    private static final String INITD = "/system/etc/init.d";
    private static final String one = "uno";
    private static final String sev = "sette";
    private static final String nine = "nove";
    private static final String ten = "dieci";
    private static ContentResolver cr;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences sharedPref = this.getActivity().getSharedPreferences("misc",0);
        addPreferencesFromResource(R.xml.misc);
        
        cr = getActivity().getContentResolver();
                onSharedPreferenceChanged(sharedPref,"crtoff");
                onSharedPreferenceChanged(sharedPref,"sd");
                onSharedPreferenceChanged(sharedPref,"net");
                onSharedPreferenceChanged(sharedPref,"gps");
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sp,
			String key) {
		if(key.equals("crtoff")) handleCRT();
		if(key.equals("sd")) handleSD();
		if(key.equals("net")) handleNET();
		if(key.equals("gps")) handleGPS();
	}
	
	public void handleCRT() {
		final CheckBoxPreference crtoff = (CheckBoxPreference) findPreference("crtoff");
        int a = getInt(one, 0);
        crtoff.setChecked(a != 0);
        crtoff.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
                public boolean onPreferenceClick(Preference preference) {
                    if (crtoff.isChecked()) {
                        putInt(one, 1);                       
                        Utils.mRunAsSU("echo 1 > "+FBDELAY,"echo 350 > "+FBDELAY_MS);
                        Utils.mountSystemRW();
                        Utils.copyAssets("crt_on",INITD,777,getActivity().getApplicationContext());
                        ShowToast("Animation enabled!");
                    } else {
                    	putInt(one, 0);	                        
                        Utils.mRunAsSU("echo 0 > "+FBDELAY,"echo 0 > "+FBDELAY_MS);
                        Utils.mountSystemRW();
                        Utils.mRunAsSU("rm /system/etc/init.d/crt_on");
                        ShowToast("Animation disabled!");
                    }
                    return false;
                }
        });
	}
        public void handleSD(){
			final CheckBoxPreference sd = (CheckBoxPreference) findPreference("sd");
	        int e = getInt(sev, 0);
	        sd.setChecked(e != 0);
	        sd.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
	                public boolean onPreferenceClick(Preference preference) {
	                    if (sd.isChecked()) {
	                        putInt(sev, 1);
	                        Utils.mountSystemRW();
	                        Utils.copyAssets("sd",INITD,777,getActivity().getApplicationContext());
	                        ShowToast("SD tweaks enabled, changes will be applied after reboot!");
	                    } else {
	                    	putInt(sev, 0);
	                        Utils.mountSystemRW();
	                        Utils.mRunAsSU("rm /system/etc/init.d/sd");
	                        ShowToast("SD tweaks removed, changes will be applied after reboot!");
	                    }
	                    return false;
	                }
	        });
	}

        public void handleNET(){
			final CheckBoxPreference net = (CheckBoxPreference) findPreference("net");
	        int g = getInt(nine, 0);
	        net.setChecked(g != 0);
	        net.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
	                public boolean onPreferenceClick(Preference preference) {
	                    if (net.isChecked()) {
	                        putInt(nine, 1);
	                        Utils.mountSystemRW();
	                        Utils.copyAssets("net",INITD,777,getActivity().getApplicationContext());
	                        ShowToast("Net tweaks enabled, changes will be applied after reboot!");
	                    } else {
	                    	putInt(nine, 0);
	                        Utils.mountSystemRW();
	                        Utils.mRunAsSU("rm /system/etc/init.d/net");
	                        ShowToast("NET tweaks removed, changes will be applied after reboot!");
	                    }
	                    return false;
	                }
	        });
	}
        public void handleGPS(){
			PreferenceScreen prefs = getPreferenceScreen();
	        ListPreference mListPref = (ListPreference) prefs.findPreference("gps");
	        mListPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
				
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					putInt(ten, Integer.parseInt((String) newValue));
					
					if (Integer.valueOf((String) newValue) == 1) {
                        Utils.copyAssets("gpsE.conf","/system/etc",644,getActivity().getApplicationContext());
					    Utils.mRunAsSU("cp -f /system/etc/gpsE.conf /system/etc/gps.conf","rm /system/etc/gpsE.conf");}
                    else if  (Integer.valueOf((String) newValue) == 2){
                    	Utils.copyAssets("gpsNA.conf","/system/etc",644,getActivity().getApplicationContext());
                    	Utils.mRunAsSU("cp -f /system/etc/gpsNA.conf /system/etc/gps.conf","rm /system/etc/gpsNA.conf");}                     
                    else if (Integer.valueOf((String) newValue) == 3){
                    	Utils.copyAssets("gpsSA.conf","/system/etc",644,getActivity().getApplicationContext());
                    	Utils.mRunAsSU("cp -f /system/etc/gpsSA.conf /system/etc/gps.conf","rm /system/etc/gpsSA.conf");}                      
                    else if (Integer.valueOf((String) newValue) == 4){
                    	Utils.copyAssets("gpsA.conf","/system/etc",644,getActivity().getApplicationContext());
                    	Utils.mRunAsSU("cp -f /system/etc/gpsA.conf /system/etc/gps.conf","rm /system/etc/gpsA.conf");}
					return true;
				}
			});    
		}
public void ShowToast(String msg) {
	        Toast.makeText(getActivity().getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
	    }
    public int getInt(String key, int defValue) {
    	return Settings.System.getInt(cr, key, defValue);
    }
    
    public void putInt(String key, int val) {
    	Settings.System.putInt(cr, key, val);
    }
}