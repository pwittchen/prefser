Prefser
=======
Wrapper for Android [SharedPreferences](http://developer.android.com/reference/android/content/SharedPreferences.html) with object serialization and [RxJava](https://github.com/ReactiveX/RxJava) Observables

:construction: Documentation is under construction. It will be extended. :construction:

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
prefser.put("myKey1", true);               // put boolean
prefser.put("myKey2", 43f);                // put float
prefser.put("myKey3", 42);                 // put int
prefser.put("myKey4", 42l);                // put long
prefser.put("myKey5", 42.3);               // put double
prefser.put("myKey3", "hello");            // put String
prefser.put("myKey3", new CustomObject()); // put CustomObject
```

:construction: More examples will be added here. :construction:

Reading data
------------

:construction: To Be Done. :construction:

Subscribing for data updates
----------------------------

:construction: To Be Done. :construction:

Example
-------

Example is available in the `app` directory.

Tests
-----

Tests are available in `library/src/androidTest/java/` directory.

Caveats
-------

When you are going to store many numeric values under single key, you should use arrays instead of Lists. Gson converts all numeric values on the Lists into double, so you will have to deal with type conversion in case of using List data structure.

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
