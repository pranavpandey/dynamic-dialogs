/*
 * Copyright 2017-2022 Pranav Pandey
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

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.pranavpandey.android.dynamic.dialogs.DynamicDialog;
import com.pranavpandey.android.dynamic.util.DynamicSdkUtils;
import com.pranavpandey.android.dynamic.util.product.DynamicFlavor;
import com.pranavpandey.android.dynamic.util.product.DynamicProductFlavor;

/**
 * Base dialog fragment to provide all the functionality of {@link DynamicDialog} inside a
 * fragment. It can be extended to customise it further by overriding the supported methods.
 *
 * @see #onCustomiseBuilder(DynamicDialog.Builder, Bundle)
 * @see #onCustomiseDialog(DynamicDialog, View, Bundle)
 */
public class DynamicDialogFragment extends AppCompatDialogFragment
        implements DynamicProductFlavor, SharedPreferences.OnSharedPreferenceChangeListener {

    /**
     * Default button color. it will be used internally if there is no button color is applied.
     */
    public static final int ADS_DEFAULT_BUTTON_COLOR = -1;

    /**
     * Button color used by the dialog.
     */
    private @ColorInt int mButtonColor = ADS_DEFAULT_BUTTON_COLOR;

    /**
     * Positive button text used by the dialog.
     */
    private CharSequence mPositiveButtonText;

    /**
     * Negative button text used by the dialog.
     */
    private CharSequence mNegativeButtonText;

    /**
     * Neutral button text used by the dialog.
     */
    private CharSequence mNeutralButtonText;

    /**
     * {@code true} to make the dialog cancelable.
     * <p>The default value is {@code true}.
     */
    private boolean mIsCancelable = true;

    /**
     * {@code true} to dismiss the dialog in pause state.
     * <p>The default value is {@code false}.
     */
    private boolean mAutoDismiss = false;

    /**
     * Dialog builder to customise this fragment according to the requirements.
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
     * Callback when this dialog fragment has been canceled.
     */
    private DynamicDialog.OnCancelListener mOnCancelListener;

    /**
     * Callback when a key is pressed inside the dialog.
     */
    private DynamicDialog.OnKeyListener mOnKeyListener;

    /**
     * Initialize the new instance of this fragment.
     *
     * @return An instance of {@link DynamicDialogFragment}.
     */
    public static @NonNull DynamicDialogFragment newInstance() {
        return new DynamicDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public @NonNull Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mDynamicDialogBuilder = new DynamicDialog.Builder(
                requireContext(), mDynamicDialogBuilder);
        final DynamicDialog alertDialog = onCustomiseBuilder(
                mDynamicDialogBuilder, savedInstanceState).create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (mButtonColor != ADS_DEFAULT_BUTTON_COLOR) {
                    if (alertDialog.getButton(AlertDialog.BUTTON_POSITIVE) != null) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                .setTextColor(mButtonColor);

                        if (mPositiveButtonText != null) {
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setText(mPositiveButtonText);
                        }
                    }

                    if (alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE) != null) {
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                                .setTextColor(mButtonColor);

                        if (mPositiveButtonText != null) {
                            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                                    .setText(mNegativeButtonText);
                        }
                    }

                    if (alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL) != null) {
                        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
                                .setTextColor(mButtonColor);

                        if (mPositiveButtonText != null) {
                            alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
                                    .setText(mNeutralButtonText);
                        }
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

        onCustomiseDialog(alertDialog, alertDialog.getView(), savedInstanceState);
        return onCustomiseDialog(alertDialog, savedInstanceState);
    }

    /**
     * Override this method to customise the dynamic dialog builder before creating the dialog.
     *
     * @param dialogBuilder The current builder to be customised.
     * @param savedInstanceState The saved state of the fragment to restore it later.
     *
     * @return The customised dynamic dialog builder.
     */
    protected @NonNull DynamicDialog.Builder onCustomiseBuilder(
            @NonNull DynamicDialog.Builder dialogBuilder, @Nullable Bundle savedInstanceState) {
        return dialogBuilder;
    }

    /**
     * Override this method to customise the dynamic dialog before attaching it with
     * this fragment.
     *
     * @param alertDialog The current dialog to be customised.
     * @param view The view used by the dialog.
     * @param savedInstanceState The saved state of the fragment to restore it later.
     */
    protected void onCustomiseDialog(@NonNull DynamicDialog alertDialog,
            @Nullable View view, @Nullable Bundle savedInstanceState) {
    }

    /**
     * Override this method to customise the dynamic dialog before attaching it with
     * this fragment.
     *
     * @param alertDialog The current dialog to be customised.
     * @param savedInstanceState The saved state of the fragment to restore it later.
     *
     * @return The customised dynamic dialog.
     *
     * @deprecated Use {@link #onCustomiseDialog(DynamicDialog, View, Bundle)} for better
     *             customization.
     */
    protected @NonNull DynamicDialog onCustomiseDialog(@NonNull DynamicDialog alertDialog,
            @Nullable Bundle savedInstanceState) {
        return alertDialog;
    }

    @Override
    public @DynamicFlavor String getProductFlavor() {
        return DynamicFlavor.DEFAULT;
    }

    /**
     * Returns whether to register a shared preferences listener for this fragment.
     *
     * @return {@code true} to register a {@link SharedPreferences.OnSharedPreferenceChangeListener}
     *         to receive preference change callback.
     */
    public boolean isOnSharedPreferenceChangeListener() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isOnSharedPreferenceChangeListener() && getContext() != null) {
            PreferenceManager.getDefaultSharedPreferences(requireContext())
                    .registerOnSharedPreferenceChangeListener(this);
        }
    }

    @Override
    public void onPause() {
        if (isOnSharedPreferenceChangeListener() && getContext() != null) {
            PreferenceManager.getDefaultSharedPreferences(requireContext())
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        if (mAutoDismiss) {
            dismiss();
        }
        super.onPause();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);

        if (mOnCancelListener != null) {
            mOnCancelListener.onCancel(dialog);
        }
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    /**
     * Get the button color set for the dialog.
     *
     * @return The button color set for the dialog.
     */
    public @ColorInt int getButtonColor() {
        return mButtonColor;
    }

    /**
     * Set the button color for the dialog.
     *
     * @param buttonColor The button color to be set.
     *
     * @return The {@link DynamicDialogFragment} object to allow for chaining of calls to
     *         set methods.
     */
    public @NonNull DynamicDialogFragment setButtonColor(@ColorInt int buttonColor) {
        this.mButtonColor = buttonColor;

        return this;
    }

    /**
     * Get the positive button text set for the dialog.
     *
     * @return The positive button text set for the dialog.
     */
    public @Nullable CharSequence getPositiveButtonText() {
        return mPositiveButtonText;
    }

    /**
     * Set the positive button text set for the dialog.
     *
     * @param text The positive button text to be set.
     *
     * @return The {@link DynamicDialogFragment} object to allow for chaining of calls to
     *         set methods.
     */
    public @NonNull DynamicDialogFragment setPositiveButtonText(@Nullable CharSequence text) {
        this.mPositiveButtonText = text;

        return this;
    }

    /**
     * Get the negative button text set for the dialog.
     *
     * @return The negative button text set for the dialog.
     */
    public @Nullable CharSequence getNegativeButtonText() {
        return mNegativeButtonText;
    }

    /**
     * Set the negative button text set for the dialog.
     *
     * @param text The negative button text to be set.
     *
     * @return The {@link DynamicDialogFragment} object to allow for chaining of calls to
     *         set methods.
     */
    public @NonNull DynamicDialogFragment setNegativeButtonText(@Nullable CharSequence text) {
        this.mNegativeButtonText = text;

        return this;
    }

    /**
     * Get the neutral button text set for the dialog.
     *
     * @return The neutral button text set for the dialog.
     */
    public @Nullable CharSequence getNeutralButtonText() {
        return mNeutralButtonText;
    }

    /**
     * Set the neutral button text set for the dialog.
     *
     * @param text The neutral button text to be set.
     *
     * @return The {@link DynamicDialogFragment} object to allow for chaining of calls to
     *         set methods.
     */
    public @NonNull DynamicDialogFragment setNeutralButtonText(@Nullable CharSequence text) {
        this.mNeutralButtonText = text;

        return this;
    }

    /**
     * Returns whether this dialog is cancelable.
     *
     * @return {@code true} to make the dialog cancelable.
     *         <p>The default value is {@code true}.
     */
    public boolean isCancelable() {
        return mIsCancelable;
    }

    /**
     * Control whether the dialog is cancelable.
     *
     * @param cancelable {@code true} to make the dialog cancelable.
     *
     * @return The {@link DynamicDialogFragment} object to allow for chaining of calls to
     *         set methods.
     */
    public @NonNull DynamicDialogFragment setIsCancelable(boolean cancelable) {
        this.mIsCancelable = cancelable;
        setCancelable(cancelable);

        return this;
    }

    /**
     * Returns whether the dialog is auto dismissible.
     *
     * @return {@code true} to dismiss the dialog in pause state.
     *         <p>The default value is {@code false}.
     */
    protected boolean isAutoDismiss() {
        return mAutoDismiss;
    }

    /**
     * Control whether the dialog is auto dismissible.
     *
     * @param autoDismiss {@code true} to dismiss the dialog in pause state.
     *
     * @return The {@link DynamicDialogFragment} object to allow for chaining of calls to
     *         set methods.
     */
    public @NonNull DynamicDialogFragment setAutoDismiss(boolean autoDismiss) {
        this.mAutoDismiss = autoDismiss;

        return this;
    }

    /**
     * The dynamic dialog builder set for this fragment.
     *
     * @return The dialog builder to customise this fragment according to the requirements.
     */
    protected @Nullable DynamicDialog.Builder getBuilder() {
        return mDynamicDialogBuilder;
    }

    /**
     * Sets a dynamic dialog builder for this dialog fragment.
     *
     * @param dynamicAlertDialogBuilder The dynamic dialog builder to be set.
     *
     * @return The {@link DynamicDialogFragment} object to allow for chaining of calls to
     *         set methods.
     */
    public @NonNull DynamicDialogFragment setBuilder(
            @NonNull DynamicDialog.Builder dynamicAlertDialogBuilder) {
        this.mDynamicDialogBuilder = dynamicAlertDialogBuilder;

        return this;
    }

    /**
     * Get the on show listener set for the dialog.
     *
     * @return The callback when this dialog fragment is displayed.
     */
    protected @Nullable DynamicDialog.OnShowListener getOnShowListener() {
        return mOnShowListener;
    }

    /**
     * Set an show listener for the dialog.
     *
     * @param onShowListener The on show listener to be set.
     *
     * @return The {@link DynamicDialogFragment} object to allow for chaining of calls to
     *         set methods.
     */
    public @NonNull DynamicDialogFragment setOnShowListener(
            @Nullable DynamicDialog.OnShowListener onShowListener) {
        this.mOnShowListener = onShowListener;

        return this;
    }

    /**
     * Get the on dismiss listener set for the dialog.
     *
     * @return The callback when this dialog fragment has been dismissed.
     */
    protected @Nullable DynamicDialog.OnDismissListener getOnDismissListener() {
        return mOnDismissListener;
    }

    /**
     * Set an on dismiss listener for the dialog.
     *
     * @param onDismissListener The on dismiss listener to be set.
     *
     * @return The {@link DynamicDialogFragment} object to allow for chaining of calls to
     *         set methods.
     */
    public @NonNull DynamicDialogFragment setOnDismissListener(
            @Nullable DynamicDialog.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;

        return this;
    }

    /**
     * Get the on cancel listener set for the dialog.
     *
     * @return The callback when this dialog fragment has been cancelled.
     */
    protected @Nullable DynamicDialog.OnCancelListener getOnCancelListener() {
        return mOnCancelListener;
    }

    /**
     * Set an on cancel listener for the dialog.
     *
     * @param onCancelListener The on cancel listener to be set.
     *
     * @return The {@link DynamicDialogFragment} object to allow for chaining of calls to
     *         set methods.
     */
    public @NonNull DynamicDialogFragment setOnCancelListener(
            @Nullable DynamicDialog.OnCancelListener onCancelListener) {
        this.mOnCancelListener = onCancelListener;

        return this;
    }

    /**
     * Get the on key listener set for the dialog.
     *
     * @return The callback when a key is pressed in this dialog fragment.
     */
    protected @Nullable DynamicDialog.OnKeyListener getOnKeyListener() {
        return mOnKeyListener;
    }

    /**
     * Set an on key listener for the dialog.
     *
     * @param onKeyListener The on key listener to be set.
     *
     * @return The {@link DynamicDialogFragment} object to allow for chaining of calls to
     *         set methods.
     */
    public @NonNull DynamicDialogFragment setOnKeyListener(
            @Nullable DynamicDialog.OnKeyListener onKeyListener) {
        this.mOnKeyListener = onKeyListener;

        return this;
    }

    /**
     * Show this dialog fragment and attach it to the supplied activity.
     *
     * @param fragmentActivity The fragment activity to attach this dialog fragment.
     * @param tag The tag for this fragment.
     */
    public void showDialog(@NonNull FragmentActivity fragmentActivity, @Nullable String tag) {
        if (fragmentActivity.getSupportFragmentManager().isDestroyed()) {
            return;
        }

        if (fragmentActivity.getSupportFragmentManager().findFragmentByTag(tag)
                instanceof AppCompatDialogFragment) {
            try {
                final AppCompatDialogFragment fragment;
                if ((fragment = (AppCompatDialogFragment) fragmentActivity
                        .getSupportFragmentManager().findFragmentByTag(tag)) != null) {
                    fragment.dismiss();
                }
            } catch (Exception ignored) {
            }
        }

        show(fragmentActivity.getSupportFragmentManager(), tag);
    }

    /**
     * Show this dialog fragment and attach it to the supplied activity.
     *
     * @param fragmentActivity The fragment activity to attach this dialog fragment.
     */
    public void showDialog(@NonNull FragmentActivity fragmentActivity) {
        showDialog(fragmentActivity, getClass().getName());
    }

    /**
     * Returns the dialog shown by this fragment.
     *
     * @return The {@link DynamicDialog} shown by this fragment.
     */
    public @Nullable DynamicDialog getDynamicDialog() {
        return (DynamicDialog) getDialog();
    }

    /**
     * Finish the parent activity by calling {@link Activity#finish()}.
     *
     * @see FragmentActivity#supportFinishAfterTransition()
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void finishActivity() {
        if (getActivity() != null && !requireActivity().isFinishing()) {
            if (DynamicSdkUtils.is21()
                    && (requireActivity().getWindow().getSharedElementEnterTransition() != null
                    || requireActivity().getWindow().getSharedElementReturnTransition() != null)) {
                requireActivity().supportFinishAfterTransition();
            } else {
                requireActivity().finish();
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) { }
}
