package com.library.utilities;

import com.library.datamodel.model.v1_0.AdTimeSlot;
import java.util.Comparator;

/**
 *
 * @author smallgod
 */
public class AmountComparator implements Comparator<AdTimeSlot> {

    // I don't know why this isn't in Long...
    private static int compare(long a, long b) {
        return a < b ? -1 : a > b ? 1 : 0;
    }

    @Override
    public int compare(AdTimeSlot o1, AdTimeSlot o2) {

        // the value 0 if this Integer is equal to the argument Integer; 
        //a value less than 0 if this Integer is numerically less than the argument Integer; 
        //and a value greater than 0 if this Integer is numerically greater than the argument Integer (signed comparison).
        //int startComparison = compare(x.timeStarted, y.timeStarted);
        //return startComparison != 0 ? startComparison : compare(x.timeEnded, y.timeEnded);
        if (o1 == null || o2 == null || (!(o1.getSlotAdPrice().getCurrencycode().equalsIgnoreCase(o2.getSlotAdPrice().getCurrencycode())))) {
            return 3;
        }

        Integer o1Amount = o1.getSlotAdPrice().getAmount();
        Integer o2Amount = o2.getSlotAdPrice().getAmount();
        
        //consider adding in the discount thing..

        return (o1Amount.compareTo(o2Amount));

    }

}
