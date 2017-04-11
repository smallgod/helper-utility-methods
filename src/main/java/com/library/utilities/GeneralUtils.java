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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.library.customexception.ErrorWrapper;
import com.library.customexception.MyCustomException;
import com.library.datamodel.Constants.APIContentType;
import com.library.datamodel.Constants.EntityName;
import com.library.datamodel.Constants.ErrorCode;
import com.library.datamodel.Constants.NamedConstants;
import static com.library.datamodel.Constants.NamedConstants.FIRST_SLOT_ALLOCATION;
import static com.library.datamodel.Constants.NamedConstants.SECOND_SLOT_ALLOCATION;
import static com.library.datamodel.Constants.NamedConstants.SLOTS_IN_HOUR;
import static com.library.datamodel.Constants.NamedConstants.THIRD_SLOT_ALLOCATION;
import com.library.datamodel.jaxb.config.v1_0.LayoutContentType;
import com.library.datamodel.model.v1_0.AdAPIRequest;
import com.library.datamodel.model.v1_0.AdClient;
import com.library.datamodel.model.v1_0.AdMonitor;
import com.library.datamodel.model.v1_0.AdPayment;
import com.library.datamodel.model.v1_0.AdProgram;
import com.library.datamodel.model.v1_0.AdResource;
import com.library.datamodel.model.v1_0.AdSchedule;
import com.library.datamodel.model.v1_0.AdScreen;
import com.library.datamodel.model.v1_0.AdArea;
import com.library.datamodel.model.v1_0.AdScreenOwner;
import com.library.datamodel.model.v1_0.AdTerminal;
import com.library.datamodel.model.v1_0.AdText;
import com.library.datamodel.model.v1_0.AdAudienceType;
import com.library.datamodel.model.v1_0.Author;
import com.library.datamodel.model.v1_0.Book;
import com.library.datamodel.model.v1_0.AdBusinessType;
import com.library.datamodel.model.v1_0.AdTimeSlot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.openide.util.MapFormat;

/**
 *
 * @author smallgod
 */
public class GeneralUtils {

    private static final LoggerUtil logger = new LoggerUtil(GeneralUtils.class);

    private static final Type stringMapType = new TypeToken<Map<String, Object>>() {
    }.getType();

    private static final Type mapInMapType = new TypeToken<Map<String, Map<String, String>>>() {
    }.getType();

    /**
     *
     * @param entityName
     * @param isCollection
     * @return
     */
    public static Type getEntityType(EntityName entityName, boolean isCollection) {

        Type entityCollectionType = null;
        Type singleCollectionType = null;

        switch (entityName) {

            case AD_PROGRAM:
                //if they are many adverts (multiple) we need to read them in as a map????? not so ????

                singleCollectionType = new TypeToken<AdProgram>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdProgram>>() {
                }.getType();

                break;

            case AD_OWNER:
                singleCollectionType = new TypeToken<AdScreenOwner>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdScreenOwner>>() {
                }.getType();

                break;

            case AD_AREA:
                singleCollectionType = new TypeToken<AdArea>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdArea>>() {
                }.getType();
                break;
            //775930087            //775930087

            case AD_RESOURCE:
                singleCollectionType = new TypeToken<AdResource>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdResource>>() {
                }.getType();
                break;

            case AD_PAYMENT:
                singleCollectionType = new TypeToken<AdPayment>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdPayment>>() {
                }.getType();
                break;

            case AD_SCHEDULE:
                singleCollectionType = new TypeToken<AdSchedule>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdSchedule>>() {
                }.getType();
                break;

            case AD_SCREEN:
                singleCollectionType = new TypeToken<AdScreen>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdScreen>>() {
                }.getType();
                break;

            case AD_SCREENOWNER:
                singleCollectionType = new TypeToken<AdScreenOwner>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdScreenOwner>>() {
                }.getType();
                break;

            case AD_CLIENT:
                singleCollectionType = new TypeToken<AdClient>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdClient>>() {
                }.getType();
                break;

            case AD_MONITOR:
                singleCollectionType = new TypeToken<AdMonitor>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdMonitor>>() {
                }.getType();
                break;

            case AD_TERMINAL:
                singleCollectionType = new TypeToken<AdTerminal>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdTerminal>>() {
                }.getType();
                break;

            case AUDIENCE_TYPE:
                singleCollectionType = new TypeToken<AdAudienceType>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdAudienceType>>() {
                }.getType();
                break;

            case LOCATION_TYPE:
                singleCollectionType = new TypeToken<AdBusinessType>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdBusinessType>>() {
                }.getType();
                break;

            case TIME_SLOT:
                singleCollectionType = new TypeToken<AdTimeSlot>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdTimeSlot>>() {
                }.getType();
                break;

            case AD_TEXT:
                singleCollectionType = new TypeToken<AdText>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<AdText>>() {
                }.getType();
                break;

            case AUTHOR:
                singleCollectionType = new TypeToken<Author>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<Author>>() {
                }.getType();
                break;

            case BOOK:
                singleCollectionType = new TypeToken<Book>() {
                }.getType();
                entityCollectionType = new TypeToken<Set<Book>>() {
                }.getType();
                break;

            default:
                logger.warn("Unknown Entity: " + entityName + ", bad things bound to happen!!! ");
                break;
        }

        if (isCollection) {
            return entityCollectionType;
        }

        return singleCollectionType;
    }

