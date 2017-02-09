package com.library.utilities;

import java.util.Comparator;

/**
 *
 * @author smallgod
 */
public class IntegerComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {

        if (o1 == null || o2 == null) {

            return 3;
           
        }

        // the value 0 if this Integer is equal to the argument Integer; 
        //a value less than 0 if this Integer is numerically less than the argument Integer; 
        //and a value greater than 0 if this Integer is numerically greater than the argument Integer (signed comparison).
        return (o1.compareTo(o2));

        //int startComparison = compare(x.timeStarted, y.timeStarted);
        //return startComparison != 0 ? startComparison : compare(x.timeEnded, y.timeEnded);
    }

    // I don't know why this isn't in Long...
    private static int compare(long a, long b) {
        return a < b ? -1 : a > b ? 1 : 0;
    }

}
