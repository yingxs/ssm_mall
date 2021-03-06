package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {

    private final static String COOKIE_DOMAIN = ".mmall.com";
    private final static String COOKIE_NAME = "mmall_login_token";


    public static String readLoginToken(HttpServletRequest request){

        Cookie[] cks = request.getCookies();
        if(cks != null){
            for (Cookie ck : cks){
                log.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    log.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }

        }

        return null;

    }

    public static void writeLoginToken(HttpServletResponse response, String token){
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");

        //单位为秒，不设置就表示当前会话结束，删除cookie,-1表示永久
        ck.setMaxAge(60*60);
        log.info("write cookiceName:{},cookieValue:{}",ck.getName(),ck.getValue());
        response.addCookie(ck);
    }

    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck : cks){
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//设置为0，代表删除此cookie
                    log.info("del cookiceName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    response.addCookie(ck);
                    return ;
                }
            }
        }
    }

}
