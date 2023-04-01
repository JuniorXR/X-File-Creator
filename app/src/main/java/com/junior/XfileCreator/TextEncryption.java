package com.junior.XfileCreator;

public class TextEncryption {

	public static String Encrypt(String text) {
		char[] textToEncrypt = text.toCharArray();
		String s = new String();
		for (char c : textToEncrypt) {
			if (String.valueOf(c).equals("\n")) {
				s+=c;
				continue;
			} else {
				c += 42;
				s += c;
			}
		}
		return s;
	}

	public static String Decrypt(String text) {
		char[] textToEncrypt = text.toCharArray();
		String s = new String();
		for (char c : textToEncrypt) {
			if (String.valueOf(c).equals("\n")) {
				s+=c;
				continue;
			} else {
				c -= 42;
				s += c;
			}
		}
		return s;
	}

}