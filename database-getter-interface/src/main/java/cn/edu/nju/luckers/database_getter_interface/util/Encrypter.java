package cn.edu.nju.luckers.database_getter_interface.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {

	public static String encrypt(String password){
		
		String result = "";
        if (password != null) {
            try {
                // 指定加密的方式为MD5
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 进行加密运算
                byte bytes[] = md.digest(password.getBytes());
                for (int i = 0; i < bytes.length; i++) {
                    // 将整数转换成十六进制形式的字符串 这里与0xff进行与运算的原因是保证转换结果为8位
                    String str = Integer.toHexString(bytes[i] & 0xFF);
                    if(str == null){
                    	break;
                    }
                    if (str.length() == 1) {
                        str += "F";
                    }
                    result += str;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return result.toUpperCase();
		
	}
}
