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

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

import com.pranavpandey.android.dynamic.dialogs.DynamicDialog
import com.pranavpandey.android.dynamic.dialogs.fragment.DynamicDialogFragment

/**
 * Example of a custom [DynamicDialogFragment].
 */
class CustomDialogFragment : DynamicDialogFragment() {

    companion object {

        /**
         * @return A new instance of CustomDialogFragment.
         */
        fun newInstance(): CustomDialogFragment {
            return CustomDialogFragment()
        }
    }

    /**
     * Customise [DynamicDialog] by overriding this method.
     */
    override fun onCustomiseDialog(alertDialog: DynamicDialog,
                                   savedInstanceState: Bundle?): DynamicDialog {
        // Set on dismiss listener using dialog fragment method.
        onDismissListener = DialogInterface.OnDismissListener {
            val password = (alertDialog.window!!.findViewById<View>(
                    R.id.dialog_custom_simple_edit) as EditText).text.toString()

            Toast.makeText(context, String.format(getString(
                    R.string.format_password), password), Toast.LENGTH_SHORT).show()
        }

        return alertDialog
    }


    /**
     * Customise [DynamicDialog.Builder] by overriding this
     * method.
     */
    override fun onCustomiseBuilder(
            alertDialogBuilder: DynamicDialog.Builder,
            savedInstanceState: Bundle?): DynamicDialog.Builder {
        return alertDialogBuilder.setTitle(R.string.custom_simple_dialog_fragment)
                .setPositiveButton(android.R.string.ok, null)
                // Set a custom view.
                .setView(R.layout.dialog_custom_simple)
                // Set view root to automatically add scroll dividers.
                .setViewRoot(R.id.dialog_custom_simple_root)
    }
}
