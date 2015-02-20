/*
 * Copyright (C) 2015 Piotr Wittchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pwittchen.prefser.library;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

public class PrefserTest extends AndroidTestCase {

    private Prefser prefser;

    @Override
    public void setUp() {
        prefser = new Prefser(getContext());
    }

    @Override
    public void tearDown() {
        prefser.clear();
    }

    public void testContains() throws Exception {
        // given
        String givenValue = "sample value";
        String givenKey = "sampleKey";

        // when
        prefser.put(givenKey, givenValue);

        // then
        assertTrue(prefser.contains(givenKey));
        prefser.remove(givenKey);
        assertFalse(prefser.contains(givenKey));
    }

    public void testSize() throws Exception {
        // given
        prefser.clear();

        // when
        prefser.put("key1", 1);
        prefser.put("key2", 2);
        prefser.put("key3", 3);

        // then
        assertEquals(prefser.size(), 3);
        prefser.remove("key1");
        assertEquals(prefser.size(), 2);
        prefser.clear();
        assertEquals(prefser.size(), 0);
    }

    public void testRemove() throws Exception{
        // given
        prefser.put("key1", 1);

        // when
        assertTrue(prefser.contains("key1"));
        prefser.remove("key1");

        // then
        assertFalse(prefser.contains("key1"));

    }

    public void testClear() throws Exception{
        // given
        prefser.put("key1", 1);
        prefser.put("key2", 2);
        prefser.put("key3", 3);

        // when
        assertEquals(prefser.size(), 3);
        prefser.clear();

        // then
        assertEquals(prefser.size(), 0);
    }

    public void testPutBoolean() throws Exception {
        // given
        String givenKey = "sampleKey";
        boolean givenValue = true;

        // when
        prefser.put(givenKey, givenValue);

        // then
        boolean readValue = prefser.get(givenKey, Boolean.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutFloat() throws Exception {
        // given
        String givenKey = "sampleKey";
        float givenValue = 41;

        // when
        prefser.put(givenKey, givenValue);

        // then
        float readValue = prefser.get(givenKey, Float.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutInt() throws Exception {
        // given
        String givenKey = "sampleKey";
        int givenValue = 42;

        // when
        prefser.put(givenKey, givenValue);

        // then
        int readValue = prefser.get(givenKey, Integer.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutLong() throws Exception {
        // given
        String givenKey = "sampleKey";
        long givenValue = 43;

        // when
        prefser.put(givenKey, givenValue);

        // then
        long readValue = prefser.get(givenKey, Long.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutDouble() throws Exception {
        // given
        String givenKey = "sampleKey";
        double givenValue = 44.5;

        // when
        prefser.put(givenKey, givenValue);

        // then
        double readValue = prefser.get(givenKey, Double.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutString() throws Exception {
        // given
        String givenKey = "sampleKey";
        String givenValue = "sampleValueExplicit";

        // when
        prefser.put(givenKey, givenValue);

        // then
        String readValue = prefser.get(givenKey, String.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    private class CustomClass {
        private int valueOne;
        private String valueTwo;

        public CustomClass(int valueOne, String valueTwo) {
            this.valueOne = valueOne;
            this.valueTwo = valueTwo;
        }

        @Override
        public boolean equals(Object other) {
            boolean isEqual = false;

            if (other instanceof CustomClass) {
                isEqual = ((CustomClass) other).valueOne == valueOne;
                isEqual |= ((CustomClass) other).valueTwo.equals(valueTwo);
            }

            return isEqual;
        }
    }

    public void testPutCustomObject() throws Exception {
        // given
        String givenKey = "sampleKey";
        CustomClass givenObject = new CustomClass(23, "someText");

        // when
        prefser.put(givenKey, givenObject);

        // then
        CustomClass readObject = prefser.get(givenKey, CustomClass.class);
        assertTrue(readObject.equals(givenObject));
        prefser.remove(givenKey);
    }

    public void testPutListOfBooleans() throws Exception {
        // given
        String givenKey = "sampleKey";
        List<Boolean> booleans = new ArrayList<>();
        booleans.add(true);
        booleans.add(false);
        booleans.add(true);

        // when
        prefser.put(givenKey, booleans);

        // then
        List<Boolean> readObject = prefser.get(givenKey, List.class);

        assertEquals(readObject.get(0), booleans.get(0));
        assertEquals(readObject.get(1), booleans.get(1));
        assertEquals(readObject.get(2), booleans.get(2));

        prefser.remove(givenKey);
    }

    public void testPutListOfFloats() throws Exception {
        // given
        String givenKey = "sampleKey";
        List<Float> floats = new ArrayList<>();
        floats.add(4f);
        floats.add(5f);
        floats.add(6f);

        // when
        prefser.put(givenKey, floats);

        // then
        // if we read list of different type of number than double,
        // we should declare List<Number> due to implementation of Gson
        List<Number> readObject = prefser.get(givenKey, List.class);

        Double expectedNumberFirst = readObject.get(0).doubleValue();
        Double expectedNumberSecond = readObject.get(1).doubleValue();
        Double expectedNumberThird = readObject.get(2).doubleValue();

        assertEquals(expectedNumberFirst.floatValue(), floats.get(0).floatValue());
        assertEquals(expectedNumberSecond.floatValue(), floats.get(1).floatValue());
        assertEquals(expectedNumberThird.floatValue(), floats.get(2).floatValue());

        prefser.remove(givenKey);
    }

    public void testPutListOfInts() throws Exception {
        // given
        String givenKey = "sampleKey";
        List<Integer> ints = new ArrayList<>();
        ints.add(4);
        ints.add(5);
        ints.add(6);

        // when
        prefser.put(givenKey, ints);

        // then
        // if we read list of different type of number than double,
        // we should declare List<Number> due to implementation of Gson
        List<Number> readObject = prefser.get(givenKey, List.class);

        Double expectedNumberFirst = readObject.get(0).doubleValue();
        Double expectedNumberSecond = readObject.get(1).doubleValue();
        Double expectedNumberThird = readObject.get(2).doubleValue();

        assertEquals(expectedNumberFirst.intValue(), ints.get(0).intValue());
        assertEquals(expectedNumberSecond.intValue(), ints.get(1).intValue());
        assertEquals(expectedNumberThird.intValue(), ints.get(2).intValue());

        prefser.remove(givenKey);
    }

    public void testPutListOfLongs() throws Exception {
        // given
        String givenKey = "sampleKey";
        List<Long> ints = new ArrayList<>();
        ints.add(4l);
        ints.add(5l);
        ints.add(6l);

        // when
        prefser.put(givenKey, ints);

        // then
        // if we read list of different type of number than double,
        // we should declare List<Number> due to implementation of Gson
        List<Number> readObject = prefser.get(givenKey, List.class);

        Double expectedNumberFirst = readObject.get(0).doubleValue();
        Double expectedNumberSecond = readObject.get(1).doubleValue();
        Double expectedNumberThird = readObject.get(2).doubleValue();

        assertEquals(expectedNumberFirst.longValue(), ints.get(0).longValue());
        assertEquals(expectedNumberSecond.longValue(), ints.get(1).longValue());
        assertEquals(expectedNumberThird.longValue(), ints.get(2).longValue());

        prefser.remove(givenKey);
    }

    public void testPutListOfDoubles() throws Exception {
        // given
        String givenKey = "sampleKey";
        List<Double> doubles = new ArrayList<>();
        doubles.add(4.0);
        doubles.add(5.1);
        doubles.add(6.2);

        // when
        prefser.put(givenKey, doubles);

        // then
        List<Double> readObject = prefser.get(givenKey, List.class);

        assertEquals(readObject.get(0), doubles.get(0));
        assertEquals(readObject.get(1), doubles.get(1));
        assertEquals(readObject.get(2), doubles.get(2));

        prefser.remove(givenKey);
    }

    public void testPutListOfStrings() throws Exception {
        // given
        String givenKey = "sampleKey";
        List<String> strings = new ArrayList<>();
        strings.add("one");
        strings.add("two");
        strings.add("three");

        // when
        prefser.put(givenKey, strings);

        // then
        List<String> readObject = prefser.get(givenKey, List.class);
        assertEquals(readObject.get(0), strings.get(0));
        assertEquals(readObject.get(1), strings.get(1));
        assertEquals(readObject.get(2), strings.get(2));

        prefser.remove(givenKey);
    }

    public void testPutArrayOfBooleans() {
        // given
        String givenKey = "sampleKey";
        Boolean[] array = new Boolean[3];
        array[0] = true;
        array[1] = false;
        array[2] = true;

        // when
        prefser.put(givenKey, array);

        // then
        Boolean[] readObject = prefser.get(givenKey, Boolean[].class);
        assertEquals(readObject[0], array[0]);
        assertEquals(readObject[1], array[1]);
        assertEquals(readObject[2], array[2]);

        prefser.remove(givenKey);
    }

    public void testPutArrayOfFloats() {
        // given
        String givenKey = "sampleKey";
        Float[] array = new Float[3];
        array[0] = 1f;
        array[1] = 2f;
        array[2] = 3f;

        // when
        prefser.put(givenKey, array);

        // then
        Float[] readObject = prefser.get(givenKey, Float[].class);
        assertEquals(readObject[0], array[0]);
        assertEquals(readObject[1], array[1]);
        assertEquals(readObject[2], array[2]);

        prefser.remove(givenKey);
    }

    public void testPutArrayOfInts() {
        // given
        String givenKey = "sampleKey";
        Integer[] array = new Integer[3];
        array[0] = 1;
        array[1] = 2;
        array[2] = 3;

        // when
        prefser.put(givenKey, array);

        // then
        Integer[] readObject = prefser.get(givenKey, Integer[].class);
        assertEquals(readObject[0], array[0]);
        assertEquals(readObject[1], array[1]);
        assertEquals(readObject[2], array[2]);

        prefser.remove(givenKey);
    }

    public void testPutArrayOfLongs() {
        // given
        String givenKey = "sampleKey";
        Long[] array = new Long[3];
        array[0] = 1l;
        array[1] = 2l;
        array[2] = 3l;

        // when
        prefser.put(givenKey, array);

        // then
        Long[] readObject = prefser.get(givenKey, Long[].class);
        assertEquals(readObject[0], array[0]);
        assertEquals(readObject[1], array[1]);
        assertEquals(readObject[2], array[2]);

        prefser.remove(givenKey);
    }

    public void testPutArrayOfDoubles() {
        // given
        String givenKey = "sampleKey";
        Double[] array = new Double[3];
        array[0] = 1.0;
        array[1] = 2.3;
        array[2] = 4.5;

        // when
        prefser.put(givenKey, array);

        // then
        Double[] readObject = prefser.get(givenKey, Double[].class);
        assertEquals(readObject[0], array[0]);
        assertEquals(readObject[1], array[1]);
        assertEquals(readObject[2], array[2]);

        prefser.remove(givenKey);
    }

    public void testPutArrayOfStrings() {
        // given
        String givenKey = "sampleKey";
        String[] array = new String[3];
        array[0] = "one";
        array[1] = "two";
        array[2] = "three";

        // when
        prefser.put(givenKey, array);

        // then
        String[] readObject = prefser.get(givenKey, String[].class);
        assertEquals(readObject[0], array[0]);
        assertEquals(readObject[1], array[1]);
        assertEquals(readObject[2], array[2]);

        prefser.remove(givenKey);
    }

    public void testPutArrayOfCustomObjects() {
        // given
        String givenKey = "sampleKey";
        CustomClass[] customClassesArray = new CustomClass[3];
        customClassesArray[0] = new CustomClass(1, "one");
        customClassesArray[1] = new CustomClass(2, "two");
        customClassesArray[2] = new CustomClass(3, "three");

        // when
        prefser.put(givenKey, customClassesArray);

        // then
        CustomClass[] readObject = prefser.get(givenKey, CustomClass[].class);
        assertEquals(readObject[0], customClassesArray[0]);
        assertEquals(readObject[1], customClassesArray[1]);
        assertEquals(readObject[2], customClassesArray[2]);

        prefser.remove(givenKey);
    }
}