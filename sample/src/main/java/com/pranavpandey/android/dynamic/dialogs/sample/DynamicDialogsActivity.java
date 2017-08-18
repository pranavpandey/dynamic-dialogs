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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pranavpandey.android.dynamic.dialogs.DynamicDialog;
import com.pranavpandey.android.dynamic.dialogs.fragment.DynamicDialogFragment;
import com.pranavpandey.android.dynamic.utils.DynamicColorUtils;
import com.pranavpandey.android.dynamic.utils.DynamicLinkUtils;
import com.pranavpandey.android.dynamic.utils.DynamicPackageUtils;

/**
 * Main activity to show the implementation of {@link DynamicDialog}.
 */
public class DynamicDialogsActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Open source repository link.
     */
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
                getString(R.string.format_version),
                DynamicPackageUtils.getAppVersion(this)));

        fab.setOnClickListener(this);
        findViewById(R.id.dialog_message).setOnClickListener(this);
        findViewById(R.id.dialog_list).setOnClickListener(this);
        findViewById(R.id.dialog_single_choice).setOnClickListener(this);
        findViewById(R.id.dialog_multi_choice).setOnClickListener(this);
        findViewById(R.id.dialog_custom_simple).setOnClickListener(this);
        findViewById(R.id.dialog_custom_theme).setOnClickListener(this);
        findViewById(R.id.fragment_message).setOnClickListener(this);
        findViewById(R.id.fragment_list).setOnClickListener(this);
        findViewById(R.id.fragment_single_choice).setOnClickListener(this);
        findViewById(R.id.fragment_multi_choice).setOnClickListener(this);
        findViewById(R.id.fragment_custom_simple).setOnClickListener(this);
        findViewById(R.id.fragment_custom_theme).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            AboutDialogFragment.newInstance().showDialog(this);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                DynamicLinkUtils.viewUrl(DynamicDialogsActivity.this, GITHUB_LINK);
                break;

            // Message dialog.
            case R.id.dialog_message:
                new DynamicDialog.Builder(this)
                        .setTitle(R.string.message_dialog)
                        .setMessage(R.string.message_dialog_content)
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                break;

            // List dialog.
            case R.id.dialog_list:
                new DynamicDialog.Builder(this)
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
                new DynamicDialog.Builder(this)
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
                new DynamicDialog.Builder(this)
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

            // Custom simple dialog.
            case R.id.dialog_custom_simple:
                final DynamicDialog dialog = new DynamicDialog.Builder(this)
                        .setTitle(R.string.custom_simple_dialog)
                        .setPositiveButton(android.R.string.ok, null)
                        // Set a custom view.
                        .setView(R.layout.dialog_custom_simple)
                        // Set view root to automatically add scroll dividers.
                        .setViewRoot(R.id.dialog_custom_simple_root)
                        .create();

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        String password = ((EditText) dialog.getWindow().findViewById(
                                R.id.dialog_custom_simple_edit)).getText().toString();

                        Toast.makeText(DynamicDialogsActivity.this, String.format(getString(
                                R.string.format_password), password), Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
                break;

            // Custom theme dialog.
            case R.id.dialog_custom_theme:
                new DynamicDialog.Builder(this, R.style.CustomDialogStyle)
                        .setTitle(R.string.custom_theme_dialog)
                        .setMessage(R.string.custom_theme_dialog_content)
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                break;

            // Message dialog fragment.
            case R.id.fragment_message:
                DynamicDialogFragment.newInstance().setBuilder(
                        new DynamicDialog.Builder(this)
                        .setTitle(R.string.message_dialog_fragment)
                        .setMessage(R.string.message_dialog_fragment_content)
                        .setNegativeButton(android.R.string.cancel, null))
                        .showDialog(this);
                break;

            // List dialog fragment.
            case R.id.fragment_list:
                DynamicDialogFragment.newInstance().setBuilder(
                        new DynamicDialog.Builder(this)
                        .setTitle(R.string.list_dialog_fragment)
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
                        .setNegativeButton(android.R.string.cancel, null))
                        .showDialog(this);
                break;

            // Single choice dialog fragment.
            case R.id.fragment_single_choice:
                DynamicDialogFragment.newInstance().setBuilder(
                        new DynamicDialog.Builder(this)
                        .setTitle(R.string.single_choice_dialog_fragment)
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
                        .setNegativeButton(android.R.string.cancel, null))
                        .showDialog(this);
                break;

            // Multi choice dialog fragment.
            case R.id.fragment_multi_choice:
                DynamicDialogFragment.newInstance().setBuilder(
                        new DynamicDialog.Builder(this)
                        .setTitle(R.string.multi_choice_dialog_fragment)
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
                        .setNegativeButton(android.R.string.cancel, null))
                        .showDialog(this);
                break;

            // Custom simple dialog fragment.
            case R.id.fragment_custom_simple:
                CustomDialogFragment.newInstance().showDialog(this);
                break;

            // Custom theme dialog fragment.
            case R.id.fragment_custom_theme:
                DynamicDialogFragment.newInstance().setBuilder(
                        new DynamicDialog.Builder(this, R.style.CustomDialogStyle)
                        .setTitle(R.string.custom_theme_dialog_fragment)
                        .setMessage(R.string.custom_theme_dialog_content)
                        .setNegativeButton(android.R.string.cancel, null))
                        .showDialog(this);
                break;
        }
    }
}
