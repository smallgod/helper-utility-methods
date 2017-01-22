/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import com.library.customexception.MyCustomException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.CRC32;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author smallgod
 */
public class FileUtilities {

    private static final LoggerUtil logger = new LoggerUtil(FileUtilities.class);

    private static final int BUFFER_SIZE = 8 * 1024;

    /**
     *
     * @param inputFile
     * @param outputFile
     * @return true | false if file was converted successfuly
     * @throws com.namaraka.recon.exceptiontype.MyCustomException
     */
    public static boolean convertFileToCSV(String inputFile, String outputFile) {

        //String command = "ping -c 3 www.google.com";
        String createFile = "touch %s";
        String formattedCmd1 = String.format(createFile, outputFile);
        String conversionCmd = "ssconvert --export-type=Gnumeric_stf:stf_csv %s %s";
        String formattedCmd = String.format(conversionCmd, inputFile, outputFile);

        logger.debug("... executing command $ " + formattedCmd);

        Process proc;
        String line = "";
        int exitValue = 0;

        try {

            //execute the command
            Runtime.getRuntime().exec(formattedCmd1);
            proc = Runtime.getRuntime().exec(formattedCmd);

            //Read the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            while ((line = reader.readLine()) != null) {
                logger.debug(line + "\n");
            }

            exitValue = proc.waitFor();

        } catch (IOException | InterruptedException | SecurityException ex) {
            //throw new MyCustomException("Exception", ErrorCode.COMMUNICATION_ERR, "Exception executing shell command ->> $ " + formattedCmd + " ->> with errormsg: " + ex.getMessage(), ErrorCategory.SERVER_ERR_TYPE);

        } catch (Exception ex) {
            //throw new MyCustomException("Exception", ErrorCode.COMMUNICATION_ERR, "Exception executing shell command ->> $ " + formattedCmd + " ->> with errormsg: " + ex.getMessage(), ErrorCategory.SERVER_ERR_TYPE);

        }

