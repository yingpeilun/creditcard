package com.credit.utils;

import org.mindrot.jbcrypt.BCrypt;

public class CodesUtils {

    /**
     * 生成加密后的密码
     * @param password
     * @param salt
     * @return
     */
    public static String generatePassword(String password,String salt) {
        return BCrypt.hashpw(password, salt);
    }

    /**
     * 验证密码
     * @param password
     * @param salt
     * @return
     */
    public static Boolean validate(String password, String salt) {
        return BCrypt.checkpw(password, salt);
    }

    /**
     * 生成盐
     * @return
     */
    public static String generateSalt(){
        return BCrypt.gensalt(10);
    }

}

