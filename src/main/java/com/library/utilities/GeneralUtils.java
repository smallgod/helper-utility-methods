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
import com.library.datamodel.Constants.EntityName;
import com.library.datamodel.Constants.NamedConstants;
import static com.library.datamodel.Constants.NamedConstants.FIRST_SLOT_ALLOCATION;
import static com.library.datamodel.Constants.NamedConstants.SECOND_SLOT_ALLOCATION;
import static com.library.datamodel.Constants.NamedConstants.SLOTS_IN_HOUR;
import static com.library.datamodel.Constants.NamedConstants.THIRD_SLOT_ALLOCATION;
import com.library.datamodel.jaxb.config.v1_0.LayoutContentType;
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
import com.library.datamodel.model.v1_0.LocationType;
import com.library.datamodel.model.v1_0.TimeSlot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                entityCollectionType = new TypeToken<List<AdProgram>>() {
                }.getType();

                break;

            case AD_OWNER:
                singleCollectionType = new TypeToken<AdScreenOwner>() {
                }.getType();
                entityCollectionType = new TypeToken<List<AdScreenOwner>>() {
                }.getType();

                break;

            case AD_AREA:
                singleCollectionType = new TypeToken<AdScreenArea>() {
                }.getType();
                entityCollectionType = new TypeToken<List<AdScreenArea>>() {
                }.getType();
                break;
            //775930087

            case AD_RESOURCE:
                singleCollectionType = new TypeToken<AdResource>() {
                }.getType();
                entityCollectionType = new TypeToken<List<AdResource>>() {
                }.getType();
                break;

            case AD_PAYMENT:
                singleCollectionType = new TypeToken<AdPayment>() {
                }.getType();
                entityCollectionType = new TypeToken<List<AdPayment>>() {
                }.getType();
                break;

            case AD_SCHEDULE:
                singleCollectionType = new TypeToken<AdSchedule>() {
                }.getType();
                entityCollectionType = new TypeToken<List<AdSchedule>>() {
                }.getType();
                break;

            case AD_SCREEN:
                singleCollectionType = new TypeToken<AdScreen>() {
                }.getType();
                entityCollectionType = new TypeToken<List<AdScreen>>() {
                }.getType();
                break;

            case AD_SCREENOWNER:
                singleCollectionType = new TypeToken<AdScreenOwner>() {
                }.getType();
                entityCollectionType = new TypeToken<List<AdScreenOwner>>() {
                }.getType();
                break;

            case AD_CLIENT:
                singleCollectionType = new TypeToken<AdClient>() {
                }.getType();
                entityCollectionType = new TypeToken<List<AdClient>>() {
                }.getType();
                break;

            case AD_MONITOR:
                singleCollectionType = new TypeToken<AdMonitor>() {
                }.getType();
                entityCollectionType = new TypeToken<List<AdMonitor>>() {
                }.getType();
                break;

            case AD_TERMINAL:
                singleCollectionType = new TypeToken<AdTerminal>() {
                }.getType();
                entityCollectionType = new TypeToken<List<AdTerminal>>() {
                }.getType();
                break;

            case AUDIENCE_TYPE:
                singleCollectionType = new TypeToken<AudienceType>() {
                }.getType();
                entityCollectionType = new TypeToken<List<AudienceType>>() {
                }.getType();
                break;

            case LOCATION_TYPE:
                singleCollectionType = new TypeToken<LocationType>() {
                }.getType();
                entityCollectionType = new TypeToken<List<LocationType>>() {
                }.getType();
                break;

            case TIME_SLOT:
                singleCollectionType = new TypeToken<TimeSlot>() {
                }.getType();
                entityCollectionType = new TypeToken<List<TimeSlot>>() {
                }.getType();
                break;

            case AD_TEXT:
                singleCollectionType = new TypeToken<AdText>() {
                }.getType();
                entityCollectionType = new TypeToken<List<AdText>>() {
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

    /**
     *
     * @param pairs
     * @return
     */
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

        //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        
        //gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        //gsonBuilder.registerTypeAdapter(AdScreenOwner.class, new MyGsonTypeAdapter<AdScreenOwner>());
        GraphAdapterBuilder graphAdapterBuilder = new GraphAdapterBuilder();
        graphAdapterBuilder
                .addType(AdScreenOwner.class)
                .addType(AdProgram.class).registerOn(gsonBuilder);
        gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaGsonLocalDateConverter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JodaGsonLocalDateTimeConverter());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new JodaGsonLocalTimeConverter());

        Gson gson = gsonBuilder.create();

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

        //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        
        //gsonBuilder.registerTypeAdapter(AdScreenOwner.class, new MyGsonTypeAdapter<AdScreenOwner>());
        //gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        GraphAdapterBuilder graphAdapterBuilder = new GraphAdapterBuilder();
        graphAdapterBuilder
                .addType(AdScreenOwner.class)
                .addType(AdProgram.class).registerOn(gsonBuilder);
        gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaGsonLocalDateConverter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JodaGsonLocalDateTimeConverter());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new JodaGsonLocalTimeConverter());

        Gson gson = gsonBuilder.create();

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

        //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        
        //gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        //gsonBuilder.registerTypeAdapter(AdScreenOwner.class, new MyGsonTypeAdapter<AdScreenOwner>());
        GraphAdapterBuilder graphAdapterBuilder = new GraphAdapterBuilder();
        graphAdapterBuilder
                .addType(AdScreenOwner.class)
                .addType(AdProgram.class).registerOn(gsonBuilder);
        gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaGsonLocalDateConverter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JodaGsonLocalDateTimeConverter());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new JodaGsonLocalTimeConverter());

        Gson gson = gsonBuilder.create();

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
     */
    public static <T> List<T> convertFromJson(List<String> stringArrayToConvert, Type objectType) {

        //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        
        //gsonBuilder.registerTypeAdapter(AdScreenOwner.class, new MyGsonTypeAdapter<AdScreenOwner>());
        GraphAdapterBuilder graphAdapterBuilder = new GraphAdapterBuilder();
        graphAdapterBuilder
                .addType(AdScreenOwner.class)
                .addType(AdProgram.class).registerOn(gsonBuilder);
        gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaGsonLocalDateConverter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JodaGsonLocalDateTimeConverter());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new JodaGsonLocalTimeConverter());

        Gson gson = gsonBuilder.create();

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

        //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();

        //gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        //gsonBuilder.registerTypeAdapter(AdScreenOwner.class, new MyGsonTypeAdapter<AdScreenOwner>());
        GraphAdapterBuilder graphAdapterBuilder = new GraphAdapterBuilder();
        graphAdapterBuilder
                .addType(AdScreenOwner.class)
                .addType(AdProgram.class).registerOn(gsonBuilder);
        gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaGsonLocalDateConverter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JodaGsonLocalDateTimeConverter());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new JodaGsonLocalTimeConverter());

        Gson gson = gsonBuilder.create();

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

        Set<Integer> slotAllocateOrder = new HashSet<>();

        for (int slot = 1; slot <= SLOTS_IN_HOUR; slot++) {

            //Order of allocation is 3, 5, 2 and 1(Primes) lastly
            if (slot % FIRST_SLOT_ALLOCATION == 0) { // 0 & multiples of 3 first
                slotAllocateOrder.add(slot);

            }
        }

        for (int slot = 1; slot <= SLOTS_IN_HOUR; slot++) {
            if (slot % SECOND_SLOT_ALLOCATION == 0) { //then multiples of 5
                slotAllocateOrder.add(slot);
            }
        }

        for (int slot = 1; slot <= SLOTS_IN_HOUR; slot++) {
            if (slot % THIRD_SLOT_ALLOCATION == 0) { //then 2
                slotAllocateOrder.add(slot);
            }
        }

        for (int slot = 1; slot <= SLOTS_IN_HOUR; slot++) {
            if (GeneralUtils.isPrime(slot)) { //primes last
                slotAllocateOrder.add(slot);
            }
        }

        return slotAllocateOrder;
    }
}
