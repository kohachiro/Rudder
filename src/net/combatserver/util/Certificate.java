package net.combatserver.util;

import java.security.MessageDigest;
import java.util.Random;

/**
 * @author kohachiro
 * 
 */
public class Certificate {
	final static Random random = new Random();

	public static String gen() throws Exception {
		return md5s(String.valueOf(random.nextFloat()))
				+ md5s(String.valueOf(System.currentTimeMillis()));
	}

	private static String md5s(String plainText) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(plainText.getBytes());
		byte b[] = md.digest();

		int i;

		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();

	}
}
