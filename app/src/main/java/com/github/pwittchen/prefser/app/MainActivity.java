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
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.github.pwittchen.prefser.R;
import com.github.pwittchen.prefser.library.Prefser;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {

  private Prefser prefser;
  private final static String EMPTY_STRING = "";
  private final static String MY_KEY = "MY_KEY";
  private Subscription subscriptionForAllPreferences;
  private Subscription subscriptionForSinglePreference;

  @InjectView(R.id.value) protected EditText value;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
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
        .filter(new Func1<String, Boolean>() {
          @Override public Boolean call(String key) {
            return key.equals(MY_KEY);
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<String>() {
          @Override public void call(String key) {
            Toast.makeText(MainActivity.this, String.format("Value in %s changed", MY_KEY),
                Toast.LENGTH_SHORT).show();
          }
        });
  }

  private void createSubscriptionForSinglePreference() {

    // here, we created new Subscriber as an extended example,
    // but we can also use simple Action1 interface with call(String key) method
    // as in createSubscriptionForAllPreferences() method

    subscriptionForSinglePreference = prefser.getAndObserve(MY_KEY, String.class, EMPTY_STRING)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Object>() {
          @Override public void onError(Throwable e) {
            Toast.makeText(MainActivity.this, String.format("Problem with accessing key %", MY_KEY),
                Toast.LENGTH_SHORT).show();
          }

          @Override public void onNext(Object o) {
            value.setText(String.valueOf(o));
            Toast.makeText(MainActivity.this, String.format("Value in %s changed, really!", MY_KEY),
                Toast.LENGTH_SHORT).show();
          }

          @Override public void onCompleted() {
            // this will never be called until we call it explicitly
            // subscriber.onCompleted() is not called
            // in Observable<String> observe(final SharedPreferences sharedPreferences)
            // method inside Prefser class, because we want to observe preference constantly
            // and we do not want to terminate subscriber
          }
        });
  }

  @Override protected void onPause() {
    super.onPause();
    subscriptionForAllPreferences.unsubscribe();
    subscriptionForSinglePreference.unsubscribe();
  }

  @OnClick(R.id.save) public void onSaveClicked() {
    prefser.put(MY_KEY, value.getText().toString());
  }

  @OnClick(R.id.put_lenny_face) public void onPutLennyFaceClicked() {
    prefser.put(MY_KEY, "Hi! I'm Lenny ( ͡° ͜ʖ ͡°)");
  }

  @OnClick(R.id.remove) public void onRemoveClicked() {
    prefser.remove(MY_KEY);
  }
}
