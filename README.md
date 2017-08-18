<img src="https://raw.githubusercontent.com/pranavpandey/dynamic-dialogs/master/graphics/dynamic-dialogs_512x512.png" width="160" height="160" align="right" hspace="20">

# Dynamic Dialogs

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/pranavpandey/dynamic-dialogs.svg?branch=master)](https://travis-ci.org/pranavpandey/dynamic-dialogs)
[![Download](https://api.bintray.com/packages/pranavpandey/android/dynamic-dialogs/images/download.svg)](https://bintray.com/pranavpandey/android/dynamic-dialogs/_latestVersion)

A simple library to display improved app compat dialogs on Android 14+ (ICS or above) devices.

<img src="https://raw.githubusercontent.com/pranavpandey/dynamic-dialogs/master/graphics/dynamic-dialogs-preview.png">

---

## Contents

- [Installation](https://github.com/pranavpandey/dynamic-dialogs#installation)
- [Usage](https://github.com/pranavpandey/dynamic-dialogs#usage)
    - [Setup](https://github.com/pranavpandey/dynamic-dialogs#setup)
    - [DynamicDialog](https://github.com/pranavpandey/dynamic-dialogs#dynamicdialog)
    - [DynamicDialogFragment](https://github.com/pranavpandey/dynamic-dialogs#dynamicdialogfragment)
- [License](https://github.com/pranavpandey/dynamic-dialogs#license)

---

## Installation

It can be installed by adding the following dependency to your `build.gradle` file:

```groovy
dependencies {
    compile 'com.pranavpandey.android:dynamic-dialogs:0.4.0'
}
```

---

## Usage
It has some improvements over `appcompat-v7` dialogs and share the same behavior and methods.
It can be used with any UI/UX framework or library and also has built-in fragment class to display
`DialogFragment` with ease.

### Setup
First add `alertDialogStyle` in the base application theme to make `dynamic-dialogs` working.

```xml
<!-- Base application theme. -->
<style name="AppTheme" parent="...">
    <!-- Customize your theme here. -->
    ...
    
    <!-- Add dynamic dialogs style in the base app theme. -->
    <item name="alertDialogStyle">@style/Base.DynamicDialogs.AlertDialog</item>
</style>
```

### DynamicDialog
It is almost identical to the `AppCompatDialog` but provides scroll indicators at top and bottom 
for the custom dialogs also. So, if you are using any scrollable view in your custom dialog like
`NestedScrollView` or `RecyclerView`, it can be set as `root view` and the scroll indicators will be 
added automatically if the view can be scrolled.
 
```java
new DynamicDialog.Builder(context)
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
        .show();
``` 

### DynamicDialogFragment
Base dialog fragment to provide all the functionality of `DynamicDialog` inside a fragment. It can 
be extended to customise it further by overriding the supported methods.

```java
public CustomDialogFragment extends DynamicDialogFragment {
    
    ...
    
    @NonNull
    @Override
    protected DynamicDialog onCustomiseDialog(@NonNull DynamicDialog alertDialog) {
        // Customise the dialog here.
        ...
        
        return alretDialog;
    }
    
    @NonNull
    @Override
    protected DynamicDialog.Builder onCustomiseBuilder(
            @NonNull DynamicDialog.Builder alertDialogBuilder) {
        // Customise the dialog builder here.
        ...
        
        return alertDialogBuilder;
    }
    
    ...
}
```

> Show the `DynamicDialogFragment` by using `showDialog(fragmentActivity)` method.

For better understanding, please check `AboutDialog` in the `sample` project.

#### State
It will automatically maintain its state on configuration changes (like device rotation, etc.) 
by calling `setRetainInstance(true)` in `onCreate()` method. If you don't want to use this feature 
(generally, if you are displaying it in `onResume()` method) then, call `setAutoDismiss(true)` to 
dismiss it in `onPause()` method.

```java
DynamicDialogFragment.newInstance().
        ...
        .setAutoDismiss(true)
        .showDialog(fragmentActivity);

```

#### Builder
To show quick `DynamicDialogFragment`, you can use its `setBuilder(DynamicDialog.Builder)` method
and pass `DynamicDialog.Builder` with all the customisations. It will automatically wrap it in a 
`DialogFragment` and use `showDialog(fragmentActivity)` method to display it.

```java
DynamicDialogFragment.newInstance().setBuilder(
        new DynamicDialog.Builder(context)
                .setTitle(...)
                .setMessage(...)
                .setNegativeButton(..., clickListener))
        .showDialog(fragmentActivity);
```

---

## License

    Copyright (c) 2017 Pranav Pandey
    Copyright (C) 2015 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
