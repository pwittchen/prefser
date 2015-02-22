Prefser
=======
Wrapper for Android [SharedPreferences](http://developer.android.com/reference/android/content/SharedPreferences.html) with object serialization and [RxJava](https://github.com/ReactiveX/RxJava) Observables

:construction: Documentation is under construction. It will be extended. :construction:

Overview
--------

Prefser wraps SharedPreferences and thanks to Java Generics provides you simpler API than classic SharedPreferences with only two methods:
* `void put(String key, Object value)`
* `<T> T get(String key, Class classOfT)`

Classic SharedPreferences allows you to store only primitive data types and set of strings.

Thanks to Gson serialization, Prefser allows you to store:
* primitive data types
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

* `Observable<String> from(final SharedPreferences sharedPreferences)`
* `Observable<String> fromDefaultPreferences()`

You can subscribe one of these observables and monitor updates of SharedPreferences with powerful RxJava.

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