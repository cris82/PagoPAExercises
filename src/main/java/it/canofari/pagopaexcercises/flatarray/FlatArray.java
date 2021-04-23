package it.canofari.pagopaexcercises.flatarray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlatArray {



    // a nested array of Integer is an array of Objects
    public static Integer[] flattenIntegerArray(Object[] arrayToFlat) {

        List<Integer> flattenedList = new ArrayList<>();

        if(arrayToFlat==null) return null; // if an array is null then is already flat

        for (Object element : arrayToFlat) {
            if (element instanceof Integer) { // in case the element in the array is an Integer I will add it to the list of Integers --> base case
                flattenedList.add((Integer)element);
            }

            else if (element instanceof Object[]) {  //In case an element in the array is still an array, the flattenIntegerArray method is invoked --> recursive case
                flattenedList.addAll(Arrays.asList(flattenIntegerArray((Object[]) element)));
            }

            else {
                throw new IllegalArgumentException("Input must be an array of Integers or nested arrays of Integers");
            }
        }
        return flattenedList.toArray(flattenedList.toArray(new Integer[0]));

    }


}