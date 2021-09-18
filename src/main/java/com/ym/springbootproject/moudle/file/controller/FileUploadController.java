package com.ym.springbootproject.moudle.file.controller;

import com.ym.springbootproject.common.ResultBody;
import com.ym.springbootproject.moudle.file.entity.UploadEntity;
import com.ym.springbootproject.moudle.file.service.FileUploadService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  文件上传、下载入库接口
 * @Author  yangmeng
 * @Date 2021/06/28
 */
@RestController
@Api(value = "文件上传、下载、入库",tags = {"文件上传、下载、入库控制类接口"})
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 文件上传，保存默认的文件路径
     * 默认文件路径配置在 FileUploadUtils工具里，
     * D:\workspace\springboot-project\file
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/upload")
    public ResultBody upload(MultipartFile file) throws Exception {
        return fileUploadService.upload(file, null);

    }

    /**
     * 文件上传，自定义文件保存路径
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/upload/template")
    public ResultBody uploadPlace(MultipartFile file) throws Exception {

        return fileUploadService.upload(file, "E:\\upload");

    }


    /**
     *  文件下载
     * @param response
     * @param fileName 下载文件名称
     * @return
     * @throws IOException
     */
    @GetMapping("/downloadFile")
    public void downloadFile(HttpServletResponse response,@RequestParam String fileName) throws IOException {
        fileUploadService.download(response, fileName);

    }


    /**
     * 保存图片，图片保存入库
     * @param file
     * @param name
     * @return
     */
    @PostMapping (value = "/saveimg")
    @ResponseBody
    public ResultBody searchMember(@RequestParam("file") MultipartFile file,@RequestParam("name") String name){

        return fileUploadService.searchMember(file,name);

    }


    /**
     * 获取照片，数据库获取返回前端
     * @param response
     * @param name 文件名称
     * @return
     */
    @GetMapping (value = "/getImages")
    public ResultBody getImages(HttpServletResponse response,@RequestParam("name") String name){

        return fileUploadService.getImages(response,name);

    }


    /**
     *  图片下载功能
     * @param id id  对应数据库中哪个照片的id（前端id参数）
     * @param response 返回给前端数据（ 图片下载）
     * @throws IOException
     */
    @GetMapping("/downloadImage")
    public void downloadImage(Integer id , HttpServletResponse response ) throws IOException {

        //查找对应图片二进制文件
        UploadEntity entity = fileUploadService.getById(id);

        //保存后图片的命名
        Date date = new Date(); //获取当前的系统时间。
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss") ; //使用了默认的格式创建了一个日期格式化对象。
        String time = dateFormat.format(date); //可以把日期转换转指定格式的字符串
        //拼接，得到保存后的文件名
        String fileName = entity.getName() + ".png";

        //获取到二进制图片数据
        byte[] data = entity.getFileContent();
        //设置文件名编码
        fileName = URLEncoder.encode(fileName, "UTF-8");
        //清除缓冲区中存在的任何数据以及状态代码和标头。如果已提交响应，此方法将抛出一个IllegalStateException。
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");

        //用于处理字符流数据 response.getWriter()
        //用于输出字符流数据或者二进制的字节流数据   response.getOutputStream()
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        response.flushBuffer();


    }



}
