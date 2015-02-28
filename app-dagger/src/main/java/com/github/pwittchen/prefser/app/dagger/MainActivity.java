package com.github.pwittchen.prefser.app.dagger;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.github.pwittchen.prefser.library.Prefser;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MainActivity extends BaseActivity {

    @Inject
    public Prefser prefser;

    @InjectView(R.id.value)
    protected EditText value;

    private final static String MY_KEY = "MY_KEY";
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String text = prefser.get(MY_KEY, String.class, "");
        value.setText(text);

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
