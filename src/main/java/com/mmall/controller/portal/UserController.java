package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisPoolUtil;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value="login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password , HttpSession session, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest){
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){

//            session.setAttribute(Const.CURRENT_USER,response.getData());
            CookieUtil.writeLoginToken(httpServletResponse,session.getId());
            CookieUtil.readLoginToken(httpServletRequest);
            CookieUtil.delLoginToken(httpServletRequest,httpServletResponse);
            RedisPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    /**
     * 登出
     * @param session
     * @return
     */
    @RequestMapping(value="logout.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }
    

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping(value="register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    /**
     * 校验名户名或Email是否存在
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value="check_valid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str, type);
    }

    /**
     * 获取当前登陆用户的信息
     * @param session
     * @return
     */
    @RequestMapping(value="get_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登陆，无法获取用户的信息");
    }

    /**
     * 获取提示问题
     * @param username
     * @return
     */
    @RequestMapping(value="forget_get_question.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }


    /**
     * 验证提示问题的答案
     * @param username
     * @return
     */
    @RequestMapping(value="forget_check_answer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username, question, answer);
    }


    @RequestMapping(value="forget_reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetRessetPassword(String username,String passwordNew,String forgetToken){

        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    @RequestMapping(value="reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        return  iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    @RequestMapping(value="update_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(HttpSession session,User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value="get_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> get_information(HttpSession session){
         User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErroCoderMessage(ResponseCode.NEED_LOGIN.getCode(),"未登陆，需要强制登陆status=10");
        }

        return iUserService.getInformation(currentUser.getId());
    }























}
