Prefser
=======
Wrapper for Android [SharedPreferences](http://developer.android.com/reference/android/content/SharedPreferences.html) with object serialization and [RxJava](https://github.com/ReactiveX/RxJava) Observables

:construction: Documentation is under construction and it will be extended. :construction:

Contents
--------
* [Overview](#overview)
* [Creating Prefser object](#creating-prefser-object)
* [Saving data](#saving-data)
* [Reading data](#reading-data)
* [Subscribing for data updates](#subscribing-for-data-updates)
* [Example](#example)
* [Tests](#tests)
* [Caveats](#caveats)
* [References](#references)
* [License](#license)

Overview
--------

Prefser wraps SharedPreferences and thanks to Java Generics provides you simpler API than classic SharedPreferences with only two methods:
```java
void put(String key, Object value)
<T> T get(String key, Class classOfT)
```

Classic SharedPreferences allows you to store only primitive data types and set of strings.

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
Observable<String> from(final SharedPreferences sharedPreferences)
Observable<String> fromDefaultPreferences()
```

You can subscribe one of these observables and monitor updates of SharedPreferences with powerful RxJava.

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

prefser.put("key", objects);  // put array of CustomObjects

prefser.put("key", new HashSet<>(Arrays.asList("one", "two", "three"))); // put set of Strings
prefser.put("key", new HashSet<>(Arrays.asList(1.2, 3.4, 5.6)));         // put set of doubles
```

Reading data
------------

You can read data with the following method:

```java
<T> T get(String key, Class classOfT)
```

**Examples**

:construction: Examples will be added here. :construction:

Subscribing for data updates
----------------------------

You can subscribe the following Observables from `Prefser` object:

```java
Observable<String> from(final SharedPreferences sharedPreferences)
Observable<String> fromDefaultPreferences()
```

**Examples**

:construction: Examples will be added here. :construction:

Example
-------

Examplary app using `Prefser` is available in the `app` directory.

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
