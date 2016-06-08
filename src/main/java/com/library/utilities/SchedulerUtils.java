/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import com.library.scheduler.CustomSharedScheduler;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smallgod
 */
public final class SchedulerUtils {

    private static final CustomSharedScheduler customScheduler = CustomSharedScheduler.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(SchedulerUtils.class);

    public static boolean isJobTriggerPaused(String triggerName) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName);
        Trigger.TriggerState triggerState = customScheduler.getScheduler().getTriggerState(triggerKey);

        boolean isPaused = Boolean.FALSE;

        switch (triggerState) {

            case PAUSED:
                isPaused = Boolean.TRUE;
                break;

            case NORMAL:
                isPaused = Boolean.FALSE;
                break;

            default:
                throw new SchedulerException("Trigger is neither in PAUSED nor NORMAL state: " + triggerState.toString());
        }

        return isPaused;
    }

    /**
     * Pause a given job
     *
     * @param jobName
     * @param groupName
     * @return
     */
    public static boolean pauseAJob(String jobName, String groupName) {

        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        boolean isJobPaused = Boolean.TRUE;

        try {

            customScheduler.getScheduler().pauseJob(jobKey);

        } catch (SchedulerException ex) {
            logger.error("Error pausing a job: " + ex.getMessage());
            isJobPaused = Boolean.FALSE;
        }
        return isJobPaused;

    }
}
