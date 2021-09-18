package com.ym.springbootproject.common.utils.ZipUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *  bcp包数据处理,获取指定路径下的指定格式的文件
 * @author yangmeng
 * @date 2021/06/09
 */
public class BcpDataDeal {

    /**
     * 获取指定路径下的指定格式的文件
     * @param file
     */
    public static void listPath(File file) {
        // 接收筛选过后的文件对象数组
        //用文件对象调用listFiles(FilenameFilter filter);方法,
        //返回抽象路径名数组，这些路径名表示此抽象路径名表示的目录中满足指定过滤器的文件和目录
        File files[] = file.listFiles(new MyFilenameFilter());


        //遍历出指定文件路径下的所有符合筛选条件的文件
        for (File f : files) {
            readFileContent(f);
            if (f.isDirectory()) {
                listPath(f);
            } else {
                System.out.println(f);
            }
        }

    }

    /**
     * 读取文件内容
     * @param fileName
     * @return
     */
    public static String readFileContent(File fileName) {
       // File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                System.out.println("bcp内容："+ tempStr);
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }


    public static void main(String[] args) {
        // 创建指定目录的文件对象
        File file = new File("E:\\ftp\\test");
        // 调用文件晒筛选的方法,并将文件对象出入,
        listPath(file);
    }


}