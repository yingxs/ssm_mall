package com.mmall.service;


import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * Created by yingxs on 2018/11/3.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);
}
