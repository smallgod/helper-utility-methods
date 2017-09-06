package com.library.utilities.dsmbridge;

import com.library.sglogger.util.LoggerUtil;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public class MessageServer {

    private static final LoggerUtil logger = new LoggerUtil(MessageServer.class);

    public static boolean initialize(String address, int port) {
        return initialize(address, port, null);
    }

    public static boolean initialize(String address, int port, String log_directory) {
        if ((address = StringUtils.trimToNull(address)) == null) {
            throw new IllegalArgumentException("server address must cannot null.");
        }
        if (port <= 0) {
            throw new IllegalArgumentException("port must be positive number.");
        }
        serverAddr = address;
        serverPort = port;

        if ((log_directory = StringUtils.trimToNull(log_directory)) != null) {
            File dir = new File(log_directory);
            log_directory = dir.getAbsolutePath();

            if (!log_directory.endsWith(Character.toString(File.separatorChar))) {
                log_directory = log_directory + File.separatorChar;
            }
            logDirectory = log_directory;
        }

        File f = new File(logDirectory);
        if (((f.exists()) && (!f.isDirectory())) || ((!f.exists()) && (!f.mkdirs()))) {
            throw new IllegalArgumentException(
                    "invalid log directory: " + logDirectory);
        }
        return true;
    }

    protected static String serverAddr = "127.0.0.1";
    protected static int serverPort = 9091;

    protected static String logDirectory = getDefaultLogDirectory();

    protected static final int SIZE_OF_INT = 4;

    protected static final int SIZE_OF_CHAR = 1;

    protected static final int SIZE_OF_UCHAR = 1;

    protected static final int HDR_LEN = 4;

    protected static final int MSGHDR_LEN = 4;

    protected static final int HDR_MSGHDR_LEN = 8;
    protected static final byte MSG_VER = -116;
    protected static final int SIZE_OF_DEVID_LOW = 4;
    protected static final int SIZE_OF_DEVID_HIGH = 4;

    protected static String getDefaultLogDirectory() {
        try {

            String classDirectory = "";
            //String classDirectory = Storage.class.getClassLoader().getResource("/").getPath();
            classDirectory = URLDecoder.decode(classDirectory, "UTF-8");
            File f = new File(classDirectory);
            f = f.getParentFile().getParentFile();
            return f.getAbsolutePath()
                    + File.separatorChar + "log"
                    + File.separatorChar + "message_server"
                    + File.separatorChar;
        } catch (Exception ex) {
        }
        return null;
    }

    protected int dateTimeToInt(Date version) {
        return (int) (version.getTime() / 1000L) - version.getTimezoneOffset() * 60;
    }

    protected int fillInt32(byte[] buf, int pos, int val) {

        if (buf == null) {
            throw new IllegalArgumentException("[FillInt32]invalid buffer");
        }

        if ((pos < 0) || (pos + 4 > buf.length)) {
            throw new IllegalArgumentException("[FillInt32]invalid pos");
        }

        buf[(pos++)] = ((byte) (val & 0xFF));
        buf[(pos++)] = ((byte) (val >> 8 & 0xFF));
        buf[(pos++)] = ((byte) (val >> 16 & 0xFF));
        buf[(pos++)] = ((byte) (val >> 24 & 0xFF));

        return pos;
    }

    protected int fillHeader(byte[] buf, byte type, int body_len) {
        if (buf == null) {
            throw new IllegalArgumentException("invalid buffer");
        }
        if (body_len < 4) {
            throw new IllegalArgumentException("invalid body length");
        }
        if (buf.length != 4 + body_len) {
            throw new IllegalArgumentException("invalid buffer length");
        }
        int i = 0;

        buf[0] = 69;
        buf[1] = 103;
        buf[2] = ((byte) (body_len >> 8 & 0xFF));
        buf[3] = ((byte) (body_len >> 0 & 0xFF));

        i += 4;

        buf[(i + 0)] = -116;
        buf[(i + 1)] = type;
        buf[(i + 2)] = ((byte) (body_len >> 8 & 0xFF));
        buf[(i + 3)] = ((byte) (body_len >> 0 & 0xFF));

        return 8;
    }

    protected int fillInt64DeviceId(byte[] buf, int pos, long device_id) {
        if (buf == null) {
            throw new IllegalArgumentException("[FillInt64DeviceId]invalid buffer");
        }
        
        if ((pos < 0) || (pos + 8 > buf.length)) {
            throw new IllegalArgumentException("[FillInt64DeviceId]invalid pos");
        }

        buf[(pos++)] = ((byte) (int) (device_id & 0xFF));
        buf[(pos++)] = ((byte) (int) (device_id >> 8 & 0xFF));
        buf[(pos++)] = ((byte) (int) (device_id >> 16 & 0xFF));
        buf[(pos++)] = ((byte) (int) (device_id >> 24 & 0xFF));

        buf[(pos++)] = ((byte) (int) (device_id >> 32 & 0xFF));
        buf[(pos++)] = ((byte) (int) (device_id >> 40 & 0xFF));
        buf[(pos++)] = ((byte) (int) (device_id >> 48 & 0xFF));
        buf[(pos++)] = ((byte) (int) (device_id >> 56 & 0xFF));

        return pos;
    }

    protected int sendMessgae(byte[] buf) {
        return sendMessgae(buf, buf.length);
    }

    protected int sendMessgae(byte[] buf, int len) {

        if ((buf == null) || (buf.length < len)) {

            throw new IllegalArgumentException("invalid buffer or length");
        }

        if (len <= 0) {
            throw new IllegalArgumentException("invalid date length");
        }

        Socket socket = null;
        OutputStream outputStream = null;

        try {
            socket = new Socket(serverAddr, serverPort);
        } catch (UnknownHostException exp) {
            logger.error("Unknown Host [" + serverAddr + ":" + serverPort + "]: " + exp.getMessage());
        } catch (IOException exp) {
            logger.error("Connect to server[" + serverAddr + ":" + serverPort + "] failed: " + exp.getMessage());
            return -1;
        }
        try {
            logger.debug("Message server about to write to outputstream: " + new String(buf, "UTF-8"));
            outputStream = socket.getOutputStream();
            outputStream.write(buf, 0, len);

            return len;
        } catch (IOException exp) {
            logger.error("Send data failed:  " + exp.getMessage());
            exp.printStackTrace();
            return -1;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException localIOException3) {
                localIOException3.printStackTrace();
                logger.error("IOException sending message: " + localIOException3.getMessage());
            }
        }
    }

    /**
     *
     * @param task_id
     * @return
     */
    public boolean loopTaskChanged(int task_id) {

        if (task_id == 0) {
            throw new IllegalArgumentException("invalid loop task id");
        }

        int SizeofMsgLoopTaskChangedNotify = 8;

        byte[] buf = new byte[4 + SizeofMsgLoopTaskChangedNotify];

        int i = 0;
        i = fillHeader(buf, (byte) 49, SizeofMsgLoopTaskChangedNotify);
        i = fillInt32(buf, i, task_id);

        sendMessgae(buf);
        return true;
    }

    /**
     * 
     * @param task_id
     * @return 
     */
    public boolean demandTaskChanged(int task_id) {
        if (task_id == 0) {
            throw new IllegalArgumentException("invalid demand task id");
        }

        int SizeofMsgDemandTaskChangedNotify = 8;

        byte[] buf = new byte[4 + SizeofMsgDemandTaskChangedNotify];

        int i = 0;
        i = fillHeader(buf, (byte) 50, SizeofMsgDemandTaskChangedNotify);
        i = fillInt32(buf, i, task_id);

        sendMessgae(buf);
        return true;
    }

    /**
     *
     * @param task_id
     * @return
     */
    public boolean pluginTaskChanged(int task_id) {
        if (task_id == 0) {
            throw new IllegalArgumentException("invalid plugin task id");
        }

        int SizeofMsgPlugInTaskChangedNotify = 8;

        byte[] buf = new byte[4 + SizeofMsgPlugInTaskChangedNotify];

        int i = 0;
        i = fillHeader(buf, (byte) 51, SizeofMsgPlugInTaskChangedNotify);
        i = fillInt32(buf, i, task_id);

        sendMessgae(buf);
        return true;
    }

    /**
     *
     * @param config_id
     * @return
     */
    public boolean configChanged(int config_id) {
        if (config_id == 0) {
            throw new IllegalArgumentException("invalid config id");
        }

        int SizeofMsgConfigChangedNotify = 8;

        byte[] buf = new byte[4 + SizeofMsgConfigChangedNotify];

        int i = 0;
        i = fillHeader(buf, (byte) 58, SizeofMsgConfigChangedNotify);
        i = fillInt32(buf, i, config_id);

        sendMessgae(buf);
        return true;
    }

    /**
     *
     * @param device_id
     * @return
     */
    public boolean noticeAssignChanged(long device_id) {

        if (device_id == 0L) {
            throw new IllegalArgumentException("invalid device id");
        }

        int SizeofMsgClientAssignChangedNotifyChanged = 12;

        byte[] buf = new byte[4 + SizeofMsgClientAssignChangedNotifyChanged];

        int i = 0;
        i = fillHeader(buf, (byte) 19, SizeofMsgClientAssignChangedNotifyChanged);
        i = fillInt64DeviceId(buf, i, device_id);

        sendMessgae(buf);
        return true;
    }

    /**
     *
     * @param device_id
     * @return
     */
    public boolean captureScreen(long device_id) {

        if (device_id == 0L) {
            throw new IllegalArgumentException("invalid device id");
        }

        int SizeofCmdHdr = 4;
        int SizeofCmdCapture = SizeofCmdHdr;

        int SizeofMsgCommandNotify = 12 + SizeofCmdCapture;

        byte[] buf = new byte[4 + SizeofMsgCommandNotify];

        int i = 0;
        i = fillHeader(buf, (byte) 18, SizeofMsgCommandNotify);
        i = fillInt64DeviceId(buf, i, device_id);
        i = fillInt32(buf, i, 2);

        sendMessgae(buf);
        return true;
    }

    /**
     *
     * @param device_id
     * @param program_number
     * @return
     */
    public boolean demandProgram(long device_id, int program_number) {
        if (device_id == 0L) {
            throw new IllegalArgumentException("invalid device id");
        }
        if ((program_number < 0) || (program_number > 99)) {
            throw new IllegalArgumentException("invalid program number");
        }

        int SizeofCmdHdr = 4;
        int SizeofCmdDemand = SizeofCmdHdr + 4;

        int SizeofMsgCommandNotify = 12 + SizeofCmdDemand;

        byte[] buf = new byte[4 + SizeofMsgCommandNotify];

        int i = 0;
        i = fillHeader(buf, (byte) 18, SizeofMsgCommandNotify);
        i = fillInt64DeviceId(buf, i, device_id);
        i = fillInt32(buf, i, 3);
        i = fillInt32(buf, i, program_number);

        sendMessgae(buf);
        return true;
    }

    /**
     *
     * @param device_id
     * @return
     */
    public boolean clearContents(long device_id) {
        if (device_id == 0L) {
            throw new IllegalArgumentException("invalid device id");
        }

        int SizeofCmdHdr = 4;
        int SizeofCmdClearContents = SizeofCmdHdr;

        int SizeofMsgCommandNotify = 12 + SizeofCmdClearContents;

        byte[] buf = new byte[4 + SizeofMsgCommandNotify];

        int i = 0;
        i = fillHeader(buf, (byte) 18, SizeofMsgCommandNotify);
        i = fillInt64DeviceId(buf, i, device_id);
        i = fillInt32(buf, i, 4);

        sendMessgae(buf);
        return true;
    }

    /**
     *
     * @param device_id
     * @return
     */
    public boolean reboot(long device_id) {
        if (device_id == 0L) {
            throw new IllegalArgumentException("invalid device id");
        }

        int SizeofMsgRebootNotify = 12;

        byte[] buf = new byte[4 + SizeofMsgRebootNotify];

        int i = 0;
        i = fillHeader(buf, (byte) 16, SizeofMsgRebootNotify);
        i = fillInt64DeviceId(buf, i, device_id);

        sendMessgae(buf);
        return true;
    }

    /**
     *
     * @param device_id
     * @return
     */
    public boolean settingChanged(long device_id) {

        if (device_id == 0L) {
            throw new IllegalArgumentException("invalid device id");
        }

        int SizeofCmdHdr = 4;
        int SizeofCmdSettingChanged = SizeofCmdHdr;

        int SizeofMsgCommandNotify = 12 + SizeofCmdSettingChanged;

        byte[] buf = new byte[4 + SizeofMsgCommandNotify];

        int i = 0;
        i = fillHeader(buf, (byte) 18, SizeofMsgCommandNotify);
        i = fillInt64DeviceId(buf, i, device_id);
        i = fillInt32(buf, i, 58);

        sendMessgae(buf);
        return true;
    }

    /* Error */
    public static void log(String msg) {
        // Byte code:
        //   0: aload_0
        //   1: ifnull +10 -> 11
        //   4: aload_0
        //   5: invokevirtual 211	java/lang/String:length	()I
        //   8: ifne +4 -> 12
        //   11: return
        //   12: aconst_null
        //   13: astore_1
        //   14: getstatic 38	com/gnamp/server/handle/MessageServer:logDirectory	Ljava/lang/String;
        //   17: astore_2
        //   18: new 213	java/text/SimpleDateFormat
        //   21: dup
        //   22: ldc -41
        //   24: invokespecial 216	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
        //   27: new 165	java/util/Date
        //   30: dup
        //   31: invokespecial 217	java/util/Date:<init>	()V
        //   34: invokevirtual 221	java/text/SimpleDateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
        //   37: astore_3
        //   38: new 213	java/text/SimpleDateFormat
        //   41: dup
        //   42: ldc -33
        //   44: invokespecial 216	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
        //   47: new 165	java/util/Date
        //   50: dup
        //   51: invokespecial 217	java/util/Date:<init>	()V
        //   54: invokevirtual 221	java/text/SimpleDateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
        //   57: astore 4
        //   59: new 89	java/lang/StringBuilder
        //   62: dup
        //   63: aload_2
        //   64: invokestatic 93	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
        //   67: invokespecial 94	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   70: ldc -31
        //   72: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   75: aload_3
        //   76: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   79: ldc -29
        //   81: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   84: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   87: astore 5
        //   89: new 67	java/io/File
        //   92: dup
        //   93: aload 5
        //   95: invokespecial 68	java/io/File:<init>	(Ljava/lang/String;)V
        //   98: astore 6
        //   100: aload 6
        //   102: invokevirtual 104	java/io/File:exists	()Z
        //   105: ifne +29 -> 134
        //   108: aload 6
        //   110: invokevirtual 154	java/io/File:getParentFile	()Ljava/io/File;
        //   113: invokevirtual 104	java/io/File:exists	()Z
        //   116: ifne +12 -> 128
        //   119: aload 6
        //   121: invokevirtual 154	java/io/File:getParentFile	()Ljava/io/File;
        //   124: invokevirtual 110	java/io/File:mkdirs	()Z
        //   127: pop
        //   128: aload 6
        //   130: invokevirtual 232	java/io/File:createNewFile	()Z
        //   133: pop
        //   134: new 89	java/lang/StringBuilder
        //   137: dup
        //   138: new 89	java/lang/StringBuilder
        //   141: dup
        //   142: ldc -22
        //   144: invokespecial 94	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   147: aload 4
        //   149: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   152: ldc -20
        //   154: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   157: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   160: invokespecial 94	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   163: astore 7
        //   165: aload 7
        //   167: aload_0
        //   168: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   171: pop
        //   172: aload 7
        //   174: ldc -18
        //   176: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   179: pop
        //   180: new 229	java/io/FileWriter
        //   183: dup
        //   184: aload 6
        //   186: iconst_1
        //   187: invokespecial 241	java/io/FileWriter:<init>	(Ljava/io/File;Z)V
        //   190: astore_1
        //   191: aload_1
        //   192: aload 7
        //   194: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   197: invokevirtual 244	java/io/FileWriter:write	(Ljava/lang/String;)V
        //   200: goto +38 -> 238
        //   203: astore_2
        //   204: aload_1
        //   205: ifnull +46 -> 251
        //   208: aload_1
        //   209: invokevirtual 247	java/io/FileWriter:close	()V
        //   212: goto +39 -> 251
        //   215: astore 9
        //   217: goto +34 -> 251
        //   220: astore 8
        //   222: aload_1
        //   223: ifnull +12 -> 235
        //   226: aload_1
        //   227: invokevirtual 247	java/io/FileWriter:close	()V
        //   230: goto +5 -> 235
        //   233: astore 9
        //   235: aload 8
        //   237: athrow
        //   238: aload_1
        //   239: ifnull +12 -> 251
        //   242: aload_1
        //   243: invokevirtual 247	java/io/FileWriter:close	()V
        //   246: goto +5 -> 251
        //   249: astore 9
        //   251: return
        // Line number table:
        //   Java source line #199	-> byte code offset #0
        //   Java source line #200	-> byte code offset #11
        //   Java source line #202	-> byte code offset #12
        //   Java source line #204	-> byte code offset #14
        //   Java source line #206	-> byte code offset #18
        //   Java source line #207	-> byte code offset #27
        //   Java source line #206	-> byte code offset #34
        //   Java source line #208	-> byte code offset #38
        //   Java source line #209	-> byte code offset #47
        //   Java source line #208	-> byte code offset #54
        //   Java source line #211	-> byte code offset #59
        //   Java source line #213	-> byte code offset #89
        //   Java source line #214	-> byte code offset #100
        //   Java source line #215	-> byte code offset #108
        //   Java source line #216	-> byte code offset #119
        //   Java source line #217	-> byte code offset #128
        //   Java source line #220	-> byte code offset #134
        //   Java source line #221	-> byte code offset #165
        //   Java source line #222	-> byte code offset #172
        //   Java source line #226	-> byte code offset #180
        //   Java source line #227	-> byte code offset #191
        //   Java source line #228	-> byte code offset #200
        //   Java source line #231	-> byte code offset #204
        //   Java source line #232	-> byte code offset #208
        //   Java source line #233	-> byte code offset #212
        //   Java source line #229	-> byte code offset #220
        //   Java source line #231	-> byte code offset #222
        //   Java source line #232	-> byte code offset #226
        //   Java source line #233	-> byte code offset #230
        //   Java source line #235	-> byte code offset #235
        //   Java source line #231	-> byte code offset #238
        //   Java source line #232	-> byte code offset #242
        //   Java source line #233	-> byte code offset #246
        //   Java source line #236	-> byte code offset #251
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	252	0	msg	String
        //   13	230	1	fileWriter	java.io.FileWriter
        //   17	47	2	dir	String
        //   203	1	2	localException	Exception
        //   37	39	3	date	String
        //   57	91	4	time	String
        //   87	7	5	path	String
        //   98	87	6	file	File
        //   163	30	7	log	StringBuilder
        //   220	16	8	localObject	Object
        //   215	1	9	localException1	Exception
        //   233	1	9	localException2	Exception
        //   249	1	9	localException3	Exception
        // Exception table:
        //   from	to	target	type
        //   14	200	203	java/lang/Exception
        //   204	212	215	java/lang/Exception
        //   14	204	220	finally
        //   222	230	233	java/lang/Exception
        //   238	246	249	java/lang/Exception
    }

}
