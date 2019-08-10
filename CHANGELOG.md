CHANGELOG
=========

v. 2.2.3
--------
*09 Aug 2019*
- updated project dependencies

v. 2.2.2
--------
*29 Jan 2019*

- updated project dependencies - 74de9db550ff941083448c28c48b6daffaed7b6c
- migrated to `androidx` - 74de9db550ff941083448c28c48b6daffaed7b6c, fixes #130

v. 2.2.1
--------
*03 Jan 2018*
- Updated project dependencies
  - RxJava -> 2.1.8
  - Truth -> 0.3.7
- Updated Gradle to 3.1

v. 2.2.0
--------
*08 Dec 2017*
- Allow to pass custom instance of Gson object - PR #123 
- Updated project dependencies
  - RxJava -> 2.1.7
  - Gson -> 2.8.2
  - Support Annotations -> 27.0.2
  - AppCompat v7 -> 27.0.2
  - Truth -> 0.36
  - Mockito Core -> 2.13.0
- Updated Gradle to 3.0

v. 2.1.0
--------
*19 Jun 2017*
- migrated project to RxJava2.x on `RxJava2.x` branch
- kept backward compatibility with RxJava.x or `RxJava1.x` branch
- removed `master` branch from the Git repository
- updated project dependencies
- updated project samples
- updated documentation

v. 2.0.7
--------
*25 May 2017*

- updated dependencies
- updated Gradle configuration
- migrated unit tests to Robolectric
- started executing unit tests on Travis CI
- added integration with codecov.io and coverage report
- extracted code related to accessors from the `Prefser` class (refactoring library internals) #109 

v. 2.0.6
--------
*30 Jul 2016*

- bumped RxJava to v. 1.1.8
- bumped Gson to 2.7
- bumped Truth to 0.28
- updated dependencies in sample apps

v. 2.0.5
--------
*28 Dec 2015*
- improved error checking in `put(...)` method
- added missing annotations to some tests and reorganized tests
- added missing license info to some classes
- bumped RxAndroid version in `README.md` file

v. 2.0.4
--------
*13 Dec 2015*
- bumped Gson dependency to v. 2.5
- bumped RxJava dependency to v. 1.1.0
- bumped Google Truth test dependency to v. 0.27

v. 2.0.3
--------
*04 Dec 2015*

- added `@NonNull` Android support annotations
- updated Gson dependency to the newest version
- updated Target Android SDK version to 23
- removed Dagger 1 sample app, because it was outdated and too trivial

v. 2.0.2
--------
*06 Nov 2015*

- fixed bug reported in issue #70: get(...) method now returns a null value instead of "null" string when setting default value to null of String type
- fixed RxJava usage in sample app
- fixed RxJava usage in code snippets in `README.md`
- changed code formatting to `SquareAndroid`
- added static code analysis
- improved code according to static code analysis suggestions

v. 2.0.1
--------
*24 Sep 2015*

- bumped RxJava to v. 1.0.14
- bumped Gradle Build Tools to v. 1.3.1
- bumped RxAndroid to 1.0.1 in `README.md` (RxAndroid isn't part of prefser, but it can be used with it)

v. 2.0.0
--------
*06 Aug 2015*

- fixed not keeping reference to listener when `Observable` instance is reused
- fixed not unregistering listener, which causes `onNext()` even after `unsubscribe()`
- fixed possible missed update with `getAndObserve()`
- removed `observe(sharedPreferences)` method - *backward incompatible*
- changed `observeDefaultPreferences()` method name to `observePreferences()` - *backward incompatible*
- added `TypeToken` and use of generics for interfaces
- added possibility to store Lists of different types of data including custom objects
- added more unit tests
- updated test dependencies
- updated JavaDoc available at http://pwittchen.github.io/prefser/

v. 1.0.5
--------
*18 Jun 2015*

- Removed `final` keyword from `Prefser` class in order to allow class mocking
- Removed unused imports from `Prefser` class
- Added test coverage report generation
- Increased test coverage to 100%
- Added abstraction for `JsonConverter` and default `GsonConverter`
- Added `getAndObserve(...)` method
- Emitting current value right on subscription to `Observable` with `getAndObserve(...)` method
- Added GitHub pages with generated JavaDoc documentation on `gh-pages` branch available at: http://pwittchen.github.io/prefser/

v. 1.0.4
--------
*09 May 2015*

**1. Changed method names in the API from**:
```java
Observable<String> from(final SharedPreferences sharedPreferences);
Observable<String> fromDefaultPreferences();
<T> Observable<T> getObservable(final String key, final Class classOfT, final T defaultValue)
```
**to**:
```java
Observable<String> observe(final SharedPreferences sharedPreferences);
Observable<String> observeDefaultPreferences();
<T> Observable<T> observe(final String key, final Class classOfT, final T defaultValue)
```
**2. Removed the following methods from the public API**:
```java
<T> T get(String key, Class classOfT);
<T> Observable<T> getObservable(final String key, final Class classOfT)
```
Now, we can read data only with **defined default value** like in regular `SharedPreferences`.

**3. Fixed bug with returning default Boolean value as String**
It was reported in #22. Now, Prefser uses default methods from `SharedPreferences` for reading and saving data when it's possible. Other kind of data are serialized to JSON format via Gson library and stored as Strings.

**4. Added example of using library with PreferenceActivity**
It's available in [app-preference-activity](https://github.com/pwittchen/prefser/tree/master/app-preference-activity) directory.

**5. Added getPreferences() method to the public API**
This method can be used for getting `SharedPreferences` object in the following way:
```java
prefser.getPreferences();
```
We can find more information in [README.md file](https://github.com/pwittchen/prefser#getting-sharedpreferences-object).

**6. Updated Unit Tests**
Removed tests, which are no longer valid and added a few new tests.

v. 1.0.3
--------
*08 Apr 2015*

added possibility to read data from a single preference stored under a specified key via the following RxJava Observables:

```java
<T> Observable<T> getObservable(final String key, final Class classOfT)
<T> Observable<T> getObservable(final String key, final Class classOfT, final T defaultValue)
```

v. 1.0.2
--------
*01 Mar 2015*

changed min sdk version from 11 to 9

v. 1.0.1
--------
*28 Feb 2015*

- improved error checking
- updated unit tests
- added example of library usage with Dagger
- updated documentation

v. 1.0.0
--------
*22 Feb 2015*

First version of the library released to Maven Central Repository.

It wraps Android SharedPreferences and allows to store and read: primitive data types (boolean, float, int, long, double), Strings, Custom Objects, Lists, Arrays and Sets. In addition, Prefser transforms OnSharedPreferenceChangeListener into Observables from RxJava, which can be subscribed in order to monitor data updates.

Min sdk version = 11.

