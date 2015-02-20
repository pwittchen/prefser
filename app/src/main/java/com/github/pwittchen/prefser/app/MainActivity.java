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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.pwittchen.prefser.R;
import com.github.pwittchen.prefser.library.Prefser;

/**
 * This is simple exemplary app, which shows basic usage of Prefser,
 * which is wrapper for shared preferences.
 */
public class MainActivity extends ActionBarActivity {

    private Prefser prefser;
    private EditText value;
    private Button save;
    private Button remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefser = new Prefser(this);
        value = (EditText) findViewById(R.id.value);
        save = (Button) findViewById(R.id.save);
        remove = (Button) findViewById(R.id.remove);

        final String myKey = "myKey";
        final String myValue = prefser.get(myKey, String.class, "");
        value.setText(myValue);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefser.put(myKey, value.getText().toString());
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefser.remove(myKey);
                value.setText(prefser.get(myKey, String.class, ""));
            }
        });
    }
}
