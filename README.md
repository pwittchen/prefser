Prefser [![Build Status](https://travis-ci.org/pwittchen/prefser.svg)](https://travis-ci.org/pwittchen/prefser) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Prefser-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1594) ![Maven Central](https://img.shields.io/maven-central/v/com.github.pwittchen/prefser.svg?style=flat)
=======
Wrapper for Android [SharedPreferences](http://developer.android.com/reference/android/content/SharedPreferences.html) with object serialization and [RxJava](https://github.com/ReactiveX/RxJava) Observables

min sdk version = 9

Contents
--------
* [Overview](#overview)
* [Creating Prefser object](#creating-prefser-object)
* [Saving data](#saving-data)
* [Reading data](#reading-data)
 * [get method](#get-method)
 * [observe mehtod](#observe-method)
 * [getAndObserve method](#getandobserve-method)
* [Contains method](#contains-method)
* [Removing data](#removing-data)
* [Size of data](#size-of-data)
* [Getting SharedPreferences object](#getting-sharedpreferences-object)
* [Subscribing for data updates](#subscribing-for-data-updates)
* [Examples](#examples)
* [Download](#download)
* [Tests](#tests)
* [Caveats](#caveats)
* [References](#references)
* [License](#license)

Overview
--------

Prefser wraps SharedPreferences and thanks to Java Generics provides you simpler API than classic SharedPreferences with only two methods:
```java
<T> void put(String key, T value)
<T> T get(String key, Class<T> classOfT, T defaultValue)
```

Classic SharedPreferences allows you to store only primitive data types, Strings and Set of Strings.

Thanks to Gson serialization, Prefser allows you to store:
* Primitive data types
 * boolean
 * float
 * int
 * long
 * double
* Strings
* Custom Objects
* Lists
* Arrays
* Sets

In addition, Prefser transforms [OnSharedPreferenceChangeListener](http://developer.android.com/reference/android/content/SharedPreferences.OnSharedPreferenceChangeListener.html) into Observable from RxJava:
```java
Observable<String> observePreferences();
```

You can subscribe one of this Observable and [monitor updates of SharedPreferences](#subscribing-for-data-updates) with powerful RxJava.
You can also [read data from RxJava Observables](#reading-data-from-observables) in order to monitor single shared preference with a specified key.

Creating Prefser object
-----------------------

You can create `Prefser` object in the following ways:
```java
Prefser prefser = new Prefser(context);
Prefser prefser = new Prefser(sharedPreferences);
```
 
When you create `Prefser` object with Android Context, it will use default `SharedPreferences` from `PreferenceManager`.

You can set `JsonConverter` implementation for `Prefser`. When it's not set, `Prefser` will use `GsonConverter` by default.

```java
Prefser prefser = new Prefser(context, jsonConverter);
Prefser prefser = new Prefser(sharedPreferences, jsonConverter);
```

Saving data
-----------

You can save data with the following method:

```java
<T> void put(String key, T value)
```

**Examples**

```java
prefser.put("key", true);               // put boolean
prefser.put("key", 43f);                // put float
prefser.put("key", 42);                 // put int
prefser.put("key", 42l);                // put long
prefser.put("key", 42.3);               // put double
prefser.put("key", "hello");            // put String
prefser.put("key", new CustomObject()); // put CustomObject

prefser.put("key", Arrays.asList(true, false, true));     // put list of booleans
prefser.put("key", Arrays.asList(1f, 2f, 3f));            // put list of floats
prefser.put("key", Arrays.asList(1, 2, 3));               // put list of integers
prefser.put("key", Arrays.asList(1l, 2l, 3l));            // put list of longs
prefser.put("key", Arrays.asList(1.2, 2.3, 3.4));         // put list of doubles
prefser.put("key", Arrays.asList("one", "two", "three")); // put list of Strings

List<CustomClass> objects = Arrays.asList(
   new CustomObject(),
   new CustomObject(),
   new CustomObject());

prefser.put(givenKey, objects); // put list of CustomObjects

prefser.put("key", new Boolean[]{true, false, true});     // put array of booleans
prefser.put("key", new Float[]{1f, 2f, 3f});              // put array of floats
prefser.put("key", new Integer[]{1, 2, 3});               // put array of integers
prefser.put("key", new Long[]{1l, 2l, 3l});               // put array of longs
prefser.put("key", new Double[]{1.2, 2.3, 3.4});          // put array of doubles
prefser.put("key", new String[]{"one", "two", "three"});  // put array of Strings

CustomObject[] objects = new CustomObject[]{
   new CustomObject(), 
   new CustomObject(), 
   new CustomObject()
};

prefser.put("key", objects); // put array of CustomObjects

Set<String> setOfStrings = new HashSet<>(Arrays.asList("one", "two", "three"));
Set<Double> setOfDoubles = new HashSet<>(Arrays.asList(1.2, 3.4, 5.6));
prefser.getPreferences().edit().putStringSet("key", setOfStrings).apply(); // put Set of Strings in a "classical way"
prefser.put("key", setOfDoubles); // put set of doubles
```

Reading data
------------

### get method

You can read data with the following method:

```java
<T> T get(String key, Class<T> classOfT, T defaultValue)
```

**Examples**

```java

// reading primitive types

Boolean value = prefser.get("key", Boolean.class, false);
Float value = prefser.get("key", Float.class, 1.0f);
Integer value = prefser.get("key", Integer.class, 1);
Long value = prefser.get("key", Long.class, 1.0l);
Double value = prefser.get("key", Double.class, 1.0);
String value = prefser.get("key", String.class, "default string");

// reading custom object

CustomObject value = prefser.get("key", CustomObject.class, new CustomObject());

// reading lists

// example with List of Booleans

List<Boolean> defaultBooleans = Arrays.asList(false, false, false);

TypeToken<List<Boolean>> typeToken = new TypeToken<List<Boolean>>() {
};

List<Boolean> readObject = prefser.get(givenKey, typeToken, defaultBooleans);

// in the same way we can read list of objects of any type including custom objects
// the only thing we need to do is replacing Boolean type with our desired type

// reading arrays

Boolean[] value = prefser.get("key", Boolean[].class, new Boolean[]{});
Float[] value = prefser.get("key", Float[].class, new Float[]{});
Integer[] value = prefser.get("key", Integer[].class, new Integer[]{});
Long[] value = prefser.get("key", Long[].class, new Long[]{});
Double[] value = prefser.get("key", Double[].class, new Double[]{});
String[] value = prefser.get("key", String[].class, new String[]{});
CustomObject[] value = prefser.get("key", CustomObject[].class, new CustomObject[]{});

// reading sets

Set<String> value = prefser.getPreferences().getStringSet("key", new HashSet<>()); // accessing set of strings in a "classical way"
Set<Double> value = prefser.get("key", Set.class, new HashSet<>());
```

### observe method

You can observe changes of data with the following RxJava Observable:

```java
<T> Observable<T> observe(String key, Class<T> classOfT, T defaultValue)
```

**Note**

Use it, when you want to observe single preference under a specified key.
When you want to observe many preferences, use [observePreferences()](#subscribing-for-data-updates) method.

**Example**

```java
Subscription subscription = prefser.observe(key, String.class, "default value")
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        ... // you can do anything else, what is possible with RxJava
        .subscribe(new Action1<String>() {
            @Override
            public void call(String key) {
                // Perform any action you want.
                // E.g. get value stored under key
                // and display in a TextView.
            }
        });
```

### getAndObserve method

You can combine functionality of `get(...)` and `observe(...)` methods with `getAndObserve(...)`, which is defined as follows:

```java
<T> Observable<T> getAndObserve(String key, Class<T> classOfT, T defaultValue)
```

You can subscribe this method in exactly the same way as `observe(...)` method. The only difference is the fact that this method will emit value from SharedPreferences as first element of the stream with `get(...)` method even if SharedPreferences were not changed. When SharedPreferences changes, subscriber will be notified about the change in the same way as in regular `observe(...)` method.

Contains method
-------------

You can check if data exists under a specified key in the following way:

```java
prefser.contains("key");
```

Removing data
-------------

You can remove data under specified key in the following way:

```java
prefser.remove("key");
```

When you want to clear all SharedPreferences you can use `clear()` method as follows:

```java
prefser.clear();
```

Size of data
------------

You can read number of all items stored in the SharedPreferences in the following way:

```java
prefser.size();
```

Getting SharedPreferences object
--------------------------------

You can get `SharedPreferences` object in the following way:

```java
prefser.getPreferences();
```

You can use it for performing operations on `SharedPreferences` without Prefser library. E.g. for reading and writing Set of Strings, what is currently not supported by Prefser. See sections about [saving data](#saving-data) and [reading data](#reading-data) where you can find examples.

Subscribing for data updates
----------------------------

You can subscribe the following RxJava Observable from `Prefser` object:

```java
Observable<String> observePreferences();
```

**Note**

Use it, when you want to observe many shared preferences.
If you want to observe single preference under as specified key, use [observe()](#observe-method) method.

**Example**
```java
Subscription subscription = prefser.observePreferences()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .filter(...) // you can filter your updates by key
        ...          // you can do anything else, what is possible with RxJava
        .subscribe(new Action1<String>() {
            @Override
            public void call(String key) {
              // Perform any action you want.
              // E.g. get value stored under key 
              // and display in a TextView.
            }
        });
```

This subscription can be created e.g. in `onResume()` method, but it depends on your specific implementation and project requirements. Now, everytime when data in SharedPreferences changes, subscriber will be notified under which key value was updated and it can react on that change.

**Unsubscribing from Observable**

When you are subscribing for the updates in Activity, please remember to unsubscribe your subscriber in `onPause()` method in the following way:

```java
@Override
protected void onPause() {
    super.onPause();
    subscription.unsubscribe();
}
```

Examples
--------

- Examplary app using `Prefser` is available in the `app` directory.
- If you want to use `Prefser` with [Dagger](https://github.com/square/dagger/), check out examplary app in `app-dagger` directory.
- If you want to use `Prefser` with [PreferenceActivity](http://developer.android.com/reference/android/preference/PreferenceActivity.html), check out examplary in `app-preference-activity` directory.
- More usage examples can be found in unit tests in `PrefserTest` class.

Download
--------

If you want to use Observables, besides dependency to Prefser you should also add dependency to RxAndroid.

You can depend on the library through Maven:

```xml
<dependency>
    <groupId>com.github.pwittchen</groupId>
    <artifactId>prefser</artifactId>
    <version>1.0.5</version>
</dependency>
<dependency>
    <groupId>io.reactivex</groupId>
    <artifactId>rxandroid</artifactId>
    <version>1.0.0</version>
</dependency>
```
or through Gradle:

```groovy
dependencies {
  compile 'com.github.pwittchen:prefser:1.0.5'
  compile 'io.reactivex:rxandroid:1.0.0'
}
```

Tests
-----

Tests are available in `library/src/androidTest/java/` directory and can be executed on emulator or Android device from Android Studio or CLI with the following command:

```
./gradlew connectedCheck
```

Test coverage report can be generated with the following command:

```
./gradlew createDebugCoverageReport
```

In order to generate report, emulator or Android device needs to be connected to the computer.
Report will be generated in the `library/build/outputs/reports/coverage/debug/` directory.

Caveats
-------

* Set of Strings should be saved and read in a "classical way" with `getPreferences()` method.

References
----------
* [SharedPreferences](http://developer.android.com/reference/android/content/SharedPreferences.html)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)

License
-------

    Copyright 2015 Piotr Wittchen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
