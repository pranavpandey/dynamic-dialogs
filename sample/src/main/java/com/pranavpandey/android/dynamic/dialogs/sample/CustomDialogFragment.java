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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.Toast;

import com.pranavpandey.android.dynamic.dialogs.DynamicDialog;
import com.pranavpandey.android.dynamic.dialogs.fragment.DynamicDialogFragment;

/**
 * Example of a custom {@link DynamicDialogFragment}.
 */
public class CustomDialogFragment extends DynamicDialogFragment {

    public static CustomDialogFragment newInstance() {
        return new CustomDialogFragment();
    }

    @Override
    protected @NonNull DynamicDialog onCustomiseDialog(@NonNull final DynamicDialog alertDialog,
                                                       @Nullable Bundle savedInstanceState) {
         // Set on dismiss listener using dialog fragment method.
        setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                String password = ((EditText) alertDialog.getWindow().findViewById(
                        R.id.dialog_custom_simple_edit)).getText().toString();

                Toast.makeText(getContext(), String.format(getString(
                        R.string.format_password), password), Toast.LENGTH_SHORT).show();
            }
        });

        return alertDialog;
    }


    @Override
    protected @NonNull DynamicDialog.Builder onCustomiseBuilder(
            @NonNull DynamicDialog.Builder alertDialogBuilder,
            @Nullable Bundle savedInstanceState) {
        return alertDialogBuilder.setTitle(R.string.custom_simple_dialog_fragment)
                .setPositiveButton(android.R.string.ok, null)
                // Set a custom view.
                .setView(R.layout.dialog_custom_simple)
                // Set view root to automatically add scroll dividers.
                .setViewRoot(R.id.dialog_custom_simple_root);
    }
}
