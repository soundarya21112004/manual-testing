package com.eagle.mas.common;


import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class ReadImage {

    Logger logger = LoggerFactory.getLogger(ReadImage.class);

    static {
        try {
            nu.pattern.OpenCV.loadLocally();
            System.out.println("----------------OPENCV lOADED SUCCESSFULLY");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load OpenCV", e);
        }
    }

    public String covertasImage(byte[] imageData,int headerSize){
        try{
            StringBuffer buffer = new StringBuffer(asHexString(imageData));
            String headerRemoved = buffer.delete(0,headerSize).toString();
//        OpenCV.loadShared();
            MatOfByte byteArr = new MatOfByte(toByteArray(headerRemoved));
            MatOfByte matOfByte = new MatOfByte();
            Mat imageFile = Imgcodecs.imdecode(byteArr,Imgcodecs.IMREAD_ANYCOLOR);
            Imgcodecs.imencode(".jpg",imageFile,matOfByte);
            logger.info("Image converted by using header size");
            return "data:image/jpg;base64,"+ Base64.getEncoder().encodeToString(matOfByte.toArray());
        }
        catch (Exception e){
          return  ConvertingasImage(imageData);
        }

    }

    public String ConvertingasImage(byte[] imageData){
        StringBuffer buffer = new StringBuffer(asHexString(imageData));
        String headerRemoved = buffer.delete(0, buffer.indexOf("0000000C6A5020200D0A870A".toLowerCase())).toString();
//        OpenCV.loadShared();
        MatOfByte byteArr = new MatOfByte(toByteArray(headerRemoved));
        MatOfByte matOfByte = new MatOfByte();
        Mat imageFile = Imgcodecs.imdecode(byteArr,Imgcodecs.IMREAD_ANYCOLOR);
        Imgcodecs.imencode(".jpg",imageFile,matOfByte);
        logger.info("Image converted by using jp2 magic number(iso standard)");
        return "data:image/jpg;base64,"+ Base64.getEncoder().encodeToString(matOfByte.toArray());
    }

    private final String asHexString(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    private final byte[] toByteArray(String hexString) {
        int arrLength = hexString.length() >> 1;
        byte buf[] = new byte[arrLength];
        for (int ii = 0; ii < arrLength; ii++) {
            int index = ii << 1;
            String l_digit = hexString.substring(index, index + 2);
            buf[ii] = (byte) Integer.parseInt(l_digit, 16);
        }
        return buf;
    }
}
