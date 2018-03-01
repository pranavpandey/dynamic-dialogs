/*
 * Copyright 2017 Pranav Pandey
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

package com.pranavpandey.android.dynamic.dialogs.sample

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.pranavpandey.android.dynamic.dialogs.DynamicDialog
import com.pranavpandey.android.dynamic.dialogs.fragment.DynamicDialogFragment
import com.pranavpandey.android.dynamic.utils.DynamicColorUtils
import com.pranavpandey.android.dynamic.utils.DynamicLinkUtils
import com.pranavpandey.android.dynamic.utils.DynamicPackageUtils

/**
 * Main activity to show the implementation of [DynamicDialog].
 */
class DynamicDialogsActivity : AppCompatActivity(), View.OnClickListener {

    companion object {

        /**
         * Open source repository url.
         */
        const val URL_GITHUB = "https://github.com/pranavpandey/dynamic-dialogs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dynamic_dialogs)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setSubtitle(R.string.app_name_sample)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setColorFilter(DynamicColorUtils.getTintColor(
                ContextCompat.getColor(this, R.color.color_accent)))

        (findViewById<View>(R.id.gradle) as TextView).text = String.format(
                getString(R.string.format_version),
                DynamicPackageUtils.getAppVersion(this))

        fab.setOnClickListener(this)
        findViewById<View>(R.id.dialog_message).setOnClickListener(this)
        findViewById<View>(R.id.dialog_list).setOnClickListener(this)
        findViewById<View>(R.id.dialog_single_choice).setOnClickListener(this)
        findViewById<View>(R.id.dialog_multi_choice).setOnClickListener(this)
        findViewById<View>(R.id.dialog_custom_simple).setOnClickListener(this)
        findViewById<View>(R.id.dialog_custom_theme).setOnClickListener(this)
        findViewById<View>(R.id.fragment_message).setOnClickListener(this)
        findViewById<View>(R.id.fragment_list).setOnClickListener(this)
        findViewById<View>(R.id.fragment_single_choice).setOnClickListener(this)
        findViewById<View>(R.id.fragment_multi_choice).setOnClickListener(this)
        findViewById<View>(R.id.fragment_custom_simple).setOnClickListener(this)
        findViewById<View>(R.id.fragment_custom_theme).setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            AboutDialogFragment.newInstance().showDialog(this)

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> DynamicLinkUtils.viewUrl(this@DynamicDialogsActivity, URL_GITHUB)

            // Message dialog.
            R.id.dialog_message -> DynamicDialog.Builder(this)
                    .setTitle(R.string.message_dialog)
                    .setMessage(R.string.message_dialog_content)
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()

            // List dialog.
            R.id.dialog_list -> DynamicDialog.Builder(this)
                    .setTitle(R.string.list_dialog)
                    .setItems(R.array.dialog_array
                    ) { _, i ->
                        Toast.makeText(this@DynamicDialogsActivity,
                                String.format(getString(
                                        R.string.list_click_format), i + 1),
                                Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()

            // Single choice dialog.
            R.id.dialog_single_choice -> DynamicDialog.Builder(this)
                    .setTitle(R.string.single_choice_dialog)
                    .setSingleChoiceItems(R.array.dialog_array, 1
                    ) { _, i ->
                        Toast.makeText(this@DynamicDialogsActivity,
                                String.format(getString(
                                        R.string.single_choice_click_format), i + 1),
                                Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()

            // Multi choice dialog.
            R.id.dialog_multi_choice -> DynamicDialog.Builder(this)
                    .setTitle(R.string.multi_choice_dialog)
                    .setMultiChoiceItems(R.array.dialog_array,
                            booleanArrayOf(true, false, true, false, true, false, true)
                    ) { _, i, b ->
                        Toast.makeText(this@DynamicDialogsActivity,
                                String.format(getString(
                                        R.string.multi_choice_click_format), i + 1,
                                        getString(if (b)
                                            R.string.checked
                                        else
                                            R.string.unchecked)),
                                Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()

            // Custom simple dialog.
            R.id.dialog_custom_simple -> {
                val dialog = DynamicDialog.Builder(this)
                        .setTitle(R.string.custom_simple_dialog)
                        .setPositiveButton(android.R.string.ok, null)
                        // Set a custom view.
                        .setView(R.layout.dialog_custom_simple)
                        // Set view root to automatically add scroll dividers.
                        .setViewRoot(R.id.dialog_custom_simple_root)
                        .create()

                dialog.setOnDismissListener {
                    val password = (dialog.window!!.findViewById<View>(
                            R.id.dialog_custom_simple_edit) as EditText).text.toString()

                    Toast.makeText(this@DynamicDialogsActivity, String.format(getString(
                            R.string.format_password), password), Toast.LENGTH_SHORT).show()
                }

                dialog.show()
            }

            // Custom theme dialog.
            R.id.dialog_custom_theme -> DynamicDialog.Builder(
                    this, R.style.CustomDialogStyle)
                    .setTitle(R.string.custom_theme_dialog)
                    .setMessage(R.string.custom_theme_dialog_content)
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()

            // Message dialog fragment.
            R.id.fragment_message -> DynamicDialogFragment.newInstance().setBuilder(
                    DynamicDialog.Builder(this)
                            .setTitle(R.string.message_dialog_fragment)
                            .setMessage(R.string.message_dialog_fragment_content)
                            .setNegativeButton(android.R.string.cancel, null))
                    .showDialog(this)

            // List dialog fragment.
            R.id.fragment_list -> DynamicDialogFragment.newInstance().setBuilder(
                    DynamicDialog.Builder(this)
                            .setTitle(R.string.list_dialog_fragment)
                            .setItems(R.array.dialog_array
                            ) { _, i ->
                                Toast.makeText(this@DynamicDialogsActivity,
                                        String.format(getString(
                                                R.string.list_click_format), i + 1),
                                        Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton(android.R.string.cancel, null))
                    .showDialog(this)

            // Single choice dialog fragment.
            R.id.fragment_single_choice -> DynamicDialogFragment.newInstance().setBuilder(
                    DynamicDialog.Builder(this)
                            .setTitle(R.string.single_choice_dialog_fragment)
                            .setSingleChoiceItems(R.array.dialog_array, 1
                            ) { _, i ->
                                Toast.makeText(this@DynamicDialogsActivity,
                                        String.format(getString(
                                                R.string.single_choice_click_format), i + 1),
                                        Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton(android.R.string.cancel, null))
                    .showDialog(this)

            // Multi choice dialog fragment.
            R.id.fragment_multi_choice -> DynamicDialogFragment.newInstance().setBuilder(
                    DynamicDialog.Builder(this)
                            .setTitle(R.string.multi_choice_dialog_fragment)
                            .setMultiChoiceItems(R.array.dialog_array,
                                    booleanArrayOf(true, false, true, false, true, false, true)
                            ) { _, i, b ->
                                Toast.makeText(this@DynamicDialogsActivity,
                                        String.format(getString(
                                                R.string.multi_choice_click_format), i + 1,
                                                getString(if (b)
                                                    R.string.checked
                                                else
                                                    R.string.unchecked)),
                                        Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton(android.R.string.cancel, null))
                    .showDialog(this)

            // Custom simple dialog fragment.
            R.id.fragment_custom_simple -> CustomDialogFragment
                    .newInstance().showDialog(this)

            // Custom theme dialog fragment.
            R.id.fragment_custom_theme -> DynamicDialogFragment.newInstance().setBuilder(
                    DynamicDialog.Builder(this, R.style.CustomDialogStyle)
                            .setTitle(R.string.custom_theme_dialog_fragment)
                            .setMessage(R.string.custom_theme_dialog_content)
                            .setNegativeButton(android.R.string.cancel, null))
                    .showDialog(this)
        }
    }
}
