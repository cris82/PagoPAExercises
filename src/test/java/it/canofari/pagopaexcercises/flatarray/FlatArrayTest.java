package it.canofari.pagopaexcercises.flatarray;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for flatten an array of arbitrarily nested arrays of integers
 */
public class FlatArrayTest {

    Integer[] expectedArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @Test
    public void testNullReturnsNull() throws IllegalArgumentException {
        Assert.assertNull(
                "Testing a null argument",
                FlatArray.flattenIntegerArray(null)
        );
    }

    @Test
    public void testEmptyArray() throws IllegalArgumentException {
        Assert.assertArrayEquals(
                "Testing an empty array",
                new Integer[]{},
                FlatArray.flattenIntegerArray(new Object[]{})
        );
    }

    @Test
    public void testFlatArray() throws IllegalArgumentException {
        Assert.assertArrayEquals(
                "Testing a flat array",
                expectedArray,
                FlatArray.flattenIntegerArray(new Object[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
        );
    }

    @Test
    public void testNestedArray() throws IllegalArgumentException {
        Assert.assertArrayEquals(
                "Testing nested array",
                expectedArray,
                FlatArray.flattenIntegerArray(new Object[]{1, 2, 3, 4, new Object[]{5, 6, 7, 8}, 9, 10})
        );
    }

    @Test
    public void testMultipleNestedArrays() throws IllegalArgumentException {
        Assert.assertArrayEquals(
                "Testing multiple nested arrays",
                expectedArray,
                FlatArray.flattenIntegerArray(new Object[]{1, 2, new Object[]{3, 4, new Object[]{5}, 6, 7}, 8, 9, 10})
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionForObjectInArray() throws IllegalArgumentException {
        FlatArray.flattenIntegerArray(
                new Object[]{new Object()}
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionForObjectInNestedArray() throws IllegalArgumentException {
        FlatArray.flattenIntegerArray(
                new Object[]{1, 2, new Object[]{3, new Object()}}
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionForNullInArray() throws IllegalArgumentException {
        FlatArray.flattenIntegerArray(
                new Object[]{null}
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionForNullInNestedArray() throws IllegalArgumentException {
        FlatArray.flattenIntegerArray(
                new Object[]{1, 2, new Object[]{3, null}}
        );
    }
}

