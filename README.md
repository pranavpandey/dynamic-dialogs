<img src="./graphics/icon.png" width="160" height="160" align="right" hspace="20">

# Dynamic Dialogs

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/pranavpandey/dynamic-dialogs.svg?branch=master)](https://travis-ci.org/pranavpandey/dynamic-dialogs)
[![Release](https://img.shields.io/maven-central/v/com.pranavpandey.android/dynamic-dialogs)](https://search.maven.org/artifact/com.pranavpandey.android/dynamic-dialogs)

A simple library to display dialogs and dialog fragments on Android 4.0 (API 14) and above.

<img src="./graphics/preview.png">

> Since v2.0.0, it uses [AndroidX][androidx] so, first [migrate][androidx-migrate] your
project to AndroidX.
<br/>Since v4.1.0, it is dependent on Java 8 due to the dependency on
[Dynamic Utils](https://github.com/pranavpandey/dynamic-utils).

---

## Contents

- [Installation](#installation)
- [Usage](#usage)
    - [Setup](#setup)
    - [DynamicDialog](#dynamicdialog)
    - [DynamicDialogFragment](#dynamicdialogfragment)
        - [Show dialog](#show-dialog)
        - [Dialog state](#dialog-state)
        - [Dialog builder](#dialog-builder)
        - [Dialog callbacks](#dialog-callbacks)
    - [Dependency](#dependency)
- [License](#license)

---

## Installation

It can be installed by adding the following dependency to your `build.gradle` file:

```groovy
dependencies {
    // For AndroidX enabled projects.
    implementation 'com.pranavpandey.android:dynamic-dialogs:4.3.2'

    // For legacy projects.
    implementation 'com.pranavpandey.android:dynamic-dialogs:1.3.0'
}
```

---

## Usage

It is a library to display dialog and dialog fragments with ease. It has some improvements 
over `appcompat-v7` (or AndroidX) dialogs and shares the same behavior and methods which can be 
used with any other framework or library. 

> For a complete reference, please read the [documentation][documentation].

### Setup

First add `alertDialogStyle` in the base application theme to make `dynamic-dialogs` working.

```xml
<!-- Base application theme. -->
<style name="AppTheme" parent="...">
    <!-- Customize your theme here. -->
    ...
    
    <!-- Add dynamic dialogs style in the base app theme. -->
    <item name="alertDialogStyle">@style/DynamicDialog</item>
</style>
```

### DynamicDialog

It is almost identical to the `AppCompatDialog` but provides scroll indicators at top and bottom 
for the custom dialogs also. So, if you are using any scrollable view in your custom dialog like
`NestedScrollView` or `RecyclerView`, it can be set as `root view` and the scroll indicators will be 
added automatically if the view can be scrolled.
 
```java
new DynamicDialog.Builder(context)
        // Set dialog title.
        .setTitle(...)
        ...
        // Set a custom view.
        .setView(int layoutResId)
        // OR
        setView(View view)
        // Set view root to automatically add scroll dividers.
        .setViewRoot(int viewRootId)
        // OR
        .setViewRoot(View viewRoot)
        // Show the dialog.
        .show();
``` 

### DynamicDialogFragment

Base dialog fragment to provide all the functionality of `DynamicDialog` inside a fragment. It can 
be extended to customise it further by overriding the supported methods.

```java
public CustomDialogFragment extends DynamicDialogFragment {
    
    ...
    
    /**
     * Override this method to customise the dynamic dialog builder before creating the dialog.
     *
     * @param dialogBuilder The current builder to be customised.
     * @param savedInstanceState The saved state of the fragment to restore it later.
     *
     * @return The customised dynamic dialog builder.
     */
    @Override
    protected @NonNull DynamicDialog.Builder onCustomiseBuilder(
            @NonNull DynamicDialog.Builder dialogBuilder, @Nullable Bundle savedInstanceState) {
        // TODO: Customise the dialog here.
        ...
        
        return dialogBuilder;
    }
    
    /**
     * Override this method to customise the dynamic dialog before attaching it with 
     * this fragment.
     *
     * @param alertDialog The current dialog to be customised.
     * @param savedInstanceState The saved state of the fragment to restore it later.
     *
     * @return The customised dynamic dialog.
     */
    protected @NonNull DynamicDialog onCustomiseDialog(@NonNull DynamicDialog alertDialog,
            @Nullable Bundle savedInstanceState) {
        // TODO: Customise the dialog builder here.
        ...
        
        return alertDialog;
    }
    
    ...
}
```

#### Show dialog

Show the `DynamicDialogFragment` by using `showDialog(fragmentActivity)` method.

> For better understanding, please check `AboutDialogFragment` in the `sample` project.

#### Dialog state

It will automatically maintain its state on configuration changes (like device rotation, etc.) 
by calling `setRetainInstance(true)` in `onCreate()` method. If you don't want to use this feature 
(generally, if you are displaying it in `onResume()` method) then, call `setAutoDismiss(true)` to 
dismiss it in `onPause()` method.

```java
DynamicDialogFragment.newInstance().
        ...
        // Dismiss dialog fragment on configuration changes.
        .setAutoDismiss(true)
        // Show the dialog fragment.
        .showDialog(fragmentActivity);

```

#### Dialog builder

To show quick `DynamicDialogFragment`, you can use its `setBuilder(DynamicDialog.Builder)` method
and pass `DynamicDialog.Builder` with all the customisations. It will automatically wrap it in a 
`DialogFragment` and use `showDialog(fragmentActivity)` method to display it.

```java
DynamicDialogFragment.newInstance().setBuilder(
        new DynamicDialog.Builder(context)
                // Set dialog title.
                .setTitle(...)
                // Set dialog message.
                .setMessage(...)
                // Set negative button with a click listener.
                .setNegativeButton(..., clickListener))
        // Show the dialog fragment.
        .showDialog(fragmentActivity);
```

#### Dialog callbacks

As `DialogFragment` has required some extra work to get the event callbacks, it already has all the 
listeners of `DynamicDialog.Builder` built-in so that there is no extra effort is required.

```java
DynamicDialogFragment.newInstance()
        ...
        // Callback when this dialog fragment is displayed.
        .setOnShowListener(onShowListener)
        // Callback when this dialog fragment has been dismissed.
        .setOnDismissListener(onDismissListener)
        // Callback when this dialog fragment has been canceled.
        .setOnCancelListener(onCancelListener)
        // Callback when a key is pressed in this dialog fragment.
        .setOnKeyListener(onKeyListener)
        // Show the dialog fragment.
        .showDialog(fragmentActivity);
        
```

> For better understanding, please check `CustomDialogFragment` in the `sample` project.

### Dependency

It depends on the [dynamic-utils][dynamic-utils] to perform various internal operations. So, its 
functions can also be used to perform other useful operations.

---

## Author

Pranav Pandey

[![GitHub](https://img.shields.io/github/followers/pranavpandey?label=GitHub&style=social)](https://github.com/pranavpandey)
[![Follow on Twitter](https://img.shields.io/twitter/follow/pranavpandeydev?label=Follow&style=social)](https://twitter.com/intent/follow?screen_name=pranavpandeydev)
[![Donate via PayPal](https://img.shields.io/static/v1?label=Donate&message=PayPal&color=blue)](https://paypal.me/pranavpandeydev)

---

## License

    Copyright 2017-2023 Pranav Pandey
    Copyright 2015 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[androidx]: https://developer.android.com/jetpack/androidx
[androidx-migrate]: https://developer.android.com/jetpack/androidx/migrate
[documentation]: https://pranavpandey.github.io/dynamic-dialogs
[dynamic-utils]: https://github.com/pranavpandey/dynamic-utils
