Prefser [![Build Status](https://travis-ci.org/pwittchen/prefser.svg)](https://travis-ci.org/pwittchen/prefser) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Prefser-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1594) ![Maven Central](https://img.shields.io/maven-central/v/com.github.pwittchen/prefser.svg?style=flat)
=======
Wrapper for Android [SharedPreferences](http://developer.android.com/reference/android/content/SharedPreferences.html) with object serialization and [RxJava](https://github.com/ReactiveX/RxJava) Observables

Contents
--------
* [Overview](#overview)
* [Creating Prefser object](#creating-prefser-object)
* [Saving data](#saving-data)
* [Reading data](#reading-data)
* [Contains method](#contains-method)
* [Removing data](#removing-data)
* [Size of data](#size-of-data)
* [Subscribing for data updates](#subscribing-for-data-updates)
* [Example](#example)
* [Download](#download)
* [Tests](#tests)
* [Caveats](#caveats)
* [References](#references)
* [License](#license)

Overview
--------

Prefser wraps SharedPreferences and thanks to Java Generics provides you simpler API than classic SharedPreferences with only three methods:
```java
void put(String key, Object value);
<T> T get(String key, Class classOfT);
<T> T get(String key, Class classOfT, T defaultValue);
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

In addition, Prefser transforms [OnSharedPreferenceChangeListener](http://developer.android.com/reference/android/content/SharedPreferences.OnSharedPreferenceChangeListener.html) into Observables from RxJava:
```java
Observable<String> from(final SharedPreferences sharedPreferences);
Observable<String> fromDefaultPreferences();
```

You can subscribe one of these Observables and monitor updates of SharedPreferences with powerful RxJava.

Creating Prefser object
-----------------------

You can create `Prefser` object in the following ways:
```java
Prefser prefser = new Prefser(context);
Prefser prefser = new Prefser(sharedPreferences);
```
 
When you create `Prefser` object with Android Context, it will use default `SharedPreferences` from `PreferenceManager`.

Saving data
-----------

You can save data with the following method:

```java
void put(String key, Object value);
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
prefser.put("key", setOfStrings); // put set of Strings
prefser.put("key", setOfDoubles); // put set of doubles
```

Reading data
------------

You can read data with the following methods:

```java
<T> T get(String key, Class classOfT);
<T> T get(String key, Class classOfT, T defaultValue);
```

**Examples**

```java
Boolean value = prefser.get("key", Boolean.class);           // reading boolean
Float value = prefser.get("key", Float.class);               // reading float
Integer value = prefser.get("key", Integer.class);           // reading integer
Long value = prefser.get("key", Long.class);                 // reading long
Double value = prefser.get("key", Double.class);             // reading double
String value = prefser.get("key", String.class);             // reading String
CustomObject value = prefser.get("key", CustomObject.class); // reading CustomObject

/**
 * Reading boolean and setting default value. 
 * This method will return "true", when key "key" doesn't exist.
 * Default value can be set for any kind of data in the same way.
 */
Boolean value = prefser.get("key", Boolean.class, true);

List<Double> value = prefser.get("key", List.class);   // reading List of doubles
List<String> value = prefser.get("key", List.class);   // reading List of Strings

Boolean[] value = prefser.get("key", Boolean[].class);           // reading array of booleans
Float[] value = prefser.get("key", Float[].class);               // reading array of floats
Integer[] value = prefser.get("key", Integer[].class);           // reading array of integers
Long[] value = prefser.get("key", Long[].class);                 // reading array of longs
Double[] value = prefser.get("key", Double[].class);             // reading array of doubles
String[] value = prefser.get("key", String[].class);             // reading array of Strings
CustomObject[] value = prefser.get("key", CustomObject[].class); // reading array of CustomObjects

Set<String> value = prefser.get("key", Set.class); // reading Set of Strings
Set<Double> value = prefser.get("key", Set.class); // reading Set of Doubles
```

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
prefser.size()
```

Subscribing for data updates
----------------------------

You can subscribe the following RxJava Observables from `Prefser` object:

```java
Observable<String> from(final SharedPreferences sharedPreferences);
Observable<String> fromDefaultPreferences();
```

**Example**
```java
Subscription subscription = prefser.fromDefaultPreferences()
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

Example
-------

Examplary app using `Prefser` is available in the `app` directory.

If you want to use `Prefser` with [Dagger](https://github.com/square/dagger/), check out examplary app in `app-dagger` directory.

Download
--------

If you want to use Observables, besides dependency to Prefser you should also add dependency to RxAndroid.

You can depend on the library through Maven:

```xml
<dependency>
    <groupId>com.github.pwittchen</groupId>
    <artifactId>prefser</artifactId>
    <version>1.0.1</version>
</dependency>
<dependency>
    <groupId>io.reactivex</groupId>
    <artifactId>rxandroid</artifactId>
    <version>0.24.0</version>
</dependency>
```
or through Gradle:

```groovy
dependencies {
  compile 'com.github.pwittchen:prefser:1.0.1'
  compile 'io.reactivex:rxandroid:0.24.0'
}
```

Tests
-----

Tests are available in `library/src/androidTest/java/` directory.

Caveats
-------

* When you are going to store many numeric values under single key, you should use arrays instead of Lists. Gson converts all numeric values on the Lists into double, so you will have to deal with type conversion in case of using List data structure.
* When you are going to store many custom objects under single key, you should use arrays instead of Lists, because Lists are not deserialized correctly for custom data types.

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
