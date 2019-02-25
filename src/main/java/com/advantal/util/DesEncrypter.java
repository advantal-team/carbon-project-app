/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantal.util;

import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.log4j.Logger;

public class DesEncrypter {

    private static final Logger log = Logger.getLogger(DesEncrypter.class);
    private static Cipher ecipher;
    private static Cipher dcipher;
    private static final String keyText = "VRHKG34YW4WANDJCHBFGNHH";
    private static final String ALGO_NAME = "PBEWithMD5AndDES";
    private static final byte[] salt = {
        (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };
    private static final int iterationCount = 3;

    private static void prepareKey() {
        try {

            KeySpec keySpec = new PBEKeySpec(keyText.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance(ALGO_NAME).generateSecret(keySpec);

            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        } catch (Exception e) {
            log.error("Failed to prepare Key prepareKey() : " + e.getMessage());
        }
    }

    static {
        prepareKey();
    }

    public static String encrypt(String str) {
        if (str == null) {
            return null;
        }
//        prepareKey();
        try {

            byte[] utf8 = str.getBytes("UTF8");
            byte[] enc = ecipher.doFinal(utf8);
//            return new sun.misc.BASE64Encoder().encode(enc);
            return org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(enc);

        } catch (IllegalBlockSizeException e) {
            log.error("error in IllegalBlockSizeException in encrypt method for string : " + str + " : " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("error in UnsupportedEncodingException in encrypt method for string : " + str + " : " + e.getMessage());
        } catch (NullPointerException e) {
            log.error("error in NullPointerException in encrypt method for string : " + str + " : " + e.getMessage());
        } catch (Exception e) {
            log.error("error in Exception in encrypt method for string : " + str + " : " + e.getMessage());
        }
        return null;
    }

    public static String decrypt(String str) {
        if (str == null || str.trim().equals("")) {
            return null;
        }
//        prepareKey();
        try {
//            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
            byte[] dec = org.apache.commons.codec.binary.Base64.decodeBase64(str);
            byte[] utf8 = dcipher.doFinal(dec);

            return new String(utf8, "UTF8");

        } catch (IllegalBlockSizeException e) {
            log.error("error in IllegalBlockSizeException in decrypt method for string : " + str + " : " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("error in UnsupportedEncodingException in decrypt method for string : " + str + " : " + e.getMessage());
        } catch (NullPointerException e) {
            log.error("error in NullPointerException in decrypt method for string : " + str + " : " + e.getMessage());
        } catch (Exception e) {
            log.error("error in Exception in decrypt method for string : " + str + " : " + e.getMessage());
        }
        return "Invalid String";
    }

    public static String decrypt(String str, String key) {
        if (str == null || str.trim().equals("")) {
            return null;
        }
//        prepareKey();
        try {
//            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
            byte[] dec = org.apache.commons.codec.binary.Base64.decodeBase64(str);
            byte[] utf8 = dcipher.doFinal(dec);
            if (key != null) {
                return new String(utf8, "UTF8").replace(key, "");
            } else {
                return new String(utf8, "UTF8");
            }
//        } catch (IllegalBlockSizeException e) {
//        } catch (UnsupportedEncodingException e) {
//        } catch (NullPointerException e) {
        } catch (Exception e) {
            log.error("error in Exception in decrypt method for string : " + str + ",key : " + key + " : " + e.getMessage());
        }

        return "Invalid String";
    }

    public static String encryptWithKey(String str, String keyText) {
        if (str == null) {
            return null;
        }
        Cipher ecipher = null;
        Cipher dcipher = null;
        byte[] salt = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
        };
        int iterationCount = 3;

        try {
            KeySpec keySpec = new PBEKeySpec(keyText.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance(ALGO_NAME).generateSecret(keySpec);

            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        } catch (java.security.InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            log.error("error in InvalidAlgorithmParameterException in encryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        } catch (java.security.spec.InvalidKeySpecException e) {
            e.printStackTrace();
            log.error("error in InvalidKeySpecException in encryptWithKey method for string : " + str + ",key : " + keyText + " : " + " : " + e.getMessage());
        } catch (javax.crypto.NoSuchPaddingException e) {
            e.printStackTrace();
            log.error("error in NoSuchPaddingException in encryptWithKey method for string : " + str + ",key : " + keyText + " : " + " : " + e.getMessage());
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("error in NoSuchAlgorithmException in encryptWithKey method for string : " + str + ",key : " + keyText + " : " + " : " + e.getMessage());
        } catch (java.security.InvalidKeyException e) {
            e.printStackTrace();
            log.error("error in InvalidKeyException in encryptWithKey method for string : " + str + ",key : " + keyText + " : " + " : " + e.getMessage());
        }
        try {
            byte[] utf8 = str.getBytes("UTF8");
//            utf8 = fromHexString(str);
            byte[] enc = ecipher.doFinal(utf8);
//            return new sun.misc.BASE64Encoder().encode(enc);
            return org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(enc);
//            return asHex(enc);

        } catch (javax.crypto.BadPaddingException e) {
            e.printStackTrace();
            log.error("error in BadPaddingException in encryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            log.error("error in IllegalBlockSizeException in encryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("error in UnsupportedEncodingException in encryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            log.error("error in NullPointerException in encryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        }
        return null;
    }

    public static String decryptWithKey(String str, String keyText) {
        if (str == null || str.trim().equals("")) {
            return null;
        }
//        Cipher ecipher = null;
        Cipher dcipherLoc = null;
        byte[] salt = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
        };
        int iterationCountLoc = 3;
        try {
            KeySpec keySpec = new PBEKeySpec(keyText.toCharArray(), salt, iterationCountLoc);
            SecretKey key = SecretKeyFactory.getInstance(ALGO_NAME).generateSecret(keySpec);

//            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipherLoc = Cipher.getInstance(key.getAlgorithm());

            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCountLoc);

//            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipherLoc.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (java.security.InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            log.error("error in InvalidAlgorithmParameterException in decryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        } catch (java.security.spec.InvalidKeySpecException e) {
            e.printStackTrace();
            log.error("error in InvalidKeySpecException in decryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        } catch (javax.crypto.NoSuchPaddingException e) {
            e.printStackTrace();
            log.error("error in NoSuchPaddingException in decryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("error in NoSuchAlgorithmException in decryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        } catch (java.security.InvalidKeyException e) {
            e.printStackTrace();
            log.error("error in InvalidKeyException in decryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        }
        try {
//            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
            byte[] dec = org.apache.commons.codec.binary.Base64.decodeBase64(str);
//            byte[] dec = fromHexString(str);
            byte[] utf8 = dcipherLoc.doFinal(dec);
            return new String(utf8, "UTF8");

        } catch (javax.crypto.BadPaddingException e) {
            e.printStackTrace();
            log.error("error in BadPaddingException in decryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            log.error("error in IllegalBlockSizeException in decryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("error in UnsupportedEncodingException in decryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            log.error("error in NullPointerException in decryptWithKey method for string : " + str + ",key : " + keyText + " : " + e.getMessage());
        }
        return "Invalid String";
    }

    public static String encryptURI(String str) {
//        prepareKey();
        try {
            byte[] utf8 = str.getBytes("UTF8");
            byte[] enc = ecipher.doFinal(utf8);
            return org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(enc);
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }

        return null;
    }

    public static String decryptURI(String str) {
//        prepareKey();
        try {
            byte[] dec = org.apache.commons.codec.binary.Base64.decodeBase64(str);
            byte[] utf8 = dcipher.doFinal(dec);

            return new String(utf8, "UTF8");

        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }

        return "Invalid String";

    }

    public static void main(String[] args) {
           System.out.println("Pass142=" + encrypt("pass@123"));
           System.out.println("Pass144=" + decrypt("JvjUwNgXu7VDem03GdV5LA"));
    }
}