        if (exitValue == 0X0) { //0 indicates normal termination
            return Boolean.TRUE;
        } else {

            //throw new MyCustomException("Abnormal termination", ErrorCode.INTERNAL_ERR, "Exception executing shell command ->> $ " + formattedCmd + " terminated with a non-normal flag:: " + exitValue, ErrorCategory.SERVER_ERR_TYPE);
            return Boolean.FALSE;
        }
    }

    /**
     * *
     * Get file extension e.g. txt, xls, ods, odt, csv, xlsl
     *
     * @param fileName
     * @return
     * @throws NullPointerException
     * @throws com.namaraka.recon.exceptiontype.MyCustomException
     */
    public static String getFileExtension(String fileName) {

        String fileExtension = null;

        logger.info("fileName::: " + fileName);

        int indexOfDot = fileName.lastIndexOf('.');

        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (indexOfDot > p) {

            fileExtension = fileName.substring(indexOfDot + 1);
        }

        if (fileExtension == null) {
            //throw new MyCustomException("NullPointer Exception", ErrorCode.NOT_SUPPORTED_ERR, "Failed to get file Extension", ErrorCategory.CLIENT_ERR_TYPE);
        }

        logger.info("File Extension got: " + fileExtension);

        return fileExtension.trim();
    }

    /**
     *
     * @param oldFileName
     * @param newFileExtension
     * @return filename with new extension
     * @throws com.namaraka.recon.exceptiontype.MyCustomException
     */
    public static String changeFileTypeToCSV(String oldFileName, String newFileExtension) {

        String newFileName = null;

        int indexOfDot = oldFileName.lastIndexOf('.');
        int p = Math.max(oldFileName.lastIndexOf('/'), oldFileName.lastIndexOf('\\'));

        if (indexOfDot > p) {
            newFileName = oldFileName.replaceFirst(oldFileName.substring(indexOfDot + 1), newFileExtension);
        }

        if (newFileName == null) {
            //throw new MyCustomException("NullPointer Exception", ErrorCode.NOT_SUPPORTED_ERR, "Failed to create new File Name from:: " + oldFileName, ErrorCategory.SERVER_ERR_TYPE);

        }
        return newFileName.trim();
    }

    /**
     *
     * @param absoluteFileName
     * @return fileNameAndType
     */
    public static String getFileNameAndType(String absoluteFileName) {

        int indexOfFileName = Math.max(absoluteFileName.lastIndexOf('/'), absoluteFileName.lastIndexOf('\\'));
        String fileNameAndType = absoluteFileName.substring(indexOfFileName + 1).trim();

        return fileNameAndType;
    }

    /**
     *
     * @param source
     * @param dest
     * @throws IOException
     */
    public static void copyFile(File source, File dest) throws IOException {

        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void copyFile(String source, String dest) {

        FileChannel sourceChannel = null;
        FileChannel destChannel = null;

        try {

            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            long bitesTransfered = destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

        } catch (FileNotFoundException ex) {
            logger.error("FileNotFoundException occurred while copying file: " + ex.getMessage());
        } catch (IOException ex) {
            logger.error("IOException occurred while copying file: " + ex.getMessage());
        } finally {

            try {
                if (sourceChannel != null) {
                    sourceChannel.close();
                }
                if (destChannel != null) {
                    destChannel.close();
                }
            } catch (IOException ex) {
                logger.error("IOException occurred while closing openned File Streams: " + ex.getMessage());
            }
        }

    }

    public static void copyFileUsingChannel(File source, File dest) {

        FileChannel sourceChannel = null;
        FileChannel destChannel = null;

        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            long bitesTransfered = destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

        } catch (FileNotFoundException ex) {
            logger.error("FileNotFoundException occurred while copying file: " + ex.getMessage());
        } catch (IOException ex) {
            logger.error("IOException occurred while copying file: " + ex.getMessage());
        } finally {
            try {
                if (sourceChannel != null) {
                    sourceChannel.close();
                }
                if (destChannel != null) {
                    destChannel.close();
                }
            } catch (IOException ex) {
                logger.error("IOException occurred while closing openned File Streams: " + ex.getMessage());
            }
        }
    }

    /**
     *
     * @param absFileName
     * @return
     * @throws MyCustomException
     */
    public static boolean createFileOnDisk(String absFileName) {

        boolean isCreated = false;

        try {

            File newFile = new File(absFileName);
            //newFile.mkdirs();
            isCreated = newFile.createNewFile();

        } catch (IOException ex) {
            //throw new MyCustomException("IO Exception", ErrorCode.COMMUNICATION_ERR, "Failed to create new File : " + ex.getMessage() + " --> FileName: " + absFileName, ErrorCategory.SERVER_ERR_TYPE);

        } catch (SecurityException ex) {
            //throw new MyCustomException("SecurityException", ErrorCode.SERVER_ERR, "Failed to create new File : " + ex.getMessage() + " --> FileName: " + absFileName, ErrorCategory.SERVER_ERR_TYPE);

        } catch (NullPointerException ex) {
            //throw new MyCustomException("NPE", ErrorCode.SERVER_ERR, "Failed to create new File : " + ex.getMessage() + " --> FileName: " + absFileName, ErrorCategory.SERVER_ERR_TYPE);

        } catch (Exception ex) {
            //throw new MyCustomException("Exception", ErrorCode.SERVER_ERR, "Failed to create new File : " + ex.getMessage(), ErrorCategory.SERVER_ERR_TYPE);

        }

        return isCreated;
    }

    public static boolean createDirectory(String dir_path) {
        logger.debug("CREATING new DIR: " + dir_path);
        return (new File(dir_path).mkdirs());
    }

    public static void createFileDirectory(String file_path) {
        new File(file_path).getParentFile().mkdirs();
    }

    public static void deleteDirectory(String dir) {
        File dirFile = new File(dir);
        if (dirFile.exists()) {
            File[] children = dirFile.listFiles();
            if ((children != null) && (children.length > 0)) {
                for (int i = 0; i < children.length; i++) {
                    if (children[i].isDirectory()) {
                        deleteDirectory(children[i].getAbsolutePath());
                    } else if (children[i].isFile()) {
                        children[i].delete();
                    }
                }
            }
            dirFile.delete();
        }
    }

    public static long getFileCode(String file_path, long file_id) {
        int HEAD_LEN = 32;
        int TAIL_LEN = 32;

        int BLOCK_SIZE = 64;
        int BLOCK_NUM = 30;
        int CHECK_LEN = HEAD_LEN + BLOCK_SIZE * BLOCK_NUM + TAIL_LEN;

        RandomAccessFile fs = null;
        byte[] buf = null;
        try {
            File file = new File(file_path);
            if ((!file.exists()) || (!file.isFile())) {
                throw new IllegalStateException("cannot find file: " + file_path);
            }
            int file_id_low = (int) (file_id & 0xFFFFFFFF);
            long size = file.length();
            fs = new RandomAccessFile(file, "r");

            buf = new byte[CHECK_LEN];

            if (size <= CHECK_LEN) {
                readToBuffer(fs, buf, 0, (int) size);
                for (int i = (int) size; i < buf.length; i++) {
                    buf[i] = 0;
                }
            } else {
                int dwDataLen = 0;
                readToBuffer(fs, buf, dwDataLen, HEAD_LEN);
                dwDataLen += HEAD_LEN;

                long interval = (size - CHECK_LEN) / BLOCK_NUM;
                if (interval > 0L) {
                    int i = 0;
                    for (i = 0; i < BLOCK_NUM; i++) {
                        fs.seek(HEAD_LEN + i * (interval + BLOCK_SIZE));
                        readToBuffer(fs, buf, dwDataLen, BLOCK_SIZE);
                        dwDataLen += BLOCK_SIZE;
                    }
                } else {
                    readToBuffer(fs, buf, dwDataLen, BLOCK_SIZE * BLOCK_NUM);
                    dwDataLen += BLOCK_SIZE * BLOCK_NUM;
                }

                fs.seek(size - TAIL_LEN);
                readToBuffer(fs, buf, dwDataLen, TAIL_LEN);
            }

            CRC32 crc32 = new CRC32();
            crc32.update(buf);
            return crc32.getValue();
        } catch (IOException ioex) {
            throw new RuntimeException(ioex);
        } finally {
            buf = null;
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException localIOException2) {
                    logger.error("Error Getting FileCode: " + localIOException2.getMessage());
                }
            }
        }
    }

    public static long getFileSize(String file_path) {
        File file = new File(file_path);
        if ((!file.exists()) || (!file.isFile())) {
            throw new IllegalArgumentException("cannot find file: " + file_path);
        }
        return file.length();
    }

    public static long getFileCRC(String file_path) {
        File file = new File(file_path);
        if ((!file.exists()) || (!file.isFile())) {
            throw new IllegalArgumentException("cannot find file: " + file_path);
        }
        int BUF_SIZE = 65536;

        long fileSize = file.length();

        FileInputStream fs = null;
        byte[] buf = null;
        try {
            fs = new FileInputStream(file);

            buf = new byte[65536];

            long oneceSize = 0L;
            long calculated = 0L;
            CRC32 crc32 = new CRC32();
            while (calculated < fileSize) {
                oneceSize = fileSize - calculated;
                if (oneceSize > 65536L) {
                    oneceSize = 65536L;
                }
                int read = 0;
                while (read < oneceSize) {
                    int rlen = fs.read(buf, read, (int) oneceSize - read);
                    if (rlen <= 0) {
                        throw new RuntimeException("Read tar file error: " + file_path);
                    }
                    read += rlen;
                }

                crc32.update(buf, 0, read);
                calculated += oneceSize;
            }

            return crc32.getValue();
        } catch (IOException ioex) {
            throw new RuntimeException(ioex);
        } finally {
            buf = null;
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException localIOException2) {
            }
        }
    }

    public static boolean existFile(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static boolean moveFile(String src, String dest) {

        File file_src = new File(src);
        File file_dest = new File(dest);

        if (!file_src.exists()) {
            throw new IllegalArgumentException("cannot find source file: " + src);
        }
        if (file_dest.exists()) {
            throw new IllegalStateException("exited destination file: " + dest);
        }
        file_dest.getParentFile().mkdirs();
        return file_src.renameTo(file_dest);
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.exists() ? file.delete() : true;
    }

    public static boolean isNewFileCreated(String filePath) throws IOException {

        File file = new File(filePath);
        if (!file.exists()) {
            //check if parent dir exists
            if (!(file.getParentFile().exists())) {
                logger.debug("Creating DIRs: " + file);
                file.getParentFile().mkdirs();
            }
            file.createNewFile();  //Dont create the file yet

            return Boolean.TRUE;
        } else {

            return Boolean.FALSE;
        }
    }

    protected static int readToBuffer(RandomAccessFile fs, byte[] buffer, int offset, int read_size) throws IOException {
        if ((fs == null) || (buffer == null) || (read_size == 0) || (offset + read_size > buffer.length)) {
            throw new IllegalArgumentException("invalid argument");
        }
        int dwLen = 0;
        int dwRead = 0;
        while (dwRead < read_size) {
            dwLen = fs.read(buffer, offset + dwRead, read_size - dwRead);
            if (dwLen <= 0) {
                throw new IOException("read file exception, file length is invalid");
            }
            dwRead += dwLen;
        }

        return dwRead;
    }

    /**
     *
     * @param workBook
     * @param sheetIndex
     * @return
     */
    public static Sheet getSheetAtIndex(Workbook workBook, int sheetIndex) {

        int numberOfSheets = workBook.getNumberOfSheets();
        //first sheet from workbook
        Sheet sheet = workBook.getSheetAt(sheetIndex);

        return sheet;
    }

    /**
     *
     * @param sheet
     * @return
     * @throws MyCustomException
     */
//    public static int getNumRecordsMinusHeaders(Sheet sheet) throws MyCustomException {
//
//        int noOfPhysicalRows = sheet.getPhysicalNumberOfRows() - 1; //minus HeaderNames row
//        if (noOfPhysicalRows < 1) {
//
//            logger.error("Excel sheet is empty");
//            throw new MyCustomException("Uploaded file empty", ErrorCode.CLIENT_ERR, "Uploaded file must contain atleast one row of data", ErrorCategory.CLIENT_ERR_TYPE);
//        }
//
//        return noOfPhysicalRows;
//    }
    /**
     * *
     * @param sheet
     * @return Iterator
     * @throws MyCustomException
     */
//    public static Iterator<Row> getWorkBookRowIteratorHelper(Sheet sheet) throws MyCustomException {
//
//        Iterator<Row> rowIterator = sheet.iterator();
//
//        return rowIterator;
//    }
    /**
     * Count the number of lines in a file
     *
     * @param fileName
     * @return
     * @throws MyCustomException
     */
//    public static int countNoOfLinesInFile1(String fileName) throws MyCustomException {
//
//        LineNumberReader lineNumberReader = null;
//        int lineCount = 0;
//
//        try {
//
//            File file = new File(fileName);
//            lineNumberReader = new LineNumberReader(new FileReader(file));
//            lineNumberReader.skip(Long.MAX_VALUE);
//            lineCount = lineNumberReader.getLineNumber();
//
//        } catch (FileNotFoundException ex) {
//            throw new MyCustomException("FileNotFound Error", ErrorCode.INTERNAL_ERR, "File NOT found: " + ex.getMessage(), ErrorCategory.SERVER_ERR_TYPE);
//
//        } catch (IOException ex) {
//            throw new MyCustomException("IO Error", ErrorCode.COMMUNICATION_ERR, "IO error: " + ex.getMessage(), ErrorCategory.SERVER_ERR_TYPE);
//
//        } finally {
//
//            try {
//                if (lineNumberReader != null) {
//                    lineNumberReader.close();
//                }
//            } catch (IOException ex) {
//                logger.error("Error while closing LineNumberReader stream: " + ex.getMessage());
//            }
//        }
//
//        return lineCount;
//
//    }
    /**
     * Counts number of lines in file (**NOT cross-platform)
     *
     * @param fileName
     * @return
     * @throws MyCustomException
     */
//    public final static int countNoOfLinesInFile2(String fileName) throws MyCustomException {
//
//        int lineCount = -1;
//
//        try {
//
//            ProcessBuilder builder = new ProcessBuilder("wc", "-l", fileName);
//            Process process = builder.start();
//
//            InputStream in = process.getInputStream();
//            LineNumberReader reader = new LineNumberReader(new InputStreamReader(in));
//            String line = reader.readLine();
//
//            if (line != null) {
//
//                lineCount = Integer.parseInt(line.trim().split(" ")[0]);
//            }
//
//        } catch (IOException ex) {
//            throw new MyCustomException("IO Error", ErrorCode.COMMUNICATION_ERR, "IO error: " + ex.getMessage(), ErrorCategory.SERVER_ERR_TYPE);
//
//        }
//        return lineCount;
//    }
    /**
     * Count the number of lines (3* faster)
     *
     * @param fileName
     * @return
     * @throws com.namaraka.recon.exceptiontype.MyCustomException
     */
//    public static int countNoOfLinesInFile(String fileName) throws MyCustomException {
//
//        FileInputStream fis = null;
//        int lineCount = -1;
//
//        try {
//
//            fis = new FileInputStream(new File(fileName));
//            byte[] buffer = new byte[BUFFER_SIZE]; // BUFFER_SIZE = 8 * 1024
//
//            int read;
//            while ((read = fis.read(buffer)) != -1) {
//                for (int i = 0; i < read; i++) {
//                    if (buffer[i] == '\n') {
//                        lineCount++;
//                    }
//                }
//            }
//
//        } catch (FileNotFoundException ex) {
//            throw new MyCustomException("FileNotFound Error", ErrorCode.INTERNAL_ERR, "File NOT found: " + ex.getMessage(), ErrorCategory.SERVER_ERR_TYPE);
//
//        } catch (IOException ex) {
//            throw new MyCustomException("IO Error", ErrorCode.COMMUNICATION_ERR, "IO error: " + ex.getMessage(), ErrorCategory.SERVER_ERR_TYPE);
//
//        } finally {
//            try {
//                if (fis != null) {
//                    fis.close();
//                }
//            } catch (IOException ex) {
//                logger.error("Error while closing FileInputStream stream: " + ex.getMessage());
//            }
//        }
//        return lineCount;
//    }
}