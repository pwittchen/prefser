/*
 * Copyright (C) 2015 Piotr Wittchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pwittchen.prefser.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.github.pwittchen.prefser.R;
import com.github.pwittchen.prefser.library.Prefser;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * This is simple exemplary app, which shows basic usage of Prefser,
 * which is wrapper for SharedPreferences.
 */
public class MainActivity extends ActionBarActivity {

    private Prefser prefser;
    private final static String MY_KEY = "MY_KEY";
    private Subscription subscription;

    @InjectView(R.id.value)
    protected EditText value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        prefser = new Prefser(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        value.setText(prefser.get(MY_KEY, String.class, ""));

        subscription = prefser.fromDefaultPreferences()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String key) {
                        return key.equals(MY_KEY);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String key) {
                        value.setText(prefser.get(key, String.class, ""));
                        Toast.makeText(
                                MainActivity.this,
                                String.format("value in %s changed", MY_KEY),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }

    @OnClick(R.id.save)
    public void onSaveClicked() {
        prefser.put(MY_KEY, value.getText().toString());
    }

    @OnClick(R.id.put_lenny_face)
    public void onPutLennyFaceClicked() {
        prefser.put(MY_KEY, "Hi! I'm Lenny ( ͡° ͜ʖ ͡°)");
    }

    @OnClick(R.id.remove)
    public void onRemoveClicked() {
        prefser.remove(MY_KEY);
    }
}
