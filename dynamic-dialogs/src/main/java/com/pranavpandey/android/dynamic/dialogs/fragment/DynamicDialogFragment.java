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

package com.pranavpandey.android.dynamic.dialogs.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.pranavpandey.android.dynamic.dialogs.DynamicDialog;

/**
 * Base dialog fragment to provide all the functionality of
 * {@link DynamicDialog} inside a fragment. It can be extended to
 * customise it further by overriding the supported methods.
 *
 * @see #onCustomiseBuilder(DynamicDialog.Builder)
 * @see #onCustomiseDialog(DynamicDialog)
 */
public class DynamicDialogFragment extends DialogFragment {

    /**
     * Default button color. it will be used internally if there is
     * no button color is applied.
     */
    public static final int DEFAULT_BUTTON_COLOR = -1;

    /**
     * Custom button color to be used by this dialog fragment.
     */
    private @ColorInt int mButtonColor = DEFAULT_BUTTON_COLOR;

    /**
     * {@code true} to make the dialog cancelable. The default value
     * is {@code true}.
     */
    private boolean mIsCancelable = true;

    /**
     * {@code true} to dismiss the dialog in pause state. The default
     * value is {@code false}.
     */
    private boolean mAutoDismiss = false;

    /**
     * Dialog builder to customise this fragment according to the need.
     */
    private DynamicDialog.Builder mDynamicDialogBuilder;

    /**
     * Initialize the new instance of this fragment.
     *
     * @return A instance of {@link DynamicDialogFragment}.
     */
    public static DynamicDialogFragment newInstance() {
        return new DynamicDialogFragment();
    }

    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();
        // handles https://code.google.com/p/android/issues/detail?id=17423
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mAutoDismiss) {
            dismiss();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mDynamicDialogBuilder == null) {
            mDynamicDialogBuilder = new DynamicDialog.Builder(getContext());
        }

        final DynamicDialog alertDialog = onCustomiseBuilder(
                mDynamicDialogBuilder).create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (mButtonColor != DEFAULT_BUTTON_COLOR) {
                    if (alertDialog.getButton(AlertDialog.BUTTON_POSITIVE) != null) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                .setTextColor(mButtonColor);
                    }

                    if (alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE) != null) {
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                                .setTextColor(mButtonColor);
                    }

                    if (alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL) != null) {
                        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
                                .setTextColor(mButtonColor);
                    }
                }
            }
        });

        return onCustomiseDialog(alertDialog);
    }

    /**
     * Override this method to customise the {@link #mDynamicDialogBuilder}
     * before creating the dialog.
     *
     * @param dialogBuilder The current builder to be customised.
     *
     * @return Customised {@link DynamicDialog.Builder}.
     */
    @NonNull
    protected DynamicDialog.Builder onCustomiseBuilder(
            @NonNull DynamicDialog.Builder dialogBuilder) {
        return dialogBuilder;
    }

    /**
     * Override this method to customise the {@link DynamicDialog}
     * before attaching it with this fragment.
     *
     * @param alertDialog The current dialog to be customised.
     *
     * @return Customised {@link DynamicDialog}.
     */
    @NonNull
    protected DynamicDialog onCustomiseDialog(
            @NonNull DynamicDialog alertDialog) {
        return alertDialog;
    }

    /**
     * Setter for {@link #mIsCancelable}.
     *
     * @return {@link DynamicDialogFragment} object to allow for chaining of
     *         calls to set methods.
     */
    public DynamicDialogFragment setIsCancelable(boolean isCancelable) {
        this.mIsCancelable = isCancelable;
        setCancelable(isCancelable);

        return this;
    }

    /**
     * Setter for {@link #mButtonColor}.
     *
     * @return {@link DynamicDialogFragment} object to allow for chaining of
     *         calls to set methods.
     */
    public DynamicDialogFragment setButtonColor(@ColorInt int buttonColor) {
        this.mButtonColor = buttonColor;

        return this;
    }

    /**
     * Getter for {@link #mIsCancelable}.
     */
    public boolean isCancelable() {
        return mIsCancelable;
    }

    /**
     * Getter for {@link #mAutoDismiss}.
     */
    public boolean isAutoDismiss() {
        return mAutoDismiss;
    }

    /**
     * Setter for {@link #mAutoDismiss}.
     *
     * @return {@link DynamicDialogFragment} object to allow for chaining of
     *         calls to set methods.
     */
    public DynamicDialogFragment setAutoDismiss(boolean autoDismiss) {
        this.mAutoDismiss = autoDismiss;

        return this;
    }

    /**
     * Getter for {@link #mDynamicDialogBuilder}.
     */
    public DynamicDialog.Builder getBuilder() {
        return mDynamicDialogBuilder;
    }

    /**
     * Setter for {@link #mDynamicDialogBuilder}.
     *
     * @return {@link DynamicDialogFragment} object to allow for chaining of
     *         calls to set methods.
     */
    public DynamicDialogFragment setBuilder(
            @NonNull DynamicDialog.Builder dynamicAlertDialogBuilder) {
        this.mDynamicDialogBuilder = dynamicAlertDialogBuilder;

        return this;
    }

    /**
     * Show this dialog fragment and attach it to the supplied activity.
     */
    public void showDialog(@NonNull FragmentActivity fragmentActivity) {
        show(fragmentActivity.getSupportFragmentManager(),
                DynamicDialogFragment.class.getName());
    }
}