    /**
     * The following method shuts down an ExecutorService in two phases, first
     * by calling shutdown to reject incoming tasks, and then calling
     * shutdownNow, if necessary, to cancel any lingering tasks (timeToWait
     * time) elapses.
     *
     * @param pool the executor service pool
     */
    public static void shutdownProcessor(final ExecutorService pool, long timeToWait, TimeUnit timeUnit) {

        logger.info("Executor pool waiting for tasks to complete");
        pool.shutdown(); // Disable new tasks from being submitted

        try {

            boolean terminatedOK = pool.awaitTermination(timeToWait, timeUnit);

            // Wait a while for existing tasks to terminate
            if (!terminatedOK) {

                // Wait a while for tasks to respond to being cancelled
                terminatedOK = pool.awaitTermination(++timeToWait, timeUnit);

                if (!terminatedOK) {
                    logger.warn("Executor waiting for pending tasks, another " + timeToWait + " " + timeUnit.toString() + "...");

                    pool.shutdownNow(); // Cancel currently executing tasks
                    logger.warn("Executor ShutdownNow with pending tasks");
                }

            } else {
                logger.info("Executor pool completed all tasks and has shut "
                        + "down normally");
            }
        } catch (InterruptedException ie) {
            logger.error("Executor pool shutdown error: " + ie.getMessage());
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();

            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Convert a JSON string to pretty print version
     *
     * @param jsonString
     * @return a well formatted JSON string
     */
    public static String toPrettyJsonOLD(String jsonString) {

        JsonParser parser = new JsonParser();

        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

    /**
     * Print out pretty json
     *
     * @param jsonString
     * @return
     */
    public static String toPrettyJson(String jsonString) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonString);
        String prettyJson = gson.toJson(je);

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

    /**
     * Write a response to calling server client
     *
     * @param response
     * @param responseToWrite
     * @throws com.library.customexception.MyCustomException
     */
    public static void writeResponse(HttpServletResponse response, String responseToWrite) throws MyCustomException {

        try (PrintWriter out = response.getWriter()) {

            out.write(responseToWrite);
            out.flush();
            response.flushBuffer();

        } catch (IOException ex) {

            MyCustomException error = GeneralUtils.getSingleError(ErrorCode.COMMUNICATION_ERR, "Error writing back client response", "Error writing response to client: " + ex.getMessage());
            throw error;

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
    public static <T> String convertToJson(Object objectToConvert, Class<T> objectType) throws MyCustomException {

        try {
            //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();

            //gsonBuilder.excludeFieldsWithoutExposeAnnotation();
            //gsonBuilder.registerTypeAdapter(AdScreenOwner.class, new MyGsonTypeAdapter<AdScreenOwner>());
            GraphAdapterBuilder graphAdapterBuilder = new GraphAdapterBuilder();
            graphAdapterBuilder
                    .addType(Author.class)
                    .addType(AdScreenOwner.class)
                    //.addType(AdProgram.class)
                    .registerOn(gsonBuilder);
            gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaGsonLocalDateConverter());
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JodaGsonLocalDateTimeConverter());
            gsonBuilder.registerTypeAdapter(LocalTime.class, new JodaGsonLocalTimeConverter());

            Gson gson = gsonBuilder.create();

            return gson.toJson(objectToConvert, objectType);

        } catch (IllegalArgumentException iae) {

            String errorDescription = "Error! Sorry, request cannot be processed now, please try again later";
            String errorDetails = "IllegalArgumentException occurred trying to convert to JSON: " + iae.getMessage();
            MyCustomException error = GeneralUtils.getSingleError(ErrorCode.PROCESSING_ERR, errorDescription, errorDetails);
            throw error;
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
    public static <T> String convertToJson(Object objectToConvert, Type objectType) throws MyCustomException {

        try {
            //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();

            //gsonBuilder.registerTypeAdapter(AdScreenOwner.class, new MyGsonTypeAdapter<AdScreenOwner>());
            //gsonBuilder.excludeFieldsWithoutExposeAnnotation();
            GraphAdapterBuilder graphAdapterBuilder = new GraphAdapterBuilder();
            graphAdapterBuilder
                    .addType(Author.class)
                    .addType(AdScreenOwner.class)
                    //.addType(AdProgram.class)
                    .registerOn(gsonBuilder);
            gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaGsonLocalDateConverter());
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JodaGsonLocalDateTimeConverter());
            gsonBuilder.registerTypeAdapter(LocalTime.class, new JodaGsonLocalTimeConverter());

            Gson gson = gsonBuilder.create();

            return gson.toJson(objectToConvert, objectType);

        } catch (IllegalArgumentException iae) {

            String errorDescription = "Error! Sorry, request cannot be processed now, please try again later";
            String errorDetails = "IllegalArgumentException occurred trying to convert to JSON: " + iae.getMessage();
            MyCustomException error = GeneralUtils.getSingleError(ErrorCode.PROCESSING_ERR, errorDescription, errorDetails);
            throw error;
        }
    }

    /**
     * Return Object from JSON string
     *
     * @param <T>
     * @param stringToConvert
     * @param objectType
     * @return
     * @throws com.library.customexception.MyCustomException
     */
    public static <T> T convertFromJson(String stringToConvert, Class<T> objectType) throws MyCustomException {

        ErrorWrapper errorWrapper = new ErrorWrapper(); //incase it happens
        Set<ErrorWrapper> errors = new HashSet<>();

        //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();

        //gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        //gsonBuilder.registerTypeAdapter(AdScreenOwner.class, new MyGsonTypeAdapter<AdScreenOwner>());
        GraphAdapterBuilder graphAdapterBuilder = new GraphAdapterBuilder();
        graphAdapterBuilder
                .addType(Author.class)
                .addType(AdScreenOwner.class)
                //.addType(AdProgram.class)
                .registerOn(gsonBuilder);
        gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaGsonLocalDateConverter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JodaGsonLocalDateTimeConverter());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new JodaGsonLocalTimeConverter());

        Gson gson = gsonBuilder.create();

        T returnObj = null;

        try {
            returnObj = gson.fromJson(stringToConvert.trim(), objectType);
            return returnObj;

        } catch (JsonSyntaxException jse) {

            errorWrapper.setErrorCode(ErrorCode.PROCESSING_ERR);
            errorWrapper.setErrorDetails("Error converting from JSON");
            errorWrapper.setDescription(jse.getMessage());

        }

        errors.add(errorWrapper);
        throw new MyCustomException("", errors);

    }

