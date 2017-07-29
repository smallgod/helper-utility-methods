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
     * create new campaign message to alert advertXpo admin Send a text message
     * to the avertXpo admin alerting them of this new campaign created
     *
     * @param createTime
     * @param campaignCost
     * @param momoAccount
     * @param recipientAccount
     * @param clientPool
     * @return
     * @throws com.library.customexception.MyCustomException
     */
    public static String generateAndSendNewCampaignMsg(String createTime, int campaignCost, String momoAccount, String recipientAccount, final HttpClientPool clientPool) throws MyCustomException {

        Map<String, String> map = new HashMap<>();

        map.put("createTime", createTime);
        map.put("campaignCost", "" + campaignCost);
        map.put("momoAccount", momoAccount);

        String message = MapFormat.format(NamedConstants.SMS_TEMPLATE_NEW_CAMPAIGN_ADMIN, map);
        logger.debug("message : " + message);

        sendSMS(message, recipientAccount, clientPool);

        return message;
    }

    /**
     * Create SMS to send to admin upon escalation of campaign
     *
     * @param id
     * @param momoAccount
     * @param recipientAccount
     * @param clientPool
     * @return
     */
    public static String generateAndSendCampaignEscalateMsg(int id, String momoAccount, String recipientAccount, final HttpClientPool clientPool) throws MyCustomException {

        //Object[] params = {"nameRobert", "rhume55@gmail.com"};
        Map<String, String> map = new HashMap<>();

        map.put("id", "" + id);
        map.put("momoAccount", momoAccount);

        String message = MapFormat.format(NamedConstants.SMS_TEMPLATE_CAMPAIGN_ESCALATE_ADMIN, map);
        logger.debug("message : " + message);

        sendSMS(message, recipientAccount, clientPool);

        return message;
    }

    /**
     * * Send a text message to the avertXpo admin alerting them of campaign
     * that needs review Create SMS to send to admin for review of campaign
     *
     * @param id
     * @param momoAccount
     * @param recipientAccount
     * @param clientPool
     * @return
     */
    public static String generateAndSendCampaignReviewAdminMsg(int id, String momoAccount, String recipientAccount, final HttpClientPool clientPool) throws MyCustomException {

        //Object[] params = {"nameRobert", "rhume55@gmail.com"};
        Map<String, String> map = new HashMap<>();

        map.put("id", "" + id);
        map.put("momoAccount", momoAccount);

        String message = MapFormat.format(NamedConstants.SMS_TEMPLATE_CAMPAIGN_REVIEW_ADMIN, map);
        logger.debug("message : " + message);

        sendSMS(message, recipientAccount, clientPool);

        return message;
    }

    /**
     *
     * @param smsText
     * @param recipientNumber
     * @param clientPool
     * @return
     * @throws MyCustomException
     */
    public static String sendSMS(String smsText, String recipientNumber, final HttpClientPool clientPool) throws MyCustomException {

        logger.debug("###### Sending Message: " + smsText + " #######");

        Map<String, Object> paramPairs = GeneralUtils.prepareTextMsgParams(smsText, recipientNumber);

        //for now use hardcoded stuff in constants, but we have an externalunits entry in the xml that we can use for SMS server
        //configs and mobile money configs as well
        //String smsSendResponse = GeneralUtils.sendSMS(paramPairs);
        //String smsSendResponse = clientPool.sendRemoteRequest("", NamedConstants.SMS_API_URL, paramPairs, HTTPMethod.GET);
        String smsSendResponse = "";
        logger.info("Response from SMS web API Server: " + smsSendResponse);

        return smsSendResponse;
    }
}
