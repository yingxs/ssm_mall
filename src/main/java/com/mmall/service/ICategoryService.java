package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by admin on 2018/11/6.
 */
public interface ICategoryService {

    ServerResponse<String> addCategory(String categoryName, Integer parentId);
    ServerResponse<String> updateCategoryName(Integer categoryId,String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse selectCategoryAndChildrenById(Integer categoryId);
}
