package com.danz.service;

import android.util.Base64;

public class AwangDaniWoy {
    private static String msg;
    public static String encrypt(String text) {
        byte[] msg1 = Base64.encode(text.getBytes(), Base64.DEFAULT);
        msg = new String(msg1);

        return msg;
    }
    public static String decrypt(String text) {
        try{
            byte[] msg1 = Base64.decode(text, Base64.DEFAULT);
            msg = new String(msg1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return msg;
	}
}
