package com.coursemanager.util.common;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES加密类
 */
public class DESCryptography {
	private static final String KEY = "ramskeys";
	public static void main(String[] args) {
		String content2 = "admin";	
		String decrypted = DESEncode(content2);
		System.out.println("加密后："+decrypted);
 	}
	
	
	public static String DESEncode(String password){
		try {
			return byteArrayToHexString(DES_CBC_Encrypt(password.getBytes("utf-8"), KEY.getBytes("utf-8")));
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String DESDecode(String password){
		try {
			return new String(DES_CBC_Decrypt(hexStringToByteArray(password), KEY.getBytes("utf-8")), "utf-8");
		} catch (Exception e) {
			return "";
		}
	}

	private static byte[] DES_CBC_Encrypt(byte[] content, byte[] keyBytes) {
		try {
			DESKeySpec keySpec = new DESKeySpec(keyBytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);

			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(keySpec.getKey()));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static byte[] DES_CBC_Decrypt(byte[] content, byte[] keyBytes) {
		try {
			DESKeySpec keySpec = new DESKeySpec(keyBytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);

			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(keyBytes));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String byteArrayToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
	
	private static byte[] hexStringToByteArray(String hex) {   
	    int len = (hex.length() / 2);   
	    byte[] result = new byte[len];   
	    char[] achar = hex.toCharArray();   
	    for (int i = 0; i < len; i++) {   
	     int pos = i * 2;   
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));   
	    }   
	    return result;   
	}  
	
	private static byte toByte(char c) {   
	    byte b = (byte) "0123456789ABCDEF".indexOf(c);   
	    return b;   
	}  
}