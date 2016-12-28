/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.library.datamodel.Constants.APIContentType;
import com.library.datamodel.Constants.NamedConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.openide.util.MapFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smallgod
 */
public class GeneralUtils {

    private static final Logger logger = LoggerFactory.getLogger(GeneralUtils.class);

    private static final Type stringMapType = new TypeToken<Map<String, Object>>() {
    }.getType();

    private static final Type mapInMapType = new TypeToken<Map<String, Map<String, String>>>() {
    }.getType();

    /**
     * Convert a JSON string to pretty print version
     *
     * @param jsonString
     * @return a well formatted JSON string
     */
    public static String toPrettyJson(String jsonString) {
        JsonParser parser = new JsonParser();

        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

    /**
     * Get the JSON string from an HTTPServerletRequest
     *
     * @param request
     * @return
     */
    public static String extractJson(HttpServletRequest request) {

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        String s;

        try {

            reader = request.getReader();

            do {

                s = reader.readLine();

                if (s != null) {
                    sb.append(s);
                } else {
                    break;
                }

            } while (true);

        } catch (IOException ex) {
            logger.error("IO Exception, failed to decode JSON string from request: " + ex.getMessage());
            //throw new MyCustomException("IO Exception occurred", ErrorCode.CLIENT_ERR, "Failed to decode JSON string from the HTTP request: " + ex.getMessage(), ErrorCategory.CLIENT_ERR_TYPE);

        } catch (Exception ex) {
            logger.error("General Exception, failed to decode JSON string from request: " + ex.getMessage());
            //throw new MyCustomException("General Exception occurred", ErrorCode.CLIENT_ERR, "Failed to decode JSON string from the HTTP request: " + ex.getMessage(), ErrorCategory.CLIENT_ERR_TYPE);

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    logger.error("exception closing buffered reader: " + ex.getMessage());
                }
            }
        }

