package kr.triniti.common.util;

import java.text.DecimalFormat;
import java.util.Random;

public class RandomUtil {
    public static int power(int a, int b) {
        return Math.round((int) Math.pow(a, b));
    }

    public static String decPattern(int length) {
        return decPattern(length, true);
    }

    public static String decPattern(int length, boolean unavailabilityZero) {
        String padCh = unavailabilityZero ? "0" : "9";
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buff.append(padCh);
        }
        return buff.toString();
    }

    public static long getRandomNumber(int length) {
        return getRandomNumber(0, power(10, length));
    }

    public static long getRandomNumber(int min, int max) {
        return new Random().longs(min, max).findFirst().getAsLong();
    }
    
//    public static long getRandomDouble(double min, double max) {
//        return new Random().longs(min, max).findFirst().getAsLong();
//    }

    public static String getRandomNumberText(int length) {
        DecimalFormat df = new DecimalFormat(decPattern(length));

        return df.format(getRandomNumber(length));
    }

    public static String getRandomNumberText(int min, int max) {
        return getRandomNumberText(max - min);
    }

    public static char getRandomChar() {
        return (char) (int) (Math.round(Math.random() * 95.0D) + 32L);
    }
    
    final static char [] LETTERS = {
    	'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
    	'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    	'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    	'~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '{', '}', '[', ']',  
    	'-', '_', '+', '=', ':', ';', '"', '\'', '<', '>', ',', '.', '?', '/', '\\'
    };
    
    public static char getRandomAlphaNumeric() {
    	return RandomUtil.LETTERS[(int) Math.ceil(Math.random() * (62 - 1))]; 
    }
    
    public static char getRandomHasSpecialChar() {
    	return RandomUtil.LETTERS[(int) Math.ceil(Math.random() * (RandomUtil.LETTERS.length - 1))];
    }
    
    public static String getRandomStr(int length) {
    	return getRandomStr(length, false);
    }
    
    public static String getRandomStr(int length, boolean hasSpacical) {
    	StringBuffer buff = new StringBuffer();
    	
    	for (int i = 0; i < length; i++) {
    		buff.append(hasSpacical ? getRandomHasSpecialChar() : getRandomAlphaNumeric());
    	}
    	
    	return buff.toString();
    }
    
	public class RandomItem {
		String id;
		RandomItem item;
		double ratio;
	}
	
	public class RandomBox {
		java.util.List<RandomItem> box;
	}
	
	public static void main(String [] args) {
		//Random r = new Random();
		int maxLimit = 20;
		int loopCount = 1000000;
		long [] rCount = new long[maxLimit];
		
		for (int i = 0; i < maxLimit; i++) {
			rCount[i] = 0;
		}
		
		for (long i = 0; i < loopCount; i++) {
			long rnum = getRandomNumber(0, maxLimit);
			
			rCount[(int) rnum]++;
		}
		
		for (int i = 0; i < maxLimit; i++) {
			System.out.println("RCount[" + i + "]=" + rCount[i]);
		}
		
		String password = getRandomStr(32, true);
		System.out.println(password + "=> correct Password : " + kr.triniti.tools.password.PasswordUtil.checkPassword(password));
	}
}
