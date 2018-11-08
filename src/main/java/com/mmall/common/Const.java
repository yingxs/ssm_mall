package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by admin on 2018/11/3.
 */
public class Const {

    public static final String CURRENT_USER = "current_User";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface ProductListOrderBy{
        Set<String> PROCE_ASC_DESC =  Sets.newHashSet("price_desc","price_asc");
    }



    public interface Role{
        int ROLE_CUSTOMER = 0 ; //普通用户
        int ROLE_ADMIN = 1;     //管理员
    }


    public enum ProductStatusEnum{
        ON_SALE(1,"上架");
        private String value;
        private int code;
        ProductStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        public String  getValue(){
            return value;
        }

        public int getCode(){
            return code;
        }
    }

}