        return sb.toString();
    }

    /**
     * Get the method name value with key "method" if Json request or enclosing
     * method root name if xml request
     *
     * @param jsonRequest
     * @param apiType
     * @return
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws IOException
     */
    public static String getMethodName(String jsonRequest, APIContentType apiType) throws JsonProcessingException, IOException {

        String methodName = "";

        switch (apiType) {

            case JSON:

                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(jsonRequest);
                methodName = rootNode.path(NamedConstants.JSON_METHOD_NODENAME).asText();

                break;

            case XML:
                break;

        }

        //APIMethodName methodNameEnum = APIMethodName.convertToEnum(methodName);
        return methodName;
    }

    public static List<NameValuePair> convertToNameValuePair(Map<String, String> pairs) {

        if (pairs == null) {
            return null;
        }
        
        List<NameValuePair> nvpList = new ArrayList<>(pairs.size());

        for (Map.Entry<String, String> entry : pairs.entrySet()) {
            nvpList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        return nvpList;

    }

    /**
     * Write a response to calling server client
     *
     * @param response
     * @param responseToWrite
     */
    public static void writeResponse(HttpServletResponse response, String responseToWrite) {

        PrintWriter out = null;

        try {

            out = response.getWriter();
            out.write(responseToWrite);
            out.flush();
            response.flushBuffer();

        } catch (IOException ex) {

            //throw new MyCustomException("Error writing response to client", ErrorCode.COMMUNICATION_ERR, ex.getMessage(), ErrorCategory.SERVER_ERR_TYPE);
            logger.error("Error writing response to client: " + ex.getMessage());

        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Return JSON string representation of given object
     *
     * @param <T>
     * @param objectToConvert
     * @param objectType
     * @return
     */
    public static <T> String convertToJson(Object objectToConvert, Class<T> objectType) {

        Gson gson = new Gson();
        //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson.toJson(objectToConvert, objectType);
    }

    /**
     * Return JSON string representation of given object
     *
     * @param <T>
     * @param objectToConvert
     * @param objectType
     * @return
     */
    public static <T> String convertToJson(Object objectToConvert, Type objectType) {

        Gson gson = new Gson();
        //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

        return gson.toJson(objectToConvert, objectType);
    }

    /**
     * Return Object from JSON string
     *
     * @param <T>
     * @param stringToConvert
     * @param objectType
     * @return
     */
    public static <T> T convertFromJson(String stringToConvert, Class<T> objectType) {

        Gson gson = new Gson();
        T returnObj = null;

        try {
            returnObj = gson.fromJson(stringToConvert.trim(), objectType);

        } catch (JsonSyntaxException jse) {
            logger.error("JSON Syntax Error while converting from JSON: " + jse.getMessage());

            //throw new MyCustomException("JSON Syntax Error", ErrorCode.INTERNAL_ERR, "Json syntax error converting from JSON: " + jse.getMessage(), ErrorCategory.SERVER_ERR_TYPE);
        }

        return returnObj;
    }

    /**
     *
     * @param <T>
     * @param stringArrayToConvert
     * @param objectType
     * @return a list of converted JSON strings
     * @throws com.namaraka.recon.exceptiontype.MyCustomException
     */
    public static <T> List<T> convertFromJson(List<String> stringArrayToConvert, Type objectType) {

        Gson gson = new Gson();

        List list = new ArrayList<>();

        try {
            for (String strToConvert : stringArrayToConvert) {

                list.add(gson.fromJson(strToConvert.trim(), objectType));
            }
        } catch (JsonSyntaxException jse) {
            logger.error("JSON Syntax Error while converting from JSON: " + jse.getMessage());
            //throw new MyCustomException("JSON Syntax Error", ErrorCode.INTERNAL_ERR, "Json syntax error converting from JSON: " + jse.getMessage(), ErrorCategory.SERVER_ERR_TYPE);

        }
        return list;
    }

    /**
     * Return Object from JSON string
     *
     * @param <T>
     * @param stringToConvert
     * @param objectType
     * @return
     */
    public static <T> T convertFromJson(String stringToConvert, Type objectType) throws JsonSyntaxException {

        Gson gson = new Gson();
        return gson.fromJson(stringToConvert.trim(), objectType);
    }

    /**
     * Generate short UUID (13 characters)
     *
     * @return short randomValue
     */
    public static String generateShorterRandomID() {

        UUID uuid = UUID.randomUUID();
        //long longValue = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        //randomValue = Long.toString(longValue, Character.MAX_RADIX);
        long lessSignificantBits = uuid.getLeastSignificantBits();
        String randomValue = Long.toString(lessSignificantBits, Character.MAX_RADIX);

        return randomValue;

    }

    /**
     *
     * @return full randomValue
     */
    public static String generateFullRandomID() {

        UUID uuid = UUID.randomUUID();

        long longValue = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        String randomValue = Long.toString(longValue, Character.MAX_RADIX);

        return randomValue;
    }

    /**
     * Method will print to a debug file ALL the HttpServletRequest headerNames
     * and their values
     *
     * @param request
     * @throws IOException
     */
    public static void printRequesterHeaderInfo(HttpServletRequest request) throws IOException {

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {

            String headerName = headerNames.nextElement();
            logger.debug(">>> header name  : " + headerName);

            Enumeration<String> headers = request.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                logger.debug(">>> header value : " + headerValue);
            }
            logger.debug(">>> -------------------------------------");
        }
    }

    /**
     * Method logs to a debug file most of the HttpServletRequest parameters
     *
     * @param request HttpServletRequest
     */
    public static void logRequestInfo(HttpServletRequest request) {

        logger.debug(">>> Request Content-type   : " + request.getContentType());
        logger.debug(">>> Request Context-path   : " + request.getContextPath());
        logger.debug(">>> Request Content-type   : " + request.getContentType());
        logger.debug(">>> Request Content-length : " + request.getContentLength());
        logger.debug(">>> Request Protocol       : " + request.getProtocol());
        logger.debug(">>> Request PathInfo       : " + request.getPathInfo());
        logger.debug(">>> Request Remote Address : " + request.getRemoteAddr());
        logger.debug(">>> Request Remote Port    : " + request.getRemotePort());
        logger.debug(">>> Request Server name    : " + request.getServerName());
        logger.debug(">>> Request Querystring    : " + request.getQueryString());
        logger.debug(">>> Request URL            : " + request.getRequestURL().toString());
        logger.debug(">>> Request URI            : " + request.getRequestURI());
    }

    /**
     * The following methods will remove all invalid XML characters from a given
     * string (the special handling of a CDATA section is not supported).
     *
     * @param xml
     * @return
     */
    public static String sanitizeXmlChars(String xml) {

        if (xml == null || ("".equals(xml))) {
            return "";
        }
        // ref : http://www.w3.org/TR/REC-xml/#charsets
        // jdk 7
        Pattern xmlInvalidChars = Pattern.compile(
                "[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\x{10000}-\\x{10FFFF}]"
        );

        return xmlInvalidChars.matcher(xml).replaceAll("");
    }

    public static Set<Map.Entry<String, Object>> getJsonDetails(String jsonPaymentRequest) {

        Map<String, Object> paymentDetails = GeneralUtils.convertFromJson(jsonPaymentRequest, stringMapType);

        Set<Map.Entry<String, Object>> detailsSet = paymentDetails.entrySet();

        //return user;
        return detailsSet;
    }

    /**
     * Generate random 5 digit number
     *
     * @return
     */
    public static int generate5Digits() {

        Random r = new Random(System.currentTimeMillis());
        return 10000 + r.nextInt(20000);
    }

     /**
     * Convert Set to List
     *
     * @param <T>
     * @param set
     * @return
     */
    public static <T> List<T> convertSetToList(Set<T> set) {

        List<T> newList = new ArrayList<>(set);
        return newList;
    }

    /**
     * Convert List to Set
     *
     * @param <T>
     * @param list
     * @return
     */
    public static <T> Set<T> convertListToSet(List<T> list) {

        System.out.println("1st : " + MapFormat.format("", new HashMap<>()));

        Set<T> set = new HashSet<>(list);
        return set;
    }
    
    /**
     * Convert a JSON string to pretty print version
     *
     * @param jsonString
     * @return a well formatted JSON string
     */
    public static String toPrettyJsonFormat(String jsonString) {
        JsonParser parser = new JsonParser();

        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

}
