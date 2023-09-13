package com.eagle.mas.common;

import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Base64;

public class ReadImage {

    public String covertasImage(byte[] imageData,int headerSize){
        StringBuffer buffer = new StringBuffer(asHexString(imageData));
        String headerRemoved = buffer.delete(0,headerSize).toString();
        OpenCV.loadShared();
        MatOfByte byteArr = new MatOfByte(toByteArray(headerRemoved));
        MatOfByte matOfByte = new MatOfByte();
        Mat imageFile = Imgcodecs.imdecode(byteArr,Imgcodecs.IMREAD_ANYCOLOR);
        Imgcodecs.imencode(".jpg",imageFile,matOfByte);
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
