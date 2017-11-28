package com.gokhanmoral.stweaks.app;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class STweaksSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        MainActivity.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new STweaksPreferenceFragment()).commit();
    }

    public static class STweaksPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.stweaks_settings);
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            getPreferenceManager().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            final String icon = prefs.getString("pref_launcher_icon", "");
            // SharedPreferences.Editor editor = prefs.edit();
            switch (key) {
                case "pref_launcher_icon":
                    switch (icon) {
                        case "material":
                            getActivity().getPackageManager().setComponentEnabledSetting(
                                    new ComponentName("com.gokhanmoral.stweaks.app", "com.gokhanmoral.stweaks.app.MainActivity-Material"),
                                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                            getActivity().getPackageManager().setComponentEnabledSetting(
                                    new ComponentName("com.gokhanmoral.stweaks.app", "com.gokhanmoral.stweaks.app.MainActivity-NoIcon"),
                                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                            getActivity().getPackageManager().setComponentEnabledSetting(
                                    new ComponentName("com.gokhanmoral.stweaks.app", "com.gokhanmoral.stweaks.app.MainActivity-Stock"),
                                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                            Toast.makeText(getActivity(), R.string.change_icon,
                                    Toast.LENGTH_LONG).show();
                            break;
                        case "stock":
                            getActivity().getPackageManager().setComponentEnabledSetting(
                                    new ComponentName("com.gokhanmoral.stweaks.app", "com.gokhanmoral.stweaks.app.MainActivity-Stock"),
                                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                            getActivity().getPackageManager().setComponentEnabledSetting(
                                    new ComponentName("com.gokhanmoral.stweaks.app", "com.gokhanmoral.stweaks.app.MainActivity-NoIcon"),
                                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                            getActivity().getPackageManager().setComponentEnabledSetting(
                                    new ComponentName("com.gokhanmoral.stweaks.app", "com.gokhanmoral.stweaks.app.MainActivity-Material"),
                                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                            Toast.makeText(getActivity(), R.string.change_icon,
                                    Toast.LENGTH_LONG).show();
                            break;
                        case "noicon":
                            getActivity().getPackageManager().setComponentEnabledSetting(
                                    new ComponentName("com.gokhanmoral.stweaks.app", "com.gokhanmoral.stweaks.app.MainActivity-NoIcon"),
                                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                            getActivity().getPackageManager().setComponentEnabledSetting(
                                    new ComponentName("com.gokhanmoral.stweaks.app", "com.gokhanmoral.stweaks.app.MainActivity-Material"),
                                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                            getActivity().getPackageManager().setComponentEnabledSetting(
                                    new ComponentName("com.gokhanmoral.stweaks.app", "com.gokhanmoral.stweaks.app.MainActivity-Stock"),
                                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                            Toast.makeText(getActivity(), R.string.no_icon,
                                    Toast.LENGTH_LONG).show();
                            break;
                    }
                    break;
                case "pref_theme":
                    String theme = prefs.getString("pref_theme", "");
                    switch (theme) {
                        case "light":
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(R.string.theme_change)
                                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (icon.equals("noicon")) {
                                                Intent restart = new Intent(getActivity(), MainActivity.class);
                                                restart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(restart);
                                            } else {
                                                Intent restart = getActivity().getPackageManager()
                                                        .getLaunchIntentForPackage(getActivity().getPackageName());
                                                restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(restart);
                                            }
                                        }
                                    })
                                    .setTitle(R.string.warning)
                                    .create()
                                    .show();
                            break;
                        case "dark":
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setMessage(R.string.theme_change)
                                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (icon.equals("noicon")) {
                                                Intent restart = new Intent(getActivity(), MainActivity.class);
                                                restart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(restart);
                                            } else {
                                                Intent restart = getActivity().getPackageManager()
                                                        .getLaunchIntentForPackage(getActivity().getPackageName());
                                                restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(restart);
                                            }
                                        }
                                    })
                                    .setTitle(R.string.warning)
                                    .create()
                                    .show();
                            break;
                        case "sammy":
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                            builder2.setMessage(R.string.theme_change)
                                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (icon.equals("noicon")) {
                                                Intent restart = new Intent(getActivity(), MainActivity.class);
                                                restart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(restart);
                                            } else {
                                                Intent restart = getActivity().getPackageManager()
                                                        .getLaunchIntentForPackage(getActivity().getPackageName());
                                                restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(restart);
                                            }
                                        }
                                    })
                                    .setTitle(R.string.warning)
                                    .create()
                                    .show();
                            break;
                    }
                    break;
            }
        }
    }
}
