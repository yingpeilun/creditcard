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
     * @param inputPwd
     * @param dataPwd
     * @return
     */
    public static Boolean validate(String inputPwd, String dataPwd) {
        boolean checkpw = BCrypt.checkpw(inputPwd, dataPwd);
        System.out.println("checkpw:"+checkpw);
        return checkpw;
    }

    /**
     * 生成盐
     * @return
     */
    public static String generateSalt(){
        return BCrypt.gensalt(10);
    }

}

