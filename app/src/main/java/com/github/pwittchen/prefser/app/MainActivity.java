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

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.pwittchen.prefser.R;
import com.github.pwittchen.prefser.library.rx2.Prefser;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity {

  private Prefser prefser;
  private final static String EMPTY_STRING = "";
  private final static String MY_KEY = "MY_KEY";
  private Disposable subscriptionForAllPreferences;
  private Disposable subscriptionForSinglePreference;

  @BindView(R.id.value) protected EditText value;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    prefser = new Prefser(this);
  }

  @Override protected void onResume() {
    super.onResume();
    // in this project two subscriptions were created just for an example
    // in real life, one subscription should be enough for case like that
    createSubscriptionForAllPreferences();
    createSubscriptionForSinglePreference();
  }

  private void createSubscriptionForAllPreferences() {
    subscriptionForAllPreferences = prefser.observePreferences()
        .subscribeOn(Schedulers.io())
        .filter(key -> key.equals(MY_KEY))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(value -> Toast.makeText(MainActivity.this,
            String.format("global: Value in %s changed", MY_KEY), Toast.LENGTH_SHORT).show());
  }

  private void createSubscriptionForSinglePreference() {

    // here, we created new Subscriber as an extended example,
    // but we can also use simple Action1 interface with call(String key) method
    // as in createSubscriptionForAllPreferences() method

    prefser.getAndObserve(MY_KEY, String.class, EMPTY_STRING)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override public void onSubscribe(@NonNull Disposable d) {
            subscriptionForSinglePreference = d;
          }

          @Override public void onNext(@NonNull String s) {
            value.setText(String.valueOf(s));
            Toast.makeText(MainActivity.this, String.format("single: Value in %s changed", MY_KEY),
                Toast.LENGTH_SHORT).show();
          }

          @Override public void onError(@NonNull Throwable e) {
            Toast.makeText(MainActivity.this,
                String.format("Problem with accessing key %s", MY_KEY), Toast.LENGTH_SHORT).show();
          }

          @Override public void onComplete() {
          }
        });
  }

  @Override protected void onPause() {
    super.onPause();
    if (!subscriptionForAllPreferences.isDisposed()) {
      subscriptionForAllPreferences.dispose();
    }

    if (!subscriptionForSinglePreference.isDisposed()) {
      subscriptionForSinglePreference.dispose();
    }
  }

  @OnClick(R.id.save) public void onSaveClicked() {
    prefser.put(MY_KEY, value.getText().toString());
  }

  @OnClick(R.id.put_lenny_face) public void onPutNameClicked() {
    prefser.put(MY_KEY, "Hi! I'm Piotr!");
  }

  @OnClick(R.id.remove) public void onRemoveClicked() {
    prefser.remove(MY_KEY);
  }
}
