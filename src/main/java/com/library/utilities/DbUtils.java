/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import com.library.datamodel.Constants.EntityName;
import com.library.datamodel.model.v1_0.AdClient;
import com.library.datamodel.model.v1_0.AdMonitor;
import com.library.datamodel.model.v1_0.AdPayment;
import com.library.datamodel.model.v1_0.AdProgram;
import com.library.datamodel.model.v1_0.AdResource;
import com.library.datamodel.model.v1_0.AdSchedule;
import com.library.datamodel.model.v1_0.AdScreen;
import com.library.datamodel.model.v1_0.AdScreenArea;
import com.library.datamodel.model.v1_0.AdScreenOwner;
import com.library.datamodel.model.v1_0.AdTerminal;
import com.library.datamodel.model.v1_0.AdText;
import com.library.datamodel.model.v1_0.AudienceType;
import com.library.datamodel.model.v1_0.Author;
import com.library.datamodel.model.v1_0.Book;
import com.library.datamodel.model.v1_0.BusinessType;
import com.library.datamodel.model.v1_0.TimeSlot;
import java.util.Date;

public final class DbUtils {

    private static final LoggerUtil logger = new LoggerUtil(DbUtils.class);

    public static Integer ZeroToNull(int value) {
        return value == 0 ? null : value;
    }

    public static long ZeroToNull(long value) {
        return (value == 0L ? null : value);
    }

    public static String EmptyToNull(String value) {
        return (value == null) || (value.equals("")) ? null : value;
    }

    public static Date NullTo1970(Date date) {
        return date == null ? new Date(0L) : date;
    }

    /**
     * Get the entity type
     *
     * @param entity
     * @return
     */
    public static Class getEntityClass(EntityName entity) {

        Class entityType = null;

        switch (entity) {

            case AD_AREA:
                entityType = AdScreenArea.class;
                break;

            case AD_OWNER:
                entityType = AdClient.class;
                break;

            case AD_PAYMENT:
                entityType = AdPayment.class;
                break;

            case AD_PROGRAM:

                entityType = AdProgram.class;
                break;

            case AD_RESOURCE:
                entityType = AdResource.class;
                break;

            case AD_SCHEDULE:
                entityType = AdSchedule.class;
                break;

            case AD_SCREEN:
                entityType = AdScreen.class;
                break;

            case AD_SCREENOWNER:
                entityType = AdScreenOwner.class;
                break;

            case AD_CLIENT:
                entityType = AdClient.class;
                break;

            case AD_MONITOR:
                entityType = AdMonitor.class;
                break;

            case AD_TERMINAL:
                entityType = AdTerminal.class;
                break;

            case AUDIENCE_TYPE:
                entityType = AudienceType.class;
                break;

            case LOCATION_TYPE:
                entityType = BusinessType.class;
                break;

            case TIME_SLOT:
                entityType = TimeSlot.class;
                break;

            case AD_TEXT:
                entityType = AdText.class;
                break;

            case AUTHOR:
                entityType = Author.class;
                break;

            case BOOK:
                entityType = Book.class;
                break;

            default:
                logger.warn("Entity Type: " + entityType + " NOT found!!");
                break;
        }

        return entityType;
    }
}
