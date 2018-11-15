package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * Created by admin on 2018/11/15.
 */
public interface IOrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);

}
