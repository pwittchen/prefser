package com.github.pwittchen.prefser.library;

import android.annotation.TargetApi;
import android.support.test.InstrumentationRegistry;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.Subscription;

import static com.google.common.truth.Truth.assertThat;

public class PrefserObservablesTest {

  private static final String GIVEN_KEY = "givenKey";
  public static final String GIVEN_STRING_VALUE = "givenStringValue";
  private Prefser prefser;

  private class CustomClass {
    private int valueOne;
    private String valueTwo;

    public CustomClass(int valueOne, String valueTwo) {
      this.valueOne = valueOne;
      this.valueTwo = valueTwo;
    }

    @Override public boolean equals(Object other) {
      boolean isEqual = false;

      if (other instanceof CustomClass) {
        isEqual = ((CustomClass) other).valueOne == valueOne;
        isEqual |= ((CustomClass) other).valueTwo.equals(valueTwo);
      }

      return isEqual;
    }

    @Override @TargetApi(value = 19) public int hashCode() {
      return Objects.hash(valueOne, valueTwo);
    }
  }

  @Before public void setUp() {
    prefser = new Prefser(InstrumentationRegistry.getContext());
    prefser.clear();
  }

  @After public void tearDown() {
    prefser.clear();
  }

  @Test public void testObserveBoolean() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    boolean givenValue = true;
    Boolean defaultValue = false;

