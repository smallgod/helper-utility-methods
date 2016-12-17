package com.library.utilities.dsmbridge;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

public class ImageUtil {

    public static Dimension getPix(String image_path) {
        File file = new File(image_path);
        if ((!file.exists()) || (!file.isFile())) {
            throw new IllegalArgumentException("invalid image file path: " + image_path);
        }
        try {
            BufferedImage image = ImageIO.read(file);
            return new Dimension(image.getWidth(), image.getHeight());
        } catch (IIOException ex) {
            ImageInputStream imageIputStream = null;
            try {
                imageIputStream = new FileImageInputStream(file);

                ImageReader imageReader = null;
                Iterator<ImageReader> readers = ImageIO.getImageReaders(imageIputStream);
                while (readers.hasNext()) {
                    ImageReader ird = (ImageReader) readers.next();
                    if (ird.canReadRaster()) {
                        imageReader = ird;
                        break;
                    }
                }

                if (imageReader == null) {
                    throw new IllegalStateException("invalid image file: " + image_path);
                }

                imageReader.setInput(imageIputStream);
                return new Dimension(imageReader.getWidth(0), imageReader.getHeight(0));
            } catch (IOException e) {
                throw new IllegalStateException("read image[CMYK] failed: " + image_path, e);
            } finally {
                try {
                    if (imageIputStream != null) {
                        imageIputStream.close();
                    }
                } catch (IOException localIOException2) {
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException("read image failed: " + image_path, ex);
        }
    }

    /* Error */
//    public static boolean scaledTo(String src, String dest, int width, int height) {
//        
//    }

    public static boolean captureVideoImage(String video, String dest, int pos, int width, int height, String ffmpeg) {
        if ((width <= 0) || (height <= 0)) {
            throw new IllegalArgumentException("invalid width[" + width
                    + "] or height[" + height + "]");
        }
        File ffmpegFile = new File(ffmpeg);
        if ((!ffmpegFile.exists()) || (!ffmpegFile.isFile())) {
            throw new IllegalArgumentException("invalid ffmpeg: " + ffmpeg);
        }
        File destFile = new File(dest);
        destFile.getParentFile().mkdirs();

        String command = "\"" + ffmpeg + "\""
                + " -i \"" + video + "\" -y -f image2 -ss " + pos + " -t 0.001 \"" + dest + "\"";
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command);

            int INTERVAL = 200;
            int WAIT_TIME = 15000;

            InputStream in = process.getInputStream();
            InputStream err = process.getErrorStream();

            int exitValue = -1;

            int pass = 0;
            while (pass <= 15000) {
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
                        throw new IllegalStateException("image capture error: " + video, e);
                    }
                }
            }

            if (pass > 15000) {
                process.destroy();
            }
            return exitValue == 0;
        } catch (IOException ioex) {
            throw new IllegalStateException("capture image failed: " + video, ioex);
        }
    }
}
