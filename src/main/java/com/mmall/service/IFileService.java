package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by admin on 2018/11/7.
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
