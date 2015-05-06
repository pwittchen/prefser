package com.example.myapplication;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.github.pwittchen.prefser.library.Prefser;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SettingsActivity extends PreferenceActivity {

    private Prefser prefser;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SamplePreferenceFragment())
                .commit();

        prefser = new Prefser(this);
        showToastOnPreferenceUpdate();
    }

    public static class SamplePreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    private void showToastOnPreferenceUpdate() {
        prefser.observeDefaultPreferences()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String key) {
                        Toast.makeText(
                                SettingsActivity.this,
                                String.format("%s is changed", key),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}
