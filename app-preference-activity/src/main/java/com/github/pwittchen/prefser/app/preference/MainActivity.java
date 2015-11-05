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
package com.github.pwittchen.prefser.app.preference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.github.myapplication.R;
import com.github.pwittchen.prefser.library.Prefser;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends Activity {

  private Prefser prefser;

  @InjectView(R.id.user_name) protected TextView tvUserName;
  @InjectView(R.id.app_updates) protected TextView tvApplicationUpdates;
  @InjectView(R.id.screen_on) protected TextView screenOn;
  @InjectView(R.id.download_type) protected TextView tvDownloadType;
  @InjectView(R.id.notification_type) protected TextView tvNotificationType;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
    prefser = new Prefser(this);
  }

  @Override protected void onResume() {
    super.onResume();
    readPreferences();
  }

  private void readPreferences() {
    readUserName();
    readApplicationUpdates();
    readScreenOn();
    readDownloadType();
    readNotificationType();
  }

  private void readUserName() {
    String userName = prefser.get("user_name", String.class, "");
    String formattedText = String.format("username: %s", userName);
    tvUserName.setText(formattedText);
  }

  private void readApplicationUpdates() {
    Boolean applicationUpdates = prefser.get("application_updates", Boolean.class, false);
    String formattedText = String.format("app updates: %s", applicationUpdates.toString());
    tvApplicationUpdates.setText(formattedText);
  }

  private void readScreenOn() {
    Boolean appUpdates = prefser.get("screen_on", Boolean.class, false);
    String formattedText = String.format("screen on: %s", appUpdates.toString());
    screenOn.setText(formattedText);
  }

  private void readDownloadType() {
    String downloadTypeValue = prefser.get("download_type", String.class, "0");
    String[] downloadEntries = getResources().getStringArray(R.array.download_entries);
    String downloadEntry = downloadEntries[Integer.parseInt(downloadTypeValue)];
    String formattedText = String.format("download type: %s", downloadEntry);
    tvDownloadType.setText(formattedText);
  }

  private void readNotificationType() {
    Set<String> defaultValues = new HashSet<>();
    Set<String> notificationTypeValues =
        prefser.getPreferences().getStringSet("notification_type", defaultValues);
    String[] notificationEntries = getResources().getStringArray(R.array.notification_entries);
    StringBuilder stringBuilder = new StringBuilder();

    if (!notificationTypeValues.isEmpty()) {
      for (String value : notificationTypeValues) {
        stringBuilder.append(notificationEntries[Integer.parseInt(value)]);
        stringBuilder.append(", ");
      }

      stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
    }

    String formattedText = String.format("notification type: %s", stringBuilder.toString());
    tvNotificationType.setText(formattedText);
  }

  @OnClick(R.id.show_settings) public void onShowSettingsClicked() {
    Intent intent = new Intent(this, SettingsActivity.class);
    startActivity(intent);
  }
}