    // when
    RecordingObserver<Boolean> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Boolean.class, defaultValue).subscribe(observer);
    prefser.put(givenKey, givenValue);

    // then
    assertThat(observer.takeNext()).isTrue();
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveBoolean() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    boolean givenValue = true;
    Boolean defaultValue = false;

    // when
    prefser.put(givenKey, givenValue);
    Boolean first =
        prefser.getAndObserve(givenKey, Boolean.class, defaultValue).toBlocking().first();

    // then
    assertThat(first).isTrue();
  }

  @Test public void testObserveBooleanPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    boolean givenValue = true;
    boolean defaultValue = false;

    // when
    RecordingObserver<Boolean> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Boolean.class, defaultValue).subscribe(observer);
    prefser.put(givenKey, givenValue);

    // then
    assertThat(observer.takeNext()).isTrue();
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveBooleanPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    boolean givenValue = true;
    boolean defaultValue = false;

    // when
    prefser.put(givenKey, givenValue);
    boolean first =
        prefser.getAndObserve(givenKey, Boolean.class, defaultValue).toBlocking().first();

    // then
    assertThat(first).isTrue();
  }

  @Test public void testObserveFloat() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Float givenValue = 3.0f;
    Float defaultValue = 1.0f;

    // when
    RecordingObserver<Float> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Float.class, defaultValue).subscribe(observer);
    prefser.put(givenKey, givenValue);

    // then
    assertThat(observer.takeNext()).isEqualTo(givenValue);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveFloat() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Float givenValue = 3.0f;
    Float defaultValue = 1.0f;

    // when
    prefser.put(givenKey, givenValue);
    Float first = prefser.getAndObserve(givenKey, Float.class, defaultValue).toBlocking().first();

    // then
    assertThat(first).isEqualTo(givenValue);
  }

  @Test public void testObserveFloatPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    float givenValue = 3.0f;
    float defaultValue = 1.0f;

    // when
    RecordingObserver<Float> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Float.class, defaultValue).subscribe(observer);
    prefser.put(givenKey, givenValue);

    // then
    assertThat(observer.takeNext()).isEqualTo(givenValue);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveFloatPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    float givenValue = 3.0f;
    float defaultValue = 1.0f;

    // when
    prefser.put(givenKey, givenValue);
    float first = prefser.getAndObserve(givenKey, Float.class, defaultValue).toBlocking().first();

    // then
    assertThat(first).isEqualTo(givenValue);
  }

  @Test public void testObserveInteger() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Integer givenValue = 5;
    Integer defaultValue = 8;

    // when
    RecordingObserver<Integer> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Integer.class, defaultValue).subscribe(observer);
    prefser.put(givenKey, givenValue);

    // then
    assertThat(observer.takeNext()).isEqualTo(givenValue);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveInteger() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Integer givenValue = 5;
    Integer defaultValue = 8;

    // when
    prefser.put(givenKey, givenValue);
    Integer first =
        prefser.getAndObserve(givenKey, Integer.class, defaultValue).toBlocking().first();

    // then
    assertThat(first).isEqualTo(givenValue);
  }

  @Test public void testObserveIntegerPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    int givenValue = 5;
    int defaultValue = 8;

    // when
    RecordingObserver<Integer> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Integer.class, defaultValue).subscribe(observer);
    prefser.put(givenKey, givenValue);

    // then
    assertThat(observer.takeNext()).isEqualTo(givenValue);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveIntegerPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    int givenValue = 5;
    int defaultValue = 8;

    // when
    prefser.put(givenKey, givenValue);
    int first = prefser.getAndObserve(givenKey, Integer.class, defaultValue).toBlocking().first();

    // then
    assertThat(first).isEqualTo(givenValue);
  }

  @Test public void testObserveLong() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Long givenValue = 12L;
    Long defaultValue = 16L;

    // when
    RecordingObserver<Long> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Long.class, defaultValue).subscribe(observer);
    prefser.put(givenKey, givenValue);

    // then
    assertThat(observer.takeNext()).isEqualTo(givenValue);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveLong() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Long givenValue = 12L;
    Long defaultValue = 16L;

    // when
    prefser.put(givenKey, givenValue);
    Long first = prefser.getAndObserve(givenKey, Long.class, defaultValue).toBlocking().first();

    // then
    assertThat(first).isEqualTo(givenValue);
  }

  @Test public void testObserveLongPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    long givenValue = 12L;
    long defaultValue = 16L;

    // when
    RecordingObserver<Long> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Long.class, defaultValue).subscribe(observer);
    prefser.put(givenKey, givenValue);

    // then
    assertThat(observer.takeNext()).isEqualTo(givenValue);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveLongPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    long givenValue = 12L;
    long defaultValue = 16L;

    // when
    prefser.put(givenKey, givenValue);
    long first = prefser.getAndObserve(givenKey, Long.class, defaultValue).toBlocking().first();

    // then
    assertThat(first).isEqualTo(givenValue);
  }

  @Test public void testObserveDouble() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Double givenValue = 12.4;
    Double defaultValue = 19.9;

    // when
    RecordingObserver<Double> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Double.class, defaultValue).subscribe(observer);
    prefser.put(givenKey, givenValue);

    // then
    assertThat(observer.takeNext()).isEqualTo(givenValue);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveDouble() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Double givenValue = 12.4;
    Double defaultValue = 19.9;

    // when
    prefser.put(givenKey, givenValue);
    Double first = prefser.getAndObserve(givenKey, Double.class, defaultValue).toBlocking().first();

    // then
    assertThat(first).isEqualTo(givenValue);
  }

  @Test public void testObserveDoublePrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    double givenValue = 12.4;
    double defaultValue = 19.9;

    // when
    RecordingObserver<Double> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Double.class, defaultValue).subscribe(observer);
    prefser.put(givenKey, givenValue);

    // then
    assertThat(observer.takeNext()).isEqualTo(givenValue);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveDoublePrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    double givenValue = 12.4;
    double defaultValue = 19.9;

    // when
    prefser.put(givenKey, givenValue);
    double first = prefser.getAndObserve(givenKey, Double.class, defaultValue).toBlocking().first();

    // then
    assertThat(first).isEqualTo(givenValue);
  }

  @Test public void testObserveString() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    String givenValue = "hi, I'm sample string";
    String defaultValue = "";

    // when
    RecordingObserver<String> observer = new RecordingObserver<>();
    prefser.observe(givenKey, String.class, defaultValue).subscribe(observer);
    prefser.put(givenKey, givenValue);

    // then
    assertThat(observer.takeNext()).isEqualTo(givenValue);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveString() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    String givenValue = "hi, I'm sample string";
    String defaultValue = "";

    // when
    prefser.put(givenKey, givenValue);
    String first = prefser.getAndObserve(givenKey, String.class, defaultValue).toBlocking().first();

    // then
    assertThat(first).isEqualTo(givenValue);
  }

  @Test public void testObserveCustomObject() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    CustomClass customClass = new CustomClass(56, GIVEN_STRING_VALUE);
    CustomClass defaultClass = new CustomClass(1, "");

    // when
    RecordingObserver<CustomClass> observer = new RecordingObserver<>();
    prefser.observe(givenKey, CustomClass.class, defaultClass).subscribe(observer);
    prefser.put(givenKey, customClass);

    // then
    assertThat(observer.takeNext()).isEqualTo(customClass);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveCustomObject() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    CustomClass customClass = new CustomClass(56, GIVEN_STRING_VALUE);
    CustomClass defaultClass = new CustomClass(1, "");

    // when
    prefser.put(givenKey, customClass);
    CustomClass first =
        prefser.getAndObserve(givenKey, CustomClass.class, defaultClass).toBlocking().first();

    // then
    assertThat(first).isEqualTo(customClass);
  }

  @Test public void testObserveListOfBooleans() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Boolean> booleans = Arrays.asList(true, false, true);
    List<Boolean> defaultBooleans = Arrays.asList(false, false, false);

    // when
    RecordingObserver<List<Boolean>> observer = new RecordingObserver<>();
    TypeToken<List<Boolean>> typeToken = new TypeToken<List<Boolean>>() {
    };
    prefser.observe(givenKey, typeToken, defaultBooleans).subscribe(observer);
    prefser.put(givenKey, booleans);

    // then
    assertThat(observer.takeNext()).isEqualTo(booleans);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveListOfBooleans() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Boolean> booleans = Arrays.asList(true, false, true);
    List<Boolean> defaultBooleans = Arrays.asList(false, false, false);

    // when
    prefser.put(givenKey, booleans);
    TypeToken<List<Boolean>> typeToken = new TypeToken<List<Boolean>>() {
    };
    List<Boolean> first =
        prefser.getAndObserve(givenKey, typeToken, defaultBooleans).toBlocking().first();

    // then
    assertThat(first).isEqualTo(booleans);
  }

  @Test public void testObserveListOfFloats() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Float> floats = Arrays.asList(1.1f, 2.2f, 3.3f);
    List<Float> defaultFloats = Arrays.asList(0f, 0f, 0f);

    // when
    RecordingObserver<List<Float>> observer = new RecordingObserver<>();
    TypeToken<List<Float>> typeToken = new TypeToken<List<Float>>() {
    };
    prefser.observe(givenKey, typeToken, defaultFloats).subscribe(observer);
    prefser.put(givenKey, floats);

    // then
    assertThat(observer.takeNext()).isEqualTo(floats);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveListOfFloats() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Float> floats = Arrays.asList(1.1f, 2.2f, 3.3f);
    List<Float> defaultFloats = Arrays.asList(0f, 0f, 0f);

    // when
    prefser.put(givenKey, floats);
    TypeToken<List<Float>> typeToken = new TypeToken<List<Float>>() {
    };
    List<Float> first =
        prefser.getAndObserve(givenKey, typeToken, defaultFloats).toBlocking().first();

    // then
    assertThat(first).isEqualTo(floats);
  }

  @Test public void testObserveListOfIntegers() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Integer> integers = Arrays.asList(1, 2, 3);
    List<Integer> defaultIntegers = Arrays.asList(0, 0, 0);

    // when
    RecordingObserver<List<Integer>> observer = new RecordingObserver<>();
    TypeToken<List<Integer>> typeToken = new TypeToken<List<Integer>>() {
    };
    prefser.observe(givenKey, typeToken, defaultIntegers).subscribe(observer);
    prefser.put(givenKey, integers);

    // then
    assertThat(observer.takeNext()).isEqualTo(integers);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveListOfIntegers() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Integer> integers = Arrays.asList(1, 2, 3);
    List<Integer> defaultIntegers = Arrays.asList(0, 0, 0);

    // when
    prefser.put(givenKey, integers);
    TypeToken<List<Integer>> typeToken = new TypeToken<List<Integer>>() {
    };
    List<Integer> first =
        prefser.getAndObserve(givenKey, typeToken, defaultIntegers).toBlocking().first();

    // then
    assertThat(first).isEqualTo(integers);
  }

  @Test public void testObserveListOfLongs() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Long> longs = Arrays.asList(1L, 2L, 3L);
    List<Long> defaultLongs = Arrays.asList(0L, 0L, 0L);

    // when
    RecordingObserver<List<Long>> observer = new RecordingObserver<>();
    TypeToken<List<Long>> typeToken = new TypeToken<List<Long>>() {
    };
    prefser.observe(givenKey, typeToken, defaultLongs).subscribe(observer);
    prefser.put(givenKey, longs);

    // then
    assertThat(observer.takeNext()).isEqualTo(longs);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveListOfLongs() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Long> longs = Arrays.asList(1L, 2L, 3L);
    List<Long> defaultLongs = Arrays.asList(0L, 0L, 0L);

    // when
    prefser.put(givenKey, longs);
    TypeToken<List<Long>> typeToken = new TypeToken<List<Long>>() {
    };
    List<Long> first =
        prefser.getAndObserve(givenKey, typeToken, defaultLongs).toBlocking().first();

    // then
    assertThat(first).isEqualTo(longs);
  }

  @Test public void testObserveListOfDoubles() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Double> doubles = Arrays.asList(1.2, 34.65, 3.6);
    List<Double> defaultDoubles = Arrays.asList(1.0, 1.0, 1.0);

    // when
    RecordingObserver<List<Double>> observer = new RecordingObserver<>();
    TypeToken<List<Double>> typeToken = new TypeToken<List<Double>>() {
    };
    prefser.observe(givenKey, typeToken, defaultDoubles).subscribe(observer);
    prefser.put(givenKey, doubles);

    // then
    assertThat(observer.takeNext()).isEqualTo(doubles);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveListOfDoubles() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Double> doubles = Arrays.asList(1.2, 34.65, 3.6);
    List<Double> defaultDoubles = Arrays.asList(1.0, 1.0, 1.0);

    // when
    prefser.put(givenKey, doubles);
    TypeToken<List<Double>> typeToken = new TypeToken<List<Double>>() {
    };
    List<Double> first =
        prefser.getAndObserve(givenKey, typeToken, defaultDoubles).toBlocking().first();

    // then
    assertThat(first).isEqualTo(doubles);
  }

  @Test public void testObserveListOfStrings() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<String> strings = Arrays.asList("one", "two", "three");
    List<String> defaultStrings = Arrays.asList("some", "default", "strings");

    // when
    RecordingObserver<List<String>> observer = new RecordingObserver<>();
    TypeToken<List<String>> typeToken = new TypeToken<List<String>>() {
    };
    prefser.observe(givenKey, typeToken, defaultStrings).subscribe(observer);
    prefser.put(givenKey, strings);

    // then
    assertThat(observer.takeNext()).isEqualTo(strings);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveListOfStrings() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<String> strings = Arrays.asList("first", "second", "third");
    List<String> defaultStrings = Arrays.asList("some", "another", "default strings");

    // when
    prefser.put(givenKey, strings);
    TypeToken<List<String>> typeToken = new TypeToken<List<String>>() {
    };
    List<String> first =
        prefser.getAndObserve(givenKey, typeToken, defaultStrings).toBlocking().first();

    // then
    assertThat(first).isEqualTo(strings);
  }

  @Test public void testObserveListOfCustomObjects() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    CustomClass defaultCustomObject = new CustomClass(0, "zero");

    List<CustomClass> customObjects =
        Arrays.asList(new CustomClass(1, "first one"), new CustomClass(2, "second one"),
            new CustomClass(3, "yet another one"));

    List<CustomClass> defaultCustomObjects =
        Arrays.asList(defaultCustomObject, defaultCustomObject, defaultCustomObject);

    // when
    RecordingObserver<List<CustomClass>> observer = new RecordingObserver<>();
    TypeToken<List<CustomClass>> typeToken = new TypeToken<List<CustomClass>>() {
    };
    prefser.observe(givenKey, typeToken, defaultCustomObjects).subscribe(observer);
    prefser.put(givenKey, customObjects);

    // then
    assertThat(observer.takeNext()).isEqualTo(customObjects);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveListOfCustomObjects() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;

    CustomClass defaultCustomObject = new CustomClass(0, "this is zero");

    List<CustomClass> customObjects =
        Arrays.asList(new CustomClass(1, "first class"), new CustomClass(2, "second class"),
            new CustomClass(3, "third class"));

    List<CustomClass> defaultCustomObjects =
        Arrays.asList(defaultCustomObject, defaultCustomObject, defaultCustomObject);

    // when
    prefser.put(givenKey, customObjects);
    TypeToken<List<CustomClass>> typeToken = new TypeToken<List<CustomClass>>() {
    };
    List<CustomClass> first =
        prefser.getAndObserve(givenKey, typeToken, defaultCustomObjects).toBlocking().first();

    // then
    assertThat(first).isEqualTo(customObjects);
  }

  @Test public void testObserveArrayOfBooleans() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Boolean[] booleans = { true, false, true };
    Boolean[] defaultBooleans = { false, false, false };

    // when
    RecordingObserver<Boolean[]> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Boolean[].class, defaultBooleans).subscribe(observer);
    prefser.put(givenKey, booleans);

    // then
    assertThat(observer.takeNext()).isEqualTo(booleans);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveArrayOfBooleans() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Boolean[] booleans = { true, false, true };
    Boolean[] defaultBooleans = { false, false, false };

    // when
    prefser.put(givenKey, booleans);
    Boolean[] first =
        prefser.getAndObserve(givenKey, Boolean[].class, defaultBooleans).toBlocking().first();

    // then
    assertThat(first).isEqualTo(booleans);
  }

  @Test public void testObserveArrayOfBooleansPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    boolean[] booleans = { true, false, true };
    boolean[] defaultBooleans = { false, false, false };

    // when
    RecordingObserver<boolean[]> observer = new RecordingObserver<>();
    prefser.observe(givenKey, boolean[].class, defaultBooleans).subscribe(observer);
    prefser.put(givenKey, booleans);

    // then
    assertThat(observer.takeNext()).isEqualTo(booleans);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveArrayOfBooleansPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    boolean[] booleans = { true, false, true };
    boolean[] defaultBooleans = { false, false, false };

    // when
    prefser.put(givenKey, booleans);
    boolean[] first =
        prefser.getAndObserve(givenKey, boolean[].class, defaultBooleans).toBlocking().first();

    // then
    assertThat(first).isEqualTo(booleans);
  }

  @Test public void testObserveArrayOfFloats() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Float[] floats = { 1.1f, 4.5f, 6.8f };
    Float[] defaultFloats = { 1.0f, 1.0f, 1.0f };

    // when
    RecordingObserver<Float[]> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Float[].class, defaultFloats).subscribe(observer);
    prefser.put(givenKey, floats);

    // then
    assertThat(observer.takeNext()).isEqualTo(floats);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveArrayOfFloats() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Float[] floats = { 1.1f, 4.5f, 6.8f };
    Float[] defaultFloats = { 1.0f, 1.0f, 1.0f };

    // when
    prefser.put(givenKey, floats);
    Float[] first =
        prefser.getAndObserve(givenKey, Float[].class, defaultFloats).toBlocking().first();

    // then
    assertThat(first).isEqualTo(floats);
  }

  @Test public void testObserveArrayOfFloatsPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    float[] floats = { 1.1f, 4.5f, 6.8f };
    float[] defaultFloats = { 1.0f, 1.0f, 1.0f };

    // when
    RecordingObserver<float[]> observer = new RecordingObserver<>();
    prefser.observe(givenKey, float[].class, defaultFloats).subscribe(observer);
    prefser.put(givenKey, floats);

    // then
    assertThat(observer.takeNext()).isEqualTo(floats, 0.1f);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveArrayOfFloatsPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    float[] floats = { 1.1f, 4.5f, 6.8f };
    float[] defaultFloats = { 1.0f, 1.0f, 1.0f };

    // when
    prefser.put(givenKey, floats);
    float[] first =
        prefser.getAndObserve(givenKey, float[].class, defaultFloats).toBlocking().first();

    // then
    assertThat(first).isEqualTo(floats, 0.1f);
  }

  @Test public void testObserveArrayOfInts() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Integer[] ints = { 4, 5, 6 };
    Integer[] defaultInts = { 1, 1, 1 };

    // when
    RecordingObserver<Integer[]> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Integer[].class, defaultInts).subscribe(observer);
    prefser.put(givenKey, ints);

    // then
    assertThat(observer.takeNext()).isEqualTo(ints);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveArrayOfInts() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Integer[] ints = { 4, 5, 6 };
    Integer[] defaultInts = { 1, 1, 1 };

    // when
    prefser.put(givenKey, ints);
    Integer[] first =
        prefser.getAndObserve(givenKey, Integer[].class, defaultInts).toBlocking().first();

    // then
    assertThat(first).isEqualTo(ints);
  }

  @Test public void testObserveArrayOfIntsPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    int[] ints = { 4, 5, 6 };
    int[] defaultInts = { 1, 1, 1 };

    // when
    RecordingObserver<int[]> observer = new RecordingObserver<>();
    prefser.observe(givenKey, int[].class, defaultInts).subscribe(observer);
    prefser.put(givenKey, ints);

    // then
    assertThat(observer.takeNext()).isEqualTo(ints);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveArrayOfIntsPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    int[] ints = { 4, 5, 6 };
    int[] defaultInts = { 1, 1, 1 };

    // when
    prefser.put(givenKey, ints);
    int[] first = prefser.getAndObserve(givenKey, int[].class, defaultInts).toBlocking().first();

    // then
    assertThat(first).isEqualTo(ints);
  }

  @Test public void testObserveArrayOfDoubles() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Double[] doubles = { 4.5, 5.6, 6.2 };
    Double[] defaultDoubles = { 1.1, 1.1, 1.1 };

    // when
    RecordingObserver<Double[]> observer = new RecordingObserver<>();
    prefser.observe(givenKey, Double[].class, defaultDoubles).subscribe(observer);
    prefser.put(givenKey, doubles);

    // then
    assertThat(observer.takeNext()).isEqualTo(doubles);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveArrayOfDoubles() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Double[] doubles = { 4.5, 5.6, 6.2 };
    Double[] defaultDoubles = { 1.1, 1.1, 1.1 };

    // when
    prefser.put(givenKey, doubles);
    Double[] first =
        prefser.getAndObserve(givenKey, Double[].class, defaultDoubles).toBlocking().first();

    // then
    assertThat(first).isEqualTo(doubles);
  }

  @Test public void testObserveArrayOfDoublesPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    double[] doubles = { 4.5, 5.6, 6.2 };
    double[] defaultDoubles = { 1.1, 1.1, 1.1 };

    // when
    RecordingObserver<double[]> observer = new RecordingObserver<>();
    prefser.observe(givenKey, double[].class, defaultDoubles).subscribe(observer);
    prefser.put(givenKey, doubles);

    // then
    assertThat(observer.takeNext()).isEqualTo(doubles, 0.1);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveArrayOfDoublesPrimitive() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    double[] doubles = { 4.5, 5.6, 6.2 };
    double[] defaultDoubles = { 1.1, 1.1, 1.1 };

    // when
    prefser.put(givenKey, doubles);
    double[] first =
        prefser.getAndObserve(givenKey, double[].class, defaultDoubles).toBlocking().first();

    // then
    assertThat(first).isEqualTo(doubles, 0.1);
  }

  @Test public void testObserveArrayOfStrings() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    String[] strings = { "hey", "this is", "array" };
    String[] defaultStrings = { "yet another", "default array", "of strings" };

    // when
    RecordingObserver<String[]> observer = new RecordingObserver<>();
    prefser.observe(givenKey, String[].class, defaultStrings).subscribe(observer);
    prefser.put(givenKey, strings);

    // then
    assertThat(observer.takeNext()).isEqualTo(strings);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveArrayOfStrings() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    String[] strings = { "some", "random", "data" };
    String[] defaultStrings = { "this", "will be", "default stuff" };

    // when
    prefser.put(givenKey, strings);
    String[] first =
        prefser.getAndObserve(givenKey, String[].class, defaultStrings).toBlocking().first();

    // then
    assertThat(first).isEqualTo(strings);
  }

  @Test public void testObserveArrayOfCustomObjects() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    CustomClass defaultCustomObject = new CustomClass(0, "this is zero");

    CustomClass[] customClasses = {
        new CustomClass(1, "never"), new CustomClass(2, "gonna"), new CustomClass(3, "give you up")
    };

    CustomClass[] defaultCustomClasses = {
        defaultCustomObject, defaultCustomObject, defaultCustomObject
    };

    // when
    RecordingObserver<CustomClass[]> observer = new RecordingObserver<>();
    prefser.observe(givenKey, CustomClass[].class, defaultCustomClasses).subscribe(observer);
    prefser.put(givenKey, customClasses);

    // then
    assertThat(observer.takeNext()).isEqualTo(customClasses);
    observer.assertNoMoreEvents();
  }

  @Test public void testGetAndObserveArrayOfCustomObjects() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    CustomClass defaultCustomObject = new CustomClass(0, "zero value");

    CustomClass[] customClasses = {
        new CustomClass(1, "never gonna"), new CustomClass(2, "let you"), new CustomClass(3, "down")
    };

    CustomClass[] defaultCustomClasses = {
        defaultCustomObject, defaultCustomObject, defaultCustomObject
    };

    // when
    prefser.put(givenKey, customClasses);
    CustomClass[] first = prefser.getAndObserve(givenKey, CustomClass[].class, defaultCustomClasses)
        .toBlocking()
        .first();

    // then
    assertThat(first).isEqualTo(customClasses);
  }

  @Test public void testObservePreferencesOnPut() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    String givenValue = GIVEN_STRING_VALUE;

    // when
    RecordingObserver<String> observer = new RecordingObserver<>();
    prefser.observePreferences().subscribe(observer);
    prefser.put(givenKey, givenValue);

    // then
    assertThat(observer.takeNext()).isEqualTo(givenKey);
    observer.assertNoMoreEvents();
  }

  @Test public void testObservePreferencesOnUpdate() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    String givenValue = GIVEN_STRING_VALUE;
    String anotherGivenValue = "anotherGivenValue";
    prefser.put(givenKey, givenValue);

    // when
    RecordingObserver<String> observer = new RecordingObserver<>();
    prefser.observePreferences().subscribe(observer);
    prefser.put(givenKey, anotherGivenValue);

    // then
    assertThat(observer.takeNext()).isEqualTo(givenKey);
    observer.assertNoMoreEvents();
  }

  @Test public void testObservePreferencesOnRemove() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    String givenValue = GIVEN_STRING_VALUE;
    prefser.put(givenKey, givenValue);

    // when
    RecordingObserver<String> observer = new RecordingObserver<>();
    prefser.observePreferences().subscribe(observer);
    prefser.remove(givenKey);

    // then
    assertThat(observer.takeNext()).isEqualTo(givenKey);
    observer.assertNoMoreEvents();
  }

  @Test public void testObservePreferencesTwice() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    String givenValue = GIVEN_STRING_VALUE;
    String anotherGivenValue = "anotherGivenValue";
    String yetAnotherGivenValue = "yetAnotherGivenValue";
    prefser.put(givenKey, givenValue);

    // when 1
    RecordingObserver<String> observer1 = new RecordingObserver<>();
    RecordingObserver<String> observer2 = new RecordingObserver<>();
    Observable<String> preferencesObservable = prefser.observePreferences();
    Subscription subscription1 = preferencesObservable.subscribe(observer1);
    preferencesObservable.subscribe(observer2);
    prefser.put(givenKey, anotherGivenValue);

    // then 1
    assertThat(observer1.takeNext()).isEqualTo(givenKey);
    assertThat(observer2.takeNext()).isEqualTo(givenKey);

    // when 2
    subscription1.unsubscribe();
    prefser.put(givenKey, yetAnotherGivenValue);

    // then 2
    observer1.assertNoMoreEvents();
    assertThat(observer2.takeNext()).isEqualTo(givenKey);
    observer2.assertNoMoreEvents();
  }
}
