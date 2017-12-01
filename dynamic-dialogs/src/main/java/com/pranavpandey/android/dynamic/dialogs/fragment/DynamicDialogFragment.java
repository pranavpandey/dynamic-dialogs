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
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

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
     * Callback when this dialog fragment is displayed.
     */
    private DynamicDialog.OnShowListener mOnShowListener;

    /**
     * Callback when this dialog fragment has been dismissed.
     */
    private DynamicDialog.OnDismissListener mOnDismissListener;

    /**
     * Callback when this dialog fragment has been cancelled.
     */
    private DynamicDialog.OnCancelListener mOnCancelListener;

    /**
     * Callback when a key is pressed in this dialog fragment.
     */
    private DynamicDialog.OnKeyListener mOnKeyListener;

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
                onCustomiseBuilder(mDynamicDialogBuilder), savedInstanceState).create();

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

                if (mOnShowListener != null) {
                    mOnShowListener.onShow(getDialog());
                }
            }
        });

        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface,
                                 int i, KeyEvent keyEvent) {
                if (mOnKeyListener != null) {
                    mOnKeyListener.onKey(dialogInterface, i, keyEvent);
                }

                return false;
            }
        });

        return onCustomiseDialog(onCustomiseDialog(alertDialog), savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mAutoDismiss) {
            dismiss();
        }
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);

        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onCancel(final DialogInterface dialog) {
        super.onCancel(dialog);

        if (mOnCancelListener != null) {
            mOnCancelListener.onCancel(dialog);
        }
    }

    /**
     * Override this method to customise the {@link #mDynamicDialogBuilder}
     * before creating the dialog.
     *
     * @param dialogBuilder The current builder to be customised.
     * @param savedInstanceState The saved state of the fragment to restore it
     *                           later.
     *
     * @return Customised {@link DynamicDialog.Builder}.
     */
    protected @NonNull DynamicDialog.Builder onCustomiseBuilder(
            @NonNull DynamicDialog.Builder dialogBuilder, @Nullable Bundle savedInstanceState) {
        return dialogBuilder;
    }

    /**
     * Override this method to customise the {@link #mDynamicDialogBuilder}
     * before creating the dialog.
     *
     * @param dialogBuilder The current builder to be customised.
     *
     * @return Customised {@link DynamicDialog.Builder}.
     *
     * @deprecated Use {@link #onCustomiseBuilder(DynamicDialog.Builder, Bundle)}
     */
    @Deprecated
    protected @NonNull DynamicDialog.Builder onCustomiseBuilder(
            @NonNull DynamicDialog.Builder dialogBuilder) {
        return dialogBuilder;
    }

    /**
     * Override this method to customise the {@link DynamicDialog}
     * before attaching it with this fragment.
     *
     * @param alertDialog The current dialog to be customised.
     * @param savedInstanceState The saved state of the fragment to restore it
     *                           later.
     *
     * @return Customised {@link DynamicDialog}.
     */
    protected @NonNull DynamicDialog onCustomiseDialog(@NonNull DynamicDialog alertDialog,
                                                       @Nullable Bundle savedInstanceState) {
        return alertDialog;
    }

    /**
     * Override this method to customise the {@link DynamicDialog}
     * before attaching it with this fragment.
     *
     * @param alertDialog The current dialog to be customised.
     *
     * @return Customised {@link DynamicDialog}.
     *
     * @deprecated Use {@link #onCustomiseDialog(DynamicDialog, Bundle)}
     */
    @Deprecated
    protected @NonNull DynamicDialog onCustomiseDialog(@NonNull DynamicDialog alertDialog) {
        return alertDialog;
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
     * Getter for {@link #mAutoDismiss}.
     */
    protected boolean isAutoDismiss() {
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
    protected DynamicDialog.Builder getBuilder() {
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
     * Getter for {@link #mOnShowListener}.
     */
    protected DynamicDialog.OnShowListener getOnShowListener() {
        return mOnShowListener;
    }

    /**
     * Setter for {@link #mOnShowListener}.
     *
     * @return {@link DynamicDialogFragment} object to allow for chaining of
     *         calls to set methods.
     */
    public DynamicDialogFragment setOnShowListener(
            @Nullable DynamicDialog.OnShowListener onShowListener) {
        this.mOnShowListener = onShowListener;

        return this;
    }

    /**
     * Getter for {@link #mOnDismissListener}.
     */
    protected DynamicDialog.OnDismissListener getOnDismissListener() {
        return mOnDismissListener;
    }

    /**
     * Setter for {@link #mOnDismissListener}.
     *
     * @return {@link DynamicDialogFragment} object to allow for chaining of
     *         calls to set methods.
     */
    public DynamicDialogFragment setOnDismissListener(
            @Nullable DynamicDialog.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;

        return this;
    }

    /**
     * Getter for {@link #mOnCancelListener}.
     */
    protected DynamicDialog.OnCancelListener getOnCancelListener() {
        return mOnCancelListener;
    }

    /**
     * Setter for {@link #mOnCancelListener}.
     *
     * @return {@link DynamicDialogFragment} object to allow for chaining of
     *         calls to set methods.
     */
    public DynamicDialogFragment setOnCancelListener(
            @Nullable DynamicDialog.OnCancelListener onCancelListener) {
        this.mOnCancelListener = onCancelListener;

        return this;
    }

    /**
     * Getter for {@link #mOnKeyListener}.
     */
    protected DynamicDialog.OnKeyListener getOnKeyListener() {
        return mOnKeyListener;
    }

    /**
     * Setter for {@link #mOnKeyListener}.
     *
     * @return {@link DynamicDialogFragment} object to allow for chaining of
     *         calls to set methods.
     */
    public DynamicDialogFragment setOnKeyListener(
            @Nullable DynamicDialog.OnKeyListener onKeyListener) {
        this.mOnKeyListener = onKeyListener;

        return this;
    }

    /**
     * Show this dialog fragment and attach it to the supplied activity.
     */
    public void showDialog(@NonNull FragmentActivity fragmentActivity) {
        show(fragmentActivity.getSupportFragmentManager(),
                DynamicDialogFragment.class.getName());
    }

    /**
     * @return {@link DynamicDialog} created by this fragment.
     */
    public DynamicDialog getDynamicDialog() {
        return (DynamicDialog) getDialog();
    }
}

