/*
 * Copyright (C) 2017 Pranav Pandey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pranavpandey.android.dynamic.dialogs.sample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pranavpandey.android.dynamic.dialogs.DynamicAlertDialog;
import com.pranavpandey.android.dynamic.utils.DynamicColorUtils;
import com.pranavpandey.android.dynamic.utils.DynamicLinkUtils;
import com.pranavpandey.android.dynamic.utils.DynamicPackageUtils;

/**
 * @author Pranav Pandey
 */
public class DynamicDialogsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String GITHUB_LINK =
            "https://github.com/pranavpandey/dynamic-dialogs";

    /*
      Enable vector drawable support for this activity. Please consider
      adding {@code vectorDrawables.useSupportLibrary = true} in the
      project's {@code build.gradle} file.
     */
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dynamic_dialogs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setSubtitle(R.string.app_name_sample);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setColorFilter(DynamicColorUtils.getTintColor(
                ContextCompat.getColor(this, R.color.colorAccent)));

        ((TextView) findViewById(R.id.gradle)).setText(String.format(
                getString(R.string.version_format),
                DynamicPackageUtils.getAppVersion(this)));

        fab.setOnClickListener(this);
        findViewById(R.id.dialog_message).setOnClickListener(this);
        findViewById(R.id.dialog_list).setOnClickListener(this);
        findViewById(R.id.dialog_single_choice).setOnClickListener(this);
        findViewById(R.id.dialog_multi_choice).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                DynamicLinkUtils.viewUrl(DynamicDialogsActivity.this, GITHUB_LINK);
                break;

            // Message dialog.
            case R.id.dialog_message:
                (new DynamicAlertDialog.Builder(this))
                        .setTitle(R.string.message_dialog)
                        .setMessage(R.string.message_dialog_content)
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                break;

            // List dialog.
            case R.id.dialog_list:
                (new DynamicAlertDialog.Builder(this))
                        .setTitle(R.string.list_dialog)
                        .setItems(R.array.dialog_array,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(DynamicDialogsActivity.this,
                                                String.format(getString(
                                                        R.string.list_click_format), i + 1),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                break;

            // Single choice dialog.
            case R.id.dialog_single_choice:
                (new DynamicAlertDialog.Builder(this))
                        .setTitle(R.string.single_choice_dialog)
                        .setSingleChoiceItems(R.array.dialog_array, 1,
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(DynamicDialogsActivity.this,
                                        String.format(getString(
                                                R.string.single_choice_click_format), i + 1),
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                break;

            // Multi choice dialog.
            case R.id.dialog_multi_choice:
                (new DynamicAlertDialog.Builder(this))
                        .setTitle(R.string.multi_choice_dialog)
                        .setMultiChoiceItems(R.array.dialog_array,
                                new boolean[]{true, false, true, false, true, false, true},
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface,
                                                        int i, boolean b) {
                                        Toast.makeText(DynamicDialogsActivity.this,
                                                String.format(getString(
                                                        R.string.multi_choice_click_format), i + 1,
                                                        getString(b ? R.string.checked
                                                                : R.string.unchecked)),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                break;
        }
    }
}
