package com.mmall.common;

/**
 * Created by admin on 2018/11/3.
 */
public class Const {

    public static final String CURRENT_USER = "current_User";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";


    public interface Role{
        int ROLE_CUSTOMER = 0 ; //普通用户
        int ROLE_ADMIN = 1;     //管理员
    }

}
