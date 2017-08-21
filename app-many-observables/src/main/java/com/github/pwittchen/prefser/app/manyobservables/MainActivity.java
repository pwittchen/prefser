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
package com.github.pwittchen.prefser.app.manyobservables;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.pwittchen.prefser.library.Prefser;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {

  private final static String EMPTY_STRING = "";
  private final static String MY_KEY_ONE = "MY_KEY_ONE";
  private final static String MY_KEY_TWO = "MY_KEY_TWO";
  private final static String MY_KEY_THREE = "MY_KEY_THREE";

  private Prefser prefser;
  private Subscription subscriptionOne;
  private Subscription subscriptionTwo;
  private Subscription subscriptionThree;

  @BindView(R.id.value_one) protected EditText valueOne;
  @BindView(R.id.value_two) protected EditText valueTwo;
  @BindView(R.id.value_three) protected EditText valueThree;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    prefser = new Prefser(this);
  }

  @Override protected void onResume() {
    super.onResume();
    createSubscriptionOne();
    createSubscriptionTwo();
    createSubscriptionThree();
  }

  @Override protected void onPause() {
    super.onPause();
    subscriptionOne.unsubscribe();
    subscriptionTwo.unsubscribe();
    subscriptionThree.unsubscribe();
  }

  private void createSubscriptionOne() {
    subscriptionOne = prefser.getAndObserve(MY_KEY_ONE, String.class, EMPTY_STRING)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(value -> {
          valueOne.setText(value);
          showToast(value);
        });
  }

  private void createSubscriptionTwo() {
    subscriptionTwo = prefser.getAndObserve(MY_KEY_TWO, String.class, EMPTY_STRING)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(value -> {
          valueTwo.setText(value);
          showToast(value);
        });
  }

  private void createSubscriptionThree() {
    subscriptionThree = prefser.getAndObserve(MY_KEY_THREE, String.class, EMPTY_STRING)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(value -> {
          valueThree.setText(value);
          showToast(value);
        });
  }

  private void showToast(String message) {
    if (message.equals(EMPTY_STRING)) {
      message = "empty";
    }
    String formattedMessage = String.format("value is %s", message);
    Toast.makeText(MainActivity.this, formattedMessage, Toast.LENGTH_SHORT).show();
  }

  @OnClick(R.id.save) public void onSaveClicked() {
    prefser.put(MY_KEY_ONE, valueOne.getText().toString());
    prefser.put(MY_KEY_TWO, valueTwo.getText().toString());
    prefser.put(MY_KEY_THREE, valueThree.getText().toString());
  }
}
