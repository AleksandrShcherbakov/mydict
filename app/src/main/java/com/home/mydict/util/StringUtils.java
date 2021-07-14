package com.home.mydict.util;

import java.util.Arrays;

public class StringUtils {

    public static String generateEmptyString(int length){
        char[] chars = new char[length];
        Arrays.fill(chars, ' ');
        return new String(chars);
    }
}
