/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import com.library.customexception.MyCustomException;
import com.library.datamodel.Constants.ErrorCode;
import com.library.datamodel.Constants.NamedConstants;
import com.library.datamodel.Json.TimeSlot;
import com.library.datamodel.model.v1_0.AdProgramSlot;
import com.library.sglogger.util.LoggerUtil;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;
import org.joda.time.LocalDate;

/**
 *
 * @author smallgod
 */
public class CampaignUtilities {

    private static final LoggerUtil logger = new LoggerUtil(CampaignUtilities.class);

    /**
     *
     * @param adProgSlot
     * @return
     * @throws com.library.customexception.MyCustomException
     */
    public static Set<TimeSlot> getProgSlotsHelper(Set<AdProgramSlot> adProgSlot) throws MyCustomException {

        Set<TimeSlot> timeSlots = new HashSet<>();

        if (null == adProgSlot || adProgSlot.isEmpty()) {
//            MyCustomException error = GeneralUtils.getSingleError(ErrorCode.PROCESSING_ERR, NamedConstants.GENERIC_DB_ERR_DESC, "NO AdProgramSlots found!");
//            throw error;
            
            return timeSlots;
        }

        for (AdProgramSlot slot : adProgSlot) {

            TimeSlot timeSlot = new TimeSlot();
            Set<Integer> days = new HashSet<>();

            int frequence = resetFreq(slot.getFrequency());

            timeSlot.setName(slot.getAdTimeSlot().getTimeSlotCode());
            timeSlot.setFrequency(frequence);
            timeSlot.setPreferredHour(slot.getPreferredHour());

            if (slot.isIsMonday()) {
                days.add(DayOfWeek.MONDAY.getValue());
            }
            if (slot.isIsTuesday()) {
                days.add(DayOfWeek.TUESDAY.getValue());
            }
            if (slot.isIsWednesday()) {
                days.add(DayOfWeek.WEDNESDAY.getValue());
            }
            if (slot.isIsThursady()) {
                days.add(DayOfWeek.THURSDAY.getValue());
            }
            if (slot.isIsFriday()) {
                days.add(DayOfWeek.FRIDAY.getValue());
            }
            if (slot.isIsSaturday()) {
                days.add(DayOfWeek.SATURDAY.getValue());
            }
            if (slot.isIsSunday()) {
                days.add(DayOfWeek.SUNDAY.getValue());
            }

            timeSlot.setDays(days);

            timeSlots.add(timeSlot);
        }

        return timeSlots;
    }

    /**
     * If frequency is lessOrEqual to zero(0) Reset it to one(1)
     *
     * @param frequency
     * @return
     */
    public static int resetFreq(int frequency) {

        int newFrequency = frequency;

        if (newFrequency <= 0) { //reset frequence to 1
            newFrequency = 1;
        }
        return newFrequency;
    }

    /**
     *
     * @param dateOfAd
     * @param allowedAdDays
     * @return
     */
    public static boolean canAdvertiseOnThisDay(LocalDate dateOfAd, Set<Integer> allowedAdDays) {

        int dayOfWeek = dateOfAd.getDayOfWeek();

//        if (!(allowedAdDays == null || allowedAdDays.isEmpty()) && allowedAdDays.contains(dayOfWeek)) {
//            return Boolean.TRUE;
//        }
        if (allowedAdDays == null || allowedAdDays.isEmpty() || allowedAdDays.contains(dayOfWeek)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * Is this slot in the past of time now ?
     *
     * @param millisOfDayNow
     * @param slotStartTime
     * @param slotEndTime
     * @param dateOfAd
     * @return
     */
    public static boolean isSlotInThePast(int millisOfDayNow, int slotStartTime, int slotEndTime, LocalDate dateOfAd) {

        LocalDate dateToday = DateUtils.getDateNow();

        //if time now is lessOrEqual to slot start time and date is today, schedule this slot OR
        //if dateOfAd is in the future, schedule this slot as well
        //if time now of advert is greater than slot start time but within slot time and it is today or tomorrow - we are still within slot
        if (dateOfAd.isAfter(dateToday)) {
            logger.debug("As long as the date is in the future, schedule on any time slot");
            return Boolean.FALSE;

        } else if (dateOfAd.isBefore(dateToday)) {
            logger.debug("As long as the date is in the past, we can't schedule on any time slot");
            return Boolean.TRUE;

        } else if (millisOfDayNow <= slotStartTime) {
            logger.debug("Slot is in the future of time now!");
            return Boolean.FALSE;

        } else if (millisOfDayNow > slotStartTime) {
            //millisOfDay now can only be greater than slot starttime iff it is before midnight

            if (millisOfDayNow < slotEndTime) {
                logger.debug("Time now is within slot schedule start & end time");
                return Boolean.FALSE;

            } else if (millisOfDayNow >= slotEndTime && slotStartTime > slotEndTime) {
                logger.debug("Slot is across midnight i.e. starts b4 midnight and ends after midnight!");
                return Boolean.FALSE;

            }
        }
        logger.warn("Can't schedule this slot, slot time for the day has ended");
        return Boolean.TRUE;

    }

}
