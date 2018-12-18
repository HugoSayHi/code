package com.hugo.test;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by catt on 2018/9/17.
 */
public class Test {

    public static void main(String[] args) {

        String pass = BCrypt.hashpw("123456", BCrypt.gensalt());
        System.out.printf(pass);

    }

}
