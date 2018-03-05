package com.nami.test;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Encrypt {

	public static final String KEY_MD = "MD5";

	public static String getResult(String inputStr) {
		System.out.println("=======加密前的数据:" + inputStr);
		BigInteger bigInteger = null;
		try {
			MessageDigest md = MessageDigest.getInstance(KEY_MD);
			byte[] inputData = inputStr.getBytes();
			md.update(inputData);
			bigInteger = new BigInteger(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("MD加密后:" + bigInteger.toString());
		return bigInteger.toString();
	}

	public static void main(String args[]) {
		/*try {
			String inputStr = "简单加密";
			getResult(inputStr);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		System.out.println("------");
	}
}
