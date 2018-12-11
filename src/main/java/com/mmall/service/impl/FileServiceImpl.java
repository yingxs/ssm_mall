package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by admin on 2018/11/7.
 */
@Service("iFileService")
@Slf4j
public class FileServiceImpl implements IFileService {

    //private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();

        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        log.info("开始上传文件:{},上传的路径为:{},新文件名为:{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File tergetFile = new File(path, uploadFileName);

        try {
            file.transferTo(tergetFile);

            //将tergetFile上传到FTP服务器上
            boolean result = FTPUtil.uploadFile(Lists.newArrayList(tergetFile));

            //上传完成之后，删除upload下的文件
            if(result){
                tergetFile.delete();
            }

        } catch (IOException e) {
            log.error("文件上传异常",e);
            return null;
        }
        return tergetFile.getName();
    }
}
