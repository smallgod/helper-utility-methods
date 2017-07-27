/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import com.library.customexception.MyCustomException;
import com.library.datamodel.Constants.HTTPMethod;
import com.library.datamodel.Constants.NamedConstants;
import com.library.httpconnmanager.HttpClientPool;
import com.library.sglogger.util.LoggerUtil;
import java.util.HashMap;
import java.util.Map;
import org.openide.util.MapFormat;

/**
 *
 * @author smallgod
 */
public class SMSSenderUtils {

    private static final LoggerUtil logger = new LoggerUtil(SMSSenderUtils.class);

    /**
     * Send a text message to the avertXpo admin alerting them of this new
     * campaign created
     *
     * @param createTime
     * @param campaignCost
     * @param momoAccount
     * @param recipientNumber
     * @param clientPool
     * @return
     * @throws MyCustomException
     */
    public static String generateAndSendNewCampaignMsg(String createTime, int campaignCost, String momoAccount, String recipientNumber, final HttpClientPool clientPool) throws MyCustomException {

        String smsText = getNewCampaignAdminMessage(createTime, campaignCost, momoAccount);

        logger.debug("###### Message to client: " + smsText + " #######");

        Map<String, Object> paramPairs = GeneralUtils.prepareTextMsgParams(smsText, recipientNumber);

        //for now use hardcoded stuff in constants, but we have an externalunits entry in the xml that we can use for SMS server
        //configs and mobile money configs as well
        //String smsSendResponse = GeneralUtils.sendSMS(paramPairs);
        String smsSendResponse = clientPool.sendRemoteRequest("", NamedConstants.SMS_API_URL, paramPairs, HTTPMethod.GET);

        logger.info("Response from SMS web API Server: " + smsSendResponse);

        return smsSendResponse;

    }

    /**
     * create new campaign message to alert advertXpo admin
     *
     * @param createTime
     * @param campaignCost
     * @param momoAccount
     * @return
     */
    public static String getNewCampaignAdminMessage(String createTime, int campaignCost, String momoAccount) {

        //Object[] params = {"nameRobert", "rhume55@gmail.com"};
        Map<String, String> map = new HashMap<>();

        map.put("createTime", createTime);
        map.put("campaignCost", "" + campaignCost);
        map.put("momoAccount", momoAccount);

        String message = MapFormat.format(NamedConstants.SMS_TEMPLATE_NEW_CAMPAIGN_ADMIN, map);
        logger.debug("message : " + message);

        return message;
    }

    /**
     * Send a text message to the avertXpo admin alerting them of this campaign
     * escalation
     *
     * @param id
     * @param momoAccount
     * @param recipientNumber
     * @param clientPool
     * @return
     * @throws MyCustomException
     */
    public static String generateAndSendCampaignEscalateMsg(int id, String momoAccount, String recipientNumber, final HttpClientPool clientPool) throws MyCustomException {

        String smsText = getCampaignEscalateAdminMessage(id, momoAccount);

        logger.debug("###### Message to client: " + smsText + " #######");

        Map<String, Object> paramPairs = GeneralUtils.prepareTextMsgParams(smsText, recipientNumber);

        //for now use hardcoded stuff in constants, but we have an externalunits entry in the xml that we can use for SMS server
        //configs and mobile money configs as well
        //String smsSendResponse = GeneralUtils.sendSMS(paramPairs);
        String smsSendResponse = clientPool.sendRemoteRequest("", NamedConstants.SMS_API_URL, paramPairs, HTTPMethod.GET);

        logger.info("Response from SMS web API Server: " + smsSendResponse);

        return smsSendResponse;

    }

    /**
     * Create SMS to send to admin upon escalation of campaign
     *
     * @param id
     * @param momoAccount
     * @return
     */
    public static String getCampaignEscalateAdminMessage(int id, String momoAccount) {

        //Object[] params = {"nameRobert", "rhume55@gmail.com"};
        Map<String, String> map = new HashMap<>();

        map.put("id", "" + id);
        map.put("momoAccount", momoAccount);

        String message = MapFormat.format(NamedConstants.SMS_TEMPLATE_CAMPAIGN_ESCALATE_ADMIN, map);
        logger.debug("message : " + message);

        return message;
    }

    /**
     * Send a text message to the avertXpo admin alerting them of campaign that
     * needs review
     *
     * @param id
     * @param momoAccount
     * @param recipientNumber
     * @param clientPool
     * @return
     * @throws MyCustomException
     */
    public static String generateAndSendCampaignReviewAdminMsg(int id, String momoAccount, String recipientNumber, final HttpClientPool clientPool) throws MyCustomException {

        String smsText = getCampaignReviewAdminMessage(id, momoAccount);

        logger.debug("###### Message to client: " + smsText + " #######");

        Map<String, Object> paramPairs = GeneralUtils.prepareTextMsgParams(smsText, recipientNumber);

        //for now use hardcoded stuff in constants, but we have an externalunits entry in the xml that we can use for SMS server
        //configs and mobile money configs as well
        //String smsSendResponse = GeneralUtils.sendSMS(paramPairs);
        String smsSendResponse = clientPool.sendRemoteRequest("", NamedConstants.SMS_API_URL, paramPairs, HTTPMethod.GET);

        logger.info("Response from SMS web API Server: " + smsSendResponse);

        return smsSendResponse;

    }

    /**
     * Create SMS to send to admin for review of campaign
     *
     * @param id
     * @param momoAccount
     * @return
     */
    public static String getCampaignReviewAdminMessage(int id, String momoAccount) {

        //Object[] params = {"nameRobert", "rhume55@gmail.com"};
        Map<String, String> map = new HashMap<>();

        map.put("id", "" + id);
        map.put("momoAccount", momoAccount);

        String message = MapFormat.format(NamedConstants.SMS_TEMPLATE_CAMPAIGN_REVIEW_ADMIN, map);
        logger.debug("message : " + message);

        return message;
    }

}
