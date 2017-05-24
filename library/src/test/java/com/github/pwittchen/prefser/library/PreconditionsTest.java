package com.github.pwittchen.prefser.library;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class) @Config(constants = BuildConfig.class)
public class PreconditionsTest {

  @Test public void shouldNotThrowAnyExceptionWhenObjectIsNotNull() {
    // given
    Object object = new Object();

    // when
    try {
      Preconditions.checkNotNull(object, "object == null");
    } catch (Exception e) {
      fail(e.getMessage());
    }

    // then no exception is thrown
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowAnExceptionWhenObjectIsNull() {
    // given
    Object object = null;

    // when
    Preconditions.checkNotNull(object, "object == null");

    // then an exception is thrown
  }

  @Test(expected = IllegalAccessException.class)
  public void constructorShouldBePrivate() throws Exception {
    Preconditions.class.newInstance();
  }
}