    /**
     *
     * @param <T>
     * @param stringArrayToConvert
     * @param objectType
     * @return a list of converted JSON strings
     */
    public static <T> List<T> convertFromJson(List<String> stringArrayToConvert, Type objectType) throws MyCustomException {

        ErrorWrapper errorWrapper = new ErrorWrapper(); //incase it happens
        Set<ErrorWrapper> errors = new HashSet<>();

        //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();

        //gsonBuilder.registerTypeAdapter(AdScreenOwner.class, new MyGsonTypeAdapter<AdScreenOwner>());
        GraphAdapterBuilder graphAdapterBuilder = new GraphAdapterBuilder();
        graphAdapterBuilder
                .addType(Author.class)
                .addType(AdScreenOwner.class)
                //.addType(AdProgram.class)
                .registerOn(gsonBuilder);
        gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaGsonLocalDateConverter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JodaGsonLocalDateTimeConverter());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new JodaGsonLocalTimeConverter());

        Gson gson = gsonBuilder.create();

        List list = new ArrayList<>();

        try {
            for (String strToConvert : stringArrayToConvert) {

                list.add(gson.fromJson(strToConvert.trim(), objectType));
            }

            return list;

        } catch (JsonSyntaxException jse) {

            errorWrapper.setErrorCode(ErrorCode.PROCESSING_ERR);
            errorWrapper.setErrorDetails("Error converting from JSON");
            errorWrapper.setDescription(jse.getMessage());

        }

        errors.add(errorWrapper);
        throw new MyCustomException("", errors);

    }

    /**
     * Return Object from JSON string
     *
     * @param <T>
     * @param stringToConvert
     * @param objectType
     * @return
     * @throws com.library.customexception.MyCustomException
     */
    public static <T> T convertFromJson(String stringToConvert, Type objectType) throws MyCustomException {

        ErrorWrapper errorWrapper = new ErrorWrapper(); //incase it happens
        Set<ErrorWrapper> errors = new HashSet<>();

        try {

            //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();

            //gsonBuilder.excludeFieldsWithoutExposeAnnotation();
            //gsonBuilder.registerTypeAdapter(AdScreenOwner.class, new MyGsonTypeAdapter<AdScreenOwner>());
            GraphAdapterBuilder graphAdapterBuilder = new GraphAdapterBuilder();
            graphAdapterBuilder
                    .addType(Author.class)
                    .addType(AdScreenOwner.class)
                    //.addType(AdProgram.class)
                    .registerOn(gsonBuilder);
            gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaGsonLocalDateConverter());
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JodaGsonLocalDateTimeConverter());
            gsonBuilder.registerTypeAdapter(LocalTime.class, new JodaGsonLocalTimeConverter());

            Gson gson = gsonBuilder.create();

            return gson.fromJson(stringToConvert.trim(), objectType);

        } catch (JsonSyntaxException jse) {

            errorWrapper.setErrorCode(ErrorCode.PROCESSING_ERR);
            errorWrapper.setErrorDetails("Error converting from JSON");
            errorWrapper.setDescription(jse.getMessage());

        }

        errors.add(errorWrapper);
        throw new MyCustomException("", errors);

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
     * Generate a random alpha numeric string of specified length
     *
     * @param stringLength
     * @return
     */
    public static String generateRandomAlphaNumeric(int stringLength) {

        String randomString = RandomStringUtils.randomAlphanumeric(stringLength).toUpperCase();

        return randomString;
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
     *
     * @param request
     * @return
     */
    public static String getRequesterHeaderInfo(HttpServletRequest request) {

        String allHeaders = "";

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {

            String headerName = headerNames.nextElement();
            allHeaders += headerName;

            Enumeration<String> headers = request.getHeaders(headerName);
            while (headers.hasMoreElements()) {

                String headerValue = headers.nextElement();
                allHeaders = allHeaders + " = " + headerValue + ";";
            }

            allHeaders += " <> ";
        }

        return allHeaders;
    }

    /**
     * Method logs to a debug file most of the HttpServletRequest parameters
     *
     * @param request HttpServletRequest
     * @return
     */
    public static AdAPIRequest getRequestInfo(HttpServletRequest request) {

        AdAPIRequest apiRequest = new AdAPIRequest();
        apiRequest.setContentType(request.getContentType());
        apiRequest.setContextPath(request.getContextPath());
        apiRequest.setContentLength(request.getContentLength());
        apiRequest.setProtocol(request.getProtocol());
        apiRequest.setPathInfo(request.getPathInfo());
        apiRequest.setRemoteAddress(request.getRemoteAddr());
        apiRequest.setRemotePort(request.getRemotePort());
        apiRequest.setServerName(request.getServerName());
        apiRequest.setQueryString(request.getQueryString());
        apiRequest.setRequestUrl(request.getRequestURL().toString());
        apiRequest.setRequestUri(request.getRequestURI());
        apiRequest.setServletPath(request.getServletPath());
        apiRequest.setRequestBody("");

        return apiRequest;

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

    /**
     *
     * @param jsonPaymentRequest
     * @return
     * @throws MyCustomException
     */
    public static Set<Map.Entry<String, Object>> getJsonDetails(String jsonPaymentRequest) throws MyCustomException {

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
     * Return a nice string representation of the array
     *
     * @param iterable
     * @return
     */
    public static String getPrintableIterable(Iterable iterable) {

        return (String.join(",", iterable));

    }

    /**
     * Get a printable array string
     *
     * @param <T>
     * @param collection
     * @return
     */
    public static <T> String getPrintableArray(Set<T> collection) {

        return (Arrays.toString(collection.toArray()));
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
     *
     * @param <T>
     * @param list
     * @return
     */
    public static <T> Object[] convertListToArray(List<T> list) {

        Object[] newArray = new Object[list.size()];
        newArray = list.toArray(newArray);

        return newArray;
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

    /**
     * Round up to next 100th integer
     *
     * @param value
     * @return
     */
    public static int roundUpToNext100(double value) {

        return (int) (Math.ceil(value / 100.0) * 100);
    }

    /**
     * Round up to next integer
     *
     * @param value
     * @return
     */
    public static int roundUpToNextInt(double value) {
        return (int) Math.ceil(value);
    }

    /**
     * Add commas to a number
     *
     * @param numberToFormat
     * @return
     */
    public static String addCommas1(int numberToFormat) {

        return (NumberFormat.getNumberInstance(Locale.US).format(numberToFormat));
    }

    /**
     *
     * @param numberToAddCommas
     * @return
     */
    public static String addCommas2(int numberToAddCommas) {

        String str = "UGX" + String.valueOf(numberToAddCommas).replaceAll("/\\B(?=(\\d{3})+(?!\\d))/g", ",");

        logger.debug("Formatted amount string is: " + str);

        return str;
    }

    /**
     * Add (a) comma(s) to a number
     *
     * @param numberToAddCommas
     * @return
     */
    public static String addCommasAndCurrency(int numberToAddCommas) {

        DecimalFormat myFormatter = new DecimalFormat("#,###");
        String output = "UGX" + myFormatter.format(numberToAddCommas);

        logger.debug("Formatted amount string is: " + output);

        return output;

    }

    public static int getNH(int terminalHeight, LayoutContentType layout) {
        return GeneralUtils.roundUpToNextInt((terminalHeight * layout.getNH()) / 100);
    }

    public static int getNW(int terminalWidth, LayoutContentType layout) {
        return GeneralUtils.roundUpToNextInt((terminalWidth * layout.getNW()) / 100);
    }

    public static int getNX(int terminalWidth, LayoutContentType layout) {
        return GeneralUtils.roundUpToNextInt((terminalWidth * layout.getNX()) / 100);
    }

    public static int getNY(int terminalHeight, LayoutContentType layout) {
        return GeneralUtils.roundUpToNextInt((terminalHeight * layout.getNY()) / 100);
    }

    /**
     * Is given number a Prime
     *
     * @param n
     * @return
     */
    public static boolean isPrime(long n) {
        // fast even test.
        if (n > 2 && (n & 1) == 0) {
            return false;
        }
        // only odd factors need to be tested up to n^0.5
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Is given number a Prime
     *
     * @param n
     * @return
     */
    public static boolean isPrime(int n) {
        // fast even test.
        if (n > 2 && (n & 1) == 0) {
            return false;
        }
        // only odd factors need to be tested up to n^0.5
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the Advert/Program slot allocation order. First we allocate multiples
     * of 3, then 5, then 2 and lastly all remaining 1s (Primes)
     *
     * @return
     */
    public static Set<Integer> allocateSlotsForAnHour() {

        Set<Integer> slotAllocateOrder = new LinkedHashSet<>(); //linked has set maintains order of insertion

        for (int slot = 1; slot <= SLOTS_IN_HOUR; slot++) {

            //Order of allocation is 3, 5, 2 and 1(Primes) lastly
            if (slot % FIRST_SLOT_ALLOCATION == 0) { // 0 & multiples of 3 first
                slotAllocateOrder.add(slot);

                logger.debug("Adding slot : " + slot);

            }
        }

        for (int slot = 1; slot <= SLOTS_IN_HOUR; slot++) {
            if (slot % SECOND_SLOT_ALLOCATION == 0) { //then multiples of 5
                slotAllocateOrder.add(slot);

                logger.debug("Adding slot : " + slot);
            }
        }

        for (int slot = 1; slot <= SLOTS_IN_HOUR; slot++) {
            if (slot % THIRD_SLOT_ALLOCATION == 0) { //then 2
                slotAllocateOrder.add(slot);

                logger.debug("Adding slot : " + slot);
            }
        }

        for (int slot = 1; slot <= SLOTS_IN_HOUR; slot++) {
            if (GeneralUtils.isPrime(slot)) { //primes last
                slotAllocateOrder.add(slot);

                logger.debug("Adding slot : " + slot);
            }
        }

        logger.debug("Slot allocation Order set: " + getPrintableArray(slotAllocateOrder));

        return slotAllocateOrder;
    }

    /**
     * Convert an Object to a long value
     *
     * @param value
     * @return
     */
    public static long convertObjectToLong(Object value) {

        logger.debug("Converting Object to long");
        return (value instanceof Number ? ((Number) value).longValue() : -1);
    }

    /**
     * Convert an Object to a double value
     *
     * @param value
     * @return
     */
    public static double convertObjectToDouble(Object value) {
        logger.debug("Converting Object to double");
        return (value instanceof Number ? ((Number) value).doubleValue() : -1.0);
    }

    /**
     *
     * @param mapOfSchedulesAndProgIds
     * @return
     */
    public static String convertToStringMapTheOfSchedulesAndProgIds(Map<Integer, Long> mapOfSchedulesAndProgIds) {

        String scheduleString = "";

        for (Map.Entry<Integer, Long> entry : mapOfSchedulesAndProgIds.entrySet()) {

            int scheduleTime = entry.getKey();
            long progEntityId = entry.getValue();

            scheduleString += (progEntityId + "::" + scheduleTime + ";");

        }

        return scheduleString;
    }

    /**
     * Convert the String returned from the schedule table column that maps
     * Schedule times for this screen to their respective program Entity Ids
     * String is in the format "764::4563;905::2355;" i.e.
     * schedTime::progEntityId;
     *
     * @param scheduleStringFromDatabase
     * @return
     */
    public static Map<Integer, Long> convertToMapStringOfSchedulesAndProgIds(String scheduleStringFromDatabase) {

        logger.debug(">>>> Schedule String, fetched from Database >> " + scheduleStringFromDatabase + " <<<<<<<<");

        String[] progTimeArray = scheduleStringFromDatabase.trim().split("\\s*;\\s*"); // ["764:4563", "905:2355"]

        logger.debug("ProgTimeArray: " + Arrays.toString(progTimeArray));

        //add program ids and their schedule times to an iterable
        Map<Integer, Long> mapOfScheduleAndProgIds = new HashMap<>();

        for (String progAndTime : progTimeArray) { //"764:4563"

            if (!progAndTime.isEmpty()) {

                long progEntityId = Long.parseLong(progAndTime.split("\\s*::\\s*")[0]);
                int previouslySchedTime = Integer.parseInt(progAndTime.split("\\s*::\\s*")[1]);

                mapOfScheduleAndProgIds.put(previouslySchedTime, progEntityId);
            }
        }

        return mapOfScheduleAndProgIds;
    }

    /**
     * formatMSISDN
     *
     * @param MSISDN
     * @return
     */
    public static String formatMSISDN(String MSISDN) {

        if (MSISDN.startsWith("+")) {
            MSISDN = MSISDN.replace("+", "").trim();
        }
        Long phoneNumber;
        try {
            phoneNumber = Long.valueOf(MSISDN);
        } catch (NumberFormatException ex) {
            logger.error("Could not convert number to a Long value: " + ex.getMessage() + ". So returning the number as it was.");
            return MSISDN;
        }
        int length = phoneNumber.toString().length();

        switch (length) {
            case 12:
                logger.info("MSISDN [ "
                        + MSISDN + "] has length: " + MSISDN.length()
                        + " when converted to a long value. No fix to be done");
                break;
            case 9:
                logger.info("MSISDN [ "
                        + MSISDN + "] has length: " + MSISDN.length() + ". "
                        + " when converted to a long value."
                        + " An attempt to fix the number by adding a prefix "
                        + "will be done");
                if (phoneNumber.toString().startsWith("7")) {
                    MSISDN = 256 + phoneNumber.toString();
                }
                break;

            default:
                logger.info("MSISDN [ "
                        + MSISDN + "] has length " + MSISDN.length()
                        + " when converted to a long value. "
                        + "Will be sent as is.");
                break;
        }

        logger.debug("Returning formatted MSISDN as: " + MSISDN);

        return MSISDN;
    }

    private static int createRandomInteger(int aStart, long aEnd, Random aRandom) {

        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = aEnd - aStart + 1;
        //logger.info("range>>>>>>>>>>>" + range);
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long) (range * aRandom.nextDouble());
        //logger.info("fraction>>>>>>>>>>>>>>>>>>>>" + fraction);
        long randomNumber = fraction + (long) aStart;
        //logger.info("Generated : " + randomNumber);

        return (int) randomNumber;

    }

    /**
     * Generate OTP (4-digit PIN)
     * 
     * @return
     */
    public static synchronized int generateOTP() {

        int START = 1000;
        long END = 9999L;

        Random random = new Random();
        int generatedOTP = createRandomInteger(START, END, random);

        return generatedOTP;
    }
    
        public static synchronized int generateInt() {

        int START = 1000;
        long END = 9999L;

        Random random = new Random();
        int generatedOTP = createRandomInteger(START, END, random);

        return generatedOTP;
    }

    /**
     * getActivationMessage from template
     *
     * @param firstName
     * @param amount
     * @param outstandingBalance
     * @param activationCode
     * @param numberOfActiveDays
     * @return
     */
    public static String getActivationCodeMessage(String firstName, int amount, int outstandingBalance, String activationCode, int numberOfActiveDays) {

        //Object[] params = {"nameRobert", "rhume55@gmail.com"};
        Map<String, Object> map = new HashMap<>();

        map.put("firstName", firstName);
        map.put("amount", addCommasAndCurrency(amount));
        map.put("outstandingBalance", addCommasAndCurrency(outstandingBalance));
        map.put("activationCode", activationCode);
        map.put("numberOfActiveDays", String.valueOf(numberOfActiveDays));

        String message = MapFormat.format(NamedConstants.SMS_TEMPLATE_ACT_CODE, map);
        logger.debug("Activation message going out : " + message);

        return message;
    }

    /**
     * getPaymentFailMessage message from Template
     *
     * @param firstName
     * @param amount
     * @param generatorId
     * @param statusDescription
     * @return
     */
    public static String getPaymentFailMessage(String firstName, int amount, String generatorId, String statusDescription) {

        //Object[] params = {"nameRobert", "rhume55@gmail.com"};
        Map<String, Object> map = new HashMap<>();

        map.put("firstName", firstName);
        map.put("amount", addCommasAndCurrency(amount));
        map.put("generatorId", generatorId);
        map.put("statusDescription", statusDescription);

        String message = MapFormat.format(NamedConstants.SMS_PAYMENT_FAILURE, map);
        logger.debug("Payment Failure message going out : " + message);

        return message;
    }

    /**
     * getActivationMessage from template
     *
     * @param firstName
     * @param otp
     * @return
     */
    public static String getOTPMessage(String firstName, int otp) {

        //Object[] params = {"nameRobert", "rhume55@gmail.com"};
        Map<String, String> map = new HashMap<>();

        map.put("firstName", firstName);
        map.put("otp", "" + otp);
        //map.put("telesolaAccount", telesolaAccount);

        String message = MapFormat.format(NamedConstants.SMS_TEMPLATE_OTP, map);
        logger.debug("OTP message : " + message);

        return message;
    }

    /**
     *
     * @param smsText
     * @param recipientNumber
     * @return
     */
    public static Map<String, Object> prepareTextMsgParams(String smsText, String recipientNumber) {

        Map<String, Object> paramPairs = new HashMap<>();

        paramPairs.put(NamedConstants.SMS_API_PARAM_USERNAME, NamedConstants.SMS_API_USERNAME);
        paramPairs.put(NamedConstants.SMS_API_PARAM_PASSOWRD, NamedConstants.SMS_API_PASSWORD);
        paramPairs.put(NamedConstants.SMS_API_PARAM_SENDER, NamedConstants.SMS_API_SENDER_NAME);
        paramPairs.put(NamedConstants.SMS_API_PARAM_TEXT, smsText);
        paramPairs.put(NamedConstants.SMS_API_PARAM_RECIPIENT, recipientNumber);

        return paramPairs;
    }

    public static List<NameValuePair> convertToNameValuePair(Map<String, Object> pairs) {

        if (pairs == null) {
            return null;
        }

        List<NameValuePair> nvpList = new ArrayList<>(pairs.size());

        for (Map.Entry<String, Object> entry : pairs.entrySet()) {
            nvpList.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }

        return nvpList;

    }

    /**
     * Send out SMS
     *
     * @param paramPairs
     * @return
     */
    public static String sendSMS(Map<String, Object> paramPairs) {

        //String response = "Assume an SMS is sent and this is the response, hihihihi, LOLEST!!";
        //String response = AppEntry.clientPool.sendRemoteRequest("", NamedConstants.SMS_API_URL, paramPairs, HTTPMethod.GET);
        return "";
    }

    /**
     *
     * @param errorCode
     * @param errorDescription
     * @param errorDetails
     * @return
     */
    public static MyCustomException getSingleError(ErrorCode errorCode, String errorDescription, String errorDetails) {

        ErrorWrapper errorWrapper = new ErrorWrapper();
        Set<ErrorWrapper> errors = new HashSet<>();
        errorWrapper.setErrorCode(errorCode);
        errorWrapper.setDescription(errorDescription);
        errorWrapper.setErrorDetails(errorDetails);

        errors.add(errorWrapper);

        return new MyCustomException("", errors);
    }

    /**
     * Generate a User Id account from the client's primaryContact
     *
     * @param primaryContact
     * @return
     */
    public static String generateUserId(String primaryContact) {
        return (primaryContact.substring(3));

        //To-Do
        //Separate accounts by region, especially for distributors e.g. DKLA774983602 for a Kampala Distributor
    }

    /**
     * Convert a set of string objects to a comma delimited String
     *
     * @param screenCodes
     * @return
     */
    public static String convertSetToCommaDelString(Set<String> screenCodes) {

        String screenCodeStr = "";
        if (screenCodes != null) {

            screenCodeStr = String.join(",", screenCodes);
        }

        //org.apache.commons.lang.StringUtils.join(screenCodes, ",");
        return screenCodeStr;
    }

    /**
     * Convert a comma delimited String to a Set
     *
     * @param commaDelString
     * @return
     */
    public static Set<String> convertCommaDelStringToSet(String commaDelString) {

        Set<String> set = new HashSet<>();

        StringTokenizer st = new StringTokenizer(commaDelString, ",");
        while (st.hasMoreTokens()) {
            set.add(st.nextToken());
        }

        //Set<String> hashSet = new HashSet<>(Arrays.asList(commaDelString.split(",")));
        return set;

    }
}
