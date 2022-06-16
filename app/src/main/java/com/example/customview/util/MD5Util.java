package com.example.customview.util;

import java.security.MessageDigest;

public class MD5Util {
    public static String md5(String string){
        if(string==null){
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for(byte b:bytes){
                String temp = Integer.toHexString(b & 0xff);
                if(temp.length()==1){
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result.toUpperCase();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
