package com.ym.springbootproject.moudle.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ym.springbootproject.common.ResultBody;
import com.ym.springbootproject.common.utils.file.FileTypeUtils;
import com.ym.springbootproject.common.utils.file.FileUtils;
import com.ym.springbootproject.moudle.file.FileUploadUtils;
import com.ym.springbootproject.moudle.file.entity.UploadEntity;
import com.ym.springbootproject.moudle.file.mapper.FileUploadMapper;
import com.ym.springbootproject.moudle.file.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @Description TODO
 * @Author yangmeng
 * @Date  2021/7/2
 **/
@Service
public class FileUploadServiceImpl extends ServiceImpl<FileUploadMapper,UploadEntity> implements FileUploadService {

    @Autowired
    FileUploadMapper uploadMapper;


    /**
     * 文件上传功能
     * @param file 上传文件
     * @param baseDir 指定文件保存的路径
     * @throws Exception
     * @return
     */
    @Override
    public ResultBody upload(MultipartFile file, String baseDir) throws Exception {
        //就算什么也不传，controller层的file也不为空，但是originalFilename会为空（亲测）
        String originalFilename = file.getOriginalFilename();

        //TODO 上传文件名称
        String uploadFileName = FileTypeUtils.getFileName(originalFilename);

        if(originalFilename == null || "".equals(originalFilename)) {
            throw new Exception( "上传文件不能为空");
        }
        //TODO 检测是否上传过同样的文件，如果有的话就删除。（这边可根据个人的情况修改逻辑）
        QueryWrapper<UploadEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("old_name", originalFilename);
        UploadEntity oldEntity = uploadMapper.selectOne(queryWrapper);

        //TODO 新的文件
        UploadEntity uploadEntity = new UploadEntity();
        uploadEntity.setCreateTime(new Date());
        //uploadEntity.setUpdateTime(new Date());
        uploadEntity.setOldName(file.getOriginalFilename()); //这边可以根据业务修改，项目中不要写死
        uploadEntity.setName(uploadFileName);//保存上传文件名称
        String fileLocation = null ;
        if(baseDir != null) {
            fileLocation = FileUploadUtils.upload(baseDir, file);
        }else {
            //Todo 如果文件上传保存位置为空，则使用默认
            fileLocation = FileUploadUtils.upload(file);
        }

        uploadEntity.setLocation(fileLocation);
        uploadMapper.insert(uploadEntity);

        if(oldEntity != null) {
            //确保新的文件保存成功后，删除原有的同名文件(实体文件 and 数据库文件)
            FileUtils.deleteFile(oldEntity.getLocation());
            uploadMapper.deleteById(oldEntity.getId());
        }
        return ResultBody.ok();
    }

    /**
     *
     * @param response
     * @param fileName
     * @throws IOException
     */
    @Override
    public void download(HttpServletResponse response, String fileName)
            throws IOException {
        QueryWrapper<UploadEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", fileName);
        UploadEntity uploadEntity = uploadMapper.selectOne(queryWrapper);

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");        //这边可以设置文件下载时的名字，我这边用的是文件原本的名字，可以根据实际场景设置
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(uploadEntity.getOldName(), "UTF-8"));
        FileUtils.writeBytes(uploadEntity.getLocation(), response.getOutputStream());
    }

    /**
     * 保存图片
     * @param file
     * @param name
     * @return
     */
    @Override
    public ResultBody searchMember(MultipartFile file, String name) {
        try {
            InputStream ins = file.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while((len=ins.read(buffer))!= -1){
                bos.write(buffer,0,len);
            }
            bos.flush();
            byte data[] = bos.toByteArray();

            UploadEntity uploadEntity = new UploadEntity();
            uploadEntity.setName(name);
            uploadEntity.setCreateTime(new Date());
            uploadEntity.setFileContent(file.getBytes());
            uploadEntity.setOldName(file.getOriginalFilename());
            uploadMapper.insert(uploadEntity);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResultBody.ok();
    }

    /**
     * 获取图片，数据库获取返回前端
     * @param response
     * @param name
     * @return
     */
    @Override
    public ResultBody getImages(HttpServletResponse response, String name) {
        QueryWrapper<UploadEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("name",name);
        UploadEntity uploadEntity = uploadMapper.selectOne(queryWrapper);
        if(uploadEntity == null){
            ResultBody.error("照片不存在");
        }else{
            byte[] data = uploadEntity.getFileContent();
            try {
                response.setCharacterEncoding("UTF-8");
                ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
                OutputStream outputStream = response.getOutputStream();
                InputStream  bis = new BufferedInputStream(inputStream);
                int len = 0;
                byte[] buffer = new byte[1024];
                while((len =bis.read(buffer,0,1024)) != -1){
                    outputStream.write(buffer,0,len);
                }
                outputStream.flush();
                outputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return ResultBody.ok();
    }

}
