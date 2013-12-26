package qinyuan.lib.lang;

import java.text.DecimalFormat;
import java.util.Random;

public class MyMath {

	private MyMath() {
	}

	public static String round(double decimal, int precision) {
		String pattern = "#";
		if (precision > 0)
			pattern += "." + repeat('#', precision);

		return new DecimalFormat(pattern).format(decimal);
	}

	public static boolean isNumeric(String str) {
		if (str == null || str.isEmpty())
			return false;
		return str.matches("[-+]?(\\d+)([.](\\d+))?");
	}

	private static String repeat(char ch, int precision) {
		StringBuilder str = new StringBuilder();
		while ((precision--) > 0) {
			str.append(ch);
		}
		return str.toString();
	}

	public static String getRandomStr(int length) {
		char[] chs = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
				'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
				'Y', 'Z' };
		int charsLen = chs.length;
		StringBuilder s = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < length; i++) {
			char ch = chs[rand.nextInt(charsLen)];
			s.append(ch);
		}
		return s.toString();
	}
}
