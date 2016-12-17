package com.library.utilities.dsmbridge;

import java.io.File;

public final class ImageMagickUtil {

    public static final String OutFileName = "OUTPUT";

    protected static boolean executeCommand(String command) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command);

            int INTERVAL = 200;
            int WAIT_TIME = 300000;

            java.io.InputStream in = process.getInputStream();
            java.io.InputStream err = process.getErrorStream();

            int exitValue = -1;

            int pass = 0;
            while (pass <= 300000) {
                try {
                    do {
                        in.read();
                    } while (in.available() > 0);

                    while (err.available() > 0) {
                        err.read();
                    }
                    exitValue = -1;
                    exitValue = process.exitValue();
                } catch (IllegalThreadStateException itex) {
                    try {
                        Thread.sleep(200L);
                        pass += 200;
                    } catch (InterruptedException e) {
                        throw new IllegalStateException("pdf convert error: " + command, e);
                    }
                }
            }

            if (pass > 300000) {
                process.destroy();
            }
            return exitValue == 0;
        } catch (java.io.IOException ioex) {
            throw new IllegalStateException("convert to image failed: " + command, ioex);
        }
    }

    public static boolean convertToJpg(String convert_ImageMagick, String pdf, String dest) {
        File convert_ImageMagickFile = new File(convert_ImageMagick);

        if ((!convert_ImageMagickFile.exists()) || (!convert_ImageMagickFile.isFile())) {
            throw new IllegalArgumentException("invalid convert_ImageMagickFile: " + convert_ImageMagickFile);
        }
        String command = "\"" + convert_ImageMagick + "\""
                + "-verbose -resize 0x0! -rotate 0  -quality 100 ";

        if (!dest.endsWith(File.separator)) {
            dest = dest + File.separator;
        }
        dest = dest + "OUTPUT.jpg";

        command = command + " \"" + pdf + "\" \"" + dest + "\" ";

        File destFile = new File(dest);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        return executeCommand(command);
    }

    public static boolean convertToPng(String convert_ImageMagick, String pdf, String dest) {
        File convert_ImageMagickFile = new File(convert_ImageMagick);

        if ((!convert_ImageMagickFile.exists()) || (!convert_ImageMagickFile.isFile())) {
            throw new IllegalArgumentException("invalid convert_ImageMagickFile: " + convert_ImageMagickFile);
        }
        String command = "\"" + convert_ImageMagick + "\"" + "-verbose -resize 0x0! -rotate 0 -quality 100 ";

        command = command + " -define png:bit-depth=8 -depth 24  -type TrueColorMatte ";

        if (!dest.endsWith(File.separator)) {
            dest = dest + File.separator;
        }
        dest = dest + "OUTPUT.png";

        command = command + " \"" + pdf + "\" \"" + dest + "\" ";

        File destFile = new File(dest);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        return executeCommand(command);
    }
}
