package com.github.pwittchen.prefser.library.rx2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class) @Config(manifest = Config.NONE)
public class PreconditionsTest {

  @Test public void shouldCreatePreconditionsObject() {
    // when
    Preconditions preconditions = Preconditions.create();

    // then
    assertThat(preconditions).isNotNull();
  }

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

  @Test(expected = IllegalAccessException.class) public void constructorShouldBePrivate()
      throws Exception {
    Preconditions.class.newInstance();
  }
}
