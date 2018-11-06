package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * Created by admin on 2018/11/6.
 */
public interface ICategoryService {

    ServerResponse<String> addCategory(String categoryName, Integer parentId);
    ServerResponse<String> updateCategoryName(Integer categoryId,String categoryName);

}
