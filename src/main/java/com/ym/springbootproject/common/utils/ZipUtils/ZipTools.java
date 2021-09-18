package com.ym.springbootproject.common.utils.ZipUtils;


import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.util.Enumeration;

/**
 * zip文件的解压工具类
 * @author yangmeng
 * @date 2021/06/08
 */
public class ZipTools {

    /**
     *  解压zip文件夹
     * @param zipFile zip目标文件
     * @param descDir 解压到指定文件夹
     * @throws IOException
     */
    public static void unzipFile(File zipFile,String descDir) throws IOException {
        System.out.println("文件解压开始.............");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File targetFile = new File(descDir);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile);
        for(Enumeration entries = zip.getEntries();entries.hasMoreElements();){
            ZipEntry zipEntry = (ZipEntry) entries.nextElement();
            String entryName = zipEntry.getName();
            inputStream = zip.getInputStream(zipEntry);
            String outPath = (descDir+"/"+entryName).replaceAll("\\*","/");
            File file = new File(outPath.substring(0,outPath.lastIndexOf("/")));
            if(!file.exists()){
                file.mkdirs();
            }
            if(new File(outPath).isDirectory()){
                continue;
            }
            outputStream = new FileOutputStream(outPath);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = inputStream.read(bytes))>0){
                outputStream.write(bytes,0,length);
            }
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        zip.close();
        System.gc();

        System.out.println("***********zip压缩包解压完成************");
    }

    /**
     * 解压Zip文件
     * @param zipFileName 需要解压缩的文件位置
     * @param descFileName 将文件解压到某个路径
     * @throws IOException
     */
    public static void unZip(String zipFileName,String descFileName) throws IOException{
        System.out.println("文件解压开始...");
        String descFileNames = descFileName;
        File destDir = new File(descFileName);
        // 判断文件夹是否存在
        if(!destDir.exists()){
            //创建文件夹
            destDir.mkdirs();
        }
        if(!descFileNames.endsWith(File.separator)){
            descFileNames = descFileNames + File.separator;
        }
        try {
            ZipFile zipFile = new ZipFile(zipFileName);
            ZipEntry entry = null;
            String entryName = null;
            String descFileDir = null;
            byte[] buf = new byte[4096];
            int readByte = 0;
            @SuppressWarnings("rawtypes")
            Enumeration enums = zipFile.getEntries();
            while(enums.hasMoreElements()){
                entry =(ZipEntry) enums.nextElement();
                entryName = entry.getName();
                descFileDir = descFileNames + entryName;
                if(entry.isDirectory()){
                    new File(descFileDir).mkdir();
                    continue;
                }else{
                    new File(descFileDir).getParentFile().mkdir();
                }
                File file = new File(descFileDir);
                OutputStream os = new FileOutputStream(file);
                InputStream is = zipFile.getInputStream(entry);
                while((readByte = is.read(buf))!=-1){
                    os.write(buf, 0, readByte);
                }
                os.close();
                is.close();
            }
            zipFile.close();
            System.out.println("文件解压成功!");
        } catch (Exception e) {
            System.out.println("文件解压失败!");
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws Exception {

        //new ZipTools().unZipFiles(new File("E:\\ftp\\00001.zip"), "E:\\test");
        new ZipTools().unZip("E:\\ftp\\00001.zip", "E:\\zipTest");
        unzipFile(new File("E:\\ftp\\00001.zip"), "E:\\zipTest002");
    }

}
