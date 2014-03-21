package com.hasta.htweaker;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.widget.Toast;

public class Kernel extends PreferenceFragment implements OnSharedPreferenceChangeListener {
    
	private static final String INITD = "/system/etc/init.d";
    private static final String FSYNCODE = "/sys/kernel/fsync/mode";
    private static final String two = "due";
    private static final String six = "sei";
    private static final String twelve = "dodici";
    private static final String thirt = "tredici";
    private static final String fourth = "quattordici";
    private static final String eight = "otto";
    private static ContentResolver cr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.kernel);
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("kernel",0);
        cr = getActivity().getContentResolver();
        onSharedPreferenceChanged(sharedPref,"fsync");
        onSharedPreferenceChanged(sharedPref,"performance");
        onSharedPreferenceChanged(sharedPref,"gpu");
        onSharedPreferenceChanged(sharedPref,"iostats");
        onSharedPreferenceChanged(sharedPref,"ext");
        onSharedPreferenceChanged(sharedPref,"vac");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp,
                                          String key) {
        if(key.equals("fsync")) handleF();
        if(key.equals("performance")) handlePERF();
        if(key.equals("gpu")) handleGPU();
        if(key.equals("iostats")) handleIOSTATS();
        if(key.equals("ext")) handleEXT();
        if(key.equals("vac")) handleVAC();
    }
    public void handleF(){
        final CheckBoxPreference fsync = (CheckBoxPreference) findPreference("fsync");
        int a = getInt(two, 0);
        fsync.setChecked(a != 0);
        fsync.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
            public boolean onPreferenceClick(Preference preference) {
                if (fsync.isChecked()) {
                    putInt(two, 1);                   
                    Utils.mRunAsSU("echo 1 > " + FSYNCODE);                   
                    Utils.copyAssets("fsync_off", INITD,777,getActivity().getApplicationContext());
                    ShowToast("Fsync disabled!");
                } else {
                    putInt(two, 0);                    
                    Utils.mRunAsSU("echo 0 > " + FSYNCODE);
                    Utils.mRunAsSU("rm /system/etc/init.d/fsync_off");
                    ShowToast("Fsync enabled!");
                }
                return false;
            }
        });
    }
    public void handleVAC(){
        final CheckBoxPreference vac = (CheckBoxPreference) findPreference("vac");
        int f = getInt(eight, 0);
        vac.setChecked(f != 0);
        vac.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
            public boolean onPreferenceClick(Preference preference) {
                if (vac.isChecked()) {
                    putInt(eight, 1);
                    Utils.mountSystemRW();
                    Utils.copyAssets("vac",INITD,777,getActivity().getApplicationContext());
                    ShowToast("VAC enabled, changes will be applied after reboot!");
                } else {
                    putInt(eight, 0);
                    Utils.mountSystemRW();
                    new SU().execute("rm /system/etc/init.d/vac");
                    ShowToast("VACUUMING removed, changes will be applied after reboot!");
                }
                return false;
            }
        });
    }
        public void handlePERF(){
            final CheckBoxPreference performance = (CheckBoxPreference) findPreference("performance");
            int d = getInt(six, 0);
            performance.setChecked(d != 0);
            performance.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
                public boolean onPreferenceClick(Preference preference) {
                    if (performance.isChecked()) {
                        putInt(six, 1);
                        Utils.mountSystemRW();
                        Utils.copyAssets("perf",INITD,777,getActivity().getApplicationContext());
                        ShowToast("Performance tweaks enabled, changes will be applied after reboot!");
                    } else {
                        putInt(six, 0);
                        Utils.mountSystemRW();
                        new SU().execute("rm /system/etc/init.d/perf");
                        ShowToast("Performance tweaks removed, changes will be applied after reboot!");
                    }
                    return false;
                }
            });
        }
        public void handleGPU(){
            final CheckBoxPreference gpu = (CheckBoxPreference) findPreference("gpu");
            int h = getInt(twelve, 0);
            gpu.setChecked(h != 0);
            gpu.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
                public boolean onPreferenceClick(Preference preference) {
                    if (gpu.isChecked()) {
                        putInt(twelve, 1);
                        Utils.mountSystemRW();
                        Utils.copyAssets("GPU",INITD,777,getActivity().getApplicationContext());
                        ShowToast("GPU render enabled, changes will be applied after reboot!");
                    } else {
                        putInt(twelve, 0);
                        Utils.mountSystemRW();
                        Utils.mRunAsSU("rm /system/etc/init.d/GPU");
                        ShowToast("GPU render removed!");
                    }
                    return false;
                }
            });

        }
        public void handleIOSTATS(){
            final CheckBoxPreference iostats = (CheckBoxPreference) findPreference("iostats");
            int i = getInt(thirt, 0);
            iostats.setChecked(i != 0);
            iostats.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
                public boolean onPreferenceClick(Preference preference) {
                    if (iostats.isChecked()) {
                        putInt(thirt, 1);
                        Utils.mountSystemRW();
                        Utils.copyAssets("iostats",INITD,777,getActivity().getApplicationContext());
                        ShowToast("SD tweaks enabled, changes will be applied after reboot!");
                    } else {
                        putInt(thirt, 0);
                        Utils.mountSystemRW();
                        Utils.mRunAsSU("rm /system/etc/init.d/iostats");
                        ShowToast("SD tweaks removed, changes will be applied after reboot!");
                    }
                    return false;
                }
            });
        }
    public void handleEXT(){
        final CheckBoxPreference ext = (CheckBoxPreference) findPreference("ext");
        int j = getInt(fourth, 0);
        ext.setChecked(j != 0);
        ext.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
            public boolean onPreferenceClick(Preference preference) {
                if (ext.isChecked()) {
                    putInt(fourth, 1);
                    Utils.mountSystemRW();
                    Utils.copyAssets("ext",INITD,777,getActivity().getApplicationContext());
                    ShowToast("EXT tweaks enabled, changes will be applied after reboot!");
                } else {
                    putInt(fourth, 0);
                    Utils.mountSystemRW();
                    Utils.mRunAsSU("rm /system/etc/init.d/ext");
                    ShowToast("EXT tweaks removed, changes will be applied after reboot!");
                }
                return false;
            }
        });
    }
    public void ShowToast(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    public int getInt(String key, int defValue) {
        return Settings.System.getInt(cr, key, defValue);
    }

    public void putInt(String key, int val) {
        Settings.System.putInt(cr, key, val);
    }
}