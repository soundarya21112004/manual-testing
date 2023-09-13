package com.eagle.mas.service;


import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class EncryptData {

    private static SecretKey secretKey;
    private static String mykey = "67556B58703273357638792F423F4528482B4D6250655368566D597133743677";

    public String AESEncrypt(String strToEncrypt) {
        try {
            setKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8"))); //For Base64 output
            return ByteToHexNew(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));  //For Hex output
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String AESDecrypt(String strToDecrypt) {
        try {
            setKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            // return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt))); //For Base64 input
            return new String(cipher.doFinal(HexToByteNew(strToDecrypt))); //For Hex input
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static void setKey() {
        try {
            byte[] raw = HexToByteNew(mykey);

            secretKey = new SecretKeySpec(raw, "AES");
        } catch (Exception e) {
            System.out.println("setKey: " + e.toString());
        }

    }

    public static byte[] HexToByteNew(String keyData) {

        byte[] val = null;
        try {
            val = new byte[keyData.length() / 2];
            for (int i = 0; i < val.length; i++) {
                int index = i * 2;
                int j = Integer.parseInt(keyData.substring(index, index + 2), 16);
                val[i] = (byte) j;
            }

            return val;

        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
            return val;
        }

    }


    public static String ByteToHexNew(byte[] publicKey) {
        String outValue = "";
        StringBuffer retString = new StringBuffer();
        try {


            for (int i = 0; i < publicKey.length; ++i) {
                retString.append(Integer.toHexString(0x0100 + (publicKey[i] & 0x00FF)).substring(1));
            }
            outValue = retString.toString();
            return outValue;


        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
            return outValue;
        }


    }
}
