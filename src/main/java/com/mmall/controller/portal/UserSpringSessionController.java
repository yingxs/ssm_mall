package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yingxs on 2018/11/3.
 */
@Controller
@RequestMapping("/user/springsession")
public class UserSpringSessionController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value="login.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> login(String username, String password , HttpSession session, HttpServletResponse httpServletResponse){
        ServerResponse<User> response = iUserService.login(username, password);

        int i = 10/0;

        if(response.isSuccess()){

            session.setAttribute(Const.CURRENT_USER,response.getData());
//            CookieUtil.writeLoginToken(httpServletResponse,session.getId());
//            //CookieUtil.readLoginToken(httpServletRequest);
//            //CookieUtil.delLoginToken(httpServletRequest,httpServletResponse);
//            RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping(value="logout.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session,HttpServletRequest httpServletRequest ,HttpServletResponse httpServletResponse){

//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        CookieUtil.delLoginToken(httpServletRequest,httpServletResponse);
//        RedisShardedPoolUtil.del(loginToken);
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }
    



    /**
     * 获取当前登陆用户的信息
     * @return
     */
    @RequestMapping(value="get_user_info.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session,HttpServletRequest httpServletRequest){


//        User user = (User)session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.createByErrorMessage("用户未登陆，无法获取用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登陆，无法获取用户的信息");
    }



}
