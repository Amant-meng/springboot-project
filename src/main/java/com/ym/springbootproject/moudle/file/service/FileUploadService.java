package com.ym.springbootproject.moudle.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ym.springbootproject.common.ResultBody;
import com.ym.springbootproject.moudle.file.entity.UploadEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileUploadService extends IService<UploadEntity> {


    ResultBody upload(MultipartFile file, String baseDir) throws Exception;

    void download(HttpServletResponse response , String fileName) throws IOException;

    ResultBody searchMember(MultipartFile file, String name);

    ResultBody getImages(HttpServletResponse response, String name);
}
