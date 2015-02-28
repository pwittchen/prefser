package com.github.pwittchen.prefser.app.dagger;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getApplication()).inject(this);
    }
}
