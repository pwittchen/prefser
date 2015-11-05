package com.github.pwittchen.prefser.app.manyobservables;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.github.pwittchen.prefser.library.Prefser;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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

  @InjectView(R.id.value_one) protected EditText valueOne;
  @InjectView(R.id.value_two) protected EditText valueTwo;
  @InjectView(R.id.value_three) protected EditText valueThree;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
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
        .subscribe(new Action1<String>() {
          @Override public void call(String value) {
            valueOne.setText(value);
            showToast(value);
          }
        });
  }

  private void createSubscriptionTwo() {
    subscriptionTwo = prefser.getAndObserve(MY_KEY_TWO, String.class, EMPTY_STRING)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<String>() {
          @Override public void call(String value) {
            valueTwo.setText(value);
            showToast(value);
          }
        });
  }

  private void createSubscriptionThree() {
    subscriptionThree = prefser.getAndObserve(MY_KEY_THREE, String.class, EMPTY_STRING)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<String>() {
          @Override public void call(String value) {
            valueThree.setText(value);
            showToast(value);
          }
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
