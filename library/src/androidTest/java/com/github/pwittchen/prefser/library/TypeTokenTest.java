package com.github.pwittchen.prefser.library;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
final public class TypeTokenTest {

    @Test(expected = RuntimeException.class)
    public void testShouldThrowRuntimeExceptionWithMissingTypeParameter() {
        TypeToken typeTokenWithMissingType = new TypeToken() {
        };
    }

    @Test(expected = NullPointerException.class)
    public void testShouldThrowNullPointerExceptionForNullClassOfTInFromClassMethod() {
        TypeToken.fromClass(null);
    }

    @Test(expected = NullPointerException.class)
    public void testShouldThrowNullPointerExceptionForNullClassOfTInFromValueMethod() {
        TypeToken.fromValue(null);
    }

    @Test
    public void testShouldReturnTypeWhenItIsGivenWhileObjectCreation() {
        // given
        TypeToken<Boolean> typeToken = new TypeToken<Boolean>() {
        };

        // when
        Type type = typeToken.getType();

        // then
        assertThat(type).isEqualTo(Boolean.class);
    }
}
